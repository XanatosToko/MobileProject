package com.example.nathantucker.justgames;

public class GameProfile {
    private int name;
    private int nameDev;
    private int storyVideoRes;
    private int gameplayVideoRes;
    private int imageRes;

    private int playerInfo;
    private int genreInfo;
    private int ratingInfo;
    private int timeInfo;
    private int devInfo;
    private int publishInfo;
    private int alsoLikeInfo;
    private int reviewerInfo;
    private int usersInfo;
    private int whereToPlay;
    private int whereToGet;

    public GameProfile(int name, int nameDev, int storyVideoRes, int gameplayVideoRes, int imageRes,
                       int playerInfo, int genreInfo, int ratingInfo, int timeInfo, int devInfo,
                       int publishInfo, int alsoLikeInfo, int reviewerInfo, int usersInfo,
                       int whereToPlay, int whereToGet) {
        this.name = name;
        this.nameDev = nameDev;
        this.storyVideoRes = storyVideoRes;
        this.gameplayVideoRes = gameplayVideoRes;
        this.imageRes = imageRes;
        this.playerInfo = playerInfo;
        this.genreInfo = genreInfo;
        this.ratingInfo = ratingInfo;
        this.timeInfo = timeInfo;
        this.devInfo = devInfo;
        this.publishInfo = publishInfo;
        this.alsoLikeInfo = alsoLikeInfo;
        this.reviewerInfo = reviewerInfo;
        this.usersInfo = usersInfo;
        this.whereToPlay = whereToPlay;
        this.whereToGet = whereToGet;
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

    public int getPlayerInfo() {
        return playerInfo;
    }

    public int getGenreInfo() {
        return genreInfo;
    }

    public int getRatingInfo() {
        return ratingInfo;
    }

    public int getTimeInfo() {
        return timeInfo;
    }

    public int getDevInfo() {
        return devInfo;
    }

    public int getPublishInfo() {
        return publishInfo;
    }

    public int getAlsoLikeInfo() {
        return alsoLikeInfo;
    }

    public int getReviewerInfo() {
        return reviewerInfo;
    }

    public int getUsersInfo() {
        return usersInfo;
    }

    public int getWhereToPlay() {
        return whereToPlay;
    }

    public int getWhereToGet() {
        return whereToGet;
    }
}
