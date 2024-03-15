package com.guvi.ticket_reservation_app;

import com.guvi.ticket_reservation_app.entity.Bus;
import com.guvi.ticket_reservation_app.repository.BusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

@SpringBootTest
class BusTicketReservationApplicationTests {

	@Autowired
	private BusRepository busRepository;

	@Test
	void contextLoads() {
		/*Bus bus1 = new Bus();
		bus1.setBusName("Tippu Sulthan Travels");
		bus1.setBusType("AC Sleeper");
		bus1.setFrom("Chennai");
		bus1.setTo("Nagercoil");
		bus1.setDepartureTime(LocalTime.parse("19:30"));
		bus1.setArrivalTime(LocalTime.parse("08:00"));
		bus1.setPrice(850);
		bus1.setTotalSeats(40);
		bus1.setAvailableSeats(40);
		bus1.setLastSoldSeatNumber(0);
		busRepository.save(bus1);

		Bus bus2 = new Bus();
		bus2.setBusName("Tranz King Travels");
		bus2.setBusType("Non-AC Seater");
		bus2.setFrom("Chennai");
		bus2.setTo("Nagercoil");
		bus2.setDepartureTime(LocalTime.parse("17:15"));
		bus2.setArrivalTime(LocalTime.parse("06:45"));
		bus2.setPrice(950);
		bus2.setTotalSeats(40);
		bus2.setAvailableSeats(40);
		bus2.setLastSoldSeatNumber(0);
		busRepository.save(bus2);*/
	}

}
