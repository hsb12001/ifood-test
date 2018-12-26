package mx.shosvb.ifood.adapters;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mx.shosvb.ifood.R;
import mx.shosvb.ifood.fragments.SearchFragment;
import mx.shosvb.ifood.fragments.TimelineFragment;

public class FoodPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_SEARCH = 0;
    private static final int PAGE_USER = 1;


    private static final int[] PAGE_TITLE_RES_IDS = {
            R.string.search,
            R.string.time_line
    };
    private Resources resources;

    public FoodPagerAdapter(FragmentManager fm, Resources resources) {
        super(fm);
        this.resources = resources;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_SEARCH:
                return SearchFragment.newInstance();
            case PAGE_USER:
                return TimelineFragment.newInstance();
            default:
                throw new IllegalStateException("Unexpected Fragment page item requested.");
        }
    }

    @Override
    public int getCount() {
        return PAGE_TITLE_RES_IDS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PAGE_SEARCH:
                return resources.getString(PAGE_TITLE_RES_IDS[PAGE_SEARCH]);
            case PAGE_USER:
                return resources.getString(PAGE_TITLE_RES_IDS[PAGE_USER]);

            default:
                throw new IllegalStateException("Unexpected Fragment page title requested.");
        }
    }
}