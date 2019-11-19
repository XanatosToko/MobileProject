package com.example.nathantucker.justgames;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class VideoPageFragment extends Fragment {

    private int position;
    private int containerId = 0;
    private int layoutFileId = 0;
    private String videoLink;
    private YouTubePlayerSupportFragmentX youTubePlayerFragment;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private ImageView playButton;

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
            videoLink = getString(R.string.jedi_gameplay_video);
            containerId = R.id.youtube_fragment_container;
            layoutFileId = R.layout.video_slide_page;
        }
        else if(position == 2){
            videoLink = getString(R.string.jedi_story_video);
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

        youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(containerId, youTubePlayerFragment);
        transaction.commit();

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoLink); //TODO: Dynamically grab the different videos
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
                playButton.setVisibility(View.GONE);
            }
        });

    }

    public void reinitializeYoutubePlayer(){
        youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(containerId, youTubePlayerFragment);
        transaction.commit();
        playButton.setVisibility(View.VISIBLE);
    }
}
