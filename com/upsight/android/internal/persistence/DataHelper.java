package com.upsight.android.internal.persistence;

import android.content.ContentValues;
import android.database.Cursor;

abstract interface DataHelper
{
  public abstract int delete(String paramString, String[] paramArrayOfString);
  
  public abstract long insert(ContentValues paramContentValues);
  
  public abstract Cursor query(String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2);
  
  public abstract int update(ContentValues paramContentValues, String paramString, String[] paramArrayOfString);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/DataHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */