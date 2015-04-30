package com.zdn.sort;

public class SortModel {

	private String name;   //显示的数�?
	private String sortLetters;  //显示数据拼音的首字母
	private String PhoneNumber;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String GetPhoneNumber()
	{
		return PhoneNumber;
	}
	
	public void setPhoneNumber( String PhoneNumber )
	{
		this.PhoneNumber = PhoneNumber;
	}
	
	public String getSortLetters() {
		return sortLetters;
	}
	
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
