package com.example.nathantucker.justgames;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    //Overlay
    private ImageView menuButton;
    private TextView appNameText;
    private ImageView likeButton;
    private ImageView dislikeButton;
    private LinearLayout sliderDotsPanel;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_main);

        //Pager initialization
        mFragments = new ArrayList<Fragment>();

        mPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(new OnScreenChangeListener());

        //Buttons overlay initialization
        menuButton = findViewById(R.id.menu_icon);
        appNameText = findViewById(R.id.app_name);
        likeButton = findViewById(R.id.like_icon);
        dislikeButton = findViewById(R.id.dislike_icon);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:Open list of saved games
                CharSequence text = "Here are your saved games.";
                Toast toast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:Save to list and continue to another game
                CharSequence text = "Game saved. Here's a new one.";
                Toast toast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
                toast.show();
                loadNextGame();
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:Continue to another game
                CharSequence text = "Game not saved. Here's a new one.";
                Toast toast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //Slider dots initialization
        sliderDotsPanel = findViewById(R.id.sliderDots);
        dots = new ImageView[NUM_PAGES];

        for(int i = 0; i < NUM_PAGES; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,10,8,10);

            sliderDotsPanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

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
                    //Reveal activity overlay
                    showOverlay();
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            for(int i = 0; i < NUM_PAGES; i++){
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactive_dot));
            }
            dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
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

    private void showOverlay(){
        sliderDotsPanel.setVisibility(View.VISIBLE);
        menuButton.setVisibility(View.VISIBLE);
        appNameText.setVisibility(View.VISIBLE);
        likeButton.setVisibility(View.VISIBLE);
        dislikeButton.setVisibility(View.VISIBLE);
    }

    private void loadNextGame(){
        //TODO: Dynamically grab the different videos

        //TODO: Dynamically grab the different images
    }

}
