package bootsample.dao;

import org.springframework.data.repository.CrudRepository;

import bootsample.model.CostMetric;

public interface CostMetricRespository extends CrudRepository<CostMetric, Integer> {

}
