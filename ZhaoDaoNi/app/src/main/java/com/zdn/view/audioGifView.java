package com.zdn.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;

import java.io.IOException;

/**
 * Created by wanghp1 on 2015/8/17.
 */
public class audioGifView extends GifView implements MediaPlayer.OnCompletionListener {
    String audioPath = null;
    MediaPlayer mp = null;
    int index = 0;

    public audioGifView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mp =new MediaPlayer();
        mp.setOnCompletionListener(this);

    }

    public void setaudioPath( String audioPath )
    {
        this.audioPath = audioPath;
    }
    public void setIndex( int position )
    {
        index = position;
    }
    public void startAndPlay()
    {
        if( audioPath != null )
        {
           try {
                mp.setDataSource(audioPath);
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }mp.start();
        }



        super.start();
    }

    public void stop()
    {
        mp.stop();
        mp.reset();
        super.stop();
    }

    public boolean isRunning()
    {
        return super.isRunning();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        stop();
    }
}
