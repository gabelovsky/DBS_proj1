package gui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Connect_win extends Stage{
	Button conn_but;
	TextField dbs_field;
	TextField name_field;
	PasswordField pass_field;
	
	public Button get_conn_but(){
		return conn_but;
	}
	public TextField get_dbs_field(){
		return dbs_field;
	}
	public TextField get_name_field(){
		return name_field;
	}
	public TextField get_pass_field(){
		return pass_field;
	}
	void new_window(){
		VBox root=new VBox();
		conn_but=new Button("Connect");
		conn_but.setMinSize(120,65);
		Label dbs_label=new Label("Database:");
		dbs_field=new TextField("jdbc:postgresql://localhost/dbs_proj");
		Label name_label=new Label("Login name:");
		name_field=new TextField("postgres");
		Label pass_label=new Label("Password:");
		pass_field=new PasswordField();//"postgres"
		pass_field.setText("postgres");
		root.getChildren().addAll(dbs_label,dbs_field,name_label,name_field,pass_label,pass_field,conn_but);
		root.setAlignment(Pos.CENTER);

		root.setPadding(new Insets(50));
		root.setSpacing(10);
		setTitle("Connection");
        setScene(new Scene(root, 300, 300));
        setResizable(false);
	}
}
