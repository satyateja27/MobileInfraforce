package bootsample.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	private double duration;
	private int num_instance;
	private int num_CPU;
	private int num_Storage;
	private int ami_id;
	private ArrayList<String> real_instance_ids;
	private int user_id;
	public Instance()
	{
		
	}
	public Instance(String instance_name,Date createTime, Date startDate, int num_instance, int num_CPU, int num_Storage, int ami_id,int user_id,boolean terminated,ArrayList<String> list_instances) {
		super();
		this.instance_name = instance_name;
		this.createTime= createTime;
		this.startDate = startDate;
		this.num_instance = num_instance;
		this.num_CPU = num_CPU;
		this.num_Storage = num_Storage;
		this.ami_id = ami_id;
		this.user_id=user_id;
		this.instance_terminated=Boolean.FALSE;
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
	
	public int getAmi_id() {
		return ami_id;
	}
	public void setAmi_id(int ami_id) {
		this.ami_id = ami_id;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public List<String> getReal_instance_ids() {
		return real_instance_ids;
	}
	public void setReal_instance_ids(ArrayList<String> real_instance_ids) {
		this.real_instance_ids = real_instance_ids;
	}
	@Override
	public String toString() {
		return "Instance [instance_id=" + instance_id + ", instance_name=" + instance_name + ", createTime="
				+ createTime + ", startDate=" + startDate + ", endTime=" + endTime + ", instance_terminated="
				+ instance_terminated + ", duration=" + duration + ", num_instance=" + num_instance + ", num_CPU="
				+ num_CPU + ", num_Storage=" + num_Storage + ", ami_id=" + ami_id + ", real_instance_ids="
				+ real_instance_ids + ", user_id=" + user_id + "]";
	}
	
}
