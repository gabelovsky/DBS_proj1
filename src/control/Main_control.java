package control;


import mediator.Mediator;

public class Main_control {
	
	
	public Main_control(Mediator med){
		
		
		
		Conn_control conn=new Conn_control(med);
		conn.set_conn();
		conn.set_client();
		conn.set_closes();
		Res_control res=new Res_control(med);
		res.set_search();
		res.set_confirm();
		res.set_service_boxes();
		res.set_total_thread();
		res.set_listener();
		res.set_date_listener();
		res.set_jedis_thread();
		Search_control sea=new Search_control(med);
		sea.set_room();
		sea.set_person();
		sea.set_reservation();
		sea.set_remove();
		sea.set_edit();
		sea.set_show();
		Edit_control edi=new Edit_control(med);
		edi.set_confirm();
		edi.set_cancel();
		edi.set_show();
	}
	
	
	
	
	
	
}
