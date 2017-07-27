import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;
 
public class Tone{
 
  public static void main(String[] args){
 
    try {
    	Tone.createTone(220, 10, 10);
        Tone.createTone(440, 10, 10);
        Tone.createTone(220, 10, 10);
    } catch (LineUnavailableException lue) {
        System.out.println(lue);
    }
  }
 
  public static void createTone(int Hertz, int volume, int hundressecs)
    throws LineUnavailableException {
 
    float rate = 2100;
    byte[] buf;
    AudioFormat audioF;
 
    buf = new byte[1];
    audioF = new AudioFormat(rate,8,1,true,false);
    //sampleRate, sampleSizeInBits,channels,signed,bigEndian
 
    SourceDataLine sourceDL = AudioSystem.getSourceDataLine(audioF);
    sourceDL = AudioSystem.getSourceDataLine(audioF);
    sourceDL.open(audioF);
    sourceDL.start();
 
    for(int i=0; i<(rate/100)*hundressecs; i++){
      double angle = (i/rate)*Hertz*2.0*Math.PI;
      buf[0]=(byte)(Math.sin(angle)*volume);
      sourceDL.write(buf,0,1);
    }
 
    sourceDL.drain();
    sourceDL.stop();
    sourceDL.close();
  }
}