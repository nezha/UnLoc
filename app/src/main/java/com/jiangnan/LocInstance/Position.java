package com.jiangnan.LocInstance;

/**
 * Created by 123 on 2015/8/13.
 */
public class Position {
    //定义user的位置坐标
    private float LocX;
    private float LocY;
    //保留字段，主要是识别是哪一层
    private int floor;
    private static Position position = null;

    private Position(){}

    public static synchronized Position getPosition(){
        if(position == null){
            position = new Position();
        }
        return position;
    }

    public float getLocX() {
        return LocX;
    }

    public float getLocY() {
        return LocY;
    }

    public int getFloor() {
        return floor;
    }

    public void setLocX(float locX) {
        LocX = locX;
    }

    public void setLocY(float locY) {
        LocY = locY;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
