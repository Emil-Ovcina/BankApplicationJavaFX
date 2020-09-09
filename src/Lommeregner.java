import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Lommeregner {
	private TextField tf;

	private String tekst = "";
	private double tal = 0;
	private double temp = 0;
	private boolean eq = false;
	private boolean plu;
	private boolean min;
	private boolean mul;
	private boolean div;

	private double calculated = 0;

	public void render(GridPane pane) {
		GridPane p = new GridPane();

		pane.add(TextLabel.createTextLabel("Regner", TextAlignment.CENTER, 30), 1, 0);
		pane.add(TextLabel.createTextLabel("", 30), 0, 1); // Filler
		// ------------------------------------------------------------------------------------------

		tf = new TextField();
		tf.setDisable(true);
		tf.setText("0");
		tf.setAlignment(Pos.CENTER_RIGHT);

		Button tal7 = createButton("7", "lommeRegnerTal", 7);
		Button tal8 = createButton("8", "lommeRegnerTal", 8);
		Button tal9 = createButton("9", "lommeRegnerTal", 9);
		Button tal4 = createButton("4", "lommeRegnerTal", 4);
		Button tal5 = createButton("5", "lommeRegnerTal", 5);
		Button tal6 = createButton("6", "lommeRegnerTal", 6);
		Button tal1 = createButton("1", "lommeRegnerTal", 1);
		Button tal2 = createButton("2", "lommeRegnerTal", 2);
		Button tal3 = createButton("3", "lommeRegnerTal", 3);
		Button tal0 = createButton("0", "lommeRegnerTal", 0);

		Button funkPlus = Knap.createButton("+", "lommeRegnerOp");
		funkPlus.setOnAction(e -> {
			plus();
		});

		Button funkMinus = Knap.createButton("–", "lommeRegnerOp");
		funkMinus.setOnAction(e -> {
			minus();
		});

		Button funkGange = Knap.createButton("×", "lommeRegnerOp");
		funkGange.setOnAction(e -> {
			multiplicate();
		});

		Button funkDel = Knap.createButton("÷", "lommeRegnerOp");
		funkDel.setOnAction(e -> {
			divide();
		});

		Button funkEq = Knap.createButton("=", "lommeRegnerOp");
		funkEq.setOnAction(e -> {
			equals();
		});

		Button funkPoint = Knap.createButton(" ,", "lommeRegnerFunk");
		funkPoint.setOnAction(e -> {
			addPoint();
		});
		Button funkplusminus = Knap.createButton("±", "lommeRegnerFunk");
		funkplusminus.setOnAction(e -> {
			change();
		});
		Button slet = Knap.createButton("C", "lommeRegnerFunk");
		slet.setOnAction(e -> {
			fjern();
		});
		Button talPi = Knap.createButton("π", "lommeRegnerFunk");
		talPi.setOnAction(e -> {
			pi();
		});
		Button talE = Knap.createButton("e", "lommeRegnerFunk");
		talE.setOnAction(e -> {
			e();
		});

		p.add(tf, 0, 0, 5, 1);

		p.add(slet, 0, 1);
		p.add(talPi, 1, 1);
		p.add(talE, 2, 1);
		p.add(funkDel, 4, 1);

		p.add(tal7, 0, 2);
		p.add(tal8, 1, 2);
		p.add(tal9, 2, 2);
		p.add(funkPlus, 4, 2);

		p.add(tal4, 0, 3);
		p.add(tal5, 1, 3);
		p.add(tal6, 2, 3);
		p.add(funkMinus, 4, 3);

		p.add(tal1, 0, 4);
		p.add(tal2, 1, 4);
		p.add(tal3, 2, 4);
		p.add(funkGange, 4, 4);

		p.add(tal0, 0, 5);
		p.add(funkplusminus, 1, 5);
		p.add(funkPoint, 2, 5);
		p.add(funkEq, 4, 5);

		tf.textProperty().addListener((obs, old, newV) -> {
			if (tf.getText().length() > 8) {
				String s = tf.getText().substring(0, 8);
				tf.setText(s);
			}
		});

		// ------------------------------------------------------------------------------------------
		p.setVgap(5);
		p.setHgap(5);
		p.setStyle("-fx-border-color: #333; -fx-padding: 5px; -fx-background-color:white");

		p.setMaxWidth(400);
		pane.add(p, 1, 2);
		pane.add(TextLabel.createTextLabel("", 30, Color.BLACK), 2, 0); // filler
	}

	public void fjern() {
		slet();
		calculated = 0;
		tekst = "";
		tf.setText("0");
	}

	public void slet() {
		plu = false;
		min = false;
		mul = false;
		div = false;
		eq = false;
		tal = 0;
		temp = 0;
		tekst = "";
	}

	public void plus() {
		calculated = temp + tal;
		temp = tal;
		tal = 0;
		tekst = "";
		tf.setText("" + calculated);
		plu = true;
		up();
	}

	public void minus() {
		if (temp == 0)
			calculated = tal - temp;
		else
			calculated = temp - tal;
		temp = tal;
		tal = 0;
		tekst = "";
		tf.setText("" + calculated);
		min = true;
		up();
	}

	public void multiplicate() {
		if (temp == 0)
			calculated = tal;
		else
			calculated = temp * tal;
		temp = tal;
		tal = 0;
		tekst = "";
		tf.setText("" + calculated);
		mul = true;
		up();
	}

	public void divide() {
		if (tal != 0) {
			if (temp == 0)
				calculated = tal;
			else
				calculated = temp / tal;
			temp = tal;
			tal = 0;
			tekst = "";
			tf.setText("" + calculated);
			div = true;
		} else {
			// Hvis man deler med 0 (burde være dødstraf)
			return;
		}
		up();
	}

	public void pi() {
		tal = Math.PI;
		tekst = Double.toString(Math.PI);
		update();
	}

	public void e() {
		tal = Math.E;
		tekst = Double.toString(Math.E);
		update();
	}

	public void change() {
		tal *= -1;
		tekst = Double.toString(tal);
		up();
		update();
	}

	public void addPoint() {
		if (tf.getText().equals("0")) {
			tekst = "0.";
			update();
			return;
		}
		tekst += '.';
		up();
		update();
	}

	public void equals() {
		if (plu) {
			calculated = (temp + tal);
			tekst = "" + (calculated);
		}
		if (min) {
			calculated = (temp - tal);
			tekst = "" + (calculated);
		}
		if (mul) {
			calculated = (temp * tal);
			tekst = "" + (calculated);
		}
		if (div) {
			calculated = (temp / tal);
			tekst = "" + (calculated);
		}
		eq = true;
		up();
		update();
	}

	public void up() {
		if (tf.getText().trim().endsWith(".0"))
			tf.setText(tf.getText().substring(0, tf.getText().length() - 2));
	}

	public void update() {
		if (eq) {
			slet();
			tf.setText("" + calculated);
			return;
		}
		tf.setText("" + tekst);
		if (tekst.length() <= 8)
			tal = Double.parseDouble(tekst);
	}

	public Button createButton(String text, String cssClass, int tal) {
		Button b = new Button(text);
		b.getStyleClass().add(cssClass);
		b.setFont(new Font("Arial", 20));
		b.setOnAction(e -> {
			if (tal == 0 && tf.getText().trim().equals("0")) {
				return;
			}
			tekst += Integer.toString(tal);
			update();
		});
		return b;
	}
}