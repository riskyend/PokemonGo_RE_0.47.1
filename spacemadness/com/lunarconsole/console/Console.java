package spacemadness.com.lunarconsole.console;

import spacemadness.com.lunarconsole.core.Destroyable;
import spacemadness.com.lunarconsole.debug.Log;

public class Console
  implements Destroyable, ConsoleAdapter.DataSource
{
  private static final LunarConsoleListener NULL_LISTENER = new LunarConsoleListener()
  {
    public void onAddEntry(Console paramAnonymousConsole, ConsoleEntry paramAnonymousConsoleEntry, boolean paramAnonymousBoolean) {}
    
    public void onClearEntries(Console paramAnonymousConsole) {}
    
    public void onRemoveEntries(Console paramAnonymousConsole, int paramAnonymousInt1, int paramAnonymousInt2) {}
  };
  private LunarConsoleListener consoleListener;
  private final ConsoleEntryList entries;
  private final Options options;
  
  public Console(Options paramOptions)
  {
    if (paramOptions == null) {
      throw new NullPointerException("Options is null");
    }
    this.options = paramOptions.clone();
    this.entries = new ConsoleEntryList(paramOptions.getCapacity(), paramOptions.getTrimCount());
    this.consoleListener = NULL_LISTENER;
  }
  
  private void notifyEntriesCleared()
  {
    try
    {
      this.consoleListener.onClearEntries(this);
      return;
    }
    catch (Exception localException)
    {
      Log.e(localException, "Error while notifying delegate", new Object[0]);
    }
  }
  
  private void notifyEntryAdded(ConsoleEntry paramConsoleEntry, boolean paramBoolean)
  {
    try
    {
      this.consoleListener.onAddEntry(this, paramConsoleEntry, paramBoolean);
      return;
    }
    catch (Exception paramConsoleEntry)
    {
      Log.e(paramConsoleEntry, "Error while notifying delegate", new Object[0]);
    }
  }
  
  private void notifyRemoveEntries(int paramInt1, int paramInt2)
  {
    try
    {
      this.consoleListener.onRemoveEntries(this, paramInt1, paramInt2);
      return;
    }
    catch (Exception localException)
    {
      Log.e(localException, "Error while notifying delegate", new Object[0]);
    }
  }
  
  public void clear()
  {
    this.entries.clear();
    notifyEntriesCleared();
  }
  
  public void destroy()
  {
    this.entries.clear();
  }
  
  public ConsoleEntryList entries()
  {
    return this.entries;
  }
  
  public int entriesCount()
  {
    return this.entries.count();
  }
  
  public ConsoleEntry entryAtIndex(int paramInt)
  {
    return this.entries.getEntry(paramInt);
  }
  
  public LunarConsoleListener getConsoleListener()
  {
    return this.consoleListener;
  }
  
  public ConsoleEntry getEntry(int paramInt)
  {
    return this.entries.getEntry(paramInt);
  }
  
  public int getEntryCount()
  {
    return this.entries.count();
  }
  
  public String getText()
  {
    return this.entries.getText();
  }
  
  public boolean isTrimmed()
  {
    return this.entries.isTrimmed();
  }
  
  public void logMessage(String paramString1, String paramString2, byte paramByte)
  {
    logMessage(new ConsoleEntry(paramByte, paramString1, paramString2));
  }
  
  void logMessage(ConsoleEntry paramConsoleEntry)
  {
    int i = this.entries.trimmedCount();
    paramConsoleEntry.index = this.entries.totalCount();
    boolean bool = this.entries.filterEntry(paramConsoleEntry);
    this.entries.addEntry(paramConsoleEntry);
    i = this.entries.trimmedCount() - i;
    if (i > 0) {
      notifyRemoveEntries(0, i);
    }
    notifyEntryAdded(paramConsoleEntry, bool);
  }
  
  public void setConsoleListener(LunarConsoleListener paramLunarConsoleListener)
  {
    if (paramLunarConsoleListener != null) {}
    for (;;)
    {
      this.consoleListener = paramLunarConsoleListener;
      return;
      paramLunarConsoleListener = NULL_LISTENER;
    }
  }
  
  public int trimmedCount()
  {
    return this.entries.trimmedCount();
  }
  
  public static class Options
  {
    private final int capacity;
    private int trimCount;
    
    public Options(int paramInt)
    {
      if (paramInt <= 0) {
        throw new IllegalArgumentException("Invalid capacity: " + paramInt);
      }
      this.capacity = paramInt;
      this.trimCount = 1;
    }
    
    public Options clone()
    {
      Options localOptions = new Options(this.capacity);
      localOptions.trimCount = this.trimCount;
      return localOptions;
    }
    
    public int getCapacity()
    {
      return this.capacity;
    }
    
    public int getTrimCount()
    {
      return this.trimCount;
    }
    
    public void setTrimCount(int paramInt)
    {
      if ((paramInt <= 0) || (paramInt > this.capacity)) {
        throw new IllegalArgumentException("Illegal trim count: " + paramInt);
      }
      this.trimCount = paramInt;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/Console.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */