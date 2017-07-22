import java.io.IOException;

public class Main  {
	

	public static void main(String[] args) throws InterruptedException, IOException {
		
		long findPrimes = 99_553_366_882L;

		Prime myPrime = new Prime(findPrimes);

		
		
		myPrime.doPrimes(false, false);
		System.out.println(myPrime.doPrimeFactor(findPrimes, 2));
		
	
    }
}
