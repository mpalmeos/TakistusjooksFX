package TakistusjooksFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Maie on 22/10/2016.
 */
/**public class FXGame extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox aken1 = new VBox();
        Scene stseen1 = new Scene(aken1, 400, 100);

        primaryStage.setScene(stseen1);
        primaryStage.setTitle("AMAZING!");
        primaryStage.show();

        Label esimene = new Label("Are you not entertained?");
        Button yes1 = new Button("Yes");
        Button no1 = new Button("No");

        aken1.getChildren().addAll(esimene, yes1, no1);

        StackPane aken2 = new StackPane();
        aken2.setStyle("-fx-background-color:GREEN");
        Scene stseen2 = new Scene(aken2, 100, 100);
        Text text1 = new Text(25, 25, "GOOD!");
        text1.setFill(Color.BLUE);

        aken2.getChildren().addAll(text1);

        StackPane aken3 = new StackPane();
        aken3.setStyle("-fx-background-color:RED");
        Scene stseen3 = new Scene(aken3, 100, 100);
        Text text2 = new Text(25, 25, "Too Bad!");
        text2.setFill(Color.DARKRED);
        aken3.getChildren().addAll(text2);

        yes1.setOnAction(event -> {
            primaryStage.setScene(stseen2);
        });

        no1.setOnAction(event -> {
            primaryStage.setScene(stseen3);
        });

    }
}*/
