package spacemadness.com.lunarconsole.console;

import java.util.ArrayList;
import java.util.List;
import spacemadness.com.lunarconsole.debug.Log;
import spacemadness.com.lunarconsole.utils.ThreadUtils;

class ConsoleEntryDispatcher
{
  private final Runnable dispatchRunnable;
  private final List<ConsoleEntry> entries;
  private final OnDispatchListener listener;
  
  public ConsoleEntryDispatcher(OnDispatchListener paramOnDispatchListener)
  {
    if (paramOnDispatchListener == null) {
      throw new NullPointerException("Listener is null");
    }
    this.listener = paramOnDispatchListener;
    this.entries = new ArrayList();
    this.dispatchRunnable = createDispatchRunnable();
  }
  
  private Runnable createDispatchRunnable()
  {
    new Runnable()
    {
      public void run()
      {
        ConsoleEntryDispatcher.this.dispatchEntries();
      }
    };
  }
  
  public void add(ConsoleEntry paramConsoleEntry)
  {
    synchronized (this.entries)
    {
      this.entries.add(paramConsoleEntry);
      if (this.entries.size() == 1) {
        postEntriesDispatch();
      }
      return;
    }
  }
  
  public void cancelAll()
  {
    cancelEntriesDispatch();
    synchronized (this.entries)
    {
      this.entries.clear();
      return;
    }
  }
  
  protected void cancelEntriesDispatch()
  {
    ThreadUtils.cancel(this.dispatchRunnable);
  }
  
  protected void dispatchEntries()
  {
    synchronized (this.entries)
    {
      try
      {
        this.listener.onDispatchEntries(this.entries);
        this.entries.clear();
        return;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          Log.e(localException, "Can't dispatch entries", new Object[0]);
        }
      }
    }
  }
  
  protected void postEntriesDispatch()
  {
    ThreadUtils.runOnUIThread(this.dispatchRunnable);
  }
  
  public static abstract interface OnDispatchListener
  {
    public abstract void onDispatchEntries(List<ConsoleEntry> paramList);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/ConsoleEntryDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */