import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// This is a controller for handling the process of using threading and search for factors/not primes
// as the main process isPrime can skip.

public class findNotPrimes {

	// This array contains integers that are NOT primes and can be skipped
	private ArrayList<Long> foundNotPrimes1 = new ArrayList<>();
	private ArrayList<Long> foundNotPrimes3 = new ArrayList<>();
	private ArrayList<Long> foundNotPrimes5 = new ArrayList<>();
	private ArrayList<Long> foundNotPrimes7 = new ArrayList<>();
	private ArrayList<Long> foundNotPrimes9 = new ArrayList<>();

	// we use this array that that primes to find out if there are any factors
	// the giving integer
	private ArrayList<Long> foundPrimesInRAM = new ArrayList<>();

	long from = 0;
	long to = 10;
	int cpu = 5;
	private ExecutorService executor = Executors.newFixedThreadPool(this.cpu);
	public findNotPrimes(long from, long to, ArrayList<Long> Primes, int cpu) {
           initfindNotPrimes(from, to, Primes, cpu);
	}

	public void initfindNotPrimes(long from, long to, ArrayList<Long> Primes, int cpu) {
		this.from = from;
		this.to = to;
		this.foundPrimesInRAM = Primes;
		this.cpu = cpu;
		roundUp();

	}

	public String printPrime(long value) {
		DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,###");
		String output = myFormatter.format(value);
		return output;
	}

	public void showInitParm() {
		System.out.println("From " + printPrime(this.from) + " to " + printPrime(this.to));
	}

	public void ShowSizes() {
		showInitParm();
		System.out.println("Size of factors-arrays is : ");
		System.out.println("1 - " + foundNotPrimes1.size() + " from " + foundNotPrimes1.get(0) + " to "
				+ foundNotPrimes1.get(foundNotPrimes1.size() - 1));
		System.out.println("3 - " + foundNotPrimes3.size() + " from " + foundNotPrimes3.get(0) + " to "
				+ foundNotPrimes3.get(foundNotPrimes3.size() - 1));
		System.out.println("5 - " + foundNotPrimes5.size() + " from " + foundNotPrimes5.get(0) + " to "
				+ foundNotPrimes5.get(foundNotPrimes5.size() - 1));
		System.out.println("7 - " + foundNotPrimes7.size() + " from " + foundNotPrimes7.get(0) + " to "
				+ foundNotPrimes7.get(foundNotPrimes7.size() - 1));
		System.out.println("9 - " + foundNotPrimes9.size() + " from " + foundNotPrimes9.get(0) + " to "
				+ foundNotPrimes9.get(foundNotPrimes9.size() - 1));
	}

	public void doFindNotPrimes() {
		
		Runnable worker1 = new WorkerThread(this.foundPrimesInRAM, this.from + 1, this.to, 1, this.foundNotPrimes1);
		executor.execute(worker1);
		Runnable worker3 = new WorkerThread(this.foundPrimesInRAM, this.from + 3, this.to, 3, this.foundNotPrimes3);
		executor.execute(worker3);
		Runnable worker5 = new WorkerThread(this.foundPrimesInRAM, this.from + 5, this.to, 5, this.foundNotPrimes5);
		executor.execute(worker5);
		Runnable worker7 = new WorkerThread(this.foundPrimesInRAM, this.from + 7, this.to, 7, this.foundNotPrimes7);
		executor.execute(worker7);
		Runnable worker9 = new WorkerThread(this.foundPrimesInRAM, this.from + 9, this.to, 9, this.foundNotPrimes9);
		executor.execute(worker9);

		//executor.shutdown();
		//while (!executor.isTerminated()) {} 
		//System.out.println("Finished all threads");
	}

	private void roundUp() {
		this.from = Math.round(this.from / 10);
		this.from *= 10;
	}

	public long hasThisIntegerAFactor(int startPos, Long search) {
		Long retval = 0L;
		if (search % 2 == 0) {
			beep(2L, search);
			retval = search;
		}
		for (int i = startPos; i < foundNotPrimes1.size(); i++) {
			if (foundNotPrimes1.get(i).longValue() == search) {
				beep(foundNotPrimes1.get(i), search);
				retval = search;
				break;
			}
		}
		for (int i = startPos; i < foundNotPrimes3.size(); i++) {
			if (foundNotPrimes3.get(i).longValue() == search) {
				beep(foundNotPrimes3.get(i), search);
				retval = search;
				break;
			}

		}
		for (int i = startPos; i < foundNotPrimes5.size(); i++) {
			if (foundNotPrimes5.get(i).longValue() == search) {
				beep(foundNotPrimes5.get(i), search);
				retval = search;
				break;
			}

		}
		for (int i = startPos; i < foundNotPrimes7.size(); i++) {
			if (foundNotPrimes7.get(i).longValue() == search) {
				beep(foundNotPrimes7.get(i), search);
				retval = search;
				break;
			}

		}
		for (int i = startPos; i < foundNotPrimes9.size(); i++) {
			if (foundNotPrimes9.get(i).longValue() == search) {
				beep(foundNotPrimes9.get(i), search);
				retval = search;
				break;
			}

		}
		 //System.out.println("hasThisIntegerAFactor = " + retval);
		return retval;

	}

	private void beep(Long f, Long s) {

		//System.out.println("thread utilized - factor(" + f + ") found : " + s);
	}

}
