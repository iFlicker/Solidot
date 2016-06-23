package ag.solidot.AdapterH;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by flicker on 16/5/31.
 */
public class PagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> mFragments;

    private String tabTitles[] = new String[]{"创业问答","Linux","科学","科技","移动"
                                             ,"苹果","硬件","软件","安全","游戏"
                                             ,"书籍","ask","idle","博客","云计算"};

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
