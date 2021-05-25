package Objects.Fruits;

import Objects.Interfaces.Sliceable;
import Objects.Throw;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public abstract class Fruit extends Throw implements Sliceable {
   public int point;
   public Circle circle;
            Fruit() {
         type="fruit";
     }

    @Override
    public Pane slice(double X, double Y) {
        AudioClip note =new AudioClip(this.getClass().getResource("/Contents/Sounds/sliceSound.wav").toString());
        note.setVolume(0.3);
        note.play();
        Label lbl=new Label("+"+point);
        lbl.setTextFill(Color.BLACK);
        lbl.setStyle("-fx-font-weight: bold");
        circle= new Circle(X,Y,50);
        circle.setFill(Color.YELLOW);
        lbl.setLayoutX(circle.getCenterX()-8);
        lbl.setLayoutY(circle.getCenterY()-15);
        Pane pane =new Pane();
        pane.getChildren().addAll(circle,lbl);
        return  pane;
    }
}
