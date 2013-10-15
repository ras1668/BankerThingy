import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Ben Kantor (bdk3079@rit.edu)
 * @author Rachel Sperrazza (ras1668@rit.edu)
 */
public class Banker {
	/*
	Of course, in developing the Banker class you'll need to find 
	some way to map each registered thread to its current allocation 
	and remaining claim. This will also require appropriate 
	synchronization within the class which is missing from the information below.
	*/
	
	private final AtomicInteger nUnits;
	private Hashtable<String,Integer[]> threadClaims = new Hashtable<String,Integer[]>();
	
	public Banker(int nUnits) {
		/**
		 * Initialize the new Banker object to manage nUnits units of resource.
		 * No threads are registered with the Banker.
		 */
		this.nUnits = new AtomicInteger(nUnits);
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
		if(threadClaims.containsKey(Thread.currentThread().getName()) || nUnits <= 0 || nUnits > this.nUnits.get()){
			System.exit(1);
		} else{
			String name = Thread.currentThread().getName();
			Integer[] claim = {nUnits,0};
			threadClaims.put(name,claim);
			System.out.println("Thread " + name + " sets a claim for " + nUnits + " units.");
		}
		
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
		 *      	Updates the banker's state and returns.
		 */
		String name = Thread.currentThread().getName();
		if (threadClaims.containsKey(name) == false || nUnits <= 0){
			System.exit(1);
		}
		Integer[] claim = threadClaims.get(Thread.currentThread().getName());
		if ((claim[0] - claim[1]) > nUnits){
			System.exit(1);
		}
		
		System.out.println("Thread " + name + " requests " + nUnits + " units.");
		int unitsOnHand = this.nUnits.get();
		Hashtable<String,Integer[]> threadClaimsCopy = new Hashtable<String,Integer[]>();
		threadClaimsCopy.putAll(threadClaims);	
		threadClaimsCopy.get(name)[1] += nUnits;
		while (bankersAlgorithm(unitsOnHand,threadClaimsCopy) == false){
			System.out.println("Thread " + name + " waits.");
			try{
			Thread.currentThread().wait();
			} catch(InterruptedException e){
				System.out.println("Thread" + name + " awakened");
			}
		}
		System.out.println("Thread " + name + "has " + nUnits + " units allocated.");
		this.nUnits.set(this.nUnits.get() - nUnits);
		threadClaims.get(name)[0] += nUnits;
		return true;
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
		String name = Thread.currentThread().getName();
		if (threadClaims.containsKey(name) == false || nUnits <= 0){
			System.exit(1);
		}
		Integer[] claim = threadClaims.get(name);
		if (nUnits > claim[1]){
			System.exit(1);
		}
		System.out.println("Thread " + name + " releases " + nUnits + " units.");
		this.nUnits.getAndAdd(nUnits);
		threadClaims.get(name)[0] = 0;
		notifyAll();
	}
	
	public int allocated( ) {
		//Returns the number of units allocated to the current thread.
		Integer[] claim = threadClaims.get(Thread.currentThread().getName());
		return claim[1];
	}
	
	public int remaining( ) {
		//Returns the number of units remaining in the current thread's claim.
		Integer[] claim = threadClaims.get(Thread.currentThread().getName());
		return (claim[0] - claim[1]);
	}
	
	private boolean bankersAlgorithm(int unitsOnHand, Hashtable<String,Integer[]> threadClaims){
		ArrayList<Integer[]> claimArray = new ArrayList<Integer[]>();
		for (Integer[] i: threadClaims.values()){
			int iClaim = i[0] - i[1];
			if (claimArray.size() == 0){
				claimArray.add(i);
			} else{
				for(int j = 0; j < claimArray.size(); j++){
					int jClaim = claimArray.get(j)[0] - claimArray.get(j)[1];
					if (iClaim <= jClaim){
						claimArray.add(j,i);
						break;
					}
				}
			}
		}
		for (int k = 0; k < claimArray.size(); k++){
			int remainClaim = claimArray.get(k)[0] - claimArray.get(k)[1];
			if(remainClaim > unitsOnHand){
				return false;
			}else{
				unitsOnHand = unitsOnHand + remainClaim;
			}
		}
		return true;
	}
}
