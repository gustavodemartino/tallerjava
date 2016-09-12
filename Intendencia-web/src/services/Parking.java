package services;

import javax.jws.WebService;

@WebService
public class Parking {

	public Sale parkingSale(String agencyId, String plate, int minutes, long startTime){
		return new Sale();
	}

	public Credit parkingCancel(){
		return new Credit();
	}
}
