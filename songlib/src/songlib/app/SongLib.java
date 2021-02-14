package songlib.app;

import javafx.application.Application;
import javafx.stage.Stage;
import songlib.view.SonglibController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{ 
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/songlib.fxml"));
		VBox root = (VBox)loader.load();
		
		//Added listcontroller for the songView list -- Andrew 02/12/2021
		SonglibController listCtrl = loader.getController();
		listCtrl.startList(primaryStage);
	
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
