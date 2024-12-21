package javafxproj;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.List;

public class mainclass extends Application {

    private Stage mainStage;
    private List<Place> allPlaces = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        // إنشاء البيانات الأساسية
        populatePlaces();

        // إنشاء الشاشة الرئيسية
        VBox mainScreen = createMainScreen();

        // إعداد المشهد الأساسي
        Scene mainScene = new Scene(mainScreen, 600, 400);
        mainStage.setTitle("الأماكن الأثرية");
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    private VBox createMainScreen() {
        // شريط الفلتر
        ComboBox<String> filterBar = new ComboBox<>();
        filterBar.getItems().addAll("القاهرة", "الأقصر", "الإسكندرية", "أسوان", "الجيزة");
        filterBar.setPromptText("اختر المحافظة");

        Button filterButton = new Button("فلتر");

        // شبكة الأماكن
        GridPane placesGrid = new GridPane();
        placesGrid.setPadding(new Insets(10));
        placesGrid.setHgap(10);
        placesGrid.setVgap(10);

        // عرض كل الأماكن افتراضيًا
        updateGrid(placesGrid, allPlaces);

        // وظيفة الفلتر
        filterButton.setOnAction(e -> {
            String selectedGovernorate = filterBar.getValue();
            if (selectedGovernorate != null) {
                List<Place> filteredPlaces = filterPlacesByGovernorate(selectedGovernorate);
                updateGrid(placesGrid, filteredPlaces);
            }
        });

        // ScrollPane للأماكن
        ScrollPane scrollPane = new ScrollPane(placesGrid);
        scrollPane.setFitToWidth(true);

        VBox filterBox = new VBox(10, filterBar, filterButton);
        filterBox.setPadding(new Insets(10));

        // دمج المكونات
        VBox root = new VBox(10, filterBox, scrollPane);
        root.setPadding(new Insets(10));
        return root;
    }

    private void updateGrid(GridPane grid, List<Place> places) {
        grid.getChildren().clear();
        int row = 0, col = 0;

        for (Place place : places) {
            VBox placeBox = createPlaceBox(place);
            grid.add(placeBox, col, row);
            col++;
            if (col > 2) { // 3 أماكن في كل صف
                col = 0;
                row++;
            }
        }
    }

    private VBox createPlaceBox(Place place) {
        Image image = new Image(place.getImagePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        Text placeText = new Text(place.getName());
        Button detailsButton = new Button("عرض المزيد");

        // عند الضغط على زر التفاصيل
        detailsButton.setOnAction(e -> showDetailsScreen(place));

        return new VBox(5, imageView, placeText, detailsButton);
    }

    private void showDetailsScreen(Place place) {
        Image image = new Image(place.getImagePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        Text title = new Text(place.getName());
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Text description = new Text(place.getDetails());

        Button backButton = new Button("رجوع");
        backButton.setOnAction(e -> mainStage.setScene(new Scene(createMainScreen(), 600, 400)));

        VBox detailsBox = new VBox(10, imageView, title, description, backButton);
        detailsBox.setPadding(new Insets(10));
        detailsBox.setStyle("-fx-alignment: center;");

        Scene detailsScene = new Scene(detailsBox, 600, 400);
        mainStage.setScene(detailsScene);
    }

    private List<Place> filterPlacesByGovernorate(String governorate) {
        List<Place> filteredPlaces = new ArrayList<>();
        for (Place place : allPlaces) {
            if (place.getGovernorate().equals(governorate)) {
                filteredPlaces.add(place);
            }
        }
        return filteredPlaces;
    }

    private void populatePlaces() {
        allPlaces.add(new Place("الأهرامات", "القاهرة", "images/pyramids.jpg", "معلومات عن الأهرامات..."));
        allPlaces.add(new Place("معبد الكرنك", "الأقصر", "images/karnak_temple.jpg", "معلومات عن معبد الكرنك..."));
        allPlaces.add(new Place("قلعة قايتباي", "الإسكندرية", "images/qaitbay_castle.jpg", "معلومات عن قلعة قايتباي..."));
        allPlaces.add(new Place("جزيرة فيلة", "أسوان", "images/philae_island.jpg", "معلومات عن جزيرة فيلة..."));
        allPlaces.add(new Place("أبو الهول", "الجيزة", "images/sphinx.jpg", "معلومات عن أبو الهول..."));
        allPlaces.add(new Place("معبد حتشبسوت", "الأقصر", "images/hatshpsut_temple.jpg", "معلومات عن معبد حتشبسوت..."));
        allPlaces.add(new Place("المتحف المصري", "القاهرة", "images/egyptian_museum.jpg", "معلومات عن المتحف المصري..."));
        allPlaces.add(new Place("مكتبة الاسكندرية", "الإسكندرية", "images/alexanderia.jpg", "معلومات عن  مكتبة لاسكندرية..."));
        allPlaces.add(new Place("الازهر", "القاهرة", "images/alazhar.jpg", "معلومات عن المتحف المصري..."));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Place {
    private String name;
    private String governorate;
    private String imagePath;
    private String details;

    public Place(String name, String governorate, String imagePath, String details) {
        this.name = name;
        this.governorate = governorate;
        this.imagePath = imagePath;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getGovernorate() {
        return governorate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDetails() {
        return details;
    }
}
