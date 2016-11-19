package spacemadness.com.lunarconsole.console;

import android.view.ViewGroup;

public abstract class ViewHolderBuilder<T extends ConsoleEntry>
{
  public abstract ConsoleAdapter.ViewHolder<T> createViewHolder(ViewGroup paramViewGroup);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/ViewHolderBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */