package hw8;

/**
 * This class represents coordinates of a node.
 * 
 * @specfield x_coordinate : double
 * @specfield y_coordinate : double
 * 
 * @author Jun Wang
 */
public class NodeCoordinate {
	
	public Double cx;// x coordinate
	public Double cy;// y coordinate
	
	
	// Rep Invariant: 
	//     cx is not null && cy is not null
	
	// Abstract function:
	//          this is the coordinate such that
	//          this.x = cx;
	//          this.y = cy;
	
	
	/**
	 * Constructs coordinates of a node. 
	 * 
	 * @param x : x coordinate of the node
	 * @param y : y coordinate of the node
	 * @requires cx != null && cy != null 
	 */
	public NodeCoordinate(double cx, double cy){
		this.cx=cx;
		this.cy=cy;	
		checkRep();
	}
	
	
	/**
	 * Returns x coordinate of the node
	 * 
	 * @return x coordinate of the node
	 */	
	public double getX(){
		checkRep();
		return cx;
	}
	
	
	/**
	 * Returns y coordinate of the node
	 * 
	 * @return y coordinate of the node
	 */
	public double getY(){
		checkRep();
		return cy;
	}
	
	/**
	 * Return the direction of the other coordinate
	 *           relative to this coordinate          
	 * @param other : coordinate of the other node 
	 * @requires other != null
	 * @return the direction of the other coordinate
	 *           relative to this coordinate
	 */	
	public String getDirection(NodeCoordinate other){
	   	 double theta=Math.atan2(other.getY()-this.getY(), other.getX()-this.getX());
	   	 if(theta>=-Math.PI/8&&theta<=Math.PI/8){
	   		 return "E";
	   	 }else if(theta>-3*Math.PI/8&&theta<-Math.PI/8){
	   		 return "NE";
	   	 }else if(theta>=-5*Math.PI/8&&theta<=-3*Math.PI/8){
	   		 return "N";
	   	 }else if(theta>-7*Math.PI/8&&theta<-5*Math.PI/8){
	   		 return "NW";
	   	 }else if(theta>Math.PI/8&&theta<3*Math.PI/8){
	   		 return "SE";
	   	 }else if(theta<=5*Math.PI/8&&theta>=3*Math.PI/8){
	   		 return "S";
	   	 }else if(theta>5*Math.PI/8&&theta<7*Math.PI/8){
	   		 return "SW";
	   	 }else{
	   		 return "W";
	   	 }
    }
	
	/**
	 * Returns a hash code for this Coordinates object
	 * 
	 * @return a hash code for this Coordinates object
	 */
	@Override
	public int hashCode(){
		checkRep();
		return cx.hashCode()+2*cy.hashCode();
	}
	
	/**
	 * Compares this object with the specified object
	 * 
	 * @param other : specified object to be compared
	 * @return true if other represents the same coordinates
	 */
	@Override
	public boolean equals(Object other){
		if(!(other instanceof NodeCoordinate)){
			return false;
		}		
		NodeCoordinate o=(NodeCoordinate) other;
		checkRep();
		return cx.equals(o.getX())&&cy.equals(o.getY());		
	}	
	
	
	/**
	 * Returns string representation of this coordinate.
	 * 
	 * @return string representation of this coordinate
	 */
	@Override
	public String toString() {
		String result = "(" + cx + ","+cy+")";
		return result;
	}
	
	
	/**
	 * Checks if representation invariant holds
	 */
	public void checkRep(){
		assert (!cx.equals(null)): "x coordinate cannot be null";
		assert (!cy.equals(null)): "y coordinate cannot be null";
	}
}
