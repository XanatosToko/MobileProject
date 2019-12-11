package com.example.nathantucker.justgames;

import android.content.Context;

public class Games {
    private static final Games mInstance = new Games();

    private int NUM_GAMES = 3;
    private int currentIndex = 0;
    private GameProfile[] allGames = {
            new GameProfile(R.string.jedi_name,
                    R.string.jedi_text,
                    R.string.jedi_story_video,
                    R.string.jedi_gameplay_video,
                    R.drawable.star_wars_image,
                    R.string.jedi_playerType,
                    R.string.jedi_genre,
                    R.string.jedi_rating,
                    R.string.jedi_time,
                    R.string.jedi_developer,
                    R.string.jedi_publisher,
                    R.string.jedi_alsoLiked,
                    R.drawable.jedi_review,
                    R.drawable.jedi_user,
                    R.string.jedi_platforms,
                    R.string.jedi_stores),
            new GameProfile(R.string.ds_name,
                    R.string.ds_text,
                    R.string.ds_story_video,
                    R.string.ds_gameplay_video,
                    R.drawable.ds_image,
                    R.string.ds_playerType,
                    R.string.ds_genre,
                    R.string.ds_rating,
                    R.string.ds_time,
                    R.string.ds_developer,
                    R.string.ds_publisher,
                    R.string.ds_alsoLiked,
                    R.drawable.ds_review,
                    R.drawable.ds_user,
                    R.string.ds_platforms,
                    R.string.ds_stores),
            new GameProfile(R.string.warframe_name,
                    R.string.warframe_text,
                    R.string.warframe_story_video,
                    R.string.warframe_gameplay_video,
                    R.drawable.warframe_image,
                    R.string.warframe_playerType,
                    R.string.warframe_genre,
                    R.string.warframe_rating,
                    R.string.warframe_time,
                    R.string.warframe_developer,
                    R.string.warframe_publisher,
                    R.string.warframe_alsoLiked,
                    R.drawable.warframe_review,
                    R.drawable.warframe_user,
                    R.string.warframe_platforms,
                    R.string.warframe_stores)
    };
    private GameProfile[] savedGames = new GameProfile[NUM_GAMES];

    public static Games getInstance() {
        return mInstance;
    }

    private Games() {
    }

    public void changeCurrentGame(){
        int i;
        do{
            i = (int)(NUM_GAMES*Math.random());
        } while(i == currentIndex); //Do until changed
        currentIndex = i;
    }

    public GameProfile getCurrentGame(){
        return allGames[currentIndex];
    }

    public void setCurrentGame(Context context, String name){
        for(int i = 0; i < NUM_GAMES; i++){
            if(name.equals(context.getString(allGames[i].getName()))){
                currentIndex = i;
                return;
            }
        }
    }

    public void saveCurrentGame(){
        savedGames[currentIndex] = allGames[currentIndex];
    }

    public GameProfile[] getSavedGames(){
        return savedGames;
    }
}
