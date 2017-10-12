package hw9;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import hw8.CampusPathFinderModel;


/**
 * CampusPathController represents the controller of path 
 *      finding tool for the campus data.  
 *      It gets all the commands from the user and 
 *      does appropriate actions.
 * <p>
 */
public class CampusPathController extends JPanel {
	
	
	// Rep invariant:
	//     model, view, From, startBuildingList, To
	//            endBuildingList, distance, distanceValue
	//            timeLabel, and time are not null
			
	// Abstract function: 
	//     AF(this) = a CampusPathController c such that
	//		   c.model = this.model
	//		   c.view = this.view
	//         c.From = this.From
	//		   c.startBuildingList = this.startBuildingList
	//         c.To = this.To;
    //         c.endBuildingList = this.endBuildingList;
    //         c.distance = this.distance;
    //         c.distanceValue = this.distanceValue;
    //         c.timeLabel = this.timeLabel;
    //         c.time = this.time;
	
	
	//hold a CampusPathFinderModel
	private CampusPathFinderModel model; 
	//hold a CampusPathView
	private CampusPathView view;
	//store a From label
	private JLabel From;
	//hold all building names of starting nodes to select
	private JComboBox <String> startBuildingList;
	//store a To label
    private JLabel To;
    //hold all building names of destination nodes to select
	private JComboBox <String> endBuildingList;
	//store a distance label
	private JLabel distance;
	//store distance value in this JTextField
	private JTextField distanceValue;
	//store a time label
	private JLabel timeLabel;
	//store time value in this JTextField
	private JTextField time;
	
	/**
	 * Construct a controller for the GUI of 
	 *        campus path tool.
	 * @param model the CampusPathFinderModel for campus path tool
	 * @param view the CampusPathView for campus path tool
	 * @requires model != null && view != null
	 * @effects create a JFrame to hold view and all
	 *              buttons to perform appropriate 
	 *              operations and labels to display 
	 *              related path and information
	 *     
	 */
	//construct a new CampusPathController
	@SuppressWarnings("unchecked")
	public CampusPathController(CampusPathFinderModel model, 
			          CampusPathView view ){
		this.model = model;
		this.view = view;
		Map<String,String> names = model.getShortName2LongName();
		// create names for JComboBox
		Set <String> buildingNames = new TreeSet <String>(); 
		for(String head : names.keySet()){
			String temp = head+": "+names.get(head);
			buildingNames.add(temp);
		}
						
		//create a panel to hold From and To labels
		JPanel fromToPanel = new JPanel(new GridLayout(2, 1));
		From = new JLabel("From :");
		To = new JLabel("To :");		
		//add From and To labels to related JPanel
		fromToPanel.add(From);
		fromToPanel.add(To);
				
		// create a panel to hold all Combobox
		JPanel optionPanel = new JPanel(new GridLayout(2, 1));
		// convert set of names to array
		startBuildingList = new 
				JComboBox(buildingNames.toArray(new String[0]));
		// convert set of names to array
		endBuildingList = new 
				JComboBox(buildingNames.toArray(new String[0]));
		//add Combobox to related JPanel
		optionPanel.add(startBuildingList);
		optionPanel.add(endBuildingList);				
		
		// create a panel to hold buttons for finding path and resetting operation
		JPanel buttonPanel = new JPanel(new GridLayout(2, 1));		
		JButton FindPath = new JButton("Find Path");
		//add ActionListener to FindPath JButton
		FindPath.addActionListener(new CurrentActionListener());
		JButton reset = new JButton("Reset");
		//add ActionListener to reset JButton	
		reset.addActionListener(new CurrentActionListener());					
		//add buttons to related JPanel
		buttonPanel.add(FindPath);
		buttonPanel.add(reset);
				
		//create a JPanel to hold distance JLabel and JTextField
		JPanel panel4Distance = new JPanel(new GridLayout(2, 1));
		distance = new JLabel("Total Distance");
		distanceValue =new JTextField("");
		//add distance JLabel and JTextField to JPanel
		panel4Distance.add(distance);
		panel4Distance.add(distanceValue);
		
		//create a JPanel to hold time JLabel and JTextField
		JPanel timePanel = new JPanel(new GridLayout(2, 1));
		timeLabel = new JLabel("Estimated Time");
		time = new JTextField("");
		//add time JLabel and JTextField to JPanel
		timePanel.add(timeLabel);
		timePanel.add(time);
		
		//add all JPanel to this JFrame
		this.add(fromToPanel);
		this.add(optionPanel);
		this.add(optionPanel);
		this.add(buttonPanel);		
		this.add(panel4Distance);		
		this.add(timePanel);

	}
		
	
	/**
	 * Construct a new ActionListener to respond to 
	 *        finding path and reset button 
	 *     
	 */
	//create a new ActionListener for finding path and resetting
	private class CurrentActionListener implements ActionListener {

		
		/**
		 * @param e an action event 
		 *     
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//get the current action the user choose
			String currentAction = e.getActionCommand();
			
			if(currentAction.equals("Find Path")){
				
				//get the items the user selected from the JComboBox
				String first = (String) startBuildingList.getSelectedItem();
            	String second =(String) endBuildingList.getSelectedItem();

            	//split the item string to get the short building name
            	String[] ftokens = first.split(":");
            	String[] stokens = second.split(":");            	
            	String start = ftokens[0];
            	String end = stokens[0];

            	//display distance and time in the appropriate JLabels
            	String result = 
       		         String.format("    %.0f feet", view.getDistance(start, end));
            	distanceValue.setText(result);
            	String timeString = String.format
            			("   %.1f minutes", view.getDistance(start, end)/4.5/60);
            	time.setText(timeString);
			}else{
				//reset the view and set distance and time JLabel to initial state
				view.reset();
				distanceValue.setText("");
				time.setText("");
			}
		}		
	}		

}
