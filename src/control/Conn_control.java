package control;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.scene.control.Label;
import mediator.Mediator;

public class Conn_control {

	Mediator med;
	Conn_control(Mediator med){
		this.med=med;
	}
	
	void set_conn(){
		med.get_conn_win().get_conn_but().setOnAction(event ->{
			med.set_database(med.get_conn_win().get_dbs_field().getText());
			med.set_user(med.get_conn_win().get_name_field().getText());
			med.set_password(med.get_conn_win().get_pass_field().getText());
			
			try {
				Connection conn=DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
				
				med.get_conn_win().close();
				med.get_main_stage().show();
				
				conn.close();
			} catch (Exception e1) {
				((Label)med.get_pop_win().getScene().getRoot()).setText("Can't connect to database");
	    		med.get_pop_win().show();
	    		return;}
		});
	}

}
