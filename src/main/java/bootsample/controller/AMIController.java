package bootsample.controller;

import java.io.IOException;
import java.util.List;

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

import bootsample.model.AMI;
import bootsample.service.AMIService;

@RestController
public class AMIController {

	@Autowired
	private AMIService amiService;
	
	@PostMapping("/api/ami/register")
	public void registerAMI(@RequestParam(value="ami_name",required=true) String ami_name,
			@RequestParam(value="location",required=true) String location,
			@RequestParam(value="provider",required=true) String provider,
			@RequestParam(value="connection",required=true) String connection,
			HttpServletResponse response) throws IOException
	{
		ModelMap model=new ModelMap();	
		try{
		amiService.registerAMI(ami_name,location,provider,connection);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Registration not succesful");
			
		}
		model.addAttribute("message","AMI resgistered sucessfully");
		response.sendRedirect("/admin/dashBoard");
	}
	
	@PostMapping("/api/ami/{ami_id}/delete")
	public void deleteAMI(@PathVariable(value="ami_id",required=true) int ami_id,
			HttpServletResponse response) throws IOException
	{
		ModelMap model=new ModelMap();	
		try{
		AMI ami = amiService.getAmiById(ami_id);
		amiService.deleteAMI(ami);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Deletion of AMI not succesful");
			
		}
		model.addAttribute("message","AMI resgistered sucessfully");
		response.sendRedirect("/admin/dashBoard");
	}
	
	@GetMapping("/api/ami/getAll")
	public ModelAndView getAll()
	{
		ModelMap model=new ModelMap();	
		try{
		List<AMI> listAMI = amiService.getAllAMI();
		model.addAttribute("amis",listAMI);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Error while retrieving AMI");
			return new ModelAndView("ListAMI",model);
			
		}
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
}
