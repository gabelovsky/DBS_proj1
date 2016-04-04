package control;

import mediator.Mediator;

public class Main_control {
	
	
	public Main_control(Mediator med){
		Res_control res=new Res_control(med);
		res.set_search();
		res.set_confirm();
		res.set_total_thread();
	}
	
	
	
	
}
