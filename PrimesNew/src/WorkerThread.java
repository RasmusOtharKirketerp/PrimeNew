import java.util.ArrayList;

public class WorkerThread implements Runnable {

	long from = 0;
	long to = 0;
	int div = 0;
	int factorsFoundCount = 0;
	private ArrayList<Long> foundPrimes = new ArrayList<>();
	private ArrayList<Long> foundNotPrimes = new ArrayList<>();

	public WorkerThread(ArrayList<Long> arrFP, long from, long to, int div, ArrayList<Long> notP) {
		initWorkerThread(arrFP, from, to, div, notP);
	}
	
	public void initWorkerThread(ArrayList<Long> arrFP, long from, long to, int div, ArrayList<Long> notP) {
		this.from = from;
		this.to = to;
		this.div = div;
		this.foundPrimes = arrFP;
		this.foundNotPrimes = notP;

	}

	@Override
	public void run() {
		//System.out.println(Thread.currentThread().getName() + " Starting...");
		// System.out.println(Thread.currentThread().getName() + " Handling
		// Integers ending with " + this.div);
		// System.out.println(Thread.currentThread().getName() + " from : " +
		// this.from);
		// System.out.println(Thread.currentThread().getName() + " to : " +
		// this.to);

		processCommand();
		//System.out.println(Thread.currentThread().getName() + " End.");
	}

	private void processCommand() {
		long cd = 0;
		for (long i = this.from; i <= this.to; i = i + 10) {
			cd = (int) Math.sqrt(i);
			for (int j = 0; j < foundPrimes.size(); j++) {
				// System.out.println("Exa(" + this.div + "). Integer: " + i +
				// "against : " + foundPrimes.get(j));
				long res = Math.floorMod(i, foundPrimes.get(j));
				if (res == 0) {
					// System.out.println("Factor found(" + this.div + ").
					// Integer:" + i + ". Factor : " + foundPrimes.get(j));
					this.foundNotPrimes.add(i);
					break;
					// i = this.to + 1;
				}
				if (foundPrimes.get(j) > cd)
					// stop when we get past the sqrt of i in the primes
					// array...
					break;
			}

		}
		//System.out.println("Factors found in thread(" + this.div + ") : " + factorsFoundCount);
	}

}
