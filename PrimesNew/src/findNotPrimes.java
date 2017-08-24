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

	private int[] optimizeArrSearchLastPos = new int[10];

	// we use this array that that primes to find out if there are any factors
	// the giving integer
	private ArrayList<Long> foundPrimesInRAM = new ArrayList<>();

	long from = 0;
	long to = 10;
	int cpu = 5;
	private ExecutorService executorFP = Executors.newFixedThreadPool(this.cpu);
	//StopWatch time2Searcg = new StopWatch();

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

	public int getFactorCount() {
		return foundNotPrimes1.size() + foundNotPrimes3.size() + foundNotPrimes5.size() + foundNotPrimes7.size()
				+ foundNotPrimes9.size();
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
		executorFP.execute(worker1);
		Runnable worker3 = new WorkerThread(this.foundPrimesInRAM, this.from + 3, this.to, 3, this.foundNotPrimes3);
		executorFP.execute(worker3);
		Runnable worker5 = new WorkerThread(this.foundPrimesInRAM, this.from + 5, this.to, 5, this.foundNotPrimes5);
		executorFP.execute(worker5);
		Runnable worker7 = new WorkerThread(this.foundPrimesInRAM, this.from + 7, this.to, 7, this.foundNotPrimes7);
		executorFP.execute(worker7);
		Runnable worker9 = new WorkerThread(this.foundPrimesInRAM, this.from + 9, this.to, 9, this.foundNotPrimes9);
		executorFP.execute(worker9);
		//System.out.println("Count factor : " + getFactorCount());

		//executorFP.shutdown();
		 //while (!executorFP.isTerminated()) {}
		 System.out.println("All threads started...");
	}

	private void roundUp() {
		this.from = Math.round(this.from / 10);
		this.from *= 10;
	}

	public int searchArray(int istartpos, ArrayList<Long> arr, Long searchInt) {
		int retval = 0;
		for (int i = istartpos; i < arr.size(); i++) {
			if (arr.get(i).longValue() == searchInt) {
				beep(arr.get(i), searchInt);
				retval = i;
				break;
			}
		}

		return retval;

	}

	public long hasThisIntegerAFactor(int startPos, Long search) {
		int retval = 0;
		if (search % 2 == 0) {
			beep(2L, search);
			retval = 1;
		}

		//time2Searcg.start();

		long endSign = (search % 10);

		if (startPos != 0)
			startPos = optimizeArrSearchLastPos[(int) endSign];

		if (endSign == 1) {
			retval = searchArray(startPos, foundNotPrimes1, search);

		}
		if (endSign == 3)
			retval = searchArray(startPos, foundNotPrimes3, search);
		if (endSign == 5)
			retval = searchArray(startPos, foundNotPrimes5, search);
		if (endSign == 7)
			retval = searchArray(startPos, foundNotPrimes7, search);
		if (endSign == 9)
			retval = searchArray(startPos, foundNotPrimes9, search);

		if (retval > 0)
			optimizeArrSearchLastPos[(int) endSign] = retval;

		//time2Searcg.end();
		//System.out.println("Search time AVG :" + time2Searcg.getAVGNano());
		//time2Searcg.addtCounter();
		return retval;

	}

	private void beep(Long f, Long s) {

		//System.out.println("thread utilized - factor(" + s % 10 + ") found : " + s);
	}

}
