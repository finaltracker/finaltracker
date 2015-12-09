package com.zdn.basicStruct;

/**
 * Created by wanghp1 on 2015/6/19.
 */
public class robot {
    public String name;
    public String pictureUrl;
    public coordinate address = new coordinate();


    public robot( String name, String pictureUrl , double lng ,  double lat )
    {
        this.name = name;
        this.pictureUrl = pictureUrl;
        address.setCoordinate( new coordinate( lng , lat ));
    }

}
