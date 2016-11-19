package com.upsight.mediation.mraid;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.upsight.mediation.mraid.internal.MRAIDHtmlProcessor;
import com.upsight.mediation.mraid.internal.MRAIDLog;
import com.upsight.mediation.mraid.internal.MRAIDNativeFeatureManager;
import com.upsight.mediation.mraid.internal.MRAIDParser;
import com.upsight.mediation.mraid.properties.MRAIDOrientationProperties;
import com.upsight.mediation.mraid.properties.MRAIDResizeProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressLint({"ViewConstructor"})
public class MRAIDView
  extends RelativeLayout
{
  private static final int CLOSE_REGION_SIZE = 50;
  private static final int STATE_DEFAULT = 1;
  private static final int STATE_EXPANDED = 2;
  private static final int STATE_HIDDEN = 4;
  private static final int STATE_LOADING = 0;
  private static final int STATE_RESIZED = 3;
  private static final String TAG = "MRAIDView";
  public static final String VERSION = "1.0";
  private int backgroundColor;
  private String baseUrl;
  private ImageButton closeRegion;
  private int contentViewTop;
  private Context context;
  private Rect currentPosition;
  private WebView currentWebView;
  private Rect defaultPosition;
  private DisplayMetrics displayMetrics;
  private RelativeLayout expandedView;
  private GestureDetector gestureDetector;
  private Handler handler;
  private String htmlData;
  private boolean isActionBarShowing;
  private boolean isClosing;
  private boolean isExpandingFromDefault;
  private boolean isExpandingPart2;
  private boolean isForcingFullScreen;
  private final boolean isInterstitial;
  private boolean isLaidOut;
  private boolean isPageFinished;
  private boolean isViewable;
  private MRAIDViewListener listener;
  private Size maxSize;
  private String mraidJs = "<script language=\"javascript\" type=\"text/javascript\">\n(function() {\n\n\tconsole.log(\"MRAID object loading...\");\n\n\t/***************************************************************************\n\t * console logging helper\n\t **************************************************************************/\n\n\tvar LogLevelEnum = {\n\t\t\"DEBUG\"   : 0,\n\t\t\"INFO\"    : 1,\n\t\t\"WARNING\" : 2,\n\t\t\"ERROR\"   : 3,\n\t\t\"NONE\"    : 4\n\t};\n\n\tvar logLevel = LogLevelEnum.NONE;\n\tvar log = {};\n\n\tlog.d = function(msg) {\n\t\tif (logLevel <= LogLevelEnum.DEBUG) {\n\t\t\tconsole.log(\"(D-mraid.js) \" + msg);\n\t\t}\n\t};\n\n\tlog.i = function(msg) {\n\t\tif (logLevel <= LogLevelEnum.INFO) {\n\t\t\tconsole.log(\"(I-mraid.js) \" + msg);\n\t\t}\n\t};\n\n\tlog.w = function(msg) {\n\t\tif (logLevel <= LogLevelEnum.WARNING) {\n\t\t\tconsole.log(\"(W-mraid.js) \" + msg);\n\t\t}\n\t};\n\n\tlog.e = function(msg) {\n\t\tif (logLevel <= LogLevelEnum.ERROR) {\n\t\t\tconsole.log(\"(E-mraid.js) \" + msg);\n\t\t}\n\t};\n\n\t/***************************************************************************\n\t * MRAID declaration\n\t **************************************************************************/\n\n\tvar mraid = window.mraid = {};\n\n\t/***************************************************************************\n\t * constants\n\t **************************************************************************/\n\n\tvar VERSION = \"2.0\";\n\n\tvar STATES = mraid.STATES = {\n\t\t\"LOADING\" : \"loading\",\n\t\t\"DEFAULT\" : \"default\",\n\t\t\"EXPANDED\" : \"expanded\",\n\t\t\"RESIZED\" : \"resized\",\n\t\t\"HIDDEN\" : \"hidden\"\n\t};\n\n\tvar PLACEMENT_TYPES = mraid.PLACEMENT_TYPES = {\n\t\t\"INLINE\" : \"inline\",\n\t\t\"INTERSTITIAL\" : \"interstitial\"\n\t};\n\n\tvar RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION = mraid.RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION = {\n\t\t\"TOP_LEFT\" : \"top-left\",\n\t\t\"TOP_CENTER\" : \"top-center\",\n\t\t\"TOP_RIGHT\" : \"top-right\",\n\t\t\"CENTER\" : \"center\",\n\t\t\"BOTTOM_LEFT\" : \"bottom-left\",\n\t\t\"BOTTOM_CENTER\" : \"bottom-center\",\n\t\t\"BOTTOM_RIGHT\" : \"bottom-right\"\n\t};\n\n\tvar ORIENTATION_PROPERTIES_FORCE_ORIENTATION = mraid.ORIENTATION_PROPERTIES_FORCE_ORIENTATION = {\n\t\t\"PORTRAIT\" : \"portrait\",\n\t\t\"LANDSCAPE\" : \"landscape\",\n\t\t\"NONE\" : \"none\"\n\t};\n\n\tvar EVENTS = mraid.EVENTS = {\n\t\t\"ERROR\" : \"error\",\n\t\t\"READY\" : \"ready\",\n\t\t\"SIZECHANGE\" : \"sizeChange\",\n\t\t\"STATECHANGE\" : \"stateChange\",\n\t\t\"VIEWABLECHANGE\" : \"viewableChange\"\n\t};\n\n\tvar SUPPORTED_FEATURES = mraid.SUPPORTED_FEATURES = {\n\t\t\"SMS\" : \"sms\",\n\t\t\"TEL\" : \"tel\",\n\t\t\"CALENDAR\" : \"calendar\",\n\t\t\"STOREPICTURE\" : \"storePicture\",\n\t\t\"INLINEVIDEO\" : \"inlineVideo\"\n\t};\n\n\t/***************************************************************************\n\t * state\n\t **************************************************************************/\n\n\tvar state = STATES.LOADING;\n\tvar placementType = PLACEMENT_TYPES.INLINE;\n\tvar supportedFeatures = {};\n\tvar isViewable = false;\n\tvar isExpandPropertiesSet = false;\n\tvar isResizeReady = false;\n\n\tvar expandProperties = {\n\t\t\"width\" : 0,\n\t\t\"height\" : 0,\n\t\t\"useCustomClose\" : false,\n\t\t\"isModal\" : true\n\t};\n\n\tvar orientationProperties = {\n\t\t\"allowOrientationChange\" : true,\n\t\t\"forceOrientation\" : ORIENTATION_PROPERTIES_FORCE_ORIENTATION.NONE\n\t};\n\n\tvar resizeProperties = {\n\t\t\"width\" : 0,\n\t\t\"height\" : 0,\n\t\t\"customClosePosition\" : RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION.TOP_RIGHT,\n\t\t\"offsetX\" : 0,\n\t\t\"offsetY\" : 0,\n\t\t\"allowOffscreen\" : true\n\t};\n\n\tvar currentPosition = {\n\t\t\"x\" : 0,\n\t\t\"y\" : 0,\n\t\t\"width\" : 0,\n\t\t\"height\" : 0\n\t};\n\n\tvar defaultPosition = {\n\t\t\"x\" : 0,\n\t\t\"y\" : 0,\n\t\t\"width\" : 0,\n\t\t\"height\" : 0\n\t};\n\n\tvar maxSize = {\n\t\t\"width\" : 0,\n\t\t\"height\" : 0\n\t};\n\n\tvar screenSize = {\n\t\t\"width\" : 0,\n\t\t\"height\" : 0\n\t};\n\n\tvar currentOrientation = 0;\n\n\tvar listeners = {};\n\n\t/***************************************************************************\n\t * \"official\" API: methods called by creative\n\t **************************************************************************/\n\n\tmraid.addEventListener = function(event, listener) {\n\t\tlog.i(\"mraid.addEventListener \" + event + \": \" + String(listener));\n\t\tif (!event || !listener) {\n\t\t\tmraid.fireErrorEvent(\"Both event and listener are required.\", \"addEventListener\");\n\t\t\treturn;\n\t\t}\n\t\tif (!contains(event, EVENTS)) {\n\t\t\tmraid.fireErrorEvent(\"Unknown MRAID event: \" + event, \"addEventListener\");\n\t\t\treturn;\n\t\t}\n\t\tvar listenersForEvent = listeners[event] = listeners[event] || [];\n\t\t// check to make sure that the listener isn't already registered\n\t\tfor (var i = 0; i < listenersForEvent.length; i++) {\n\t\t\tvar str1 = String(listener);\n\t\t\tvar str2 = String(listenersForEvent[i]);\n\t\t\tif (listener === listenersForEvent[i] || str1 === str2) {\n\t\t\t\tlog.i(\"listener \" + str1 + \" is already registered for event \" + event);\n\t\t\t\treturn;\n\t\t\t}\n\t\t}\n\t\tlistenersForEvent.push(listener);\n\t};\n\n\tmraid.createCalendarEvent = function(parameters) {\n\t\tlog.i(\"mraid.createCalendarEvent with \" + parameters);\n\t\tif (supportedFeatures[mraid.SUPPORTED_FEATURES.CALENDAR]) {\n\t\t\tcallNative(\"createCalendarEvent?eventJSON=\"\t+ JSON.stringify(parameters));\n\t\t} else {\n\t\t\tlog.e(\"createCalendarEvent is not supported\");\n\t\t}\n\t};\n\n\tmraid.close = function() {\n\t\tlog.i(\"mraid.close\");\n\t\tif (state === STATES.LOADING\n\t\t\t\t|| (state === STATES.DEFAULT && placementType === PLACEMENT_TYPES.INLINE)\n\t\t\t\t|| state === STATES.HIDDEN) {\n\t\t\t// do nothing\n\t\t\treturn;\n\t\t}\n\t\tcallNative(\"close\");\n\t};\n\n\tmraid.expand = function(url) {\n\t\tif (url === undefined) {\n\t\t\tlog.i(\"mraid.expand (1-part)\");\n\t\t} else {\n\t\t\tlog.i(\"mraid.expand \" + url);\n\t\t}\n\t\t// The only time it is valid to call expand is when the ad is\n\t\t// a banner currently in either default or resized state.\n\t\tif (placementType !== PLACEMENT_TYPES.INLINE\n\t\t\t\t|| (state !== STATES.DEFAULT && state !== STATES.RESIZED)) {\n\t\t\treturn;\n\t\t}\n\t\tif (url === undefined) {\n\t\t\tcallNative(\"expand\");\n\t\t} else {\n\t\t\tcallNative(\"expand?url=\" + encodeURIComponent(url));\n\t\t}\n\t};\n\n\tmraid.getCurrentPosition = function() {\n\t\tlog.i(\"mraid.getCurrentPosition\");\n\t\treturn currentPosition;\n\t};\n\n\tmraid.getDefaultPosition = function() {\n\t\tlog.i(\"mraid.getDefaultPosition\");\n\t\treturn defaultPosition;\n\t};\n\n\tmraid.getExpandProperties = function() {\n\t\tlog.i(\"mraid.getExpandProperties\");\n\t\treturn expandProperties;\n\t};\n\n\tmraid.getMaxSize = function() {\n\t\tlog.i(\"mraid.getMaxSize\");\n\t\treturn maxSize;\n\t};\n\n\tmraid.getOrientationProperties = function() {\n\t\tlog.i(\"mraid.getOrientationProperties\");\n\t\treturn orientationProperties;\n\t};\n\n\tmraid.getPlacementType = function() {\n\t\tlog.i(\"mraid.getPlacementType\");\n\t\treturn placementType;\n\t};\n\n\tmraid.getResizeProperties = function() {\n\t\tlog.i(\"mraid.getResizeProperties\");\n\t\treturn resizeProperties;\n\t};\n\n\tmraid.getScreenSize = function() {\n\t\tlog.i(\"mraid.getScreenSize\");\n\t\treturn screenSize;\n\t};\n\n\tmraid.getState = function() {\n\t\tlog.i(\"mraid.getState\");\n\t\treturn state;\n\t};\n\n\tmraid.getVersion = function() {\n\t\tlog.i(\"mraid.getVersion\");\n\t\treturn VERSION;\n\t};\n\n\tmraid.isViewable = function() {\n\t\tlog.i(\"mraid.isViewable\");\n\t\treturn isViewable;\n\t};\n\n\tmraid.open = function(url) {\n\t\tlog.i(\"mraid.open \" + url);\n\t\tcallNative(\"open?url=\" + encodeURIComponent(url));\n\t};\n\n\tmraid.playVideo = function(url) {\n\t\tlog.i(\"mraid.playVideo \" + url);\n\t\tcallNative(\"playVideo?url=\" + encodeURIComponent(url));\n\t};\n\n\tmraid.removeEventListener = function(event, listener) {\n\t\tlog.i(\"mraid.removeEventListener \" + event + \" : \" + String(listener));\n\t\tif (!event) {\n\t\t\tmraid.fireErrorEvent(\"Event is required.\", \"removeEventListener\");\n\t\t\treturn;\n\t\t}\n\t\tif (!contains(event, EVENTS)) {\n\t\t\tmraid.fireErrorEvent(\"Unknown MRAID event: \" + event, \"removeEventListener\");\n\t\t\treturn;\n\t\t}\n\t\tif (listeners.hasOwnProperty(event)) {\n\t\t\tif (listener) {\n\t\t\t\tvar listenersForEvent = listeners[event];\n\t\t\t\t// try to find the given listener\n\t\t\t\tvar len = listenersForEvent.length;\n\t\t\t\tfor (var i = 0; i < len; i++) {\n\t\t\t\t\tvar registeredListener = listenersForEvent[i];\n\t\t\t\t\tvar str1 = String(listener);\n\t\t\t\t\tvar str2 = String(registeredListener);\n\t\t\t\t\tif (listener === registeredListener || str1 === str2) {\n\t\t\t\t\t\tlistenersForEvent.splice(i, 1);\n\t\t\t\t\t\tbreak;\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\tif (i === len) {\n\t\t\t\t\tlog.i(\"listener \" + str1 + \" not found for event \" + event);\n\t\t\t\t}\n\t\t\t\tif (listenersForEvent.length === 0) {\n\t\t\t\t\tdelete listeners[event];\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\t// no listener to remove was provided, so remove all listeners\n\t\t\t\t// for given event\n\t\t\t\tdelete listeners[event];\n\t\t\t}\n\t\t} else {\n\t\t\tlog.i(\"no listeners registered for event \" + event);\n\t\t}\n\t};\n\n\tmraid.resize = function() {\n\t\tlog.i(\"mraid.resize\");\n\t\t// The only time it is valid to call resize is when the ad is\n\t\t// a banner currently in either default or resized state.\n\t\t// Trigger an error if the current state is expanded.\n\t\tif (placementType === PLACEMENT_TYPES.INTERSTITIAL || state === STATES.LOADING || state === STATES.HIDDEN) {\n\t\t\t// do nothing\n\t\t\treturn;\n\t\t}\n\t\tif (state === STATES.EXPANDED) {\n\t\t\tmraid.fireErrorEvent(\"mraid.resize called when ad is in expanded state\", \"mraid.resize\");\n\t\t\treturn;\n\t\t}\n\t\tif (!isResizeReady) {\n\t\t\tmraid.fireErrorEvent(\"mraid.resize is not ready to be called\", \"mraid.resize\");\n\t\t\treturn;\n\t\t}\n\t\tcallNative(\"resize\");\n\t};\n\n\tmraid.setExpandProperties = function(properties) {\n\t\tlog.i(\"mraid.setExpandProperties\");\n\n\t\tif (!validate(properties, \"setExpandProperties\")) {\n\t\t\tlog.e(\"failed validation\");\n\t\t\treturn;\n\t\t}\n\n\t\tvar oldUseCustomClose = expandProperties.useCustomClose;\n\n\t\t// expandProperties contains 3 read-write properties: width, height, and useCustomClose;\n\t\t// the isModal property is read-only\n\t\tvar rwProps = [ \"width\", \"height\", \"useCustomClose\" ];\n\t\tfor (var i = 0; i < rwProps.length; i++) {\n\t\t\tvar propname = rwProps[i];\n\t\t\tif (properties.hasOwnProperty(propname)) {\n\t\t\t\texpandProperties[propname] = properties[propname];\n\t\t\t}\n\t\t}\n\n\t\t// In MRAID v2.0, all expanded ads by definition cover the entire screen,\n\t\t// so the only property that the native side has to know about is useCustomClose.\n\t\t// (That is, the width and height properties are not needed by the native code.)\n\t\tif (expandProperties.useCustomClose !== oldUseCustomClose) {\n\t\t\tcallNative(\"useCustomClose?useCustomClose=\"\t+ expandProperties.useCustomClose);\n\t\t}\n\n\t\tisExpandPropertiesSet = true;\n\t};\n\n\tmraid.setOrientationProperties = function(properties) {\n\t\tlog.i(\"mraid.setOrientationProperties\");\n\n\t\tif (!validate(properties, \"setOrientationProperties\")) {\n\t\t\tlog.e(\"failed validation\");\n\t\t\treturn;\n\t\t}\n\n\t\tvar newOrientationProperties = {};\n\t\tnewOrientationProperties.allowOrientationChange = orientationProperties.allowOrientationChange,\n\t\tnewOrientationProperties.forceOrientation = orientationProperties.forceOrientation;\n\n\t\t// orientationProperties contains 2 read-write properties:\n\t\t// allowOrientationChange and forceOrientation\n\t\tvar rwProps = [ \"allowOrientationChange\", \"forceOrientation\" ];\n\t\tfor (var i = 0; i < rwProps.length; i++) {\n\t\t\tvar propname = rwProps[i];\n\t\t\tif (properties.hasOwnProperty(propname)) {\n\t\t\t\tnewOrientationProperties[propname] = properties[propname];\n\t\t\t}\n\t\t}\n\n\t\t// Setting allowOrientationChange to true while setting forceOrientation\n\t\t// to either portrait or landscape\n\t\t// is considered an error condition.\n\t\tif (newOrientationProperties.allowOrientationChange\n\t\t\t\t&& newOrientationProperties.forceOrientation !== mraid.ORIENTATION_PROPERTIES_FORCE_ORIENTATION.NONE) {\n\t\t\tmraid.fireErrorEvent(\n\t\t\t\t\t\"allowOrientationChange is true but forceOrientation is \"\n\t\t\t\t\t+ newOrientationProperties.forceOrientation,\n\t\t\t\t\t\"setOrientationProperties\");\n\t\t\treturn;\n\t\t}\n\n\t\torientationProperties.allowOrientationChange = newOrientationProperties.allowOrientationChange;\n\t\torientationProperties.forceOrientation = newOrientationProperties.forceOrientation;\n\n\t\tvar params = \"allowOrientationChange=\"\n\t\t\t\t+ orientationProperties.allowOrientationChange\n\t\t\t\t+ \"&forceOrientation=\" + orientationProperties.forceOrientation;\n\n\t\tcallNative(\"setOrientationProperties?\" + params);\n\t};\n\n\tmraid.setResizeProperties = function(properties) {\n\t\tlog.i(\"mraid.setResizeProperties\");\n\n\t\tisResizeReady = false;\n\n\t\t// resizeProperties contains 6 read-write properties:\n\t\t// width, height, offsetX, offsetY, customClosePosition, allowOffscreen\n\n\t\t// The properties object passed into this function must contain width, height, offsetX, offsetY.\n\t\t// The remaining two properties are optional.\n\t\tvar requiredProps = [ \"width\", \"height\", \"offsetX\", \"offsetY\" ];\n\t\tfor (var i = 0; i < requiredProps.length; i++) {\n\t\t\tvar propname = requiredProps[i];\n\t\t\tif (!properties.hasOwnProperty(propname)) {\n\t\t\t\tmraid.fireErrorEvent(\n\t\t\t\t\t\t\"required property \" + propname + \" is missing\",\n\t\t\t\t\t\t\"mraid.setResizeProperties\");\n\t\t\t\treturn;\n\t\t\t}\n\t\t}\n\n\t\tif (!validate(properties, \"setResizeProperties\")) {\n\t\t\tmraid.fireErrorEvent(\"failed validation\", \"mraid.setResizeProperties\");\n\t\t\treturn;\n\t\t}\n\n        var adjustments = { \"x\": 0, \"y\": 0 };\n\n\t\tvar allowOffscreen = properties.hasOwnProperty(\"allowOffscreen\") ? properties.allowOffscreen : resizeProperties.allowOffscreen;\n        if (!allowOffscreen) {\n            if (properties.width > maxSize.width || properties.height > maxSize.height) {\n                mraid.fireErrorEvent(\"resize width or height is greater than the maxSize width or height\", \"mraid.setResizeProperties\");\n                return;\n            }\n            adjustments = fitResizeViewOnScreen(properties);\n        } else if (!isCloseRegionOnScreen(properties)) {\n            mraid.fireErrorEvent(\"close event region will not appear entirely onscreen\", \"mraid.setResizeProperties\");\n            return;\n        }\n\n\t\tvar rwProps = [ \"width\", \"height\", \"offsetX\", \"offsetY\", \"customClosePosition\", \"allowOffscreen\" ];\n\t\tfor (var i = 0; i < rwProps.length; i++) {\n\t\t\tvar propname = rwProps[i];\n\t\t\tif (properties.hasOwnProperty(propname)) {\n\t\t\t\tresizeProperties[propname] = properties[propname];\n\t\t\t}\n\t\t}\n\n\t\tvar params =\n\t\t\t\"width=\" + resizeProperties.width +\n\t\t\t\"&height=\" + resizeProperties.height +\n\t        \"&offsetX=\" + (resizeProperties.offsetX + adjustments.x) +\n\t        \"&offsetY=\" + (resizeProperties.offsetY + adjustments.y) +\n\t\t\t\"&customClosePosition=\" + resizeProperties.customClosePosition +\n\t\t\t\"&allowOffscreen=\" + resizeProperties.allowOffscreen;\n\n\t\tcallNative(\"setResizeProperties?\" + params);\n\n\t\tisResizeReady = true;\n\t};\n\n\tmraid.storePicture = function(url) {\n\t\tlog.i(\"mraid.storePicture \" + url);\n\t\tif (supportedFeatures[mraid.SUPPORTED_FEATURES.STOREPICTURE]) {\n\t\t\tcallNative(\"storePicture?url=\" + encodeURIComponent(url));\n\t\t} else {\n\t\t\tlog.e(\"storePicture is not supported\");\n\t\t}\n\t};\n\n\tmraid.supports = function(feature) {\n\t\tlog.i(\"mraid.supports \" + feature + \" \" + supportedFeatures[feature]);\n\t\tvar retval = supportedFeatures[feature];\n\t\tif (typeof retval === \"undefined\") {\n\t\t\tretval = false;\n\t\t}\n\t\treturn retval;\n\t};\n\n\tmraid.useCustomClose = function(isCustomClose) {\n\t\tlog.i(\"mraid.useCustomClose \" + isCustomClose);\n\t\tif (expandProperties.useCustomClose !== isCustomClose) {\n\t\t\texpandProperties.useCustomClose = isCustomClose;\n\t\t\tcallNative(\"useCustomClose?useCustomClose=\"\n\t\t\t\t\t+ expandProperties.useCustomClose);\n\t\t}\n\t};\n\n\tmraid.rewardComplete = function() {\n\t\tlog.i(\"mraid.rewardComplete\");\n\t\tcallNative(\"rewardComplete\");\n\t}\n\n\t/***************************************************************************\n\t * helper methods called by SDK\n\t **************************************************************************/\n\n\t// setters to change state\n\tmraid.setCurrentPosition = function(x, y, width, height) {\n\t\tlog.i(\"mraid.setCurrentPosition \" + x + \",\" + y + \",\" + width + \",\"\t+ height);\n\n\t\tvar previousSize = {};\n\t\tpreviousSize.width = currentPosition.width;\n\t\tpreviousSize.height = currentPosition.height;\n\t\tlog.i(\"previousSize \" + previousSize.width + \",\" + previousSize.height);\n\n\t\tcurrentPosition.x = x;\n\t\tcurrentPosition.y = y;\n\t\tcurrentPosition.width = width;\n\t\tcurrentPosition.height = height;\n\n\t\tif (width !== previousSize.width || height !== previousSize.height) {\n\t\t\tmraid.fireSizeChangeEvent(width, height);\n\t\t}\n\t};\n\n\tmraid.setDefaultPosition = function(x, y, width, height) {\n\t\tlog.i(\"mraid.setDefaultPosition \" + x + \",\" + y + \",\" + width + \",\"\t+ height);\n\t\tdefaultPosition.x = x;\n\t\tdefaultPosition.y = y;\n\t\tdefaultPosition.width = width;\n\t\tdefaultPosition.height = height;\n\t};\n\n\tmraid.setExpandSize = function(width, height) {\n\t\tlog.i(\"mraid.setExpandSize \" + width + \"x\" + height);\n\t\texpandProperties.width = width;\n\t\texpandProperties.height = height;\n\t};\n\n\tmraid.setMaxSize = function(width, height) {\n\t\tlog.i(\"mraid.setMaxSize \" + width + \"x\" + height);\n\t\tmaxSize.width = width;\n\t\tmaxSize.height = height;\n\t};\n\n\tmraid.setPlacementType = function(pt) {\n\t\tlog.i(\"mraid.setPlacementType \" + pt);\n\t\tplacementType = pt;\n\t};\n\n\tmraid.setScreenSize = function(width, height) {\n\t\tlog.i(\"mraid.setScreenSize \" + width + \"x\" + height);\n\t\tscreenSize.width = width;\n\t\tscreenSize.height = height;\n\t\tif (!isExpandPropertiesSet) {\n\t\t\texpandProperties.width = width;\n\t\t\texpandProperties.height = height;;\n\t\t}\n\t};\n\n\tmraid.setSupports = function(feature, supported) {\n\t\tlog.i(\"mraid.setSupports \" + feature + \" \" + supported);\n\t\tsupportedFeatures[feature] = supported;\n\t};\n\n\t// methods to fire events\n\n\tmraid.fireErrorEvent = function(message, action) {\n\t\tlog.i(\"mraid.fireErrorEvent \" + message + \" \" + action);\n\t\tfireEvent(mraid.EVENTS.ERROR, message, action);\n\t};\n\n\tmraid.fireReadyEvent = function() {\n\t\tlog.i(\"mraid.fireReadyEvent\");\n\t\tfireEvent(mraid.EVENTS.READY);\n\t};\n\n\tmraid.fireSizeChangeEvent = function(width, height) {\n\t\tlog.i(\"mraid.fireSizeChangeEvent \" + width + \"x\" + height);\n\t\tif (state !== mraid.STATES.LOADING) {\n\t\t\tfireEvent(mraid.EVENTS.SIZECHANGE, width, height);\n\t\t}\n\t};\n\n\tmraid.fireStateChangeEvent = function(newState) {\n\t\tlog.i(\"mraid.fireStateChangeEvent \" + newState);\n\t\tif (state !== newState) {\n\t\t\tstate = newState;\n\t\t\tfireEvent(mraid.EVENTS.STATECHANGE, state);\n\t\t}\n\t};\n\n\tmraid.fireViewableChangeEvent = function(newIsViewable) {\n\t\tlog.i(\"mraid.fireViewableChangeEvent \" + newIsViewable);\n\t\tif (isViewable !== newIsViewable) {\n\t\t\tisViewable = newIsViewable;\n\t\t\tfireEvent(mraid.EVENTS.VIEWABLECHANGE, isViewable);\n\t\t}\n\t};\n\n\t/***************************************************************************\n\t * internal helper methods\n\t **************************************************************************/\n\n\tfunction callNative(command) {\n\t\tvar iframe = document.createElement(\"IFRAME\");\n\t\tiframe.setAttribute(\"src\", \"mraid://\" + command);\n\t\tdocument.documentElement.appendChild(iframe);\n\t\tiframe.parentNode.removeChild(iframe);\n\t\tiframe = null;\n\t};\n\n\tfunction fireEvent(event) {\n\t\tvar args = Array.prototype.slice.call(arguments);\n\t\targs.shift();\n\t\tlog.i(\"fireEvent \" + event + \" [\" + args.toString() + \"]\");\n\t\tvar eventListeners = listeners[event];\n\t\tif (eventListeners) {\n\t\t\tvar len = eventListeners.length;\n\t\t\tlog.i(len + \" listener(s) found\");\n\t\t\tfor (var i = 0; i < len; i++) {\n\t\t\t\teventListeners[i].apply(null, args);\n\t\t\t}\n\t\t} else {\n\t\t\tlog.i(\"no listeners found\");\n\t\t}\n\t};\n\n\tfunction contains(value, array) {\n\t\tfor ( var i in array) {\n\t\t\tif (array[i] === value) {\n\t\t\t\treturn true;\n\t\t\t}\n\t\t}\n\t\treturn false;\n\t};\n\n\tmnRewardUser = function(mnType, mnValue) {\n\t\tlog.i(\"mnRewardUser\");\n\t\tmraid.rewardComplete();\n\t};\n\n\t// The action parameter is a string which is the name of the setter function\n\t// which called this function\n\t// (in other words, setExpandPropeties, setOrientationProperties, or\n\t// setResizeProperties).\n\t// It serves both as the key to get the the appropriate set of validating\n\t// functions from the allValidators object\n\t// as well as the action parameter of any error event that may be thrown.\n\tfunction validate(properties, action) {\n\t\tvar retval = true;\n\t\tvar validators = allValidators[action];\n\t\tfor (var prop in properties) {\n\t\t\tvar validator = validators[prop];\n\t\t\tvar value = properties[prop];\n\t\t\tif (validator && !validator(value)) {\n\t\t\t\tmraid.fireErrorEvent(\"Value of property \" + prop + \" (\" + value\t+ \") is invalid\", \"mraid.\" + action);\n\t\t\t\tretval = false;\n\t\t\t}\n\t\t}\n\t\treturn retval;\n\t};\n\n\tvar allValidators = {\n\t\t\"setExpandProperties\" : {\n\t\t\t// In MRAID 2.0, the only property in expandProperties we actually care about is useCustomClose.\n\t\t\t// Still, we'll do a basic sanity check on the width and height properties, too.\n\t\t\t\"width\" : function(width) {\n\t\t\t\treturn !isNaN(width);\n\t\t\t},\n\t\t\t\"height\" : function(height) {\n\t\t\t\treturn !isNaN(height);\n\t\t\t},\n\t\t\t\"useCustomClose\" : function(useCustomClose) {\n\t\t\t\treturn (typeof useCustomClose === \"boolean\");\n\t\t\t}\n\t\t},\n\t\t\"setOrientationProperties\" : {\n\t\t\t\"allowOrientationChange\" : function(allowOrientationChange) {\n\t\t\t\treturn (typeof allowOrientationChange === \"boolean\");\n\t\t\t},\n\t\t\t\"forceOrientation\" : function(forceOrientation) {\n\t\t\t\tvar validValues = [ \"portrait\", \"landscape\", \"none\" ];\n\t\t\t\treturn (typeof forceOrientation === \"string\" && validValues.indexOf(forceOrientation) !== -1);\n\t\t\t}\n\t\t},\n\t\t\"setResizeProperties\" : {\n\t\t\t\"width\" : function(width) {\n\t\t\t\treturn !isNaN(width) && 50 <= width;\n\t\t\t},\n\t\t\t\"height\" : function(height) {\n\t\t\t\treturn !isNaN(height) && 50 <= height;\n\t\t\t},\n\t\t\t\"offsetX\" : function(offsetX) {\n\t\t\t\treturn !isNaN(offsetX);\n\t\t\t},\n\t\t\t\"offsetY\" : function(offsetY) {\n\t\t\t\treturn !isNaN(offsetY);\n\t\t\t},\n\t\t\t\"customClosePosition\" : function(customClosePosition) {\n\t\t\t\tvar validPositions = [ \"top-left\", \"top-center\", \"top-right\",\n\t\t\t\t                       \"center\",\n\t\t\t\t                       \"bottom-left\", \"bottom-center\",\t\"bottom-right\" ];\n\t\t\t\treturn (typeof customClosePosition === \"string\" && validPositions.indexOf(customClosePosition) !== -1);\n\t\t\t},\n\t\t\t\"allowOffscreen\" : function(allowOffscreen) {\n\t\t\t\treturn (typeof allowOffscreen === \"boolean\");\n\t\t\t}\n\t\t}\n\t};\n\n    function isCloseRegionOnScreen(properties) {\n        log.d(\"isCloseRegionOnScreen\");\n        log.d(\"defaultPosition \" + defaultPosition.x + \" \" + defaultPosition.y);\n        log.d(\"offset \" + properties.offsetX + \" \" + properties.offsetY);\n\n        var resizeRect = {};\n        resizeRect.x = defaultPosition.x + properties.offsetX;\n        resizeRect.y = defaultPosition.y + properties.offsetY;\n        resizeRect.width = properties.width;\n        resizeRect.height = properties.height;\n        printRect(\"resizeRect\", resizeRect);\n\n\t\tvar customClosePosition = properties.hasOwnProperty(\"customClosePosition\") ?\n\t\t\t\tproperties.customClosePosition : resizeProperties.customClosePosition;\n        log.d(\"customClosePosition \" + customClosePosition);\n\n        var closeRect = { \"width\": 50, \"height\": 50 };\n\n        if (customClosePosition.search(\"left\") !== -1) {\n            closeRect.x = resizeRect.x;\n        } else if (customClosePosition.search(\"center\") !== -1) {\n            closeRect.x = resizeRect.x + (resizeRect.width / 2) - 25;\n        } else if (customClosePosition.search(\"right\") !== -1) {\n            closeRect.x = resizeRect.x + resizeRect.width - 50;\n        }\n\n        if (customClosePosition.search(\"top\") !== -1) {\n            closeRect.y = resizeRect.y;\n        } else if (customClosePosition === \"center\") {\n            closeRect.y = resizeRect.y + (resizeRect.height / 2) - 25;\n        } else if (customClosePosition.search(\"bottom\") !== -1) {\n            closeRect.y = resizeRect.y + resizeRect.height - 50;\n        }\n\n        var maxRect = { \"x\": 0, \"y\": 0 };\n        maxRect.width = maxSize.width;\n        maxRect.height = maxSize.height;\n\n        return isRectContained(maxRect, closeRect);\n    }\n\n    function fitResizeViewOnScreen(properties) {\n        log.d(\"fitResizeViewOnScreen\");\n        log.d(\"defaultPosition \" + defaultPosition.x + \" \" + defaultPosition.y);\n        log.d(\"offset \" + properties.offsetX + \" \" + properties.offsetY);\n\n        var resizeRect = {};\n        resizeRect.x = defaultPosition.x + properties.offsetX;\n        resizeRect.y = defaultPosition.y + properties.offsetY;\n        resizeRect.width = properties.width;\n        resizeRect.height = properties.height;\n        printRect(\"resizeRect\", resizeRect);\n\n        var maxRect = { \"x\": 0, \"y\": 0 };\n        maxRect.width = maxSize.width;\n        maxRect.height = maxSize.height;\n\n        var adjustments = { \"x\": 0, \"y\": 0 };\n\n        if (isRectContained(maxRect, resizeRect)) {\n            log.d(\"no adjustment necessary\");\n            return adjustments;\n        }\n\n        if (resizeRect.x < maxRect.x) {\n            adjustments.x = maxRect.x - resizeRect.x;\n        } else if ((resizeRect.x + resizeRect.width) > (maxRect.x + maxRect.width)) {\n            adjustments.x = (maxRect.x + maxRect.width) - (resizeRect.x + resizeRect.width);\n        }\n        log.d(\"adjustments.x \" + adjustments.x);\n\n        if (resizeRect.y < maxRect.y) {\n            adjustments.y = maxRect.y - resizeRect.y;\n        } else if ((resizeRect.y + resizeRect.height) > (maxRect.y + maxRect.height)) {\n            adjustments.y = (maxRect.y + maxRect.height) - (resizeRect.y + resizeRect.height);\n        }\n        log.d(\"adjustments.y \" + adjustments.y);\n\n        resizeRect.x = defaultPosition.x + properties.offsetX + adjustments.x;\n        resizeRect.y = defaultPosition.y + properties.offsetY + adjustments.y;\n        printRect(\"adjusted resizeRect\", resizeRect);\n\n        return adjustments;\n    }\n\n    function isRectContained(containingRect, containedRect) {\n        log.d(\"isRectContained\");\n        printRect(\"containingRect\", containingRect);\n        printRect(\"containedRect\", containedRect);\n        return (containedRect.x >= containingRect.x &&\n            (containedRect.x + containedRect.width) <= (containingRect.x + containingRect.width) &&\n            containedRect.y >= containingRect.y &&\n            (containedRect.y + containedRect.height) <= (containingRect.y + containingRect.height));\n    }\n\n    function printRect(label, rect) {\n        log.d(label +\n            \" [\" + rect.x + \",\" + rect.y + \"]\" +\n            \",[\" + (rect.x + rect.width) + \",\" + (rect.y + rect.height) + \"]\" +\n            \" (\" + rect.width + \"x\" + rect.height + \")\");\n    }\n\n\tmraid.dumpListeners = function() {\n\t\tvar nEvents = Object.keys(listeners).length;\n\t\tlog.i(\"dumping listeners (\" + nEvents + \" events)\");\n\t\tfor ( var event in listeners) {\n\t\t\tvar eventListeners = listeners[event];\n\t\t\tlog.i(\"  \" + event + \" contains \" + eventListeners.length + \" listeners\");\n\t\t\tfor (var i = 0; i < eventListeners.length; i++) {\n\t\t\t\tlog.i(\"    \" + eventListeners[i]);\n\t\t\t}\n\t\t}\n\t};\nmraid.success = function()\n{\nlog.i(\"mraid.success\");\n    if (state === STATES.LOADING ||\n        (state === STATES.DEFAULT && placementType === PLACEMENT_TYPES.INLINE) ||\n        state === STATES.HIDDEN) {\n        // do nothing\n        return;\n    }\n    callNative(\"success\");\n}\nmraid.replay = function()\n{\nlog.i(\"mraid.replay\");\ncallNative(\"replay\");\n};\n\tconsole.log(\"MRAID object loaded\");\n\n})();\n</script>";
  private MRAIDWebChromeClient mraidWebChromeClient;
  private MRAIDWebViewClient mraidWebViewClient;
  private MRAIDNativeFeatureListener nativeFeatureListener;
  private MRAIDNativeFeatureManager nativeFeatureManager;
  private MRAIDOrientationProperties orientationProperties;
  private int origTitleBarVisibility;
  private final int originalRequestedOrientation;
  private MRAIDResizeProperties resizeProperties;
  private RelativeLayout resizedView;
  private int rotateMode;
  private Size screenSize;
  private int state;
  private View titleBar;
  private boolean useCustomClose;
  private WebView webView;
  private WebView webViewPart2;
  
  protected MRAIDView(Context paramContext, String paramString1, String paramString2, int paramInt, String[] paramArrayOfString, MRAIDViewListener paramMRAIDViewListener, MRAIDNativeFeatureListener paramMRAIDNativeFeatureListener, boolean paramBoolean)
  {
    super(paramContext);
    this.context = paramContext;
    this.baseUrl = paramString1;
    this.isInterstitial = paramBoolean;
    this.backgroundColor = paramInt;
    this.state = 0;
    this.isViewable = false;
    this.useCustomClose = false;
    this.orientationProperties = new MRAIDOrientationProperties();
    this.resizeProperties = new MRAIDResizeProperties();
    this.nativeFeatureManager = new MRAIDNativeFeatureManager(paramContext, Arrays.asList(paramArrayOfString));
    this.listener = paramMRAIDViewListener;
    this.nativeFeatureListener = paramMRAIDNativeFeatureListener;
    this.displayMetrics = new DisplayMetrics();
    ((Activity)paramContext).getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
    this.currentPosition = new Rect();
    this.defaultPosition = new Rect();
    this.maxSize = new Size(null);
    this.screenSize = new Size(null);
    this.originalRequestedOrientation = ((Activity)paramContext).getRequestedOrientation();
    this.gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener()
    {
      public boolean onScroll(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
      {
        return true;
      }
    });
    this.handler = new Handler(Looper.getMainLooper());
    this.mraidWebChromeClient = new MRAIDWebChromeClient(null);
    this.mraidWebViewClient = new MRAIDWebViewClient(null);
    this.webView = createWebView();
    this.currentWebView = this.webView;
    addView(this.webView);
    this.htmlData = MRAIDHtmlProcessor.processRawHtml(this.mraidJs + paramString2);
    this.webView.loadDataWithBaseURL(paramString1, this.htmlData, "text/html", "UTF-8", null);
  }
  
  public MRAIDView(Context paramContext, String paramString1, String paramString2, String[] paramArrayOfString, MRAIDViewListener paramMRAIDViewListener, MRAIDNativeFeatureListener paramMRAIDNativeFeatureListener)
  {
    this(paramContext, paramString1, paramString2, 0, paramArrayOfString, paramMRAIDViewListener, paramMRAIDNativeFeatureListener, false);
  }
  
  private void addCloseRegion(View paramView)
  {
    this.closeRegion = new ImageButton(this.context);
    this.closeRegion.setBackgroundColor(0);
    this.closeRegion.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MRAIDView.this.close();
      }
    });
    this.closeRegion.setVisibility(4);
    this.closeRegion.postDelayed(new Runnable()
    {
      public void run()
      {
        MRAIDView.this.closeRegion.setVisibility(0);
      }
    }, 1000L);
    if ((paramView == this.expandedView) && (!this.useCustomClose))
    {
      Drawable localDrawable1 = MRaidDrawables.getDrawableForImage(this.context, "/assets/drawable/close_button_normal.png", "close_button_normal", -16777216);
      Drawable localDrawable2 = MRaidDrawables.getDrawableForImage(this.context, "/assets/drawable/close_button_pressed.png", "close_button_pressed", -16777216);
      StateListDrawable localStateListDrawable = new StateListDrawable();
      localStateListDrawable.addState(new int[] { -16842919 }, localDrawable1);
      localStateListDrawable.addState(new int[] { 16842919 }, localDrawable2);
      this.closeRegion.setImageDrawable(localStateListDrawable);
      this.closeRegion.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
    ((ViewGroup)paramView).addView(this.closeRegion);
  }
  
  private void applyOrientationProperties()
  {
    MRAIDLog.v("MRAIDView", "applyOrientationProperties " + this.orientationProperties.allowOrientationChange + " " + this.orientationProperties.forceOrientationString());
    Activity localActivity = (Activity)this.context;
    int i;
    String str;
    if (getResources().getConfiguration().orientation == 1)
    {
      i = 1;
      StringBuilder localStringBuilder = new StringBuilder().append("currentOrientation ");
      if (i == 0) {
        break label159;
      }
      str = "portrait";
      label94:
      MRAIDLog.v("MRAIDView", str);
    }
    switch (this.rotateMode)
    {
    default: 
      localActivity.setRequestedOrientation(2);
    case 2: 
    case 0: 
    case 1: 
      do
      {
        return;
        i = 0;
        break;
        str = "landscape";
        break label94;
        if (this.orientationProperties.forceOrientation == 0)
        {
          localActivity.setRequestedOrientation(1);
          return;
        }
        if (this.orientationProperties.forceOrientation == 1)
        {
          localActivity.setRequestedOrientation(0);
          return;
        }
      } while (!this.orientationProperties.allowOrientationChange);
      localActivity.setRequestedOrientation(2);
      return;
    case 3: 
      label159:
      localActivity.setRequestedOrientation(6);
      return;
    }
    localActivity.setRequestedOrientation(7);
  }
  
  private void calculateMaxSize()
  {
    Rect localRect = new Rect();
    Window localWindow = ((Activity)this.context).getWindow();
    localWindow.getDecorView().getWindowVisibleDisplayFrame(localRect);
    MRAIDLog.v("MRAIDView", "calculateMaxSize frame [" + localRect.left + "," + localRect.top + "][" + localRect.right + "," + localRect.bottom + "] (" + localRect.width() + "x" + localRect.height() + ")");
    int i = localRect.top;
    this.contentViewTop = localWindow.findViewById(16908290).getTop();
    int j = this.contentViewTop;
    MRAIDLog.v("MRAIDView", "calculateMaxSize statusHeight " + i);
    MRAIDLog.v("MRAIDView", "calculateMaxSize titleHeight " + (j - i));
    MRAIDLog.v("MRAIDView", "calculateMaxSize contentViewTop " + this.contentViewTop);
    i = localRect.width();
    j = this.screenSize.height - this.contentViewTop;
    MRAIDLog.v("MRAIDView", "calculateMaxSize max size " + i + "x" + j);
    if ((i != this.maxSize.width) || (j != this.maxSize.height))
    {
      this.maxSize.width = i;
      this.maxSize.height = j;
      if (this.isPageFinished) {
        setMaxSize();
      }
    }
  }
  
  private void calculatePosition(boolean paramBoolean)
  {
    int[] arrayOfInt = new int[2];
    Object localObject;
    String str;
    label24:
    int i;
    int j;
    if (paramBoolean)
    {
      localObject = this.currentWebView;
      if (!paramBoolean) {
        break label179;
      }
      str = "current";
      ((View)localObject).getLocationOnScreen(arrayOfInt);
      i = arrayOfInt[0];
      j = arrayOfInt[1];
      MRAIDLog.v("MRAIDView", "calculatePosition " + str + " locationOnScreen [" + i + "," + j + "]");
      MRAIDLog.v("MRAIDView", "calculatePosition " + str + " contentViewTop " + this.contentViewTop);
      if (j >= this.contentViewTop) {
        break label187;
      }
      MRAIDLog.v("MRAIDView", "calculatePosition " + str + " y < contentViewTop, returning");
    }
    label179:
    label187:
    label292:
    label378:
    label380:
    label402:
    for (;;)
    {
      return;
      localObject = this;
      break;
      str = "default";
      break label24;
      j -= this.contentViewTop;
      int k = ((View)localObject).getWidth();
      int m = ((View)localObject).getHeight();
      MRAIDLog.v("MRAIDView", "calculatePosition " + str + " position [" + i + "," + j + "] (" + k + "x" + m + ")");
      if (paramBoolean)
      {
        localObject = this.currentPosition;
        if ((i == ((Rect)localObject).left) && (j == ((Rect)localObject).top) && (k == ((Rect)localObject).width()) && (m == ((Rect)localObject).height())) {
          break label378;
        }
        if (!paramBoolean) {
          break label380;
        }
        this.currentPosition = new Rect(i, j, i + k, j + m);
      }
      for (;;)
      {
        if (!this.isPageFinished) {
          break label402;
        }
        if (!paramBoolean) {
          break label404;
        }
        setCurrentPosition();
        return;
        localObject = this.defaultPosition;
        break label292;
        break;
        this.defaultPosition = new Rect(i, j, i + k, j + m);
      }
    }
    label404:
    setDefaultPosition();
  }
  
  private void calculateScreenSize()
  {
    int i = 1;
    StringBuilder localStringBuilder;
    if (getResources().getConfiguration().orientation == 1)
    {
      localStringBuilder = new StringBuilder().append("calculateScreenSize orientation ");
      if (i == 0) {
        break label159;
      }
    }
    label159:
    for (String str = "portrait";; str = "landscape")
    {
      MRAIDLog.v("MRAIDView", str);
      i = this.displayMetrics.widthPixels;
      int j = this.displayMetrics.heightPixels;
      MRAIDLog.v("MRAIDView", "calculateScreenSize screen size " + i + "x" + j);
      if ((i != this.screenSize.width) || (j != this.screenSize.height))
      {
        this.screenSize.width = i;
        this.screenSize.height = j;
        if (this.isPageFinished) {
          setScreenSize();
        }
      }
      return;
      i = 0;
      break;
    }
  }
  
  private void close()
  {
    close(true);
  }
  
  private void close(final boolean paramBoolean)
  {
    MRAIDLog.v("MRAIDView-JS callback", "close " + this.state);
    this.handler.post(new Runnable()
    {
      public void run()
      {
        if ((MRAIDView.this.state == 0) || ((MRAIDView.this.state == 1) && (!MRAIDView.this.isInterstitial)) || (MRAIDView.this.state == 4)) {}
        do
        {
          return;
          if ((MRAIDView.this.state == 1) || (MRAIDView.this.state == 2))
          {
            MRAIDView.this.closeFromExpanded(paramBoolean);
            return;
          }
        } while (MRAIDView.this.state != 3);
        MRAIDView.this.closeFromResized(paramBoolean);
      }
    });
  }
  
  private void closeFromExpanded(final boolean paramBoolean)
  {
    if ((this.state == 1) && (this.isInterstitial))
    {
      this.state = 4;
      pauseWebView(this.currentWebView);
      this.isClosing = true;
      this.expandedView.removeAllViews();
      ((FrameLayout)((Activity)this.context).findViewById(16908290)).removeView(this.expandedView);
      this.expandedView = null;
      this.closeRegion = null;
      restoreOriginalOrientation();
      restoreOriginalScreenState();
      if (this.webViewPart2 != null) {
        break label138;
      }
      addView(this.webView);
    }
    for (;;)
    {
      this.handler.post(new Runnable()
      {
        public void run()
        {
          MRAIDView.this.fireStateChangeEvent();
          if ((MRAIDView.this.listener != null) && (paramBoolean)) {
            MRAIDView.this.listener.mraidViewClose(MRAIDView.this);
          }
        }
      });
      return;
      if ((this.state != 2) && (this.state != 3)) {
        break;
      }
      this.state = 1;
      break;
      label138:
      this.webViewPart2.setWebChromeClient(null);
      this.webViewPart2.setWebViewClient(null);
      this.webViewPart2 = null;
      this.webView.setWebChromeClient(this.mraidWebChromeClient);
      this.webView.setWebViewClient(this.mraidWebViewClient);
      this.currentWebView = this.webView;
    }
  }
  
  private void closeFromResized(final boolean paramBoolean)
  {
    this.state = 1;
    this.isClosing = true;
    removeResizeView();
    addView(this.webView);
    this.handler.post(new Runnable()
    {
      public void run()
      {
        MRAIDView.this.fireStateChangeEvent();
        if ((MRAIDView.this.listener != null) && (paramBoolean)) {
          MRAIDView.this.listener.mraidViewClose(MRAIDView.this);
        }
      }
    });
  }
  
  private void createCalendarEvent(String paramString)
  {
    MRAIDLog.v("MRAIDView-JS callback", "createCalendarEvent " + paramString);
    if (this.nativeFeatureListener != null) {
      this.nativeFeatureListener.mraidNativeFeatureCreateCalendarEvent(paramString);
    }
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  private WebView createWebView()
  {
    WebView local2 = new WebView(this.context)
    {
      private static final String TAG = "MRAIDView-WebView";
      
      public void onConfigurationChanged(Configuration paramAnonymousConfiguration)
      {
        super.onConfigurationChanged(paramAnonymousConfiguration);
        StringBuilder localStringBuilder = new StringBuilder().append("onConfigurationChanged ");
        if (paramAnonymousConfiguration.orientation == 1) {}
        for (paramAnonymousConfiguration = "portrait";; paramAnonymousConfiguration = "landscape")
        {
          MRAIDLog.v("MRAIDView-WebView", paramAnonymousConfiguration);
          if (MRAIDView.this.isInterstitial) {
            ((Activity)MRAIDView.this.context).getWindowManager().getDefaultDisplay().getMetrics(MRAIDView.this.displayMetrics);
          }
          return;
        }
      }
      
      protected void onLayout(boolean paramAnonymousBoolean, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
      {
        super.onLayout(paramAnonymousBoolean, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4);
        MRAIDView.this.onLayoutWebView(this, paramAnonymousBoolean, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4);
      }
      
      protected void onVisibilityChanged(View paramAnonymousView, int paramAnonymousInt)
      {
        super.onVisibilityChanged(paramAnonymousView, paramAnonymousInt);
        MRAIDLog.v("MRAIDView-WebView", "onVisibilityChanged " + MRAIDView.getVisibilityString(paramAnonymousInt));
        if (MRAIDView.this.isInterstitial) {
          MRAIDView.this.setViewable(paramAnonymousInt);
        }
      }
      
      protected void onWindowVisibilityChanged(int paramAnonymousInt)
      {
        super.onWindowVisibilityChanged(paramAnonymousInt);
        int i = getVisibility();
        MRAIDLog.v("MRAIDView-WebView", "onWindowVisibilityChanged " + MRAIDView.getVisibilityString(paramAnonymousInt) + " (actual " + MRAIDView.getVisibilityString(i) + ")");
        if (MRAIDView.this.isInterstitial) {
          MRAIDView.this.setViewable(i);
        }
      }
    };
    local2.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    local2.setBackgroundColor(this.backgroundColor);
    local2.setScrollContainer(false);
    local2.setVerticalScrollBarEnabled(false);
    local2.setHorizontalScrollBarEnabled(false);
    local2.setScrollBarStyle(33554432);
    local2.setFocusableInTouchMode(true);
    local2.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        switch (paramAnonymousMotionEvent.getAction())
        {
        }
        for (;;)
        {
          return false;
          if (!paramAnonymousView.hasFocus()) {
            paramAnonymousView.requestFocus();
          }
        }
      }
    });
    local2.getSettings().setJavaScriptEnabled(true);
    local2.getSettings().setDomStorageEnabled(true);
    local2.getSettings().setDatabaseEnabled(true);
    if (Build.VERSION.SDK_INT < 19) {
      local2.getSettings().setDatabasePath(getContext().getFilesDir() + local2.getContext().getPackageName() + "/databases/");
    }
    local2.setWebChromeClient(this.mraidWebChromeClient);
    local2.setWebViewClient(this.mraidWebViewClient);
    return local2;
  }
  
  private void expand(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder().append("expand ");
    if (paramString != null) {}
    for (final String str = paramString;; str = "(1-part)")
    {
      MRAIDLog.v("MRAIDView-JS callback", str);
      if (((!this.isInterstitial) || (this.state == 0)) && ((this.isInterstitial) || (this.state == 1) || (this.state == 3))) {
        break;
      }
      return;
    }
    if (TextUtils.isEmpty(paramString))
    {
      if ((this.isInterstitial) || (this.state == 1)) {
        removeView(this.webView);
      }
      for (;;)
      {
        expandHelper();
        return;
        if (this.state == 3) {
          removeResizeView();
        }
      }
    }
    try
    {
      paramString = URLDecoder.decode(paramString, "UTF-8");
      str = paramString;
      if (!paramString.startsWith("http://"))
      {
        str = paramString;
        if (!paramString.startsWith("https://")) {
          str = this.baseUrl + paramString;
        }
      }
      new Thread(new Runnable()
      {
        public void run()
        {
          final String str = MRAIDView.this.getStringFromUrl(str);
          if (!TextUtils.isEmpty(str))
          {
            ((Activity)MRAIDView.this.context).runOnUiThread(new Runnable()
            {
              public void run()
              {
                if (MRAIDView.this.state == 3)
                {
                  MRAIDView.this.removeResizeView();
                  MRAIDView.this.addView(MRAIDView.this.webView);
                }
                MRAIDView.this.webView.setWebChromeClient(null);
                MRAIDView.this.webView.setWebViewClient(null);
                MRAIDView.access$1602(MRAIDView.this, MRAIDView.this.createWebView());
                MRAIDView.this.injectMraidJs(MRAIDView.this.webViewPart2);
                MRAIDView.this.webViewPart2.loadDataWithBaseURL(MRAIDView.this.baseUrl, str, "text/html", "UTF-8", null);
                MRAIDView.access$2002(MRAIDView.this, MRAIDView.this.webViewPart2);
                MRAIDView.access$2102(MRAIDView.this, true);
                MRAIDView.this.expandHelper();
              }
            });
            return;
          }
          MRAIDLog.i("Could not load part 2 expanded content for URL: " + str);
        }
      }, "2-part-content").start();
      return;
    }
    catch (UnsupportedEncodingException paramString) {}
  }
  
  private void expandHelper()
  {
    if (!this.isInterstitial) {
      this.state = 2;
    }
    applyOrientationProperties();
    forceFullScreen();
    this.expandedView = new RelativeLayout(this.context);
    this.expandedView.addView(this.currentWebView);
    if (!this.useCustomClose)
    {
      addCloseRegion(this.expandedView);
      setCloseRegionPosition(this.expandedView);
    }
    ((Activity)this.context).addContentView(this.expandedView, new RelativeLayout.LayoutParams(-1, -1));
    this.isExpandingFromDefault = true;
  }
  
  private void fireReadyEvent()
  {
    MRAIDLog.v("MRAIDView", "fireReadyEvent");
    injectJavaScript("mraid.fireReadyEvent();");
  }
  
  @SuppressLint({"DefaultLocale"})
  private void fireStateChangeEvent()
  {
    MRAIDLog.v("MRAIDView", "fireStateChangeEvent");
    StringBuilder localStringBuilder = new StringBuilder().append("mraid.fireStateChangeEvent('");
    int i = this.state;
    injectJavaScript(new String[] { "loading", "default", "expanded", "resized", "hidden" }[i] + "');");
  }
  
  private void fireViewableChangeEvent()
  {
    MRAIDLog.v("MRAIDView", "fireViewableChangeEvent");
    injectJavaScript("mraid.fireViewableChangeEvent(" + this.isViewable + ");");
  }
  
  private void forceFullScreen()
  {
    MRAIDLog.v("MRAIDView", "forceFullScreen");
    Object localObject = (Activity)this.context;
    this.origTitleBarVisibility = -9;
    try
    {
      if (Activity.class.getMethod("getActionBar", new Class[0]) != null)
      {
        localObject = ((Activity)localObject).getActionBar();
        if (localObject != null)
        {
          this.isActionBarShowing = ((ActionBar)localObject).isShowing();
          ((ActionBar)localObject).hide();
        }
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    MRAIDLog.v("MRAIDView", "isActionBarShowing " + this.isActionBarShowing);
    MRAIDLog.v("MRAIDView", "origTitleBarVisibility " + getVisibilityString(this.origTitleBarVisibility));
    this.isForcingFullScreen = false;
  }
  
  private static String getOrientationString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "UNKNOWN";
    case -1: 
      return "UNSPECIFIED";
    case 0: 
      return "LANDSCAPE";
    }
    return "PORTRAIT";
  }
  
  private String getStringFromFileUrl(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    paramString = paramString.split("/");
    if (paramString[3].equals("android_asset"))
    {
      BufferedReader localBufferedReader;
      try
      {
        localBufferedReader = new BufferedReader(new InputStreamReader(this.context.getAssets().open(paramString[4])));
        paramString = localBufferedReader.readLine();
        localStringBuffer.append(paramString);
        while (paramString != null)
        {
          paramString = localBufferedReader.readLine();
          localStringBuffer.append(paramString);
          continue;
          return localStringBuffer.toString();
        }
      }
      catch (IOException paramString)
      {
        MRAIDLog.i("Error fetching file: " + paramString.getMessage());
      }
      for (;;)
      {
        localBufferedReader.close();
      }
    }
    MRAIDLog.i("Unknown location to fetch file content");
    return "";
  }
  
  private String getStringFromUrl(String paramString)
  {
    Object localObject3;
    if (paramString.startsWith("file:///")) {
      localObject3 = getStringFromFileUrl(paramString);
    }
    for (;;)
    {
      return (String)localObject3;
      Object localObject6 = null;
      Object localObject4 = null;
      InputStream localInputStream = null;
      Object localObject5 = null;
      StringBuilder localStringBuilder = null;
      Object localObject1 = localObject4;
      Object localObject2 = localStringBuilder;
      localObject3 = localObject5;
      try
      {
        localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
        localObject1 = localObject4;
        localObject2 = localStringBuilder;
        localObject3 = localObject5;
        int i = localHttpURLConnection.getResponseCode();
        localObject1 = localObject4;
        localObject2 = localStringBuilder;
        localObject3 = localObject5;
        MRAIDLog.v("MRAIDView", "response code " + i);
        paramString = (String)localObject6;
        if (i == 200)
        {
          localObject1 = localObject4;
          localObject2 = localStringBuilder;
          localObject3 = localObject5;
          MRAIDLog.v("MRAIDView", "getContentLength " + localHttpURLConnection.getContentLength());
          localObject1 = localObject4;
          localObject2 = localStringBuilder;
          localObject3 = localObject5;
          localInputStream = localHttpURLConnection.getInputStream();
          localObject1 = localObject4;
          localObject2 = localInputStream;
          localObject3 = localInputStream;
          paramString = new byte['ל'];
          localObject1 = localObject4;
          localObject2 = localInputStream;
          localObject3 = localInputStream;
          localStringBuilder = new StringBuilder();
          for (;;)
          {
            localObject1 = localObject4;
            localObject2 = localInputStream;
            localObject3 = localInputStream;
            i = localInputStream.read(paramString);
            if (i == -1) {
              break;
            }
            localObject1 = localObject4;
            localObject2 = localInputStream;
            localObject3 = localInputStream;
            localStringBuilder.append(new String(paramString, 0, i));
          }
        }
      }
      catch (IOException paramString)
      {
        HttpURLConnection localHttpURLConnection;
        localObject3 = localObject2;
        MRAIDLog.i("MRAIDView", "getStringFromUrl failed " + paramString.getLocalizedMessage());
        localObject3 = localObject1;
        if (localObject2 != null)
        {
          try
          {
            ((InputStream)localObject2).close();
            return (String)localObject1;
          }
          catch (IOException paramString)
          {
            return (String)localObject1;
          }
          localObject1 = localObject4;
          localObject2 = localInputStream;
          localObject3 = localInputStream;
          paramString = localStringBuilder.toString();
          localObject1 = paramString;
          localObject2 = localInputStream;
          localObject3 = localInputStream;
          MRAIDLog.v("MRAIDView", "getStringFromUrl ok, length=" + paramString.length());
          localObject1 = paramString;
          localObject2 = localInputStream;
          localObject3 = localInputStream;
          localHttpURLConnection.disconnect();
          localObject3 = paramString;
          if (localInputStream != null) {
            try
            {
              localInputStream.close();
              return paramString;
            }
            catch (IOException localIOException1)
            {
              return paramString;
            }
          }
        }
      }
      finally
      {
        if (localObject3 == null) {}
      }
    }
    try
    {
      ((InputStream)localObject3).close();
      throw paramString;
    }
    catch (IOException localIOException2)
    {
      for (;;) {}
    }
  }
  
  private static String getVisibilityString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "UNKNOWN";
    case 8: 
      return "GONE";
    case 4: 
      return "INVISIBLE";
    }
    return "VISIBLE";
  }
  
  private void injectMraidJs(WebView paramWebView)
  {
    MRAIDLog.v("MRAIDView", "injectMraidJs ok " + this.mraidJs.length());
    paramWebView.loadUrl("javascript:" + this.mraidJs);
  }
  
  private void onLayoutWebView(WebView paramWebView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool;
    if (paramWebView == this.currentWebView)
    {
      bool = true;
      StringBuilder localStringBuilder = new StringBuilder().append("onLayoutWebView ");
      if (paramWebView != this.webView) {
        break label143;
      }
      paramWebView = "1 ";
      label38:
      MRAIDLog.v("MRAIDView", paramWebView + bool + " (" + this.state + ") " + paramBoolean + " " + paramInt1 + " " + paramInt2 + " " + paramInt3 + " " + paramInt4);
      if (bool) {
        break label150;
      }
      MRAIDLog.v("MRAIDView", "onLayoutWebView ignored, not current");
    }
    label143:
    label150:
    do
    {
      do
      {
        return;
        bool = false;
        break;
        paramWebView = "2 ";
        break label38;
        if (this.isForcingFullScreen)
        {
          MRAIDLog.v("MRAIDView", "onLayoutWebView ignored, isForcingFullScreen");
          this.isForcingFullScreen = false;
          return;
        }
        if ((this.state == 0) || (this.state == 1))
        {
          calculateScreenSize();
          calculateMaxSize();
        }
        if (!this.isClosing)
        {
          calculatePosition(true);
          if ((this.isInterstitial) && (!this.defaultPosition.equals(this.currentPosition)))
          {
            this.defaultPosition = new Rect(this.currentPosition);
            setDefaultPosition();
          }
        }
      } while (!this.isExpandingFromDefault);
      this.isExpandingFromDefault = false;
      if (this.isInterstitial)
      {
        this.state = 1;
        this.isLaidOut = true;
      }
      if (!this.isExpandingPart2)
      {
        MRAIDLog.v("MRAIDView", "calling fireStateChangeEvent 1");
        fireStateChangeEvent();
      }
      if (this.isInterstitial)
      {
        fireReadyEvent();
        if (this.isViewable) {
          fireViewableChangeEvent();
        }
      }
    } while (this.listener == null);
    this.listener.mraidViewExpand(this);
  }
  
  private void open(String paramString)
  {
    try
    {
      paramString = URLDecoder.decode(paramString, "UTF-8");
      MRAIDLog.v("MRAIDView-JS callback", "open " + paramString);
      if (this.nativeFeatureListener == null) {
        return;
      }
      if (paramString.startsWith("sms"))
      {
        this.nativeFeatureListener.mraidNativeFeatureSendSms(paramString);
        return;
      }
      if (paramString.startsWith("tel"))
      {
        this.nativeFeatureListener.mraidNativeFeatureCallTel(paramString);
        return;
      }
    }
    catch (UnsupportedEncodingException paramString)
    {
      paramString.printStackTrace();
      return;
    }
    this.nativeFeatureListener.mraidNativeFeatureOpenBrowser(paramString);
  }
  
  private void parseCommandUrl(String paramString)
  {
    MRAIDLog.v("MRAIDView", "parseCommandUrl " + paramString);
    Map localMap = new MRAIDParser().parseCommandUrl(paramString);
    paramString = (String)localMap.get("command");
    for (;;)
    {
      try
      {
        if (Arrays.asList(new String[] { "close", "resize", "success", "rewardComplete", "replay" }).contains(paramString))
        {
          getClass().getDeclaredMethod(paramString, new Class[0]).invoke(this, new Object[0]);
          return;
        }
        if (!Arrays.asList(new String[] { "createCalendarEvent", "expand", "open", "playVideo", "storePicture", "useCustomClose" }).contains(paramString)) {
          break label251;
        }
        Method localMethod = getClass().getDeclaredMethod(paramString, new Class[] { String.class });
        if (paramString.equals("createCalendarEvent"))
        {
          paramString = "eventJSON";
          localMethod.invoke(this, new Object[] { (String)localMap.get(paramString) });
          return;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        return;
      }
      if (paramString.equals("useCustomClose"))
      {
        paramString = "useCustomClose";
        continue;
        label251:
        if (Arrays.asList(new String[] { "setOrientationProperties", "setResizeProperties" }).contains(paramString)) {
          getClass().getDeclaredMethod(paramString, new Class[] { Map.class }).invoke(this, new Object[] { localMap });
        }
      }
      else
      {
        paramString = "url";
      }
    }
  }
  
  private void pauseWebView(WebView paramWebView)
  {
    MRAIDLog.v("MRAIDView", "pauseWebView " + paramWebView.toString());
    if (Build.VERSION.SDK_INT >= 11)
    {
      paramWebView.onPause();
      return;
    }
    paramWebView.loadUrl("about:blank");
  }
  
  private void playVideo(String paramString)
  {
    try
    {
      paramString = URLDecoder.decode(paramString, "UTF-8");
      MRAIDLog.v("MRAIDView-JS callback", "playVideo " + paramString);
      if (this.nativeFeatureListener != null) {
        this.nativeFeatureListener.mraidNativeFeaturePlayVideo(paramString);
      }
      return;
    }
    catch (UnsupportedEncodingException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  private int px2dip(int paramInt)
  {
    return paramInt * 160 / this.displayMetrics.densityDpi;
  }
  
  private void removeCloseRegion(View paramView)
  {
    ((ViewGroup)paramView).removeView(this.closeRegion);
  }
  
  private void removeResizeView()
  {
    this.resizedView.removeAllViews();
    ((FrameLayout)((Activity)this.context).findViewById(16908290)).removeView(this.resizedView);
    this.resizedView = null;
    this.closeRegion = null;
  }
  
  private void replay()
  {
    close(false);
    this.listener.mraidReplayVideoPressed(this);
  }
  
  private void resize()
  {
    MRAIDLog.v("MRAIDView-JS callback", "resize");
    if (this.listener == null) {}
    while (!this.listener.mraidViewResize(this, this.resizeProperties.width, this.resizeProperties.height, this.resizeProperties.offsetX, this.resizeProperties.offsetY)) {
      return;
    }
    this.state = 3;
    if (this.resizedView == null)
    {
      this.resizedView = new RelativeLayout(this.context);
      removeAllViews();
      this.resizedView.addView(this.webView);
      if (!this.useCustomClose) {
        addCloseRegion(this.resizedView);
      }
      ((FrameLayout)getRootView().findViewById(16908290)).addView(this.resizedView);
    }
    if (!this.useCustomClose) {
      setCloseRegionPosition(this.resizedView);
    }
    setResizedViewSize();
    setResizedViewPosition();
    this.handler.post(new Runnable()
    {
      public void run()
      {
        MRAIDView.this.fireStateChangeEvent();
      }
    });
  }
  
  private void restoreOriginalOrientation()
  {
    MRAIDLog.d("MRAIDView", "restoreOriginalOrientation");
    Activity localActivity = (Activity)this.context;
    if (localActivity.getRequestedOrientation() != this.originalRequestedOrientation) {
      localActivity.setRequestedOrientation(this.originalRequestedOrientation);
    }
  }
  
  private void restoreOriginalScreenState()
  {
    Activity localActivity = (Activity)this.context;
    if (this.isActionBarShowing) {
      localActivity.getActionBar().show();
    }
    while (this.titleBar == null) {
      return;
    }
    this.titleBar.setVisibility(this.origTitleBarVisibility);
  }
  
  private void rewardComplete()
  {
    MRAIDLog.v("MRAIDView-JS callback", "rewardComplete " + this.state);
    this.handler.post(new Runnable()
    {
      public void run()
      {
        if (MRAIDView.this.nativeFeatureListener != null) {
          MRAIDView.this.nativeFeatureListener.mraidRewardComplete();
        }
      }
    });
  }
  
  private void setCloseRegionPosition(View paramView)
  {
    int i = (int)TypedValue.applyDimension(1, 50.0F, this.displayMetrics);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(i, i);
    if (paramView == this.expandedView)
    {
      localLayoutParams.addRule(10);
      localLayoutParams.addRule(11);
    }
    for (;;)
    {
      this.closeRegion.setLayoutParams(localLayoutParams);
      return;
      if (paramView == this.resizedView)
      {
        switch (this.resizeProperties.customClosePosition)
        {
        }
        for (;;)
        {
          switch (this.resizeProperties.customClosePosition)
          {
          default: 
            break;
          case 0: 
          case 1: 
          case 2: 
            localLayoutParams.addRule(10);
            break;
            localLayoutParams.addRule(9);
            continue;
            localLayoutParams.addRule(14);
            continue;
            localLayoutParams.addRule(11);
          }
        }
        localLayoutParams.addRule(15);
        continue;
        localLayoutParams.addRule(12);
      }
    }
  }
  
  private void setCurrentPosition()
  {
    int i = this.currentPosition.left;
    int j = this.currentPosition.top;
    int k = this.currentPosition.width();
    int m = this.currentPosition.height();
    MRAIDLog.v("MRAIDView", "setCurrentPosition [" + i + "," + j + "] (" + k + "x" + m + ")");
    injectJavaScript("mraid.setCurrentPosition(" + px2dip(i) + "," + px2dip(j) + "," + px2dip(k) + "," + px2dip(m) + ");");
  }
  
  private void setDefaultPosition()
  {
    int i = this.defaultPosition.left;
    int j = this.defaultPosition.top;
    int k = this.defaultPosition.width();
    int m = this.defaultPosition.height();
    MRAIDLog.v("MRAIDView", "setDefaultPosition [" + i + "," + j + "] (" + k + "x" + m + ")");
    injectJavaScript("mraid.setDefaultPosition(" + px2dip(i) + "," + px2dip(j) + "," + px2dip(k) + "," + px2dip(m) + ");");
  }
  
  private void setMaxSize()
  {
    MRAIDLog.v("MRAIDView", "setMaxSize");
    int i = this.maxSize.width;
    int j = this.maxSize.height;
    MRAIDLog.v("MRAIDView", "setMaxSize " + i + "x" + j);
    injectJavaScript("mraid.setMaxSize(" + px2dip(i) + "," + px2dip(j) + ");");
  }
  
  private void setOrientationProperties(Map<String, String> paramMap)
  {
    boolean bool = Boolean.parseBoolean((String)paramMap.get("allowOrientationChange"));
    paramMap = (String)paramMap.get("forceOrientation");
    MRAIDLog.v("MRAIDView-JS callback", "setOrientationProperties " + bool + " " + paramMap);
    if ((this.orientationProperties.allowOrientationChange != bool) || (this.orientationProperties.forceOrientation != MRAIDOrientationProperties.forceOrientationFromString(paramMap)))
    {
      this.orientationProperties.allowOrientationChange = bool;
      this.orientationProperties.forceOrientation = MRAIDOrientationProperties.forceOrientationFromString(paramMap);
      if ((this.isInterstitial) || (this.state == 2)) {
        applyOrientationProperties();
      }
    }
  }
  
  private void setResizeProperties(Map<String, String> paramMap)
  {
    int i = Integer.parseInt((String)paramMap.get("width"));
    int j = Integer.parseInt((String)paramMap.get("height"));
    int k = Integer.parseInt((String)paramMap.get("offsetX"));
    int m = Integer.parseInt((String)paramMap.get("offsetY"));
    String str = (String)paramMap.get("customClosePosition");
    boolean bool = Boolean.parseBoolean((String)paramMap.get("allowOffscreen"));
    MRAIDLog.v("MRAIDView-JS callback", "setResizeProperties " + i + " " + j + " " + k + " " + m + " " + str + " " + bool);
    this.resizeProperties.width = i;
    this.resizeProperties.height = j;
    this.resizeProperties.offsetX = k;
    this.resizeProperties.offsetY = m;
    this.resizeProperties.customClosePosition = MRAIDResizeProperties.customClosePositionFromString(str);
    this.resizeProperties.allowOffscreen = bool;
  }
  
  private void setResizedViewPosition()
  {
    MRAIDLog.v("MRAIDView", "setResizedViewPosition");
    if (this.resizedView == null) {}
    int i;
    int j;
    int m;
    int k;
    do
    {
      return;
      i = this.resizeProperties.width;
      j = this.resizeProperties.height;
      m = this.resizeProperties.offsetX;
      k = this.resizeProperties.offsetY;
      i = (int)TypedValue.applyDimension(1, i, this.displayMetrics);
      j = (int)TypedValue.applyDimension(1, j, this.displayMetrics);
      m = (int)TypedValue.applyDimension(1, m, this.displayMetrics);
      k = (int)TypedValue.applyDimension(1, k, this.displayMetrics);
      m = this.defaultPosition.left + m;
      k = this.defaultPosition.top + k;
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.resizedView.getLayoutParams();
      localLayoutParams.leftMargin = m;
      localLayoutParams.topMargin = k;
      this.resizedView.setLayoutParams(localLayoutParams);
    } while ((m == this.currentPosition.left) && (k == this.currentPosition.top) && (i == this.currentPosition.width()) && (j == this.currentPosition.height()));
    this.currentPosition.left = m;
    this.currentPosition.top = k;
    this.currentPosition.right = (m + i);
    this.currentPosition.bottom = (k + j);
    setCurrentPosition();
  }
  
  private void setResizedViewSize()
  {
    MRAIDLog.v("MRAIDView", "setResizedViewSize");
    int i = this.resizeProperties.width;
    int j = this.resizeProperties.height;
    Log.d("MRAIDView", "setResizedViewSize " + i + "x" + j);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams((int)TypedValue.applyDimension(1, i, this.displayMetrics), (int)TypedValue.applyDimension(1, j, this.displayMetrics));
    this.resizedView.setLayoutParams(localLayoutParams);
  }
  
  private void setScreenSize()
  {
    MRAIDLog.v("MRAIDView", "setScreenSize");
    int i = this.screenSize.width;
    int j = this.screenSize.height;
    MRAIDLog.v("MRAIDView", "setScreenSize " + i + "x" + j);
    injectJavaScript("mraid.setScreenSize(" + px2dip(i) + "," + px2dip(j) + ");");
  }
  
  private void setSupportedServices()
  {
    MRAIDLog.v("MRAIDView", "setSupportedServices");
    injectJavaScript("mraid.setSupports(mraid.SUPPORTED_FEATURES.CALENDAR, " + this.nativeFeatureManager.isCalendarSupported() + ");");
    injectJavaScript("mraid.setSupports(mraid.SUPPORTED_FEATURES.INLINEVIDEO, " + this.nativeFeatureManager.isInlineVideoSupported() + ");");
    injectJavaScript("mraid.setSupports(mraid.SUPPORTED_FEATURES.SMS, " + this.nativeFeatureManager.isSmsSupported() + ");");
    injectJavaScript("mraid.setSupports(mraid.SUPPORTED_FEATURES.STOREPICTURE, " + this.nativeFeatureManager.isStorePictureSupported() + ");");
    injectJavaScript("mraid.setSupports(mraid.SUPPORTED_FEATURES.TEL, " + this.nativeFeatureManager.isTelSupported() + ");");
  }
  
  private void setViewable(int paramInt)
  {
    if (paramInt == 0) {}
    for (boolean bool = true;; bool = false)
    {
      if (bool != this.isViewable)
      {
        this.isViewable = bool;
        if ((this.isPageFinished) && (this.isLaidOut)) {
          fireViewableChangeEvent();
        }
      }
      return;
    }
  }
  
  private void storePicture(String paramString)
  {
    try
    {
      paramString = URLDecoder.decode(paramString, "UTF-8");
      MRAIDLog.v("MRAIDView-JS callback", "storePicture " + paramString);
      if (this.nativeFeatureListener != null) {
        this.nativeFeatureListener.mraidNativeFeatureStorePicture(paramString);
      }
      return;
    }
    catch (UnsupportedEncodingException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  private void success()
  {
    this.listener.mraidViewAcceptPressed(this);
    close();
  }
  
  private void useCustomClose(String paramString)
  {
    MRAIDLog.v("MRAIDView-JS callback", "useCustomClose " + paramString);
    this.useCustomClose = Boolean.parseBoolean(paramString);
    if (this.useCustomClose)
    {
      if (this.expandedView == null) {
        break label57;
      }
      removeCloseRegion(this.expandedView);
    }
    label57:
    while (this.resizedView == null) {
      return;
    }
    removeCloseRegion(this.resizedView);
  }
  
  public void injectJavaScript(String paramString)
  {
    if (!TextUtils.isEmpty(paramString)) {
      this.currentWebView.loadUrl("javascript:" + paramString);
    }
  }
  
  protected void onAttachedToWindow()
  {
    MRAIDLog.v("MRAIDView", "onAttachedToWindow");
    super.onAttachedToWindow();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    StringBuilder localStringBuilder = new StringBuilder().append("onConfigurationChanged ");
    if (paramConfiguration.orientation == 1) {}
    for (paramConfiguration = "portrait";; paramConfiguration = "landscape")
    {
      MRAIDLog.v("MRAIDView", paramConfiguration);
      ((Activity)this.context).getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
      return;
    }
  }
  
  protected void onDetachedFromWindow()
  {
    MRAIDLog.v("MRAIDView", "onDetachedFromWindow");
    super.onDetachedFromWindow();
  }
  
  @SuppressLint({"DrawAllocation"})
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    MRAIDLog.v("MRAIDView", "onLayout (" + this.state + ") " + paramBoolean + " " + paramInt1 + " " + paramInt2 + " " + paramInt3 + " " + paramInt4);
    if (this.isForcingFullScreen)
    {
      MRAIDLog.v("MRAIDView", "onLayout ignored");
      return;
    }
    if ((this.state == 2) || (this.state == 3))
    {
      calculateScreenSize();
      calculateMaxSize();
    }
    if (this.isClosing)
    {
      this.isClosing = false;
      this.currentPosition = new Rect(this.defaultPosition);
      setCurrentPosition();
    }
    for (;;)
    {
      if ((this.state == 3) && (paramBoolean)) {
        this.handler.post(new Runnable()
        {
          public void run()
          {
            MRAIDView.this.setResizedViewPosition();
          }
        });
      }
      this.isLaidOut = true;
      if ((this.state != 0) || (!this.isPageFinished)) {
        break;
      }
      this.state = 1;
      fireStateChangeEvent();
      fireReadyEvent();
      if (!this.isViewable) {
        break;
      }
      fireViewableChangeEvent();
      return;
      calculatePosition(false);
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.gestureDetector.onTouchEvent(paramMotionEvent)) {
      paramMotionEvent.setAction(3);
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    super.onVisibilityChanged(paramView, paramInt);
    MRAIDLog.v("MRAIDView", "onVisibilityChanged " + getVisibilityString(paramInt));
    setViewable(paramInt);
  }
  
  protected void onWindowVisibilityChanged(int paramInt)
  {
    super.onWindowVisibilityChanged(paramInt);
    int i = getVisibility();
    MRAIDLog.v("MRAIDView", "onWindowVisibilityChanged " + getVisibilityString(paramInt) + " (actual " + getVisibilityString(i) + ")");
    setViewable(i);
  }
  
  public void setOrientationConfig(int paramInt)
  {
    this.rotateMode = paramInt;
  }
  
  protected void showAsInterstitial()
  {
    expand(null);
  }
  
  public void updateContext(Context paramContext)
  {
    this.context = paramContext;
  }
  
  private class MRAIDWebChromeClient
    extends WebChromeClient
  {
    private MRAIDWebChromeClient() {}
    
    private boolean handlePopups(JsResult paramJsResult)
    {
      paramJsResult.cancel();
      return true;
    }
    
    public boolean onConsoleMessage(ConsoleMessage paramConsoleMessage)
    {
      StringBuilder localStringBuilder;
      if (!paramConsoleMessage.message().contains("Uncaught ReferenceError"))
      {
        localStringBuilder = new StringBuilder().append(paramConsoleMessage.message());
        if (paramConsoleMessage.sourceId() != null) {
          break label64;
        }
      }
      label64:
      for (String str = "";; str = " at " + paramConsoleMessage.sourceId())
      {
        MRAIDLog.i("JS console", str + ":" + paramConsoleMessage.lineNumber());
        return true;
      }
    }
    
    public boolean onJsAlert(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
    {
      MRAIDLog.v("JS alert", paramString2);
      return handlePopups(paramJsResult);
    }
    
    public boolean onJsConfirm(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
    {
      MRAIDLog.v("JS confirm", paramString2);
      return handlePopups(paramJsResult);
    }
    
    public boolean onJsPrompt(WebView paramWebView, String paramString1, String paramString2, String paramString3, JsPromptResult paramJsPromptResult)
    {
      MRAIDLog.v("JS prompt", paramString2);
      return handlePopups(paramJsPromptResult);
    }
  }
  
  private class MRAIDWebViewClient
    extends WebViewClient
  {
    private MRAIDWebViewClient() {}
    
    public void onPageFinished(WebView paramWebView, String paramString)
    {
      MRAIDLog.v("MRAIDView", "onPageFinished: " + paramString);
      super.onPageFinished(paramWebView, paramString);
      StringBuilder localStringBuilder;
      if (MRAIDView.this.state == 0)
      {
        MRAIDView.access$2702(MRAIDView.this, true);
        paramString = MRAIDView.this;
        localStringBuilder = new StringBuilder().append("mraid.setPlacementType('");
        if (!MRAIDView.this.isInterstitial) {
          break label247;
        }
      }
      label247:
      for (paramWebView = "interstitial";; paramWebView = "inline")
      {
        paramString.injectJavaScript(paramWebView + "');");
        MRAIDView.this.setSupportedServices();
        if (MRAIDView.this.isLaidOut)
        {
          MRAIDView.this.setScreenSize();
          MRAIDView.this.setMaxSize();
          MRAIDView.this.setCurrentPosition();
          MRAIDView.this.setDefaultPosition();
          MRAIDView.access$902(MRAIDView.this, 1);
          MRAIDView.this.fireStateChangeEvent();
          MRAIDView.this.fireReadyEvent();
          if (MRAIDView.this.isViewable) {
            MRAIDView.this.fireViewableChangeEvent();
          }
        }
        if (MRAIDView.this.listener != null) {
          MRAIDView.this.listener.mraidViewLoaded(MRAIDView.this);
        }
        if (MRAIDView.this.isExpandingPart2)
        {
          MRAIDView.access$2102(MRAIDView.this, false);
          MRAIDView.this.handler.post(new Runnable()
          {
            public void run()
            {
              MRAIDView localMRAIDView = MRAIDView.this;
              StringBuilder localStringBuilder = new StringBuilder().append("mraid.setPlacementType('");
              if (MRAIDView.this.isInterstitial) {}
              for (String str = "interstitial";; str = "inline")
              {
                localMRAIDView.injectJavaScript(str + "');");
                MRAIDView.this.setSupportedServices();
                MRAIDView.this.setScreenSize();
                MRAIDView.this.setDefaultPosition();
                MRAIDLog.v("MRAIDView", "calling fireStateChangeEvent 2");
                MRAIDView.this.fireStateChangeEvent();
                MRAIDView.this.fireReadyEvent();
                if (MRAIDView.this.isViewable) {
                  MRAIDView.this.fireViewableChangeEvent();
                }
                return;
              }
            }
          });
        }
        return;
      }
    }
    
    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      MRAIDLog.v("MRAIDView", "onReceivedError: " + paramString1);
      super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
    }
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      MRAIDLog.v("MRAIDView", "shouldOverrideUrlLoading: " + paramString);
      if (paramString.startsWith("mraid://")) {
        MRAIDView.this.parseCommandUrl(paramString);
      }
      do
      {
        do
        {
          do
          {
            return true;
            if (!paramString.startsWith("sms:")) {
              break;
            }
          } while ((MRAIDView.this.nativeFeatureListener == null) || (!MRAIDView.this.nativeFeatureManager.isSmsSupported()));
          MRAIDView.this.nativeFeatureListener.mraidNativeFeatureSendSms(paramString);
          return true;
          if (!paramString.startsWith("tel:")) {
            break;
          }
        } while ((MRAIDView.this.nativeFeatureListener == null) || (!MRAIDView.this.nativeFeatureManager.isTelSupported()));
        MRAIDView.this.nativeFeatureListener.mraidNativeFeatureCallTel(paramString);
        return true;
        if (!paramString.startsWith("market:")) {
          break;
        }
      } while (MRAIDView.this.nativeFeatureListener == null);
      MRAIDView.this.nativeFeatureListener.mraidNativeFeatureOpenMarket(paramString);
      return true;
      return super.shouldOverrideUrlLoading(paramWebView, paramString);
    }
  }
  
  private final class Size
  {
    public int height;
    public int width;
    
    private Size() {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/MRAIDView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */