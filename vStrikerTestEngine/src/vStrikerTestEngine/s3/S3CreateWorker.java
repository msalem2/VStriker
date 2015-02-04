package vStrikerTestEngine.s3;

import java.io.FileInputStream;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionReportData;

import com.emc.vipr.s3.s3api;

//@author Sanjeev Chauhan

public class S3CreateWorker implements Callable<ExecutionReportData> {

	private String objectLocation;
	private Api api;
	ExecutionReportData reportData = new ExecutionReportData();

	/*
	 * Constructor
	 */
	public S3CreateWorker() {
	}

	public S3CreateWorker(String objectLocation, Api api) {
		this.objectLocation = objectLocation;
		this.api = api;
	}

	public ExecutionReportData call() throws Exception {
		// Create object in S3
		long startTime = System.nanoTime();
		s3api.CreateObject(api.getSubtenant(), api.getSecretKey(),
				api.getUrl(), null, api.getBucket(), FilenameUtils.getName(objectLocation),
				new FileInputStream(objectLocation));
		long endTime = System.nanoTime();
		System.out.println("CreateObject execution time: " + (endTime - startTime));
		reportData.setDataKey("S3");
		reportData.setThreadValue(Thread.currentThread().getName());
		reportData.setCrudValue("Create");
		reportData.setDataValue(Long.toString((endTime-startTime)/1000000));
		return reportData;
	}
}