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

    void setGprsConnect( boolean gprsConnect ) { this.gprsConnect = gprsConnect; }
    void sewifiConnect( boolean wifiConnect ) { this.wifiConnect = wifiConnect; }

    boolean getGprsConnect() { return gprsConnect ; }
    boolean getwifiConnect() { return wifiConnect ; }

}
