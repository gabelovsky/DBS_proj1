package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import mediator.Mediator;

public class Res_control {
	Mediator med;

	Res_control(Mediator med){
		this.med=med;
	}
	
	void set_search(){
		med.get_res_tab().get_search_button().setOnAction(new EventHandler<ActionEvent>() { 
		    @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
		    public void handle(ActionEvent e) {
		    	try {
		    		
		    		
		    		
					Connection conn=DriverManager.getConnection(med.database, med.user, med.password);
					Statement st=conn.createStatement();
					med.get_res_tab().get_mid_display().getColumns().clear();
			
					String []columns={"Number:","Floor:","Type:","Alig.:","Price:"};
					String from_str;
					String to_str;
					String date_str;
					String num_str="";
					String floor_str="";
					String type_str="";
					String ali_str="";
					String price_str="";
					
					from_str="'"+med.get_res_tab().get_from_y().getValue()+"-"+med.get_res_tab().get_from_m().getValue()+"-"+med.get_res_tab().get_from_d().getText()+"'";
					to_str="'"+med.get_res_tab().get_to_y().getValue()+"-"+med.get_res_tab().get_to_m().getValue()+"-"+med.get_res_tab().get_to_d().getText()+"'";
					date_str=" AND(r.id NOT IN (SELECT room from bookings as bo where("+from_str+" BETWEEN bo.from_date AND bo.to_date) OR ("+to_str
					+"BETWEEN bo.from_date AND bo.to_date) OR ("+from_str+"<=bo.from_date AND"+to_str+">=bo.to_date)))"; 
					System.out.println(date_str);
					if(!med.get_res_tab().get_room_field().getText().equals("")){
						num_str=" AND r.number="+med.get_res_tab().get_room_field().getText();
					}
					if(!med.get_res_tab().get_floor_box().getValue().equals("-")){
						floor_str=" AND r.floor="+med.get_res_tab().get_floor_box().getValue();
					}
					if(!med.get_res_tab().get_type_box().getValue().equals("-")){
						type_str=" AND r.type='"+med.get_res_tab().get_type_box().getValue()+"'";
					}
		
					if(!med.get_res_tab().get_ali_box().getValue().equals("-")){
						ali_str=" AND r.alig='"+med.get_res_tab().get_ali_box().getValue()+"'";
					}
					
					if(!med.get_res_tab().get_price_box().getValue().equals("-")){	
						switch(med.get_res_tab().get_price_box().getValue()){
							case "50-":
								price_str=" AND r.price<=50";
								break;
							case "50-80":
								price_str=" AND r.price>=50 AND price<=80";
								break;
							case "80-150":
								price_str=" AND r.price>=80 AND price<=150";
								break;
							case "150+":
								price_str=" AND r.price>=150";
								break;	
						}	
					}
					
					
					ResultSet rs=st.executeQuery("SELECT r.number,r.floor,r.type,r.alig,r.price FROM rooms AS r LEFT JOIN bookings AS b ON b.room=r.id WHERE TRUE"
					+num_str+floor_str+type_str+ali_str+price_str+date_str+" ORDER BY r.number;");
					System.out.println("SELECT r.number,r.floor,r.type,r.alig,r.price FROM rooms AS r LEFT JOIN bookings AS b ON b.room=r.id WHERE TRUE"
					+num_str+floor_str+type_str+ali_str+price_str+date_str+" ORDER BY r.number;");
					
				   //get columns //this rework!!
					for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
			                
			                final int j = i;                
			                TableColumn col = new TableColumn(/*rs.getMetaData().getColumnName(i+1)*/columns[i]);
			               col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
			                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
			                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
			                    }                    
			                });
			                med.get_res_tab().get_mid_display().getColumns().addAll(col); 
			               
			            }

					//set data
					 ObservableList<ObservableList> data=FXCollections.observableArrayList();
					 while(rs.next()){
			                ObservableList<String> row = FXCollections.observableArrayList();
			                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
			                    row.add(rs.getString(i));
			                }
			    
			                data.add(row);
			            }
					 
					 med.get_res_tab().get_mid_display().setItems(data);
					
			        
					rs.close();
					st.close();
					conn.close();
					
				} catch (SQLException e1) {	
					e1.printStackTrace();}}});
	}
	
	void set_confirm(){
		med.get_res_tab().get_confirm_button().setOnAction(new EventHandler<ActionEvent>() { 
		    @SuppressWarnings("unchecked")
			public void handle(ActionEvent e) {
		    	
		    	
		    	
		    	
		    	if(med.get_res_tab().get_mid_display().getSelectionModel().isEmpty()){
		    		((Label)med.get_pop_win().get_err_stage().getScene().getRoot()).setText("No room selected");
		    		med.get_pop_win().get_err_stage().show();
		    		return;
		    	}		    	
		    	if(med.get_res_tab().get_res_name().getText().equals("")){
		    		((Label)med.get_pop_win().get_err_stage().getScene().getRoot()).setText("Reservation name missing");
		    		med.get_pop_win().get_err_stage().show();
		    		return;
		    	}
		    	if(med.get_res_tab().get_res_id().getText().equals("")){
		    		((Label)med.get_pop_win().get_err_stage().getScene().getRoot()).setText("Reservation id missing");
		    		med.get_pop_win().get_err_stage().show();
		    		return;
		    	}
		    	if(med.get_res_tab().get_pay_name().getText().equals("")){
		    		((Label)med.get_pop_win().get_err_stage().getScene().getRoot()).setText("Billing name missing");
		    		med.get_pop_win().get_err_stage().show();
		    		return;
		    	}
		    	if(med.get_res_tab().get_pay_id().getText().equals("")){
		    		((Label)med.get_pop_win().get_err_stage().getScene().getRoot()).setText("Billing id missing");
		    		med.get_pop_win().get_err_stage().show();
		    		return;
		    	}
		    	if(!med.get_res_tab().get_pay_box().getValue().equals("Cash") && med.get_res_tab().get_card_field().getText().equals("")){
		    		((Label)med.get_pop_win().get_err_stage().getScene().getRoot()).setText("Card number missing");
		    		med.get_pop_win().get_err_stage().show();
		    		return;
		    	}
		    	
		    	
		    	
				try {
					Connection conn=DriverManager.getConnection(med.database, med.user, med.password);
					Statement st=conn.createStatement();
					
					String from_str="'"+med.get_res_tab().get_from_y().getValue()+"-"+med.get_res_tab().get_from_m().getValue()+"-"+med.get_res_tab().get_from_d().getText()+"'";
					String to_str="'"+med.get_res_tab().get_to_y().getValue()+"-"+med.get_res_tab().get_to_m().getValue()+"-"+med.get_res_tab().get_to_d().getText()+"'";
					
					ObservableList<String> row=(ObservableList<String>) med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem();
					
					
					Calendar cal = Calendar.getInstance();
					cal.set(Integer.parseInt(med.get_res_tab().get_from_y().getValue()),
							Integer.parseInt(med.get_res_tab().get_from_m().getValue()),
							Integer.parseInt(med.get_res_tab().get_from_d().getText()));
					Date from_date = cal.getTime();
					cal.set(Integer.parseInt(med.get_res_tab().get_to_y().getValue()),
							Integer.parseInt(med.get_res_tab().get_to_m().getValue()),
							Integer.parseInt(med.get_res_tab().get_to_d().getText()));
					Date to_date = cal.getTime();
					
					int days = (int) ((to_date.getTime() - from_date.getTime()) / (1000 * 60 * 60 * 24));
					int price= days*Integer.parseInt(row.get(4));
					
					String cardnum="0";
					if(!med.get_res_tab().get_pay_box().getValue().equals("Cash"))
						cardnum=med.get_res_tab().get_card_field().getText();
					
					
					ResultSet rs=st.executeQuery("SELECT perid FROM customers WHERE perid="+med.get_res_tab().get_res_id().getText());
					if(!rs.next()){
						st.executeUpdate("INSERT INTO customers(name,perid) VALUES ('"+med.get_res_tab().get_res_name().getText()+"',"+med.get_res_tab().get_res_id().getText()+");");
					}
					
					st.executeUpdate("INSERT INTO billings(name,perid,type,cardid,sum) VALUES ('"
					+med.get_res_tab().get_pay_name().getText()+"',"+med.get_res_tab().get_pay_id().getText()
					+",'"+med.get_res_tab().get_pay_box().getValue()+"',"+cardnum
					+","+price+");"			
					,Statement.RETURN_GENERATED_KEYS);
					rs = st.getGeneratedKeys();
					int key=0;
					if ( rs.next() ) {
						key = rs.getInt(1);
					}
					
					st.executeUpdate("INSERT INTO bookings(from_date,to_date,room,customer,billing) VALUES "
							+"("+from_str+","+to_str+",(SELECT id FROM rooms WHERE number="+row.get(0)+"),(SELECT id FROM customers WHERE perid="+med.get_res_tab().get_res_id().getText()
							+"),"+Integer.toString(key)+");"
							);
				
					rs.close();
					st.close();
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();}}});
	}
	
	void set_total_thread(){
		Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                	Platform.runLater(new Runnable(){
						@SuppressWarnings("unchecked")
						@Override
						public void run() {
							if(!med.get_res_tab().get_mid_display().getSelectionModel().isEmpty()
							&& !med.get_res_tab().get_from_d().getText().equals("")
							&& !med.get_res_tab().get_to_d().getText().equals("")){
								
								ObservableList<String> row=(ObservableList<String>) med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem();
								Calendar cal = Calendar.getInstance();
								cal.set(Integer.parseInt(med.get_res_tab().get_from_y().getValue()),
										Integer.parseInt(med.get_res_tab().get_from_m().getValue()),
										Integer.parseInt(med.get_res_tab().get_from_d().getText()));
								Date from_date = cal.getTime();
								cal.set(Integer.parseInt(med.get_res_tab().get_to_y().getValue()),
										Integer.parseInt(med.get_res_tab().get_to_m().getValue()),
										Integer.parseInt(med.get_res_tab().get_to_d().getText()));
								Date to_date = cal.getTime();
								
								int days = (int) ((to_date.getTime() - from_date.getTime()) / (1000 * 60 * 60 * 24));
								int price= days*Integer.parseInt(row.get(4));
								med.get_res_tab().get_total_area().setText("Days: "+Integer.toString(days)+"\nPrice total: "+Integer.toString(price)+"€");
							}else{
								med.get_res_tab().get_total_area().setText("No room selected");
							}
							
							
							
							
						}
                		
                	});
                	try{Thread.sleep(100);}
                	catch (InterruptedException ex) {
        				break;
        			}
                } 
            }
        });
		med.get_main_stage().setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override public void handle(WindowEvent t) {
		        th.interrupt();
		    }
		});
		th.start();
		
	}
	
}
