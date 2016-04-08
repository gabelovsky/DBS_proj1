package control;


import mediator.Mediator;

public class Main_control {
	
	
	public Main_control(Mediator med){
		Res_control res=new Res_control(med);
		res.set_search();
		res.set_confirm();
		res.set_total_thread();
		Search_control sea=new Search_control(med);
		sea.set_room();
		sea.set_person();
		sea.set_reservation();
		sea.set_remove();
		sea.set_edit();
		Edit_control edi=new Edit_control(med);
		edi.set_confirm();
		edi.set_cancel();
	}
	
	
	
	
	
	
}
