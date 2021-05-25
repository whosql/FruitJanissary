package Objects;

import Objects.Interfaces.Sliceable;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class Throw extends ImageView implements Sliceable {

    public  String type;
    Timeline rota;
    RotateTransition rotate;

    public Throw(){
        rotate =new RotateTransition(Duration.millis(5000),this);
        rotate.setFromAngle(0);
        rotate.setToAngle(720);
        rotate.setCycleCount(1);
        rotate.play();
        setFitHeight(65);
        setFitWidth(65);
        setX(20+(Math.random()*160));
        setY(450);
        final int[] dy = {-2};
        int maxY=20+(int)(Math.random()*90);
        rota = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if(getX()<650){
                setX(getX()+1);
                if(getY()==maxY||getY()==maxY+1){
                    dy[0] = 2;
                }
                setY(getY()+ dy[0]);
            }
        }));

        rota.setCycleCount(-1);
        rota.play();
    }

    public void play(){
         rota.play();
         rotate.play();
    }

    public  void pause(){
        rota.pause();
        rotate.pause();
    }

    public void stop(){
        rota.stop();
        rotate.stop();
    }
}
