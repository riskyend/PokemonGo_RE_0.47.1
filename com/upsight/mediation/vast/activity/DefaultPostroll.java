package com.upsight.mediation.vast.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.upsight.mediation.vast.Postroll.Postroll;
import com.upsight.mediation.vast.Postroll.Postroll.Listener;
import com.upsight.mediation.vast.Postroll.VasButton;
import com.upsight.mediation.vast.util.Assets;

public class DefaultPostroll
  extends RelativeLayout
  implements Postroll
{
  private static final int BUTTON_HEIGHT = 75;
  private static final int BUTTON_WIDTH = 256;
  private static final int MARGIN = 8;
  private static final float SCALED_DENSITY = VASTActivity.displayMetrics.scaledDensity;
  public static final int TEXT_FONT_SIZE = 28;
  private static final int X_HEIGHT = 29;
  private static final int X_WIDTH = 29;
  private final String mActionButtonText;
  private Context mContext;
  private ImageView mLastFrameView;
  private LinearLayout mLayerOfDarkness;
  private Button mLearnButton;
  private final Postroll.Listener mListener;
  private final boolean mShouldShowActionButton;
  private ImageView mXButtonView;
  
  public DefaultPostroll(Context paramContext, Postroll.Listener paramListener, boolean paramBoolean, String paramString)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mListener = paramListener;
    this.mShouldShowActionButton = paramBoolean;
    this.mActionButtonText = paramString;
  }
  
  private void setUpLastFrameView(Context paramContext)
  {
    this.mLastFrameView = new ImageView(paramContext);
    this.mLastFrameView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    addView(this.mLastFrameView);
  }
  
  private void setUpLayerOfDarkness(Context paramContext)
  {
    this.mLayerOfDarkness = new LinearLayout(paramContext);
    paramContext = new LinearLayout.LayoutParams(-1, -1);
    this.mLayerOfDarkness.setLayoutParams(paramContext);
    this.mLayerOfDarkness.setBackgroundColor(Color.argb(192, 0, 0, 0));
    addView(this.mLayerOfDarkness);
  }
  
  private void setUpLearn(Context paramContext)
  {
    if (this.mShouldShowActionButton)
    {
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(Assets.convertToDps(256.0F), Assets.convertToDps(75.0F));
      localLayoutParams.addRule(13);
      this.mLearnButton = new VasButton(paramContext);
      this.mLearnButton.setTypeface(Typeface.create("sans-serif-light", 0));
      this.mLearnButton.setMaxLines(1);
      this.mLearnButton.setLayoutParams(localLayoutParams);
      this.mLearnButton.setGravity(17);
      Assets.setImage(this.mLearnButton, Assets.getPostrollButton(getResources()));
      this.mLearnButton.setEnabled(true);
      this.mLearnButton.setVisibility(0);
      this.mLearnButton.setTextColor(-1);
      this.mLearnButton.setText(this.mActionButtonText);
      this.mLearnButton.setTextSize(28 - this.mActionButtonText.length() / 2);
      this.mLearnButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          DefaultPostroll.this.mListener.infoClicked(true);
        }
      });
      addView(this.mLearnButton);
    }
  }
  
  private void setUpX(Context paramContext)
  {
    this.mXButtonView = new ImageView(paramContext);
    Assets.setImage(this.mXButtonView, Assets.getXButton(getResources()));
    Assets.setAlpha(this.mXButtonView, 0.8F);
    paramContext = new RelativeLayout.LayoutParams(Assets.convertToDps(29.0F), Assets.convertToDps(29.0F));
    paramContext.addRule(11);
    int i = Assets.convertToDps(8.0F);
    paramContext.setMargins(0, i, i, 0);
    this.mXButtonView.setLayoutParams(paramContext);
    this.mXButtonView.setId(16908327);
    this.mXButtonView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DefaultPostroll.this.hide();
        DefaultPostroll.this.mListener.closeClicked();
      }
    });
    addView(this.mXButtonView);
  }
  
  public void hide()
  {
    ((ViewGroup)getParent()).removeView(this);
  }
  
  public void init()
  {
    setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    setUpLastFrameView(this.mContext);
    setUpLayerOfDarkness(this.mContext);
    setUpLearn(this.mContext);
    setUpX(this.mContext);
  }
  
  public boolean isReady()
  {
    return true;
  }
  
  public void show(ViewGroup paramViewGroup)
  {
    paramViewGroup.addView(this);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/activity/DefaultPostroll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */