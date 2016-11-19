package spacemadness.com.lunarconsole.console;

public abstract interface LunarConsoleListener
{
  public abstract void onAddEntry(Console paramConsole, ConsoleEntry paramConsoleEntry, boolean paramBoolean);
  
  public abstract void onClearEntries(Console paramConsole);
  
  public abstract void onRemoveEntries(Console paramConsole, int paramInt1, int paramInt2);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/LunarConsoleListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */