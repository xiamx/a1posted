package a1posted;

/*
 *   STUDENT NAME      : Meng Xuan Xia 
 *   STUDENT ID        : 260524129 
 *   
 *   If you have any issues that you wish the T.A.s to consider, then you
 *   should list them here.   If you discussed on the assignment in depth 
 *   with another student, then you should list that student's name here.   
 *   We insist that you each write your own code.   But we also expect 
 *   (and indeed encourage) that you discuss some of the technical
 *   issues and problems with each other, in case you get stuck.    
 */

import java.util.LinkedList;
import java.util.Iterator;

public class NaturalNumber  {
	
	int	base;       

	private LinkedList<Integer>  coefficients;

	public LinkedList<Integer> getCoefficients(){
		return this.coefficients;
	}

	public void setCoefficients (LinkedList<Integer> coefficients){
		this.coefficients = coefficients;
	}

	//   For any base and any positive integer, the representation of that positive 
	//   integer as a sum of powers of that base is unique.  
	//   Moreover,  we require that the "last" coefficient (namely, the coefficient
	//   of the largest power)  is non-zero.  
	//   For example,  350 is a valid representation (which we call "three hundred fifty") 
	//   but 0353 is not.  
	
	//  Constructors

	//   This constructor is called from the Tester class.
        //   Note that it reverses the order of the coefficients, namely it reads in an array
        //   and then constructs a LinkedList by successively reading the elements of the array
        //   and adding them to the front of the LinkedList.   
        //   e.g.  if the array is a[0] = 5, a[1] = 7,  then it constructs the LinkedList (7, 5).

	NaturalNumber(int base, int[] intarray) throws Exception{
		this.base = base;
		coefficients = new LinkedList<Integer>();
		for (int i=0; i < intarray.length; i++){
			if ((i >= 0) && (intarray[i] < base))
				coefficients.addFirst( new Integer( intarray[i] ) );
			else{
				System.out.println("constructor error:  all coefficients should be non-negative and less than base");
				throw new Exception();
			}
		}
	}
  
        //  This constructor acts as a helper.  It is not called from the Tester class.
	
	NaturalNumber(int base){
		this.base = base;
		coefficients = new LinkedList<Integer>();
	}

	//  This constructor acts as a helper.  It is not called from the Tester class.

	NaturalNumber(int base, int i) throws Exception{
		this.base = base;
		coefficients = new LinkedList<Integer>();
		
		if ((i >= 0) && (i < base))
			coefficients.addFirst( new Integer(i) );
		else {
			System.out.println("constructor error: all coefficients should be non-negative and less than base");
			throw new Exception();
		}
	}
	/**
	 * Remove leading zeroes of a given coefficients
	 *
	 * @param coefficients The coefficients to be trimed
	 * @return Trimed coefficients with no leading zeros
	 */ 
	private LinkedList trimLeadingZero(LinkedList coefficients){
		//obtain a working copy 
		LinkedList result = (LinkedList)coefficients.clone();
		
		//iterate and romve leading zero
		while (result.peekLast().equals(new Integer(0)) && result.size()>1){
			result.removeLast();
		}
		//handle insane case
		if (result.size()==0){
			result.add(new Integer(0));
		}
		return result;
	}

	/**
	 * Add two numbers
	 * @param second the number to be added
	 * @return result 
	 */
	public NaturalNumber add( NaturalNumber  second){
				
		//  initialize the sum as an empty list of coefficients
		
		NaturalNumber sum = new NaturalNumber( this.base );
	
		//   ADD YOUR CODE HERE
		LinkedList<Integer> sumCoefficients = new LinkedList<Integer> ();
		LinkedList<Integer> firstCoefficients = this.coefficients;
		LinkedList<Integer> secondCoefficients = second.getCoefficients();
		//   First we need to make sure both number have equal number
		//   of coefficients, we add zeros to the one of smaller size
		//

		if (firstCoefficients.size() > secondCoefficients.size()){
			while (firstCoefficients.size()>secondCoefficients.size()){
			       secondCoefficients.addLast(new Integer(0));
			}
		}else if (secondCoefficients.size() > firstCoefficients.size()){
			while (secondCoefficients.size()>firstCoefficients.size()){
				firstCoefficients.addLast(new Integer(0));
			}
		}else{}

		int i;
		int carry = 0;
		for (i =  0; i < this.coefficients.size() ; i++){
			// add two digits
			sumCoefficients.addLast(
					(firstCoefficients.get(i) + secondCoefficients.get(i) + carry) % this.base );
			
			// get the carry for next digit
			carry = ((firstCoefficients.get(i) + secondCoefficients.get(i) + carry)/ this.base);
		}

		sumCoefficients.addLast(carry);

		// Now remove any leading 0s
		//
		sumCoefficients = this.trimLeadingZero(sumCoefficients);

		sum.setCoefficients(sumCoefficients);	
		return sum;		
	}
	
	/**
	 * subtract self by a number
	 * @param second the number to subtract
	 * @return the result 
	 */
	public NaturalNumber  subtract(NaturalNumber second) throws Exception{

		//  initialize difference as an empty list of coefficients
		
		NaturalNumber  difference = new NaturalNumber(this.base);

		//   The subtract method shouldn't affect the number itself. 
		//   But the grade school algorithm sometimes requires us to "borrow" 
		//   from a higher coefficient to a lower one.   So let's just work
		//   with a copy (a clone) of 'this' so that we don't modify 'this'.   		

		NaturalNumber  first = this.clone();
		if (this.compareTo(second) < 0){
			System.out.println("Error:  subtract a-b requires that a > b");
			throw new Exception();
		}

		//   ADD YOUR CODE HERE
                LinkedList<Integer> diffCoefficients = new LinkedList<Integer> ();
                LinkedList<Integer> firstCoefficients = this.coefficients;
                LinkedList<Integer> secondCoefficients = second.getCoefficients();
                //   First we need to make sure both number have equal number
                //   of coefficients, we add zeros to the one of smaller size
                //
                if (firstCoefficients.size() > secondCoefficients.size()){
                        while (firstCoefficients.size()>secondCoefficients.size()){
                               secondCoefficients.addLast(new Integer(0));
                        }
                }else if (secondCoefficients.size() > firstCoefficients.size()){
                        while (secondCoefficients.size()>firstCoefficients.size()){
                                firstCoefficients.addLast(new Integer(0));
                        }
                }else{}

                int i;
                int borrow = 0;
		int lastborrow = 0;
                for (i =  0; i < this.coefficients.size() ; i++){
			// first check if first coefficient > second
			// otherwise borrow

			if (!(firstCoefficients.get(i)-borrow >= secondCoefficients.get(i))){
				borrow = 1;
			}else{
				borrow = 0;
			}

                        diffCoefficients.addLast(
                                        (firstCoefficients.get(i)+ this.base * borrow - secondCoefficients.get(i)-lastborrow ) % this.base );
			lastborrow = borrow;
                }


             // Now remove any leading 0s
                //
		diffCoefficients = this.trimLeadingZero(diffCoefficients);

                difference.setCoefficients(diffCoefficients);                                 
		
			
		return difference;	
	}
	/**
	 * Do a single step multiplication
	 *
	 * A single step multiplication multiply the result by a single
	 * digit and put the result on the corresponding bit location
	 *
	 * @param digit the digit to multiply
	 * @param startBit the bit will the result will be placed
	 * @return the result
	 */

	private NaturalNumber singleMultiplyStep(int digit, int startBit){
		// below is very straight forward, the variable names
		// are self explained. So, no comments
		int carry =  0;
		long product = 0;
		int prodDigit = 0;
		NaturalNumber result = new NaturalNumber(this.base);
		for (int i = 0; i< this.coefficients.size(); i++){
			product = (long)this.coefficients.get(i) * (long)digit;
			prodDigit = (int)(product % base);
			result.getCoefficients().addLast(prodDigit+carry);
			carry = (int)(product / base);
		}
		result.getCoefficients().addLast(carry);
		// Append zero bits
		for (int j = 0; j<startBit; j++){
			result.getCoefficients().addFirst(new Integer(0));
		}
		return result;	
	}	
	
	//   The multiply method should NOT be the same as what you learned in
	//   grade school since that method requires space proportional to the
	//   square of the number of coefficients in the number.   Instead,
	//   you must write a method that uses space that is proportional to
	//   the number of coefficients.    This can be done by basically 
	//   changing the order of loops, as was sketched in class.  
	//
	//  You are not allowed to simply perform addition repeatedly. 
	//  Such a method would be correct, but way too slow to be useful.

	public NaturalNumber multiply( NaturalNumber  second) throws Exception{
		
		//  initialize product as an empty list of coefficients
		
		NaturalNumber product	= new NaturalNumber( this.base );
		
		//    ADD YOUR CODE HERE
		NaturalNumber digitProduct = new NaturalNumber(this.base);
		for (int i = 0; i < second.getCoefficients().size(); i++){
			// add each single multiplication step
			
			digitProduct = this.singleMultiplyStep(second.getCoefficients().get(i),i);
			product = product.add(digitProduct);			
			
		}
		return product;
	}
	
			

	//  The divide method divides 'this' by 'second' i.e. this/second.   
	//  'this' is the "dividend", 'second' is the "divisor".
	//  This method ignores the remainder.    
	//
	//  You are not allowed to simply subtract the divisor repeatedly.
	//  This would give the correct result, but it is way too slow!
	
	public NaturalNumber divide( NaturalNumber  divisor ) throws Exception{
		
		// First check the if the variable passed is sane

		if (divisor.compareTo(new NaturalNumber(this.base, new int[] {0}))==0)
			throw new IllegalArgumentException("Divisor insane");
		
		//  initialize quotient as an empty list of coefficients
		NaturalNumber  quotient = new NaturalNumber(this.base);
		
		if (this.getCoefficients().getLast()==0){
			// if the divident is zero
			// there's nothing to calculate
			// the answer will be zero	
			quotient = new NaturalNumber(this.base, new int[]{0});
			return quotient;
		}		
		
		//First I need to get the coefficient list of the
		//divident in reverse order, 
		//that is: starting from the leading number
		//because that's how long division work
		//
		//Useless to write long reverse sequence
		//A reversed iterator should do 
		//

		Iterator<Integer> reversedDividentCoefficients = 
			this.coefficients.descendingIterator();
		int bi = 0;
		NaturalNumber q;
		int next = 0;
		NaturalNumber remainder;
		NaturalNumber biNaturalNumber = new NaturalNumber(this.base);
		NaturalNumber multipliedResult = new NaturalNumber(this.base);
		LinkedList<Integer> quotientCoefficients = new LinkedList<Integer>();
		while (reversedDividentCoefficients.hasNext()){
			// bi is the current coefficients
			
			bi = (int)reversedDividentCoefficients.next();
			biNaturalNumber =new NaturalNumber(this.base,new int[] {bi}).add(biNaturalNumber);
			//System.out.println("**Begin**biNaturalNumber"+ biNaturalNumber.toString());
			// we are gonna find a number q such that
			// divisor * q does not exeed bi
			// this number will be the first number
			// of the result.
			q = new NaturalNumber(this.base, new int []{0});
			while (divisor.multiply(q).compareTo(biNaturalNumber)<=0){
				
				q=q.add(new NaturalNumber(this.base,new int[] {1}));
				//System.out.println("*****" + q.toString());
				if ((divisor.multiply(q).compareTo(biNaturalNumber))>0){
					q=q.subtract(new NaturalNumber(this.base,new int[]{1}));	
					break;
				}
				//System.out.println("***" + q.toString());
				
			}
			// now bring the remainder into next calculation
			
			//System.out.println("**" + q.toString());
			multipliedResult = divisor.multiply(q);
			remainder = biNaturalNumber.subtract(multipliedResult);
			biNaturalNumber = remainder.multiplyByBaseToThePower(1);
			quotientCoefficients.addFirst(q.getCoefficients().getFirst());
			/*System.out.println("multipliedResult" + multipliedResult.toString());
			System.out.println("remainder" + remainder.toString());
			System.out.println("biNaturalNumber"+ biNaturalNumber.toString());
			*/
		}
		quotientCoefficients = this.trimLeadingZero(quotientCoefficients);
		quotient.setCoefficients(quotientCoefficients);
		return quotient;	
	}

	/*
	 * The methods should not alter the two numbers.  If a method require
	 * that one of the numbers be altered (e.g. borrowing in subtraction)
	 * then you need to clone the number and work with the cloned number 
	 * instead of the original. 
	 */
	
	public NaturalNumber  clone(){

		//  For technical reasons we'll discuss later, this methods 
		//  has to be declared public (not private).
		//  This detail need not concern you now.

		NaturalNumber copy = new NaturalNumber(this.base);
		for (int i=0; i < this.coefficients.size(); i++){
			copy.coefficients.addLast( new Integer( this.coefficients.get(i) ) );
		}
		return copy;
	}
	
	/*
	 *  The subtraction method computes a-b and requires that a>b.   
	 *  The a.compareTo(b) method is useful for checking this condition. 
	 *  It returns -1 if a < b,  it returns 0 if a == b,  
	 *  and it returns 1 if a > b.
	 */
	
	private int 	compareTo(NaturalNumber second){
		
		int diff = this.coefficients.size() - second.coefficients.size();
		if (diff < 0)
			return -1;
		else if (diff > 0)
			return 1;
		else { 
			boolean done = false;
			int i = this.coefficients.size() - 1;
			while (i >=0 && !done){
				diff = this.coefficients.get(i) - second.coefficients.get(i); 
				if (diff < 0){
					return -1;
				}
				else if (diff > 0)
					return 1;
				else{
					i--;
				}
			}
			return 0;
		}
	}

	/*  computes  a*base^n  where a is the number represented by 'this'
	 */
	
	private NaturalNumber multiplyByBaseToThePower(int n){
		for (int i=0; i< n; i++){
			this.coefficients.addFirst(new Integer(0));
		}
		return this;
	}

	//   This method is invoked by System.out.print.
	//   It returns the string with coefficients in the reverse order 
	//   which is the natural format for people to reading numbers,
	//   i.e..  [ a[N-1], ... a[2], a[1], a[0] ] as in the Tester class. 
	//   It does so simply by make a copy of the list with elements in 
	//   reversed order, and then printing the list using the LinkedList's
	//   toString() method.
	
	public String toString(){	
		LinkedList<Integer> reverseCoefficients = new LinkedList<Integer>();
		for (int i=0;  i < coefficients.size(); i++)
			reverseCoefficients.addFirst( coefficients.get(i));
		return reverseCoefficients.toString();		
	}

}

