package com.example.nathantucker.justgames;

public class GameProfile {
    private int name;
    private int nameDev;
    private int storyVideoRes;
    private int gameplayVideoRes;
    private int imageRes;

    public GameProfile(int name, int nameDev, int storyVideoRes, int gameplayVideoRes, int imageRes) {
        this.name = name;
        this.nameDev = nameDev;
        this.storyVideoRes = storyVideoRes;
        this.gameplayVideoRes = gameplayVideoRes;
        this.imageRes = imageRes;
    }

    public int getName() {
        return name;
    }

    public int getNameDev() {
        return nameDev;
    }

    public int getStoryVideoRes() {
        return storyVideoRes;
    }

    public int getGameplayVideoRes() {
        return gameplayVideoRes;
    }

    public int getImageRes() {
        return imageRes;
    }

    //TODO: Add other info
}
