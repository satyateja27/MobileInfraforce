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
	
	@Autowired
	private AWSServices awsServices;

	public AMIService(AMIRepository amiRepository) {	
		this.amiRepository = amiRepository;
	}
	
	public AMI getAmiById(int id){
		return amiRepository.findOne(id);
	}
	
	public void registerAMI(String name,String location,String provider,String connection)
	{
		String imageName="ami-1480d574";
		String amiAmazonId = awsServices.copyAMI(imageName, name);
		AMI ami=new AMI(name,amiAmazonId,location,provider,connection);
		amiRepository.save(ami);
	}
	
	public void deleteAMI(AMI ami)	
	{
		String amiAmazonId = ami.getAmiAmazonId();
		awsServices.deleteAMI(amiAmazonId);
		amiRepository.delete(ami);;
	}
	
	public List<AMI> getAllAMI()
	{
		return (List)amiRepository.findAll();
	}
	
}
