package TakistusjooksFX;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Created by Maie on 29/10/2016.
 */
public class Katse2 extends Application {

    public ArrayList<Rectangle> platvormid = new ArrayList();       // ArrayList, mis hoiab kõik mängulaua elemendid, v.a mängija
    public Pane appRoot = new Pane();                               // appRoot'is toimub kogu liikumine
    public Pane gameRoot = new Pane();                              // gameRootis' on kõik mängu elemendid (mängija+level)
    public int manguLaius;                                          // Teeb kindlaks ühe leveli laiuse
    public int maxLaius;
    public String rida;
    public double grav;                                            // Gravitatsioonikonstant
    public boolean jumping = false;                                 // Topelthüpe keelatud ehk mängija hüppab korra ja järgmine hüpe on lubatud pärast esimese hüppe lõppu

    public Rectangle taust = new Rectangle(600, 400);   // Taust ehk must ruut

    private Rectangle tekitaRuut(int x, int y, int w, int h, Color color) {      //Programmisisene meetod elementide loomiseks (kõik ruudud)
        Rectangle ruut = new Rectangle(w, h);
        ruut.setTranslateX(x);                              //Seab paika koordinaadid tekkinud ruudule
        ruut.setTranslateY(y);
        ruut.setFill(color);                                //Annab ruudule värvi

        gameRoot.getChildren().addAll(ruut);                //Tekkinud ruudud pannakse gameRoot'i ehk Pane'i, kus on kõik elemendid
        return ruut;
    }

    public void manguSisu() {          //Meetod, mis genereerib terve leveli ette antud andmetest (LevelData) (Almas Baimagambetov:
                                        // https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/tutorial14/Main.java)
            manguLaius = LevelData.LVL1[0].length(); //Mängu laiuseks võta LevelData's oleva stringi ühe "sõna" pikkuse
            maxLaius = LevelData.LVL1.length;

        for (int i = 0; i < maxLaius; i++) { //Käi läbi iga rida
                rida = LevelData.LVL1[i];              //Muuda iga "sõna" sees olev täht eraldi stringiks (muidu asi ei tööta)
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
        appRoot.getChildren().addAll(taust, gameRoot);     //Pane kogu krempel kõige peamisele Pane'ile ehk sellele, kus toimub liikumine (appRoot)
    }

    Rectangle mangija = tekitaRuut(100, 100, 30, 30, Color.WHITE);    //Tekita mängija ruut

    private void taustLiigub() {               //Meetod, mis paneb tausta vasakult paremale liikuma (liigub gameRoot)
        double scrollSpeed = -3;              //Liikumise kiirus
        double taustParemale = gameRoot.getLayoutX() + scrollSpeed;  //Võta gameRoot X asukoha ja lisa juurde liikumine
        gameRoot.setLayoutX(taustParemale);    //Sea uueks gameRoot X-telje asukohaks tekkinud arv
    }

    public void gravity(boolean isHitting) {             //Mängija kukub pidevalt allapoole (sõbra abi - Jason Parks)
        if (isHitting && grav == 0) {
            return;
        }
        double mangijaY = mangija.getTranslateY();  //Leia mängija asukoht Y-teljel
        grav = grav + 0.1;                  //Gravity ehk mida kõrgemalt kukub, seda kõrgem väärtus
        double uusMangijaY = mangijaY + grav;  //Leia mängija asukoht Y-teljel koos gravitatsiooniga
        mangija.setTranslateY(uusMangijaY);     //Sea mängija uueks Y-koordinaadiks leitud asukoht
    }

    public void playerMove(boolean isHitting) {         //Mängija liigutamine (sõbra abi - Jason Parks)
        if (!isHitting) {                               //Kui kokkupõrget pole, siis liiguta mängijat edasi
            mangija.setTranslateX(mangija.getTranslateX() + 3);
        }
    }

    public boolean verticalHitDetection() {             //Kukub platvormile ja jääb püsima (sõbra abi - Jason Parks)
        for (Rectangle platvorm : platvormid) {         //Käi läbi kõik platvormid
            if (mangija.getBoundsInParent().intersects(platvorm.getBoundsInParent())) { //Kui mängija asukoht ristub platvormiga...
                if (grav != -4 && grav != 0) {          //Kui gravitatsioon ei ole -4 (ehk ei toimu hüpet) ja see ei ole 0 (ehk mängija ei seisa platvormil,
                    grav = 0;                           //siis lülita gravitatsioon välja ja
                    jumping = false;                    //(Hüpe false ehk topelthüpet ei saa teha)
                    mangija.setTranslateY(platvorm.getTranslateY() - mangija.getHeight()); //sea mängija asukohaks platvorm + mängija kõrgus ehk platvormi kohale
                }
                return true;                            //Ülalnimetatud tegevuste tulemuseks määra meetodi tõeseks ehk kokkupõrge toimus
            }
        }
        return false;                                   //Kui kokkupõrget ei toimunud, siis määra meetod mittetõeseks
    }

    public boolean horizontalHitDetection() {           //Läheb vastu platvormi ja ei sõida sisse (sõbra abi - Jason Parks)
        for (Rectangle platvorm : platvormid) {         //Käi läbi kõik platvormid
            if (mangija.getTranslateX() + mangija.getWidth() > platvorm.getTranslateX()  //Kui mängija ei asu platvormi piires ehk mängija vasak külg + laius on suuremad
                    && mangija.getTranslateX() + mangija.getWidth() < platvorm.getTranslateX() + platvorm.getWidth()) { //platvormi vasakust ja paremast küljest
                //System.out.println("hit a platform " + platvorm.getTranslateX() + " " + platvorm.getTranslateY());
                //System.out.println("" + mangija.getTranslateY() + " " + (platvorm.getTranslateY() - mangija.getHeight()));
                if (mangija.getTranslateY() > platvorm.getTranslateY() - mangija.getHeight()) { //Kui samal ajal mängija Y-koordinaat on suurem platvormi Y-koordinaadist - mängija kõrgus
                    //System.out.println("ouch");                                                 //ehk mängija on platvormi kohal,
                    return true;                                                            //siis määra meetodi tõeseks ehk kokkupõrge toimus (mööda X-telge)
                }
            }
        }
        return false;           //Vastasel juhul on meetod mittetõene ehk kokkupõrget ei toimunud.
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        manguSisu();                            //Genereeri mängu sisu ehk lae level.

        Scene esimene = new Scene(appRoot);     //Akna tegemine ja appRoot näitamine
        primaryStage.setTitle("Napikas: The Game");
        primaryStage.setScene(esimene);
        primaryStage.show();

        VBox akenEnd = new VBox();                      //Death screen (Tehtud docs.oracle.com juhendi järgi)
        Button uuesti = new Button("Uuesti?");
        uuesti.setStyle("-fx-font-size: 21pt;");        //Nupu suurus (teksti suurus)
        akenEnd.setStyle("-fx-background-color:RED");
        akenEnd.getChildren().add(uuesti);
        akenEnd.setAlignment(Pos.CENTER);               //Seab nupu keskele
        Scene teine = new Scene(akenEnd, 600, 400);

        VBox congratsAken = new VBox();                 //Lõpusõnum
        Button uuesti2 = new Button("Uuesti?");
        uuesti2.setStyle("-fx-font-size: 21pt;");
        Text congratsT = new Text("See oli küll napikas!!!");
        congratsT.setStyle("-fx-font-size: 40pt;");
        congratsAken.getChildren().addAll(congratsT, uuesti2);
        congratsAken.setAlignment(Pos.CENTER);
        Scene kolmas = new Scene(congratsAken, 600, 400);

        AnimationTimer timer = new AnimationTimer() { //Kogu liikumine, mis mängus toimub.
            @Override
            public void handle(long now) {
                boolean isHorizontalHitting = horizontalHitDetection();
                playerMove(isHorizontalHitting);
                boolean isHitting = verticalHitDetection();
                gravity(isHitting);
                taustLiigub();

                if (mangija.getTranslateY() > appRoot.getTranslateY() + 400) {  //Kui mängija kukub alla, siis näita Death Screen
                    primaryStage.setScene(teine);
                    }

                if (mangija.getTranslateX() > (manguLaius + 5) * 50){           //kui mängija jõuab lõppu + 1 ruut, siis näita lõpusõnumit
                    primaryStage.setScene(kolmas);
                    }
            }

        };timer.start();

        {
            esimene.setOnKeyPressed(event -> {          //Hüppamine (sõbra abi - Jason Parks)
                if (event.getCode() == KeyCode.SPACE && mangija.getTranslateY() >= 5 && !jumping){
                    grav = -3;
                    jumping = true;
                } else if (event.getCode() == KeyCode.UP && mangija.getTranslateY() >= 5 && !jumping){
                    grav = -3;
                    jumping = true;
                    }
            });

            uuesti.setOnAction(event -> {       //Death screen nupp -> reset game
                mangija.setTranslateX(100);
                mangija.setTranslateY(100);
                gameRoot.setLayoutX(0);
                primaryStage.setScene(esimene);
            });

            uuesti2.setOnAction(event -> {       //Death screen nupp -> reset game
                mangija.setTranslateX(100);
                mangija.setTranslateY(100);
                gameRoot.setLayoutX(0);
                primaryStage.setScene(esimene);
            });
        }
    }
}