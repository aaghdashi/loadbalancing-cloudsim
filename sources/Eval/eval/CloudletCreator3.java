import java.util.ArrayList;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;

/**
 * CloudletCreator Creates Cloudlets as per the User Requirements.
 * More short tasks then long tasks which makes max-min outperrform min-min
 *
 */
public class CloudletCreator3 {
	
	
	//cloudlet creator
	public ArrayList<Cloudlet> createUserCloudlet(int reqTasks,int brokerId){
		ArrayList<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
		
    	//Cloudlet properties
    	int id = 0;
    	int pesNumber=1;
        long[] length = {};
    	long fileSize = 300;
    	long outputSize = 300;
    	UtilizationModel utilizationModel = new UtilizationModelFull();
    	   	
    	
    	for(id=0;id<reqTasks;id++){
    		
    		Cloudlet task = new Cloudlet(id, length[id], pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
    		task.setUserId(brokerId);
    		
    		
    		//System.out.println("Task"+id+"="+(task.getCloudletLength()));
    		
    		//add the cloudlets to the list
        	cloudletList.add(task);
    	}

    	System.out.println("SUCCESSFULLY Cloudletlist created :)");

		return cloudletList;
		
	}

}