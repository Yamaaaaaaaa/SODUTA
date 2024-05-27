package com.mygdx.game.model.file;

public class Save_Ranking {
    private String nameRanking;
    private int pointRanking;
    private String dayRanking;

    public Save_Ranking(String name,int turn, String dayRanking) {
        this.nameRanking = name;
        this.pointRanking = turn;
        this.dayRanking = dayRanking;
    }

    public String getDayRanking() {
        return dayRanking;
    }

    public void setDayRanking(String dayRanking) {
        this.dayRanking = dayRanking;
    }

    public String getName() {
        return nameRanking;
    }

    public void setName(String name) {
        this.nameRanking = name;
    }

    public int getTurn() {
        return pointRanking;
    }

    public void setTurn(int turn) {
        this.pointRanking = turn;
    }
}
