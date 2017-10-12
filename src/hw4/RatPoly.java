package hw4;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <b>RatPoly</b> represents an immutable single-variate polynomial expression.
 * RatPolys are sums of RatTerms with non-negative exponents.
 * <p>
 *
 * Examples of RatPolys include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
 */
// See RatNum's documentation for a definition of "immutable".
@SuppressWarnings("nullness")
public final class RatPoly {

  /** Holds all the RatTerms in this RatPoly */
  private final List<RatTerm> terms;

  // Definitions:
  // For a RatPoly p, let C(p,i) be "p.terms.get(i).getCoeff()" and
  // E(p,i) be "p.terms.get(i).getExpt()"
  // length(p) be "p.terms.size()"
  // (These are helper functions that will make it easier for us
  // to write the remainder of the specifications. They are not
  // executable code; they just represent complex expressions in a
  // concise manner, so that we can stress the important parts of
  // other expressions in the spec rather than get bogged down in
  // the details of how we extract the coefficient for the 2nd term
  // or the exponent for the 5th term. So when you see C(p,i),
  // think "coefficient for the ith term in p".)
  //
  // Abstraction Function:
  // RatPoly, p, represents the polynomial equal to the sum of the
  // RatTerms contained in 'terms':
  // sum (0 <= i < length(p)): p.terms.get(i)
  // If there are no terms, then the RatPoly represents the zero
  // polynomial.
  //
  // Representation Invariant for every RatPoly p:
  // terms != null &&
  // forall i such that (0 <= i < length(p)), C(p,i) != 0 &&
  // forall i such that (0 <= i < length(p)), E(p,i) >= 0 &&
  // forall i such that (0 <= i < length(p) - 1), E(p,i) > E(p, i+1)
  // In other words:
  // * The terms field always points to some usable object.
  // * No term in a RatPoly has a zero coefficient.
  // * No term in a RatPoly has a negative exponent.
  // * The terms in a RatPoly are sorted in descending exponent order.
  // (It is implied that 'terms' does not contain any null elements by the
  // above
  // invariant.)

  /** A constant holding a Not-a-Number (NaN) value of type RatPoly */
  public static final RatPoly NaN = new RatPoly(RatTerm.NaN);

  /** A constant holding a zero value of type RatPoly */
  public static final RatPoly ZERO = new RatPoly();

  /**
   * @effects Constructs a new Poly, "0".
   */
  public RatPoly() {
    terms = new ArrayList<RatTerm>();
    checkRep();
  }
  

  /**
   * @param rt The single term which the new RatPoly equals.
   * @requires rt.getExpt() >= 0
   * @effects Constructs a new Poly equal to "rt". If rt.getCoeff() is zero,
   *          constructs a "0" polynomial.
   */
  public RatPoly(RatTerm rt) {
	terms = new ArrayList<RatTerm>();//create a new list to store RatTerms
    if(!rt.getCoeff().equals(new RatNum(0))){//add rt to the list if rt is not zero coefficient  
        terms.add(new RatTerm(rt.getCoeff(),rt.getExpt()));        
    }
    checkRep();
  }

  /**
   * @param c The constant in the term which the new RatPoly equals.
   * @param e The exponent in the term which the new RatPoly equals.
   * @requires e >= 0
   * @effects Constructs a new Poly equal to "c*x^e". If c is zero, constructs
   *          a "0" polynomial.
   */
  public RatPoly(int c, int e) {
	terms = new ArrayList<RatTerm>();	//create a new list to store RatTerms  
    if(c!=0){ // add a new RatTerm to the list if the coefficient is not zero
        terms.add(new RatTerm(new RatNum(c,1),e));
    }
    checkRep();
  }

  /**
   * @param rt A list of terms to be contained in the new RatPoly.
   * @requires 'rt' satisfies clauses given in rep. invariant
   * @effects Constructs a new Poly using 'rt' as part of the representation.
   *          The method does not make a copy of 'rt'.
   */
  private RatPoly(List<RatTerm> rt) {
    terms = rt;
    // The spec tells us that we don't need to make a copy of 'rt'
    checkRep();
  }

  /**
   * Returns the degree of this RatPoly.
   *
   * @requires !this.isNaN()
   * @return the largest exponent with a non-zero coefficient, or 0 if this is
   *         "0".
   */
  public int degree() {
    if(terms.size()==0){// if the Poly is 0, then return 0
    	return 0;
    }else{
    	return terms.get(0).getExpt();//else return the largest degree in the poly
    }    
  }

  /**
   * Gets the RatTerm associated with degree 'deg'
   *
   * @param deg The degree for which to find the corresponding RatTerm.
   * @requires !this.isNaN()
   * @return the RatTerm of degree 'deg'. If there is no term of degree 'deg'
   *         in this poly, then returns the zero RatTerm.
   */
  public RatTerm getTerm(int deg) {
    for(RatTerm r:terms){// if deg is in this list, return that RatTerm
    	if(r.getExpt()==deg){
    		return r;
    	}    	
    }
    checkRep();
    return new RatTerm(RatNum.ZERO, 0);//else return zero RatTerm
  }

  /**
   * Returns true if this RatPoly is not-a-number.
   *
   * @return true if and only if this has some coefficient = "NaN".
   */
  public boolean isNaN() {
	    for(RatTerm r:terms){
	    	if(r.getCoeff().isNaN()){
	    		return true;// return true if this.isNaN()
	    	}    	
	    }
	    return false;//else return false
  }

  /**
   * Scales coefficients within 'lst' by 'scalar' (helper procedure).
   *
   * @param lst The RatTerms to be scaled.
   * @param scalar the value by which to scale coefficients in lst.
   * @requires lst, scalar != null
   * @modifies lst
   * @effects Forall i s.t. 0 <= i < lst.size(), if lst.get(i) = (C . E) then
   *          lst_post.get(i) = (C*scalar . E)
   * @see hw4.RatTerm regarding (C . E) notation
   */
  private static void scaleCoeff(List<RatTerm> lst, RatNum scalar) {
//    for(int i=0;i<lst.size();i++){
//    	lst.set(i, new RatTerm(lst.get(i).getCoeff().mul(scalar),lst.get(i).getExpt()));    	
//    }
	  if(lst.size()==0||scalar.equals(new RatNum(0))){//added........
		  lst.clear();
	  }else{
		    for(int i=0;i<lst.size();i++){
		    	lst.set(i, new RatTerm(lst.get(i).getCoeff().mul(scalar),lst.get(i).getExpt()));    	
		    }
	  }
    
  }

  /**
   * Increments exponents within 'lst' by 'degree' (helper procedure).
   *
   * @param lst The RatTerms whose exponents are to be incremented.
   * @param degree the value by which to increment exponents in lst.
   * @requires lst != null
   * @modifies lst
   * @effects Forall i s.t. 0 <= i < lst.size(), if (C . E) = lst.get(i) then
   *          lst_post.get(i) = (C . E+degree)
   * @see hw4.RatTerm regarding (C . E) notation
   */
  private static void incremExpt(List<RatTerm> lst, int degree) {
    for(int i=0;i<lst.size();i++){
    	lst.set(i, new RatTerm(lst.get(i).getCoeff(),lst.get(i).getExpt()+degree));
    }
  }

  /**
   * Helper procedure: Inserts a term into a sorted sequence of terms,
   * preserving the sorted nature of the sequence. If a term with the given
   * degree already exists, adds their coefficients.
   *
   * Definitions: Let a "Sorted List<RatTerm>" be a List<RatTerm> V such
   * that [1] V is sorted in descending exponent order && [2] there are no two
   * RatTerms with the same exponent in V && [3] there is no RatTerm in V with
   * a coefficient equal to zero
   *
   * For a Sorted List<RatTerm> V and integer e, let cofind(V, e) be either
   * the coefficient for a RatTerm rt in V whose exponent is e, or zero if
   * there does not exist any such RatTerm in V. (This is like the coeff
   * function of RatPoly.) We will write sorted(lst) to denote that lst is a
   * Sorted List<RatTerm>, as defined above.
   *
   * @param lst The list into which newTerm should be inserted.
   * @param newTerm The term to be inserted into the list.
   * @requires lst != null && sorted(lst)
   * @modifies lst
   * @effects sorted(lst_post) && (cofind(lst_post,newTerm.getExpt()) =
   *          cofind(lst,newTerm.getExpt()) + newTerm.getCoeff())
   */
  private static void sortedInsert(List<RatTerm> lst, RatTerm newTerm) {
   // if newTerm coefficient is 0, then don't add to the list  
   if(!newTerm.getCoeff().equals(new RatNum(0))){
	  if(lst.size()==0){// if list is empty, add newTerm to it
	    	lst.add(newTerm); 
	  }else{//if list is not empty, then find whether newTerm has  
		int index=-1;        //the same degree in the list
		for(int i=0;i<lst.size();i++){
			  if(lst.get(i).getExpt()==newTerm.getExpt()){
				  index=i;// if same degree found, return index where 
			  }           // that degree is found
		}
		if(index!=-1){// replace the coefficient with the sum of the new 
			// coefficient at the index where same degree is found
			  RatNum co=lst.get(index).getCoeff().add(newTerm.getCoeff());
			  int de=lst.get(index).getExpt();
			  if(!co.equals(new RatNum(0))){//replace the coefficient
				  lst.set(index, new RatTerm(co,de));
			  }else{//if the sum of the coefficient is 0, then remove 
				  lst.remove(index);//RatTerm at that index
			  }
		}
		else{//if the degree is not found in the list, find the index 
		    int i=0;// where the newTerm should be inserted
		    for(RatTerm r: lst){
		    	if(r.getExpt()>newTerm.getExpt()){
		    			i++;
		    	}
		    }
			lst.add(i, newTerm);
		}		 
	  } 
	}
  }

  /**
   *
   * @return a copy of all the terms in this RatPoly.
   */  
  private ArrayList<RatTerm> getCopy(){// this is the method to get a copy 
	  ArrayList<RatTerm> temp = new ArrayList<RatTerm>();// of the RatTerms
	  for(RatTerm r:terms){//from the list in case of when changing lists
		  temp.add(new RatTerm(r.getCoeff(),r.getExpt()));// is necessary
	  }
	  checkRep();
	  return temp;
  }
  
  /**
   * Return the additive inverse of this RatPoly.
   *
   * @return a RatPoly equal to "0 - this"; if this.isNaN(), returns some r
   *         such that r.isNaN()
   */
  public RatPoly negate() {
	  ArrayList<RatTerm> temp=getCopy();//get a copy of RatTerms of the list
	  if(isNaN()){// if isNaN(),return NaN
		  return new RatPoly(RatTerm.NaN); 
	  }else{//else, scale all the coefficents by -1
		  scaleCoeff(temp, new RatNum(-1,1));//create a new list
		  checkRep();
		  return new RatPoly(temp); 
	  }

  }

  /**
   * Addition operation.
   *
   * @param p The other value to be added.
   * @requires p != null
   * @return a RatPoly, r, such that r = "this + p"; if this.isNaN() or
   *         p.isNaN(), returns some r such that r.isNaN()
   */
  public RatPoly add(RatPoly p) {
	checkRep();
	ArrayList<RatTerm> temp=p.getCopy();//get a copy of the list of the added RatPoly 
    if(isNaN()||p.isNaN()){//if this.isNaN() or p.isNaN(), return NaN()
    	return new RatPoly(RatTerm.NaN);
    }else{
    	for(RatTerm r:terms){//else, insert all the RatTerms of the original  
    		sortedInsert(temp, r);// RatPoly to the copy of the list
    	}
    	checkRep();
    	return new RatPoly(temp);
    }    
  }

  /**
   * Subtraction operation.
   *
   * @param p The value to be subtracted.
   * @requires p != null
   * @return a RatPoly, r, such that r = "this - p"; if this.isNaN() or
   *         p.isNaN(), returns some r such that r.isNaN()
   */
  public RatPoly sub(RatPoly p) {
	    checkRep();
	    if(isNaN()||p.isNaN()){//if this.isNaN() or p.isNaN(), return NaN()
	    	return new RatPoly(RatTerm.NaN);
	    }else{
	    	RatPoly temp=p;//get a copy of the RatPoly p
	    	temp=temp.negate();//negate the copy
	    	ArrayList<RatTerm> curr=temp.getCopy();//get a copy of list from the negate Poly
		    for(RatTerm r:terms){// insert all RatTerms of the original Poly to the copy list
		    	sortedInsert(curr, r);
		    }
		    checkRep();
		    return new RatPoly(curr);
	    }	    
  }

  /**
   * Multiplication operation.
   *
   * @param p The other value to be multiplied.
   * @requires p != null
   * @return a RatPoly, r, such that r = "this * p"; if this.isNaN() or
   *         p.isNaN(), returns some r such that r.isNaN()
   */
  public RatPoly mul(RatPoly p) {   
	checkRep();
    if(isNaN()||p.isNaN()){//if this.isNaN() or p.isNaN(), return NaN()
    	return new RatPoly(RatTerm.NaN); 
    }else{
    	RatPoly mulpi =new RatPoly();   //create a zero Poly  	
    	for(RatTerm r:terms){
    		ArrayList<RatTerm> temp=p.getCopy();// get a copy of the RatPoly p
    		incremExpt(temp, r.getExpt());//modify the coefficients 
    		scaleCoeff(temp, r.getCoeff()); //modify the degree   		    		
    		mulpi=mulpi.add(new RatPoly(temp));//create a new Poly and add it to the zero Poly
    	}
    	checkRep();
    	return mulpi;    	
    }
  }

  /**
   * Division operation (truncating).
   *
   * @param p The divisor.
   * @requires p != null
   * @return a RatPoly, q, such that q = "this / p"; if p = 0 or this.isNaN()
   *         or p.isNaN(), returns some q such that q.isNaN()
   *         <p>
   *
   * Division of polynomials is defined as follows: Given two polynomials u
   * and v, with v != "0", we can divide u by v to obtain a quotient
   * polynomial q and a remainder polynomial r satisfying the condition u = "q *
   * v + r", where the degree of r is strictly less than the degree of v, the
   * degree of q is no greater than the degree of u, and r and q have no
   * negative exponents.
   * <p>
   *
   * For the purposes of this class, the operation "u / v" returns q as
   * defined above.
   * <p>
   *
   * The following are examples of div's behavior: "x^3-2*x+3" / "3*x^2" =
   * "1/3*x" (with r = "-2*x+3"). "x^2+2*x+15 / 2*x^3" = "0" (with r =
   * "x^2+2*x+15"). "x^3+x-1 / x+1 = x^2-x+2 (with r = "-3").
   * <p>
   *
   * Note that this truncating behavior is similar to the behavior of integer
   * division on computers.
   */
  public RatPoly div(RatPoly p) {    
	checkRep();
    if(isNaN()||p.isNaN()||p.equals(ZERO)){// if isNaN(),or p.isNaN() or p is zero 
    	return new RatPoly(RatTerm.NaN); // return NaN
    }else if(this.degree()<p.degree()){
    	return new RatPoly();//if numerator degree is less than divisor degree, return 0
    }else{
    	RatPoly result=new RatPoly();//create a 0 poly for the result    	
    	RatPoly temp=new RatPoly(this.getCopy());//create a copy of the original poly (numerator) 
    	int equalTimes=0;
    	// Inv: result * p + temp == this_pre
    	while((temp.degree()>=p.degree())&&(temp.terms.size()!=0)){//keeping dividing until 
    		                     // numerator degree is less than divisor degree
    		if(temp.degree()==p.degree()&&temp.degree()==0){
    			equalTimes++;//keep track of times that numerator degree is same as divisor degree 
    		}
    		
    		// divide the numerator coefficient by divisor coefficient
    		RatNum r=temp.getTerm(temp.degree()).getCoeff().div(p.getTerm(p.degree()).getCoeff());
    		
    		// create a new tempory quotient poly from the new coefficient and degree
    		RatPoly rr=new RatPoly(new RatTerm(r,temp.degree()-p.degree()));
    		result=result.add(rr);// add the new poly to the result poly
    		temp=temp.sub(rr.mul(p));//modify the numerator poly by subtracting divisor poly
    		                         // times the previous tempory quotient poly 
    		if(equalTimes==1){// if numerator degree is same as divisor degree and they both are zeros,   
    			break;        // division operation can only be applied once  
    		}                 // otherwise, the loop never stops
    	}
    	checkRep();
    	return result;   

    }	  
  }

  /**
   * Return the derivative of this RatPoly.
   *
   * @return a RatPoly, q, such that q = dy/dx, where this == y. In other
   *         words, q is the derivative of this. If this.isNaN(), then return
   *         some q such that q.isNaN()
   *
   * <p>
   * The derivative of a polynomial is the sum of the derivative of each term.
   */
  public RatPoly differentiate() {
    if(isNaN()){// if this.isNaN(), return NaN
    	return new RatPoly(RatTerm.NaN);
    }else{
    	ArrayList<RatTerm> curr=new ArrayList<RatTerm>();//create a new empty list to store RatTerms
    	for(RatTerm r:terms){                                   // for all RatTerm in the original list
    		if((!r.getCoeff().equals(RatNum.ZERO))&&(r.getExpt()!=0)){//if coefficient and degree are not 
    			sortedInsert(curr,r.differentiate());//0, then added the derivative of the RatTerm to 
    		}    		                              //the list 
    	} 
    	checkRep();
    	return new RatPoly(curr);
    }
  }

  /**
   * Returns the antiderivative of this RatPoly.
   *
   * @param integrationConstant The constant of integration to use when
   *  computating the antiderivative.
   * @requires integrationConstant != null
   * @return a RatPoly, q, such that dq/dx = this and the constant of
   *         integration is "integrationConstant" In other words, q is the
   *         antiderivative of this. If this.isNaN() or
   *         integrationConstant.isNaN(), then return some q such that
   *         q.isNaN()
   *
   * <p>
   * The antiderivative of a polynomial is the sum of the antiderivative of
   * each term plus some constant.
   */
  public RatPoly antiDifferentiate(RatNum integrationConstant) {
	    if(isNaN()||integrationConstant.isNaN()){//if this.isNaN() or RatNum integrationConstant is NaN, 
	    	return new RatPoly(RatTerm.NaN);//return NaN poly
	    }else{
	    	ArrayList<RatTerm> curr=new ArrayList<RatTerm>();//create a new empty list to store RatTerms
	    	for(RatTerm r:terms){// add all the antiderivatives of all the terms in the original poly  
	    		sortedInsert(curr,r.antiDifferentiate());// to the list
	    	}
	    	if(!integrationConstant.equals(RatNum.ZERO)){//if RatNum integrationConstant is not zero,
	    		curr.add(new RatTerm(integrationConstant,0));//add it to the list
	    	}
	    	checkRep();
	    	return new RatPoly(curr);
	    }
  }

  /**
   * Returns the integral of this RatPoly, integrated from lowerBound to
   * upperBound.
   *
   * <p>
   * The Fundamental Theorem of Calculus states that the definite integral of
   * f(x) with bounds a to b is F(b) - F(a) where dF/dx = f(x) NOTE: Remember
   * that the lowerBound can be higher than the upperBound.
   *
   * @param lowerBound The lower bound of integration.
   * @param upperBound The upper bound of integration.
   * @return a double that is the definite integral of this with bounds of
   *         integration between lowerBound and upperBound. If this.isNaN(),
   *         or either lowerBound or upperBound is Double.NaN, return
   *         Double.NaN.
   */
  public double integrate(double lowerBound, double upperBound) {
    if(isNaN()||Double.isNaN(lowerBound)||Double.isNaN(upperBound)){
    	return Double.NaN;// is this.isNaN, return Double.NaN
    }else{
    	RatPoly anti=antiDifferentiate(new RatNum(0));//create antiderivative of the original polynomial
    	return anti.eval(upperBound)-anti.eval(lowerBound);
    }
  }

  /**
   * Returns the value of this RatPoly, evaluated at d.
   *
   * @param d The value at which to evaluate this polynomial.
   * @return the value of this polynomial when evaluated at 'd'. For example,
   *         "x+2" evaluated at 3 is 5, and "x^2-x" evaluated at 3 is 6. if
   *         (this.isNaN() == true), return Double.NaN
   */
  public double eval(double d) {
	  if(isNaN()||Double.isNaN(d)){// if this is NaN or number d is NaN, return Double.NaN
		  return Double.NaN; 
	  }else{
		  double sum=0;
		  for(RatTerm r:terms){//add all values evaluated at double d
			 sum+=r.eval(d); 
		  }
		  return sum;
	  }
  }

  /**
   * Returns a string representation of this RatPoly.
   *
   * @return A String representation of the expression represented by this,
   *         with the terms sorted in order of degree from highest to lowest.
   *         <p>
   *         There is no whitespace in the returned string.
   *         <p>
   *         If the polynomial is itself zero, the returned string will just
   *         be "0".
   *         <p>
   *         If this.isNaN(), then the returned string will be just "NaN"
   *         <p>
   *         The string for a non-zero, non-NaN poly is in the form
   *         "(-)T(+|-)T(+|-)...", where "(-)" refers to a possible minus
   *         sign, if needed, and "(+|-)" refer to either a plus or minus
   *         sign, as needed. For each term, T takes the form "C*x^E" or "C*x"
   *         where C > 0, UNLESS: (1) the exponent E is zero, in which case T
   *         takes the form "C", or (2) the coefficient C is one, in which
   *         case T takes the form "x^E" or "x". In cases were both (1) and
   *         (2) apply, (1) is used.
   *         <p>
   *         Valid example outputs include "x^17-3/2*x^2+1", "-x+1", "-1/2",
   *         and "0".
   *         <p>
   */
  @Override
  public String toString() {
    if (terms.size() == 0) {
      return "0";
    }
    if (isNaN()) {
      return "NaN";
    }
    StringBuilder output = new StringBuilder();
    boolean isFirst = true;
    for (RatTerm rt : terms) {
      if (isFirst) {
        isFirst = false;
        output.append(rt.toString());
      } else {
        if (rt.getCoeff().isNegative()) {
          output.append(rt.toString());
        } else {
          output.append("+" + rt.toString());
        }
      }
    }
    return output.toString();
  }

  /**
   * Builds a new RatPoly, given a descriptive String.
   *
   * @param ratStr A string of the format described in the @requires clause.
   * @requires 'polyStr' is an instance of a string with no spaces that
   *           expresses a poly in the form defined in the toString() method, except that
   *           the ordering of the terms by the degrees is not necessary.
   *           <p>
   *
   * Valid inputs include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
   *
   * @return a RatPoly p such that p.toString() = polyStr
   */
  public static RatPoly valueOf(String polyStr) {

    List<RatTerm> parsedTerms = new ArrayList<RatTerm>();

    // First we decompose the polyStr into its component terms;
    // third arg orders "+" and "-" to be returned as tokens.
    StringTokenizer termStrings = new StringTokenizer(polyStr, "+-", true);

    boolean nextTermIsNegative = false;
    while (termStrings.hasMoreTokens()) {
      String termToken = termStrings.nextToken();

      if (termToken.equals("-")) {
        nextTermIsNegative = true;
      } else if (termToken.equals("+")) {
        nextTermIsNegative = false;
      } else {
        // Not "+" or "-"; must be a term
        RatTerm term = RatTerm.valueOf(termToken);

        // at this point, coeff and expt are initialized.
        // Need to fix coeff if it was preceeded by a '-'
        if (nextTermIsNegative) {
          term = term.negate();
        }

        // accumulate terms of polynomial in 'parsedTerms'
        sortedInsert(parsedTerms, term);
      }
    }
    return new RatPoly(parsedTerms);
  }

  /**
   * Standard hashCode function.
   *
   * @return an int that all objects equal to this will also return.
   */
  @Override
  public int hashCode() {
    // all instances that are NaN must return the same hashcode;
    if (this.isNaN()) {
      return 0;
    }
    return terms.hashCode();
  }

  /**
   * Standard equality operation.
   *
   * @param obj The object to be compared for equality.
   * @return true if and only if 'obj' is an instance of a RatPoly and 'this'
   *         and 'obj' represent the same rational polynomial. Note that all
   *         NaN RatPolys are equal.
   */
  @Override
  public boolean equals(/*@Nullable*/ Object obj) {
    if (obj instanceof RatPoly) {
      RatPoly rp = (RatPoly) obj;

      // special case: check if both are NaN
      if (this.isNaN() && rp.isNaN()) {
        return true;
      } else {
        return terms.equals(rp.terms);
      }
    } else {
      return false;
    }
  }

  /**
   * Checks that the representation invariant holds (if any).
   */
  private void checkRep() {
    assert (terms != null);
    
    for (int i = 0; i < terms.size(); i++) {
        assert (!terms.get(i).getCoeff().equals(new RatNum(0))) : "zero coefficient";
        assert (terms.get(i).getExpt() >= 0) : "negative exponent";
        
        if (i < terms.size() - 1)
            assert (terms.get(i + 1).getExpt() < terms.get(i).getExpt()) : "terms out of order";
    }
  }
}
