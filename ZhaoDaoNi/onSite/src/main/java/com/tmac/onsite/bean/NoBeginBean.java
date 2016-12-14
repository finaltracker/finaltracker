/**
 * 
 */
package com.tmac.onsite.bean;

/**
 * @author tmac
 */
public class NoBeginBean {

	private String timeRemin;
    private String number;
    private String workArea;
    private String finishTime;
    private boolean isRob;
    
	public NoBeginBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoBeginBean(String timeRemin, String number, String workArea,
			String finishTime, boolean isRob) {
		super();
		this.timeRemin = timeRemin;
		this.number = number;
		this.workArea = workArea;
		this.finishTime = finishTime;
		this.isRob = isRob;
	}

	public String getTimeRemin() {
		return timeRemin;
	}

	public void setTimeRemin(String timeRemin) {
		this.timeRemin = timeRemin;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getWorkArea() {
		return workArea;
	}

	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public boolean isRob() {
		return isRob;
	}

	public void setRob(boolean isRob) {
		this.isRob = isRob;
	}

	@Override
	public String toString() {
		return "NoBeginBean [timeRemin=" + timeRemin + ", number=" + number
				+ ", workArea=" + workArea + ", finishTime=" + finishTime
				+ ", isRob=" + isRob + "]";
	}
    
}
