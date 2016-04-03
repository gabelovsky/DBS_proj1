package gui;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

import javafx.stage.Stage;

public class Main_gui extends Application{

	public static void main(String[] args) {
		launch();}

	
	@Override
	public void start(Stage main_stage) throws Exception {
		
		TabPane tp=new TabPane();
		Scene scene = new Scene(tp, 800, 800);
		Res_tab res_tab=new Res_tab();
		
		tp.getTabs().addAll(res_tab);
		
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);	
		main_stage.setTitle("Hotel manager 3000");
		main_stage.setScene(scene);
		main_stage.show();
	}

}
