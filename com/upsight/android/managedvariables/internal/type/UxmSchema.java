package com.upsight.android.managedvariables.internal.type;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.managedvariables.type.UpsightManagedBoolean;
import com.upsight.android.managedvariables.type.UpsightManagedFloat;
import com.upsight.android.managedvariables.type.UpsightManagedInt;
import com.upsight.android.managedvariables.type.UpsightManagedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class UxmSchema
{
  private static final String ITEM_SCHEMA_KEY_DEFAULT = "default";
  private static final String ITEM_SCHEMA_KEY_TAG = "tag";
  private static final String ITEM_SCHEMA_KEY_TYPE = "type";
  private static final Map<Class<? extends ManagedVariable>, Class<? extends BaseSchema>> sClassSchemaMap = new HashMap() {};
  private static final Map<String, Class<? extends BaseSchema>> sModelTypeSchemaMap = new HashMap() {};
  private static final Map<String, String> sTypeSchemaMap = new HashMap() {};
  private List<BaseSchema> mItemList = new ArrayList();
  private Map<String, BaseSchema> mItemSchemaMap = new HashMap();
  private UpsightLogger mLogger;
  public final String mSchemaJsonString;
  
  UxmSchema(UpsightLogger paramUpsightLogger)
  {
    this.mLogger = paramUpsightLogger;
    this.mSchemaJsonString = null;
  }
  
  private UxmSchema(List<BaseSchema> paramList, Map<String, BaseSchema> paramMap, UpsightLogger paramUpsightLogger, String paramString)
  {
    this.mItemList = paramList;
    this.mItemSchemaMap = paramMap;
    this.mLogger = paramUpsightLogger;
    this.mSchemaJsonString = paramString;
  }
  
  public static UxmSchema create(String paramString, Gson paramGson, JsonParser paramJsonParser, UpsightLogger paramUpsightLogger)
    throws IllegalArgumentException
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    Object localObject1;
    for (;;)
    {
      try
      {
        paramJsonParser = paramJsonParser.parse(paramString);
        if ((paramJsonParser != null) && (paramJsonParser.isJsonArray()))
        {
          paramJsonParser = paramJsonParser.getAsJsonArray();
          Iterator localIterator = paramJsonParser.iterator();
          if (!localIterator.hasNext()) {
            break label648;
          }
          paramString = (JsonElement)localIterator.next();
          if (!paramString.isJsonObject())
          {
            paramString = "Managed variable schema must be a JSON object: " + paramString;
            paramUpsightLogger.e("Upsight", paramString, new Object[0]);
            throw new IllegalArgumentException(paramString);
          }
        }
        else
        {
          paramGson = "UXM schema must be a JSON array: " + paramString;
          paramUpsightLogger.e("Upsight", paramGson, new Object[0]);
          throw new IllegalArgumentException(paramGson);
        }
      }
      catch (JsonSyntaxException paramGson)
      {
        paramString = "Failed to parse UXM schema JSON: " + paramString;
        paramUpsightLogger.e("Upsight", paramGson, paramString, new Object[0]);
        throw new IllegalArgumentException(paramString, paramGson);
      }
      Object localObject2 = paramString.getAsJsonObject().get("tag");
      if ((localObject2 == null) || (!((JsonElement)localObject2).isJsonPrimitive()) || (!((JsonElement)localObject2).getAsJsonPrimitive().isString()))
      {
        paramString = "Managed variable schema must contain a tag: " + paramString;
        paramUpsightLogger.e("Upsight", paramString, new Object[0]);
        throw new IllegalArgumentException(paramString);
      }
      localObject1 = paramString.getAsJsonObject().get("type");
      if ((localObject1 == null) || (!((JsonElement)localObject1).isJsonPrimitive()) || (!((JsonElement)localObject1).getAsJsonPrimitive().isString()))
      {
        paramString = "Managed variable schema must contain a type: " + paramString;
        paramUpsightLogger.e("Upsight", paramString, new Object[0]);
        throw new IllegalArgumentException(paramString);
      }
      if (!paramString.getAsJsonObject().has("default"))
      {
        paramString = "Managed variable schema must contain a default value: " + paramString;
        paramUpsightLogger.e("Upsight", paramString, new Object[0]);
        throw new IllegalArgumentException(paramString);
      }
      localObject1 = (String)sTypeSchemaMap.get(((JsonElement)localObject1).getAsString());
      if (!TextUtils.isEmpty((CharSequence)localObject1))
      {
        paramString.getAsJsonObject().addProperty("type", (String)localObject1);
        localObject2 = ((JsonElement)localObject2).getAsString();
        Class localClass = (Class)sModelTypeSchemaMap.get(localObject1);
        if (localClass == null) {
          break label605;
        }
        try
        {
          localObject1 = (BaseSchema)paramGson.fromJson(paramString, localClass);
          ((BaseSchema)localObject1).validate(paramString);
          localArrayList.add(localObject1);
          localHashMap.put(localObject2, localObject1);
        }
        catch (JsonSyntaxException paramGson)
        {
          paramString = "Managed variable contains invalid fields: " + paramString;
          paramUpsightLogger.e("Upsight", paramGson, paramString, new Object[0]);
          throw new IllegalArgumentException(paramString, paramGson);
        }
      }
    }
    paramString = "Managed variable contains invalid types: " + paramString;
    paramUpsightLogger.e("Upsight", paramString, new Object[0]);
    throw new IllegalArgumentException(paramString);
    label605:
    paramString = "Unknown managed variable type: " + (String)localObject1;
    paramUpsightLogger.e("Upsight", paramString, new Object[0]);
    throw new IllegalArgumentException(paramString);
    label648:
    return new UxmSchema(localArrayList, localHashMap, paramUpsightLogger, paramJsonParser.toString());
  }
  
  public <T extends ManagedVariable> BaseSchema get(Class<T> paramClass, String paramString)
  {
    BaseSchema localBaseSchema = (BaseSchema)this.mItemSchemaMap.get(paramString);
    if (localBaseSchema == null) {
      paramClass = null;
    }
    Class localClass1;
    Class localClass2;
    do
    {
      return paramClass;
      localClass1 = (Class)sClassSchemaMap.get(paramClass);
      localClass2 = (Class)sModelTypeSchemaMap.get(localBaseSchema.type);
      if ((localClass1 == null) || (localClass2 == null)) {
        break;
      }
      paramClass = localBaseSchema;
    } while (localClass2.equals(localClass1));
    paramClass = "The tag is not of the expected class: " + paramString;
    this.mLogger.e("Upsight", paramClass, new Object[0]);
    throw new IllegalArgumentException(paramClass);
  }
  
  public List<BaseSchema> getAllOrdered()
  {
    return new ArrayList(this.mItemList);
  }
  
  public static abstract class BaseSchema<T>
  {
    private static final Set<String> BASE_KEYS = new HashSet() {};
    @Expose
    @SerializedName("default")
    public T defaultValue;
    @Expose
    @SerializedName("description")
    public String description;
    @Expose
    @SerializedName("tag")
    public String tag;
    @Expose
    @SerializedName("type")
    public String type;
    
    private void validate(JsonElement paramJsonElement)
      throws IllegalArgumentException
    {
      if (paramJsonElement == null) {
        throw new IllegalArgumentException(getClass().getSimpleName() + " validation failed due to null JSON element");
      }
      if (!paramJsonElement.isJsonObject()) {
        throw new IllegalArgumentException(getClass().getSimpleName() + " validation failed due to invalid JSON element type");
      }
      Iterator localIterator = paramJsonElement.getAsJsonObject().entrySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)((Map.Entry)localIterator.next()).getKey();
        if ((!BASE_KEYS.contains(str)) && (!getTypeSpecificKeys().contains(str))) {
          throw new IllegalArgumentException(getClass().getSimpleName() + " validation failed due to unknown key");
        }
      }
      if (!isDefaultValueValid(paramJsonElement.getAsJsonObject().get("default"))) {
        throw new IllegalArgumentException(getClass().getSimpleName() + " validation failed due to invalid default value");
      }
    }
    
    abstract Set<String> getTypeSpecificKeys();
    
    abstract boolean isDefaultValueValid(JsonElement paramJsonElement);
  }
  
  public static class BooleanSchema
    extends UxmSchema.BaseSchema<Boolean>
  {
    private static final Set<String> TYPE_SPECIFIC_KEYS = new HashSet();
    
    Set<String> getTypeSpecificKeys()
    {
      return TYPE_SPECIFIC_KEYS;
    }
    
    boolean isDefaultValueValid(JsonElement paramJsonElement)
    {
      return (paramJsonElement.isJsonPrimitive()) && (paramJsonElement.getAsJsonPrimitive().isBoolean());
    }
  }
  
  public static class FloatSchema
    extends UxmSchema.BaseSchema<Float>
  {
    private static final Set<String> TYPE_SPECIFIC_KEYS = new HashSet() {};
    @Expose
    @SerializedName("max")
    public Float max;
    @Expose
    @SerializedName("min")
    public Float min;
    
    Set<String> getTypeSpecificKeys()
    {
      return TYPE_SPECIFIC_KEYS;
    }
    
    boolean isDefaultValueValid(JsonElement paramJsonElement)
    {
      return (paramJsonElement.isJsonPrimitive()) && (paramJsonElement.getAsJsonPrimitive().isNumber());
    }
  }
  
  public static class IntSchema
    extends UxmSchema.BaseSchema<Integer>
  {
    private static final Set<String> TYPE_SPECIFIC_KEYS = new HashSet() {};
    @Expose
    @SerializedName("max")
    public Integer max;
    @Expose
    @SerializedName("min")
    public Integer min;
    
    Set<String> getTypeSpecificKeys()
    {
      return TYPE_SPECIFIC_KEYS;
    }
    
    boolean isDefaultValueValid(JsonElement paramJsonElement)
    {
      return (paramJsonElement.isJsonPrimitive()) && (paramJsonElement.getAsJsonPrimitive().isNumber());
    }
  }
  
  public static class StringSchema
    extends UxmSchema.BaseSchema<String>
  {
    private static final Set<String> TYPE_SPECIFIC_KEYS = new HashSet();
    
    Set<String> getTypeSpecificKeys()
    {
      return TYPE_SPECIFIC_KEYS;
    }
    
    boolean isDefaultValueValid(JsonElement paramJsonElement)
    {
      return (paramJsonElement.isJsonPrimitive()) && (paramJsonElement.getAsJsonPrimitive().isString());
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */