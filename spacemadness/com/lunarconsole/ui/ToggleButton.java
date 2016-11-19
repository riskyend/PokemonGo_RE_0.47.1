package spacemadness.com.lunarconsole.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ToggleButton
  extends Button
{
  private boolean on;
  private OnStateChangeListener stateChangeListener;
  
  public ToggleButton(Context paramContext)
  {
    super(paramContext);
    init();
  }
  
  public ToggleButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  public ToggleButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  @TargetApi(21)
  public ToggleButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
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
        paramAnonymousView = ToggleButton.this;
        if (!ToggleButton.this.on) {}
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
  
  public OnStateChangeListener getOnStateChangeListener()
  {
    return this.stateChangeListener;
  }
  
  public boolean isOn()
  {
    return this.on;
  }
  
  public void setOn(boolean paramBoolean)
  {
    if (this.on != paramBoolean)
    {
      this.on = paramBoolean;
      notifyStateChanged();
    }
  }
  
  public void setOnStateChangeListener(OnStateChangeListener paramOnStateChangeListener)
  {
    this.stateChangeListener = paramOnStateChangeListener;
  }
  
  public static abstract interface OnStateChangeListener
  {
    public abstract void onStateChanged(ToggleButton paramToggleButton);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/ui/ToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */