package spacemadness.com.lunarconsole.console;

import java.util.Iterator;
import spacemadness.com.lunarconsole.utils.ObjectUtils;
import spacemadness.com.lunarconsole.utils.StringUtils;

public class ConsoleEntryList
{
  private LimitSizeEntryList currentEntries;
  private final LimitSizeEntryList entries;
  private int errorCount;
  private String filterText;
  private LimitSizeEntryList filteredEntries;
  private int logCount;
  private int logDisabledTypesMask;
  private int warningCount;
  
  public ConsoleEntryList(int paramInt1, int paramInt2)
  {
    this.entries = new LimitSizeEntryList(paramInt1, paramInt2);
    this.currentEntries = this.entries;
    this.logDisabledTypesMask = 0;
  }
  
  private boolean appendFilter()
  {
    if (isFiltering())
    {
      useFilteredFromEntries(this.filteredEntries);
      return true;
    }
    return applyFilter();
  }
  
  private boolean applyFilter()
  {
    if ((StringUtils.length(this.filterText) > 0) || (hasLogTypeFilters())) {}
    for (int i = 1; i != 0; i = 0)
    {
      useFilteredFromEntries(this.entries);
      return true;
    }
    return removeFilter();
  }
  
  private LimitSizeEntryList filterEntries(LimitSizeEntryList paramLimitSizeEntryList)
  {
    LimitSizeEntryList localLimitSizeEntryList = new LimitSizeEntryList(paramLimitSizeEntryList.capacity(), paramLimitSizeEntryList.getTrimSize());
    paramLimitSizeEntryList = paramLimitSizeEntryList.iterator();
    while (paramLimitSizeEntryList.hasNext())
    {
      ConsoleEntry localConsoleEntry = (ConsoleEntry)paramLimitSizeEntryList.next();
      if (isFiltered(localConsoleEntry)) {
        localLimitSizeEntryList.addObject(localConsoleEntry);
      }
    }
    return localLimitSizeEntryList;
  }
  
  private boolean hasLogTypeFilters()
  {
    return this.logDisabledTypesMask != 0;
  }
  
  private boolean isFiltered(ConsoleEntry paramConsoleEntry)
  {
    if ((this.logDisabledTypesMask & ConsoleLogType.getMask(paramConsoleEntry.type)) != 0) {}
    while ((StringUtils.length(this.filterText) != 0) && (!StringUtils.containsIgnoreCase(paramConsoleEntry.message, this.filterText))) {
      return false;
    }
    return true;
  }
  
  private boolean removeFilter()
  {
    if (isFiltering())
    {
      this.currentEntries = this.entries;
      this.filteredEntries = null;
      return true;
    }
    return false;
  }
  
  private void useFilteredFromEntries(LimitSizeEntryList paramLimitSizeEntryList)
  {
    paramLimitSizeEntryList = filterEntries(paramLimitSizeEntryList);
    this.currentEntries = paramLimitSizeEntryList;
    this.filteredEntries = paramLimitSizeEntryList;
  }
  
  public void addEntry(ConsoleEntry paramConsoleEntry)
  {
    this.entries.addObject(paramConsoleEntry);
    int i = paramConsoleEntry.type;
    if (i == 3) {
      this.logCount += 1;
    }
    do
    {
      return;
      if (i == 2)
      {
        this.warningCount += 1;
        return;
      }
    } while (!ConsoleLogType.isErrorType(i));
    this.errorCount += 1;
  }
  
  public void clear()
  {
    this.entries.clear();
    if (this.filteredEntries != null) {
      this.filteredEntries.clear();
    }
    this.logCount = 0;
    this.warningCount = 0;
    this.errorCount = 0;
  }
  
  public int count()
  {
    return this.currentEntries.count();
  }
  
  public boolean filterEntry(ConsoleEntry paramConsoleEntry)
  {
    if (isFiltering())
    {
      if (isFiltered(paramConsoleEntry)) {
        this.filteredEntries.addObject(paramConsoleEntry);
      }
    }
    else {
      return true;
    }
    return false;
  }
  
  public ConsoleEntry getEntry(int paramInt)
  {
    return (ConsoleEntry)this.currentEntries.objectAtIndex(paramInt);
  }
  
  public int getErrorCount()
  {
    return this.errorCount;
  }
  
  public String getFilterText()
  {
    return this.filterText;
  }
  
  public int getLogCount()
  {
    return this.logCount;
  }
  
  public String getText()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    int k = this.currentEntries.count();
    Iterator localIterator = this.currentEntries.iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append(((ConsoleEntry)localIterator.next()).message);
      int j = i + 1;
      i = j;
      if (j < k)
      {
        localStringBuilder.append('\n');
        i = j;
      }
    }
    return localStringBuilder.toString();
  }
  
  public int getWarningCount()
  {
    return this.warningCount;
  }
  
  public boolean isFilterLogTypeEnabled(int paramInt)
  {
    return (this.logDisabledTypesMask & ConsoleLogType.getMask(paramInt)) == 0;
  }
  
  public boolean isFiltering()
  {
    return this.filteredEntries != null;
  }
  
  public boolean isOverfloating()
  {
    return this.currentEntries.isOverfloating();
  }
  
  public boolean isTrimmed()
  {
    return this.currentEntries.isTrimmed();
  }
  
  public int overflowAmount()
  {
    return this.currentEntries.overflowCount();
  }
  
  public boolean setFilterByLogType(int paramInt, boolean paramBoolean)
  {
    return setFilterByLogTypeMask(ConsoleLogType.getMask(paramInt), paramBoolean);
  }
  
  public boolean setFilterByLogTypeMask(int paramInt, boolean paramBoolean)
  {
    int i = this.logDisabledTypesMask;
    if (paramBoolean) {
      this.logDisabledTypesMask |= paramInt;
    }
    while (i != this.logDisabledTypesMask) {
      if (paramBoolean)
      {
        return appendFilter();
        this.logDisabledTypesMask &= (paramInt ^ 0xFFFFFFFF);
      }
      else
      {
        return applyFilter();
      }
    }
    return false;
  }
  
  public boolean setFilterByText(String paramString)
  {
    if (!ObjectUtils.areEqual(this.filterText, paramString))
    {
      String str = this.filterText;
      this.filterText = paramString;
      if ((StringUtils.length(paramString) > StringUtils.length(str)) && ((StringUtils.length(str) == 0) || (StringUtils.hasPrefix(paramString, str)))) {
        return appendFilter();
      }
      return applyFilter();
    }
    return false;
  }
  
  public int totalCount()
  {
    return this.currentEntries.totalCount();
  }
  
  public void trimHead(int paramInt)
  {
    this.entries.trimHead(paramInt);
  }
  
  public int trimmedCount()
  {
    return this.currentEntries.trimmedCount();
  }
  
  private static class LimitSizeEntryList
    extends LimitSizeList<ConsoleEntry>
  {
    public LimitSizeEntryList(int paramInt1, int paramInt2)
    {
      super(paramInt1, paramInt2);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/ConsoleEntryList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */