package com.languages.learningapi.models.controller.stat.retention;

public class RetentionStat {
	private int beforePupilsCount;
	private int afterPupilsCount;
	private double retention;
	private double outflow;
	
	private int newPupilsCount;
	private int outflowPupilsCount;
	
	public int getBeforePupilsCount() {
		return beforePupilsCount;
	}
	public void setBeforePupilsCount(int beforePupilsCount) {
		this.beforePupilsCount = beforePupilsCount;
	}
	public int getAfterPupilsCount() {
		return afterPupilsCount;
	}
	public void setAfterPupilsCount(int afterPupilsCount) {
		this.afterPupilsCount = afterPupilsCount;
	}
	public double getRetention() {
		return retention;
	}
	public void setRetention(double retention) {
		this.retention = retention;
	}
	public double getOutflow() {
		return outflow;
	}
	public void setOutflow(double outflow) {
		this.outflow = outflow;
	}
	public int getNewPupilsCount() {
		return newPupilsCount;
	}
	public void setNewPupilsCount(int newPupilsCount) {
		this.newPupilsCount = newPupilsCount;
	}
	public int getOutflowPupilsCount() {
		return outflowPupilsCount;
	}
	public void setOutflowPupilsCount(int outflowPupilsCount) {
		this.outflowPupilsCount = outflowPupilsCount;
	}
	
}
