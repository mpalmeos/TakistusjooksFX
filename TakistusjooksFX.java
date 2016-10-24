package TakistusjooksFX;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Maie on 22/10/2016.
 */
/**public class TakistusjooksFX extends Application {

    private ArrayList<Node> platforms = new ArrayList<Node>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Node player;
    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean canJump = true;

    private int levelWidth;

    private void initContent() {
        Rectangle taust = new Rectangle(800, 600);

        levelWidth = LevelData.LVL1[0].length() * 60;

        for (int i = 0; i < LevelData.LVL1.length; i++) {
            String line = LevelData.LVL1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Node platform = createEntity(j*60, i*60, 60, 60, Color.WHITESMOKE);
                        platforms.add(platform);
                        break;
                }
            }
        }

        player = createEntity(60, 180, 40, 40, Color.BLUE);

        appRoot.getChildren().addAll(taust, gameRoot, uiRoot);
    }

    private void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -30);
            canJump = false;
        }
    }

    private void update() {
        if (playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0, 1);
        }
    }

    private Node createEntity(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        entity.getProperties().put("alive", true);

        gameRoot.getChildren().add(entity);
        return entity;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        initContent();

        Scene scene = new Scene(appRoot);
        primaryStage.setTitle("TakistusjooksFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }
        public static void main(String[] args) {
        launch(args);
    }
}*/
