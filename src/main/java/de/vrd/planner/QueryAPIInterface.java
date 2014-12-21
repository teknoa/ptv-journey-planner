package de.vrd.planner;

import java.util.List;

import de.vrd.ptvapi.model.Departure;
import de.vrd.ptvapi.model.Stop;

public interface QueryAPIInterface {
	
	public List<Stop> getNearStopNames(double lat, double lon);
	
	public List<Stop> searchStop(String pattern);

	public List<Departure> getJourney(String source, String target);
}
