package bootsample.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import bootsample.dao.UserRepository;
import bootsample.model.User;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private final UserRepository userRepository; 
	public UserService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}
	
	public void register(String f_name,String l_name,String email,String pswrd,String org) throws MySQLIntegrityConstraintViolationException, Exception
	{
		User user=new User(f_name,l_name,email,pswrd,org);
		
		userRepository.save(user);
		
	}
	public User findUserbyId(int user_id){
		return userRepository.findOne(user_id);
	}
	
	public User login(String email,String pswrd)
	{
		User user=userRepository.retrieveUserByEmail(email);
		if(user==null || !(user.getPassword().equals(pswrd))){
			return null;
		}else
		return user;		
	}
	
}
