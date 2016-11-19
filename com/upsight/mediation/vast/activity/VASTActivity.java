package com.upsight.mediation.vast.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.vast.Postroll.MRaidPostroll;
import com.upsight.mediation.vast.Postroll.Postroll;
import com.upsight.mediation.vast.Postroll.Postroll.Listener;
import com.upsight.mediation.vast.VASTPlayer;
import com.upsight.mediation.vast.VASTPlayer.VASTPlayerListener;
import com.upsight.mediation.vast.model.TRACKING_EVENTS_TYPE;
import com.upsight.mediation.vast.model.VASTModel;
import com.upsight.mediation.vast.model.VASTTracking;
import com.upsight.mediation.vast.model.VideoClicks;
import com.upsight.mediation.vast.util.Assets;
import com.upsight.mediation.vast.util.HttpTools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VASTActivity
  extends Activity
  implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, Postroll.Listener
{
  private static final long QUARTILE_TIMER_INTERVAL = 250L;
  private static String TAG = "VASTActivity";
  private static final long TOOLBAR_HIDE_DELAY = 3000L;
  private static final long VIDEO_PROGRESS_TIMER_INTERVAL = 33L;
  public static DisplayMetrics displayMetrics;
  private String mActionText;
  private int mCurrentOrientation;
  private int mCurrentVideoPosition;
  private String mEndCardHtml = "";
  private Handler mHandler;
  private boolean mIsCompleted = false;
  private boolean mIsPlayBackError = false;
  private boolean mIsProcessedImpressions = false;
  private boolean mIsRewarded;
  private boolean mIsVideoPaused = false;
  private final int mMaxProgressTrackingPoints = 20;
  private MediaPlayer mMediaPlayer;
  private RelativeLayout mOverlay;
  private PlayerControls mPlayerControls;
  private Postroll mPostroll;
  private boolean mPostrollFlag;
  private ProgressBar mProgressBar;
  private int mQuartile = 0;
  private RelativeLayout mRootLayout;
  private int mScreenHeight;
  private int mScreenWidth;
  private long mSkipOffset;
  private boolean mSkipOffsetRelative;
  private long mSkipOffsetServer;
  private Timer mStartVideoProgressTimer;
  private SurfaceHolder mSurfaceHolder;
  private SurfaceView mSurfaceView;
  private Timer mToolBarTimer;
  private HashMap<TRACKING_EVENTS_TYPE, List<VASTTracking>> mTrackingEventMap;
  private Timer mTrackingEventTimer;
  public VASTModel mVastModel = null;
  private String mVersion;
  private int mVideoDuration;
  private int mVideoHeight;
  private LinkedList<Integer> mVideoProgressTracker = null;
  private int mVideoWidth;
  private Rect rekt;
  private boolean shouldPlayOnResume = false;
  private boolean showingPostroll;
  
  private void activateButtons(boolean paramBoolean)
  {
    if (this.mPlayerControls == null) {
      return;
    }
    if (paramBoolean)
    {
      this.mPlayerControls.setVisibility(0);
      return;
    }
    this.mPlayerControls.setVisibility(8);
  }
  
  private void calculateAspectRatio()
  {
    if ((this.mVideoWidth == 0) || (this.mVideoHeight == 0))
    {
      FuseLog.d(TAG, "mVideoWidth or mVideoHeight is 0, skipping calculateAspectRatio");
      return;
    }
    double d = Math.min(1.0D * this.mScreenWidth / this.mVideoWidth, 1.0D * this.mScreenHeight / this.mVideoHeight);
    int i = (int)(this.mVideoWidth * d);
    int j = (int)(this.mVideoHeight * d);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(i, j);
    localLayoutParams.addRule(13);
    this.mSurfaceView.setLayoutParams(localLayoutParams);
    this.mSurfaceHolder.setFixedSize(i, j);
    FuseLog.v(TAG, " screen size: " + this.mScreenWidth + "x" + this.mScreenHeight);
    FuseLog.v(TAG, " video size:  " + this.mVideoWidth + "x" + this.mVideoHeight);
    FuseLog.v(TAG, "surface size: " + i + "x" + j);
  }
  
  private void cleanActivityUp()
  {
    cleanUpMediaPlayer();
    stopTrackingEventTimer();
    stopVideoProgressTimer();
    stopToolBarTimer();
    if (this.mPlayerControls != null)
    {
      this.mPlayerControls.setVisibility(8);
      this.mOverlay.removeView(this.mPlayerControls);
      this.mPlayerControls = null;
    }
  }
  
  private void cleanUpMediaPlayer()
  {
    if (this.mMediaPlayer != null)
    {
      if (this.mMediaPlayer.isPlaying()) {
        this.mMediaPlayer.stop();
      }
      this.mMediaPlayer.setOnCompletionListener(null);
      this.mMediaPlayer.setOnErrorListener(null);
      this.mMediaPlayer.setOnPreparedListener(null);
      this.mMediaPlayer.setOnVideoSizeChangedListener(null);
      this.mMediaPlayer.release();
      this.mMediaPlayer = null;
    }
  }
  
  private void createMediaPlayer()
  {
    this.mMediaPlayer = new MediaPlayer();
    this.mMediaPlayer.setOnCompletionListener(this);
    this.mMediaPlayer.setOnErrorListener(this);
    this.mMediaPlayer.setOnPreparedListener(this);
    this.mMediaPlayer.setOnVideoSizeChangedListener(this);
    this.mMediaPlayer.setOnSeekCompleteListener(this);
    this.mMediaPlayer.setAudioStreamType(3);
  }
  
  private void createOverlay(RelativeLayout.LayoutParams paramLayoutParams)
  {
    this.mOverlay = new RelativeLayout(this);
    this.mOverlay.setLayoutParams(paramLayoutParams);
    this.mOverlay.setPadding(0, 0, 0, 0);
    this.mOverlay.setBackgroundColor(0);
    this.mOverlay.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        VASTActivity.this.overlayClicked();
        return false;
      }
    });
    this.mRootLayout.addView(this.mOverlay);
  }
  
  private void createPlayerControls()
  {
    Object localObject = new RelativeLayout.LayoutParams(-1, -2);
    ((RelativeLayout.LayoutParams)localObject).addRule(10);
    resolveSkipOffset();
    this.mPlayerControls = new PlayerControls(this);
    this.mPlayerControls.setVastModel(this.mVastModel);
    PlayerControls localPlayerControls = this.mPlayerControls;
    if (this.mSkipOffset != -1L) {}
    for (boolean bool = true;; bool = false)
    {
      localPlayerControls.init(bool, this.mPostrollFlag);
      this.mPlayerControls.setLayoutParams((ViewGroup.LayoutParams)localObject);
      this.mPlayerControls.setVisibility(8);
      localObject = this.mPlayerControls.getLearnText();
      if (localObject != null)
      {
        ((TextView)localObject).setText(this.mActionText);
        ((TextView)localObject).setTextSize(20 - this.mActionText.length() / 2);
        ((TextView)localObject).setOnTouchListener(new View.OnTouchListener()
        {
          public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
          {
            switch (paramAnonymousMotionEvent.getAction())
            {
            }
            do
            {
              return true;
              VASTActivity.access$102(VASTActivity.this, new Rect(paramAnonymousView.getLeft(), paramAnonymousView.getTop(), paramAnonymousView.getRight(), paramAnonymousView.getBottom()));
              Assets.setAlpha(paramAnonymousView, 0.75F);
              return true;
            } while (!VASTActivity.this.rekt.contains(paramAnonymousView.getLeft() + (int)paramAnonymousMotionEvent.getX(), paramAnonymousView.getTop() + (int)paramAnonymousMotionEvent.getY()));
            Assets.setAlpha(paramAnonymousView, 1.0F);
            VASTActivity.this.infoClicked(true);
            return true;
          }
        });
      }
      this.mPlayerControls.setSkipButtonListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          switch (paramAnonymousMotionEvent.getAction())
          {
          }
          for (;;)
          {
            return true;
            VASTActivity.access$102(VASTActivity.this, new Rect(paramAnonymousView.getLeft(), paramAnonymousView.getTop(), paramAnonymousView.getRight(), paramAnonymousView.getBottom()));
            Assets.setAlpha(paramAnonymousView, 0.75F);
            continue;
            if (VASTActivity.this.rekt.contains(paramAnonymousView.getLeft() + (int)paramAnonymousMotionEvent.getX(), paramAnonymousView.getTop() + (int)paramAnonymousMotionEvent.getY()))
            {
              Assets.setAlpha(paramAnonymousView, 1.0F);
              VASTActivity.this.skipClicked();
            }
          }
        }
      });
      this.mOverlay.addView(this.mPlayerControls);
      return;
    }
  }
  
  private void createPostroll()
  {
    if ((this.mEndCardHtml != null) && (this.mEndCardHtml.length() > 0))
    {
      this.mPostroll = new MRaidPostroll(this, this.mEndCardHtml, this);
      this.mPostroll.init();
      return;
    }
    String str = this.mVastModel.getVideoClicks().getClickThrough();
    if ((str != null) && (str.length() > 0)) {}
    for (boolean bool = true;; bool = false)
    {
      this.mPostroll = new DefaultPostroll(this, this, bool, this.mActionText);
      break;
    }
  }
  
  private void createProgressBar()
  {
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
    localLayoutParams.addRule(13);
    this.mProgressBar = new ProgressBar(this);
    this.mProgressBar.setLayoutParams(localLayoutParams);
    this.mRootLayout.addView(this.mProgressBar);
    this.mProgressBar.setVisibility(8);
  }
  
  private void createRootLayout(RelativeLayout.LayoutParams paramLayoutParams)
  {
    this.mRootLayout = new RelativeLayout(this);
    this.mRootLayout.setLayoutParams(paramLayoutParams);
    this.mRootLayout.setPadding(0, 0, 0, 0);
    this.mRootLayout.setBackgroundColor(-16777216);
  }
  
  private void createSurface(RelativeLayout.LayoutParams paramLayoutParams)
  {
    this.mSurfaceView = new SurfaceView(this);
    this.mSurfaceView.setLayoutParams(paramLayoutParams);
    this.mSurfaceHolder = this.mSurfaceView.getHolder();
    this.mSurfaceHolder.addCallback(this);
    this.mSurfaceHolder.setType(3);
    this.mRootLayout.addView(this.mSurfaceView);
  }
  
  private void createUIComponents()
  {
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
    createRootLayout(localLayoutParams);
    createSurface(localLayoutParams);
    createMediaPlayer();
    createOverlay(localLayoutParams);
    createPlayerControls();
    if (this.mPostrollFlag) {
      createPostroll();
    }
    setContentView(this.mRootLayout);
    createProgressBar();
  }
  
  private void finishVAST()
  {
    cleanActivityUp();
    try
    {
      VASTPlayer.currentPlayer.listener.vastDismiss();
      VASTPlayer.currentPlayer.setLoaded(false);
      finish();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;) {}
    }
  }
  
  private void fireUrl(String paramString1, String paramString2)
  {
    if (paramString2 != null)
    {
      HttpTools.httpGetURL(paramString1, paramString2, VASTPlayer.currentPlayer);
      return;
    }
    FuseLog.d(TAG, "\turl is null");
  }
  
  private void fireUrls(String paramString, List<String> paramList)
  {
    if (paramList != null)
    {
      paramList = paramList.iterator();
      while (paramList.hasNext()) {
        HttpTools.httpGetURL(paramString, (String)paramList.next(), VASTPlayer.currentPlayer);
      }
    }
    FuseLog.d(TAG, "\turl list is null");
  }
  
  private void hideProgressBar()
  {
    this.mProgressBar.setVisibility(8);
  }
  
  private void hideTitleStatusBars()
  {
    requestWindowFeature(1);
    getWindow().setFlags(1024, 1024);
  }
  
  private void overlayClicked()
  {
    startToolBarTimer();
  }
  
  private void processClickThroughEvent()
  {
    Object localObject = this.mVastModel.getVideoClicks().getClickThrough();
    FuseLog.v(TAG, "clickThrough url: " + (String)localObject);
    fireUrls("click", this.mVastModel.getVideoClicks().getClickTracking());
    try
    {
      localObject = new Intent("android.intent.action.VIEW", Uri.parse((String)localObject));
      if (getPackageManager().resolveActivity((Intent)localObject, 32) == null)
      {
        FuseLog.d(TAG, "Clickthrough error occured, uri unresolvable");
        if (this.mCurrentVideoPosition >= this.mMediaPlayer.getCurrentPosition() * 0.99D) {
          this.mMediaPlayer.start();
        }
        activateButtons(true);
        return;
      }
      startActivity((Intent)localObject);
      return;
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  private void processErrorEvent()
  {
    fireUrls("error", this.mVastModel.getErrorUrl());
  }
  
  private void processEvent(TRACKING_EVENTS_TYPE paramTRACKING_EVENTS_TYPE)
  {
    Object localObject = (List)this.mTrackingEventMap.get(paramTRACKING_EVENTS_TYPE);
    ArrayList localArrayList = new ArrayList();
    if (localObject == null) {
      return;
    }
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      localArrayList.add(((VASTTracking)((Iterator)localObject).next()).getValue());
    }
    fireUrls(paramTRACKING_EVENTS_TYPE.name(), localArrayList);
  }
  
  private void processImpressions()
  {
    this.mIsProcessedImpressions = true;
    fireUrls("impression", this.mVastModel.getImpressions());
  }
  
  private void processPauseSteps()
  {
    this.mIsVideoPaused = true;
    this.mMediaPlayer.pause();
    this.shouldPlayOnResume = true;
    stopVideoProgressTimer();
    stopToolBarTimer();
  }
  
  private void processPlaySteps()
  {
    this.mIsVideoPaused = false;
    this.mMediaPlayer.start();
    startToolBarTimer();
    startVideoProgressTimer();
  }
  
  private void processProgressEvent(VASTTracking paramVASTTracking)
  {
    if (paramVASTTracking == null) {
      return;
    }
    fireUrl(paramVASTTracking.getEvent().toString(), paramVASTTracking.getValue());
  }
  
  private void resetPlayerToBeginning()
  {
    this.showingPostroll = false;
    this.mIsCompleted = false;
    this.mQuartile = 0;
    createPlayerControls();
    surfaceCreated(this.mSurfaceHolder);
  }
  
  private void resolveSkipOffset()
  {
    String str;
    if (this.mVersion.equals("3.0"))
    {
      str = this.mVastModel.getSkipOffset();
      if ((str == null) || (str.length() == 0)) {
        this.mSkipOffset = this.mSkipOffsetServer;
      }
    }
    for (;;)
    {
      FuseLog.v(TAG, "skipOffset:  " + this.mSkipOffset);
      return;
      if (str.endsWith("%"))
      {
        this.mSkipOffsetRelative = true;
        this.mSkipOffset = Long.parseLong(str.substring(0, str.indexOf("%")));
      }
      else
      {
        this.mSkipOffset = Assets.parseOffset(str);
        continue;
        this.mSkipOffset = this.mSkipOffsetServer;
      }
    }
  }
  
  private void showPostroll()
  {
    this.showingPostroll = true;
    cleanActivityUp();
    this.mPostroll.show(this.mRootLayout);
  }
  
  private void showProgressBar()
  {
    this.mProgressBar.setVisibility(0);
  }
  
  private void skipClicked()
  {
    if (this.mPostrollFlag)
    {
      cleanActivityUp();
      showPostroll();
    }
    for (;;)
    {
      if (this.mVersion.equals("3.0")) {
        processEvent(TRACKING_EVENTS_TYPE.skip);
      }
      VASTPlayer.currentPlayer.listener.vastSkip();
      return;
      finishVAST();
    }
  }
  
  private void startToolBarTimer()
  {
    if (this.mQuartile == 4) {}
    do
    {
      return;
      if ((this.mMediaPlayer != null) && (this.mMediaPlayer.isPlaying()))
      {
        stopToolBarTimer();
        this.mToolBarTimer = new Timer();
        this.mToolBarTimer.schedule(new TimerTask()
        {
          public void run()
          {
            VASTActivity.this.mHandler.post(new Runnable()
            {
              public void run()
              {
                VASTActivity.this.mPlayerControls.setVisibility(8);
              }
            });
          }
        }, 3000L);
        this.mPlayerControls.setVisibility(0);
      }
    } while (!this.mIsVideoPaused);
    activateButtons(true);
  }
  
  private void startTrackingEventTimer()
  {
    stopTrackingEventTimer();
    if (this.mIsCompleted) {
      return;
    }
    final int i = this.mMediaPlayer.getDuration();
    final Object localObject = null;
    try
    {
      List localList = (List)this.mTrackingEventMap.get(TRACKING_EVENTS_TYPE.progress);
      localObject = localList;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    this.mTrackingEventTimer = new Timer();
    this.mTrackingEventTimer.scheduleAtFixedRate(new TimerTask()
    {
      public void run()
      {
        for (;;)
        {
          int i;
          int j;
          VASTTracking localVASTTracking;
          try
          {
            i = VASTActivity.this.mMediaPlayer.getCurrentPosition();
            if (i == 0) {
              return;
            }
            j = i * 100 / i;
            if (j >= VASTActivity.this.mQuartile * 25)
            {
              if (VASTActivity.this.mQuartile == 0)
              {
                VASTActivity.this.processEvent(TRACKING_EVENTS_TYPE.start);
                VASTPlayer.currentPlayer.listener.vastProgress(0);
                VASTActivity.access$708(VASTActivity.this);
              }
            }
            else
            {
              if ((!VASTActivity.this.mVersion.equals("3.0")) || (localObject == null)) {
                break;
              }
              Iterator localIterator = localObject.iterator();
              if (!localIterator.hasNext()) {
                break;
              }
              localVASTTracking = (VASTTracking)localIterator.next();
              if (!localVASTTracking.isOffsetRelative()) {
                break label342;
              }
              if ((j < localVASTTracking.getParsedOffset()) || (localVASTTracking.isConsumed())) {
                continue;
              }
              VASTActivity.this.processProgressEvent(localVASTTracking);
              localVASTTracking.setConsumed(true);
              VASTPlayer.currentPlayer.listener.vastProgress(j);
              continue;
            }
            if (VASTActivity.this.mQuartile != 1) {
              break label261;
            }
          }
          catch (Exception localException)
          {
            FuseLog.w(VASTActivity.TAG, "mediaPlayer.getCurrentPosition exception: " + localException.getMessage());
            cancel();
            return;
          }
          VASTActivity.this.processEvent(TRACKING_EVENTS_TYPE.firstQuartile);
          VASTPlayer.currentPlayer.listener.vastProgress(25);
          continue;
          label261:
          if (VASTActivity.this.mQuartile == 2)
          {
            VASTActivity.this.processEvent(TRACKING_EVENTS_TYPE.midpoint);
            VASTPlayer.currentPlayer.listener.vastProgress(50);
          }
          else if (VASTActivity.this.mQuartile == 3)
          {
            VASTActivity.this.processEvent(TRACKING_EVENTS_TYPE.thirdQuartile);
            VASTPlayer.currentPlayer.listener.vastProgress(75);
            VASTActivity.this.stopTrackingEventTimer();
            continue;
            label342:
            if ((i >= localVASTTracking.getParsedOffset()) && (!localVASTTracking.isConsumed()))
            {
              VASTActivity.this.processProgressEvent(localVASTTracking);
              localVASTTracking.setConsumed(true);
              VASTPlayer.currentPlayer.listener.vastProgress(j);
            }
          }
        }
      }
    }, 0L, 250L);
  }
  
  private void startVideoProgressTimer()
  {
    this.mStartVideoProgressTimer = new Timer();
    this.mVideoProgressTracker = new LinkedList();
    this.mPlayerControls.setTimes(this.mVideoDuration, this.mSkipOffset);
    this.mStartVideoProgressTimer.schedule(new TimerTask()
    {
      int maxAmountInList = 19;
      
      public void run()
      {
        if (VASTActivity.this.mMediaPlayer == null) {
          return;
        }
        int i;
        if (VASTActivity.this.mVideoProgressTracker.size() == this.maxAmountInList)
        {
          i = ((Integer)VASTActivity.this.mVideoProgressTracker.getFirst()).intValue();
          if (((Integer)VASTActivity.this.mVideoProgressTracker.getLast()).intValue() > i) {
            VASTActivity.this.mVideoProgressTracker.removeFirst();
          }
        }
        try
        {
          i = VASTActivity.this.mMediaPlayer.getCurrentPosition();
          VASTActivity.this.runOnUiThread(new Runnable()
          {
            public void run()
            {
              if (VASTActivity.this.mPlayerControls != null) {
                VASTActivity.this.mPlayerControls.update(33L);
              }
            }
          });
          VASTActivity.this.mVideoProgressTracker.addLast(Integer.valueOf(i));
          return;
        }
        catch (Exception localException) {}
      }
    }, 0L, 33L);
  }
  
  private void stopToolBarTimer()
  {
    if (this.mToolBarTimer != null)
    {
      this.mToolBarTimer.cancel();
      this.mToolBarTimer = null;
    }
  }
  
  private void stopTrackingEventTimer()
  {
    if (this.mTrackingEventTimer != null)
    {
      this.mTrackingEventTimer.cancel();
      this.mTrackingEventTimer = null;
    }
  }
  
  private void stopVideoProgressTimer()
  {
    if (this.mStartVideoProgressTimer != null) {
      this.mStartVideoProgressTimer.cancel();
    }
  }
  
  public void closeClicked()
  {
    cleanActivityUp();
    if (!this.mIsPlayBackError) {
      processEvent(TRACKING_EVENTS_TYPE.close);
    }
    finishVAST();
  }
  
  public void infoClicked(boolean paramBoolean)
  {
    if (VASTPlayer.currentPlayer.listener != null) {
      VASTPlayer.currentPlayer.listener.vastClick();
    }
    activateButtons(false);
    if (paramBoolean) {
      processClickThroughEvent();
    }
    closeClicked();
  }
  
  public void onBackPressed()
  {
    if (this.mSkipOffset > 0L)
    {
      if (!this.mIsCompleted) {
        VASTPlayer.currentPlayer.listener.vastSkip();
      }
      closeClicked();
      super.onBackPressed();
    }
  }
  
  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    cleanActivityUp();
    if ((!this.mIsPlayBackError) && (!this.mIsCompleted))
    {
      this.mIsCompleted = true;
      processEvent(TRACKING_EVENTS_TYPE.complete);
      if (VASTPlayer.currentPlayer.listener != null)
      {
        VASTPlayer.currentPlayer.listener.vastProgress(100);
        VASTPlayer.currentPlayer.listener.vastComplete();
        if (this.mIsRewarded) {
          VASTPlayer.currentPlayer.listener.vastRewardedVideoComplete();
        }
      }
    }
    if (this.mPostrollFlag)
    {
      showPostroll();
      return;
    }
    closeClicked();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getIntent();
    this.mPostrollFlag = paramBundle.getBooleanExtra("postroll", false);
    this.mEndCardHtml = paramBundle.getStringExtra("endCardHtml");
    this.mSkipOffsetServer = paramBundle.getLongExtra("skipOffset", 0L);
    this.mIsRewarded = paramBundle.getBooleanExtra("rewarded", true);
    this.mActionText = paramBundle.getStringExtra("actionText");
    this.mVastModel = ((VASTModel)paramBundle.getSerializableExtra("com.nexage.android.vast.player.vastModel"));
    if (this.mVastModel == null)
    {
      FuseLog.d(TAG, "vastModel is null. Stopping activity.");
      finishVAST();
      return;
    }
    if (VASTPlayer.currentPlayer == null)
    {
      FuseLog.d(TAG, "currentPlayer is null. Stopping activity.");
      finishVAST();
      return;
    }
    hideTitleStatusBars();
    this.mHandler = new Handler();
    displayMetrics = getResources().getDisplayMetrics();
    this.mScreenWidth = displayMetrics.widthPixels;
    this.mScreenHeight = displayMetrics.heightPixels;
    this.mVersion = this.mVastModel.getVastVersion();
    this.mTrackingEventMap = this.mVastModel.getTrackingEvents();
    createUIComponents();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    cleanActivityUp();
  }
  
  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    this.mIsPlayBackError = true;
    FuseLog.w(TAG, "Shutting down Activity due to Media Player errors: WHAT:" + paramInt1 + ": EXTRA:" + paramInt2 + ":");
    VASTPlayer.currentPlayer.listener.vastError(405);
    processErrorEvent();
    closeClicked();
    return true;
  }
  
  public void onOpenMRaidUrl(String paramString)
  {
    FuseLog.i(TAG, "Opening MRAID Postroll click through link: " + paramString);
    paramString = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
    if (paramString.resolveActivity(getPackageManager()) != null) {
      startActivity(paramString);
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    if (this.mMediaPlayer != null)
    {
      processPauseSteps();
      this.mCurrentVideoPosition = this.mMediaPlayer.getCurrentPosition();
    }
  }
  
  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    this.mVideoDuration = this.mMediaPlayer.getDuration();
    this.mVideoWidth = this.mMediaPlayer.getVideoWidth();
    this.mVideoHeight = this.mMediaPlayer.getVideoHeight();
    if (this.mSkipOffsetRelative) {
      this.mSkipOffset = ((this.mVideoDuration / 100.0F * (float)this.mSkipOffset));
    }
    calculateAspectRatio();
    hideProgressBar();
    if (this.mIsVideoPaused) {
      this.mMediaPlayer.pause();
    }
    for (;;)
    {
      if (this.mCurrentVideoPosition > 0) {
        this.mMediaPlayer.seekTo(this.mCurrentVideoPosition);
      }
      if ((!this.mMediaPlayer.isPlaying()) && (!this.mIsVideoPaused))
      {
        this.mMediaPlayer.start();
        VASTPlayer.currentPlayer.listener.vastDisplay();
        startTrackingEventTimer();
        startToolBarTimer();
      }
      if ((!this.mIsProcessedImpressions) && (this.mMediaPlayer.isPlaying())) {
        processImpressions();
      }
      return;
      startVideoProgressTimer();
    }
  }
  
  protected void onRestart()
  {
    super.onRestart();
  }
  
  public void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
  }
  
  protected void onResume()
  {
    super.onResume();
    if ((this.shouldPlayOnResume) && (this.mMediaPlayer != null)) {
      surfaceCreated(this.mSurfaceHolder);
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
  }
  
  public void onSeekComplete(MediaPlayer paramMediaPlayer)
  {
    this.mMediaPlayer.start();
  }
  
  protected void onStart()
  {
    super.onStart();
  }
  
  protected void onStop()
  {
    super.onStop();
  }
  
  public void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    this.mVideoWidth = paramInt1;
    this.mVideoHeight = paramInt2;
    calculateAspectRatio();
    FuseLog.v(TAG, "video size: " + this.mVideoWidth + "x" + this.mVideoHeight);
  }
  
  public void replayedClicked()
  {
    createPostroll();
    resetPlayerToBeginning();
    VASTPlayer.currentPlayer.listener.vastReplay();
  }
  
  public void setRequestedOrientation(int paramInt)
  {
    super.setRequestedOrientation(paramInt);
  }
  
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (this.showingPostroll) {
      return;
    }
    try
    {
      if (this.mMediaPlayer == null) {
        createMediaPlayer();
      }
      showProgressBar();
      this.mMediaPlayer.setDisplay(paramSurfaceHolder);
      if ((this.mMediaPlayer != null) && (this.mIsVideoPaused))
      {
        processPlaySteps();
        hideProgressBar();
        return;
      }
    }
    catch (Exception paramSurfaceHolder)
    {
      FuseLog.w(TAG, paramSurfaceHolder.getMessage(), paramSurfaceHolder);
      VASTPlayer.currentPlayer.listener.vastError(405);
      finishVAST();
      return;
    }
    paramSurfaceHolder = this.mVastModel.getPickedMediaFileLocation();
    this.mMediaPlayer.setDataSource(paramSurfaceHolder);
    if (this.mVastModel.getPickedMediaFileDeliveryType().equals("streaming"))
    {
      this.mMediaPlayer.prepareAsync();
      return;
    }
    this.mMediaPlayer.prepare();
  }
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    this.shouldPlayOnResume = false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/activity/VASTActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */