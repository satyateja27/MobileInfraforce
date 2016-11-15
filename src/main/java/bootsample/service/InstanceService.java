package bootsample.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import bootsample.dao.InstanceRepository;
import bootsample.model.Instance;

@Service
@Transactional
public class InstanceService {
	
	private final InstanceRepository instanceRepository;

	public InstanceService(InstanceRepository instanceRepository) {
		this.instanceRepository = instanceRepository;
	}
	
	public void createInstance(String instance_name,int num_instance, int num_CPU, int num_Storage, int ami_id,int user_id)
	{
		Date startDate=new Date();
		Date createTime=new Date();
		boolean terminated=false;
		/* Call real instance creation API*/
		ArrayList<String> list_instances=new ArrayList<String>();
		instanceRepository.save(new Instance(instance_name,createTime,startDate,num_instance,num_CPU,num_Storage,ami_id,user_id,terminated,list_instances));
	}
	
	/* for admin*/
	
	public void deleteInstance(int instance_id)
	{
		instanceRepository.delete(instance_id);
	}
	
	public void stopInstance(int instance_id)
	{
		Instance instance=instanceRepository.findOne(instance_id);
		instance.setEndTime(new Date());
		System.out.println("End Time:"+instance.getEndTime());
		instance.setDuration(instance.getDuration()+(instance.getEndTime().getTime()-instance.getStartDate().getTime())/(24*60*1000));
		System.out.println("duration:"+instance.getDuration());
		instanceRepository.save(instance);
		
	}
	
	 /* For admin */
	public List<Instance> getAll()
	{
		return (List)instanceRepository.findAll();
	}
	/* For User */
	public List<Instance> getUserInstances(int user_id)
	{
		return (List)instanceRepository.findUserInstances(user_id);
	}
	
	public void terminateInstance(int instance_id)
	{
		Instance instance=instanceRepository.findOne(instance_id);
		instance.setEndTime(new Date());
		System.out.println("End Time:"+instance.getEndTime());
		instance.setInstance_terminated(Boolean.TRUE);
		instanceRepository.save(instance);
	}
	/* todo startInstance after a stop */
	
}
