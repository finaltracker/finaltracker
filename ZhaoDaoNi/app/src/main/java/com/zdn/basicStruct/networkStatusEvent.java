package com.zdn.basicStruct;

/**
 * Created by wanghp1 on 2015/5/8.
 */
public class networkStatusEvent {

    private boolean gprsConnect;
    private boolean wifiConnect;


    public networkStatusEvent( boolean gprsConnect , boolean wifiConnect )
    {
        this.gprsConnect = gprsConnect;
        this.wifiConnect = wifiConnect;
    }

    public void setGprsConnect( boolean gprsConnect ) { this.gprsConnect = gprsConnect; }
    public  void sewifiConnect( boolean wifiConnect ) { this.wifiConnect = wifiConnect; }

    public boolean getGprsConnect() { return gprsConnect ; }
    public  boolean getwifiConnect() { return wifiConnect ; }

}
