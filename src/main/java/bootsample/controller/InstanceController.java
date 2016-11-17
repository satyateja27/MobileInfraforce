package bootsample.controller;

import java.util.List;

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
import bootsample.service.InstanceService;
import bootsample.service.UserService;

@RestController
public class InstanceController {

	@Autowired
	private InstanceService instanceService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/api/instance/admin/getAllInstance")
	public ModelAndView getAllInstances(){
		
		ModelMap model=new ModelMap();
		List<Instance> instance = instanceService.getAllInstances();
		model.addAttribute("Instances",instance);
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
	
	@PostMapping("/api/instance/create")
	public ModelAndView create(@RequestParam(value="instance_name",required=true) String instance_name,
			@RequestParam(value="num_instance",required=true) int num_instance,
			@RequestParam(value="num_CPU",required=true) int num_CPU	,
			@RequestParam(value="num_Storage",required=true) int num_Storage,
			@RequestParam(value="ami_id",required=true) String ami_id,
			@RequestParam(value="user_id",required=true) int user_id)
	{
		ModelMap model=new ModelMap();
		User user = userService.findUserbyId(user_id);
		System.out.println(user);
		try{
		instanceService.createInstance(instance_name, num_instance, num_CPU, num_Storage, ami_id,user);
		}
		catch(Exception e)
		{
			model.addAttribute("error", "Instance creation failed");
			return new ModelAndView("CreateInstance",model);
		}
		System.out.println("Instance created");
		model.addAttribute("sucess", "Sucessfully created instance");
		return new ModelAndView("UserDashboard",model);
	}
	
	@PostMapping("/api/instance/{userId}/stopInstance")
	public ModelAndView stopInstance(@PathVariable(value="userId") int userId,
			@RequestParam(value="instanceId",required=true) int instance_id)
	{
		User user = userService.findUserbyId(userId);
		Instance instance = instanceService.findOneInstance(instance_id);		
		ModelMap model=new ModelMap();	
		try{
		instanceService.stopInstance(instance.getReal_instance_ids(),instance_id);
		}
		catch(Exception e)
		{
			model.addAttribute("error", "Instance deletion failed");
			return new ModelAndView("CreateInstance",model);
		}
		model.addAttribute("sucess", "Sucessfully created instance");
		return new ModelAndView("UserDashboard",model);
	}
	@PostMapping("/api/instance/{userId}/terminateInstance")
	public ModelAndView terminateInstance(@PathVariable(value="userId") int userId,
			@RequestParam(value="instanceId",required=true) int instance_id)
	{
		User user = userService.findUserbyId(userId);
		Instance instance = instanceService.findOneInstance(instance_id);
		ModelMap model=new ModelMap();	
		try{
		instanceService.terminateInstance(instance.getReal_instance_ids(),instance_id);
		}
		catch(Exception e)
		{
			model.addAttribute("error", "Instance deletion failed");
			return new ModelAndView("CreateInstance",model);
		}
		model.addAttribute("sucess", "Sucessfully created instance");
		return new ModelAndView("UserDashboard",model);
	}
	
	/* For admin to see the list of all instances*/
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
			model.addAttribute("error","Error while retrieving Intstances");
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
	

	@PostMapping("/api/instance/stopInstance")
	public ModelAndView stopInstance(@RequestParam(value="instance_id",required=true) int instance_id)
	{
		ModelMap model=new ModelMap();	
		try{
		//instanceService.stopInstance(instance_id);
		}
		catch(Exception e)
		{
			model.addAttribute("error", "Error while stopping instance");
			return new ModelAndView("UserDasboard",model);
		}
		model.addAttribute("sucess", "Sucessfully stopped instance");
		return new ModelAndView("UserDashboard",model);
	}
}
