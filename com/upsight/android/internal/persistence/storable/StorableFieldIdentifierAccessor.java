package com.upsight.android.internal.persistence.storable;

import java.lang.reflect.Field;

class StorableFieldIdentifierAccessor
  implements StorableIdentifierAccessor
{
  private final Field mField;
  
  StorableFieldIdentifierAccessor(Field paramField)
  {
    if (paramField == null) {
      throw new IllegalArgumentException("Specified Field can not be null.");
    }
    this.mField = paramField;
  }
  
  /* Error */
  public String getId(Object paramObject)
    throws com.upsight.android.UpsightException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   6: iconst_1
    //   7: invokevirtual 35	java/lang/reflect/Field:setAccessible	(Z)V
    //   10: aload_0
    //   11: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   14: aload_1
    //   15: invokevirtual 39	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   18: checkcast 41	java/lang/String
    //   21: astore_1
    //   22: aload_0
    //   23: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   26: iconst_0
    //   27: invokevirtual 35	java/lang/reflect/Field:setAccessible	(Z)V
    //   30: aload_0
    //   31: monitorexit
    //   32: aload_1
    //   33: areturn
    //   34: astore_1
    //   35: new 27	com/upsight/android/UpsightException
    //   38: dup
    //   39: new 43	java/lang/StringBuilder
    //   42: dup
    //   43: invokespecial 44	java/lang/StringBuilder:<init>	()V
    //   46: ldc 46
    //   48: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: aload_0
    //   52: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   55: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   58: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   61: iconst_1
    //   62: anewarray 4	java/lang/Object
    //   65: dup
    //   66: iconst_0
    //   67: aload_1
    //   68: aastore
    //   69: invokespecial 60	com/upsight/android/UpsightException:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   72: athrow
    //   73: astore_1
    //   74: aload_0
    //   75: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   78: iconst_0
    //   79: invokevirtual 35	java/lang/reflect/Field:setAccessible	(Z)V
    //   82: aload_1
    //   83: athrow
    //   84: astore_1
    //   85: aload_0
    //   86: monitorexit
    //   87: aload_1
    //   88: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	89	0	this	StorableFieldIdentifierAccessor
    //   0	89	1	paramObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	22	34	java/lang/Exception
    //   2	22	73	finally
    //   35	73	73	finally
    //   22	30	84	finally
    //   74	84	84	finally
  }
  
  /* Error */
  public void setId(Object paramObject, String paramString)
    throws com.upsight.android.UpsightException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   6: iconst_1
    //   7: invokevirtual 35	java/lang/reflect/Field:setAccessible	(Z)V
    //   10: aload_0
    //   11: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   14: aload_1
    //   15: aload_2
    //   16: invokevirtual 67	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   19: aload_0
    //   20: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   23: iconst_0
    //   24: invokevirtual 35	java/lang/reflect/Field:setAccessible	(Z)V
    //   27: aload_0
    //   28: monitorexit
    //   29: return
    //   30: astore_1
    //   31: new 27	com/upsight/android/UpsightException
    //   34: dup
    //   35: new 43	java/lang/StringBuilder
    //   38: dup
    //   39: invokespecial 44	java/lang/StringBuilder:<init>	()V
    //   42: ldc 46
    //   44: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: aload_0
    //   48: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   51: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   54: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   57: iconst_1
    //   58: anewarray 4	java/lang/Object
    //   61: dup
    //   62: iconst_0
    //   63: aload_1
    //   64: aastore
    //   65: invokespecial 60	com/upsight/android/UpsightException:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   68: athrow
    //   69: astore_1
    //   70: aload_0
    //   71: getfield 22	com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor:mField	Ljava/lang/reflect/Field;
    //   74: iconst_0
    //   75: invokevirtual 35	java/lang/reflect/Field:setAccessible	(Z)V
    //   78: aload_1
    //   79: athrow
    //   80: astore_1
    //   81: aload_0
    //   82: monitorexit
    //   83: aload_1
    //   84: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	this	StorableFieldIdentifierAccessor
    //   0	85	1	paramObject	Object
    //   0	85	2	paramString	String
    // Exception table:
    //   from	to	target	type
    //   2	19	30	java/lang/Exception
    //   2	19	69	finally
    //   31	69	69	finally
    //   19	27	80	finally
    //   70	80	80	finally
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/StorableFieldIdentifierAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */