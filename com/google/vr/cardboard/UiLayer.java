package com.google.vr.cardboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class UiLayer
{
  private static final int ALIGNMENT_MARKER_LINE_COLOR = -13487566;
  private static final int ALIGNMENT_MARKER_LINE_WIDTH = 4;
  private static final int ICON_WIDTH_DP = 28;
  private static final String TAG = UiLayer.class.getSimpleName();
  private static final float TOUCH_SLOP_FACTOR = 1.5F;
  private View alignmentMarker;
  private ImageView backButton;
  private volatile Runnable backButtonRunnable = null;
  private final Drawable backIconDrawable;
  private final Context context;
  private volatile boolean isAlignmentMarkerEnabled = false;
  private volatile boolean isSettingsButtonEnabled = true;
  private final DisplayMetrics metrics;
  private final RelativeLayout rootLayout;
  private ImageView settingsButton;
  private final Drawable settingsIconDrawable;
  
  public UiLayer(Context paramContext)
  {
    if (!(paramContext instanceof Activity)) {
      throw new RuntimeException("Context is not an instance of activity: Aborting.");
    }
    this.context = paramContext;
    this.settingsIconDrawable = decodeBitmapFromString("iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAQAAAD/5HvMAAADEklEQVRoBe3BXWjVZQDH8d/0HKW00OZ0kh6XiKUiIl0okhARdBcEjUQSmViYkF14K+TCnTNDEd9ShMGgFGZC9HaZqElo0aZDkhAkt2b5np7j8e3P+XazwWE8/+floA9enM9Hqqure0oxn0HSDPCyYqMDm82KjcPYHFRs9GHzq2KjiM1NxUUzLo2KieW4LFVMrMFllWIij0u7Hi/GcIRr7GexRmE8H/E3LgOsY7xG4VUOcJVDNCgUaxnxG2uZoGGsYABfl3hPw5jIh/zOiNUKw/NcodptvmARzfxIqO+ZymL2c4dql5moEGzDpEwtypgU5I+5PORJu89s+eIHYvhGfniLWN6QGxn+IJZ+xsqFT4hpveyYwi1ius5k2bCPUBV66SJPni76qBBql9LxIgkhinQwU1XIUaBEiEc0KQ1TKePvKDNkQI7j+CsxSelYxhB+DpFRCrL04GeQJbJjGsdwO0pGFmQ5gdtPNMmNDNuwKzJDDuQoYdfJWPniXYqk65AHOkl3m3cUhnmcx6zCTHlgFhXMzjFX4ViJWa88cRazVtWCjZh1yRPdmG1QLfgMs7w8UcBsk2pBO2Z5eaKA2SbVgo2YdckT3ZhtUC1YiVmfPNGPWavCsYA/MauQkwdaqGB2nnkKwwpKpCvIA1tJV6RVvsiyE7sSOTnQQhm77WTkxnR+xu04WVkwjpO4HWOa7FjOP/jpIasUjONr/AyxTOlo4h7+TpCTAS2cxN9dJisN00kIUaKTWapCC1spE+IhU5SOPYSqcJZuChTopp8KoXbIhhe4QUzXmCQ7PiamdXIhwzliOcMYufEmsbwuP3xLDEfkizk84Em7x0vyx+eYlKhFEZMOheA5/qXaLXaygCa+I9RhGlnIbv6j2hATFIY1jPiF1TyjYbRyEV8XeFvDeJY2TjFilULRwEEG2c1CjUKWDxjA5S/ayGgUFrGXy3xJgx4v8ri0KybacHlfMfEaLksUE824NCou7mBzU7HRi81pxUYPNl8pNrZg86li4xUukpCQkJCQkJCQkJDwiAvMUV1d3VPqfz17MXquI1uXAAAAAElFTkSuQmCC");
    this.backIconDrawable = decodeBitmapFromString("iVBORw0KGgoAAAANSUhEUgAAAGAAAABgCAQAAABIkb+zAAAArklEQVR42u3VyRHCMBBFwQkAcoQ8WJQbEJewby4XRxkzQ3cE/1VpiQAAANhEb73lnj9ruednTVjMz5iwmp8t4cP82Tn3/Ec/mG+++eabb7755ptvvvnmm2+++eYPmv8FyecPCdhz/oCAfecLKHCEClziAs9ogY9MggQJ/5DwlCBBQp2EowQJEmokvCRIkCDhZxJOEZkTLpHLKuEa+SwSMs5fJNwirynhHgAAAJt4A/ZvpX5veSF2AAAAAElFTkSuQmCC");
    Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    this.metrics = new DisplayMetrics();
    if (Build.VERSION.SDK_INT >= 17) {
      localDisplay.getRealMetrics(this.metrics);
    }
    for (;;)
    {
      this.rootLayout = new RelativeLayout(paramContext);
      initializeViews();
      return;
      localDisplay.getMetrics(this.metrics);
    }
  }
  
  private ImageView createButton(Drawable paramDrawable, boolean paramBoolean, int... paramVarArgs)
  {
    int j = (int)(28.0F * this.metrics.density);
    int i = (int)(j * 1.5F);
    j = (i - j) / 2;
    ImageView localImageView = new ImageView(this.context);
    localImageView.setPadding(j, j, j, j);
    localImageView.setImageDrawable(paramDrawable);
    localImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    paramDrawable = new RelativeLayout.LayoutParams(i, i);
    j = paramVarArgs.length;
    i = 0;
    while (i < j)
    {
      paramDrawable.addRule(paramVarArgs[i]);
      i += 1;
    }
    localImageView.setLayoutParams(paramDrawable);
    if (paramBoolean) {}
    for (i = 0;; i = 4)
    {
      localImageView.setVisibility(i);
      return localImageView;
    }
  }
  
  private Drawable decodeBitmapFromString(String paramString)
  {
    paramString = Base64.decode(paramString, 0);
    paramString = BitmapFactory.decodeByteArray(paramString, 0, paramString.length);
    return new BitmapDrawable(this.context.getResources(), paramString);
  }
  
  private void initializeViews()
  {
    int i = 0;
    int j = (int)((int)(28.0F * this.metrics.density) * 1.5F);
    this.settingsButton = createButton(this.settingsIconDrawable, this.isSettingsButtonEnabled, new int[] { 12, 13 });
    this.settingsButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UiUtils.launchOrInstallCardboard(paramAnonymousView.getContext());
      }
    });
    this.rootLayout.addView(this.settingsButton);
    this.backButton = createButton(this.backIconDrawable, getBackButtonEnabled(), new int[] { 10, 9 });
    this.backButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = UiLayer.this.backButtonRunnable;
        if (paramAnonymousView != null) {
          paramAnonymousView.run();
        }
      }
    });
    this.rootLayout.addView(this.backButton);
    this.alignmentMarker = new View(this.context);
    this.alignmentMarker.setBackground(new ColorDrawable(-13487566));
    Object localObject = new RelativeLayout.LayoutParams((int)(4.0F * this.metrics.density), -1);
    ((RelativeLayout.LayoutParams)localObject).addRule(13);
    ((RelativeLayout.LayoutParams)localObject).setMargins(0, j, 0, j);
    this.alignmentMarker.setLayoutParams((ViewGroup.LayoutParams)localObject);
    localObject = this.alignmentMarker;
    if (this.isAlignmentMarkerEnabled) {}
    for (;;)
    {
      ((View)localObject).setVisibility(i);
      this.rootLayout.addView(this.alignmentMarker);
      return;
      i = 8;
    }
  }
  
  public void attachUiLayer(final ViewGroup paramViewGroup)
  {
    ((Activity)this.context).runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (paramViewGroup == null)
        {
          ((Activity)UiLayer.this.context).addContentView(UiLayer.this.rootLayout, new RelativeLayout.LayoutParams(-1, -1));
          return;
        }
        paramViewGroup.addView(UiLayer.this.rootLayout);
      }
    });
  }
  
  public boolean getAlignmentMarkerEnabled()
  {
    return this.isAlignmentMarkerEnabled;
  }
  
  public boolean getBackButtonEnabled()
  {
    return this.backButtonRunnable != null;
  }
  
  public boolean getSettingsButtonEnabled()
  {
    return this.isSettingsButtonEnabled;
  }
  
  public void setAlignmentMarkerEnabled(final boolean paramBoolean)
  {
    this.isAlignmentMarkerEnabled = paramBoolean;
    ((Activity)this.context).runOnUiThread(new Runnable()
    {
      public void run()
      {
        View localView = UiLayer.this.alignmentMarker;
        if (paramBoolean) {}
        for (int i = 0;; i = 4)
        {
          localView.setVisibility(i);
          return;
        }
      }
    });
  }
  
  public void setBackButtonListener(final Runnable paramRunnable)
  {
    this.backButtonRunnable = paramRunnable;
    ((Activity)this.context).runOnUiThread(new Runnable()
    {
      public void run()
      {
        ImageView localImageView = UiLayer.this.backButton;
        if (paramRunnable == null) {}
        for (int i = 4;; i = 0)
        {
          localImageView.setVisibility(i);
          return;
        }
      }
    });
  }
  
  public void setEnabled(final boolean paramBoolean)
  {
    ((Activity)this.context).runOnUiThread(new Runnable()
    {
      public void run()
      {
        RelativeLayout localRelativeLayout = UiLayer.this.rootLayout;
        if (paramBoolean) {}
        for (int i = 0;; i = 4)
        {
          localRelativeLayout.setVisibility(i);
          return;
        }
      }
    });
  }
  
  public void setSettingsButtonEnabled(final boolean paramBoolean)
  {
    this.isSettingsButtonEnabled = paramBoolean;
    ((Activity)this.context).runOnUiThread(new Runnable()
    {
      public void run()
      {
        ImageView localImageView = UiLayer.this.settingsButton;
        if (paramBoolean) {}
        for (int i = 0;; i = 4)
        {
          localImageView.setVisibility(i);
          return;
        }
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/vr/cardboard/UiLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */