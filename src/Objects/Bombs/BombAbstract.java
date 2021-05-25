package Objects.Bombs;

import Objects.Interfaces.Sliceable;
import Objects.Throw;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class BombAbstract extends Throw implements Sliceable {
    BombAbstract(){
        type="bomb";
    }
    public Circle circle;


    @Override
    public Pane slice(double X, double Y) {
        AudioClip note =new AudioClip(this.getClass().getResource("/Contents/Sounds/explodeSound.wav").toString());
        note.setVolume(0.3);
        note.play();
        circle= new Circle(X,Y,70);
        circle.setFill(Color.BLACK);
        Pane pane=new Pane();
        pane.getChildren().add(circle);
        return  pane;
    }
}
