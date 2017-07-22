import java.io.IOException;

public class Main  {
	

	public static void main(String[] args) throws InterruptedException, IOException {
		
		long findPrimes = 223764L;

		Prime myPrime = new Prime(findPrimes);

		
		
		myPrime.doPrimes(false, false);
		System.out.println(myPrime.doPrimeFactor(findPrimes, 2));
		
	
    }
}
