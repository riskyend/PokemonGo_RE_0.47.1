package spacemadness.com.lunarconsole.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ToggleImageButton
  extends ImageButton
{
  private Drawable offDrawable;
  private boolean on;
  private Drawable onDrawable;
  private OnStateChangeListener stateChangeListener;
  
  public ToggleImageButton(Context paramContext)
  {
    super(paramContext);
    init();
  }
  
  public ToggleImageButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  public ToggleImageButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  @TargetApi(21)
  public ToggleImageButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init();
  }
  
  private void init()
  {
    setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = ToggleImageButton.this;
        if (!ToggleImageButton.this.on) {}
        for (boolean bool = true;; bool = false)
        {
          paramAnonymousView.setOn(bool);
          return;
        }
      }
    });
  }
  
  private void notifyStateChanged()
  {
    if (this.stateChangeListener != null) {
      this.stateChangeListener.onStateChanged(this);
    }
  }
  
  public Drawable getOffDrawable()
  {
    return this.offDrawable;
  }
  
  public Drawable getOnDrawable()
  {
    return this.onDrawable;
  }
  
  public OnStateChangeListener getOnStateChangeListener()
  {
    return this.stateChangeListener;
  }
  
  public boolean isOn()
  {
    return this.on;
  }
  
  public void setOffDrawable(Drawable paramDrawable)
  {
    this.offDrawable = paramDrawable;
  }
  
  public void setOn(boolean paramBoolean)
  {
    boolean bool = this.on;
    this.on = paramBoolean;
    if (this.on) {}
    for (Drawable localDrawable = this.onDrawable;; localDrawable = this.offDrawable)
    {
      if (localDrawable != null) {
        setImageDrawable(localDrawable);
      }
      if (bool != paramBoolean) {
        notifyStateChanged();
      }
      return;
    }
  }
  
  public void setOnDrawable(Drawable paramDrawable)
  {
    this.onDrawable = paramDrawable;
  }
  
  public void setOnStateChangeListener(OnStateChangeListener paramOnStateChangeListener)
  {
    this.stateChangeListener = paramOnStateChangeListener;
  }
  
  public static abstract interface OnStateChangeListener
  {
    public abstract void onStateChanged(ToggleImageButton paramToggleImageButton);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/ui/ToggleImageButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */