package cn.itcast.view;

import javax.swing.*;

public class Config {
    public static final int RECT_SIZE=24;
    public static final int GAME_WIDTH=45;
    public static final int GAME_HEIGHT=33;

   //数据库相关常量
    public static final String DRIVER="com.mysql.cj.jdbc.Driver";
    public static final String URL="jdbc:mysql://localhost:3306/userInfo?useUnicode=true&characterEncoding=utf-8&userSSL=false&serverTimezone=GMT%2B8";
    public static final String USER="root";
    public static final String PASSWORD="2368824873";
    public static final ImageIcon missile=new ImageIcon();

    public static final int SPEED=15;
    //用户信息
    public static String userName;
    public static String password;
    //管理员信息
    public static String adminName="chenlang";
    public static String adminPassword="2368824873";
    public static String TANK_1_UP;
    public static String TANK_1_DOWN;
    public static String TANK_1_LEFT;
    public static String TANK_1_RIGHt;
    //墙体种类
    public static final ImageIcon YELLOW_WALL=new ImageIcon();
    public static final ImageIcon WHITE_WALL=new ImageIcon();

    //public static final int BLOOD_Y_W=POWER*5;
    //public static final int BLOOD_W_W=POWER*3;


}
