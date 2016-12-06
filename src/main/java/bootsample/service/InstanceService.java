package bootsample.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bootsample.dao.InstanceRepository;
import bootsample.model.CostMetric;
import bootsample.model.Instance;
import bootsample.model.User;

@Service
@Transactional
public class InstanceService {
	
	private final InstanceRepository instanceRepository;

	private AWSServices awsServices = new AWSServices();
	
	public List<Instance> getAllInstances(){
		return (List<Instance>) instanceRepository.findAll();
	}

	public InstanceService(InstanceRepository instanceRepository) {
		this.instanceRepository = instanceRepository;
	}
	public Instance findOneInstance(int instanceId){
		return instanceRepository.findOne(instanceId);
	}
	
	public void createInstance(String instance_name,int num_instance, int num_CPU, int num_Storage, String ami_name,User user)
	{
		System.out.println(2);
		Date startDate=new Date();
		Date createTime=new Date();
		boolean terminated=false;
		boolean active=true;
		boolean stopped=false;
		List<String> list_instances = awsServices.spinInstances(num_instance, ami_name);
		System.out.println("Sucessfully created instances in AWS");
		for(String real_instance_name:list_instances)
		{
			System.out.println("instance:"+ real_instance_name);
		}
		Instance instance = new Instance(instance_name,createTime,startDate,num_instance,num_CPU,num_Storage,ami_name,user,terminated,active,stopped,list_instances);
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
	
	public Map<Instance,Double> findUserBill(List<Instance> instances,List<CostMetric> metrics)
	{	
		double cpuCost=0.0;
		double storageCost=0.0;
		double instanceBill=0.0;
		Map<Instance,Double> billMap=new HashMap<Instance,Double>();
		for(CostMetric costMetric:metrics)
		{
			if(costMetric.getName().equalsIgnoreCase("CPU"))
				cpuCost=costMetric.getCost();
			if(costMetric.getName().equalsIgnoreCase("Storage"))
				storageCost=costMetric.getCost();			
		}
		//duration for calculating the CPU Cost
		long duration=0;
		//storageTime for calculating the storage Cost
		long storageTime=0;
		
		for(Instance instance:instances)
		{
			//if instance is neither stopped nor terminated
			if(instance.getEndTime()==null)
			{
				duration = new Date().getTime() - instance.getStartDate().getTime();
				duration = TimeUnit.HOURS.convert(duration,TimeUnit.MILLISECONDS);	
				duration+=instance.getDuration();
				storageTime=new Date().getTime() - instance.getCreateTime().getTime();
				storageTime=TimeUnit.HOURS.convert(storageTime,TimeUnit.MILLISECONDS);
			}
			//If instance is stopped or terminated
			else 
			{
				duration=(long)instance.getDuration();
				storageTime=instance.getEndTime().getTime()-instance.getCreateTime().getTime();
				storageTime=TimeUnit.HOURS.convert(storageTime,TimeUnit.MILLISECONDS);
			}
			instanceBill=(duration*cpuCost*instance.getNum_CPU()+storageCost*storageTime*instance.getNum_Storage())*instance.getNum_instance();
			billMap.put(instance, instanceBill);
		}
		return billMap;
	}
	
	public void startInstance(List<String> instances,int instance_id)
	{
		System.out.println("real instances:"+instances);
		awsServices.startInstances(instances);
		System.out.println("After starting real instances");
		Instance instance = instanceRepository.findOne(instance_id);
		Date startDate = new Date();	
		instance.setStartDate(startDate);
		instance.setEndTime(null);
		instance.setInstance_stopped(false);
		instance.setInstance_active(true);
		instanceRepository.save(instance);
	}
	
	public void stopInstance(List<String> instances,int instance_id)
	{
		awsServices.stopInstances(instances);
		System.out.println("AWS instance stopped");
		Instance instance = instanceRepository.findOne(instance_id);
		Date endDate = new Date();		
		instance.setEndTime(endDate);
		long duration = endDate.getTime() - instance.getStartDate().getTime();
		duration = TimeUnit.HOURS.convert(duration,TimeUnit.MILLISECONDS);	
		duration+=instance.getDuration();
		instance.setDuration(duration);
		instance.setInstance_stopped(true);
		instance.setInstance_active(false);
		instanceRepository.save(instance);
	}
		
	public void terminateInstance(List<String> instances,int instance_id)
	{
		awsServices.terminateInstances(instances);
		System.out.println("AWS instance terminated");
		Instance instance = instanceRepository.findOne(instance_id);
		instance.setInstance_terminated(true);
		instance.setInstance_active(false);
		instance.setInstance_stopped(false);
		//Update the duration only if the current state of Instance is running
		//else update only the endtime
		if(!(instance.getEndTime()==null)){
			Date endDate = new Date();		
			instance.setEndTime(endDate);
		}else{
			Date endDate = new Date();		
			instance.setEndTime(endDate);
			long duration = endDate.getTime() - instance.getStartDate().getTime();
			duration = TimeUnit.HOURS.convert(duration,TimeUnit.MILLISECONDS);	
			duration+=instance.getDuration();
			instance.setDuration(duration);
		}
		instanceRepository.save(instance);
	}
	
	public List<Double> getHealthMetrics(List<String> instances){
		ArrayList<Double> healthMetrics = new ArrayList<Double>();
		double cpu = 0;
		double disk = 0;
		double network = 0;
		int count = 0;
		for(String instanceId : instances){
			cpu += awsServices.getCPUUtilization(instanceId);
			disk+= awsServices.getDiskWriteOpsUtilization(instanceId);
			network+=awsServices.getNetworkInUtilization(instanceId);
			count++;
		}
		if(count == 0){
			healthMetrics.add(0.0);
			healthMetrics.add(0.0);
			healthMetrics.add(0.0);
		}else{
			healthMetrics.add(cpu/count);
			healthMetrics.add(disk/count);
			healthMetrics.add(network/count);
		}
		return healthMetrics;
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
