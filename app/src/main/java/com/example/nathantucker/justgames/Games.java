package com.example.nathantucker.justgames;

public class Games {
    private static final Games mInstance = new Games();

    private int NUM_GAMES = 3;
    private int currentIndex = 0;
    private GameProfile[] allGames = {
            new GameProfile(R.string.jedi_name,
                    R.string.jedi_text,
                    R.string.jedi_story_video,
                    R.string.jedi_gameplay_video,
                    R.drawable.star_wars_image),
            new GameProfile(R.string.ds_name,
                    R.string.ds_text,
                    R.string.ds_story_video,
                    R.string.ds_gameplay_video,
                    R.drawable.ds_image),
            new GameProfile(R.string.warframe_name,
                    R.string.warframe_text,
                    R.string.warframe_story_video,
                    R.string.warframe_gameplay_video,
                    R.drawable.warframe_image)
    };

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

    public GameProfile getGame(int name){
        for(int i = 0; i < NUM_GAMES; i++){
            if(name == allGames[i].getName()){
                return allGames[i];
            }
        }
        return allGames[0]; //default
    }
}
