package com.Amazon.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import bootsample.service.AWSServices;

public class TestClass {
	
	@Test
	public void checkAmazon(){
		
//		
	AWSServices awsServices = new AWSServices();
	List<String> list_instances = awsServices.spinInstances(2,"ami-c58adfa5");
//		List<String> list_instances = new ArrayList<>();
//		list_instances.add("i-99324abd");
//		list_instances.add("i-a6324a82");
//		awsServices.terminateInstances(list_instances);
	System.out.println(list_instances.toString());

		
	}

}
