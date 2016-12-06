package bootsample.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import bootsample.model.CostMetric;
import bootsample.model.Instance;
import bootsample.model.User;
import bootsample.service.CostMetricService;
import bootsample.service.InstanceService;
import bootsample.service.UserService;

@RestController
public class InstanceController {

	@Autowired
	private InstanceService instanceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CostMetricService costMetricService;
	
	@GetMapping("/api/instance/admin/getAllInstance")
	public ModelAndView getAllInstances(){
		
		ModelMap model=new ModelMap();
		List<Instance> instance = instanceService.getAllInstances();
		model.addAttribute("Instances",instance);
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
	
	@PostMapping("/api/instance/create/{user_id}")
	public void create(@RequestParam(value="instance_name",required=true) String instance_name,
			@RequestParam(value="num_instance",required=true) int num_instance,
			@RequestParam(value="num_CPU",required=true) int num_CPU	,
			@RequestParam(value="num_Storage",required=true) int num_Storage,
			@RequestParam(value="ami_name",required=true) String ami_name,
			@PathVariable(value="user_id") int user_id,
			HttpServletResponse response) throws IOException
	{
		ModelMap model=new ModelMap();
		User user = userService.findUserbyId(user_id);
		System.out.println(user);
		try{
		instanceService.createInstance(instance_name, num_instance, num_CPU, num_Storage, ami_name,user);
		}
		catch(Exception e)
		{
			model.addAttribute("error", "Instance creation failed");
			//return new ModelAndView("CreateInstance",model);
		}
		System.out.println("Instance created");
		model.addAttribute("sucess", "Sucessfully created instance");
		//return new ModelAndView("UserDashboard",model);
		response.sendRedirect("/user/"+user_id+"/dashBoard");
	}
	
	@PostMapping("/api/instance/startInstance")
	public ModelAndView startInstance(@RequestParam(value="instanceId",required=true) int instance_id)
	{
		Instance instance = instanceService.findOneInstance(instance_id);		
		ModelMap model=new ModelMap();	
		try{
		instanceService.startInstance(instance.getReal_instance_ids(),instance_id);
		System.out.println("After updating instance table");
		}
		catch(Exception e)
		{
			model.addAttribute("message", "Failed while starting instance");
			return new ModelAndView(new MappingJackson2JsonView(),model);
		}
		model.addAttribute("message", "Sucessfully started instance");
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
	
	@PostMapping("/api/instance/stopInstance")
	public ModelAndView stopInstance(@RequestParam(value="instanceId",required=true) int instance_id)
	{
		Instance instance = instanceService.findOneInstance(instance_id);		
		ModelMap model=new ModelMap();	
		try{
		instanceService.stopInstance(instance.getReal_instance_ids(),instance_id);
		}
		catch(Exception e)
		{
			model.addAttribute("message", "Instance deletion failed");
			return new ModelAndView(new MappingJackson2JsonView(),model);
		}
		model.addAttribute("message", "Sucessfully Stopped instance");
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
	@PostMapping("/api/instance/terminateInstance")
	public ModelAndView terminateInstance(@RequestParam(value="instanceId",required=true) int instance_id)
	{
		Instance instance = instanceService.findOneInstance(instance_id);
		ModelMap model=new ModelMap();	
		try{
		instanceService.terminateInstance(instance.getReal_instance_ids(),instance_id);
		}
		catch(Exception e)
		{
			model.addAttribute("message", "Instance deletion failed");
			return new ModelAndView(new MappingJackson2JsonView(),model);
		}
		model.addAttribute("message", "Sucessfully created instance");
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
	

	@GetMapping("/api/instance/{userId}/getAllInstances")
	public ModelAndView getAll(@PathVariable(value="userId") int userId)
	{
		ModelMap model=new ModelMap();	
		try{			
		User user = userService.findUserbyId(userId);
		List<Instance> instances = instanceService.findInstanceOfUser(user);
		model.addAttribute("instances",instances);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Error while retrieving Instances");
			return new ModelAndView("ListAllInstances",model);
			
		}
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
	
	@GetMapping("/api/instance/{userId}/findUserBill")
	public ModelAndView findUserBill(@PathVariable(value="userId") int userId)
	{
		ModelMap model=new ModelMap();	
		try{
			
		User user = userService.findUserbyId(userId);
		List<Instance> instances = instanceService.findInstanceOfUser(user);
		List<CostMetric> costMetrics=costMetricService.getAll();
		Map<Instance,Double> billMap=instanceService.findUserBill(instances,costMetrics);
		double totalBill=0.0;
		for(Map.Entry<Instance,Double> e:billMap.entrySet())
		{
			totalBill+=(Double)e.getValue();
		}
		model.addAttribute("totalBill",totalBill);
		model.addAttribute("billMap",billMap);
		System.out.println("billMap:"+billMap);
		System.out.println("totalBill:"+totalBill);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Error while calculating Bill");
			return new ModelAndView("ListAllInstances",model);
			
		}
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
	
	@GetMapping("/api/instance/getUserInstances")
	public ModelAndView getUserInstances(@RequestParam(value="user_id",required=true) int user_id)
	{
		ModelMap model=new ModelMap();	
		try{
		List<Instance> listInstance = instanceService.getUserInstances(user_id);
		model.addAttribute("instances",listInstance);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Error while retrieving user Intstances");
			return new ModelAndView("UserDashboard",model);
			
		}
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
	

}
