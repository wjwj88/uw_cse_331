package hw9;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import java.awt.BasicStroke;
import hw5.EdgesOfNode;
import hw8.CampusPathFinderModel;
import hw8.NodeCoordinate;






/**
 * CampusPathView represents the view of path 
 *      finding tool for the campus data.  
 *      It displays the path on the campus map 
 * <p>
 */
public class CampusPathView extends JComponent{
	
	// Rep invariant:
	//     model, x_ratio, y_ratio, img, pathList
	//             and coordinateList are not null
			
	// Abstract function: 
	//     AF(this) = a CampusPathView c such that
	//		   c.model = this.model
	//		   c.x_ratio = this.x_ratio
	//         c.y_ratio = this.y_ratio
	//		   c.img = this.img
	//         c.pathList = this.pathList;
    //         c.coordinateList = this.coordinateList;
	
	
	
	
	
	//hold a CampusPathFinderModel	
	private CampusPathFinderModel model;
	//hold the ration of the window width to campus map width
	private double x_ratio;
	//hold the ration of the window height to campus map height
	private double y_ratio;
	//hold the campus map image
	private BufferedImage img;
	//hold all the path edges in this path list
	private List<EdgesOfNode<String,Double>> pathList;
	//hold all the coordinates of nodes in this path list
	private List<NodeCoordinate> coordinateList;

	
	/**
	 * Construct a map view for the GUI of 
	 *        campus path tool.
	 * @param model the CampusPathFinderModel for campus path tool
	 * @requires model != null 
	 * @effects load the campus map to the image
	 *     
	 */
	//construct a new CampusPathView
	public CampusPathView (CampusPathFinderModel model){
		
		this.model = model;		
		pathList = null;
		
		this.setPreferredSize(new Dimension (1000, 700));
		
		//load image
		try {
		    img = ImageIO.read(new File("src/hw8/data/campus_map.jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		}				
	}
	
	
	/**
	 * get the shortest distance from start to  
	 *        destination for this path.
	 * @param start the node where the shortest path starts
	 * @param destination the node where the shortest path stops
	 * @requires start != null && destination !=null 
	 * @returns the shortest distance from start to 
	 *           destination for this path.  
	 *     
	 */
	//find the shortest distance between two buildings 
	public double getDistance(String start, String destination){
		Map<String, NodeCoordinate> shortName2Coordinate=
				   model.getShortName2Coordinate();
		String startCoor2String=""+shortName2Coordinate.get(start).getX()+
				  ","+shortName2Coordinate.get(start).getY();
		String endCoor2String=""+shortName2Coordinate.get(destination).getX()+
				  ","+shortName2Coordinate.get(destination).getY();
		
		//get the path
		pathList = model.findShortestPath(startCoor2String, endCoor2String);
		int last = pathList.size()-1;
		//get the distance 
		double result = pathList.get(last).getEdge();

		repaint();
		
		//put all coordinates in this path into a list
		coordinateList = new ArrayList<NodeCoordinate>();
		for (EdgesOfNode<String,Double> e : pathList){
			coordinateList.add(model.getCoorOfNodeOnPath(e.getTail())) ;
		}	
				
		return result;		
	}
	
	
	/**
	 * reset the path to initial state        
	 *     
	 */
	public void reset() {
		pathList = null;
		repaint();
	}
	
	
	/**
	 * paint appropriate components on the graph
	 * @param g the graph used to draw components
	 * @modifies the view the user see
	 * @effects display the map and draw the path  
	 *           on this graph, or resize them when 
	 *           the window is resized    
	 */
	@Override
	public void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;

		
		// draw campus map
		g2.drawImage(img, 0, 0, getWidth(), getHeight(), 
					 0, 0, img.getWidth(), img.getHeight(), null);
		
		x_ratio = getWidth()*1.0/img.getWidth();
		y_ratio = getHeight()*1.0/img.getHeight();
		
		
		// draw the path on the map
		g2.setColor(Color.red);		
		g2.setStroke(new BasicStroke(2));
		int i;
		if(pathList != null){
			
			//connects all nodes on the path with lines
			for (i = 0; i < coordinateList.size()-1; i++){
				int head_x = (int)Math.round(coordinateList.get(i).getX() * x_ratio);
				int head_y = (int)Math.round(coordinateList.get(i).getY() * y_ratio);
				int tail_x = (int)Math.round(coordinateList.get(i+1).getX() * x_ratio);
				int tail_y = (int)Math.round(coordinateList.get(i+1).getY() * y_ratio);
				g2.drawLine(head_x, head_y, tail_x, tail_y);
				
			}
			
			//mark starting node
			g2.setColor(Color.green);
			int start_x = (int)Math.round(coordinateList.get(0).getX() * x_ratio);
			int start_y = (int)Math.round(coordinateList.get(0).getY() * y_ratio);
			g2.fillOval(start_x - 5, start_y - 5, 10, 10);						
			
            //mark destination node
			g2.setColor(Color.green);
			int end_x = (int)Math.round(coordinateList.get(i).getX() * x_ratio);
			int end_y = (int)Math.round(coordinateList.get(i).getY() * y_ratio);
			g2.fillOval(end_x - 5, end_y - 5, 10, 10);
			

		}		
	}	
}
