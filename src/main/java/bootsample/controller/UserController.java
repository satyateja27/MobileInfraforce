package bootsample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import bootsample.service.UserService;

@RestController
public class UserController {
	
		@Autowired
		private UserService userService;
		
		@PostMapping("/api/user/register")
		public String registerUser(@RequestParam(value="first_name",required=true) String f_name,
				@RequestParam(value="last_name",required=true) String l_name,
				@RequestParam(value="email",required=true) String email,
				@RequestParam(value="password",required=true) String password,
				@RequestParam(value="org",required=true) String org)
		{
			try{
			userService.register(f_name, l_name, email, password, org);
			}
			catch(MySQLIntegrityConstraintViolationException e)
			{
				return "Duplicate Id's found!";
			}
			catch(Exception e)
			{
				return "Duplicate Id's found!";
			}
			return "User created";
		}
		
		
}
