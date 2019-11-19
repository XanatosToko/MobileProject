package com.example.nathantucker.justgames;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_main);

        mFragments = new ArrayList<Fragment>();

        mPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(new OnScreenChangeListener());
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void startTrackingFragment(Fragment f, int position) {
        mFragments.add(position, f);
    }

    public void stopTrackingFragment(int position) {
        mFragments.remove(position);
    }

    /**
     * A simple pager adapter that represents NUM_PAGES ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f;
            if(position == 0) f = new ImagePageFragment();
            else f = VideoPageFragment.newInstance(position);
            startTrackingFragment(f, position);
            return f;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private Fragment getCurrentItem(int position) {
        return mFragments.get(position);
    }

    private class OnScreenChangeListener extends ViewPager.SimpleOnPageChangeListener {
        private static final float thresholdOffset = 0.5f;
        private static final int thresholdOffsetPixels = 1;
        private boolean scrollStarted, checkDirection;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (checkDirection) {
                if (thresholdOffset > positionOffset && positionOffsetPixels > thresholdOffsetPixels){
                    Log.i("Me", "going right");
                } else {
                    position++; //going left
                    Log.i("Me", "going left");
                }
            }
            checkDirection = false;
            super.onPageSelected(position);
            if(position < NUM_PAGES){
                Fragment f = (Fragment)pagerAdapter.instantiateItem(mPager, position);
                if (f instanceof VideoPageFragment) {
                    VideoPageFragment fVid = (VideoPageFragment)f;
                    fVid.reinitializeYoutubePlayer();
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            if(!scrollStarted && state == ViewPager.SCROLL_STATE_DRAGGING){
                scrollStarted = true;
                checkDirection = true;
            } else {
                scrollStarted = false;
            }
        }
    }

    //TODO: Dynamically grab the different videos
    //TODO: Dynamically grab the different images

}
