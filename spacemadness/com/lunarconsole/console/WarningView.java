package spacemadness.com.lunarconsole.console;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import spacemadness.com.lunarconsole.R.id;
import spacemadness.com.lunarconsole.R.layout;
import spacemadness.com.lunarconsole.core.Destroyable;
import spacemadness.com.lunarconsole.debug.Log;
import spacemadness.com.lunarconsole.debug.Tags;

public class WarningView
  extends FrameLayout
  implements Destroyable
{
  private Listener listener;
  private TextView messageText;
  
  public WarningView(Context paramContext)
  {
    super(paramContext);
    init(paramContext);
  }
  
  public WarningView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }
  
  @TargetApi(11)
  public WarningView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext);
  }
  
  @TargetApi(21)
  public WarningView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramContext);
  }
  
  private void init(Context paramContext)
  {
    paramContext = LayoutInflater.from(paramContext).inflate(R.layout.lunar_layout_warning, null, false);
    setupUI(paramContext);
    addView(paramContext, new FrameLayout.LayoutParams(-1, -2, 81));
  }
  
  private void notifyDetails()
  {
    if (this.listener != null) {}
    try
    {
      this.listener.onDetailsClick(this);
      return;
    }
    catch (Exception localException)
    {
      Log.e("Error while notifying listener", new Object[0]);
    }
  }
  
  private void notifyDismiss()
  {
    if (this.listener != null) {}
    try
    {
      this.listener.onDismissClick(this);
      return;
    }
    catch (Exception localException)
    {
      Log.e("Error while notifying listener", new Object[0]);
    }
  }
  
  private void setOnClickListener(View paramView, int paramInt, View.OnClickListener paramOnClickListener)
  {
    paramView.findViewById(paramInt).setOnClickListener(paramOnClickListener);
  }
  
  private void setupUI(View paramView)
  {
    this.messageText = ((TextView)paramView.findViewById(R.id.lunar_console_text_warning_message));
    paramView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
    });
    setOnClickListener(paramView, R.id.lunar_console_button_dismiss, new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WarningView.this.notifyDismiss();
      }
    });
    setOnClickListener(paramView, R.id.lunar_console_button_details, new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WarningView.this.notifyDetails();
      }
    });
  }
  
  public void destroy()
  {
    Log.d(Tags.WARNING_VIEW, "Destroy warning", new Object[0]);
    this.listener = null;
  }
  
  public Listener getListener()
  {
    return this.listener;
  }
  
  public void setListener(Listener paramListener)
  {
    this.listener = paramListener;
  }
  
  public void setMessage(String paramString)
  {
    this.messageText.setText(paramString);
  }
  
  public static abstract interface Listener
  {
    public abstract void onDetailsClick(WarningView paramWarningView);
    
    public abstract void onDismissClick(WarningView paramWarningView);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/WarningView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */