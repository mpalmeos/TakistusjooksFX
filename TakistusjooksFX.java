package TakistusjooksFX;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Maie on 22/10/2016.
 */
public class TakistusjooksFX extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private ArrayList<Node> platforms = new ArrayList<Node>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();

    private Node player;
    private boolean canJump = true;
    private boolean running = true;

    private int levelWidth;

    private void initContent() {
        Rectangle taust = new Rectangle(600, 400);

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

        player = createEntity(60, 140, 40, 40, Color.BLUE);

        appRoot.getChildren().addAll(taust, gameRoot);
    }

    private boolean isPressed(KeyCode key) {

        return keys.getOrDefault(key, false);
    }

    private void update() {
        if(isPressed(KeyCode.SPACE) && player.getTranslateY() >= 5){
            jumpPlayer();
        }

        if(isPressed(KeyCode.W) && player.getTranslateY() >=5) {
            jumpPlayer();
        }

        if(isPressed(KeyCode.UP) && player.getTranslateY() >=5) {
            jumpPlayer();
        }
    }

    private void jumpPlayer() {
        if (canJump) {
            canJump = false;
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
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("TakistusjooksFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.setTranslateY(player.getTranslateY()+2);
                if (running) {
                    update();
                }

                running = true;
            }

        };
        timer.start();
    }
        public static void main(String[] args) {

            launch(args);
    }
}
