package com.example.nathantucker.justgames;

import android.content.Context;

public class Games {
    private static final Games mInstance = new Games();

    public boolean isRevisit() {
        return isRevisit;
    }

    public void setRevisit(boolean revisit) {
        isRevisit = revisit;
    }

    private boolean isRevisit = false;
    private int NUM_GAMES = 6;
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
                    R.string.warframe_stores),
            new GameProfile(R.string.smash_name,
                    R.string.smash_text,
                    R.string.smash_story_video,
                    R.string.smash_gameplay_video,
                    R.drawable.smash_image,
                    R.string.smash_playerType,
                    R.string.smash_genre,
                    R.string.smash_rating,
                    R.string.smash_time,
                    R.string.smash_developer,
                    R.string.smash_publisher,
                    R.string.smash_alsoLiked,
                    R.drawable.smash_review,
                    R.drawable.smash_user,
                    R.string.smash_platforms,
                    R.string.smash_stores),
            new GameProfile(R.string.goose_name,
                    R.string.goose_text,
                    R.string.goose_story_video,
                    R.string.goose_gameplay_video,
                    R.drawable.goose_image,
                    R.string.goose_playerType,
                    R.string.goose_genre,
                    R.string.goose_rating,
                    R.string.goose_time,
                    R.string.goose_developer,
                    R.string.goose_publisher,
                    R.string.goose_alsoLiked,
                    R.drawable.goose_review,
                    R.drawable.goose_user,
                    R.string.goose_platforms,
                    R.string.goose_stores),
            new GameProfile(R.string.stardew_name,
                    R.string.stardew_text,
                    R.string.stardew_story_video,
                    R.string.stardew_gameplay_video,
                    R.drawable.stardew_image,
                    R.string.stardew_playerType,
                    R.string.stardew_genre,
                    R.string.stardew_rating,
                    R.string.stardew_time,
                    R.string.stardew_developer,
                    R.string.stardew_publisher,
                    R.string.stardew_alsoLiked,
                    R.drawable.stardew_review,
                    R.drawable.stardew_user,
                    R.string.stardew_platforms,
                    R.string.stardew_stores)
    };
    private GameProfile[] savedGames = new GameProfile[NUM_GAMES];
    private int savedGamesNum = 0;

    public static Games getInstance() {
        return mInstance;
    }

    private Games() {
    }

    public void changeCurrentGame(){
        int i;
//        i = currentIndex;
//        i++;
//        if(i >= NUM_GAMES) currentIndex = 0; //return to start of list
        do{
            i = (int)(NUM_GAMES*Math.random());
        } while(i == currentIndex || (savedGames[i] != null && savedGamesNum != NUM_GAMES)); //Do until changed and not in saved already
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
        if(savedGames[currentIndex] == null){
            savedGames[currentIndex] = allGames[currentIndex];
            savedGamesNum++;
        }
    }

    public GameProfile[] getSavedGames(){
        return savedGames;
    }
}
