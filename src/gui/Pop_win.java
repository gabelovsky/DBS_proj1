package gui;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Pop_win extends Stage{
	
	
	
	
	void new_window(){
		
		Label root=new Label();
		root.setAlignment(Pos.CENTER);
		setTitle("Error:");
		
		
		
		
        setScene(new Scene(root, 200, 100));
	}
	

}
