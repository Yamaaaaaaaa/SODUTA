package com.mygdx.game.model.file;

import java.io.Serializable;

public class Point_SetEnding implements Serializable, Comparable<Point_SetEnding>{
    private String nameRanking;
    private int pointRanking;
    private String dayRanking;

    public Point_SetEnding(String name, int point) {
        this.nameRanking = name;
        this.pointRanking = point;
      //  this.dayRanking = dayRanking;
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

    public int getPoint() {
        return pointRanking;
    }

    public void setPoint(int point) {
        this.pointRanking = point;
    }

    @Override
    public int compareTo(Point_SetEnding o) {
        return (-this.pointRanking + o.getPoint());
    }
}
