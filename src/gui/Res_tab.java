package gui;


import java.util.Calendar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Res_tab extends Tab {
 
	ComboBoxC ser1_box;
	ComboBoxC ser2_box;
	ComboBoxC ser3_box;
	
	Button search_button;
	NumberTextField room_field;
	ComboBoxC floor_box;
	ComboBoxC type_box;
	ComboBoxC ali_box;
	ComboBoxC price_box;
	ComboBoxC from_y_box;
	ComboBoxC from_m_box;
	NumberTextField from_d_field;
	ComboBoxC to_y_box;
	ComboBoxC to_m_box;
	NumberTextField to_d_field;
	Button confirm_button;
	
	TextField res_name_field;
	NumberTextField res_id_field;
	Label total_area;
	ComboBoxC pay_box;
	TextField pay_name_field;
	NumberTextField pay_id_field;
	NumberTextField card_field;
	
	@SuppressWarnings("rawtypes")
	TableView mid_table;
	
	Res_tab(){
		setText("Reservations");
		BorderPane border_p = new BorderPane();
		create_top(border_p);
		create_mid(border_p);
		create_bot(border_p);
		setContent(border_p);	
		
		
	}
	
	public Button get_search_button(){
		 return search_button;
	}
	public TextField get_room_field(){
		return room_field;
	}
	@SuppressWarnings("rawtypes")
	public TableView get_mid_display(){
		return mid_table;
	}
	public ComboBoxC get_floor_box(){
		return floor_box;
	}
	public ComboBoxC get_type_box(){
		return type_box;
	}
	public ComboBoxC get_ali_box(){
		return ali_box;
	}
	public ComboBoxC get_price_box(){
		return price_box;
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
	public Button get_confirm_button(){
		return confirm_button;
	}
	public TextField get_res_name(){
		return  res_name_field;
	}
	public TextField get_res_id(){
		return  res_id_field;
	}
	public Label get_total_area(){
		return total_area;
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
	public ComboBoxC get_ser1_box(){
		return ser1_box;
	}
	public ComboBoxC get_ser2_box(){
		return ser2_box;
	}
	public ComboBoxC get_ser3_box(){
		return ser3_box;
	}
	
	void create_top(BorderPane border_p){
		GridPane top_grid= new GridPane();
		Label room_label=new Label("Room number");
		room_field=new NumberTextField();
		Label floor_label=new Label("Floor");
		floor_box=new ComboBoxC("-","0","1","2");
		Label type_label=new Label("Type");
		type_box=new ComboBoxC("-","Cheap","Luxury","2 man");
		Label ali_label=new Label("Alignment");
		ali_box=new ComboBoxC("-","North","South-E","South");
		Label price_label=new Label("Price");
		price_box=new ComboBoxC("-","50-","50-80","80-150","150+");
		//Label extra1_label= new Label("Extra 1");
		//ComboBoxC extra1_box=new ComboBoxC("-","Sauna","Balcony","Pool access");
		//Label extra2_label= new Label("Extra 2");
		//ComboBoxC extra2_box=new ComboBoxC("-","Sauna","Balcony","Pool access");
		search_button=new Button("Search room");
		
		Label from_label=new Label("From:");
		from_y_box=new ComboBoxC("2016","2017","2018");
		from_m_box=new ComboBoxC("1","2","3","4","5","6","7","8","9","10","11","12");
		from_d_field=new NumberTextField(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
		from_m_box.getSelectionModel().select(String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1));
		from_y_box.getSelectionModel().select(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		
		Label to_label=new Label("To:");
		to_y_box=new ComboBoxC("2016","2017","2018");
		to_m_box=new ComboBoxC("1","2","3","4","5","6","7","8","9","10","11","12");
		to_d_field=new NumberTextField(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1));
		to_m_box.getSelectionModel().select(String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1));
		to_y_box.getSelectionModel().select(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		
		
		top_grid.add(room_label, 0, 0);
		top_grid.add(room_field, 0, 1);
		top_grid.add(floor_label, 1, 0);
		top_grid.add(floor_box, 1, 1);
		top_grid.add(type_label, 2, 0);
		top_grid.add(type_box, 2, 1);
		top_grid.add(ali_label, 3, 0);
		top_grid.add(ali_box, 3, 1);
		top_grid.add(price_label, 4, 0);
		top_grid.add(price_box, 4, 1);
		//top_grid.add(extra1_label, 5, 0);
		//top_grid.add(extra1_box, 5, 1);
		//top_grid.add(extra2_label, 6, 0);
		//top_grid.add(extra2_box, 6, 1);
		
		
		top_grid.add(from_label, 1, 2);
		top_grid.add(from_y_box, 1, 3);
		top_grid.add(from_m_box, 2, 3);
		top_grid.add(from_d_field, 3, 3);
		
		
		top_grid.add(to_label, 4, 2);
		top_grid.add(to_y_box, 4, 3);
		top_grid.add(to_m_box, 5, 3);
		top_grid.add(to_d_field, 6, 3);
		
		top_grid.add(search_button, 0, 2, 1, 2);
		
		room_field.setMaxWidth(100);
		from_d_field.setMaxWidth(50);
		to_d_field.setMaxWidth(50);
		search_button.setPrefHeight(50);
		
		
		top_grid.setAlignment(Pos.CENTER);
		top_grid.setVgap(10);
		top_grid.setHgap(10);
		top_grid.setPadding(new Insets(10));
		
		border_p.setTop(top_grid);
	}
	void create_mid(BorderPane border_p){
		//ScrollPane mid_scroll= new ScrollPane();
		
		mid_table=new TableView<Object>();
		mid_table.setEditable(true);
		
		
		mid_table.setPlaceholder(new Label(""));
		 
	/*	mid_scroll.setContent(mid_table);
		mid_scroll.setPadding(new Insets(10));
		mid_scroll.setFitToHeight(true);
		mid_scroll.setFitToWidth(true); ///better solution?
		
		mid_scroll.setStyle("-fx-background-color:transparent;");*/
		mid_table.setMaxWidth(500);
		border_p.setCenter(mid_table);
		
	}
	void create_bot(BorderPane border_p){

		GridPane bot_grid= new GridPane();
		
		Label res_label=new Label("Reservation:");
		Label res_name_label=new Label("Name:");
		res_name_field=new TextField();
		Label res_id_label=new Label("Identity number:");
		res_id_field=new NumberTextField();
		Label ser1_label=new Label("Service 1");
		ser1_box=new ComboBoxC("-","Golf","Beauty pack small","Beauty pack large","Sauna");
		Label ser2_label=new Label("Service 2");
		ser2_box=new ComboBoxC("-","Golf","Beauty pack small","Beauty pack large","Sauna");
		Label ser3_label=new Label("Service 3");
		ser3_box=new ComboBoxC("-","Golf","Beauty pack small","Beauty pack large","Sauna");
		Label total_label=new Label("Total:");
		total_area=new Label();
		
		Label bill_label=new Label("Billing:");
		Label pay_label=new Label("Payment method:");
		pay_box=new ComboBoxC("Cash","Card","Prepaid card");
		Label pay_name_label=new Label("Name:");
		pay_name_field=new TextField();
		Label pay_id_label=new Label("Identity number:");
		pay_id_field=new NumberTextField();
		Label card_label=new Label("Card number:");
		card_field=new NumberTextField();
		confirm_button=new Button("Confirm");
		
		
		bot_grid.add(res_label, 0, 0,2,1);
		bot_grid.add(res_name_label, 1, 1);
		bot_grid.add(res_name_field, 1, 2);
		bot_grid.add(res_id_label, 2, 1);
		bot_grid.add(res_id_field, 2, 2);
		
		bot_grid.add(ser1_label, 1, 3);
		bot_grid.add(ser1_box, 1, 4);
		bot_grid.add(ser2_label, 2, 3);
		bot_grid.add(ser2_box, 2, 4);
		bot_grid.add(ser3_label, 3, 3);
		bot_grid.add(ser3_box, 3, 4);
		
		bot_grid.add(total_label, 4, 1);
		bot_grid.add(total_area, 4, 2,2,3);
	
		bot_grid.add(bill_label, 0, 5,2,1);
		bot_grid.add(pay_label, 1, 6);
		bot_grid.add(pay_box, 1, 7);
		bot_grid.add(pay_name_label, 2, 6);
		bot_grid.add(pay_name_field, 2, 7);
		bot_grid.add(pay_id_label, 3, 6);
		bot_grid.add(pay_id_field, 3, 7);
		
		bot_grid.add(card_label, 1, 8);
		bot_grid.add(card_field, 1, 9);
		bot_grid.add(confirm_button, 4, 6,2,2);
		
		
		
		confirm_button.setPrefHeight(50);
		confirm_button.setPrefWidth(100);
		
		total_area.setPrefSize(150, 80);
		total_area.setStyle("-fx-border-color:black;");
		total_area.setAlignment(Pos.TOP_CENTER);
	
		
	
		bot_grid.setVgap(10);
		bot_grid.setHgap(10);
		bot_grid.setPadding(new Insets(10));
		bot_grid.setAlignment(Pos.CENTER);
		border_p.setBottom(bot_grid);
	}
}
