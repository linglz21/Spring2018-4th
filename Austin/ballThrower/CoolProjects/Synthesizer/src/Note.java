
public class Note
{
	public double [] samples;

	public Note(double hz, double duration, double amplitude){
	    
        samples = new double[(int)(StdAudio.SAMPLE_RATE*duration+1)];
        int oneCycle = (int)(StdAudio.SAMPLE_RATE/hz);
        for (int i = 0; i <= oneCycle; i++){
            samples[i] = amplitude * Math.sin(2 * Math.PI * i * hz / StdAudio.SAMPLE_RATE) + Math.random()*0.7;
            samples[i] += amplitude * Math.sin(4 * Math.PI * i * hz / StdAudio.SAMPLE_RATE) + Math.random()*0.7;
            samples[i] += amplitude * Math.sin(8 * Math.PI * i * hz / StdAudio.SAMPLE_RATE) + Math.random()*0.7;
        }
        double damp = 0.5*Math.pow(Math.E , Math.log(0.001)/(hz*duration));
		for (int i=oneCycle ; i< samples.length ; i++){
		    double average = (samples[i-oneCycle] + samples[i-oneCycle+1])*damp;
			samples[i] = average;
		}

	}
}
