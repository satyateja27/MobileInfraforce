package bootsample.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

public class User implements Serializable{

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private int user_id;
	private String first_name;
	private String last_name;
	
	@Column(unique=true)
	private String email;
	private String password;
	private String org;
	public User(String first_name, String last_name, String email, String password, String org) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.org = org;
	}
	
	User()
	{
	
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", first_name=" + first_name + ", last_name=" + last_name + ", email="
				+ email + ", password=" + password + ", org=" + org + "]";
	}

	
	
}
