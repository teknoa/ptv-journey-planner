package de.vrd.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.vrd.planner.QueryAPI;
import de.vrd.ptvapi.connector.PTVAPI;
import de.vrd.ptvapi.model.Stop;

public class TestQueryAPI {

	PTVAPI api;
	
	QueryAPI qAPI;
	@Before
	public void setUp() throws Exception {
		
		api = new PTVAPI("1000317", "7e39cff6-7aad-11e4-a34a-0665401b7368");
		qAPI = new QueryAPI(api);
	}

	@Test
	public void testGetStations() {
//		fail("Not yet implemented");
		String pattern;
		pattern = "caulfield";
		System.out.println("searching for: " + pattern);
		List<Stop> searchStop = qAPI.searchStop(pattern);
		for(Stop stop : searchStop)
			System.out.println("["+stop.getStop_id()+"]."+stop.getTransport_type() + " departing at: " +stop.getLocation_name());
//		pattern = "Clayton St";
//		System.out.println("searching for: " + pattern);
//		qAPI.searchStop(pattern);
	}

}
