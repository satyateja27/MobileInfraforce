package bootsample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import bootsample.model.CostMetric;
import bootsample.service.CostMetricService;

@RestController
public class CostMetricController {

	@Autowired
	private CostMetricService costService;
	
	@PostMapping("/api/cost/add")
	public void add(@RequestParam(value="metric_name",required=true) String metric_name,
			@RequestParam(value="cost",required=true) double cost)
	{
		costService.addCostMetric(metric_name, cost);
	}
	
	@PostMapping("/api/cost/update")
	public ModelAndView updateCost(@RequestParam(value="type_id",required=true) int type_id,
			@RequestParam(value="cost",required=true) double cost)
	{
		ModelMap model=new ModelMap();	
		try{
		costService.updateCostMetric(type_id, cost);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Updation of CostMetric not succesful");
			return new ModelAndView("UpdateCostMetric",model);
			
		}
		model.addAttribute("message","Cost updated sucessfully");
		return new ModelAndView("UpdateCostMetric",model);
	}
	
	@GetMapping("/api/cost/getAll")
	public ModelAndView getAll()
	{
		ModelMap model=new ModelMap();	
		try{
		List<CostMetric> listCostMetric = costService.getAll();
		model.addAttribute("metrics",listCostMetric);
		}
		
		catch(Exception e)
		{
			model.addAttribute("error","Error while retrieving Metrics");
			return new ModelAndView("UpdateCostMetric",model);
			
		}
		return new ModelAndView(new MappingJackson2JsonView(),model);
	}
	
}
