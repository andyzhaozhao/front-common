package com.uhf.structures;

public class DynamicQParms {
	public int startQValue;
	public int minQValue;
	public int maxQValue;
	public int retryCount;
	public int toggleTarget;
	public int thresholdMultiplier;
	public DynamicQParms(){};
	public DynamicQParms(int startQValue,int minQValue,int maxQValue,int retryCount,int toggleTarget,int thresholdMultiplier){
		this.startQValue = startQValue;
		this.minQValue = minQValue;
		this.maxQValue = maxQValue;
		this.retryCount = retryCount;
		this.toggleTarget = toggleTarget;
		this.thresholdMultiplier = thresholdMultiplier;
	}
	public void setValue(int startQValue,int minQValue,int maxQValue,int retryCount,int toggleTarget,int thresholdMultiplier){
		this.startQValue = startQValue;
		this.minQValue = minQValue;
		this.maxQValue = maxQValue;
		this.retryCount = retryCount;
		this.toggleTarget = toggleTarget;
		this.thresholdMultiplier = thresholdMultiplier;
	}
}
