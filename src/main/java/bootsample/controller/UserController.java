package bootsample.controller;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
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

import bootsample.dao.InstanceRepository;
import bootsample.model.AMI;
import bootsample.model.CostMetric;
import bootsample.model.Instance;
import bootsample.model.User;
import bootsample.service.AMIService;
import bootsample.service.CostMetricService;
import bootsample.service.InstanceService;
import bootsample.service.UserService;

@RestController
public class UserController {
	
		private static final String Integer = null;
		@Autowired
		private UserService userService;
		
		@Autowired
		private InstanceService instanceService;
		
		@Autowired		
		private AMIService amiService;
		
		@Autowired
		private CostMetricService costMetricService;
		
		@GetMapping("/api/admin/findAllUsers")
		public ModelAndView findAllUsers(){
			ModelMap model=new ModelMap();
			List<User> user = userService.findAllUsers();
			model.addAttribute("Users",user);
			return new ModelAndView(new MappingJackson2JsonView(),model);
		}
		
		@PostMapping("/api/admin/delete")
		public ModelAndView deleteUser(@RequestParam(value="userId",required=true) int userId){
			ModelMap model=new ModelMap();
			User user = userService.findUserbyId(userId);
			userService.deleteUser(user);
			model.addAttribute("message","Delete Successful");
			return new ModelAndView(new MappingJackson2JsonView(),model);
		}
		
		@PostMapping("/api/user/register")
		public ModelAndView registerUser(@RequestParam(value="first_name",required=true) String f_name,
				@RequestParam(value="last_name",required=true) String l_name,
				@RequestParam(value="email",required=true) String email,
				@RequestParam(value="password",required=true) String password,
				@RequestParam(value="org",required=true) String org)
		{
			ModelMap model=new ModelMap();	
			try{
			System.out.println("in user controller");
			userService.register(f_name, l_name, email, password, org);
			}
			
			catch(Exception e)
			{
				System.out.println(e.toString());
				model.addAttribute("error","Duplicate Id's found!");
				return new ModelAndView("SignUp",model);
				
			}
			model.addAttribute("message","User resgistered sucessfully");
			return new ModelAndView("LogIn",model);
			
		}
		
		@GetMapping("/user/{userId}/dashBoard")		
		public ModelAndView dashBoardLoading(@PathVariable(value="userId") int userId){
			
			ModelMap map = new ModelMap();
			User user = userService.findUserbyId(userId);
			map.addAttribute("user", user);
			List<Instance> instances = instanceService.findInstanceOfUser(user);
			map.addAttribute("Instance",instances);
			return new ModelAndView("UserDashboard",map);
		}
		
		@GetMapping("/user/{userId}/createInstance")		
		public ModelAndView userInstanceCreate(@PathVariable(value="userId") int userId){
			
			ModelMap map = new ModelMap();
			User user = userService.findUserbyId(userId);
			map.addAttribute("user", user);
			return new ModelAndView("CreateInstance",map);
		}
		
		@GetMapping("/user/{userId}/monitorInstance")		
		public ModelAndView userInstanceMonitor(@PathVariable(value="userId") int userId){
			
			ModelMap map = new ModelMap();
			User user = userService.findUserbyId(userId);
			map.addAttribute("user", user);
			List<Instance> instances = instanceService.findInstanceOfUser(user);
			map.addAttribute("Instance",instances);
			return new ModelAndView("UserMonitor",map);
		}
		
		@PostMapping("/user/{user_id}/monitorInstance")		
		public ModelAndView userInstanceStats(@PathVariable(value="user_id") int userId){
			
			ModelMap map = new ModelMap();
			User user = userService.findUserbyId(userId);
			map.addAttribute("user", user);
			map.addAttribute("show",true);
			List<Instance> instances = instanceService.findInstanceOfUser(user);
			map.addAttribute("Instance",instances);
			return new ModelAndView("UserMonitor",map);
		}
		
		@GetMapping("/user/{userId}/userProfile")		
		public ModelAndView userProfile(@PathVariable(value="userId") int userId){
			
			ModelMap map = new ModelMap();
			User user = userService.findUserbyId(userId);
			map.addAttribute("user", user);
			return new ModelAndView("UserProfile",map);
		}
		
		@GetMapping("/user/{userId}/userBilling")		
		public ModelAndView userBilling(@PathVariable(value="userId") int userId){
			
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
			model.addAttribute("user", user);
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
			return new ModelAndView("UserBilling",model);
		}
		
		@GetMapping("/user/{user_id}")
		public ModelAndView getUserJson(@PathVariable(value="user_id") int userId){
			
			ModelMap map = new ModelMap();
			User user = userService.findUserbyId(userId);
			map.addAttribute("user", user);
			List<Instance> instances = instanceService.findInstanceOfUser(user);
			map.addAttribute("Instance",instances);
			return new ModelAndView(new MappingJackson2JsonView(),map);
		}
		
		@GetMapping("/admin/dashBoard")
		public ModelAndView getAdminDashboard(){
			
			List<User> users = userService.findAllUsers();
			List<Instance> instance = instanceService.getAllInstances();
			List<AMI> ami = amiService.getAllAMI();
			ModelMap map = new ModelMap();
			map.addAttribute("users", users);
			map.addAttribute("instance", instance);
			map.addAttribute("ami", ami);			
			return new ModelAndView("AdminDashboard",map);
		}
		
		@GetMapping("/admin/createImage")
		public ModelAndView createImage(){
			
			return new ModelAndView("CreateAmi");
		}
		
		@GetMapping("/admin/changeCost")
		public ModelAndView changeCost(){
			List<CostMetric> costMetric = costMetricService.getAll();
			ModelMap map = new ModelMap();
			map.addAttribute("costMetrics", costMetric);
			return new ModelAndView("UpdateMetrics",map);
		}
		
		
		@PostMapping("/api/user/login")
		public void login(
				@RequestParam(value="email",required=true) String email,
				@RequestParam(value="password",required=true) String password,
				HttpServletResponse response,
				HttpServletRequest request
		) throws IOException
		{
			ModelMap model=new ModelMap();	
			User user;
			
			user=userService.login(email, password);
			if(email.equals("admin@gmail.com") && password.equals("admin")){
				model.addAttribute("role","Admin Role Login Intiated");
				//return new ModelAndView("/admin/dashBoard",model);
				response.sendRedirect("/admin/dashBoard");
			}
			if(user==null){
				model.addAttribute("error","Invalid Login");
				//return new ModelAndView("LogIn",model);
				response.sendRedirect("/");
			}
			else{
				System.out.println("user:"+user.toString());
				model.addAttribute("user",user);
				response.sendRedirect("/user/"+user.getUser_id()+"/dashBoard");
				//return new ModelAndView("UserDashboard",model);		
			}
			
		}
		
		
}
