package com.upsight.android.managedvariables.internal.type;

import android.content.res.Resources;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.squareup.otto.Bus;
import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.analytics.UpsightAnalyticsComponent;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.managedvariables.experience.UpsightUserExperience;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import javax.inject.Named;
import javax.inject.Singleton;
import org.apache.commons.io.IOUtils;
import rx.Scheduler;

@Module
public class UxmModule
{
  public static final String GSON_UXM_SCHEMA = "gsonUxmSchema";
  public static final String JSON_PARSER_UXM_SCHEMA = "jsonParserUxmSchema";
  public static final String STRING_RAW_UXM_SCHEMA = "stringRawUxmSchema";
  
  @Provides
  @Singleton
  ManagedVariableManager provideManagedVariableManager(UpsightContext paramUpsightContext, @Named("main") Scheduler paramScheduler, UxmSchema paramUxmSchema)
  {
    return new ManagedVariableManager(paramScheduler, paramUpsightContext.getDataStore(), paramUxmSchema);
  }
  
  @Provides
  @Singleton
  UxmBlockProvider provideUxmBlockProvider(UpsightContext paramUpsightContext, @Named("stringRawUxmSchema") String paramString, UxmSchema paramUxmSchema)
  {
    return new UxmBlockProvider(paramUpsightContext, paramString, paramUxmSchema);
  }
  
  @Provides
  @Singleton
  UxmContentFactory provideUxmContentFactory(UpsightContext paramUpsightContext, @Named("main") Scheduler paramScheduler, UpsightUserExperience paramUpsightUserExperience)
  {
    UpsightCoreComponent localUpsightCoreComponent = paramUpsightContext.getCoreComponent();
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    UpsightLogger localUpsightLogger = paramUpsightContext.getLogger();
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    Object localObject3 = localObject4;
    Object localObject2 = localObject5;
    Object localObject1 = localObject6;
    if (localUpsightCoreComponent != null)
    {
      localObject3 = localObject4;
      localObject2 = localObject5;
      localObject1 = localObject6;
      if (localUpsightAnalyticsExtension != null)
      {
        localObject3 = localUpsightCoreComponent.bus();
        localObject2 = localUpsightCoreComponent.gson();
        localObject1 = ((UpsightAnalyticsComponent)localUpsightAnalyticsExtension.getComponent()).clock();
      }
    }
    return new UxmContentFactory(new UxmContentActions.UxmContentActionContext(paramUpsightContext, (Bus)localObject3, (Gson)localObject2, (Clock)localObject1, paramScheduler.createWorker(), localUpsightLogger), paramUpsightUserExperience);
  }
  
  @Provides
  @Singleton
  UxmSchema provideUxmSchema(UpsightContext paramUpsightContext, @Named("gsonUxmSchema") Gson paramGson, @Named("jsonParserUxmSchema") JsonParser paramJsonParser, @Named("stringRawUxmSchema") String paramString)
  {
    UpsightLogger localUpsightLogger = paramUpsightContext.getLogger();
    Object localObject = null;
    paramUpsightContext = (UpsightContext)localObject;
    if (!TextUtils.isEmpty(paramString)) {}
    try
    {
      paramUpsightContext = UxmSchema.create(paramString, paramGson, paramJsonParser, localUpsightLogger);
      paramGson = paramUpsightContext;
      if (paramUpsightContext == null)
      {
        paramGson = new UxmSchema(localUpsightLogger);
        localUpsightLogger.d("Upsight", "Empty UXM schema used", new Object[0]);
      }
      return paramGson;
    }
    catch (IllegalArgumentException paramUpsightContext)
    {
      for (;;)
      {
        localUpsightLogger.e("Upsight", paramUpsightContext, "Failed to parse UXM schema", new Object[0]);
        paramUpsightContext = (UpsightContext)localObject;
      }
    }
  }
  
  @Provides
  @Named("gsonUxmSchema")
  @Singleton
  Gson provideUxmSchemaGson(UpsightContext paramUpsightContext)
  {
    return paramUpsightContext.getCoreComponent().gson();
  }
  
  @Provides
  @Named("jsonParserUxmSchema")
  @Singleton
  JsonParser provideUxmSchemaJsonParser(UpsightContext paramUpsightContext)
  {
    return paramUpsightContext.getCoreComponent().jsonParser();
  }
  
  @Provides
  @Named("stringRawUxmSchema")
  @Singleton
  String provideUxmSchemaRawString(UpsightContext paramUpsightContext, @Named("resUxmSchema") Integer paramInteger)
  {
    UpsightLogger localUpsightLogger = paramUpsightContext.getLogger();
    try
    {
      paramUpsightContext = paramUpsightContext.getResources().openRawResource(paramInteger.intValue());
      if (paramUpsightContext != null) {
        return IOUtils.toString(paramUpsightContext);
      }
      localUpsightLogger.e("Upsight", "Failed to find UXM schema file", new Object[0]);
      return "";
    }
    catch (IOException paramUpsightContext)
    {
      localUpsightLogger.e("Upsight", paramUpsightContext, "Failed to read UXM schema file", new Object[0]);
    }
    return "";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */