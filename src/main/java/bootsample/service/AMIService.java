package bootsample.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bootsample.dao.AMIRepository;
import bootsample.model.AMI;

@Service
@Transactional
public class AMIService {
	
	@Autowired
	private final AMIRepository amiRepository;

	public AMIService(AMIRepository amiRepository) {	
		this.amiRepository = amiRepository;
	}
	
	public void registerAMI(String name,String location,String provider,String connection)
	{
		AMI ami=new AMI(name,location,provider,connection);
		amiRepository.save(ami);
	}
	
	public void deleteAMI(int id)
	{
		amiRepository.delete(id);
	}
	
	public List<AMI> getAllAMI()
	{
		return (List)amiRepository.findAll();
	}
	
}
