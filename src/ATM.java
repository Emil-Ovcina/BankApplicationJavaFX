import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ATM {
	private List<String> choices = new ArrayList<String>();

	private TextField tf;
	private PasswordField tf2;

	private TextField tf3;
	private PasswordField tf4;

	private ChoiceBox<String> cb;
	private ChoiceBox<String> cb2;

	private String PIN = "1337";
	private int forsog = 0;

	private String pass = "macbook";
	private int forsogPass = 0;

	private VBox vb;
	private Button b;
	private Button b2;

	private boolean tfv1 = true;
	private boolean tfv2 = true;
	private boolean tfv3 = true;
	private boolean tfv4 = true;

	public void render(GridPane pane) {
		GridPane p = new GridPane();
		GridPane p2 = new GridPane();
		vb = new VBox();
		ScrollPane scrollPane = new ScrollPane(vb);

		pane.add(TextLabel.createTextLabel("Hæveautomat", TextAlignment.CENTER, 30), 1, 0);
		pane.add(TextLabel.createTextLabel("", 30), 0, 1); // Filler
		// ------------------------------------------------------------------------------------------

		tf = new TextField();
		tf.setPromptText("DKK");
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
		tf.setOnAction(e -> {
			hevPenge();
		});

		tf3 = new TextField();
		tf3.setPromptText("DKK");
		tf3.setMinWidth(100);
		TextFormatter<String> textFormatter3 = new TextFormatter<>(filter);
		tf3.setTextFormatter(textFormatter3);

		cb = new ChoiceBox<>();
		cb.getItems().addAll(choices);
		cb.setMinWidth(100);
		cb.getSelectionModel().selectFirst();

		cb2 = new ChoiceBox<>();
		cb2.getItems().addAll(choices);
		cb2.setMinWidth(100);
		cb2.getSelectionModel().selectFirst();

		tf2 = new PasswordField();
		tf2.setPromptText("PIN-KODE");
		UnaryOperator<Change> filter2 = change -> {
			String text = change.getText();
			if (text.matches("[0-9]*")) {
				return change;
			}
			return null;
		};
		TextFormatter<String> textFormatter2 = new TextFormatter<>(filter2);
		tf2.setTextFormatter(textFormatter2);
		tf2.textProperty().addListener((obs, old, newV) -> {
			if (tf2.getText().length() > 4) {
				String s = tf2.getText().substring(0, 4);
				tf2.setText(s);
			}
		});
		tf2.setOnAction(e -> {
			hevPenge();
		});

		tf4 = new PasswordField();
		tf4.setPromptText("Adgangskode");
		tf4.setOnAction(e -> {
			indsetPenge();
		});

		b = new Button("Hæv");
		b.setMinWidth(100);
		b.setOnAction(e -> {
			hevPenge();
		});

		b2 = new Button("Indsæt");
		b2.setMinWidth(100);
		b2.setOnAction(e -> {
			indsetPenge();
		});

		b.setDisable(true);
		b2.setDisable(true);

		tf.textProperty().addListener((ovs, old, newV) -> {
			tfv1 = newV.trim().isEmpty();
			check1();
		});
		tf2.textProperty().addListener((ovs, old, newV) -> {
			tfv2 = newV.trim().isEmpty();
			check1();
		});

		tf3.textProperty().addListener((ovs, old, newV) -> {
			tfv3 = newV.trim().isEmpty();
			check2();
		});

		tf4.textProperty().addListener((ovs, old, newV) -> {
			tfv4 = newV.trim().isEmpty();
			check2();
		});

		p.add(TextLabel.createTextLabel("Hæv: ", 20), 0, 0);
		p.add(tf, 1, 0);
		p.add(TextLabel.createTextLabel(" Fra: ", 20), 2, 0);
		p.add(cb, 3, 0);
		p.add(TextLabel.createTextLabel("", 16), 0, 1);
		p.add(TextLabel.createTextLabel("PIN: ", 20), 0, 2);
		p.add(tf2, 1, 2);
		p.add(b, 3, 2);

		p2.add(TextLabel.createTextLabel("Indsæt: ", 20), 0, 0);
		p2.add(tf3, 1, 0);
		p2.add(TextLabel.createTextLabel(" På: ", 20), 2, 0);
		p2.add(cb2, 3, 0);
		p2.add(TextLabel.createTextLabel("", 16), 0, 1);
		p2.add(TextLabel.createTextLabel("Kode: ", 20), 0, 2);
		p2.add(tf4, 1, 2);
		p2.add(b2, 3, 2);

		// ------------------------------------------------------------------------------------------
		vb.setStyle("-fx-background-color:white");
		p.setStyle("-fx-border-color: #333; -fx-padding: 5px; -fx-background-color:white");
		p.setMinWidth(400);
		p.setMaxWidth(400);
		p2.setStyle("-fx-border-color: #333; -fx-padding: 5px; -fx-background-color:white");
		p2.setMinWidth(400);
		p2.setMaxWidth(400);
		scrollPane.setStyle("-fx-border-color: #333; -fx-padding: 5px; -fx-background-color:white;");
		scrollPane.setMinWidth(400);
		scrollPane.setMaxWidth(400);
		scrollPane.setMinHeight(160);
		scrollPane.setMaxHeight(160);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		pane.add(p, 1, 2);
		pane.add(TextLabel.createTextLabel("", 16), 0, 3); // filler
		pane.add(p2, 1, 4);
		pane.add(TextLabel.createTextLabel("", 16), 0, 5); // Filler
		pane.add(scrollPane, 1, 6);
		pane.add(TextLabel.createTextLabel("", 30, Color.BLACK), 2, 0); // filler
	}

	public void update() {
		for (Konto kon : Konto.konti) {

			choices.remove(kon.getNavn());
			cb.getItems().removeAll(kon.getNavn());
			cb2.getItems().removeAll(kon.getNavn());

			if (!choices.contains(kon.getNavn()) && !kon.isClosed() && kon.getType() != EnumKontiType.OPSPARING) {
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

	public void hevPenge() {
		if (!tf.getText().isEmpty() && tf2.getText().equals(PIN)) {
			double amount = Double.parseDouble(tf.getText().replace(",", "."));

			Konto k = null;
			for (Konto x : Konto.konti) {
				if (x.getNavn().equals(cb.getValue()))
					k = x;
			}

			k.addBalance(-amount);
			String time = "" + ZonedDateTime.now().toString().substring(11, 19);
			vb.getChildren().add(0, TextLabel
					.createTextLabel(time + " - " + amount + " DKK blev trukket fra " + k.getNavn() + ".", 12));
			tf.setText("");
		} else {
			Alert alert;
			Konto k = null;
			for (Konto x : Konto.konti) {
				if (x.getNavn().equals(cb.getValue()))
					k = x;
			}

			alert = new Alert(AlertType.ERROR);
			alert.setContentText("PIN Koden var forkert");
			alert.showAndWait();

			forsog++;
			if (forsog >= 4)
				k.setClosed(true);
		}
		tf2.setText("");
	}

	public void indsetPenge() {
		if (!tf3.getText().isEmpty() && tf4.getText().equals(pass)) {
			double amount = Double.parseDouble(tf3.getText().replace(",", "."));

			Konto k = null;
			for (Konto x : Konto.konti) {
				if (x.getNavn().equals(cb.getValue()))
					k = x;
			}

			k.addBalance(amount);
			String time = "" + ZonedDateTime.now().toString().substring(11, 19);
			vb.getChildren().add(0,
					TextLabel.createTextLabel(time + " - " + amount + " DKK blev indsat på " + k.getNavn() + ".", 12));
			tf3.setText("");
		} else {
			Alert alert;

			alert = new Alert(AlertType.ERROR);
			alert.setContentText("Adgangskoden er forkert");
			alert.showAndWait();

			forsogPass++;
			if (forsogPass >= 4)
				System.exit(0);
		}
		tf4.setText("");
	}

	public void check1() {
		b.setDisable(tfv1 | tfv2);
	}

	public void check2() {
		b2.setDisable(tfv3 | tfv4);
	}
}
