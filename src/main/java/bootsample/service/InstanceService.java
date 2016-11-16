package bootsample.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bootsample.dao.InstanceRepository;
import bootsample.model.Instance;
import bootsample.model.User;

@Service
@Transactional
public class InstanceService {
	
	private final InstanceRepository instanceRepository;

	private AWSServices awsServices = new AWSServices();

	public InstanceService(InstanceRepository instanceRepository) {
		this.instanceRepository = instanceRepository;
	}
	public Instance findOneInstance(int instanceId){
		return instanceRepository.findOne(instanceId);
	}
	
	public void createInstance(String instance_name,int num_instance, int num_CPU, int num_Storage, String ami_id,User user_id)
	{
		System.out.println(2);
		Date startDate=new Date();
		Date createTime=new Date();
		boolean terminated=false;				
		List<String> list_instances = awsServices.spinInstances(num_instance, ami_id);
		Instance instance = new Instance(instance_name,startDate,createTime,num_instance,num_CPU,num_Storage,ami_id,user_id,terminated,list_instances);
		instanceRepository.save(instance);
	}
	
	public void deleteInstance(int instance_id)
	{
		instanceRepository.delete(instance_id);
	}
	
	public List<Instance> findInstanceOfUser(User user){
		
	List<Instance> userInstance = new ArrayList<>();
	for(Instance instance : instanceRepository.findAll()){
		if(instance.getUser().equals(user)){
			userInstance.add(instance);
		}
	}
	return userInstance;
	}
	
	public void startInstance(List<String> instances,int instance_id)
	{
		awsServices.startInstances(instances);
		Instance instance = instanceRepository.findOne(instance_id);
		Date startDate = new Date();	
		instance.setStartDate(startDate);
		instance.setEndTime(null);
		instanceRepository.save(instance);
	}
	
	public void stopInstance(List<String> instances,int instance_id)
	{
		awsServices.stopInstances(instances);
		Instance instance = instanceRepository.findOne(instance_id);
		Date endDate = new Date();		
		instance.setEndTime(endDate);
		long duration = endDate.getTime() - instance.getStartDate().getTime();
		duration = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS);	
		duration+=instance.getDuration();
		instance.setDuration(duration);
		instanceRepository.save(instance);
	}
		
	public void terminateInstance(List<String> instances,int instance_id)
	{
		awsServices.terminateInstances(instances);
		Instance instance = instanceRepository.findOne(instance_id);
		instance.setInstance_terminated(true);
		if(!(instance.getEndTime()==null)){
			Date endDate = new Date();		
			instance.setEndTime(endDate);
			long duration = endDate.getTime() - instance.getStartDate().getTime();
			duration = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS);	
			duration+=instance.getDuration();
			instance.setDuration(duration);
		}else{
			Date endDate = new Date();		
			instance.setEndTime(endDate);
		}
		instanceRepository.save(instance);
	}

	public List<Instance> getAll()
	{
		return (List)instanceRepository.findAll();
	}
	
	public List<Instance> getUserInstances(int user_id)
	{
		return (List)instanceRepository.findUserInstances(user_id);
	}
}
