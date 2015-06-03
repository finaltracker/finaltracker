package com.zdn.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zdn.R;
import com.zdn.adapter.navigationListDrawerItemAdapter;
import com.zdn.data.dataManager;
import com.zdn.logic.InternetComponent;
import com.zdn.sort.ClearEditText;
import com.zdn.util.FileUtil;
import com.zdn.view.FriendListView;

import java.util.List;

public class navigationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    View rootView ;
    RelativeLayout header;
    ImageView userPhotoImageView;
    TextView  userNickName;
    ListView  ItemList;
    List<navigationListDrawerItemAdapter.navigationListContextHolder> nlch = null;
    navigationListDrawerItemAdapter  adapter;
    navigationChanged nc;

    private BitmapUtils avatorBitmapUtils = null;

    // TODO: Rename and change types of parameters
    private String mParam1;


    // TODO: Rename and change types and number of parameters
    public static navigationFragment newInstance( Context context ,List<navigationListDrawerItemAdapter.navigationListContextHolder> nlch ,navigationChanged nc ,int selectIndex) {
        navigationFragment fragment = new navigationFragment( context , nlch ,nc ,selectIndex );

        return fragment;
    }

    public navigationFragment ( ) {

    }

    @SuppressLint("ValidFragment")
    public navigationFragment ( Context context  , List<navigationListDrawerItemAdapter.navigationListContextHolder>  nlch ,navigationChanged nc ,int selectIndex ) {
        this.nlch = nlch;
        this.nc = nc;
        adapter = new navigationListDrawerItemAdapter( context ,nlch  ,nc ,selectIndex );

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.navigation_list_drawer, container, false);

        findView();
        init();
        return rootView;
    }

    private void findView() {
        header =(RelativeLayout)rootView.findViewById(R.id.layoutHeader);
        userPhotoImageView=(ImageView)rootView. findViewById( R.id.navigationUserPhoto );
        userNickName = (TextView) rootView.findViewById(R.id.navigation_name);
        ItemList = (ListView) rootView.findViewById( R.id.contentList);

    }

    private void init()
    {

        header.setBackgroundResource( R.drawable.ic_user_background );
        String myAvatorDir ;
        myAvatorDir = FileUtil.makePath(FileUtil.getBaseDirector(), getString(R.string.friendsAvator));
        avatorBitmapUtils = new BitmapUtils(this.getActivity() , myAvatorDir );


        String myPhotoUrl = dataManager.self.selfInfo.basic.getPictureAddress();

        if( myPhotoUrl != null && (!myPhotoUrl.isEmpty()) )
        {
            Log.i(this.getClass().getSimpleName(), "myAvator url :" + InternetComponent.WEBSITE_ADDRESS_BASE_NO_SEPARATOR + myPhotoUrl);
            avatorBitmapUtils.display( userPhotoImageView , InternetComponent.WEBSITE_ADDRESS_BASE_NO_SEPARATOR + myPhotoUrl );
        }
        else {
            this.userPhotoImageView.setImageResource(R.drawable.ic_mylocalphoto);
        }

        String nickName = dataManager.self.selfInfo.basic.getNickName();
        if( (null == nickName ) || (nickName.isEmpty()) )
        {
            nickName = "无名";
        }
        this.userNickName.setText( nickName );

        ItemList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public interface navigationChanged{
        public void onItemClickNavigation(int position ) ;

    }

}
