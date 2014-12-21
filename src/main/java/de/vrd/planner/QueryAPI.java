package de.vrd.planner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.vrd.cache.ResultCache;
import de.vrd.ptvapi.connector.PTVAPI;
import de.vrd.ptvapi.model.Departure;
import de.vrd.ptvapi.model.Result;
import de.vrd.ptvapi.model.Stop;

public class QueryAPI implements QueryAPIInterface {

	PTVAPI api;
	
	boolean caching = true;
	
	ResultCache cache;
	
	public QueryAPI(PTVAPI api) {
		super();
		this.api = api;
		cache = new ResultCache();
	}

	public List<Stop> getNearStopNames(double lat, double lon) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Stop> searchStop(String pattern) {
		
		Set<String> setFoundStationNames = new HashSet();
		List<Stop> foundstops = new ArrayList<Stop>();
		List<Result> stationInfo = api.getStationInfo(pattern);
		for(Result curres : stationInfo) {
			if(curres instanceof Stop) {
				List<Departure> broadNextDepartures = api.getBroadNextDepartures((Stop)curres, 1);
				for(Departure dep : broadNextDepartures) {
//					System.out.println(dep.getPlatform().getStop().getLocation_name() 
//							+ "| direction:" + dep.getPlatform().getDirection().getDirection_name()
//							+ "| destination:" + dep.getPlatform().getStop().getTransport_type() + " to " + dep.getRun().getDestination_name());
					String name = dep.getPlatform().getStop().getLocation_name();
					if( ! setFoundStationNames.contains(name)) {
						foundstops.add(dep.getPlatform().getStop());
						setFoundStationNames.add(name);
						if(caching){
							cache.getStopByStopId.put(dep.getPlatform().getStop().getStop_id(), dep.getPlatform().getStop());
						}
					}
				}
			}
		}
		
		
		
		return foundstops;
	}

	public List<Departure> getJourney(String source, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
