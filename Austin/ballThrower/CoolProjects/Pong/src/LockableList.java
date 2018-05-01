import java.util.ArrayList;
import java.util.concurrent.Semaphore;

// An extension to ArrayList that allows us to acquire a lock.
// This is useful for any multi threaded application
public class LockableList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;
	Semaphore lock;
	
	public LockableList(){
		lock = new Semaphore(1);
	}
	
	public void acquire(){
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void release(){
		lock.release();
	}
	
}
