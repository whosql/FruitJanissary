package Objects.Bombs;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Bomb extends BombAbstract {

    public Bomb(){
        setImage();

    }
    private void setImage(){
        Image image=new Image("Contents/Images/bomb.png");
        this.setImage(image);
    }





    @Override
    public Pane slice(double X, double Y) {
        Pane pane=super.slice(X,Y);
        circle.setFill(new ImagePattern(new Image("Contents/Images/bombexplode.png")));
        return pane;
    }
}
