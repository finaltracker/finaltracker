package com.zdn.util;

import com.zdn.CommandParser.CommandE;
import com.zdn.CommandParser.Property;
import com.zdn.basicStruct.friendMemberDataBasic;
import com.zdn.chat.ZdnMessage;
import com.zdn.data.dataManager;

import java.io.File;

public class ObjectConvertTool {

	static public void friendMemberDataPackToCommandE( friendMemberDataBasic in ,CommandE e )
	{
		e.AddAProperty(new Property("group",in.getTeamName() ) );
		e.AddAProperty(new Property("member_name",in.getMemberName() ) );
		e.AddAProperty(new Property("friend_mobile",in.getPhoneNumber() ) );
		e.AddAProperty(new Property("nick_name",in.getNickName() ) );
		e.AddAProperty(new Property("comment",in.getComment()) );
		e.AddAProperty(new Property("picture_address",in.getPictureAddress()) );
	}
	
	static public void messagePackToCommandForSendToServer( ZdnMessage in ,CommandE e ,String targetMobile )
	{
		//e.AddAProperty(new Property("mobile",dataManager.self.preferencesPara.getPhoneNumber() ) );
		e.AddAProperty(new Property("friend_mobile",targetMobile ) );
		e.AddAProperty(new Property("create_time",in.getTimeString() ) );
		int type =  in.getType();


		if( type == in.MSG_TYPE_TEXT )
		{
			e.AddAProperty(new Property("message", in.getContent() ));
			e.AddAProperty(new Property("audio_url","" ) );
			e.AddAProperty(new Property("photo_url", ""));
		}
		else if ( type  == in.MSG_TYPE_PHOTO )
		{
			e.AddAProperty(new Property("message",""));
			File uFile = new File((String) in.getContent());
			e.AddAProperty(new Property("photo_file",uFile));
			e.AddAProperty(new Property("audio_file","" ) );
		}
		else if( type == in.MSG_TYPE_AUDIO )
		{
			e.AddAProperty(new Property("message",""));
			File uFile = new File((String) in.getContent());
			e.AddAProperty(new Property("photo_file",""));
			e.AddAProperty(new Property("audio_file",uFile ) );
		}
		else // type  ==??
		{
			e.AddAProperty(new Property("message",""));
			e.AddAProperty(new Property("photo_url",""));
			e.AddAProperty(new Property("audio_url","" ) );
		}


	}
	
}
