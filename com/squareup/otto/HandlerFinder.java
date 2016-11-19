package com.squareup.otto;

import java.util.Map;
import java.util.Set;

abstract interface HandlerFinder
{
  public static final HandlerFinder ANNOTATED = new HandlerFinder()
  {
    public Map<Class<?>, EventProducer> findAllProducers(Object paramAnonymousObject)
    {
      return AnnotatedHandlerFinder.findAllProducers(paramAnonymousObject);
    }
    
    public Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object paramAnonymousObject)
    {
      return AnnotatedHandlerFinder.findAllSubscribers(paramAnonymousObject);
    }
  };
  
  public abstract Map<Class<?>, EventProducer> findAllProducers(Object paramObject);
  
  public abstract Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object paramObject);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/squareup/otto/HandlerFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */