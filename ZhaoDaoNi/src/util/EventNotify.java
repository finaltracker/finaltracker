package util;

import CommandParser.CommandE;

public interface EventNotify {
	/* interface between SMS and Engine */
	// void associatedPhoneCommand( int command, Object obj); // from outer
	//
	// void associatedPhoneGpsPositionChanged( PositionInfo position );
	//
	//
	// /* interface between GPS and Engine */
	//
	void localGpsPositionChanged(CommandE position);

	void receive(String from, CommandE dfb);

//	void notifyNetworkState(boolean isNetworkOk);

	int getSpecifyPhoneStatus(String from, int module);
	
	void onRemoteCnfSync(String from,int module,int phoneStatus,boolean isAlive);
}
