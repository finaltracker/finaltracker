package com.zdn.CommandParser;

import java.util.ArrayList;


//
public class ExpCommandE extends CommandE
{
	
	
	private String Cmd;
	
	private ArrayList<Property> ExpPropertyList;
	private Object              userData;
	
	//String Packet;
	
	public ExpCommandE()
	{
		userData = null;
		ExpPropertyList = new ArrayList<Property>();
	}
	
	public ExpCommandE( String Command )
	{
		super(Command);
		userData = null;
		ExpPropertyList = new ArrayList<Property>();
		
	}
	
	public ExpCommandE( String Command , String P_Name1 , String P_Context1)
	{
		super(Command,P_Name1,P_Context1);
		userData = null;
		ExpPropertyList = new ArrayList<Property>();
		
		
	}
	
	public ExpCommandE( String Command , String P_Name1 , String P_Context1 ,  String P_Name2 , String P_Context2 )
	{
		super(Command,P_Name1,P_Context1,P_Name2,P_Context2);
		userData = null;
		ExpPropertyList = new ArrayList<Property>();
	}
	
	public ExpCommandE( String Command , String P_Name1 , String P_Context1 ,  String P_Name2 , String P_Context2,String P_Name3 , String P_Context3 )
	{
		super(Command,P_Name1,P_Context1,P_Name2,P_Context2,P_Name3,P_Context3);
		userData = null;
		ExpPropertyList = new ArrayList<Property>();
	}
	
	public ExpCommandE( String Command , String P_Name1 , String P_Context1 ,  String P_Name2 , String P_Context2,String P_Name3 , String P_Context3 ,String P_Name4 , String P_Context4 )
	{
		super(Command,P_Name1,P_Context1,P_Name2,P_Context2,P_Name3,P_Context3,P_Name4,P_Context4);
		userData = null;
		ExpPropertyList = new ArrayList<Property>();
	}
	
	public ExpCommandE( String Command , String P_Name1 , String P_Context1 ,  String P_Name2 , String P_Context2,String P_Name3 , String P_Context3 ,String P_Name4 , String P_Context4,String P_Name5 , String P_Context5 )
	{
		super(Command,P_Name1,P_Context1,P_Name2,P_Context2,P_Name3,P_Context3,P_Name4,P_Context4,P_Name5,P_Context5);
		userData = null;
		ExpPropertyList = new ArrayList<Property>();
	}
	

	public void AddAExpProperty( Property CmdProperty )
	{
		ExpPropertyList.add(CmdProperty);
	}
	
	public int GetExpPropertyNum()
	{
		return ExpPropertyList.size();
	}
	
	public Property GetExpProperty(int index )
	{
		if( index < ExpPropertyList.size())
		{
			return ExpPropertyList.get(index);
		}
		else
		{
			return null;
		}
	}
	public Property GetExpProperty( String ProtertyName)
	{
		for( int i = 0 ;i < ExpPropertyList.size() ; i++ )
		{
			 if( ExpPropertyList.get( i ).GetPropertyName().equals(ProtertyName) )
			 {
				 return ExpPropertyList.get( i );				 
			 }
			
		}
		
		return null;
	}
	public String GetExpPropertyContext(String ProtertyName )
	{
		for( int i = 0 ;i < ExpPropertyList.size() ; i++ )
		{
			 if( ExpPropertyList.get( i ).GetPropertyName().equals(ProtertyName) )
			 {
				 return ExpPropertyList.get( i ).GetPropertyContext();				 
			 }
			
		}
		
		return null;
		
	}
	
	public void setUserData( Object o )
	{
		this.userData = o;
	}
	
	public Object getUserData() { return this.userData; }
	
}

