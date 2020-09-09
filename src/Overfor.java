import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Overfor {
	private List<String> choices = new ArrayList<String>();

	private ChoiceBox<String> cb;
	private ChoiceBox<String> cb2;

	private TextField tf;
	private VBox history;

	public void render(GridPane pane) {
		GridPane hb = new GridPane();
		history = new VBox();
		pane.add(TextLabel.createTextLabel("Overfør", TextAlignment.CENTER, 30), 1, 0);
		pane.add(TextLabel.createTextLabel("", 30), 0, 1); // Filler
		// Elementer bliver lagt ind her
		// -------------------------------------------------------------------

		cb = new ChoiceBox<>();
		cb.getItems().addAll(choices);
		cb.setMinWidth(80);
		cb.getSelectionModel().selectFirst();

		cb2 = new ChoiceBox<>();
		cb2.getItems().addAll(choices);
		cb2.setMinWidth(80);
		cb2.getSelectionModel().selectFirst();

		tf = new TextField();
		tf.setPromptText("DKK");
		UnaryOperator<Change> filter = change -> {
			String text = change.getText();
			if (text.matches("[0-9]*") || text.matches(",")) {
				return change;
			}
			return null;
		};
		Button done = new Button("Overfør");

		TextFormatter<String> textFormatter = new TextFormatter<>(filter);
		tf.setTextFormatter(textFormatter);
		tf.setOnAction(e -> {
			if (!done.isDisable())
				overfor();
		});

		done.setDisable(true);
		tf.textProperty().addListener((obs, old, newValue) -> {
			done.setDisable(newValue.trim().isEmpty());
		});

		done.setOnAction(event -> {
			overfor();
		});

		hb.add(TextLabel.createTextLabel("Overfør fra: ", 20), 0, 0);
		hb.add(cb, 1, 0);
		hb.add(TextLabel.createTextLabel("  til: ", TextAlignment.CENTER, 20, 600), 2, 0);
		hb.add(cb2, 3, 0);
		hb.add(TextLabel.createTextLabel("", 20), 0, 1);
		hb.add(TextLabel.createTextLabel("Mængde: ", 20), 0, 2);
		hb.add(tf, 1, 2);
		hb.add(done, 3, 2);

		// ------------------------------------------------------------------------------------------------
		hb.setMinWidth(460);
		hb.setMaxWidth(460);
		hb.setStyle("-fx-border-color: #333; -fx-padding: 5px; -fx-background-color: white");
		history.setStyle("-fx-border-color: #333; -fx-padding: 5px; -fx-background-color: white");

		ScrollPane scrollPane = new ScrollPane(history);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setMinHeight(242);
		scrollPane.setMaxHeight(242);

		hb.setMaxWidth(400);
		hb.setMinWidth(400);
		scrollPane.setMaxWidth(400);
		scrollPane.setMinWidth(400);

		pane.add(hb, 1, 2);
		pane.add(TextLabel.createTextLabel("", 30), 1, 3);
		pane.add(scrollPane, 1, 4);
		pane.add(TextLabel.createTextLabel("", 30, Color.BLACK), 2, 0); // filler
	}

	public void update() {
		for (Konto kon : Konto.konti) {

			choices.remove(kon.getNavn());
			cb.getItems().removeAll(kon.getNavn());
			cb2.getItems().removeAll(kon.getNavn());

			if (!choices.contains(kon.getNavn()) && !kon.isClosed()) {
				choices.add(kon.getNavn());
				if (!cb.getItems().contains(kon.getNavn())) {
					cb.getItems().add(kon.getNavn());
					cb2.getItems().add(kon.getNavn());

					if (kon.isClosed()) {
						choices.remove(kon.getNavn());
					}
				}
			}
		}

		cb.getSelectionModel().selectFirst();
		cb2.getSelectionModel().selectFirst();
	}

	public void overfor() {
		double amount = Double.parseDouble(tf.getText().replace(",", "."));

		Konto k = null;
		for (Konto x : Konto.konti) {
			if (x.getNavn().equals(cb.getValue()))
				k = x;
		}

		Konto k2 = null;
		for (Konto x : Konto.konti) {
			if (x.getNavn().equals(cb2.getValue()))
				k2 = x;
		}

		if (k == null || k2 == null) {
			System.out.println("Fejl i valg af konti");
			return; // Do something in case of error...
		}

		if (amount >= 0) {

		}
		// Hvis det ikke er BARN eller OPSPARING
		if (k.getType() == EnumKontiType.VOKSEN) {
			k.addBalance(-amount);
			k2.addBalance(amount);
		} else if (amount < k.getBalance()) // Må ikke gå i minus
		{
			k.addBalance(-amount);
			k2.addBalance(amount);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Du kan ikke lave overtræk på en " + k.getType() + " konto.");
			alert.setContentText("Sørg for at du ikke trækker for meget fra din konto");
			alert.showAndWait();
			return;
		}
		tf.setText("");
		if (k.getNavn() != k2.getNavn()) {
			String time = "" + ZonedDateTime.now().toString().substring(11, 19);
			String am = "" + amount;
			String msg = time + " - " + "Overført " + am.replace(".", ",") + " DKK fra \"" + k.getNavn() + "\" til \""
					+ k2.getNavn() + "\".";
			history.getChildren().add(0, TextLabel.createTextLabel(msg, 12));
		}
	}
}
