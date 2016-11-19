package org.apache.commons.io.monitor;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;

public class FileAlterationObserver
  implements Serializable
{
  private final Comparator<File> comparator;
  private final FileFilter fileFilter;
  private final List<FileAlterationListener> listeners = new CopyOnWriteArrayList();
  private final FileEntry rootEntry;
  
  public FileAlterationObserver(File paramFile)
  {
    this(paramFile, (FileFilter)null);
  }
  
  public FileAlterationObserver(File paramFile, FileFilter paramFileFilter)
  {
    this(paramFile, paramFileFilter, (IOCase)null);
  }
  
  public FileAlterationObserver(File paramFile, FileFilter paramFileFilter, IOCase paramIOCase)
  {
    this(new FileEntry(paramFile), paramFileFilter, paramIOCase);
  }
  
  public FileAlterationObserver(String paramString)
  {
    this(new File(paramString));
  }
  
  public FileAlterationObserver(String paramString, FileFilter paramFileFilter)
  {
    this(new File(paramString), paramFileFilter);
  }
  
  public FileAlterationObserver(String paramString, FileFilter paramFileFilter, IOCase paramIOCase)
  {
    this(new File(paramString), paramFileFilter, paramIOCase);
  }
  
  protected FileAlterationObserver(FileEntry paramFileEntry, FileFilter paramFileFilter, IOCase paramIOCase)
  {
    if (paramFileEntry == null) {
      throw new IllegalArgumentException("Root entry is missing");
    }
    if (paramFileEntry.getFile() == null) {
      throw new IllegalArgumentException("Root directory is missing");
    }
    this.rootEntry = paramFileEntry;
    this.fileFilter = paramFileFilter;
    if ((paramIOCase == null) || (paramIOCase.equals(IOCase.SYSTEM)))
    {
      this.comparator = NameFileComparator.NAME_SYSTEM_COMPARATOR;
      return;
    }
    if (paramIOCase.equals(IOCase.INSENSITIVE))
    {
      this.comparator = NameFileComparator.NAME_INSENSITIVE_COMPARATOR;
      return;
    }
    this.comparator = NameFileComparator.NAME_COMPARATOR;
  }
  
  private void checkAndNotify(FileEntry paramFileEntry, FileEntry[] paramArrayOfFileEntry, File[] paramArrayOfFile)
  {
    int i = 0;
    if (paramArrayOfFile.length > 0) {}
    int j;
    int k;
    FileEntry localFileEntry;
    for (FileEntry[] arrayOfFileEntry = new FileEntry[paramArrayOfFile.length];; arrayOfFileEntry = FileEntry.EMPTY_ENTRIES)
    {
      int m = paramArrayOfFileEntry.length;
      j = 0;
      k = i;
      if (j >= m) {
        break label209;
      }
      localFileEntry = paramArrayOfFileEntry[j];
      while ((i < paramArrayOfFile.length) && (this.comparator.compare(localFileEntry.getFile(), paramArrayOfFile[i]) > 0))
      {
        arrayOfFileEntry[i] = createFileEntry(paramFileEntry, paramArrayOfFile[i]);
        doCreate(arrayOfFileEntry[i]);
        i += 1;
      }
    }
    if ((i < paramArrayOfFile.length) && (this.comparator.compare(localFileEntry.getFile(), paramArrayOfFile[i]) == 0))
    {
      doMatch(localFileEntry, paramArrayOfFile[i]);
      checkAndNotify(localFileEntry, localFileEntry.getChildren(), listFiles(paramArrayOfFile[i]));
      arrayOfFileEntry[i] = localFileEntry;
      i += 1;
    }
    for (;;)
    {
      j += 1;
      break;
      checkAndNotify(localFileEntry, localFileEntry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
      doDelete(localFileEntry);
    }
    label209:
    while (k < paramArrayOfFile.length)
    {
      arrayOfFileEntry[k] = createFileEntry(paramFileEntry, paramArrayOfFile[k]);
      doCreate(arrayOfFileEntry[k]);
      k += 1;
    }
    paramFileEntry.setChildren(arrayOfFileEntry);
  }
  
  private FileEntry createFileEntry(FileEntry paramFileEntry, File paramFile)
  {
    FileEntry localFileEntry = paramFileEntry.newChildInstance(paramFile);
    localFileEntry.refresh(paramFile);
    paramFile = listFiles(paramFile);
    if (paramFile.length > 0) {}
    for (paramFileEntry = new FileEntry[paramFile.length];; paramFileEntry = FileEntry.EMPTY_ENTRIES)
    {
      int i = 0;
      while (i < paramFile.length)
      {
        paramFileEntry[i] = createFileEntry(localFileEntry, paramFile[i]);
        i += 1;
      }
    }
    localFileEntry.setChildren(paramFileEntry);
    return localFileEntry;
  }
  
  private void doCreate(FileEntry paramFileEntry)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
    {
      FileAlterationListener localFileAlterationListener = (FileAlterationListener)localIterator.next();
      if (paramFileEntry.isDirectory()) {
        localFileAlterationListener.onDirectoryCreate(paramFileEntry.getFile());
      } else {
        localFileAlterationListener.onFileCreate(paramFileEntry.getFile());
      }
    }
    paramFileEntry = paramFileEntry.getChildren();
    int j = paramFileEntry.length;
    int i = 0;
    while (i < j)
    {
      doCreate(paramFileEntry[i]);
      i += 1;
    }
  }
  
  private void doDelete(FileEntry paramFileEntry)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
    {
      FileAlterationListener localFileAlterationListener = (FileAlterationListener)localIterator.next();
      if (paramFileEntry.isDirectory()) {
        localFileAlterationListener.onDirectoryDelete(paramFileEntry.getFile());
      } else {
        localFileAlterationListener.onFileDelete(paramFileEntry.getFile());
      }
    }
  }
  
  private void doMatch(FileEntry paramFileEntry, File paramFile)
  {
    if (paramFileEntry.refresh(paramFile))
    {
      Iterator localIterator = this.listeners.iterator();
      while (localIterator.hasNext())
      {
        FileAlterationListener localFileAlterationListener = (FileAlterationListener)localIterator.next();
        if (paramFileEntry.isDirectory()) {
          localFileAlterationListener.onDirectoryChange(paramFile);
        } else {
          localFileAlterationListener.onFileChange(paramFile);
        }
      }
    }
  }
  
  private File[] listFiles(File paramFile)
  {
    File[] arrayOfFile = null;
    if (paramFile.isDirectory()) {
      if (this.fileFilter != null) {
        break label54;
      }
    }
    label54:
    for (arrayOfFile = paramFile.listFiles();; arrayOfFile = paramFile.listFiles(this.fileFilter))
    {
      paramFile = arrayOfFile;
      if (arrayOfFile == null) {
        paramFile = FileUtils.EMPTY_FILE_ARRAY;
      }
      if ((this.comparator != null) && (paramFile.length > 1)) {
        Arrays.sort(paramFile, this.comparator);
      }
      return paramFile;
    }
  }
  
  public void addListener(FileAlterationListener paramFileAlterationListener)
  {
    if (paramFileAlterationListener != null) {
      this.listeners.add(paramFileAlterationListener);
    }
  }
  
  public void checkAndNotify()
  {
    Object localObject = this.listeners.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((FileAlterationListener)((Iterator)localObject).next()).onStart(this);
    }
    localObject = this.rootEntry.getFile();
    if (((File)localObject).exists()) {
      checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), listFiles((File)localObject));
    }
    for (;;)
    {
      localObject = this.listeners.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((FileAlterationListener)((Iterator)localObject).next()).onStop(this);
      }
      if (this.rootEntry.isExists()) {
        checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
      }
    }
  }
  
  public void destroy()
    throws Exception
  {}
  
  public File getDirectory()
  {
    return this.rootEntry.getFile();
  }
  
  public FileFilter getFileFilter()
  {
    return this.fileFilter;
  }
  
  public Iterable<FileAlterationListener> getListeners()
  {
    return this.listeners;
  }
  
  public void initialize()
    throws Exception
  {
    this.rootEntry.refresh(this.rootEntry.getFile());
    File[] arrayOfFile = listFiles(this.rootEntry.getFile());
    if (arrayOfFile.length > 0) {}
    for (FileEntry[] arrayOfFileEntry = new FileEntry[arrayOfFile.length];; arrayOfFileEntry = FileEntry.EMPTY_ENTRIES)
    {
      int i = 0;
      while (i < arrayOfFile.length)
      {
        arrayOfFileEntry[i] = createFileEntry(this.rootEntry, arrayOfFile[i]);
        i += 1;
      }
    }
    this.rootEntry.setChildren(arrayOfFileEntry);
  }
  
  public void removeListener(FileAlterationListener paramFileAlterationListener)
  {
    while ((paramFileAlterationListener != null) && (this.listeners.remove(paramFileAlterationListener))) {}
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append("[file='");
    localStringBuilder.append(getDirectory().getPath());
    localStringBuilder.append('\'');
    if (this.fileFilter != null)
    {
      localStringBuilder.append(", ");
      localStringBuilder.append(this.fileFilter.toString());
    }
    localStringBuilder.append(", listeners=");
    localStringBuilder.append(this.listeners.size());
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/monitor/FileAlterationObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */