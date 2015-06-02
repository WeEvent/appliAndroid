package nf28.weevent.Controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Vector;

/**
 * Created by KM on 13/05/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int Numboftabs =5;

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    static private Vector<Integer> tab_names = new Vector<Integer>();
    private static CharSequence Tiles_all[]={"Description","Event","Date","Place","Transport","Overview"};

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    public static void addTab(int id){
        System.err.println("-"+id);
        tab_names.add(id);
    }

    public static int getSizeTab(){
        return tab_names.size();
    }

    public static CharSequence[] getTiles(){
        CharSequence Titl[] = new CharSequence[tab_names.size()];
        int i = 0;
        for(Integer idx : tab_names){
            Titl[i++] = Tiles_all[idx];
        }
        return Titl;
    }

    public static void resetTabs(){
        tab_names.clear();
    }

    public static int getTabName(int idx){
        if(idx >=0 && idx <tab_names.size())
            return tab_names.get(idx);
        return tab_names.get(0);
    }
    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(ViewPagerAdapter.getTabName(position) == 0) // if the position is 0 we are returning the First tab
        {
            Description description = new Description();
            return description;
        }
        else  if(ViewPagerAdapter.getTabName(position) == 1)      // second tab corresponds to the second category and so on ...
        {
            EventActivity event = new EventActivity();
            return event;
        }else  if(ViewPagerAdapter.getTabName(position) == 2)      // 3 tab corresponds to Date
        {
            Date date = new Date();
            return date;
        } else  if(ViewPagerAdapter.getTabName(position) == 3)      // 4 tab corresponds to Place
        {
            Place place = new Place();
            return place;
        } else if(ViewPagerAdapter.getTabName(position) == 4)      // 5 tab corresponds to Transport
        {
            Transport transport = new Transport();
            return transport;
        }else if(ViewPagerAdapter.getTabName(position) == 5 )       //  6 overview
        {
            Overview overview = new Overview();
            return overview;
        }else{
            return null;
        }
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}