package com.zdn.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zdn.R;
import com.zdn.activity.commonNewInputActivity;
import com.zdn.cropimage.ChooseDialog;
import com.zdn.cropimage.CropHelper;
import com.zdn.data.dataManager;
import com.zdn.internet.InternetComponent;
import com.zdn.logic.MainControl;
import com.zdn.util.FileUtil;
import com.zdn.util.OSUtils;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link myInfomationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link myInfomationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myInfomationFragment extends mainActivityFragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View rootView = null;
    ImageView mymajia = null;
    TextView changeHeadmap = null;
    ImageView erweimaView = null;
    TextView NiCheng = null;
    LinearLayout nichengline = null;
    private ChooseDialog mDialog;
    private CropHelper mCropHelper;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static myInfomationFragment newInstance(String param1, String param2) {
        myInfomationFragment fragment = new myInfomationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public myInfomationFragment() {
        // Required empty public constructor
        setFragmentIndex( mainActivityFragmentBase.PERSION_INFORMATION_FRAGMENT );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.my_information, container, false);

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mCropHelper=new CropHelper( this, OSUtils.getSdCardDirectory()+"/head.png");
        mDialog=new ChooseDialog( this, mCropHelper);
        init();
        return rootView;
    }

    private void init()
    {
        mymajia = (ImageView)( rootView.findViewById( R.id.my_majia ) );
        NiCheng = (TextView)( rootView.findViewById( R.id.myInformationNiCheng ) );
        erweimaView = (ImageView)( rootView.findViewById( R.id.myInformationErweimaView ) );
        changeHeadmap = (TextView)(rootView.findViewById(R.id.changePhotoHeadMap));
        nichengline = (LinearLayout)(rootView.findViewById( R.id.nichengLine) );
        //mymajia.setImageDrawable( getResources().getDrawable(R.drawable.ic_mylocalphoto)
        String myPhotoUrl = dataManager.self.selfInfo.basic.getPictureAddress();


        BitmapUtils avatorBitmapUtils = null;
        String myAvatorDir ;
        myAvatorDir = FileUtil.makePath(FileUtil.getBaseDirector(), getString(R.string.friendsAvator));
        avatorBitmapUtils = new BitmapUtils(this.getActivity() , myAvatorDir );
        avatorBitmapUtils.display(mymajia, InternetComponent.WEBSITE_ADDRESS_BASE_NO_SEPARATOR + myPhotoUrl);
        String nickName = dataManager.self.selfInfo.basic.getNickName();
        if( (null == nickName ) || (nickName.isEmpty()) )
        {
            nickName = "无名";
        }
        NiCheng.setText(nickName);

        changeHeadmap.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mDialog.popSelectDialog();

                    }
                });
        nichengline.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                //start comment edit activity
                Intent intent = new Intent( myInfomationFragment.this.getActivity() , commonNewInputActivity.class );
                Bundle b = new Bundle();
                b.putString("comment", NiCheng.getText().toString() );
                intent.putExtras(b);
                startActivityForResult(intent,  CropHelper.COMMON_INPUT_REQUEST_ID );

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("onActivityResult", requestCode+"**"+resultCode);
        if(requestCode==Activity.RESULT_CANCELED){
            return;
        }else{
            switch (requestCode) {
                case CropHelper.HEAD_FROM_ALBUM: {
                    mCropHelper.getDataFromAlbum(data);
                    Log.e("onActivityResult", "接收到图库图片");
                    break;
                }
                case CropHelper.HEAD_FROM_CAMERA: {
                    mCropHelper.getDataFromCamera(data);
                    Log.e("onActivityResult", "接收到拍照图片");
                    break;
                }
                case CropHelper.HEAD_SAVE_PHOTO: {
                    if (data != null && data.getParcelableExtra("data") != null) {
                        mCropHelper.savePhoto(data, OSUtils.getSdCardDirectory() + "/myHead.png");
                        mymajia.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                        File uFile = new File( OSUtils.getSdCardDirectory() + "/myHead.png");
                        MainControl.uploadFile("avatar_url", uFile );
                    }
                    break;
                }
                case CropHelper.COMMON_INPUT_REQUEST_ID:
                {
                    String nickName = data.getExtras().getString("newInput");//得到新Activity 关闭后返回的数据
                    NiCheng.setText(nickName);
                    Log.i(this.getClass().getName(), "user new nike name " + nickName);

                    dataManager.self.selfInfo.basic.setNickName( nickName );
                    break;
                }
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
