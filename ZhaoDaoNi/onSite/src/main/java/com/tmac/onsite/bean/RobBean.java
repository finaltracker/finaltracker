/**
 * 
 */
package com.tmac.onsite.bean;

/**
 * @author tmac
 */
public class RobBean {
	
	private String number;
    private String address;
    private String time;

    public RobBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RobBean(String number, String address, String time) {
		super();
		this.number = number;
		this.address = address;
		this.time = time;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "RobBean [number=" + number + ", address=" + address + ", time="
				+ time + "]";
	}

}
