import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {
	private int WIDTH = 640, HEIGHT = 480, SIDEPANEWIDTH = 100;
	private BorderPane root;
	private VBox box;

	private GridPane oversigtPane;
	private GridPane overforPane;
	private GridPane renterPane;
	private GridPane infoPane;
	private GridPane atmPane;
	private GridPane lommePane;
	private GridPane valutaPane;

	private Overfor overfor;
	private Renter renter;
	private Info info;
	private ATM atm;
	private Lommeregner lomme;
	private Valuta valuta;

	private Konto opsparing;
	private Dialogbox db;

	private Button knapTilfoj;
	private boolean tilfojKnapCondition;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		primaryStage.setTitle("Sparekasse");
		primaryStage.setResizable(false);

		// Definerer elementerne
		root = new BorderPane();
		box = new VBox();

		oversigtPane = new GridPane();
		overforPane = new GridPane();
		renterPane = new GridPane();
		infoPane = new GridPane();
		atmPane = new GridPane();
		lommePane = new GridPane();
		valutaPane = new GridPane();

		db = new Dialogbox();
		overfor = new Overfor();
		renter = new Renter();
		info = new Info();
		atm = new ATM();
		lomme = new Lommeregner();
		valuta = new Valuta();

		Button knap = Knap.createSideButton("Oversigt", "/ikon_oversigt.png");
		knap.setOnAction(event -> {
			root.setCenter(oversigtPane);
			update();
		});

		Button knapOverfor = Knap.createSideButton("Overfør", "/ikon_overfor.png");
		knapOverfor.setStyle("-fx-padding: 5px 5px 5px 0;");
		knapOverfor.setOnAction(event -> {
			root.setCenter(overforPane);
			update();
		});

		Button knapRenter = Knap.createSideButton("Renter", "/ikon_renter.png");
		knapRenter.setStyle("-fx-padding: 5px 12px 5px 0;");
		knapRenter.setOnAction(event -> {
			root.setCenter(renterPane);
			update();
		});

		Button knapInfo = Knap.createSideButton("Info", "/ikon_info.png");
		knapInfo.setStyle("-fx-padding: 5px 33px 5px 0;");
		knapInfo.setOnAction(event -> {
			root.setCenter(infoPane);
			update();
		});

		Button knapAtm = Knap.createSideButton("ATM", "/ikon_atm.png");
		knapAtm.setStyle("-fx-padding: 5px 27px 5px 0");
		knapAtm.setOnAction(e -> {
			root.setCenter(atmPane);
			update();
		});

		Button knapLomme = Knap.createSideButton("Regner", 16, "/ikon_lomme.png");
		knapLomme.setStyle("-fx-padding: 5px 8px 5px 0");
		knapLomme.setOnAction(e -> {
			root.setCenter(lommePane);
			update();
		});

		Button knapValuta = Knap.createSideButton("Valuta", 16, "/ikon_kurs.png");
		knapValuta.setStyle("-fx-padding: 5px 14px 5px 0");
		knapValuta.setOnAction(e -> {
			root.setCenter(valutaPane);
			update();
		});

		// Maks 5 kontoer
		knapTilfoj = Knap.createButton("+", "addButton");
		knapTilfoj.setOnAction(event -> {
			db.init(oversigtPane);
			update();
		});

		// Redigerer elementerne
		box.setSpacing(5);
		box.setPrefSize(SIDEPANEWIDTH, HEIGHT);
		box.getStyleClass().add("skydMig");
		box.setMaxWidth(SIDEPANEWIDTH);
		oversigtPane.getStyleClass().add("contentPanels");
		oversigtPane.setMaxWidth(400);

		// Lægger elementer på scenen.
		box.getChildren().add(
				TextLabel.createTextLabel("Konto", TextAlignment.CENTER, 24, SIDEPANEWIDTH, Color.WHITE, "kontiLabel"));
		box.getChildren().addAll(knap, knapOverfor, knapRenter, knapInfo);
		box.getChildren().add(
				TextLabel.createTextLabel(" ", TextAlignment.CENTER, 16, SIDEPANEWIDTH, Color.WHITE, "kontiLabel"));
		box.getChildren().addAll(knapAtm, knapLomme, knapValuta);

		// Oversigt
		oversigtPane.add(TextLabel.createTextLabel("Oversigt", TextAlignment.CENTER, 30), 1, 0);
		oversigtPane.add(TextLabel.createTextLabel("", 30), 0, 1); // Filler

		opsparing = Konto.create(EnumKontiType.OPSPARING, "Opsparing");
		opsparing.addBalance(5000);
		oversigtPane.add(opsparing, 1, 3);

		oversigtPane.add(TextLabel.createTextLabel("", 30, Color.BLACK), 2, 0); // filler
		oversigtPane.add(TextLabel.createTextLabel("", 30, Color.BLACK), 1, 32); // filler
		oversigtPane.add(knapTilfoj, 1, 33);

		overfor.render(overforPane);
		renter.render(renterPane);
		info.render(infoPane);
		atm.render(atmPane);
		lomme.render(lommePane);
		valuta.render(valutaPane);

		root.setLeft(box);
		root.setCenter(oversigtPane);

		Platform.runLater(() -> {
			update();
		});

		// Laver scenen og sætter den på skærmen.
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void update() {
		tilfojKnapCondition = Konto.konti.size() >= 5;
		knapTilfoj.setDisable(tilfojKnapCondition);
		Konto.update();
		overfor.update();
		atm.update();
	}
}