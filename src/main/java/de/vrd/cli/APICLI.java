package de.vrd.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.junit.Before;

import de.vrd.planner.QueryAPI;
import de.vrd.ptvapi.connector.PTVAPI;

public class APICLI {


	PTVAPI api;
	
	QueryAPI qAPI;

	String optionDisplay =
			"Enter number for option and (comma separated) parameters for this option\n"
			+ "1. Search Stop (Parameter: Stopname)\n"
			+ "2. Broad departures (Parameter: StopID)\n"
			+ "3. Specific Departure (Parameter: StopID)\n"
			+ "0. exit\n"
			+ ": ";
			
			
	
	public APICLI() {
		setUp();
		
		try {
			queryLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void setUp(){
		
		api = new PTVAPI("1000317", "7e39cff6-7aad-11e4-a34a-0665401b7368");
		qAPI = new QueryAPI(api);
	}

	void queryLoop() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		String command = null;
		String[] params;
		do {
			System.out.print(optionDisplay);
			line = reader.readLine();
			StringTokenizer tok = new StringTokenizer(line, ",");
			
			if(!tok.hasMoreTokens()) //empty commands
				continue;
			
			command = tok.nextToken();
			
			params = new String[tok.countTokens()-1];
			int i = 0;
			while(tok.hasMoreTokens())
				params[i++] = tok.nextToken();
			
			switch(command){
			case "1":
				searchStop(params[0]);
				
			}
			
		} while(! command.equals("0"));
	}
	
	void searchStop(String name) {
		qAPI.searchStop(name);
	}
	
	
	public static void main(String[] args) {
		new APICLI();

	}

}
