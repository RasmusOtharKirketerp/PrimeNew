
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Prime {
	// Private
	private boolean traceTime = false;
	private boolean traceDisplay = false;

	private Long nextPrime = 2L;

	private long findMaxPrime;

	long totalMemory = 0;
	long freeMemory = 0;

	// Getter and setters....
	public boolean isTraceTime() {
		return traceTime;
	}

	public void setTraceTime(boolean traceTime) {
		this.traceTime = traceTime;
	}

	public boolean isTraceDisplay() {
		return traceDisplay;
	}

	public void setTraceDisplay(boolean traceDisplay) {
		this.traceDisplay = traceDisplay;
	}

	public long getFindMaxPrime() {
		return findMaxPrime;
	}

	public void setFindMaxPrime(long findMaxPrime) {
		this.findMaxPrime = findMaxPrime;
	}

	public Prime(long maxTry) {
		this.setFindMaxPrime(maxTry);
	}

	public void doPrimes(boolean timetrace, boolean displaytrace) throws InterruptedException, IOException {
		setTraceTime(timetrace);
		setTraceDisplay(displaytrace);
		readPrimeFile();
		boolean isPrimeBoolean=false;
		for (long i = nextPrime; i <= getFindMaxPrime(); i++) {
			isPrimeBoolean = isPrime2(i);
			logThis(i, isPrimeBoolean);
		}
	}

	private void readPrimeFile() {
		BufferedReader in;
		String lineRead = "";
		String[] parts;
		String primeInString;

		//System.out.println("ReadPrimeFile...");

		try {
			FileReader fstream = new FileReader("primes.txt");
			in = new BufferedReader(fstream);
			while ((lineRead = in.readLine()) != null) {
				parts = lineRead.split(";");
				primeInString = parts[0]; // 004
				nextPrime = Long.valueOf(primeInString).longValue();
				//System.out.println("NextPrime in readPrime :" + nextPrime);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private long getNoPrimeInArchive(long search) {
		BufferedReader in;
		String lineRead = "";
		String[] parts;
		String primeNo;

		long primeNoLong;
		long retval = -1L;
		long hit = 0;

		boolean stop = false;

		//System.out.println("ReturnNoPrimeInArchive...");

		try {
			FileReader fstream = new FileReader("primes.txt");
			in = new BufferedReader(fstream);
			while ((lineRead = in.readLine()) != null && !stop) {
				parts = lineRead.split(";");
				primeNo = parts[1];
				primeNoLong = Long.valueOf(primeNo).longValue();
				if (primeNoLong > -1) 
					hit++;
				if (search == hit) {
					retval = primeNoLong;
					stop = true;
					//System.out.println("Returning prime no : " + primeNo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retval;

	}

	private long isPrimeInArchive(long l) {
		BufferedReader in;
		String lineRead = "";
		String[] parts;
		String primeInString;
		String idxInString;
		long pl;
		long il;

		long retval = 0;

		//System.out.println("Searching for primes in archive:" + l);
		try {
			FileReader fstream = new FileReader("primes.txt");
			in = new BufferedReader(fstream);
			while ((lineRead = in.readLine()) != null) {
				parts = lineRead.split(";");
				idxInString = parts[0];
				primeInString = parts[1];
				pl = Long.valueOf(primeInString).longValue();
				il = Long.valueOf(idxInString).longValue();
				if (il == l) {
					System.out.println("found: " + primeInString);
					retval = pl;
				} else
					System.out.println("On idx: " + idxInString + " was : " + pl);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("isPrimeInArchive : " + retval);
		return retval;
	}

	public String doPrimeFactor(long findOutIfThisOnIsPrime, int counter) {
		String retval = "";
		long divByThis = counter;
		double modRes = findOutIfThisOnIsPrime % divByThis;
		long divRes = 0;
		long lookUp = 0;
		if (modRes == 0) {
			divRes = findOutIfThisOnIsPrime / divByThis;
			lookUp = isPrimeInArchive(findOutIfThisOnIsPrime);
			if (lookUp == 0) {
				retval = divByThis + " x " + doPrimeFactor(divRes, 2);
				System.out.println("calc..." + findOutIfThisOnIsPrime);
			} else
				retval = divByThis + " x " + divRes + " " + retval;

		} else {
			counter++;
			retval = doPrimeFactor(findOutIfThisOnIsPrime, counter);
		}
		return retval;

	}

	public boolean hasFactor(long solveForThisPrime, int counter) {
		boolean thisHasFactors = false;

		long getPrimeNoInArchiveLong = getNoPrimeInArchive(counter);
		long res = Math.floorMod(solveForThisPrime, getPrimeNoInArchiveLong);
		if (res == 0) {
			thisHasFactors = true;
		}

		return thisHasFactors;
	}

	public boolean isPrime2(long solveForThisPrime) throws InterruptedException, IOException {
		boolean thisIsPrime = false;
		boolean thisHasFactors = false;
		long cd = (int) Math.sqrt(solveForThisPrime);
		for (int counter = 1; counter < cd; counter++) {
			thisIsPrime = true;
			thisHasFactors = false;
			if (hasFactor(solveForThisPrime, counter)) {
				counter = (int) cd;
				thisHasFactors = true;
			}
		}

		thisIsPrime = !thisHasFactors;

		return thisIsPrime;
	}

	private void logThis(long solveForThisPrime, boolean thisIsPrime) throws IOException {
		BufferedWriter out;
		String str = "";
		try {
			FileWriter fstream = new FileWriter("primes.txt", true);
			out = new BufferedWriter(fstream);
			if (!thisIsPrime)
				str = solveForThisPrime + ";" + -1 + ";" + System.lineSeparator();
			else
				str = solveForThisPrime + ";" + solveForThisPrime + ";" + System.lineSeparator();
			System.out.println(str);
			out.write(str);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}