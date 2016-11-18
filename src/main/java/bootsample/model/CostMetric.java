package bootsample.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class CostMetric implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int type_id;
	private String name;
	private double cost;
	
	@Column(length=32, columnDefinition="varchar(32) default 'perHour'")
	private String frequency;

	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public CostMetric()
	{
		
	}
	public CostMetric(String name, double cost) {
		super();
		this.name = name;
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	@Override
	public String toString() {
		return "CostMetric [type_id=" + type_id + ", name=" + name + ", cost=" + cost + ", frequency=" + frequency
				+ "]";
	}

}
