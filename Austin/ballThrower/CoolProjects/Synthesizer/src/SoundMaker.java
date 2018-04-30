import java.util.concurrent.Semaphore;
import java.lang.Thread;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.ListIterator;

public class SoundMaker extends Thread
{
    private Semaphore lock;
    private List<Double> samples;

    public SoundMaker()
    {
        lock = new Semaphore(1, true);
        samples = new LinkedList<Double>();
    }
    
    public void play(double [] s){
        try{
            lock.acquire();
            int size = samples.size();
            int i=0;
            ListIterator<Double> itter = samples.listIterator();
            while(itter.hasNext() && i<s.length){
                itter.set(itter.next() + s[i]);
                i++;
            }
            while(i<s.length){
                samples.add(s[i]);
                i++;
            }
            lock.release();
        }catch(InterruptedException e){
        }
    }
    
    public void run(){
        while(true){
            try{
                int size = samples.size();
                if(size>200) size = 200;
                if(size == 0){
                    Thread.sleep( 10 );
                }else{
                    lock.acquire();
                    for(int i=0 ; i<size ; i++){
                        StdAudio.play(samples.remove(0));
                    }
                    lock.release();
                }
            }catch(InterruptedException e){
            }
        }
    }

}
