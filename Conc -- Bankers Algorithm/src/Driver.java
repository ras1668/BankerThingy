import java.util.ArrayList;
import java.util.Random;


/** 
 * @author Ben Kantor (bdk3079@rit.edu)
 * @author Rachel Sperrazza (ras1668@rit.edu)
 */
public class Driver {
	
	final private static int numUnits = 4;    //Number of resources banker has on hand
	final private static int numClients = 4;  //Number of clients
	final private static int numRequests = 5; //Number of requests each client makes
	final private static long maxSleepMillis = 10000;
	final private static long minSleepMillis = 1000;
	
	public static void main(String[] args) {
		/**
		 * method that (a) creates a Banker object, 
		 * (b) creates several Client objects, 
		 * (c) starts all of the Clients, and 
		 * (d) waits for all the clients to complete via the instance method join( ). 
		 * With proper setting of the configuration parameters you should be able 
		 * to produce runs where a Client is delayed because granting its request would lead to an unsafe state.
		 */
		
		Banker banker = new Banker(numUnits);
		ArrayList<Client> clients = new ArrayList<Client>();
		Random random = new Random();
		for (int i = 0; i < numClients; i++){
			int claim = random.nextInt(numUnits) + 1;
			System.out.println(claim);
			String name = "Claim " + Integer.toString(i);
			Client client = new Client(name,banker,claim,numRequests,minSleepMillis,maxSleepMillis);
			clients.add(client);
		}
		for (int j = 0; j < numClients; j++){
			clients.get(j).start();
		}
		for (int k = 0; k < numClients; k++){
			try{
				clients.get(k).join();
			} catch(InterruptedException e){
				
			}
		}
		
	}
	
	/**
	 * The Driver should also contain a set of well-documented final private static int variables that specify 
	 * the configuration parameters (number of resources for the Banker, number of Clients, arguments to the 
	 * Client constructors, etc.).  I may use these to experiment with your solution to see if I can break it.
	 */

}
