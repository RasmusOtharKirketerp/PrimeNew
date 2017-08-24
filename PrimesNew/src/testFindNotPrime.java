import java.util.ArrayList;

public class testFindNotPrime {

	public static void main(String[] args) {
		ArrayList<Long> Parr = new ArrayList<>();
		StopWatch sw = new StopWatch();
		final long testPrimes = 400L;
		Parr.add(2L);
		Parr.add(3L);
		Parr.add(5L);
		Parr.add(7L);
		Parr.add(11L);
		Parr.add(13L);
		Parr.add(17L);
		Parr.add(19L);
		Parr.add(23L);
		Parr.add(29L);
		Parr.add(31L);
		Parr.add(37L);
		Parr.add(41L);
		Parr.add(43L);
		Parr.add(47L);
		Parr.add(53L);
		Parr.add(59L);
		Parr.add(61L);
		Parr.add(67L);
		Parr.add(71L);
		Parr.add(73L);
		Parr.add(79L);
		Parr.add(83L);
		Parr.add(89L);
		Parr.add(97L);
		System.gc();
		sw.start();
		findNotPrimes fnp = new findNotPrimes(100L, testPrimes, Parr, 5);
		fnp.doFindNotPrimes();
		fnp.ShowSizes();
		sw.end();
		System.out.println(sw.getDiffStr());

		for (Long findPrimes = 0L; findPrimes < testPrimes; findPrimes++) {
			//System.out.println("Finding primes for FindPrimes = " + findPrimes);
			if (findPrimes % 2 != 0)
				System.out.println(fnp.hasThisIntegerAFactor(1, findPrimes));
		}

		System.out.println("" + fnp.hasThisIntegerAFactor(0, 3L));

	}

}
