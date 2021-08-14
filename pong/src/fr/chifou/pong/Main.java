package fr.chifou.pong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {



    private int dx;
    private int dy;
    private int score;

    public void start(Stage primaryStage) {
        try {
            Group root = new Group();
            Scene scene = new Scene(root,1000,600);

            scene.setFill(new RadialGradient(0, 0, 500, 300, 500, false, CycleMethod.NO_CYCLE, new Stop(0, Color.grayRgb(60)), new Stop(1, Color.BLACK)));



            //objets:
            Circle balle = new Circle(500, 300, 25);
            balle.setFill(Color.DARKRED);

            Rectangle joueur = new Rectangle(890, 250, 30, 100);
            joueur.setArcHeight(5);
            joueur.setArcWidth(5);
            joueur.setFill(Color.RED);

            Rectangle IA = new Rectangle(80, 250, 30, 100);
            IA.setFill(Color.RED);
            IA.setArcHeight(5);
            IA.setArcWidth(5);


            Text perdu = new Text("Vous avez perdu (by chifou)");
            perdu.setFont(Font.font("Arial", 60));
            perdu.setFill(Color.DARKRED);
            perdu.setTextOrigin(VPos.CENTER);
            perdu.setLayoutX(159);
            perdu.setLayoutY(300);
            perdu.setVisible(false);




            root.getChildren().addAll(joueur, IA, balle, perdu);



            dx = +5;
            dy = 5;
            score = 0;


            //souris:
            scene.setOnMouseMoved(e -> {

                joueur.setY(e.getSceneY() - 50);

            });



            // boucle de jeu
            Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>(){
                public void handle(ActionEvent arg) {

                    //deplacement:
                    balle.setCenterX(balle.getCenterX() + dx);
                    balle.setCenterY(balle.getCenterY() + dy);

                    //rebond H:
                    if(balle.getCenterY() <= 25){
                        dy = 5;
                    }

                    //rebond B:
                    if(balle.getCenterY() >= 575){
                        dy = -5;
                    }


                    //rebond D:
                    if(balle.getCenterX() >= 865){
                        if(balle.getCenterY() >= joueur.getY() && balle.getCenterY() <= joueur.getY() + 100){
                            dx = -5;
                        }

                    }

                    //rebond G:
                    if(balle.getCenterX() <= 110){
                        if(balle.getCenterY() >= IA.getY() && balle.getCenterY() <= IA.getY() + 100){
                            dx = 5;
                            score++;
                            primaryStage.setTitle("Pong       SCORE : " + score);
                        }

                    }



                    //deplacement IA:
                    if(IA.getY() + 50 < balle.getCenterY()){
                        IA.setY(IA.getY() + 5);
                    }
                    if(IA.getY() + 50 > balle.getCenterY()){
                        IA.setY(IA.getY() - 5);
                    }


                    //
                    if(balle.getCenterX() >= 890){
                        dx=0;
                        dy=0;
                        balle.setCenterX(920);

                        perdu.setVisible(true);
                    }


                }



            }));
            loop.setCycleCount(Timeline.INDEFINITE);
            loop.play();





            primaryStage.setScene(scene);

            primaryStage.setResizable(false);
            primaryStage.setTitle("Pong(By chifou)       SCORE : 0");
            primaryStage.show();


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
