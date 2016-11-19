package com.upsight.mediation.vast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.vast.activity.VASTActivity;
import com.upsight.mediation.vast.model.VASTModel;
import com.upsight.mediation.vast.processor.VASTProcessor;
import com.upsight.mediation.vast.util.DefaultMediaPicker;
import com.upsight.mediation.vast.util.NetworkTools;

public class VASTPlayer
{
  public static final int ERROR_EXCEEDED_WRAPPER_LIMIT = 302;
  public static final int ERROR_FILE_NOT_FOUND = 401;
  public static final int ERROR_GENERAL_LINEAR = 400;
  public static final int ERROR_GENERAL_WRAPPER = 300;
  public static final int ERROR_NONE = 0;
  public static final int ERROR_NO_COMPATIBLE_MEDIA_FILE = 403;
  public static final int ERROR_NO_NETWORK = 1;
  public static final int ERROR_NO_VAST_IN_WRAPPER = 303;
  public static final int ERROR_SCHEMA_VALIDATION = 101;
  public static final int ERROR_UNDEFINED = 900;
  public static final int ERROR_UNSUPPORTED_VERSION = 102;
  public static final int ERROR_VIDEO_PLAYBACK = 405;
  public static final int ERROR_VIDEO_TIMEOUT = 402;
  public static final int ERROR_WRAPPER_TIMEOUT = 301;
  public static final int ERROR_XML_PARSE = 100;
  private static final String TAG = "VASTPlayer";
  public static final String VERSION = "1.3";
  public static VASTPlayer currentPlayer;
  private String actionText;
  private Context context;
  private final int downloadTimeout;
  private final String endCardHtml;
  private boolean isRewarded;
  public VASTPlayerListener listener;
  private boolean loaded = false;
  private String maxVideoFileSize;
  private boolean postroll;
  private boolean shouldValidateSchema;
  private long skipOffset;
  private VASTModel vastModel;
  
  public VASTPlayer(Context paramContext, VASTPlayerListener paramVASTPlayerListener, boolean paramBoolean1, String paramString1, long paramLong, boolean paramBoolean2, String paramString2, boolean paramBoolean3, String paramString3, int paramInt)
  {
    this.context = paramContext;
    this.listener = paramVASTPlayerListener;
    this.postroll = paramBoolean1;
    this.skipOffset = paramLong;
    this.isRewarded = paramBoolean2;
    this.maxVideoFileSize = paramString2;
    this.shouldValidateSchema = paramBoolean3;
    this.actionText = paramString3;
    this.endCardHtml = paramString1;
    this.downloadTimeout = paramInt;
  }
  
  private void sendError(final int paramInt)
  {
    if (this.listener != null) {
      ((Activity)this.context).runOnUiThread(new Runnable()
      {
        public void run()
        {
          VASTPlayer.this.listener.vastError(paramInt);
        }
      });
    }
  }
  
  public long getMaxFileSize()
  {
    return (Float.parseFloat(this.maxVideoFileSize) * 1000000.0F);
  }
  
  public boolean isLoaded()
  {
    return this.loaded;
  }
  
  public void loadVastResponseViaURL(final String paramString)
  {
    this.vastModel = null;
    if (NetworkTools.connectedToInternet(this.context))
    {
      new Thread(new Runnable()
      {
        /* Error */
        public void run()
        {
          // Byte code:
          //   0: aconst_null
          //   1: astore_1
          //   2: aconst_null
          //   3: astore_3
          //   4: new 32	java/io/BufferedReader
          //   7: dup
          //   8: new 34	java/io/InputStreamReader
          //   11: dup
          //   12: new 36	java/net/URL
          //   15: dup
          //   16: aload_0
          //   17: getfield 21	com/upsight/mediation/vast/VASTPlayer$1:val$urlString	Ljava/lang/String;
          //   20: invokespecial 38	java/net/URL:<init>	(Ljava/lang/String;)V
          //   23: invokevirtual 42	java/net/URL:openStream	()Ljava/io/InputStream;
          //   26: invokespecial 45	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
          //   29: invokespecial 48	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
          //   32: astore_2
          //   33: new 50	java/lang/StringBuffer
          //   36: dup
          //   37: invokespecial 51	java/lang/StringBuffer:<init>	()V
          //   40: astore_1
          //   41: aload_2
          //   42: invokevirtual 55	java/io/BufferedReader:readLine	()Ljava/lang/String;
          //   45: astore_3
          //   46: aload_3
          //   47: ifnull +41 -> 88
          //   50: aload_1
          //   51: aload_3
          //   52: invokevirtual 59	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
          //   55: ldc 61
          //   57: invokestatic 67	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
          //   60: invokevirtual 59	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
          //   63: pop
          //   64: goto -23 -> 41
          //   67: astore_1
          //   68: aload_2
          //   69: astore_1
          //   70: aload_0
          //   71: getfield 19	com/upsight/mediation/vast/VASTPlayer$1:this$0	Lcom/upsight/mediation/vast/VASTPlayer;
          //   74: bipush 100
          //   76: invokestatic 71	com/upsight/mediation/vast/VASTPlayer:access$000	(Lcom/upsight/mediation/vast/VASTPlayer;I)V
          //   79: aload_2
          //   80: ifnull +7 -> 87
          //   83: aload_2
          //   84: invokevirtual 74	java/io/BufferedReader:close	()V
          //   87: return
          //   88: aload_2
          //   89: ifnull +7 -> 96
          //   92: aload_2
          //   93: invokevirtual 74	java/io/BufferedReader:close	()V
          //   96: aload_0
          //   97: getfield 19	com/upsight/mediation/vast/VASTPlayer$1:this$0	Lcom/upsight/mediation/vast/VASTPlayer;
          //   100: aload_1
          //   101: invokevirtual 77	java/lang/StringBuffer:toString	()Ljava/lang/String;
          //   104: invokevirtual 80	com/upsight/mediation/vast/VASTPlayer:loadVastResponseViaXML	(Ljava/lang/String;)V
          //   107: return
          //   108: astore_2
          //   109: aload_1
          //   110: ifnull +7 -> 117
          //   113: aload_1
          //   114: invokevirtual 74	java/io/BufferedReader:close	()V
          //   117: aload_2
          //   118: athrow
          //   119: astore_2
          //   120: goto -24 -> 96
          //   123: astore_1
          //   124: return
          //   125: astore_1
          //   126: goto -9 -> 117
          //   129: astore_3
          //   130: aload_2
          //   131: astore_1
          //   132: aload_3
          //   133: astore_2
          //   134: goto -25 -> 109
          //   137: astore_1
          //   138: aload_3
          //   139: astore_2
          //   140: goto -72 -> 68
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	143	0	this	1
          //   1	50	1	localStringBuffer	StringBuffer
          //   67	1	1	localException1	Exception
          //   69	45	1	localObject1	Object
          //   123	1	1	localIOException1	java.io.IOException
          //   125	1	1	localIOException2	java.io.IOException
          //   131	1	1	localObject2	Object
          //   137	1	1	localException2	Exception
          //   32	61	2	localBufferedReader	java.io.BufferedReader
          //   108	10	2	localObject3	Object
          //   119	12	2	localIOException3	java.io.IOException
          //   133	7	2	localObject4	Object
          //   3	49	3	str	String
          //   129	10	3	localObject5	Object
          // Exception table:
          //   from	to	target	type
          //   33	41	67	java/lang/Exception
          //   41	46	67	java/lang/Exception
          //   50	64	67	java/lang/Exception
          //   4	33	108	finally
          //   70	79	108	finally
          //   92	96	119	java/io/IOException
          //   83	87	123	java/io/IOException
          //   113	117	125	java/io/IOException
          //   33	41	129	finally
          //   41	46	129	finally
          //   50	64	129	finally
          //   4	33	137	java/lang/Exception
        }
      }).start();
      return;
    }
    sendError(1);
  }
  
  public void loadVastResponseViaXML(final String paramString)
  {
    this.vastModel = null;
    if (NetworkTools.connectedToInternet(this.context))
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          VASTProcessor localVASTProcessor = new VASTProcessor(new DefaultMediaPicker(VASTPlayer.this.context), VASTPlayer.this);
          int i = localVASTProcessor.process(VASTPlayer.this.context, paramString, VASTPlayer.this.shouldValidateSchema, VASTPlayer.this.downloadTimeout);
          if (i == 0)
          {
            VASTPlayer.access$402(VASTPlayer.this, localVASTProcessor.getModel());
            return;
          }
          VASTPlayer.this.sendError(i);
        }
      }).start();
      return;
    }
    sendError(1);
  }
  
  public void play()
  {
    if (this.vastModel != null)
    {
      currentPlayer = this;
      Intent localIntent = new Intent(this.context, VASTActivity.class);
      localIntent.putExtra("com.nexage.android.vast.player.vastModel", this.vastModel);
      localIntent.putExtra("postroll", this.postroll);
      localIntent.putExtra("endCardHtml", this.endCardHtml);
      String str = this.vastModel.getSkipOffset();
      String[] arrayOfString;
      if (str != null) {
        arrayOfString = str.split(":");
      }
      for (;;)
      {
        if (arrayOfString.length == 3) {}
        try
        {
          long l = 3600000 * Integer.parseInt(arrayOfString[0]) + 60000 * Integer.parseInt(arrayOfString[1]) + Integer.parseInt(arrayOfString[2]) * 1000;
          FuseLog.v("VASTPlayer", "Overriding server sent skip offset with VAST offset from XML: " + l);
          this.skipOffset = l;
          localIntent.putExtra("skipOffset", this.skipOffset);
          localIntent.putExtra("rewarded", this.isRewarded);
          localIntent.putExtra("actionText", this.actionText);
          this.context.startActivity(localIntent);
          return;
          arrayOfString = new String[0];
        }
        catch (NumberFormatException localNumberFormatException)
        {
          for (;;)
          {
            FuseLog.i("VASTPlayer", "Could not parse skip offset from xml: " + str + ", using cb_ms instead");
          }
        }
      }
    }
    FuseLog.d("VASTPlayer", "vastModel is null; nothing to play");
  }
  
  public void setLoaded(boolean paramBoolean)
  {
    this.loaded = paramBoolean;
    if (paramBoolean) {
      this.listener.vastReady();
    }
  }
  
  public static abstract interface VASTPlayerListener
  {
    public abstract void vastClick();
    
    public abstract void vastComplete();
    
    public abstract void vastDismiss();
    
    public abstract void vastDisplay();
    
    public abstract void vastError(int paramInt);
    
    public abstract void vastProgress(int paramInt);
    
    public abstract void vastReady();
    
    public abstract void vastReplay();
    
    public abstract void vastRewardedVideoComplete();
    
    public abstract void vastSkip();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/VASTPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */