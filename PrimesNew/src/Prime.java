
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * @author Rasmus Othar
 *
 */
public class Prime {
	// Private
	private static final int sizeOfArray = 9_999_999;
	private ArrayList<Long> foundPrimesInRAM = new ArrayList<>(sizeOfArray);

	private long findMaxPrime;

	private boolean traceTime = false;
	private boolean traceDisplay = false;
	private boolean do_not_use_ram = false;
	private boolean file_loaded = false;
	public static boolean proc1 = false;
	public static boolean proc2 = false;
	private static final boolean DEBUG = false;

	private static final long traceProgressDivisor = 100;
	// private static final int traceProcessListPrimesSamples = 10000;
	private int traceProcessListPrimesSamplesCount = 1;
	private static final String DEBUG_FILE = "debug_primes.csv";
	private static String PRIMES_FILE = "primes.csv";
	private long MaxPrimeInRAM = 0;
	private Long nextPrime = 2L;

	private StopWatch sw = new StopWatch();
	private StopWatch swCommit = new StopWatch();

	public BufferedWriter out;
	FileWriter fstream = null;
	Long factorSearchWindow = 10L;

	// Getter and setters....
	public static boolean isProc1() {
		return proc1;
	}

	public static void setProc1(boolean proc1) {
		Prime.proc1 = proc1;
	}

	public static boolean isProc2() {
		return proc2;
	}

	public static void setProc2(boolean proc2) {
		Prime.proc2 = proc2;
	}

	public int getPrimes(int index) {
		return foundPrimesInRAM.get(index).intValue();
	}

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
		}
		out = new BufferedWriter(fstream);
	}

	public Prime(long maxTry) {
		this.setFindMaxPrime(maxTry);
		makeFileReady();
	}

	private void calcSearchWindow() {
		// customFormat("NextPrime : ###,###,###,###,###", nextPrime);

		factorSearchWindow = 1_000_000L;

		/*
		 * if (nextPrime > 1_000_000L) factorSearchWindow = 1_000L;
		 * 
		 * if (nextPrime > 5_000_000L) factorSearchWindow = 50_000L;
		 * 
		 * if (nextPrime > 100_000_000L) factorSearchWindow = 100_000L;
		 * 
		 * if (nextPrime > 1_000_000_000L) factorSearchWindow = 200_000L;
		 */

		// customFormat("factorSearchWindow : ###,###,###,###,###",
		// factorSearchWindow);
	}

	public boolean sampleIsPrime(long i) {
		// if (i % 10_000 == 0)
		return true;
		// else
		// return false;
	}

	public void doPrimes(boolean timetrace, boolean displaytrace) throws InterruptedException, IOException {

		System.out.println("cpu : " + Runtime.getRuntime().availableProcessors());
		long freeRAM = Runtime.getRuntime().freeMemory();
		if (DEBUG)
			PRIMES_FILE = DEBUG_FILE;
		setTraceTime(timetrace);
		setTraceDisplay(displaytrace);
		readPrimeFile();
		boolean isPrimeBoolean = false;
		boolean foundMaxPrime = false;

		calcSearchWindow();
		long nextSearchWindowMax = nextPrime + factorSearchWindow;
		findNotPrimes fnp = new findNotPrimes(nextPrime, nextSearchWindowMax, foundPrimesInRAM, 5);
		StopWatch isPrimeTimer = new StopWatch();
		while (!foundMaxPrime && nextPrime < getFindMaxPrime()) {
			fnp.initfindNotPrimes(nextPrime, nextSearchWindowMax, foundPrimesInRAM, 5);
			int nextsearchWindowCounter = 0;

			fnp.doFindNotPrimes();
			// fnp.ShowSizes();
			long i = nextPrime;
			for (; i <= nextSearchWindowMax; i++) {
				nextsearchWindowCounter++;
				nextPrime = i;

				// if (i % 2 == 0 || fnp.hasThisIntegerAFactor(0, i) != 0)

				if (sampleIsPrime(i))
					isPrimeTimer.start();

				if (i % 2 == 0)
					isPrimeBoolean = false;
				else {
					if (fnp.hasThisIntegerAFactor(1, i) > 0)
						isPrimeBoolean = false;
					else
						isPrimeBoolean = isPrime(i);

				}
				if (sampleIsPrime(i))
					isPrimeTimer.end();
				if (sampleIsPrime(i))
					isPrimeTimer.addtCounter();
				if (sampleIsPrime(i))
					System.out.println("AVG isPrime : " + isPrimeTimer.getAVGNano());

				registerThisPrime(i, isPrimeBoolean);
				if (file_loaded) {
					doCommit();
				}

				tracePrime(freeRAM, i);
				if (i >= getFindMaxPrime())
					foundMaxPrime = true;

				if (nextsearchWindowCounter >= nextSearchWindowMax) {
					break;
				}

			}
			calcSearchWindow();
			nextSearchWindowMax = nextPrime + factorSearchWindow;
			// System.out.println("Next searchwindows starts...");

		}

	}

	private void tracePrime(long freeRAM, long i) {
		// if (i % traceProcessListPrimesSamples == 0) {
		sw.end();
		if (sw.getSecs() > 29) {
			customFormat("Prime : ###,###,###,###,###", i);
			customFormat("RAM   : ###,###,###,###,### MB", freeRAM / 1000 / 1000);
			System.out.println("Using RAM : " + !do_not_use_ram);
			System.out.println("Time : " + PrintTimeStamp(LocalDateTime.now()));
			System.out.println("Diff-time : " + sw.getDiffStr());
			customFormat("Primes found since last : ###,###,###,###,###", traceProcessListPrimesSamplesCount);
			customFormat("Primes/MSec : ###,###,###,###,###", (traceProcessListPrimesSamplesCount / sw.getSecs()));
			sw.start();
			traceProcessListPrimesSamplesCount = 1;
			System.out.println();

		}
	}

	public void customFormat(String pattern, long value) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(value);
		System.out.println(output);
	}

	public String PrintTimeStamp(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		String text = date.format(formatter);
		return text;
	}

	public boolean addNewPrimeToRAM(long newP) {
		boolean retval = false;
		;
		if (do_not_use_ram == false && foundPrimesInRAM.size() < sizeOfArray) {
			foundPrimesInRAM.add(newP);
			if (newP > MaxPrimeInRAM)
				MaxPrimeInRAM = newP;
			retval = true;
		} else
			do_not_use_ram = true;

		return retval;

	}

	public void readPrimeFile() {
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

				// if (i % 50_000_000 == 0) {
				// System.out.println("Do not use RAM : " + do_not_use_ram);
				// customFormat("NextPrime in readPrime : ###,###,###,###,###",
				// nextPrime);
				// customFormat("Size of array with found primes :
				// ###,###,###,###,###", foundPrimesInRAM.size());

				// }

			}

			if (nextPrime == 2L) {
				// we are just starting from zero
				registerThisPrime(nextPrime, true);
			}
			if (nextPrime >= getFindMaxPrime()) {
				System.out.println("Primes to " + getFindMaxPrime() + " found. ");
				// System.exit(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		file_loaded = true;
		swCommit.start();
		customFormat("Size of array with found primes : ###,###,###,###,###", foundPrimesInRAM.size());
		customFormat("Done reading! Lets start! ###,###,###,###,###", nextPrime);

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
			for (int i = 0; i < foundPrimesInRAM.size() && retval == false; i++) {
				if (foundPrimesInRAM.get(i) == search) {
					//System.out.println("Found in RAM");
					retval = true;
					break;
				}
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
			if (counter > 999999)
				System.out.println("Factor found!" + getPrimeNoInArchiveLong + " is a factor to " + solveForThisPrime);
		}

		return thisHasFactors;
	}

	public boolean isPrime(long solveForThisPrime) {
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

	public boolean isPrimeThread(long solveForThisPrime) {

		boolean retval = false;

		final long cd = (int) Math.sqrt(solveForThisPrime);
		Thread odd = new Thread() {
			public void run() {
				boolean thisHasFactors = false;
				boolean stopLoop = false;
				// synchronized (arr) {
				for (int counter = 1; counter < cd || stopLoop == false; counter += 2) {
					// System.out.println("odd-thread. " + solveForThisPrime + "
					// from 1. Now = " + counter);

					thisHasFactors = false;
					if (hasFactor(solveForThisPrime, counter)) {
						stopLoop = true;
						thisHasFactors = true;
					}
					// System.out.println("odd-thread. " + solveForThisPrime + "
					// from 1 to " + cd);
				}

				// }

				setProc1(!thisHasFactors);
			}
		};
		Thread even = new Thread() {
			public void run() {
				boolean thisHasFactors = false;
				boolean stopLoop = false;

				// synchronized (arr) {
				for (int counter = 2; counter < cd || stopLoop == false; counter += 2) {
					// System.out.println("even-thread. " + solveForThisPrime +
					// " from 2. Now = " + counter);
					thisHasFactors = false;
					if (hasFactor(solveForThisPrime, counter)) {
						stopLoop = true;
						thisHasFactors = true;
					}
					// System.out.println("even-thread. " + solveForThisPrime +
					// " from 2 to " + cd);
				}
				// }

				setProc2(!thisHasFactors);
			}
		};
		odd.setName("solveForOdd");
		even.setName("solveForEven");
		odd.start();
		even.start();

		try {
			even.join();
			odd.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		retval = (isProc1() && isProc2());
		// System.out.println("isPrimeThread is finised with : " + retval);

		return retval;
	}

	private void doCommit() {
		swCommit.end();
		if (swCommit.getMinutes() > 9) {
			try {
				System.out.println("Starting Commit/restart....");
				out.close();
				makeFileReady();
				swCommit.start();
				System.out.println("Commit/restart done!");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void registerThisPrime(long solveForThisPrime, boolean thisIsPrime) throws IOException {
		String str = "";
		if (thisIsPrime) {
			traceProcessListPrimesSamplesCount++;
			str = solveForThisPrime + ";" + System.lineSeparator();
			if ((traceProcessListPrimesSamplesCount % 500_000 == 0)) {
				customFormat("Prime found         : ###,###,###,###,###", solveForThisPrime);
				customFormat("Find primes to this : ###,###,###,###,###", findMaxPrime);
				double work = (double) ((solveForThisPrime * ((double) traceProgressDivisor)) / getFindMaxPrime());
				System.out.format("Fuldført  :  %10.6f ", work);
				System.out.println(" %");
				System.out.println("Do not use RAM : " + do_not_use_ram);
				customFormat("Size of array with found primes : ###,###,###,###,###", foundPrimesInRAM.size());

			}

			addNewPrimeToRAM(solveForThisPrime);
			out.write(str);
			out.flush();
		}

	}

	public void draw(Graphics2D g2d, int maxX, int lineAtX) {
		if (!file_loaded)
			readPrimeFile();

		int zoom = 30;
		int counter = 0;
		System.out.println("Start drawing!");
		g2d.setFont(new Font("Georgia", Font.PLAIN, 14));
		for (int i = 1; i < 1600; i++) {
			// System.out.println("Drawing...." + i);
			int x = i * zoom;
			while (x < 1600*zoom) {
				counter++;
				// if (isPrimeFoundInRAM(counter) && i == 1) {
				// g2d.setColor(Color.MAGENTA);
				// g2d.fillArc(x, 500 - ((i * zoom / 2)), i * zoom, i * zoom, 0,
				// 360);
				// }
				// if (isPrimeFoundInRAM(i))
				// g2d.setColor(Color.cyan);
				// else
				g2d.setColor(Color.cyan);
				g2d.drawArc(x, lineAtX - ((i * zoom / 2)), i * zoom, i * zoom, 0, 360);
				x = x + i * zoom;
			}

			g2d.setColor(Color.white);
		}
		for (int i = 1; i < 1600; i++) {
		if (isPrimeFoundInRAM(i))
		{
			g2d.drawString(i + "", i * zoom + 1, lineAtX + 5);
			//g2d.setColor(Color.cyan);
			//g2d.drawArc(1, lineAtX - ((i * zoom / 2)), i * zoom, i * zoom, 337, 45);
			
		}
		}
		System.out.println("Done drawing!");

	}

	public void save(BufferedImage bi) {
		try {
			ImageIO.write(bi, "BMP", new File("prime_out.bmp"));
			ImageIO.write(bi, "JPG", new File("prime_out.jpg"));
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw2(Graphics2D g2d) {
		if (!file_loaded)
			readPrimeFile();

		int zoom = 2;
		for (int i = 1; 2100 < (((i * zoom / 2) + i * zoom)); i++) {
			int x = i * zoom;
			System.out.println("Drawing...." + i);
			if (isPrimeFoundInRAM(i)) {
				g2d.setColor(Color.MAGENTA);
				g2d.drawArc(900 - (x / 2), 500 - (x / 2), x, x, 0, 360);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
