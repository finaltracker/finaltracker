package com.zdn.com;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zdn.R;
import com.zdn.fragment.mainActivityFragmentBase;

import java.nio.InvalidMarkException;

/**
 * Created by wanghp1 on 2015/6/11.
 */
public class headerCtrl {

    private LinearLayout header = null;
    private menuStateChange msc = null;


    private ImageView navigationButton = null;
    private ImageView backoff = null;
    private TextView headerTitle = null;
    private ImageView ShowFriendListButton = null;
    private ImageView addFriend = null;


    public headerCtrl( LinearLayout header ,  menuStateChange msc )
    {
        this.header = header;
        this.msc = msc;

        RelativeLayout rl = (RelativeLayout)(header.findViewById(R.id.title_bar));
        int buttonCount = rl.getChildCount();

        for( int i = 0 ; i < buttonCount ; i++ )
        {
            final View button = rl.getChildAt(i);

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (headerCtrl.this.msc != null) {
                        headerCtrl.this.msc.onMenuClick( button.getId());
                    }
                }
            });
        }

    }

    public void  setBackGroundColor( int color )
    {
        if( header != null )
        {
            header.setBackgroundColor( color );
        }
    }

    public void setTitle( String title )
    {
        if( headerTitle != null )
        {
            headerTitle.setText(title);
        }
    }

    public void setNavigationImage( int srcId )
    {
        if( navigationButton != null )
        {
            navigationButton.setBackgroundResource(srcId);
        }


    }


    public interface menuStateChange
    {
        public void onMenuClick(  int menuId );

        public void menuFragmentClick();
    }

}
