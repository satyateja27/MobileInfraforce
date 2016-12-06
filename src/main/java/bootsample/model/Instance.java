package bootsample.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Instance implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int instance_id;	
	private String instance_name;	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	private Boolean instance_terminated;
	private Boolean instance_active;
	private Boolean instance_stopped;
	private double duration;
	private int num_instance;
	private int num_CPU;
	private int num_Storage;
	private String ami_name;
	@ElementCollection
	private List<String> real_instance_ids;
	@ManyToOne(targetEntity=User.class)
	private User user;
	public Instance()
	{
		
	}
	public Instance(String instance_name,Date createTime, Date startDate, int num_instance, int num_CPU, int num_Storage, String ami_name,User user,boolean terminated,boolean active,boolean stopped,List<String> list_instances) {
		super();
		this.instance_name = instance_name;
		this.createTime= createTime;
		this.startDate = startDate;
		this.num_instance = num_instance;
		this.num_CPU = num_CPU;
		this.num_Storage = num_Storage;
		this.ami_name = ami_name;
		this.user=user;
		this.instance_terminated=Boolean.FALSE;
		this.instance_active=Boolean.TRUE;
		this.instance_stopped=Boolean.FALSE;
		this.real_instance_ids=list_instances;
	}
	public int getInstance_id() {
		return instance_id;
	}
	public void setInstance_id(int instance_id) {
		this.instance_id = instance_id;
	}
	public String getInstance_name() {
		return instance_name;
	}
	public void setInstance_name(String instance_name) {
		this.instance_name = instance_name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getInstance_terminated() {
		return instance_terminated;
	}
	public void setInstance_terminated(Boolean instance_terminated) {
		this.instance_terminated = instance_terminated;
	}
	public Boolean getInstance_active() {
		return instance_active;
	}
	public void setInstance_active(Boolean instance_active) {
		this.instance_active = instance_active;
	}
	public Boolean getInstance_stopped() {
		return instance_stopped;
	}
	public void setInstance_stopped(Boolean instance_stopped) {
		this.instance_stopped = instance_stopped;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public int getNum_instance() {
		return num_instance;
	}
	public void setNum_instance(int num_instance) {
		this.num_instance = num_instance;
	}
	public int getNum_CPU() {
		return num_CPU;
	}
	public void setNum_CPU(int num_CPU) {
		this.num_CPU = num_CPU;
	}
	public int getNum_Storage() {
		return num_Storage;
	}
	public void setNum_Storage(int num_Storage) {
		this.num_Storage = num_Storage;
	}
	
	public String getAmi_name() {
		return ami_name;
	}
	public void setAmi_name(String ami_name) {
		this.ami_name = ami_name;
	}

	public List<String> getReal_instance_ids() {
		return real_instance_ids;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setReal_instance_ids(List<String> real_instance_ids) {
		this.real_instance_ids = real_instance_ids;
	}
	@Override
	public String toString() {
		return "Instance [instance_id=" + instance_id + ", instance_name=" + instance_name + ", createTime="
				+ createTime + ", startDate=" + startDate + ", endTime=" + endTime + ", instance_terminated="
				+ instance_terminated + ", instance_active=" + instance_active + ", instance_stopped=" + instance_stopped
				+ ", duration=" + duration + ", num_instance=" + num_instance + ", num_CPU=" + num_CPU
				+ ", num_Storage=" + num_Storage + ", ami_name=" + ami_name + ", real_instance_ids=" + real_instance_ids
				+ ", user=" + user + "]";
	}
	
	
}
