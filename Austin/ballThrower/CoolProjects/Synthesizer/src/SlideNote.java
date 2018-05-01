
public class SlideNote
{
    public double [] samples;
    public SlideNote(double hz, double duration, double amplitude)
    {
        samples = new double[(int)(StdAudio.SAMPLE_RATE*duration+1)];
        for(int i=0 ; i<samples.length ; i++){
            samples[i] = ((samples.length-i)/(double)samples.length)*amplitude * Math.sin((1+(double)i/samples.length)*2 * Math.PI * i * hz / StdAudio.SAMPLE_RATE)+ Math.random()*0.25;
            samples[i] += ((samples.length-i)/(double)samples.length)*amplitude * Math.sin((1+(double)i/samples.length)*4 * Math.PI * i * hz / StdAudio.SAMPLE_RATE);
        }
    }
}
