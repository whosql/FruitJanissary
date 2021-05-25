import Objects.Bombs.Bomb;
import Objects.Fruits.*;
import Objects.Throw;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class Game {

    private IntegerProperty fallenFruit = new SimpleIntegerProperty(0);
    private IntegerProperty score=new SimpleIntegerProperty(0);
    private BorderPane root=new BorderPane();
    private Pane pausePane =new Pane();

    private AudioClip splashSound =new AudioClip(this.getClass().getResource("/Contents/Sounds/splash.wav").toString());
    private AudioClip unPauseSound =new AudioClip(this.getClass().getResource("/Contents/Sounds/unPause.wav").toString());
    private AudioClip pauseSound =new AudioClip(this.getClass().getResource("/Contents/Sounds/pause.wav").toString());
    private AudioClip pauseMusic =new AudioClip(this.getClass().getResource("/Contents/Sounds/pauseMusic.wav").toString());
    private ImageView background=new ImageView(new Image("Contents/Images/bg.jpg"));
    private final ImageView x=new ImageView();
    private final ImageView play=new ImageView(new Image("Contents/Images/play.png"));
    private final ImageView pause=new ImageView(new Image("Contents/Images/pause.png"));
    private VBox vBox=new VBox();
    private VBox vLeftBox=new VBox();
    private VBox vRightBox=new VBox();
    private Button restartButton=new Button("Play Again");
    Button mainMenuButton=new Button("Main Menu");
    Button exitButton=new Button("Quit");
    private Label overGameScoreLabel=new Label();
    private Label scoreLabel =new Label("SCORE : 0");
    private Group group=new Group();
    private Timeline createRandom;

    Game(){
        pause.setFitWidth(40);
        pause.setFitHeight(40);
        play.setFitWidth(80);
        play.setFitHeight(80);

        restartButton.setOnAction(e->{
            x.setVisible(false);
            fallenFruit.set(0);
            score.set(0);
            scoreLabel.setVisible(true);
            createRandom.play();
            vBox.getChildren().removeAll(restartButton,overGameScoreLabel,exitButton);
            root.getChildren().removeAll(vBox);
            pause.setVisible(true);
        });
        fallenFruit.addListener((observableValue, oldValue, newValue) ->
        {System.out.println("oldValue:"+ oldValue + ", newValue = " + newValue);
            if(newValue.equals(1)){
                x.setImage(new Image("Contents/Images/x.png"));
                x.setFitHeight(30);
                x.setFitWidth(30);
                x.setVisible(true);
            }
            if(newValue.equals(2)){
                x.setImage(new Image("Contents/Images/xx.png"));
                x.setFitHeight(30);
                x.setFitWidth(60);

            }
            if(newValue.equals(3)){
                x.setImage(new Image("Contents/Images/xxx.png"));
                x.setFitHeight(30);
                x.setFitWidth(90);
                endGame();
            }
        });

        score.addListener((observableValue, oldValue, newValue)->{
                scoreLabel.setText("SCORE : "+score.get()+"");
        });

        EventHandler<ActionEvent> FallenControlHandler= e->{
            for (Node node:group.getChildren()){
                Throw aThrow=(Throw) node;
                if(aThrow.getY()>415&&aThrow.getX()>300){
                    if(aThrow.type.equals("fruit")){
                        splashSound.setVolume(0.3);
                        splashSound.play();
                        Fruit fruit=(Fruit)aThrow;
                        group.getChildren().remove(fruit);
                        fallenFruit.set(fallenFruit.get()+1);
                        break;
                    }
                    if(aThrow.type.equals("bomb")){
                        Bomb bomb=(Bomb) aThrow;
                        group.getChildren().remove(bomb);
                        break;
                    }
                }
            }
        };

        Timeline fallenControl=new Timeline(new KeyFrame(Duration.millis(50),FallenControlHandler));
        fallenControl.setCycleCount(-1);
        fallenControl.play();

        group.setOnMouseDragOver(e->{
            System.out.println("Throw Sliced");
            int counter=0;
            for(Node node:group.getChildren()){
                Throw aThrow=(Throw) node;
                if(aThrow.type.equals("fruit")){
                    Fruit fruit=(Fruit)aThrow;
                    if(fruit.contains(e.getX(),e.getY())){
                        score.set(score.get()+fruit.point);
                        Pane sliceFruitEffect =fruit.slice(e.getX(),e.getY());
                        group.getChildren().remove(counter);
                        root.getChildren().add(sliceFruitEffect);
                        FadeTransition ft=new FadeTransition(Duration.millis(150),sliceFruitEffect);
                        ft.setFromValue(1.0);
                        ft.setToValue(0.4);
                        ft.setCycleCount(1);
                        ft.setAutoReverse(false);
                        ft.play();
                        ft.setOnFinished((ActionEvent event) -> {
                            root.getChildren().remove(sliceFruitEffect); });
                        System.out.println("Fruit Sliced");

                        break;
                    }
                }
                if(aThrow.type.equals("bomb")){
                    Bomb bomb=(Bomb)aThrow;

                    if(bomb.contains(e.getX(),e.getY())){
                        endGame();
                        System.out.println("Bomb Exploded");
                        group.getChildren().remove(bomb);
                        Pane bombExplodeEffect= bomb.slice(e.getX(),e.getY());
                        root.getChildren().add(bombExplodeEffect);
                        FadeTransition ft=new FadeTransition(Duration.millis(600),bombExplodeEffect);
                        ft.setFromValue(1.0);
                        ft.setToValue(0.1);
                        ft.setCycleCount(1);
                        ft.setAutoReverse(false);
                        ft.play();
                        ft.setOnFinished((ActionEvent event) -> {
                            root.getChildren().remove(bombExplodeEffect);
                        });
                        break;
                    }
                }
                counter++;
            }
        });

        pause.setOnMouseClicked(e->{
            System.out.println("clicked");
            pauseSound.play();
            pauseGame();
            play.setX(310);
            play.setY(160);
            Rectangle r1=new Rectangle(0,0,750,450);
            r1.setFill(Color.WHITE);
            r1.setOpacity(0.4);
            ImageView janissaryImage=new ImageView( new Image("Contents/Images/janissary.png"));
            janissaryImage.setFitWidth(100);
            janissaryImage.setFitHeight(300);
            janissaryImage.setX(50);
            janissaryImage.setY(50);
            pausePane.getChildren().addAll(r1,play,janissaryImage);
            root.getChildren().add(pausePane);
            for(Node node:group.getChildren()){
                Throw aThrow=(Throw)node;
                aThrow.pause();
            }
            pauseMusic.setCycleCount(-1);
            pauseMusic.play();

        });
        play.setOnMouseClicked(e->{
            pausePane.getChildren().removeAll(pausePane.getChildren());
            root.getChildren().remove(pausePane);
            pauseMusic.stop();
            for(Node node:group.getChildren()){
                Throw aThrow=(Throw)node;
                aThrow.play();
                createRandom.play();
                unPauseSound.play();
            }
        });
    }

    public Throw createRandom() {
        int randomNumb = (int) (Math.random() * 6);
        switch (randomNumb) {
            case 0: {
                return new Watermelon();
            }
            case 1: {
                return new Pear();
            }
            case 2: {
                return new Peach();
            }
            case 3: {
                return new Pineapple();
            }
            case 4: {
                return new Apple();
            }
            default:{
                System.out.println("Bomb");
                return new Bomb();
            }
        }
    }

    public BorderPane startGame(){
        root.getChildren().add(background);
        root.setOnDragDetected(e->root.startFullDrag());
        root.getChildren().add(group);
        root.setLeft(vLeftBox);
        vLeftBox.setMinWidth(150);
        vLeftBox.getChildren().addAll(scoreLabel,pause);
        vLeftBox.setPadding(new Insets(10,0,0,10));
        root.setRight(vRightBox);
        vRightBox.getChildren().add(x);
        vRightBox.setMinWidth(150);
        vRightBox.setAlignment(Pos.TOP_RIGHT);
        BorderPane.setAlignment(play,Pos.BOTTOM_LEFT);
        scoreLabel.setTextFill(Color.RED);
        scoreLabel.setFont(Font.font(25));
        BorderPane.setAlignment(scoreLabel,Pos.TOP_LEFT);
        CreateRandomMethod();
        return root;
    }

    public void CreateRandomMethod(){
        EventHandler<ActionEvent> handler= e->{

            group.getChildren().add(createRandom());
        };

        createRandom=new Timeline(new KeyFrame(Duration.millis(600),handler));
        createRandom.setCycleCount(Timeline.INDEFINITE);
        createRandom.play();
    }

    public void pauseGame(){
        createRandom.pause();
    }

    public void endGame(){
        vBox.getChildren().removeAll(vBox.getChildren());
        createRandom.stop();
        //database process
        //database process
        //database process
        //alınacak oyun verisi score.getValue(); olucak int türünde deger dönderir

        group.getChildren().removeAll(group.getChildren());
        scoreLabel.setVisible(false);
        pause.setVisible(false);
        overGameScoreLabel.setFont(Font.font(30));
        overGameScoreLabel.setTextFill(Color.RED);
        overGameScoreLabel.setStyle("-fx-font-weight: bold");
        String overGameScore="Your Score is : "+score.get();
        BorderPane.setAlignment(overGameScoreLabel,Pos.CENTER);
        overGameScoreLabel.setText(overGameScore);
        vBox.getChildren().add(overGameScoreLabel);
        vBox.getChildren().add(restartButton);
        vBox.getChildren().add(mainMenuButton);
        vBox.getChildren().add(exitButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        root.setCenter(vBox);
        BorderPane.setAlignment(vBox,Pos.CENTER);
    }

}

