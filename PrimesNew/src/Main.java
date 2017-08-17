import java.io.IOException;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {

		long findPrimes = 99_553_366_882L;
		 //long findPrimes = 100;
		 Prime myPrime = new Prime(findPrimes);
		 myPrime.doPrimes(false, false);
		

	}
}
