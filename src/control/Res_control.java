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
					String num_str="";
				
					
					
					
					if(!med.get_res_tab().get_room_field().getText().equals("")){
						num_str=" AND number="+med.get_res_tab().get_room_field().getText();
					}
					
					
					
					ResultSet rs=st.executeQuery("SELECT number,floor,type,alig,price FROM rooms WHERE TRUE"+num_str);
					
				
					
				   //get columns
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
					e1.printStackTrace();
				}
		 		
		    }
		});
		
		
		
	}
	
	
	
}
