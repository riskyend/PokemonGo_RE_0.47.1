package com.upsight.android.internal.persistence;

import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

public final class Content
{
  public static Uri getAuthoritytUri(Context paramContext)
  {
    return Uri.parse("content://" + paramContext.getPackageName() + ".upsight");
  }
  
  static Uri getContentTypeUri(Context paramContext, String paramString)
  {
    return Uri.withAppendedPath(getContentUri(paramContext), paramString);
  }
  
  static Uri getContentUri(Context paramContext)
  {
    return Uri.withAppendedPath(getAuthoritytUri(paramContext), "model");
  }
  
  static abstract interface ModelColumns
    extends BaseColumns
  {
    public static final String DATA = "data";
    public static final String TYPE = "type";
  }
  
  static final class Models
    implements Content.ModelColumns
  {
    public static final String CONTENT_DIRECTORY = "model";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.upsight.model";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.upsight.model";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/Content.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */