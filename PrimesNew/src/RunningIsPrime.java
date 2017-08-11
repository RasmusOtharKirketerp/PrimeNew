import java.util.ArrayList;

public class RunningIsPrime extends Thread {
	boolean thisIsPrime = false;
	boolean thisHasFactors = false;
	long solveForThisPrime = 0;
	long cd = 0;
	String processID = "";

	@Override
	public void run() {
		super.run();
		cd = (int) Math.sqrt(solveForThisPrime);

		if (processID == "1") {
			for (int counter = 1; counter < cd; counter+=2) {
					thisIsPrime = true;
					thisHasFactors = false;
					if (hasFactor(solveForThisPrime, counter)) {
						counter = (int) cd;
						thisHasFactors = true;
					}
			}

			thisIsPrime = !thisHasFactors;
		}
		if (processID == "2") {
			for (int counter = 2; counter < cd; counter+=2) {
					thisIsPrime = true;
					thisHasFactors = false;
					if (hasFactor(solveForThisPrime, counter)) {
						counter = (int) cd;
						thisHasFactors = true;
					}
			}

			thisIsPrime = !thisHasFactors;
		}
		//System.out.println("Thread " + processID + " ended with : " + thisIsPrime);
		
	}

	public boolean hasFactor(long solveForThisPrime, int counter) {
		boolean thisHasFactors = false;

		// System.out.println("Process("+getName()+") : " + solveForThisPrime +
		// " counter : " + counter);
		long getPrimeNoInArchiveLong = foundPrimesInRAM.get((counter));
		long res = Math.floorMod(solveForThisPrime, getPrimeNoInArchiveLong);
		if (res == 0) {
			thisHasFactors = true;
		}

		return thisHasFactors;
	}

	public RunningIsPrime(String pid, long solveForThisPrime, ArrayList<Long> al) {
		this.processID = pid;
		this.solveForThisPrime = solveForThisPrime;
		this.foundPrimesInRAM = al;
	}

}
