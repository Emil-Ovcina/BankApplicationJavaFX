import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class Dialogbox {
	public void init(GridPane oversigtPane) {
		Dialog<Pair<String, String>> d = new Dialog<>();
		d.setTitle("Tilføj konti");
		d.setHeaderText("Vælg kontonavn og -type");
		d.getDialogPane().getStylesheets().add("style.css");

		ButtonType okdone = new ButtonType("Tilføj", ButtonData.OK_DONE);
		d.getDialogPane().getButtonTypes().addAll(okdone, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		List<String> choices = new ArrayList<String>();
		choices.add("Voksen");
		choices.add("Barn");
		choices.add("Opsparing");

		TextField name = new TextField();
		name.setPromptText("Navn");
		ChoiceBox<String> cb = new ChoiceBox<>();
		cb.getItems().addAll(choices);
		cb.getSelectionModel().select(0);

		Node loginButton = d.getDialogPane().lookupButton(okdone);
		loginButton.setDisable(true);

		name.textProperty().addListener((obs, old, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		grid.add(new Label("Navn: "), 0, 0);
		grid.add(name, 1, 0);
		grid.add(new Label("Kontotype: "), 0, 1);
		grid.add(cb, 1, 1);

		d.getDialogPane().setContent(grid);

		Platform.runLater(() -> {
			name.requestFocus();
		});

		d.setResultConverter((dialogButton) -> {
			if (dialogButton == okdone) {
				return new Pair<>(name.getText(), cb.getSelectionModel().getSelectedItem());
			}
			return null;
		});

		Optional<Pair<String, String>> result = d.showAndWait();

		result.ifPresent(r -> {
			if (r.getValue() == "Voksen") {
				Konto k = Konto.create(EnumKontiType.VOKSEN, r.getKey());
				oversigtPane.add(TextLabel.createTextLabel("", 11, Color.BLACK), 1, 3 + Konto.konti.indexOf(k) * 2);
				oversigtPane.add(k, 1, 4 + Konto.konti.indexOf(k) * 2);
			} else if (r.getValue() == "Barn") {
				Konto k = Konto.create(EnumKontiType.BARN, r.getKey());
				oversigtPane.add(TextLabel.createTextLabel("", 11, Color.BLACK), 1, 3 + Konto.konti.indexOf(k) * 2);
				oversigtPane.add(k, 1, 4 + Konto.konti.indexOf(k) * 2);
			} else {
				Konto k = Konto.create(EnumKontiType.OPSPARING, r.getKey());
				oversigtPane.add(TextLabel.createTextLabel("", 11, Color.BLACK), 1, 3 + Konto.konti.indexOf(k) * 2);
				oversigtPane.add(k, 1, 4 + Konto.konti.indexOf(k) * 2);
			}
		});
	}
}
