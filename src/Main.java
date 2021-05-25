import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Game game=new Game();// Oyuna dair tüm işlemler game sınıfında oyunu başlatmak için geçerli sceneyi game.startGame() yapılması lazım
        game.exitButton.setOnAction(e->{
            primaryStage.close();
        });

        game.mainMenuButton.setOnAction(e->{
            //Ana menuye dön
            //scene ana menu olucak
        });


        primaryStage.setScene(new Scene(game.startGame(),700,400));// başlangıç scene i login sayfası olucak
        primaryStage.setResizable(false);// ekran boyutunu degiştirmeyi önler
        primaryStage.show();
    }
}
