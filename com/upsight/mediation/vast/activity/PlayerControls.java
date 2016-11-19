package com.upsight.mediation.vast.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.upsight.mediation.vast.model.VASTModel;
import com.upsight.mediation.vast.model.VideoClicks;
import com.upsight.mediation.vast.util.Assets;

public class PlayerControls
  extends RelativeLayout
{
  private static final int BUTTON_HEIGHT = 38;
  private static final int BUTTON_WIDTH = 128;
  public static final float DOWN_STATE = 0.75F;
  private static final int MARGIN = 8;
  private static final int PROGRESS_RING_RADIUS = 10;
  private static final int PROGRESS_RING_VIEW_HEIGHT = 25;
  private static final int PROGRESS_RING_VIEW_WIDTH = 25;
  private static final int PROGRESS_RING_WIDTH = 25;
  public static final int TEXT_FONT_SIZE = 20;
  private static final int TIME_FONT_SIZE = 10;
  private CircleDrawable circleDrawable;
  private long elapsedTime = 0L;
  private Context mContext;
  private TextView mLearnText;
  private long mRemainder;
  private LinearLayout mSkipButton;
  private long mSkipOffset;
  private TextView mSkipText;
  private boolean mSkippable;
  private TextView mTimeText;
  private FrameLayout mTimerRing;
  private VASTModel mVastModel;
  private ImageView progressCircle;
  private View.OnTouchListener skipListener;
  
  public PlayerControls(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public PlayerControls(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }
  
  public PlayerControls(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
  }
  
  private void setUpLearn(Context paramContext)
  {
    Object localObject = this.mVastModel.getVideoClicks().getClickThrough();
    if ((localObject != null) && (((String)localObject).length() > 0))
    {
      localObject = new RelativeLayout.LayoutParams(Assets.convertToDps(128.0F), Assets.convertToDps(38.0F));
      ((RelativeLayout.LayoutParams)localObject).addRule(9);
      int i = Assets.convertToDps(8.0F);
      ((RelativeLayout.LayoutParams)localObject).setMargins(i, i, 0, 0);
      this.mLearnText = new TextView(paramContext);
      this.mLearnText.setText("LEARN MORE");
      this.mLearnText.setTypeface(Typeface.create("sans-serif-light", 0));
      this.mLearnText.setTextSize(20.0F);
      this.mLearnText.setMaxLines(1);
      this.mLearnText.setLayoutParams((ViewGroup.LayoutParams)localObject);
      this.mLearnText.setGravity(17);
      Assets.setImage(this.mLearnText, Assets.getPlayerUIButton(getResources()));
      this.mLearnText.setEnabled(true);
      this.mLearnText.setVisibility(0);
      addView(this.mLearnText);
    }
  }
  
  private void setUpSkipButton(Context paramContext, boolean paramBoolean)
  {
    this.mSkippable = paramBoolean;
    this.mSkipButton = new LinearLayout(paramContext);
    this.mSkipButton.setOrientation(0);
    this.mSkipButton.setGravity(17);
    Assets.setImage(this.mSkipButton, Assets.getPlayerUIButton(getResources()));
    int i = Assets.convertToDps(128.0F);
    int j = Assets.convertToDps(38.0F);
    int k = Assets.convertToDps(8.0F);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(i, j);
    localLayoutParams.addRule(11);
    localLayoutParams.setMargins(0, k, k, 0);
    this.mSkipButton.setLayoutParams(localLayoutParams);
    setUpTimerRing(paramContext);
    setUpSkipText(paramContext);
    Assets.setAlpha(this.mSkipButton, 0.75F);
    addView(this.mSkipButton);
  }
  
  private void setUpSkipText(Context paramContext)
  {
    this.mSkipText = new TextView(paramContext);
    if (this.mSkippable) {
      this.mSkipText.setText("SKIP");
    }
    for (;;)
    {
      this.mSkipText.setTypeface(Typeface.create("sans-serif-light", 0));
      this.mSkipText.setTextSize(20.0F);
      this.mSkipButton.addView(this.mSkipText);
      return;
      this.mSkipText.setText("sec");
    }
  }
  
  private void setUpTimerRing(Context paramContext)
  {
    this.mTimerRing = new FrameLayout(paramContext);
    int i = Assets.convertToDps(7.0F);
    this.mTimerRing.setPadding(0, 0, i, 0);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2);
    localLayoutParams.gravity = 17;
    this.circleDrawable = new CircleDrawable(Assets.convertToDps(25.0F), Assets.convertToDps(10.0F));
    this.progressCircle = new ImageView(paramContext);
    this.progressCircle.setMinimumWidth(Assets.convertToDps(25.0F));
    this.progressCircle.setMinimumHeight(Assets.convertToDps(25.0F));
    this.progressCircle.setImageDrawable(this.circleDrawable);
    this.progressCircle.setLayoutParams(localLayoutParams);
    this.mTimeText = new TextView(paramContext);
    this.mTimeText.setTypeface(Typeface.create("sans-serif-light", 0));
    this.mTimeText.setTextSize(10.0F);
    this.mTimeText.setLayoutParams(localLayoutParams);
    this.mTimerRing.addView(this.progressCircle);
    this.mTimerRing.addView(this.mTimeText);
    this.mSkipButton.addView(this.mTimerRing);
  }
  
  public TextView getLearnText()
  {
    return this.mLearnText;
  }
  
  public void init(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (((!paramBoolean2) && (!paramBoolean1)) || (paramBoolean1)) {
      setUpLearn(this.mContext);
    }
    setUpSkipButton(this.mContext, paramBoolean1);
  }
  
  public void setSkipButtonListener(View.OnTouchListener paramOnTouchListener)
  {
    this.skipListener = paramOnTouchListener;
  }
  
  public void setTimes(long paramLong1, long paramLong2)
  {
    this.mSkipOffset = paramLong2;
    this.mRemainder = paramLong1;
    this.circleDrawable.setTimer(paramLong1);
  }
  
  public void setVastModel(VASTModel paramVASTModel)
  {
    this.mVastModel = paramVASTModel;
  }
  
  public void update(long paramLong)
  {
    this.elapsedTime += paramLong;
    this.mTimeText.setText(Math.abs((this.elapsedTime - this.mRemainder) / 1000L) + "");
    if (this.elapsedTime > this.mRemainder)
    {
      this.circleDrawable.setSweepAngle(0.0F);
      this.progressCircle.invalidate();
      return;
    }
    if ((this.elapsedTime > this.mSkipOffset) && (this.mSkippable))
    {
      this.mSkipOffset = this.mRemainder;
      this.mSkipButton.setOnTouchListener(this.skipListener);
      Assets.setAlpha(this.mSkipButton, 1.0F);
    }
    this.circleDrawable.update(this.elapsedTime);
    this.progressCircle.invalidate();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/activity/PlayerControls.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */