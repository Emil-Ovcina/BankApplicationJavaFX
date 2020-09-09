import java.util.function.UnaryOperator;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Valuta {
	private String[] choices = { "$ - USD - Amerikansk Dollar", "¥ - YEN - Japansk Yen", "€ - EURO - EU møntfod",
			"kr - DKK - Dansk krone", "£ - POUND - Britisk pund" };

	private ChoiceBox<String> cb;
	private ChoiceBox<String> cb2;

	private TextField tf;
	private TextField tf2;

	// per 1 krone.
	private double usd = 6.84;
	private double gbp = 8.83;
	private double eur = 7.44;
	private double jpy = 6.16;

	public void render(GridPane pane) {
		GridPane p = new GridPane();

		pane.add(TextLabel.createTextLabel("Valuta", TextAlignment.CENTER, 30), 1, 0);
		pane.add(TextLabel.createTextLabel("", 30), 0, 1); // Filler
		// ------------------------------------------------------------------------------------------

		cb = new ChoiceBox<>();
		cb.getItems().addAll(choices);
		cb.setMinWidth(150);
		cb.getSelectionModel().selectFirst();

		cb2 = new ChoiceBox<>();
		cb2.getItems().addAll(choices);
		cb2.setMinWidth(150);
		cb2.getSelectionModel().selectFirst();

		tf = new TextField();
		tf.setPromptText("Omregn fra");
		tf.setMinWidth(100);

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
		tf2.setPromptText("Omregnet");
		tf2.setDisable(true);

		Button b = new Button("Omregn");
		b.setOnAction(e -> {
			omregn();
		});
		tf.setOnAction(e -> {
			if (!tf.getText().trim().isEmpty())
				omregn();
		});
		b.setDisable(true);
		tf.textProperty().addListener((obs, old, newV) -> {
			b.setDisable(newV.trim().isEmpty());
		});

		p.add(TextLabel.createTextLabel("Mængde: ", 20), 0, 0);
		p.add(tf, 1, 0);
		p.add(cb, 2, 0);
		p.add(TextLabel.createTextLabel("↓", 20), 2, 1);
		p.add(TextLabel.createTextLabel("Omregn til: ", 20), 0, 2);
		p.add(cb2, 2, 2);
		p.add(TextLabel.createTextLabel("||", 20), 2, 3);
		p.add(b, 0, 4);
		p.add(tf2, 2, 4);

		// ------------------------------------------------------------------------------------------
		p.setStyle("-fx-border-color: #333; -fx-padding: 5px; -fx-background-color:white");
		p.setMinWidth(400);
		p.setMaxWidth(400);
		p.setVgap(5);
		pane.add(p, 1, 2);
		pane.add(TextLabel.createTextLabel("", 30, Color.BLACK), 2, 0); // filler
	}

	public void omregn() {
		String c1 = cb.getSelectionModel().getSelectedItem().substring(0, 1);
		String c2 = cb2.getSelectionModel().getSelectedItem().substring(0, 1);
		double amount = Double.parseDouble(tf.getText());
		double toDKK = 0;
		double omregnet = 0;

		// Først til dkk
		switch (c1) {
		case "$":
			toDKK = amount * usd;
			break;
		case "¥":
			toDKK = amount * jpy;
			break;
		case "€":
			toDKK = amount * eur;
			break;
		case "£":
			toDKK = amount * gbp;
			break;
		case "k":
			toDKK = amount;
			break;
		}

		switch (c2) {
		case "$":
			omregnet = toDKK / usd;
			break;
		case "¥":
			omregnet = toDKK / jpy;
			break;
		case "€":
			omregnet = toDKK / eur;
			break;
		case "£":
			omregnet = toDKK / gbp;
			break;
		case "k":
			omregnet = toDKK;
			break;
		}
		tf2.setText(Konto.round(omregnet, 2) + "");
	}
}
