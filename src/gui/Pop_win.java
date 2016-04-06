package gui;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Pop_win{
	Stage stage;
	
	public Stage get_err_stage(){
		return stage;
	}
	
	
	void new_window(){
		Label root=new Label();
		root.setAlignment(Pos.CENTER);
		stage=new Stage();
		stage.setTitle("Error:");
        stage.setScene(new Scene(root, 200, 100));
        
	}
	

}
