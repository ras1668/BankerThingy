
public class Banker {
	/*
	Of course, in developing the Banker class you'll need to find 
	some way to map each registered thread to its current allocation 
	and remaining claim. This will also require appropriate 
	synchronization within the class which is missing from the information below.
	*/
	
	public Banker(int nUnits) {
		/**
		 * Initialize the new Banker object to manage nUnits units of resource.
		 * No threads are registered with the Banker.
		 */
	}
	
	public void setClaim(int nUnits) {
		/**
		 * The current  thread (available via static method Thread. currentThread()) 
		 * attempts to register a claim for up to nUnits units of resource.
		 * 
		 * The method calls System.exit(1) if:
		 * 		the thread already has a claim registered, or
		 * 		nUnits is not strictly positive, or
		 * 		nUnits exceeds the number of resources in the system.
		 * 
		 * Associate the thread with a current claim equal to nUnits and a current allocation of 0.
		 * 
		 * Print a message of the form:
		 *  	Thread /name/ sets a claim for /nUnits/ units.
		 * where name is the thread name (via Thread.currentThread().getName()) 
		 * and nUnits is the number of resources claimed, and return.
		 */
	}
	
	public boolean request(int nUnits) {
		/**
		 * The current thread requests nUnits more resources.
		 * 
		 * The method calls System.exit(1) if 
		 * (a) the current thread has no claim registered, 
		 * (b) nUnits is not strictly positive or 
		 * (c) nUnits exceeds the invoking thread's remaining claim.
		 * 
		 * Print the message
		 * 		Thread /name/ requests /nUnits/ units.
		 * 
		 * If allocating the resources results in a safe state,
		 * 		Print a message "Thread /name/ has /nUnits/ units allocated."
		 * 		Update the banker's state and return to the caller.
		 * 
		 * Otherwise enter a loop that
		 *      Prints the message "Thread /name/ waits."
		 *      Waits for notification of a change.
		 *      When reawakened, prints the message "Thread /name/ awakened."
		 *      If allocating the resources results in a safe state,
		 *      	prints "Thread /name/ has /nUnits/ units allocated."
		 *      	Updates the banker's state and returnz.
		 */
	}
	
	public void release(int nUnits) {
		/**
		 * The current thread releases nUnits resources.
		 * The method calls System.exit(1) if 
		 * (a) the current thread has no claim registered, 
		 * (b) nUnits is not strictly positive or 
		 * (c) nUnits exceeds the number of units allocated to the current thread.
		 * 
		 * Print the message
		 *       Thread /name/ releases /nUnits/ units.
		 *       
		 * Release nUnits of the units allocated to the current thread, 
		 * notify all waiting threads, and return.
		 */
	}
	
	public int allocated( ) {
		//Returns the number of units allocated to the current thread.
	}
	
	public int remaining( ) {
		//Returns the number of units remaining in the current thread's claim.
	}
}
