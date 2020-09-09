import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class Knap extends Button {
	public static Button createButton(String text) {
		Button b = new Button(text);
		return b;
	}

	public static Button createButton(String text, String cssClass) {
		Button b = new Button(text);
		b.getStyleClass().add(cssClass);
		b.setFont(new Font("Arial", 20));
		return b;
	}

	public static Button createSmallButton(String image) {
		ImageView img = new ImageView(image);
		img.setFitWidth(16);
		img.setFitHeight(16);
		Button b = new Button("", img);
		b.setMaxHeight(8);
		b.setMaxWidth(8);
		b.setStyle("-fx-background-color: white; -fx-border-color: #333; -fx-padding:0");
		b.setAlignment(Pos.TOP_RIGHT);
		return b;
	}

	public static Button createSideButton(String text, String image) {
		return createSideButton(text, 16, image);
	}

	public static Button createSideButton(String text, int size, String image) {
		ImageView img = new ImageView(image);
		img.setFitWidth(20);
		img.setFitHeight(20);
		Button b = new Button(text, img);
		b.getStyleClass().add("sidePanelBtn");
		b.setFont(new Font("Arial", size));
		b.setMaxWidth(100);
		b.setMaxHeight(20);
		return b;
	}
}
