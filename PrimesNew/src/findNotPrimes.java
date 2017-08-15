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
	private ArrayList<Long> foundNotPrimesAll = new ArrayList<>();

	// we use this array that that primes to find out if there are any factors
	// the giving integer
	private ArrayList<Long> foundPrimesInRAM = new ArrayList<>();

	long from = 0;
	long to = 10;
	int cpu;

	public findNotPrimes(long from, long to, ArrayList<Long> Primes, int cpu) {
		super();
		this.from = from;
		this.to = to;
		this.foundPrimesInRAM = Primes;
		this.cpu = cpu;

	}

	public void doFindNotPrimes() {
		ExecutorService executor = Executors.newFixedThreadPool(this.cpu);
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

		executor.shutdown();
		while (!executor.isTerminated()) {
			/*
			 * System.out.println("1 : " + foundNotPrimes1);
			 * System.out.println("3 : " + foundNotPrimes3);
			 * System.out.println("5 : " + foundNotPrimes5);
			 * System.out.println("7 : " + foundNotPrimes7);
			 * System.out.println("9 : " + foundNotPrimes9);
			 */
		}
		System.out.println("Finished all threads");
	}

}
