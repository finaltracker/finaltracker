package com.tmac.onsite.activity;

import java.util.ArrayList;
import java.util.List;

import me.tmac.photopicker.PhotoPicker;
import me.tmac.photopicker.PhotoPreview;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmac.onsite.R;
import com.tmac.onsite.adapter.RecyclerViewAdapter;
import com.tmac.onsite.inter_face.RecyclerItemClickListener;
import com.tmac.onsite.inter_face.RecyclerItemClickListener.OnItemClickListener;
import com.tmac.onsite.utils.LightControl;
import com.tmac.onsite.utils.StatusBarUtil;

public class UploadImgActivity extends Activity{
	
	private static final String TAG = "LC-UploadImgActivity";
	private static boolean DBG = true;
	private Button btn_add_img;
	private Button btn_send_img;
	private TextView tvView;
	private ListView mListView;
	private ArrayList<String> selectedPhotos;
	private RecyclerView recyclerView;
	private RecyclerViewAdapter adapter;
	private int value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_img);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.layout_title_bg),0);
		initViews(getIntent());
		selectedPhotos = new ArrayList<String>();
		adapter = new RecyclerViewAdapter(this, selectedPhotos);
		
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
		
		initEvents();
	}
	
	private void initViews(Intent intent) {
		// TODO Auto-generated method stub
		btn_add_img = (Button) findViewById(R.id.add_img_btn);
		btn_send_img = (Button) findViewById(R.id.send_img_btn);
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		tvView = (TextView) findViewById(R.id.upload_tv_title);
		value = intent.getIntExtra(DetailNoBeginActivity.RETURN_STATE, DetailNoBeginActivity.DEFAULT_VALUE);

		if(value == DetailNoBeginActivity.UPLOAD_PRE){
			tvView.setText(R.string.upload_pre_img);
		}else {
			tvView.setText(R.string.upload_end_img);
		}
	}


	private void initEvents() {
		// TODO Auto-generated method stub
		btn_add_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startActivity(new Intent(UploadImgActivity.this, SelectImgActivity.class));
				PhotoPicker.builder()
					.setPhotoCount(4)
					.setShowCamera(true)
					.setSelected(selectedPhotos)
					.start(UploadImgActivity.this);
			}
		});
		
		
		recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(UploadImgActivity.this, 
				new OnItemClickListener() {
					
					@Override
					public void onItemClick(View view, int position) {
						// TODO Auto-generated method stub
						PhotoPreview.builder()
								.setPhotos(selectedPhotos)
								.setCurrentItem(position)
								.start(UploadImgActivity.this);
					}
				}));
		
		btn_send_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LightControl.lightOff(getWindow());
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(UploadImgActivity.this);
				builder.setMessage(R.string.upload_message)
						.setPositiveButton(R.string.wait_upload, new DialogInterface.OnClickListener() {
								
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
							
						})
						.setNegativeButton(R.string.ensure_upload, new DialogInterface.OnClickListener() {
								
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								intent.putExtra(DetailNoBeginActivity.RETURN_STATE, value);
								setResult(RESULT_OK, intent);
								finish();		
							}
						})
						.setOnDismissListener(new OnDismissListener() {
							@Override
							public void onDismiss(DialogInterface dialog) {
								// TODO Auto-generated method stub
								LightControl.lightOn(getWindow());
							}
						})
						.create()
						.show();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK
				&& (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

			List<String> photos = null;
			if (data != null) {
				photos = data
						.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
			}
			selectedPhotos.clear();

			if (photos != null) {

				selectedPhotos.addAll(photos);
			}
			adapter.notifyDataSetChanged();
		}
	}

	
	
}
