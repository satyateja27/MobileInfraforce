package bootsample.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import bootsample.model.Instance;

public interface InstanceRepository extends CrudRepository<Instance, Integer> {

	@Query(value="select * from mobilecloud.instance where user_id=?",nativeQuery=true)
	public List<Instance> findUserInstances(int user_id);
}
