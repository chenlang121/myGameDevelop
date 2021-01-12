package cn.itcast.gameobject;

import cn.itcast.view.Config;

import javax.swing.*;
import java.awt.*;

public class Tank  {

    public Point position = new Point();
    private Color color;
    private int speed = Config.SPEED;
    private boolean isAlive = true;
    private char forward = 'w';
    private int blood = 5;
    private ImageIcon tankImage;
    public int xD,yD;
    public Tank() {





    }





    public Tank(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void changeDirection(char c) {
        this.forward = c;
    }

    public ImageIcon getTankImage() {
        return tankImage;
    }

    public void setTankImage(ImageIcon tankImage) {
        this.tankImage = tankImage;
    }

    public int getSpeed() {
        return this.speed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }
    public void reduceBlood(int i){blood-=i;};


}
