package a1posted;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Random;
import java.util.Arrays;
//import java.lang.Character;
public class PowerfullTester {
	
	public static void testCase(int[] first, 
			int[] second,
			int base) throws Exception{
		
		String firstAsString = new String();
		// note
		// I changed this to use BigInteger.valueOf
		// because using char does not always work on 
		// my computer
		for(int i=0; i< first.length; i++){   
		    firstAsString += new BigInteger((first[i]+"")).toString(base); 
		}
		String secondAsString = new String();
		for(int i=0; i< second.length; i++){   
		    secondAsString += new BigInteger((second[i]+"")).toString(base) ;
		}
		BigInteger firstBigInteger  = new BigInteger(firstAsString,base);  
		BigInteger secondBigInteger  = new BigInteger(secondAsString,base);  
		
		NaturalNumber firstNum  = new NaturalNumber(base,first);
		NaturalNumber secondNum = new NaturalNumber(base,second);		
		
		// note: uncomment below lines if you want the 
		// script to be verbose
		// I prefer to make it silent as it is the default
		// behavior of unix commandline programs
		System.out.println("first number:  a = " + firstAsString);
		System.out.println("second number: b = " + secondAsString);
		System.out.println("base: base = " + base);

		/*
		System.out.println("TEST FOR ADDITION (a+b) ");
		*/
		String correct = "";
		String output = "";
	       
		correct = reFormat(firstBigInteger.add(secondBigInteger),base);
		output = firstNum.add( secondNum ).toString();
		if (!correct.equals(output))
			throw new Exception(correct+":"+output); 		
		output =  secondNum.add( firstNum ).toString();
		if (!correct.equals(output))
			throw new Exception(correct+":"+output); 
		/*	
		System.out.println("TEST FOR MULTIPLICATION (a*b) ");
		*/
		correct = reFormat(firstBigInteger.multiply(secondBigInteger),base);
		output =  firstNum.multiply( secondNum ).toString() ;
		assert(correct == output);
		if (!correct.equals(output))
			throw new Exception(correct+":"+output); 
		output =  secondNum.multiply( firstNum ).toString() ;
		if (!correct.equals(output))
			throw new Exception(correct+":"+output); 
		//System.out.println("TEST FOR SUBTRACTION (a-b, where a > b)  ");
		if (firstBigInteger.compareTo(secondBigInteger)>=0){
			correct = reFormat(firstBigInteger.subtract(secondBigInteger),base);
			output = firstNum.subtract( secondNum ).toString();
                        if (!correct.equals(output))
				throw new Exception(correct+":"+output); 		
		}
		//System.out.println("TEST FOR DIVISION (a/b) ");		
		if (secondBigInteger.compareTo(BigInteger.ZERO)!=0){
			correct = reFormat(firstBigInteger.divide(secondBigInteger),base);
			output = firstNum.divide(secondNum).toString();
			if (!correct.equals(output))
				throw new Exception(correct+":"+output);
		}	
	}
			
	public static void main(String[] args) throws Exception {
		
		/* note:
		 * there are better ways to write the following 
		 * nested for loop. Such as using iterators,
		 * but many in the class haven't learned those
		 * so I'll just stick with nested loop for 
		 *  "readability"
		 */
		int[] first = new int[87];
		int[] second = new int[86];
		long computationCount = 0;
		for (int base = 2; base<=36; base++){
			for (int j = 0; j<first.length; j++){
				for (int num = 0; num<base; num++){
					first[j] = num;
				
					for (int k = 0; k<second.length; k++){
						for (int num2 = 0; num2<base; num2++){
							second[k] = num2;
							testCase(first,second,base);
							computationCount++;
						}
					}
					second = new int[86];

				}
			}
			first = new int[87];
		}
		System.out.println("Total Computation:" + computationCount);
	}
	

	/*
	 *   This method takes a BigInteger and returns a string which we
	 *   can use to compare with what NaturalNumber.toString() produces. 
	 */

	public static String reFormat(BigInteger bigInt,int base){
		String bigIntStr = bigInt.toString(base);
		String str = new String("[");		
		for (int i = 0; i < bigIntStr.length()-1; i++){
			str += Character.digit(bigIntStr.substring(i, i+1).charAt(0),base) + ", ";
		}
		str += Character.digit(bigIntStr.substring(bigIntStr.length()-1,bigIntStr.length()).charAt(0),base)  + "]";

		return str;
	}

}
