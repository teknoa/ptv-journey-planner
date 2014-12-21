package de.vrd.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.vrd.planner.QueryAPI;
import de.vrd.ptvapi.connector.PTVAPI;
import de.vrd.ptvapi.model.Departure;
import de.vrd.ptvapi.model.Stop;

public class MainGUI extends JFrame{


	PTVAPI api;
	
	QueryAPI qAPI;

	
	JTextField inputSearch;
	
	JList<Stop> foundStops;
	JTextArea selectedFoundStop;
	
	JList<Stop> nearbyStops;
	JTextArea selectedNearbyStop;
	
	JList<Departure> nextBroadDepartures;
	JTextArea selectedNextBroadDeparture;
	
	JList<Departure> stoppingPattern;
	JTextArea selectedStopOnPattern;
	
	public MainGUI() {
		

		api = new PTVAPI("1000317", "7e39cff6-7aad-11e4-a34a-0665401b7368");
		qAPI = new QueryAPI(api);

		setTitle("PTV Api Browser");
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createUI();
		setupModelControl();
		
		setVisible(true);
		
	}
	

	void createUI() {
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(5, 5));
		
		
		JPanel layoutpanel;
		JScrollPane scrollpaneList;
		JScrollPane scrollpaneTextfield;
		
		layoutpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel jLabel = new JLabel("Search Station:");
		layoutpanel.add(jLabel);
		inputSearch = new JTextField(20);
		jLabel.setLabelFor(inputSearch);
		layoutpanel.add(inputSearch);
		panel.add(layoutpanel, BorderLayout.NORTH);
		
		JPanel centerpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		/*
		 * Elements for 
		 *  * found stops
		 *  * found nearby stops for currently selected stop
		 */
		layoutpanel = new JPanel();
		layoutpanel.setLayout(new BoxLayout(layoutpanel, BoxLayout.Y_AXIS));
		layoutpanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		
//		layoutpanel.setMinimumSize(new Dimension(250, 500));
		foundStops = new JList<Stop>();
		scrollpaneList = new JScrollPane(foundStops);
		scrollpaneList.setPreferredSize(new Dimension(350, 150));
		scrollpaneList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		selectedFoundStop = new JTextArea();
		selectedFoundStop.setPreferredSize(new Dimension(250,50));
		selectedFoundStop.setLineWrap(true);
		
		layoutpanel.add(new JLabel("found stops"));
		layoutpanel.add(scrollpaneList);
		layoutpanel.add(selectedFoundStop);
		
		
		nearbyStops = new JList<Stop>();
		scrollpaneList = new JScrollPane(nearbyStops);
		scrollpaneList.setPreferredSize(new Dimension(350, 150));
		scrollpaneList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		selectedNearbyStop = new JTextArea();
		selectedNearbyStop.setPreferredSize(new Dimension(250, 50));
	
		layoutpanel.add(new JLabel("nearby stops at selected stop"));
		layoutpanel.add(scrollpaneList);
		layoutpanel.add(selectedNearbyStop);
		
		centerpanel.add(layoutpanel);
		
		/*
		 * Elements for next Broad departures
		 */
		layoutpanel = new JPanel();
		layoutpanel.setLayout(new BoxLayout(layoutpanel, BoxLayout.Y_AXIS));
		layoutpanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
		
		nextBroadDepartures = new JList<Departure>();
		scrollpaneList = new JScrollPane(nextBroadDepartures);
		scrollpaneList.setPreferredSize(new Dimension(450, 150));
		scrollpaneList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		selectedNextBroadDeparture = new JTextArea();
		scrollpaneTextfield = new JScrollPane(selectedNextBroadDeparture);
		scrollpaneTextfield.setPreferredSize(new Dimension(50, 100));
		
		layoutpanel.add(new JLabel("next broad departures"));
		layoutpanel.add(scrollpaneList);
		layoutpanel.add(scrollpaneTextfield);
//		
		
		/*
		 * Elements for next Specific departures
		 */
		stoppingPattern = new JList<Departure>();
		scrollpaneList = new JScrollPane(stoppingPattern);
		scrollpaneList.setPreferredSize(new Dimension(450, 150));
		scrollpaneList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
		selectedStopOnPattern = new JTextArea();
		selectedStopOnPattern.setPreferredSize(new Dimension(450, 50));
	
		layoutpanel.add(new JLabel("next specific departures"));
		layoutpanel.add(scrollpaneList);
		layoutpanel.add(selectedStopOnPattern);
		
		centerpanel.add(layoutpanel);
		
		
		
		panel.add(centerpanel, BorderLayout.CENTER);
	
	}
	
	private void setupModelControl() {
		
		inputSearch.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							String pattern = inputSearch.getText();
							List<Stop> searchStop = qAPI.searchStop(pattern);
							final DefaultListModel<Stop> model = new DefaultListModel<Stop>();
							for(Stop stop : searchStop)
								model.addElement(stop);
							
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {
									foundStops.setModel(model);
//									foundStops.setSelectedIndex(0);
								}
							});
							
						}
					}).start();
					
				}
			}
			
		});
		
		foundStops.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Stop selectedStop = foundStops.getSelectedValue();
				selectedFoundStop.setText(selectedStop.toString());
				
				List<Departure> broadNextDepartures = api.getBroadNextDepartures(selectedStop, 10);
				final DefaultListModel<Departure> model = new DefaultListModel<Departure>();
				for(Departure departure : broadNextDepartures)
					model.addElement(departure);
				nextBroadDepartures.setModel(model);
			}
		});
		
		nextBroadDepartures.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Departure selectedDeparture = nextBroadDepartures.getSelectedValue();
				selectedNextBroadDeparture.setText(selectedDeparture.toStringRecursive());
				
				List<Departure> stopPattern = api.getStoppingPattern(selectedDeparture, selectedDeparture.getTime_table_utc());
				final DefaultListModel<Departure> model = new DefaultListModel<Departure>();
				for(Departure departure : stopPattern)
					model.addElement(departure);
				stoppingPattern.setModel(model);
			}
		});
		
		stoppingPattern.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Departure selectedDeparture = stoppingPattern.getSelectedValue();
				selectedStopOnPattern.setText(selectedDeparture.toStringRecursive());
				
			}
		});
	}

	
	public static void main(String[] args) {
			new MainGUI();
	}

}
