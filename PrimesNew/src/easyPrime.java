
import java.util.ArrayList;

public class easyPrime {

	public static void main(String[] args) {
		//boolean[] primes = new boolean[99_553_366_882L];
		ArrayList<Boolean> primes = new ArrayList<>();
		// global array just to keep track of it in this example,
		// but you can easily do this within another function.

		// will contain true or false values for the first 10,000 integers
		// set up the primesieve
		
		for (long i = 2; i <= 99_553_366_882L; i++) {
			primes.add(false);
		}
		primes.set(1, false); // we know 0 and 1 are not prime.
		primes.set(0, false);
		
		for (int i = 2; i < primes.size(); i++) {
			// if the number is prime,
			// then go through all its multiples and make their values false.
			if (primes.get(i)) {
				for (int j = 2; i * j < primes.size(); j++) {
					primes.set(i*j,false);
				}
			}
		}
		
		for (int i = 2; i < primes.size(); i++) {
		  if (primes.get(i))
			  System.out.println("i : " + i);
		}
		
	}

}
