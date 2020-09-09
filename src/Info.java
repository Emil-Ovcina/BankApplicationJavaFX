import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Info {
	public void render(GridPane pane) {
		GridPane p = new GridPane();

		pane.add(TextLabel.createTextLabel("Information", TextAlignment.CENTER, 30), 1, 0);
		pane.add(TextLabel.createTextLabel("", 30), 0, 1); // Filler
		// ------------------------------------------------------------------------------------------

		p.add(TextLabel.createTextLabel("Kontotyper: ", 18), 0, 0);
		Text t = new Text();
		t.setFont(new Font("Arial", 14));
		t.setText(
				"\n• Voksen: Voksenkontoen kan lave overtræk men maks et overtræk på 5000 DKK, ellers bliver kontoen spærret\n\n"
						+ "• Barn: Børnekontoen kan ikke lave overtræk men ellers fungerer som en helt almindelig konto\n\n"
						+ "• Opsparing: Opsparingskontoen kan ikke lave overtræk og man kan ikke hæve fra den");
		t.setWrappingWidth(390);

		p.add(t, 0, 1);
		// ------------------------------------------------------------------------------------------
		p.setStyle("-fx-border-color: #333; -fx-padding: 5px; -fx-background-color:white");
		p.setMinWidth(400);
		p.setMaxWidth(400);
		pane.add(p, 1, 2);
		pane.add(TextLabel.createTextLabel("", 30, Color.BLACK), 2, 0); // filler
	}
}
