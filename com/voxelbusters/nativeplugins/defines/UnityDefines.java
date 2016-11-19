package com.voxelbusters.nativeplugins.defines;

public class UnityDefines
{
  public static String NATIVE_BINDING_EVENT_LISTENER = "NPBinding";
  
  public class AddressBook
  {
    public static final String ADD_CONTACTS_FINISHED = "ABAddNewContactFinished";
    public static final String READ_CONTACTS_FAILED = "ABReadContactsFailed";
    public static final String READ_CONTACTS_FINISED = "ABReadContactsFinished";
    
    public AddressBook() {}
  }
  
  public class Billing
  {
    public static final String ON_SETUP_FINISHED = "";
    public static final String REQUEST_PRODUCTS_FINISHED = "DidReceiveBillingProducts";
    public static final String TRANSACTION_FINISHED = "DidFinishBillingTransaction";
    
    public Billing() {}
  }
  
  public class GameServices
  {
    public static final String AUTHENTICATION_FINISHED = "AuthenticationFinished";
    public static final String RECEIVED_ACHIEVEMENTS = "LoadAchievementsFinished";
    public static final String RECEIVED_ACHIEVEMENTS_UI_CLOSED = "ShowAchievementViewFinished";
    public static final String RECEIVED_ACHIEVEMENT_DESCRIPTIONS = "LoadAchievementDescriptionsFinished";
    public static final String RECEIVED_LEADERBOARDS_UI_CLOSED = "ShowLeaderboardViewFinished";
    public static final String RECEIVED_LOCAL_USER_FRIENDS_REQUEST = "LoadFriendsFinished";
    public static final String RECEIVED_PICTURE_LOAD_REQUEST = "RequestForUserImageFinished";
    public static final String RECEIVED_REPORT_PROGRESS = "ReportProgressFinished";
    public static final String RECEIVED_REPORT_SCORE = "ReportScoreFinished";
    public static final String RECEIVED_SCORES = "LoadScoresFinished";
    public static final String RECEIVED_SIGN_OUT_STATUS = "SignOutFinished";
    public static final String RECEIVED_USER_PROFILES_LIST = "LoadUsersFinished";
    public static final String USER_DISCONNECTED = "UserDisconnected";
    
    public GameServices() {}
  }
  
  public class MediaLibrary
  {
    public static final String PICK_IMAGE_FINISHED = "PickImageFinished";
    public static final String PLAY_VIDEO_FINISHED = "PlayVideoFinished";
    public static final String PLAY_VIDEO_FROM_GALLERY_FINISHED = "PickVideoFinished";
    public static final String SAVE_IMAGE_TO_GALLERY_FINISHED = "SaveImageToGalleryFinished";
    
    public MediaLibrary() {}
  }
  
  public class Notification
  {
    public static final String DID_FAIL_TO_REGISTER_FOR_REMOTE_NOTIFICATION = "DidFailToRegisterRemoteNotifications";
    public static final String DID_RECEIVE_LAUNCH_NOTIFICATION = "DidReceiveAppLaunchInfo";
    public static final String DID_RECEIVE_LOCAL_NOTIFICATION = "DidReceiveLocalNotification";
    public static final String DID_RECEIVE_REMOTE_NOTIFICATION = "DidReceiveRemoteNotification";
    public static final String DID_REGISTER_FOR_REMOTE_NOTIFICATION = "DidRegisterRemoteNotification";
    
    public Notification() {}
  }
  
  public class Reachability
  {
    public static final String NETWORK_CONNECTIVITY_HARDWARE_STATUS_CHANGE = "NetworkHardwareStatusChange";
    public static final String NETWORK_CONNECTIVITY_SOCKET_STATUS_CHANGE = "NetworkSocketStatusChange";
    
    public Reachability() {}
  }
  
  public class Sharing
  {
    public static final String FINISHED = "SharingFinished";
    public static final String SENT_MAIL = "MailShareFinished";
    public static final String SENT_SMS = "MessagingShareFinished";
    public static final String WHATSAPP_SHARE_FINISHED = "WhatsAppShareFinished";
    
    public Sharing() {}
  }
  
  public class Twitter
  {
    public static final String COMPOSER_DISMISSED = "TweetComposerDismissed";
    public static final String LOGIN_FAILED = "TwitterLoginFailed";
    public static final String LOGIN_SUCCESS = "TwitterLoginSuccess";
    public static final String REQUEST_ACCOUNT_DETAILS_FAILED = "RequestAccountDetailsFailed";
    public static final String REQUEST_ACCOUNT_DETAILS_SUCCESS = "RequestAccountDetailsSuccess";
    public static final String REQUEST_EMAIL_ACCESS_FAILED = "RequestEmailAccessFailed";
    public static final String REQUEST_EMAIL_ACCESS_SUCCESS = "RequestEmailAccessSuccess";
    public static final String URL_REQUEST_FAILED = "TwitterURLRequestFailed";
    public static final String URL_REQUEST_SUCCESS = "TwitterURLRequestSuccess";
    
    public Twitter() {}
  }
  
  public class Ui
  {
    public static final String ALERT_DIALOG_CLOSED = "AlertDialogClosed";
    public static final String LOGIN_PROMPT_DIALOG_CLOSED = "LoginPromptDialogClosed";
    public static final String SINGLE_FIELD_PROMPT_DIALOG_CLOSED = "SingleFieldPromptDialogClosed";
    
    public Ui() {}
  }
  
  public class WebView
  {
    public static final String WEB_VIEW_DID_DESTROY = "WebViewDidDestroy";
    public static final String WEB_VIEW_DID_FAIL_LOAD_WITH_ERROR = "WebViewDidFailLoadWithError";
    public static final String WEB_VIEW_DID_FINISH_EVALUATING_JS = "WebViewDidFinishEvaluatingJS";
    public static final String WEB_VIEW_DID_FINISH_LOAD = "WebViewDidFinishLoad";
    public static final String WEB_VIEW_DID_HIDE = "WebViewDidHide";
    public static final String WEB_VIEW_DID_RECEIVE_MESSAGE = "WebViewDidReceiveMessage";
    public static final String WEB_VIEW_DID_SHOW = "WebViewDidShow";
    public static final String WEB_VIEW_DID_START_LOAD = "WebViewDidStartLoad";
    
    public WebView() {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/voxelbusters/nativeplugins/defines/UnityDefines.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */