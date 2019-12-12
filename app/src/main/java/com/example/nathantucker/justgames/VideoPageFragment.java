package com.example.nathantucker.justgames;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class VideoPageFragment extends Fragment {

    private int position;
    private int containerId = 0;
    private int layoutFileId = 0;
    private String videoLink;
    private YouTubePlayerSupportFragmentX youTubePlayerFragment;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private ImageView playButton;
    private String videoType;
    private TextView videoTypeText;

    public static VideoPageFragment newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt("index", index);

        VideoPageFragment fragment = new VideoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        int position = args.getInt("index", 0);
        if(position == 1){
            videoLink = getString(Games.getInstance().getCurrentGame().getGameplayVideoRes());
            videoType = getString(R.string.gameplay_text);
            containerId = R.id.youtube_fragment_container;
            layoutFileId = R.layout.video_slide_page;
        }
        else if(position == 2){
            videoLink = getString(Games.getInstance().getCurrentGame().getStoryVideoRes());
            videoType = getString(R.string.story_text);
            containerId = R.id.youtube_fragment_container_two;
            layoutFileId = R.layout.video_slide_page_two;
        }
        else videoLink = getString(R.string.default_video);

        ViewGroup rootView = (ViewGroup) inflater.inflate(layoutFileId, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playButton = getView().findViewById(R.id.playImageView);
        videoTypeText = getView().findViewById(R.id.videoType);
        videoTypeText.setText(videoType);

        youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(containerId, youTubePlayerFragment);
        transaction.commit();

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoLink);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                playButton.setVisibility(View.VISIBLE);
            }
        };

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerFragment.initialize(PlayerConfig.API_KEY,onInitializedListener);
                //Hide fragment overlay
                playButton.setVisibility(View.GONE);
                videoTypeText.setVisibility(View.GONE);
                //Hide activity overlay
                MainActivity parent = (MainActivity) getActivity();
                if(parent != null) parent.hideOverlay();
            }
        });

    }

    public void reinitializeYoutubePlayer(){
        youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(containerId, youTubePlayerFragment);
        transaction.commit();
        //Reveal fragment overlay
        playButton.setVisibility(View.VISIBLE);
        videoTypeText.setVisibility(View.VISIBLE);
    }
}
