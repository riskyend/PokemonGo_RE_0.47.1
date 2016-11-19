package com.upsight.mediation.ads.adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.VideoView;
import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.mraid.MRaidDrawables;

public class MRaidVideoActivity
  extends Activity
  implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener
{
  private static final float CLOSE_REGION_HEIGHT_OFFSET = 0.01F;
  private static final float CLOSE_REGION_SIZE = 50.0F;
  private static final float CLOSE_REGION_WIDTH_OFFSET = 0.9F;
  private static final String TAG = "MRAIDVideoActivity";
  private long closeButtonDelay;
  private ImageView closeRegion;
  private float deviceHeight;
  private float deviceWidth;
  private FrameLayout layout;
  private boolean shouldReturnToInterstitial;
  private FrameLayout.LayoutParams skipParams;
  private VideoView videoView;
  
  private void addCloseRegion()
  {
    this.closeRegion = new ImageButton(this);
    this.closeRegion.setBackgroundColor(0);
    this.closeRegion.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent();
        paramAnonymousView.putExtra("rti", MRaidVideoActivity.this.shouldReturnToInterstitial);
        MRaidVideoActivity.this.setResult(0, paramAnonymousView);
        MRaidVideoActivity.this.finish();
      }
    });
    this.closeRegion.setVisibility(4);
    if (this.closeButtonDelay != -1L) {
      this.closeRegion.postDelayed(new Runnable()
      {
        public void run()
        {
          MRaidVideoActivity.this.closeRegion.setVisibility(0);
        }
      }, this.closeButtonDelay);
    }
    Drawable localDrawable1 = MRaidDrawables.getDrawableForImage(this, "/assets/drawable/close_button_normal.png", "close_button_normal", -16777216);
    Drawable localDrawable2 = MRaidDrawables.getDrawableForImage(this, "/assets/drawable/close_button_pressed.png", "close_button_pressed", -16777216);
    StateListDrawable localStateListDrawable = new StateListDrawable();
    localStateListDrawable.addState(new int[] { -16842919 }, localDrawable1);
    localStateListDrawable.addState(new int[] { 16842919 }, localDrawable2);
    this.closeRegion.setImageDrawable(localStateListDrawable);
    this.closeRegion.setScaleType(ImageView.ScaleType.CENTER_CROP);
  }
  
  private void getScreenDimensions()
  {
    this.deviceHeight = getResources().getDisplayMetrics().heightPixels;
    this.deviceWidth = getResources().getDisplayMetrics().widthPixels;
  }
  
  private void setCloseRegionPosition()
  {
    getScreenDimensions();
    int i = (int)(this.deviceHeight * 0.01F);
    int j = (int)(this.deviceWidth * 0.9F);
    this.skipParams.topMargin = i;
    this.skipParams.leftMargin = j;
    this.closeRegion.setLayoutParams(this.skipParams);
  }
  
  private void setCloseRegionPositionAndSize()
  {
    int i = (int)TypedValue.applyDimension(1, 50.0F, getResources().getDisplayMetrics());
    this.skipParams = new FrameLayout.LayoutParams(i, i);
    setCloseRegionPosition();
    this.layout.addView(this.closeRegion);
  }
  
  public void onBackPressed()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("rti", this.shouldReturnToInterstitial);
    setResult(0, localIntent);
    finish();
  }
  
  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    paramMediaPlayer = new Intent();
    paramMediaPlayer.putExtra("rti", this.shouldReturnToInterstitial);
    setResult(-1, paramMediaPlayer);
    finish();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    setCloseRegionPosition();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.layout = new FrameLayout(this);
    paramBundle = new FrameLayout.LayoutParams(-1, -1);
    paramBundle.gravity = 17;
    this.layout.setLayoutParams(paramBundle);
    String str = getIntent().getStringExtra("url");
    this.closeButtonDelay = getIntent().getLongExtra("cb_ms", 0L);
    this.shouldReturnToInterstitial = getIntent().getBooleanExtra("rti", false);
    this.videoView = new VideoView(this);
    this.videoView.setOnCompletionListener(this);
    this.videoView.setOnErrorListener(this);
    this.videoView.setOnPreparedListener(this);
    this.videoView.setVideoPath(str);
    this.videoView.setLayoutParams(paramBundle);
    this.layout.addView(this.videoView);
    addCloseRegion();
    setCloseRegionPositionAndSize();
    setContentView(this.layout);
  }
  
  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    FuseLog.w("MRAIDVideoActivity", "ERROR LOADING VIDEO!");
    paramMediaPlayer = new Intent();
    paramMediaPlayer.putExtra("rti", this.shouldReturnToInterstitial);
    setResult(0, paramMediaPlayer);
    finish();
    return false;
  }
  
  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    this.videoView.start();
  }
  
  protected void onStop()
  {
    super.onStop();
    this.videoView.stopPlayback();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/MRaidVideoActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */