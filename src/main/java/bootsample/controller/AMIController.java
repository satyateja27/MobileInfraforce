package bootsample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import bootsample.dao.AMIRepository;
import bootsample.model.AMI;
import bootsample.service.AMIService;

@RestController
public class AMIController {

	@Autowired
	private AMIService amiService;
	
	@PostMapping("/api/ami/register")
	public ModelAndView registerAMI(@RequestParam(value="ami_name",required=true) String ami_name,
			@RequestParam(value="location",required=true) String location,
			@RequestParam(value="provider",required=true) String provider,
			@RequestParam(value="connection",required=true) String connection)
	{
		ModelMap model=new ModelMap();	
		try{
		amiService.registerAMI(ami_name,location,provider,connection);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Registration not succesful");
			return new ModelAndView("AMIRegister",model);
			
		}
		model.addAttribute("message","AMI resgistered sucessfully");
		return new ModelAndView("ListAMI",model);
	}
	
	@PostMapping("/api/ami/delete")
	public ModelAndView deleteAMI(@RequestParam(value="ami_id",required=true) int ami_id)
	{
		ModelMap model=new ModelMap();	
		try{
		AMI ami = amiService.getAmiById(ami_id);
		amiService.deleteAMI(ami);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Deletion of AMI not succesful");
			return new ModelAndView("ListAMI",model);
			
		}
		model.addAttribute("message","AMI resgistered sucessfully");
		return new ModelAndView("ListAMI",model);
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
