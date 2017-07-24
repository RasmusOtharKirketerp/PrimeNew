
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Prime {
	// Private
	private boolean traceTime = false;
	private boolean traceDisplay = false;
	private boolean traceProgress = true;
	private boolean do_not_use_ram = false;
	private static final long traceProgressDivisor = 100;
	private static final int traceProcessListPrimesSamples = 100000;
	private int traceProcessListPrimesSamplesCount = 1;

	private static final int traceProcessRAMSamples = 100000;
	private int traceProcessRAMCount = 1;

	private static final boolean DEBUG = false;

	private static final String DEBUG_FILE = "debug_primes.csv";
	private static String PRIMES_FILE = "primes.csv";

	private ArrayList<Long> foundPrimesInRAM = new ArrayList<>();
	private long MaxPrimeInRAM = 0;

	private Long nextPrime = 2L;

	private long findMaxPrime;

	public BufferedWriter out;
	FileWriter fstream = null;

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

	private void makeFileReady() {

		try {
			fstream = new FileWriter(PRIMES_FILE, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		out = new BufferedWriter(fstream);
	}

	public Prime(long maxTry) {
		this.setFindMaxPrime(maxTry);
		makeFileReady();
	}

	public void doPrimes(boolean timetrace, boolean displaytrace) throws InterruptedException, IOException {
		if (DEBUG)
			PRIMES_FILE = DEBUG_FILE;
		setTraceTime(timetrace);
		setTraceDisplay(displaytrace);
		readPrimeFile();
		boolean isPrimeBoolean = false;
		for (long i = nextPrime; i <= getFindMaxPrime(); i++) {
			if (i % 2 == 0)
				isPrimeBoolean = false;
			else
				isPrimeBoolean = isPrime2(i);
			logThis(i, isPrimeBoolean);
		}
	}

	public void customFormat(String pattern, double value) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(value);
		System.out.println(output);
	}

	public String PrintTimeStamp() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		String text = date.format(formatter);
		return text;
	}

	public boolean addNewPrimeToRAM(long newP) {
		boolean retval = false;
		long freeRAM = Runtime.getRuntime().freeMemory();
		if (!do_not_use_ram) {

			if (freeRAM > 100_000L) {
				foundPrimesInRAM.add(newP);
				if (newP > MaxPrimeInRAM)
					MaxPrimeInRAM = newP;
				retval = true;
			} else {
				do_not_use_ram = true;
			}
		}

		traceProcessRAMCount++;
		if (traceProcessRAMCount % traceProcessRAMSamples == 0) {
			// System.out.println(newP + " loaded in RAM. MaxPrime is : " +
			// MaxPrimeInRAM + ". FreeRAM : " + freeRAM);
			customFormat("Prime : ###,###,###,###,###", newP);
			customFormat("RAM   : ###,###,###,###,### MB", freeRAM / 1000 / 1000);
			System.out.println("Time : " + PrintTimeStamp());
			System.out.println();
			traceProcessListPrimesSamplesCount = 1;
		}
		return retval;
	}

	private void readPrimeFile() {
		// private
		BufferedReader in;
		String lineRead = "";
		String[] parts;
		String primeInString;

		System.out.println("ReadPrimeFile...");

		try {
			FileReader fstream = new FileReader(PRIMES_FILE);
			in = new BufferedReader(fstream);
			while ((lineRead = in.readLine()) != null) {
				parts = lineRead.split(";");
				primeInString = parts[0]; // 004
				nextPrime = Long.valueOf(primeInString).longValue();

				addNewPrimeToRAM(nextPrime);

				// System.out.println("NextPrime in readPrime :" + nextPrime);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done! NextPrime is = " + nextPrime + " lets start...");

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

		// System.out.println("ReturnNoPrimeInArchive...");
		if (search < foundPrimesInRAM.size()) {
			// System.out.println("Found in RAM!");
			retval = foundPrimesInRAM.get((int) search);
		} else {

			try {
				FileReader fstream = new FileReader(PRIMES_FILE);
				in = new BufferedReader(fstream);
				while ((lineRead = in.readLine()) != null && !stop) {
					parts = lineRead.split(";");
					primeNo = parts[0];
					primeNoLong = Long.valueOf(primeNo).longValue();
					if (primeNoLong > -1)
						hit++;
					if (search == hit) {
						retval = primeNoLong;
						stop = true;
						// System.out.println("Returning prime no : " +
						// primeNo);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return retval;

	}

	private boolean isPrimeFoundInRAM(long search) {
		boolean retval = false;
		if (search < MaxPrimeInRAM) {
			for (int i = 0; i < MaxPrimeInRAM; i++) {
				if (foundPrimesInRAM.get(i) == search)
					System.out.println("Found in RAM");
				retval = true;
			}
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

		if (isPrimeFoundInRAM(l)) {
			retval = l;

		} else {

			// System.out.println("Searching for primes in archive:" + l);
			try {
				FileReader fstream = new FileReader(PRIMES_FILE);
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
		String str = "";
		if (thisIsPrime) {
			traceProcessListPrimesSamplesCount++;
			str = solveForThisPrime + ";" + System.lineSeparator();
			if (traceProgress && (traceProcessListPrimesSamplesCount % traceProcessListPrimesSamples == 0)) {
				traceProcessListPrimesSamplesCount = 0;
				System.out.println("Prime found : " + solveForThisPrime + ". Mål : " + findMaxPrime);
				double work = (double) ((solveForThisPrime * ((double) traceProgressDivisor)) / getFindMaxPrime());
				System.out.format("Fuldført  :  %10.6f ", work);
				System.out.println(" %");
			}
			addNewPrimeToRAM(solveForThisPrime);
			out.write(str);
			out.flush();

		}

	}
}