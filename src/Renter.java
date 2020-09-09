import java.util.function.UnaryOperator;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Renter {
	private double rentePerAr = 0.05; // 5% om året i decimaltal
	private double rente;

	private TextField tf;
	private TextField tf2;
	private TextField tf3;

	private boolean tfV;
	private boolean tfV2;
	private Button b;

	public void render(GridPane pane) {
		GridPane p = new GridPane();

		pane.add(TextLabel.createTextLabel("Renter", TextAlignment.CENTER, 30), 1, 0);
		pane.add(TextLabel.createTextLabel("", 30), 0, 1); // Filler
		// ------------------------------------------------------------------------------------------

		tf = new TextField();
		tf.setPromptText("DKK");
		tf.setMinWidth(120);
		UnaryOperator<Change> filter = change -> {
			String text = change.getText();
			if (text.matches("[0-9]*") || text.matches(",")) {
				return change;
			}
			return null;
		};
		TextFormatter<String> textFormatter = new TextFormatter<>(filter);
		tf.setTextFormatter(textFormatter);

		tf2 = new TextField();
		tf2.setPromptText("År");
		tf2.setMinWidth(120);
		TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
		tf2.setTextFormatter(textFormatter2);
		tf2.setOnAction(e -> {
			udregn();
		});

		b = new Button("Udregn");
		b.setOnAction(e -> {
			udregn();
		});
		b.setDisable(true);

		tf.textProperty().addListener((obs, old, newValue) -> {
			tfV = newValue.trim().isEmpty();
			check();
		});

		tf2.textProperty().addListener((obs, old, newVal) -> {
			tfV2 = newVal.trim().isEmpty();
			check();
		});

		tf3 = new TextField();
		tf3.setDisable(true);
		tf3.setPromptText("Udregnet rente");

		p.add(TextLabel.createTextLabel("Rentesats: ", 20), 0, 0);
		p.add(TextLabel.createTextLabel((rentePerAr * 100) + "% pr. ano", TextAlignment.RIGHT, 20), 2, 0);
		p.add(TextLabel.createTextLabel("", 16), 0, 1); // Filler
		p.add(TextLabel.createTextLabel("Mængde: ", 20), 0, 2);
		p.add(tf, 2, 2);
		p.add(TextLabel.createTextLabel("", 16), 0, 3); // Filler
		p.add(TextLabel.createTextLabel("Antal år: ", 20), 0, 4);
		p.add(tf2, 2, 4);
		p.add(TextLabel.createTextLabel("", 16), 0, 5); // Filler
		p.add(b, 0, 6);
		p.add(tf3, 2, 6);

		// ------------------------------------------------------------------------------------------
		p.setStyle("-fx-border-color: #333; -fx-padding: 5px; -fx-background-color: white");
		p.setMinWidth(400);
		p.setMaxWidth(400);
		pane.add(p, 1, 2);
		pane.add(TextLabel.createTextLabel("", 30, Color.BLACK), 2, 0); // filler
	}

	public void udregn() {
		double amount = Double.parseDouble(tf.getText());
		double year = Double.parseDouble(tf2.getText());

		for (int i = 0; i < Math.floor(year); i++) {
			amount *= (1 + rentePerAr);
		}

		rente = Konto.round(amount, 2);
		tf3.setText(rente + " DKK");
	}

	public void check() {
		b.setDisable(tfV | tfV2);
	}
}
