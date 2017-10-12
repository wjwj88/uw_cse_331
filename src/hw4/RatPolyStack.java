package hw4;
import java.util.Iterator;
import java.util.Stack;

/**
 * <b>RatPolyStack</B> is a mutable finite sequence of RatPoly objects.
 * <p>
 * Each RatPolyStack can be described by [p1, p2, ... ], where [] is an empty
 * stack, [p1] is a one element stack containing the Poly 'p1', and so on.
 * RatPolyStacks can also be described constructively, with the append
 * operation, ':'. such that [p1]:S is the result of putting p1 at the front of
 * the RatPolyStack S.
 * <p>
 * A finite sequence has an associated size, corresponding to the number of
 * elements in the sequence. Thus the size of [] is 0, the size of [p1] is 1,
 * the size of [p1, p1] is 2, and so on.
 * <p>
 */
@SuppressWarnings("nullness")
public final class RatPolyStack implements Iterable<RatPoly> {

  private final Stack<RatPoly> polys;

  // Abstraction Function:
  // Each element of a RatPolyStack, s, is mapped to the
  // corresponding element of polys.
  //
  // RepInvariant:
  // polys != null &&
  // forall i such that (0 <= i < polys.size(), polys.get(i) != null

  /**
   * @effects Constructs a new RatPolyStack, [].
   */
  public RatPolyStack() {
    polys = new Stack<RatPoly>();
    checkRep();
  }

  /**
   * Returns the number of RayPolys in this RatPolyStack.
   *
   * @return the size of this sequence.
   */
  public int size() {//return the size of the stack
    return polys.size();
  }

  /**
   * Pushes a RatPoly onto the top of this.
   *
   * @param p The RatPoly to push onto this stack.
   * @requires p != null
   * @modifies this
   * @effects this_post = [p]:this
   */
  public void push(RatPoly p) {//push a RatPoly to the stack
	  checkRep();
	  polys.push(p);
	  checkRep();
  }

  /**
   * Removes and returns the top RatPoly.
   *
   * @requires this.size() > 0
   * @modifies this
   * @effects If this = [p]:S then this_post = S
   * @return p where this = [p]:S
   */
  public RatPoly pop() {
	  RatPoly r=new RatPoly();
	  if(size()>0){
		  r=polys.peek();// get a copy of the top RatPoly
		  polys.pop();// remove the top RatPoly
	  }
	  checkRep();
	  return r;
	  
  }

  /**
   * Duplicates the top RatPoly on this.
   *
   * @requires this.size() > 0
   * @modifies this
   * @effects If this = [p]:S then this_post = [p, p]:S
   */
  public void dup() {
	  RatPoly r;
      if(size()>0){
    	 r= polys.peek();// get a copy of the top RatPoly
    	 polys.push(r);// push the extra copy to the top
      }
      checkRep();
  }

  /**
   * Swaps the top two elements of this.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p2, p1]:S
   */
  public void swap() {
	  RatPoly first=new RatPoly(),second=new RatPoly();//create two empty RatPoly
      if(size()>=2){
    	 first=polys.pop(); //copy the first RatPoly and remove the first RatPoly
    	 second=polys.pop();//copy the second RatPoly and remove the second RatPoly
    	 polys.push(first);// push the first RatPoly to the stack 
    	 polys.push(second);// push the second RatPoly to the stack 
      } 
      checkRep();
  }

  /**
   * Clears the stack.
   *
   * @modifies this
   * @effects this_post = []
   */
  public void clear() {
	  while(size()>=1){// remove all RatPolys until the list is empty
		  polys.pop(); 
	  }
	  checkRep();
  }

  /**
   * Returns the RatPoly that is 'index' elements from the top of the stack.
   *
   * @param index The index of the RatPoly to be retrieved.
   * @requires index >= 0 && index < this.size()
   * @return If this = S:[p]:T where S.size() = index, then returns p.
   */
  public RatPoly getNthFromTop(int index) {
	  Stack<RatPoly> copy=new Stack<RatPoly>();
	  copy.addAll(polys);// get a copy of the original list
	  if(!((index>=0)&&(index < copy.size()))){
		  return new RatPoly();// return 0 RatPoly is index is invalid 
	  }else{
		  for(int i=0;i<index;i++){
			  copy.pop();// remove all the RatPolys before the Nth RatPoly
		  }
		  return copy.pop();// return the Nth RatPoly
	  }
  }

  /**
   * Pops two elements off of the stack, adds them, and places the result on
   * top of the stack.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 + p2
   */
  public void add() {
	  if(polys.size() >= 2){
		  RatPoly first=polys.pop();// copy the first RatPoly and then remove it from the stack
		  RatPoly second=polys.pop();//copy the second RatPoly and then remove it from the stack
		  polys.push(first.add(second));//push the sum of the first and second RatPoly to the stack
	  }
	  checkRep();
  }

  /**
   * Subtracts the top poly from the next from top poly, pops both off the
   * stack, and places the result on top of the stack.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 - p1
   */
  public void sub() {
	  if(polys.size() >= 2){
		  RatPoly first=polys.pop();// copy the first RatPoly and then remove it from the stack
		  RatPoly second=polys.pop();//copy the second RatPoly and then remove it from the stack
		  polys.push(second.sub(first));//subtract the first RatPoly from the second 
		                                //  RatPoly and push it to the stack
	  }
	  checkRep();
  }

  /**
   * Pops two elements off of the stack, multiplies them, and places the
   * result on top of the stack.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 * p2
   */
  public void mul() {
	  if(polys.size() >= 2){
		  RatPoly first=polys.pop();// copy the first RatPoly and then remove it from the stack
		  RatPoly second=polys.pop();//copy the second RatPoly and then remove it from the stack
		  polys.push(first.mul(second));//multiply the first RatPoly with the second 
                                        //  RatPoly and push it to the stack
	  }
	  checkRep();
  }

  /**
   * Divides the next from top poly by the top poly, pops both off the stack,
   * and places the result on top of the stack.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 / p1
   */
  public void div() {
	  if(polys.size() >= 2){
		  RatPoly first=polys.pop();// copy the first RatPoly and then remove it from the stack
		  RatPoly second=polys.pop();//copy the second RatPoly and then remove it from the stack
		  polys.push(second.div(first));//divide the second RatPoly with the first 
                                        //  RatPoly and push it to the stack
	  }
	  checkRep();
  }

  /**
   * Pops the top element off of the stack, differentiates it, and places the
   * result on top of the stack.
   *
   * @requires this.size() >= 1
   * @modifies this
   * @effects If this = [p1]:S then this_post = [p2]:S where p2 = derivative
   *          of p1
   */
  public void differentiate() {
	  if(polys.size() >= 1){
		  RatPoly first=polys.pop();// pop the top RatPoly		  
		  polys.push(first.differentiate());//push the derivative of the top RatPoly to the stack 
	  }
	  checkRep();
  }

  /**
   * Pops the top element off of the stack, integrates it, and places the
   * result on top of the stack.
   *
   * @requires this.size() >= 1
   * @modifies this
   * @effects If this = [p1]:S then this_post = [p2]:S where p2 = indefinite
   *          integral of p1 with integration constant 0
   */
  public void integrate() {
	  if(polys.size() >= 1){
		  RatPoly first=polys.pop();// pop the top RatPoly		  
		  polys.push(first.antiDifferentiate(new RatNum(0)));//push the anti-derivative of the top RatPoly to the stack
	  }
	  checkRep();
  }

  /**
   * Returns an iterator of the elements contained in the stack.
   *
   * @return an iterator of the elements contained in the stack in order from
   *         the bottom of the stack to the top of the stack.
   */
  @Override
  public Iterator<RatPoly> iterator() {
    return polys.iterator();
  }

  /**
   * Checks that the representation invariant holds (if any).
   */
  private void checkRep() {
    assert (polys != null) : "polys should never be null.";
    
    for (RatPoly p : polys) {
        assert (p != null) : "polys should never contain a null element.";
    }
  }
}
