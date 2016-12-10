package bootsample.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bootsample.model.Task;
import bootsample.service.TaskService;

@Controller
public class MainController {
	
	@Autowired
	private TaskService taskService;

	@GetMapping("/")
	public String home(HttpServletRequest request){
		return "LogIn";
	}
	
	@GetMapping("/signup")
	public String allTasks(HttpServletRequest request){
		return "SignUp";
	}
	
	@GetMapping("/user")
	public String newTask(HttpServletRequest request){
		return "UserDashboard";
	}
	
	@GetMapping("/user/create")
	public String saveTask(HttpServletRequest request){
		return "CreateInstance";
	}
	
	@GetMapping("/user/profile")
	public String updateTask(HttpServletRequest request){
		return "UserProfile";
	}
	
	@GetMapping("/admin")
	public String admin(HttpServletRequest request){
		return "AdminDashboard";
	}
	
	@GetMapping("/admin/ami")
	public String adminAMI(HttpServletRequest request){
		return "CreateAmi";
	}
	
	@GetMapping("/admin/metrics")
	public String adminMetrics(HttpServletRequest request){
		return "UpdateMetrics";
	}
	
	@GetMapping("/delete-task")
	public String deleteTask(@RequestParam int id, HttpServletRequest request){
		taskService.delete(id);
		request.setAttribute("task", taskService.findAll());
		request.setAttribute("mode", "MODE_TASKS");
		return "index";
	}
}
