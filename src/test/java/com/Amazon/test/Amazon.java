package com.Amazon.test;

import java.util.List;

import org.junit.Test;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CopyImageRequest;
import com.amazonaws.services.ec2.model.CopyImageResult;
import com.amazonaws.services.ec2.model.CopySnapshotRequest;
import com.amazonaws.services.ec2.model.CopySnapshotResult;
import com.amazonaws.services.ec2.model.DeregisterImageRequest;
import com.amazonaws.services.ec2.model.DeregisterImageResult;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

public class Amazon {
	
	@Test
	public void testAmazonImage(){

		BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonEC2 ec2 = new AmazonEC2Client(credentials);
		ec2.setEndpoint("ec2.us-west-2.amazonaws.com");
//		//System.out.println(ec2.describeImage);
//		DescribeInstancesRequest describeInstanceRequest = new DescribeInstancesRequest();
//		DescribeInstancesResult describeInstanceResult = ec2.describeInstances(describeInstanceRequest);
//		//System.out.println(describeInstanceResult.getReservations().toString());
//		
//		DescribeImagesRequest describeImageRequest = new DescribeImagesRequest();
//		DescribeImagesResult describeImagesResult = ec2.describeImages(describeImageRequest);
//		System.out.println(describeImagesResult.toString());
//		RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
//			    .withInstanceType("t2.micro")
//			    .withImageId("ami-924ae8f2")
//			    .withMinCount(2)
//			    .withMaxCount(2);
//		RunInstancesResult runInstances = ec2.runInstances(runInstancesRequest);
//		List<Instance> instances = runInstances.getReservation().getInstances();
//		StopInstancesRequest stopRequest = new StopInstancesRequest().withInstanceIds("i-067be6c8830511404");
//		StopInstancesResult stopResult = ec2.stopInstances(stopRequest);
		
//		TerminateInstancesRequest terminateReqest = new TerminateInstancesRequest().withInstanceIds("i-09e2e870e59f7fa67");
//		TerminateInstancesResult terminateInstanceResult = ec2.terminateInstances(terminateReqest);
//		CopyImageRequest copyImageRequest = new CopyImageRequest();
//		copyImageRequest.withSourceImageId("ami-924ae8f2");
//		copyImageRequest.withSourceRegion("us-west-2");
//		CopyImageResult copyImageResult = ec2.copyImage(copyImageRequest);
		DeregisterImageRequest deregisterImageRequest = new DeregisterImageRequest();
		deregisterImageRequest.setImageId("ami-1357f573");

		DeregisterImageResult deregisterImageResponse = new DeregisterImageResult();
		deregisterImageResponse = ec2.deregisterImage(deregisterImageRequest);
		
		
//		for (Instance instance : instances) {
//			
//		}
		
		
		
	}

}
