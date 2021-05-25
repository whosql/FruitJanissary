package Objects.Fruits;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Apple extends Fruit {

    public Apple(){
        point=8;
        setImage();
    }

    private void setImage(){
            Image image=new Image("Contents/Images/apple.png");
          this.setImage(image);
    }

    @Override
    public Pane slice(double X, double Y) {
        Pane pane=super.slice(X,Y);
        circle.setFill(new ImagePattern(new Image("Contents/Images/applesplash.png")));

        return pane;
    }
}

