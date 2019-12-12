package com.example.nathantucker.justgames;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
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

    final ArrayList<String> titles = new ArrayList<>();

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
                Intent i = new Intent(getApplicationContext(), ListActivity.class);
                startActivityForResult(i,0);
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideOverlay();
                CharSequence text = "Game saved. Here's a new one.";
                Toast toast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
                toast.show();
                saveGame();
                loadNextGame();
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideOverlay();
                CharSequence text = "Game not saved. Here's a new one.";
                Toast toast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
                toast.show();
                loadNextGame();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            hideOverlay();
            if(resultCode == RESULT_OK){
                //Game selected, need to refresh
                loadGame();
            }else{
                loadNextGame();
            }
        }
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
            //startTrackingFragment(f, position);
            return f;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
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
        if(!Games.getInstance().isRevisit()){
            likeButton.setVisibility(View.VISIBLE);
            dislikeButton.setVisibility(View.VISIBLE);
        }else{
            likeButton.setVisibility(View.GONE);
            dislikeButton.setVisibility(View.GONE);
        }
    }

    public void hideOverlay(){
        sliderDotsPanel.setVisibility(View.GONE);
        menuButton.setVisibility(View.GONE);
        appNameText.setVisibility(View.GONE);
        likeButton.setVisibility(View.GONE);
        dislikeButton.setVisibility(View.GONE);
    }

    private void loadNextGame(){
        Games.getInstance().changeCurrentGame();
        loadGame();
    }

    private void loadGame(){
        for(int i = 0; i < NUM_PAGES; i++){
            pagerAdapter.notifyDataSetChanged();
        }
        showOverlay();
    }

    private void saveGame(){
        Games.getInstance().saveCurrentGame();
    }

}
