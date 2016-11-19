package com.upsight.android;

public abstract class UpsightExtension<T extends BaseComponent, U>
{
  private T mExtensionComponent;
  
  public U getApi()
    throws IllegalStateException
  {
    throw new IllegalStateException("This Upsight extension supports no public API.");
  }
  
  public final T getComponent()
  {
    return this.mExtensionComponent;
  }
  
  protected void onCreate(UpsightContext paramUpsightContext) {}
  
  protected void onPostCreate(UpsightContext paramUpsightContext) {}
  
  protected abstract T onResolve(UpsightContext paramUpsightContext);
  
  final void setComponent(T paramT)
  {
    this.mExtensionComponent = paramT;
  }
  
  public static abstract interface BaseComponent<T extends UpsightExtension>
  {
    public abstract void inject(T paramT);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */