package de.vrd.cache;

import java.util.HashMap;
import java.util.Map;

import de.vrd.ptvapi.model.Stop;

public class ResultCache {
	
	public Map<Integer, Stop> getStopByStopId;
	
	public ResultCache() {
		getStopByStopId = new HashMap<Integer, Stop>();
	}
}
