package com.example.nathantucker.justgames;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    //Fields
    private ImageView backButton;
    private TextView[] listTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity_pop_up);

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

        //View initialization
        backButton = findViewById(R.id.backButton);
        listTextViews = new TextView[]{findViewById(R.id.list_one),
                findViewById(R.id.list_two), findViewById(R.id.list_three),
                findViewById(R.id.list_four), findViewById(R.id.list_five), findViewById(R.id.list_six)};

        //Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                onBackPressed();
            }
        });

        //Info Pull
        int currentEndList = 0;
        for(GameProfile game : Games.getInstance().getSavedGames()){
            if( game != null){
                listTextViews[currentEndList].setText(game.getName());
                currentEndList++;
            }
        }

        //Set Up Listeners
        for(int i = 0; i < currentEndList; i++){
            listTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView)v;
                    String string = textView.getText().toString();
                    Games.getInstance().setCurrentGame(getApplicationContext(), string);
                    setResult(RESULT_OK);
                    onBackPressed();
                }
            });
        }
    }
}
