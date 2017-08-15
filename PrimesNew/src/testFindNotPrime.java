import java.util.ArrayList;

public class testFindNotPrime {

	public static void main(String[] args) {
		ArrayList<Long> Parr = new ArrayList<>();
		StopWatch sw = new StopWatch();
		final int testPrimes = 150_900_000;
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
		findNotPrimes fnp  = new findNotPrimes(100, testPrimes, Parr,5);
		
		sw.start();
		 fnp.doFindNotPrimes();
		sw.end();
		System.out.println(sw.getDiffStr());
		
	}

}
