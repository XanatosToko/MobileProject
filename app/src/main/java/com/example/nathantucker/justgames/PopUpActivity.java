package com.example.nathantucker.justgames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class PopUpActivity extends AppCompatActivity {

    //Fields
    private ImageView backButton;
    private TextView titleText;
    private TextView playersText;
    private TextView genreText;
    private TextView ratingText;
    private TextView timeText;
    private TextView developerText;
    private TextView publisherText;
    private TextView alsoLikedText;
    private ImageView criticReview;
    private ImageView userReview;
    private TextView platformsText;
    private TextView storesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.90), (int)(height*.90));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
        params.x = 0;
        params.y = -10;
        getWindow().setAttributes(params);

        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Info initialization
        backButton = findViewById(R.id.backButton);
        titleText = findViewById(R.id.titleTextView);
        playersText = findViewById(R.id.playerView);
        genreText = findViewById(R.id.genreView);
        ratingText = findViewById(R.id.ratingView);
        timeText = findViewById(R.id.timeView);
        developerText = findViewById(R.id.developerView);
        publisherText = findViewById(R.id.publisherView);
        alsoLikedText = findViewById(R.id.alsoLikedView);
        criticReview = findViewById(R.id.reviewScoreImg);
        userReview = findViewById(R.id.userScoreImg);
        platformsText = findViewById(R.id.platformsView);
        storesText = findViewById(R.id.storesView);

        //Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Info Pull
        titleText.setText(Games.getInstance().getCurrentGame().getName());
        playersText.setText(Games.getInstance().getCurrentGame().getPlayerInfo());
        genreText.setText(Games.getInstance().getCurrentGame().getGenreInfo());
        ratingText.setText(Games.getInstance().getCurrentGame().getRatingInfo());
        timeText.setText(Games.getInstance().getCurrentGame().getTimeInfo());
        developerText.setText(Games.getInstance().getCurrentGame().getDevInfo());
        publisherText.setText(Games.getInstance().getCurrentGame().getPublishInfo());
        alsoLikedText.setText(Games.getInstance().getCurrentGame().getAlsoLikeInfo());
        criticReview.setImageResource(Games.getInstance().getCurrentGame().getReviewerInfo());
        userReview.setImageResource(Games.getInstance().getCurrentGame().getUsersInfo());
        platformsText.setText(Games.getInstance().getCurrentGame().getWhereToPlay());
        storesText.setText(Games.getInstance().getCurrentGame().getWhereToGet());
    }
}
