/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;


/**
 * FCFS Task scheduling
 * @author Linda J
 */
public class MinMin {

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList1;

	/** The vmlist. */
	private static List<Vm> vmlist1;
        
	private static int reqTasks = 10000;
	private static int reqVms =100;
	
	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {

		Log.printLine("Starting Min-Min...");

	        try {
	        	// First step: Initialize the CloudSim package. It should be called
	            	// before creating any entities.
	            	int num_user = 1;   // number of cloud users
	            	Calendar calendar = Calendar.getInstance();
	            	boolean trace_flag = false;  // mean trace events

	            	// Initialize the CloudSim library
	            	CloudSim.init(num_user, calendar, trace_flag);

	            	// Second step: Create Datacenters
	            	//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
	            	@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
                        

	            	//Third step: Create Broker
	            	MinminBroker broker1 = createBroker();
	            	int brokerId1 = broker1.getId();

                        //Fourth step: Create one virtual machine
	            	vmlist1 = new VmsCreator().createRequiredVms(reqVms, brokerId1);
                        


	            	//submit vm list to the broker
	            	broker1.submitVmList(vmlist1);
                        
	            	//Fifth step: Create two Cloudlets
	            	cloudletList1 = new CloudletCreator().createUserCloudlet(reqTasks, brokerId1);

      	
	            	//submit cloudlet list to the broker
	            	broker1.submitCloudletList(cloudletList1);
                        
	            	
    	
	            	//call the scheduling function via the broker
	            	broker1.scheduleTaskstoVms();
                        
            	
	            	// Sixth step: Starts the simulation
	            	CloudSim.startSimulation();


	            	// Final step: Print results when simulation is over
	            	List<Cloudlet> newList = broker1.getCloudletReceivedList();
                        

	            	CloudSim.stopSimulation();

	            	printCloudletList(newList);

	            	Log.printLine("Min-Min finished!");
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            Log.printLine("The simulation has been terminated due to an unexpected error");
	        }
	    }

		private static Datacenter createDatacenter(String name){
			Datacenter datacenter=new DataCenterCreator().createUserDatacenter(name, reqVms);			

	        return datacenter;

	    }

	    //We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	    //to the specific rules of the simulated scenario
	    private static MinminBroker createBroker(){

	    	MinminBroker broker = null;
	        try {
			broker = new MinminBroker("Broker");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	    	return broker;
	    }

	    /**
	     * Prints the Cloudlet objects
	     * @param list  list of Cloudlets
	     */
	    private static void printCloudletList(List<Cloudlet> list) {
	        int size = list.size();
	        Cloudlet cloudlet;

	        String indent = "    ";
	        Log.printLine();
	        Log.printLine("========== OUTPUT ==========");
	        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
	                "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

	        DecimalFormat dft = new DecimalFormat("###.##");
	        for (int i = 0; i < size; i++) {
	            cloudlet = list.get(i);
	            Log.print(indent + cloudlet.getCloudletId() + indent + indent);

	            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
	                Log.print("SUCCESS");

	            	Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
	                     indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
                             indent + indent + dft.format(cloudlet.getFinishTime()));
	            }
	        }

	    }
}
