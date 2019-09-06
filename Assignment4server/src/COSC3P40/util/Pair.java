package COSC3P40.util;

/** This is a generic class for pairs of objects of type X and Y.
 **
 ** @author Michael Winter
 **
 ** @version 1.00 (December 2005)
*/ 
public class Pair<X,Y> {
	
	private X first;			// first component of type X
	private Y second;			// second component of type Y
	
	/** This constructor takes a pair of objects to construct a pair. The
	 ** type of the components are supposed to be subtypes of X and Y,
	 ** respectively.
	 **
	 **	@param a	first component of type A extending X
	 ** @param b	second component of type B extending Y
	*/
	public <A extends X,B extends Y> Pair(A a,B b) {
		first = a;
		second = b;
	} // constructor
	
	/** This constructor takes a pair objects to construct a new pair. The
	 ** type of the components of the parameter pair are supposed to be 
	 ** subtypes of X and Y, respectively.
	 **
	 **	@param p	a Pair object with first component of a type extending X
	 **				and a second component of a type extending Y
	*/
	public Pair(Pair<? extends X, ? extends Y> p) {
		first = p.getFirst();
		second = p.getSecond();
	} // constructor
	
	/** A shallow clone method using the second constructor.
	 **
	 ** @return Object	a clone of a pair as an object.
	*/
	protected Object clone() {
		return new Pair<X,Y>(first,second);
	} // clone
	
	/** returns the first component
	 **
	 ** @return X	the first component
	*/
	public X getFirst() {
		return first;
	} // getFirst
	
	/** returns the second component
	 **
	 ** @return Y	the second component
	*/
	public Y getSecond() {
		return second;
	} // getSecond
	
	/** sets the first component to the given value.
	 **
	 ** @param x	the new first component
	*/
	public void setFirst(X x) {
		first = x;
	} // setFirst
	
	/** sets the second component to the given value.
	 **
	 ** @param y	the new second component
	*/
	public void setSecond(Y y) {
		second = y;
	} // setSecond
	
	/** implements an equality for a pair.
	 **
	 ** @param obj			the other object
	 ** @return boolean 	true, if this and obj are equal as a pair, false
	 **						otherwise					
	*/
	public boolean equals(Object obj) {
		if (obj instanceof Pair<?,?>) {
			Pair<?,?> p = (Pair<?,?>) obj;
			return first == p.getFirst() && second == p.getSecond();
		};
		return false;
	} // equals
	
	/** computes a hash code for a pair compatible to equals().
	 **
	 ** @return int		a hash code for this object.
	*/
	public int hashCode() {
		return first.hashCode() + second.hashCode();
	} // hashCode
	
	/** computes the usual string representation of a pair.
	 **
	 ** @return String	the usual string representation of a pair
	*/
	public String toString() {
		return "Pair(" + first + "," + second +")";
	} // toString
	
}