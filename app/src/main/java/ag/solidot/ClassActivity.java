package ag.solidot;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import ag.solidot.R;

import ag.solidot.AdapterH.PagerAdapter;
import ag.solidot.TabpageFragment.*;


public class ClassActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new Startup_fragment());
        fragments.add(new Linux_fragment());
        fragments.add(new Science_fragment());
        fragments.add(new Technology_fragment());
        fragments.add(new Mobile_fragment());
        fragments.add(new Apple_fragment());
        fragments.add(new Hardware_fragment());
        fragments.add(new Software_fragment());
        fragments.add(new Security_fragment());
        fragments.add(new Games_fragment());
        fragments.add(new Books_fragment());
        fragments.add(new Ask_fragment());
        fragments.add(new Idle_fragment());
        fragments.add(new Blog_fragment());
        fragments.add(new Cloud_fragment());



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pa = new PagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(pa);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);



    }

}
