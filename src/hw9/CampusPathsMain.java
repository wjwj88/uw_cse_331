package hw9;

import hw8.CampusPathFinderModel;


/**
 * CampusPathsMain combines all the model, view and controller
 *      of path finding tool for the campus data.  
 *      It gets all commands from the user and 
 *      does appropriate actions.
 * <p>
 */
public class CampusPathsMain {
	
	
	/**
	 * combines all the model, view and controller
	 * 
	 * @param agrs
	 * @throws exception if the file is not found
	 */
	public static void main(String[] args) throws Exception {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				CampusPathFinderModel model;
				try {
					//create the model based on the data from campus file
					model = new 
					CampusPathFinderModel("src/hw8/data/campus_buildings.dat", 
					                     "src/hw8/data/campus_paths.dat");
					//create the GUI
					new CampusPathGui(model);
				} catch (Exception e) {
					e.printStackTrace();
				}
                 				
			}			
		});
	}
}
