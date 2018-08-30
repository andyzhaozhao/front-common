package com.uhf.structures;

public class FixedQParms {
	public int qValue;
	public int retryCount;
	public int toggleTarget;
	public int repeatUntiNoTags;
	public FixedQParms(){};
	public FixedQParms(int qValue,int retryCount,int toggleTarget,int repeatUntiNoTags){
		this.qValue = qValue;
		this.retryCount = retryCount;
		this.toggleTarget = toggleTarget;
		this.repeatUntiNoTags = repeatUntiNoTags;
	}
	public void setValue(int qValue,int retryCount,int toggleTarget,int repeatUntiNoTags){
		this.qValue = qValue;
		this.retryCount = retryCount;
		this.toggleTarget = toggleTarget;
		this.repeatUntiNoTags = repeatUntiNoTags;
	}
}
