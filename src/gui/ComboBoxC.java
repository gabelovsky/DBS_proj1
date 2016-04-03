package gui;

import javafx.scene.control.ComboBox;

public class ComboBoxC extends ComboBox<String> {
	
	ComboBoxC(String... args){
		getItems().addAll(args);
		getSelectionModel().selectFirst();
	}

}
