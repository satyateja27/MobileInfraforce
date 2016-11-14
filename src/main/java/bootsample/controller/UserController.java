package bootsample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import bootsample.model.User;
import bootsample.service.UserService;

@RestController
public class UserController {
	
		@Autowired
		private UserService userService;
		
		@PostMapping("/api/user/register")
		public ModelAndView registerUser(@RequestParam(value="first_name",required=true) String f_name,
				@RequestParam(value="last_name",required=true) String l_name,
				@RequestParam(value="email",required=true) String email,
				@RequestParam(value="password",required=true) String password,
				@RequestParam(value="org",required=true) String org)
		{
			ModelMap model=new ModelMap();	
			try{
			userService.register(f_name, l_name, email, password, org);
			}
			
			catch(Exception e)
			{
				model.addAttribute("error","Duplicate Id's found!");
				return new ModelAndView("SignUp",model);
				
			}
			model.addAttribute("message","User resgistered sucessfully");
			return new ModelAndView("LogIn",model);
			
		}
		
		@PostMapping("/api/user/login")
		public ModelAndView login(
				@RequestParam(value="email",required=true) String email,
				@RequestParam(value="password",required=true) String password
		)
		{
			ModelMap model=new ModelMap();	
			User user;
			
			user=userService.login(email, password);
			
			if(user==null){
				model.addAttribute("error","Invalid Login");
				return new ModelAndView("LogIn",model);
			}
			else{
				System.out.println("user:"+user.toString());
				model.addAttribute("user",user);
				return new ModelAndView("UserDashboard",model);
			}
			
		}
		
		
}
