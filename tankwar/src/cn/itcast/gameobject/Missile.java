package cn.itcast.gameobject;

import javax.swing.*;
import java.awt.*;

public class Missile {
    public  ImageIcon image;
    public Point position=new Point();
   public int xV,yV;
    public Missile(int x,int y,int xV,int yV,ImageIcon image){
        position.x=x;
        position.y=y;
        this.xV=xV;
        this.yV=yV;
        this.image=image;
    }

}
