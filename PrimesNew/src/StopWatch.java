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
	
	long waitSecs = 0;

	public StopWatch() {
		this.start = LocalDateTime.now();
	}

	public StopWatch(LocalDateTime start) {
		super();
		this.start = start;
	}

	public void start() {
		this.start = LocalDateTime.now();
	}
	
	public String getDiffStr()
	{
		return hours + " timer " + minutes%60 +" minutter " + secs%60 +" sekunder " +milisecs%1000 + " tusindedele.";
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
	}

}
