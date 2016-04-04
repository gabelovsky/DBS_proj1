package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
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
					date_str=" AND (("+from_str+" NOT BETWEEN b.from_date AND b.to_date) AND ("+to_str+" NOT BETWEEN b.from_date AND b.to_date) AND ("+
					from_str+"<b.from_date AND "+to_str+"<b.from_date OR "+from_str+">b.to_date AND "+to_str+">b.to_date) OR (r.id NOT IN (SELECT room FROM bookings)));";
					
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
					+num_str+floor_str+type_str+ali_str+price_str+date_str);
					
					
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
				try {
					Connection conn=DriverManager.getConnection(med.database, med.user, med.password);
					Statement st=conn.createStatement();
					
					ObservableList<String> row=(ObservableList<String>) med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem();
					System.out.println("SELECT id FROM rooms WHERE number="+row.get(0));
					
					ResultSet rs=st.executeQuery("SELECT id FROM rooms WHERE number="+row.get(0));
					
					
					
					
					//rs.close();
					st.close();
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();}}});
	}
	
}
