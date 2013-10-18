import java.util.Random;

/**
 * @author Ben Kantor (bdk3079@rit.edu)
 * @author Rachel Sperrazza (ras1668@rit.edu)
 */
public class Client extends Thread {
/*
multiple Client objects call methods in a shared Banker object
*/
	private Banker banker;
	private int nUnits;
	private int nRequests;
	private long minSleepMillis;
	private long maxSleepMillis;
	private Random random = new Random();

	public Client(String name, Banker banker, int nUnits, int nRequests, long minSleepMillis, long maxSleepMillis) {
		/**
		 * Initialize the new Client  thread object. 
		 * The name string is passed to the super (Thread) class's constructor. 
		 * The remaining 5 arguments should be saved in instance variables of 
		 * the same names for use when the thread is started and run( ) is called.
		 */
		super(name);
		this.banker = banker;
		this.nUnits = nUnits;
		this.nRequests = nRequests;
		this.minSleepMillis = minSleepMillis;
		this.maxSleepMillis = maxSleepMillis;
	}
	
	public void run( ) {
		/**
		 * Register a claim for up to nUnits of resource with the banker. 
		 * 
		 * Then create a loop that will be executed nRequests times; 
		 * each iteration will either request or release resources by invoking methods in the banker.
		 *
		 * If the banker.remaining() == 0, then the client will release all of its units, 
		 * otherwise the client will request some units.
		 * 
		 * At the end of each loop, use Thread.sleep(millis) to sleep a random number of milliseconds 
		 * from minSleepMillis to maxSleepMillis. This will introduce another dose of non-determinism into your program.
		 *
		 * When the loop is done, release any units still allocated and simply return from run( ), 
		 * this will terminate the client thread.
		 */
		banker.setClaim(nUnits);
		for(int i = 0; i < nRequests; i++){
			if (banker.remaining() == 0){
				banker.release(nUnits);
			} else{
				banker.request(nUnits);
			}
			long rand = (minSleepMillis + (random.nextInt((int)maxSleepMillis) * (maxSleepMillis-minSleepMillis)));
			try{
				System.out.println("Max: " + (maxSleepMillis-minSleepMillis) + " Min: " + minSleepMillis + "rand: " + rand);
				Thread.sleep(rand);
			} catch(InterruptedException e){}
		}
		banker.release(nUnits);
	}
}