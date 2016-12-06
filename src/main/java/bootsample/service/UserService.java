package bootsample.service;

import java.security.Key;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
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
	
	public List<User> findAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public void deleteUser(User user){		
		userRepository.delete(user);		
	}
	
	public void register(String f_name,String l_name,String email,String pswrd,String org) throws MySQLIntegrityConstraintViolationException, Exception
	{
		/* Encryption logic for Password */
		 String key = "Bar12345Bar12345"; // 128 bit key
        // Create key and cipher
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // encrypt the text
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(pswrd.getBytes());
        pswrd=new String(encrypted);
		User user=new User(f_name,l_name,email,pswrd,org);
		
		userRepository.save(user);
		
	}
	public User findUserbyId(int user_id){
		return userRepository.findOne(user_id);
	}
	
	public User login(String email,String pswrd)
	{
		User user=userRepository.retrieveUserByEmail(email);
		//System.out.println("user login name:"+user.getFirst_name());
		if(user==null)
			return null;
		
		/* Decryption logic for password */
		try{
		String key = "Bar12345Bar12345"; // 128 bit key
	    // Create key and cipher
	    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(pswrd.getBytes());
        String encrypted_password=new String(encrypted);
        //System.out.println("encrypted password:"+encrypted_password);
		if(user==null || !(encrypted_password.equals(user.getPassword()))){
			return null;
		}
		else
			return user;
		}
		catch(Exception e)
		{
			return null;
		}
	
	}
	
}
