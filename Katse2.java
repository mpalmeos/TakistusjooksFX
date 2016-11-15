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

/**
 * Created by Maie on 29/10/2016.
 */
public class Katse2 extends Application {

    private ArrayList platvormid = new ArrayList();     // ArrayList, mis hoiab kõik mängulaua elemendid, v.a mängija
    private HashMap klaviatuur = new HashMap();         // HashMap, mille abil saab hiljem lugeda, missugust nuppu vajutati
    private Rectangle mangija;                          // Mängija kuubik

    private Pane appRoot = new Pane();                  // appRoot'is toimub kogu liikumine
    private Pane gameRoot = new Pane();                 //gameRootis' on kõik mängu elemendid (mängija+level)

    private int manguLaius;                             //Teeb kindlaks ühe leveli laiuse

    private boolean hypeVoimalik;

    public double grav;
    public double mangijaY;
    public Rectangle taust = new Rectangle(600, 400);

    private Rectangle tekitaRuut(int x, int y, int w, int h, Color color) {      //Programmisisene meetod elementide loomiseks (mis kõik on ruudud)
        Rectangle ruut = new Rectangle(w, h);
        ruut.setTranslateX(x);                              //Seab paika koordinaadid tekkinud ruudule
        ruut.setTranslateY(y);
        ruut.setFill(color);

        gameRoot.getChildren().addAll(ruut);                //Tekkinud ruudud pannakse gameRoot'i ehk Pane'i, kus on kõik elemendid
        return ruut;
    };

    private void manguSisu() {          //Meetod, mis genereerib terve leveli ette antud andmetest (LevelData).
        //Rectangle taust = new Rectangle(600, 400); //Uus taust (hetkel must ristkülik)

        manguLaius = LevelData.LVL1[0].length()*50; //Mängu laiuseks võta LevelData's oleva stringi ühe "sõna" pikkuse ja korruta 50-ga
                                                    //(50 on ühe individuaalse ruudu suurus ehk 50x50)
        for (int i = 0; i < LevelData.LVL1.length; i++) { //Käi läbi iga veerg
            String rida = LevelData.LVL1[i];              //Salvesta iga veeru andmed
            for (int j = 0; j < rida.length(); j++) {     //Käi läbi iga rida
                switch (rida.charAt(j)) {                 //Vastavalt real olevale märgile:
                    case '0':                             //Kui on "0", siis ära tee midagi (jääb tühjaks)
                        break;
                    case'1':                              //Kui on "1", siis tekita sinine ruut (platvorm)
                        Rectangle platvorm = tekitaRuut(j*50, i*50, 50, 50, Color.BLUE);
                        platvormid.add(platvorm);         //Lisa tekkinud platvorm ArrayListi platvormid, kusjuures selle
                        break;                            //asukohaks märgi vastavad i ja j koordinaadid
                }
            }
        }
        Rectangle mangija = tekitaRuut(30, 30, 30, 30, Color.WHITE);  //Tekita mängija (ruut) ja säti ta teatud koordinaatidele)

        appRoot.getChildren().addAll(taust,gameRoot);     //Pane kogu krempel kõige peamisele Pane'ile ehk sellele, kus toimub liikumine
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        manguSisu();                            //Genereeri mängu sisu ehk lae level.

        Scene esimene = new Scene(appRoot);     //Akna tegemine ja appRoot näitamine
        primaryStage.setTitle("Takistusjooks");
        primaryStage.setScene(esimene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() { //Kogu liikumine, mis mängus toimub.
            @Override
            public void handle(long now) {
                
            }

        };timer.start();

        {
            esimene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.SPACE){
                    System.out.println("KLIKK");
                    grav = -10;
                } else if (event.getCode() == KeyCode.UP){
                    grav = -10;
                }
            });
        }
    }
}
