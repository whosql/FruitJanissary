package Objects.Fruits;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Pineapple extends Fruit{
    public Pineapple(){
        point=5;
        setImage();

    }

    private void setImage(){
        Image image=new Image("Contents/Images/pineapple.png");
        this.setImage(image);
    }

    @Override
    public Pane slice(double X, double Y) {
        Pane pane=super.slice(X,Y);
        circle.setFill(new ImagePattern(new Image("Contents/Images/pineapplesplash.png")));
        return pane;
    }
}
