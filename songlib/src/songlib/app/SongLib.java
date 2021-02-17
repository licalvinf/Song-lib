/*Names:
	Andrew Cheng (Netid: ac1792)
	Calvin Li (Netid: cfl53)
Section: 01*/
package songlib.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
		    	if(listCtrl.madeChanges) {
		    		//Do confirm save changes
		    		if(listCtrl.confirmAlert("Confirm Save", "The program will terminate, but you still have unconfirmed changes. Click OK to save to: \"C:/data/songlibCSV.txt\" and close.")) {
		    			listCtrl.writeCSV();
		    		}
		    	}
		    	//System.out.println("Program closed");
		    	Platform.exit();
		    	System.exit(0);
		    }
		  });
		
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
