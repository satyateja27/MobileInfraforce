package bootsample.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bootsample.dao.CostMetricRespository;
import bootsample.model.CostMetric;

@Service
@Transactional
public class CostMetricService  {

	@Autowired
	private final CostMetricRespository costMetricRespository;

	public CostMetricService(CostMetricRespository costMetricRespository) {
		this.costMetricRespository = costMetricRespository;
	}
	
	public void addCostMetric(String name,double cost)
	{
		costMetricRespository.save(new CostMetric(name,cost));
	}
	
	public List<CostMetric> getAll()
	{
		return (List)costMetricRespository.findAll();
	}
	
	public void updateCostMetric(int id,double cost)
	{
		CostMetric costMetric=costMetricRespository.findOne(id);
		costMetric.setCost(cost);
		costMetricRespository.save(costMetric);
	}
	
}
