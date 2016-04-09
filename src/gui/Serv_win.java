package gui;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Serv_win extends Stage {
	
	
	@SuppressWarnings("rawtypes")
	void new_window(){
		
		TableView root=new TableView();
		
		root.setPlaceholder(new Label("No service"));
		setTitle("Services:");
        setScene(new Scene(root, 170, 250));
       
	}
}
