package bootsample.service;

import java.util.Date;
import java.util.List;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.*;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.ec2.model.Monitoring;

public class AWSMonitoring {
	
	final String awsAccessKey = "xyz";
    final String awsSecretKey = "xyz";
    final long twentyFourHrs = 1000 * 60 * 60 * 24;
    final int oneHour = 60 * 60;
    
    public void findCloudWatchMetrics(String instances){    	
    	
    	AmazonCloudWatchClient client = new AmazonCloudWatchClient(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
        client.setEndpoint("http://monitoring.eu-west-1.amazonaws.com");
        
        GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
        		.withStartTime(new Date(new Date().getTime()- twentyFourHrs))
                .withNamespace("AWS/EC2")
                .withPeriod(oneHour)
                .withDimensions(new Dimension().withName("InstanceId").withValue("i-62c4bd46"))
                .withMetricName("NetworkOut")
                .withStatistics("Average", "Maximum")
                .withEndTime(new Date());
        
        GetMetricStatisticsResult result = client.getMetricStatistics(request);
        System.out.println("Test");
        System.out.println(result.getDatapoints().toString());
        for (final Datapoint dataPoint : result.getDatapoints()) {
            System.out.printf("%s instance's average CPU utilization : %s%n", instances, dataPoint.getAverage());      
            System.out.printf("%s instance's max CPU utilization : %s%n", instances, dataPoint.getMaximum());
        }
    }
	

}
