package services;

import java.util.Date;

import javax.jws.WebService;

@WebService
public class ParkingService {

	public Sale parkingSale(String agencyId, String plate, int minutes, Date startTime){
		return new Sale();
	}

	public Credit parkingCancel(){
		return new Credit();
	}
}
