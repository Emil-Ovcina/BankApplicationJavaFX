import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TextLabel {
	public static Label createTextLabel(String text, int fontSize) {
		return createTextLabel(text, fontSize, 640);
	}

	public static Label createTextLabel(String text, int fontSize, String CSSclass) {
		return createTextLabel(text, TextAlignment.LEFT, fontSize, 640, CSSclass);
	}

	public static Label createTextLabel(String text, int fontSize, int maxWidth) {
		return createTextLabel(text, TextAlignment.LEFT, fontSize, maxWidth);
	}

	public static Label createTextLabel(String text, TextAlignment pos, int fontSize) {
		return createTextLabel(text, pos, fontSize, 640, "");

	}

	public static Label createTextLabel(String text, TextAlignment pos, int fontSize, int maxWidth) {
		return createTextLabel(text, pos, fontSize, maxWidth, "");
	}

	public static Label createTextLabel(String text, TextAlignment pos, int fontSize, int maxWidth, String CSSclass) {
		return createTextLabel(text, pos, fontSize, maxWidth, Color.BLACK, CSSclass);
	}

	public static Label createTextLabel(String text, TextAlignment pos, int fontSize, int maxWidth, Color color,
			String CSSclass) {
		Label label = new Label(text);
		label.setTextFill(Color.WHITE);
		label.setFont(new Font("Ariel", fontSize));
		label.setMaxWidth(maxWidth);
		label.setPrefWidth(maxWidth);
		label.setTextFill(color);
		label.setTextAlignment(pos);
		if (pos == TextAlignment.CENTER)
			label.setAlignment(Pos.CENTER);
		else if (pos == TextAlignment.RIGHT)
			label.setAlignment(Pos.TOP_RIGHT);
		label.getStyleClass().add(CSSclass);
		return label;
	}

	public static Label createTextLabel(String text, int fontSize, Color color) {
		Label label = createTextLabel(text, TextAlignment.LEFT, fontSize, 640);
		label.setTextFill(color);
		return label;
	}
}
