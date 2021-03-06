package bootsample.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CopyImageRequest;
import com.amazonaws.services.ec2.model.CopyImageResult;
import com.amazonaws.services.ec2.model.DeregisterImageRequest;
import com.amazonaws.services.ec2.model.DeregisterImageResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

@Service
public class AWSServices {
	
	String ACCESS_KEY = "xyz";
	String SECRET_KEY = "xyz";

	
public List<String> spinInstances(int count,String ami){
	BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	AmazonEC2 ec2 = new AmazonEC2Client(credentials);
	System.out.println("After validating AWS credentials");
	ec2.setEndpoint("ec2.us-west-1.amazonaws.com");
	RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
	runInstancesRequest.withInstanceType("t2.micro");
	runInstancesRequest.withImageId(ami);
	runInstancesRequest.withMinCount(count);
	runInstancesRequest.withMaxCount(count);
	runInstancesRequest.withMonitoring(true);
	runInstancesRequest.withKeyName("mobileinfraforce");
	RunInstancesResult runInstances = ec2.runInstances(runInstancesRequest);
	List<Instance> instanceList = runInstances.getReservation().getInstances();
	List<String> instances = new ArrayList<>();
	for(Instance instance: instanceList){
		instances.add(instance.getInstanceId());
	}
	return instances;
}

public void stopInstances(List<String> instances){
	BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	AmazonEC2 ec2 = new AmazonEC2Client(credentials);
	ec2.setEndpoint("ec2.us-west-1.amazonaws.com");
	for(String instance: instances){
		StopInstancesRequest stopRequest = new StopInstancesRequest().withInstanceIds(instance);
		StopInstancesResult stopResult = ec2.stopInstances(stopRequest);
	}
}
public void startInstances(List<String> instances){
	BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	AmazonEC2 ec2 = new AmazonEC2Client(credentials);
	ec2.setEndpoint("ec2.us-west-1.amazonaws.com");
	for(String instance: instances){
		StartInstancesRequest  startRequest = new StartInstancesRequest().withInstanceIds(instance);
		StartInstancesResult startResult = ec2.startInstances(startRequest);
	}
}
public void terminateInstances(List<String> instances){
	BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	AmazonEC2 ec2 = new AmazonEC2Client(credentials);
	ec2.setEndpoint("ec2.us-west-1.amazonaws.com");
	for(String instance: instances){
		TerminateInstancesRequest terminateReqest = new TerminateInstancesRequest().withInstanceIds(instance);
		TerminateInstancesResult terminateInstanceResult = ec2.terminateInstances(terminateReqest);
	}
}
public String copyAMI(String ami,String name){
	BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	AmazonEC2 ec2 = new AmazonEC2Client(credentials);
	ec2.setEndpoint("ec2.us-west-2.amazonaws.com");
	CopyImageRequest copyImageRequest = new CopyImageRequest();
	copyImageRequest.withSourceImageId(ami);
	copyImageRequest.withName(name);
	copyImageRequest.withSourceRegion("us-west-1");
	CopyImageResult copyImageResult = ec2.copyImage(copyImageRequest);
	return copyImageResult.getImageId();
}
public void deleteAMI(String amiID){
	BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	AmazonEC2 ec2 = new AmazonEC2Client(credentials);
	ec2.setEndpoint("ec2.us-west-2.amazonaws.com");
	DeregisterImageRequest deregisterImageRequest = new DeregisterImageRequest();
	deregisterImageRequest.setImageId(amiID);
	DeregisterImageResult deregisterImageResponse = new DeregisterImageResult();
	deregisterImageResponse = ec2.deregisterImage(deregisterImageRequest);
}

public double getCPUUtilization(String instanceId){

	 try {
	       AmazonCloudWatchClient cw = new AmazonCloudWatchClient(new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY));
	       cw.setEndpoint("http://monitoring.us-west-1.amazonaws.com");
	       long offsetInMilliseconds = 1000 * 60 * 60 * 24;
	       GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
	               .withStartTime(new Date(new Date().getTime() - offsetInMilliseconds))
	               .withNamespace("AWS/EC2")
	               .withPeriod(60 * 60)
	               .withDimensions(new Dimension().withName("InstanceId").withValue(instanceId))
	               .withMetricName("CPUUtilization")
	               .withStatistics("Average", "Maximum")
	               .withEndTime(new Date());
	       GetMetricStatisticsResult getMetricStatisticsResult = cw.getMetricStatistics(request);

	       double avgCPUUtilization = 0;
	       double average = 0;
	       int count = 0;
	       List dataPoint = getMetricStatisticsResult.getDatapoints();
	       for (Object aDataPoint : dataPoint) {
	           Datapoint dp = (Datapoint) aDataPoint;
	           avgCPUUtilization = dp.getMaximum();
	           System.out.println(instanceId + " instance's average CPU utilization : " + dp.getAverage());
	           average +=avgCPUUtilization;
	           count++;
	       }
	       if(count==0){
	    	   return 0;
	       }else{
	    	   return average/count;
	       }


	   } catch (AmazonServiceException ase) {
		   System.out.println("Caught an AmazonServiceException, which means the request was made  "
	               + "to Amazon EC2, but was rejected with an error response for some reason.");
		   System.out.println("Error Message:    " + ase.getMessage());
		   System.out.println("HTTP Status Code: " + ase.getStatusCode());
		   System.out.println("AWS Error Code:   " + ase.getErrorCode());
		   System.out.println("Error Type:       " + ase.getErrorType());
		   System.out.println("Request ID:       " + ase.getRequestId());

	   }
	return 0;
}
public double getNetworkInUtilization(String instanceId){

	 try {
	       AmazonCloudWatchClient cw = new AmazonCloudWatchClient(new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY));
	       cw.setEndpoint("http://monitoring.us-west-1.amazonaws.com");
	       long offsetInMilliseconds = 1000 * 60 * 60 * 24;
	       GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
	               .withStartTime(new Date(new Date().getTime() - offsetInMilliseconds))
	               .withNamespace("AWS/EC2")
	               .withPeriod(60 * 60)
	               .withDimensions(new Dimension().withName("InstanceId").withValue(instanceId))
	               .withMetricName("NetworkIn")
	               .withStatistics("Average", "Maximum")
	               .withEndTime(new Date());
	       GetMetricStatisticsResult getMetricStatisticsResult = cw.getMetricStatistics(request);

	       double avgCPUUtilization = 0;
	       double average = 0;
	       int count = 0;
	       List dataPoint = getMetricStatisticsResult.getDatapoints();
	       for (Object aDataPoint : dataPoint) {
	           Datapoint dp = (Datapoint) aDataPoint;
	           avgCPUUtilization = dp.getMaximum();
	           System.out.println(instanceId + " instance's average NetworkIn utilization : " + dp.getAverage());
	           average +=avgCPUUtilization;
	           count++;
	       }
	       if(count==0){
	    	   return 0;
	       }else{
	    	   return average/count;
	       }


	   } catch (AmazonServiceException ase) {
		   System.out.println("Caught an AmazonServiceException, which means the request was made  "
	               + "to Amazon EC2, but was rejected with an error response for some reason.");
		   System.out.println("Error Message:    " + ase.getMessage());
		   System.out.println("HTTP Status Code: " + ase.getStatusCode());
		   System.out.println("AWS Error Code:   " + ase.getErrorCode());
		   System.out.println("Error Type:       " + ase.getErrorType());
		   System.out.println("Request ID:       " + ase.getRequestId());

	   }
	return 0;
}
public double getDiskWriteOpsUtilization(String instanceId){

	 try {
	       AmazonCloudWatchClient cw = new AmazonCloudWatchClient(new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY));
	       cw.setEndpoint("http://monitoring.us-west-1.amazonaws.com");
	       long offsetInMilliseconds = 1000 * 60 * 60 * 24;
	       GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
	               .withStartTime(new Date(new Date().getTime() - offsetInMilliseconds))
	               .withNamespace("AWS/EC2")
	               .withPeriod(60 * 60)
	               .withDimensions(new Dimension().withName("InstanceId").withValue(instanceId))
	               .withMetricName("DiskWriteOps")
	               .withStatistics("Average", "Maximum")
	               .withEndTime(new Date());
	       GetMetricStatisticsResult getMetricStatisticsResult = cw.getMetricStatistics(request);

	       double avgCPUUtilization = 0;
	       double average = 0;
	       int count = 0;
	       List dataPoint = getMetricStatisticsResult.getDatapoints();
	       for (Object aDataPoint : dataPoint) {
	           Datapoint dp = (Datapoint) aDataPoint;
	           avgCPUUtilization = dp.getMaximum();
	           System.out.println(instanceId + " instance's average DiskWriteOps utilization : " + dp.getAverage());
	           average +=avgCPUUtilization;
	           count++;
	       }
	       if(count==0){
	    	   return 0;
	       }else{
	    	   return average/count;
	       }


	   } catch (AmazonServiceException ase) {
		   System.out.println("Caught an AmazonServiceException, which means the request was made  "
	               + "to Amazon EC2, but was rejected with an error response for some reason.");
		   System.out.println("Error Message:    " + ase.getMessage());
		   System.out.println("HTTP Status Code: " + ase.getStatusCode());
		   System.out.println("AWS Error Code:   " + ase.getErrorCode());
		   System.out.println("Error Type:       " + ase.getErrorType());
		   System.out.println("Request ID:       " + ase.getRequestId());

	   }
	return 0;
}

	
}
