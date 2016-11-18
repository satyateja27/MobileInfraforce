package bootsample.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AMI implements Serializable {

	@Id
	@Column(name = "ami_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ami_id;
	private String name;
	private String amiAmazonId;
	private String location;
	private String provider;
	private String connection;
	
	public AMI()
	{
		
	}
	public AMI(String name,String amiAmazonId,  String location, String provider, String connection) {
		super();
		this.name = name;
		this.location = location;
		this.amiAmazonId = amiAmazonId;
		this.provider = provider;
		this.connection = connection;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public String getAmiAmazonId() {
		return amiAmazonId;
	}
	public void setAmiAmazonId(String amiAmazonId) {
		this.amiAmazonId = amiAmazonId;
	}
	@Override
	public String toString() {
		return "AMI [ami_id=" + ami_id + ", name=" + name + ", location=" + location + ", provider=" + provider
				+ ", connection=" + connection + "]";
	}
	
}
