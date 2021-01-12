package cn.itcast.gameobject;

import javax.swing.*;
import java.awt.*;

public class Wall {
    private ImageIcon wallImage;
    private String kind;
    public Point position=new Point();
    private int bloodBar;
    public int score;
    public ImageIcon getWallImage() {
        return wallImage;
    }

    public int getBloodBar() {
        return bloodBar;
    }

    public Wall(String kind,int x,int y){
        position.x=x;
        position.y=y;
        this.kind=kind;
        switch (kind){
            case "whiteWall":bloodBar=8;wallImage=new ImageIcon("picture\\whiteWall.gif");break;
            case "yellowWall":bloodBar=2;wallImage=new ImageIcon("picture\\yellowWall.gif");break;
            case "water":bloodBar=30;wallImage=new ImageIcon("picture\\water.gif");break;
         }
        score=bloodBar;
   }
   public String getKind(){
        return kind;
    }
    public void setBloodBar(int b){
        this.bloodBar-=b;
    }

}
