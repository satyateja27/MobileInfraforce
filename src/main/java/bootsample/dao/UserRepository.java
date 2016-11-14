package bootsample.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import bootsample.model.Task;
import bootsample.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	@Query(value="select * from mobileCloud.user where email=?",nativeQuery=true)
	public User retrieveUserByEmail(String email);
	
}
