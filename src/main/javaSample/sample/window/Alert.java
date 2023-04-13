package javaSample.sample.window;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {

    public static void display(String popUpWindowText, String labelText, String buttonText) {
        Stage alert = new Stage();

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(popUpWindowText);

        Label label1 = new Label(labelText);
        Button button1 = new Button(buttonText);
        button1.setOnAction(e -> alert.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 400, 100);
        alert.setScene(scene1);
        alert.showAndWait();
    }
}
