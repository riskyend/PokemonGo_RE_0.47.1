package spacemadness.com.lunarconsole.console;

public final class ConsoleLogType
{
  public static final byte ASSERT = 1;
  public static final byte COUNT = 5;
  public static final byte ERROR = 0;
  public static final byte EXCEPTION = 4;
  public static final byte LOG = 3;
  public static final byte WARNING = 2;
  
  public static int getMask(int paramInt)
  {
    return 1 << paramInt;
  }
  
  public static boolean isErrorType(int paramInt)
  {
    return (paramInt == 4) || (paramInt == 0) || (paramInt == 1);
  }
  
  public static boolean isValidType(int paramInt)
  {
    return (paramInt >= 0) && (paramInt < 5);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/ConsoleLogType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */