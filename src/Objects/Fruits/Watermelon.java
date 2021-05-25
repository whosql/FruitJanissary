package Objects.Fruits;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Watermelon extends Fruit {

    public Watermelon(){
        point=4;
        setImage();

    }

    private void setImage(){
        Image image=new Image("Contents/Images/watermelon.png");
        this.setImage(image);
    }

    @Override
    public Pane slice(double X, double Y) {
        Pane pane=super.slice(X,Y);
        circle.setFill(new ImagePattern(new Image("Contents/Images/watermelonsplash.png")));

        return pane;

    }
}
