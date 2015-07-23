package com.zdn.basicStruct;

import java.util.ArrayList;
import java.util.List;

import com.zdn.db.DBHelper;
import com.zdn.db.DBManager;
import com.zdn.chat.ZdnMessage;
import com.zdn.data.dataManager;

import android.content.Context;

//define member struct

public class friendMemberData  {

	//define here cause: friendMemberDataBasic will use reflect ,so only string type to be define
	static public int   	TEAM_NAME 		= 0x01; 
	static public int 		MEMBER_NAME		= 0x02;
	static public int   	PHONE_NUMBER	= 0x04; 
	static public int   	NICK_NAME		= 0x08;
	static public int   	COMMENT			= 0x10;
	static public int   	PICTURE_ADDRESS	= 0x20;
	
	public friendMemberDataBasic basic;
	//public Bitmap	picture;
	//private int index;
	private coordinate theLastCoordinate;   // 最新的坐标信息
	private List<ZdnMessage> message = null;
	private List<gpsChange> gpscChangeListenerList = new ArrayList();


	public friendMemberData( String tag )
	{
		//index = 0;
		message = new ArrayList<ZdnMessage>();
		basic = new friendMemberDataBasic();
		theLastCoordinate = new coordinate();
		//picture = null;
		basic.setTag(tag);
		
		constructMessageFromDb( dataManager.mainContext );
	}

    public friendMemberData(friendMemberDataBasic basic )
	{
		message = new ArrayList<ZdnMessage>();
		this.basic =basic;
		theLastCoordinate = new coordinate();
        constructMessageFromDb(dataManager.mainContext);
		//picture = null;
	}

	public void registgpsChangeListener( gpsChange gpsc )
	{
		gpscChangeListenerList.add(gpsc);
	}

	public void unRegistgpsChangeListener( gpsChange gpsc )
	{

		for( int i = 0 ; i < gpscChangeListenerList.size();i++ ) {
			if (gpscChangeListenerList.get(i) == gpsc)
			{
				gpscChangeListenerList.remove(i);
				break;
			}
		}
	}
	public void constructMessageFromDb( Context context )
	{
	
		DBHelper getDbHelper = DBManager.GetDbHelper( ZdnMessage.class );
		
		ArrayList<Object> miList = getDbHelper.searchData("belogTag" , basic.tag );
		
		
		
		for( int i = 0 ; i < miList.size() ; i++ )
		{
			ZdnMessage m = (ZdnMessage)(miList.get(i));
			
			message.add(m);
				
		}
		getDbHelper.closeDB();

	}
	
	

	public void clone( friendMemberData one )
	{
		
		//picture = one.picture;
		basic = one.basic;
		theLastCoordinate = one.theLastCoordinate;
	}
	
	public List<ZdnMessage> getMessageList()
	{
		return this.message;
	}

	public void updateCoordinate ( coordinate coord )
	{
		theLastCoordinate.setCoordinate( coord );
		for( gpsChange gps : gpscChangeListenerList)
		{
			gps.updateGps( this, coord );
		}
	}

	public double getLongitude()
	{
		return  theLastCoordinate.getLongitude();
	}

	public double getLatitude()
	{
		return theLastCoordinate.getLatitude();
	}
	//gps coordingate change interface
	public interface gpsChange
	{
		void updateGps( friendMemberData fmd , coordinate gps );
	}

}