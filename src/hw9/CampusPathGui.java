package hw9;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import hw8.CampusPathFinderModel;

/**
 * CampusPathGui represents the GUI (combination of 
 *      view and controller) of path finding tool 
 *      for the campus data.  
 * <p>
 */
public class CampusPathGui extends JFrame {
	
	// Rep invariant:
	//     model and guiFrame are not null
			
	// Abstract function: 
	//     AF(this) = a CampusPathGui c such that
	//		   c.model = this.model
	//		   c.guiFrame = this.guiFrame
	
	
	
	//hold a CampusPathFinderModel
	private CampusPathFinderModel model;
	//hold the view and controller in this JFrame
	private JFrame guiFrame;
	
	
	/**
	 * Construct a GUI of campus path tool.
	 * 
	 * @param model the CampusPathFinderModel for campus path tool
	 * @requires model != null 
	 * @effects create a GUI to display view and controller 
	 *     
	 */
	public CampusPathGui(CampusPathFinderModel model){
		this.model = model;
		//create a JFrame to hold view and controller
		guiFrame = new JFrame("Campus Path Finder");
		guiFrame.setPreferredSize(new Dimension(1024, 768));
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create the view and controller
		CampusPathView view = new CampusPathView(model);
		CampusPathController controller = new 
				CampusPathController(model, view );
		controller.setPreferredSize(new Dimension(1024, 58));
		
		
		// set the layout of the view and controller in the frame 
		guiFrame.add(controller, BorderLayout.NORTH);
		guiFrame.add(view, BorderLayout.CENTER);
		
		guiFrame.pack();
		guiFrame.setVisible(true);
		
	}

}
