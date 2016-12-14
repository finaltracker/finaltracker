package com.tmac.onsite.adapter;

import java.io.File;
import java.util.ArrayList;

import me.tmac.photopicker.utils.AndroidLifecycleUtils;

import com.bumptech.glide.Glide;
import com.tmac.onsite.R;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PhotoViewHolder>{

	
	private ArrayList<String> photoPaths = new ArrayList<String>();
	private LayoutInflater inflater;

	private Context mContext;
	
	
	 public RecyclerViewAdapter(Context mContext, ArrayList<String> photoPaths) {
	    this.photoPaths = photoPaths;
	    this.mContext = mContext;
	    inflater = LayoutInflater.from(mContext);
	
	  }

	@Override
	public void onBindViewHolder(PhotoViewHolder holder, int position) {
		// TODO Auto-generated method stub
		Uri uri = Uri.fromFile(new File(photoPaths.get(position)));

	    boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.iv.getContext());

	    if (canLoadImage) {
	      Glide.with(mContext)
	              .load(uri)
	              .centerCrop()
	              .thumbnail(0.1f)
	              .placeholder(R.drawable.__picker_ic_photo_black_48dp)
	              .error(R.drawable.__picker_ic_broken_image_black_48dp)
	              .into(holder.iv);
	    }
	}

	@Override
	public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		View itemView = inflater.inflate(R.layout.recy_item, parent, false);
		return new PhotoViewHolder(itemView);
	}
	
	public static class PhotoViewHolder extends RecyclerView.ViewHolder {
		private ImageView iv;

		public PhotoViewHolder(View itemView) {
			super(itemView);
			iv = (ImageView) itemView.findViewById(R.id.recy_img);
		}
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return photoPaths.size();
	}
	
}
