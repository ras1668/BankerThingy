
public class Client extends Thread {
/*
multiple Client objects call methods in a shared Banker object
*/

	public Client(String name, Banker banker, int nUnits, int nRequests, long minSleepMillis, long maxSleepMillis) {
		/**
		 * Initialize the new Client  thread object. 
		 * The name string is passed to the super (Thread) class's constructor. 
		 * The remaining 5 arguments should be saved in instance variables of 
		 * the same names for use when the thread is started and run( ) is called.
		 */
	}
	
	public void run( ) {
		/**
		 * Register a claim for up to nUnits of resource with the banker. 
		 * Then create a loop that will be executed nRequests times; 
		 * each iteration will either request or release resources by invoking methods in the banker.
		 */
		
		/**
		 * If the banker.remaining() == 0, then the client will release all of its units, 
		 * otherwise the client will request some units.
		 */
		
		/**
		 * At the end of each loop, use Thread.sleep(millis) to sleep a random number of milliseconds 
		 * from minSleepMillis to maxSleepMillis. This will introduce another dose of non-determinism into your program.
		 */
		
		/**
		 * When the loop is done, release any units still allocated and simply return from run( ), 
		 * this will terminate the client thread.
		 */
	}
}