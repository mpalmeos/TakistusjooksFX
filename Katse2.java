package TakistusjooksFX;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Created by Maie on 29/10/2016.
 */
public class Katse2 extends Application {

    private ArrayList <Rectangle> platvormid = new ArrayList();     // ArrayList, mis hoiab kõik mängulaua elemendid, v.a mängija

    private Pane appRoot = new Pane();                  // appRoot'is toimub kogu liikumine
    private Pane gameRoot = new Pane();                 //gameRootis' on kõik mängu elemendid (mängija+level)

    private int manguLaius;                             //Teeb kindlaks ühe leveli laiuse

    private boolean hypeVoimalik;                       //topelthüpe?

    public double grav;                                 //Gravitatsioon ehk mängija kukub alla.
    public double mangijaY;                             //Mängija Y koordinaat ehk üles-alla liikumine
    public Rectangle taust = new Rectangle(600, 400);   //Taust ehk must ruut

    private Rectangle tekitaRuut(int x, int y, int w, int h, Color color) {      //Programmisisene meetod elementide loomiseks (kõik on ruudud)
        Rectangle ruut = new Rectangle(w, h);
        ruut.setTranslateX(x);                              //Seab paika koordinaadid tekkinud ruudule
        ruut.setTranslateY(y);
        ruut.setFill(color);

        gameRoot.getChildren().addAll(ruut);                //Tekkinud ruudud pannakse gameRoot'i ehk Pane'i, kus on kõik elemendid
        return ruut;
    };

    private void manguSisu() {          //Meetod, mis genereerib terve leveli ette antud andmetest (LevelData).

        manguLaius = LevelData.LVL1[0].length(); //Mängu laiuseks võta LevelData's oleva stringi ühe "sõna" pikkuse

        for (int i = 0; i < LevelData.LVL1.length; i++) { //Käi läbi iga rida
            String rida = LevelData.LVL1[i];              //Muuda iga "sõna" sees olev täht eraldi stringiks (muidu asi ei tööta)
            for (int j = 0; j < rida.length(); j++) {     //Käi läbi iga loodud string
                switch (rida.charAt(j)) {
                    case '0':                             //Kui on "0", siis ära tee midagi.
                        break;
                    case '1':                             //Kui on "1", siis tekita ruut meetodi tekitaRuut abil
                        Rectangle platvorm = tekitaRuut(j * 50, i * 50, 50, 50, Color.BLUE);
                        platvormid.add(platvorm);         //Lisa tekkinud platvorm ArrayListi platvormid
                        break;
                }
            }
        }
        appRoot.getChildren().addAll(taust,gameRoot);     //Pane kogu krempel kõige peamisele Pane'ile ehk sellele, kus toimub liikumine
    }

    Rectangle mangija = tekitaRuut(30, 30, 30, 30, Color.WHITE);    //Tekita mängija ruut

    private void liigubAlla() {                             //Gravity detection meetod ehk et mängija istub teiste ruutude peal
        for (Rectangle platvorm : platvormid) {             //Käi läbi kõik platvormid ArrayListis "platvormid".
            if (mangija.getBoundsInParent().intersects(platvorm.getBoundsInParent())) { //Kui mängija ruut läheb vastu platvorme ...
                grav = -0.55;                                //... vähenda gravitatsiooni ehk liigu vähem alla.
                    return;
                }
            }
        }

    @Override
    public void start(Stage primaryStage) throws Exception {
        manguSisu();                            //Genereeri mängu sisu ehk lae level.

        Scene esimene = new Scene(appRoot);     //Akna tegemine ja appRoot näitamine (esimene stseen - tulevikus teised ka?)
        primaryStage.setTitle("Takistusjooks");
        primaryStage.setScene(esimene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() { //Kogu liikumine, mis mängus toimub.
            @Override
            public void handle(long now) {
                mangijaY = mangija.getY();          //Gravitatsiooni tekitamine
                grav = grav +0.3;
                mangija.setY(mangijaY + grav);
                liigubAlla();
                double mangijaX = mangija.getX();
                mangija.setX(mangijaX + 3);
            }

        };timer.start();

        {
            esimene.setOnKeyPressed(event -> {          //Hüppamine
                if (event.getCode() == KeyCode.SPACE){
                    grav = -7;
                } else if (event.getCode() == KeyCode.UP){
                    grav = -7;
                }
            });
        }
    }
}
