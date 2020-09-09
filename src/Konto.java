import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Konto extends GridPane {
	public static List<Konto> konti = new ArrayList<Konto>();

	private EnumKontiType type;
	private String navn;
	private double balance = 0.0;
	private boolean closed;

	public static Konto create(EnumKontiType type, String navn) {
		Konto kon = new Konto();
		kon.type = type;
		kon.navn = navn;

		kon.getStyleClass().add("kontoBox");

		konti.add(kon);
		return kon;
	}

	public static void update() {
		for (Konto kon : konti) {
			kon.getChildren().removeAll(kon.getChildren());
			if (kon.getBalance() < -5000) {
				kon.setClosed(true);
			}
			kon.add(TextLabel.createTextLabel(kon.getNavn(), 20, Color.BLACK), 0, 0);
			if (kon.isClosed())
				kon.setStyle("-fx-background-color: #bbb");
			else
				kon.setStyle("-fx-background-color: white");

			if (kon.isClosed())
				kon.add(TextLabel.createTextLabel("SPÆRRET!", TextAlignment.CENTER, 16), 4, 0);

			kon.add(TextLabel.createTextLabel("Konto type: " + kon.getType().toString(), 14, Color.BLACK), 0, 1);
			double bal = round(kon.getBalance(), 2);
			kon.add(TextLabel.createTextLabel(Double.toString(bal).replace(".", ",") + " DKK", TextAlignment.RIGHT, 14),
					4, 1);

			Button b = Knap.createSmallButton("/luk.png");
			b.setOnAction(event -> {
				kon.closed ^= true;
				if (kon.getBalance() < -5000) {
					kon.setClosed(true);
				}
				update();
			});
			kon.add(b, 4, 0);
		}
	}

	public EnumKontiType getType() {
		return type;
	}

	public String getNavn() {
		return navn;
	}

	public void addBalance(double value) {
		balance += value;
		update();
	}

	public double getBalance() {
		return balance;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean b) {
		closed = b;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
