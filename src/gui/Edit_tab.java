package gui;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Edit_tab extends Tab{
	
	
	NumberTextField room_field;
	ComboBoxC from_y_box;
	ComboBoxC from_m_box;
	NumberTextField from_d_field;
	ComboBoxC to_y_box;
	ComboBoxC to_m_box;
	NumberTextField to_d_field;
	
	TextField res_name_field;
	NumberTextField res_id_field;
	ComboBoxC pay_box;
	TextField pay_name_field;
	NumberTextField pay_id_field;
	NumberTextField card_field;
	
	ComboBoxC serv_box;
	ComboBoxC rserv_box;
	
	Button confirm_but;
	Button cancel_but;
	Button show_serv_but;
	
	public TextField get_room_field(){
		return room_field;
	}
	public ComboBoxC get_from_y(){
		return from_y_box;
	}
	public ComboBoxC get_from_m(){
		return from_m_box;
	}
	public NumberTextField get_from_d(){
		return from_d_field;
	}
	public ComboBoxC get_to_y(){
		return to_y_box;
	}
	public ComboBoxC get_to_m(){
		return to_m_box;
	}
	public NumberTextField get_to_d(){
		return to_d_field;
	}
	public Button get_confirm_but(){
		return confirm_but;
	}
	public Button get_cancel_but(){
		return cancel_but;
	}
	public Button get_show_but(){
		return show_serv_but;
	}
	public TextField get_res_name(){
		return  res_name_field;
	}
	public TextField get_res_id(){
		return  res_id_field;
	}
	public ComboBoxC get_pay_box(){
		return pay_box;
	}
	public TextField get_pay_name(){
		return  pay_name_field;
	}
	public TextField get_pay_id(){
		return pay_id_field;
	}
	public TextField get_card_field(){
		return card_field;
	}
	
	public ComboBoxC get_serv_box(){
		return serv_box;
	}
	public ComboBoxC get_rserv_box(){
		return rserv_box;
	}
	Edit_tab(){
		setText("Edit");
		setDisable(true);
		create_edit();
	}
	void create_edit(){
		
		GridPane grid= new GridPane();
		
		Label room_label=new Label("Room number");
		room_field=new NumberTextField();
		Label from_label=new Label("From:");
		
		from_y_box=new ComboBoxC("2016","2017","2018");
		from_m_box=new ComboBoxC("1","2","3","4","5","6","7","8","9","10","11","12");
		from_d_field=new NumberTextField();
	
		
		Label to_label=new Label("To:");
		to_y_box=new ComboBoxC("2016","2017","2018");
		to_m_box=new ComboBoxC("1","2","3","4","5","6","7","8","9","10","11","12");
		to_d_field=new NumberTextField();
		
		
		Label res_label=new Label("Reservation:");
		Label res_name_label=new Label("Name:");
		res_name_field=new TextField();
		Label res_id_label=new Label("Identity number:");
		res_id_field=new NumberTextField();
		
		Label bill_label=new Label("Billing:");
		Label pay_label=new Label("Payment method:");
		pay_box=new ComboBoxC("Cash","Card","Prepaid card");
		Label pay_name_label=new Label("Name:");
		pay_name_field=new TextField();
		Label pay_id_label=new Label("Identity number:");
		pay_id_field=new NumberTextField();
		Label card_label=new Label("Card number:");
		card_field=new NumberTextField();
		
		Label serv_label=new Label("Add service:");
		serv_box=new ComboBoxC("-","Golf","Beauty pack small","Beauty pack large","Sauna");
		Label rserv_label=new Label("Remove service:");
		rserv_box=new ComboBoxC("-","Golf","Beauty pack small","Beauty pack large","Sauna");
		
		show_serv_but=new Button("Current services");
		confirm_but=new Button("Confirm edit");
		cancel_but=new Button("Cancel");
		
		grid.add(room_label, 1, 0,2,1);
		grid.add(room_field, 1, 1);
		grid.add(from_label, 1, 2);
		grid.add(from_y_box, 1, 3);
		grid.add(from_m_box, 2, 3);
		grid.add(from_d_field, 3, 3);
		grid.add(to_label, 4, 2);
		grid.add(to_y_box, 4, 3);
		grid.add(to_m_box, 5, 3);
		grid.add(to_d_field, 6, 3);
		
		grid.add(res_label, 0, 4,2,1);
		grid.add(res_name_label, 1, 5);
		grid.add(res_name_field, 1, 6,2,1);
		grid.add(res_id_label, 3, 5);
		grid.add(res_id_field, 3, 6,2,1);
		
		grid.add(bill_label, 0, 7,2,1);
		grid.add(pay_name_label, 1, 8,2,1);
		grid.add(pay_name_field, 1, 9,2,1);
		grid.add(pay_id_label, 3, 8,2,1);
		grid.add(pay_id_field, 3, 9,2,1);
		
		grid.add(pay_label, 1, 10,2,1);
		grid.add(pay_box, 1, 11,2,1);
		grid.add(card_label, 3, 10,2,1);
		grid.add(card_field, 3, 11,2,1);
		
		grid.add(confirm_but, 6,6,2,3 );
		grid.add(cancel_but, 6, 9,2,3);
		grid.add(show_serv_but, 6, 12,2,3);
		
		grid.add(serv_label, 1, 12);
		grid.add(serv_box, 1, 13,3,1);
		grid.add(rserv_label, 1, 14);
		grid.add(rserv_box, 1, 15,3,1);
		
		room_field.setMaxWidth(50);
		from_d_field.setMaxWidth(50);
		to_d_field.setMaxWidth(50);
		
		
	
		res_name_field.setMaxWidth(100);
		pay_name_field.setMaxWidth(100);
		res_id_field.setMaxWidth(100);
		pay_id_field.setMaxWidth(100);
		card_field.setMaxWidth(100);
		confirm_but.setPrefSize(120, 65);
		cancel_but.setPrefSize(120, 65);
		show_serv_but.setPrefSize(120, 65);
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(50));
		grid.setAlignment(Pos.TOP_CENTER);
		setContent(grid);
	}
	
	
}
