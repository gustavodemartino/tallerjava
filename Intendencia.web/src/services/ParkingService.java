package services;

import java.util.Date;

import javax.jws.WebService;

@WebService
public class ParkingService {

	public Ticket sale(String agencyId, String plate, int minutes, Date startTime){
		return new Ticket();
	}

	public Credit anulation(){
		return new Credit();
	}
}
