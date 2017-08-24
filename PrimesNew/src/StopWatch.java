import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class StopWatch {
	LocalDateTime start = null;
	LocalDateTime end = null;
	LocalDateTime diff = null;

	long milisecs = 0;
	long secs = 0;
	long minutes = 0;
	long hours = 0;
	long nano = 0;
	long counter = 1;
	long avgNano = 0;
	
	public long getNano() {
		return nano;
	}
	public long getAVGNano() {
		return (long) avgNano/counter;
	}
	
	public long getCounter(){
		return counter;
	}
	
	public void addtCounter(){
		avgNano = nano + avgNano;
		counter += 1;
	}

	long waitSecs = 0;

	public StopWatch() {
		this.start = LocalDateTime.now();
	}

	public StopWatch(LocalDateTime start) {
		super();
		this.start = start;
		nano = System.nanoTime();
	}

	public void start() {
		this.start = LocalDateTime.now();
		nano = System.nanoTime();
	}
	
	public String getDiffStr()
	{
		return hours + "," + minutes%60 +"," + secs%60 +"," +milisecs%1000 + "," + nano +".";
	}

	public long getMilisecs() {
		return milisecs;
	}

	public long getSecs() {
		return secs;
	}

	public long getMinutes() {
		return minutes;
	}

	public long getHours() {
		return hours;
	}
	
	public void setWaitSecs(int s)
	{
		this.waitSecs = s;
	}
	
	public boolean isWaitPassed()
	{
		boolean retval = false; 
		this.end();
		if (secs > waitSecs)
			retval = true;
		else{
			retval = false;
		}
		
		return retval;
	}

	public void end() {
		this.end = LocalDateTime.now();
		milisecs = ChronoUnit.MILLIS.between(start, end);
		secs = ChronoUnit.SECONDS.between(start, end);
		minutes = ChronoUnit.MINUTES.between(start, end);
		hours = ChronoUnit.HOURS.between(start, end);
		nano =  System.nanoTime() - nano;
	}

}
