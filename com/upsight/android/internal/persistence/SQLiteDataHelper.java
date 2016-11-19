package com.upsight.android.internal.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLiteDataHelper
  implements DataHelper
{
  private static final String DATABASE_NAME = "upsight.db";
  private static final int DATABASE_VERSION = 1;
  private static final String TABLE_MODELS = "models";
  private SQLiteOpenHelper mOpenHelper;
  
  SQLiteDataHelper(Context paramContext)
  {
    this.mOpenHelper = new DatabaseHelper(paramContext);
  }
  
  public int delete(String paramString, String[] paramArrayOfString)
  {
    try
    {
      int i = this.mOpenHelper.getWritableDatabase().delete("models", paramString, paramArrayOfString);
      return i;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public long insert(ContentValues paramContentValues)
  {
    try
    {
      long l = this.mOpenHelper.getWritableDatabase().insert("models", null, paramContentValues);
      return l;
    }
    finally
    {
      paramContentValues = finally;
      throw paramContentValues;
    }
  }
  
  public Cursor query(String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    try
    {
      paramArrayOfString1 = this.mOpenHelper.getReadableDatabase().query("models", paramArrayOfString1, paramString1, paramArrayOfString2, null, null, paramString2);
      return paramArrayOfString1;
    }
    finally
    {
      paramArrayOfString1 = finally;
      throw paramArrayOfString1;
    }
  }
  
  public int update(ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    try
    {
      int i = this.mOpenHelper.getWritableDatabase().update("models", paramContentValues, paramString, paramArrayOfString);
      return i;
    }
    finally
    {
      paramContentValues = finally;
      throw paramContentValues;
    }
  }
  
  private static final class DatabaseHelper
    extends SQLiteOpenHelper
  {
    public DatabaseHelper(Context paramContext)
    {
      super("upsight.db", null, 1);
    }
    
    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE models (_id TEXT PRIMARY KEY NOT NULL, type TEXT NOT NULL, data TEXT NOT NULL  );");
      paramSQLiteDatabase.execSQL("CREATE INDEX ID_INDEX ON models (_id);");
    }
    
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS models");
      onCreate(paramSQLiteDatabase);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/SQLiteDataHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */