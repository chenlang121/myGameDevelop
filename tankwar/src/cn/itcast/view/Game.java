package cn.itcast.view;

import cn.itcast.gameobject.Missile;
import cn.itcast.gameobject.Tank;
import cn.itcast.gameobject.Wall;
import cn.itcast.service.impl.UserInfoIServiceImpl;
import cn.itcast.vo.userinfo.UserInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game extends JPanel  implements Runnable{
    boolean flag=true;
    boolean bloodSkill=false;
    boolean isPause=false;
    Point bloodLocation=new Point();
    ImageIcon bloodP=new ImageIcon("picture\\bloodPack.png");
    int score=0;
    int count=0;
    int dx,dy;
    int tR;
    
    Random dr=new Random();
    Wall[][] map=new Wall[Config.GAME_HEIGHT][Config.GAME_WIDTH];
    JFrame frame=new JFrame("坦克大战");
    List<Wall> walls=new ArrayList<Wall>();
    List<Tank> tanks=new LinkedList<Tank>();
    List<Missile> missiles=new ArrayList<Missile>();
    Tank[][] tankMap=new Tank[Config.GAME_HEIGHT][Config.GAME_WIDTH];
    ImageIcon tankImageUP=new ImageIcon("picture\\me-up.gif");
    ImageIcon tankImageDown=new ImageIcon("picture\\me-down.gif");
    ImageIcon tankImageRight=new ImageIcon("picture\\me-right.gif");
    ImageIcon tankImageLeft=new ImageIcon("picture\\me-left.gif");
    ImageIcon myTank=tankImageUP;
    ImageIcon enemyW=new ImageIcon("picture\\enemy-w.png");
    ImageIcon enemyS=new ImageIcon("picture\\enemy-s.png");
    ImageIcon enemyA=new ImageIcon("picture\\enemy-a.png");
    ImageIcon enemyD=new ImageIcon("picture\\enemy-d.png");
    ImageIcon missileImage=new ImageIcon("picture\\tankMissile.gif");
    ImageIcon myMissileImage=new ImageIcon("picture\\myMissile.png");
    ImageIcon blood1=new ImageIcon("picture\\blood1.png");
    ImageIcon blood2=new ImageIcon("picture\\blood2.png");
    ImageIcon blood3=new ImageIcon("picture\\blood3.png");
    ImageIcon blood4=new ImageIcon("picture\\blood4.png");
    ImageIcon blood5=new ImageIcon("picture\\blood5.png");

    ImageIcon[] blood= {blood1,blood2,blood3,blood4,blood5};
    ImageIcon pauseP=new ImageIcon("picture\\pause.png");
    ImageIcon conti=new ImageIcon("picture\\continue.png");
    Font font=new Font("font",Font.ITALIC+Font.BOLD,18);
    //坦克出现位置
    //我的坦克初始化   第一次出现的位置设定
    Tank tank=new Tank(Config.RECT_SIZE*Config.GAME_WIDTH/2,
            Config.RECT_SIZE*(Config.GAME_HEIGHT-2));
    Thread go=new Thread(this);
    private String forward;
    @Override
    public void paintComponent(Graphics g){
        count++;
        super.paintComponent(g);
        ImageIcon wall=new ImageIcon("picture\\whiteWall.gif");
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Config.RECT_SIZE*Config.GAME_WIDTH,Config.RECT_SIZE*Config.GAME_HEIGHT);
        //画边界墙
        for(int i=0;i<Config.GAME_WIDTH;i++) {
            g.drawImage(wall.getImage(), Config.RECT_SIZE *i, 0, Config.RECT_SIZE, Config.RECT_SIZE, this);
            g.drawImage(wall.getImage(), Config.RECT_SIZE * i, Config.RECT_SIZE*(Config.GAME_HEIGHT-1),
                    Config.RECT_SIZE, Config.RECT_SIZE, this);
        }
        for(int i=0;i<Config.GAME_HEIGHT;i++) {
            g.drawImage(wall.getImage(), 0, Config.RECT_SIZE * (i), Config.RECT_SIZE, Config.RECT_SIZE, this);
            g.drawImage(wall.getImage(), Config.RECT_SIZE * (Config.GAME_WIDTH-1), Config.RECT_SIZE * i,
                    Config.RECT_SIZE, Config.RECT_SIZE, this);
        }

        //画炮弹
        int x,y,dx,dyu;
        //判断导弹打到物体了吗
        boolean flag;
        for(int i=0;i<missiles.size();i++) {
            flag=false;
            x = missiles.get(i).position.x / Config.RECT_SIZE;
            y = missiles.get(i).position.y / Config.RECT_SIZE;
           //是否打到墙
            if (x < Config.GAME_WIDTH && y < Config.GAME_HEIGHT && x >= 0 && y >= 0){
                if (map[y][x] != null)
                    switch (map[y][x].getKind()) {
                        case "water":
                            missiles.get(i).xV = -missiles.get(i).xV;
                            missiles.get(i).yV = -missiles.get(i).yV;
                            map[y][x].setBloodBar(1);
                            break;
                        default:
                            map[y][x].setBloodBar(1);
                            flag=true;
                            break;
                    }
                //是否打到坦克
                if(tank.position.x/Config.RECT_SIZE==x&&tank.position.y/Config.RECT_SIZE==y) {
                    if ((missiles.get(i).xV + missiles.get(i).yV) % 3 == 0) ;
                    else {
                        tank.reduceBlood(1);
                        flag=true;
                    }
                }
                if(tankMap[y][x]!=null)
                if((missiles.get(i).xV + missiles.get(i).yV) % 3 != 0) ;
                else {
                    tankMap[y][x].reduceBlood(1);
                    flag=true;
                }

            }
            if (x >= Config.GAME_WIDTH - 2 || x <= 0 || y >= Config.GAME_HEIGHT - 2 || y <= 0) {
                flag=true;
            }

            g.drawImage(missiles.get(i).image.getImage(), missiles.get(i).position.x += missiles.get(i).xV,
                    missiles.get(i).position.y += missiles.get(i).yV, 20, 20, this);
            if(flag) missiles.remove(missiles.get(i));
        }
        //画坦克

        if(tanks.size()==0)
        {

            JOptionPane.showMessageDialog(null,"胜利了");
            System.exit(1);
        }
        if(tank.getBlood()<=0)
        {
            if(Config.userName!=null&&!Config.userName.equals("游客")) {
                UserInfoIServiceImpl us = new UserInfoIServiceImpl();
                UserInfo ut = new UserInfo();
                try {
                    ut = us.get(Config.userName);

                    if(ut.getScore()<score) {
                        us = new UserInfoIServiceImpl();
                        ut.setScore(score);
                        us.update(ut);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.exit(1);
        }
        g.drawImage(myTank.getImage(),tank.position.x,tank.position.y,Config.RECT_SIZE,Config.RECT_SIZE,this);
        //g.drawImage(tankp.getImage(), 344, Config.RECT_SIZE * 5, Config.RECT_SIZE, Config.RECT_SIZE, this);
        for(int i=0;i<tanks.size();i++){
            if(tanks.get(i).getBlood()<=0){
                score+=20;
                tankMap[tanks.get(i).position.y/Config.RECT_SIZE][tanks.get(i).position.x/Config.RECT_SIZE]=null;
                tanks.remove(tanks.get(i));
                continue;
            }
            tR=count%800;
            if(tR==1){
                dx= dr.nextInt(1600);
                tanks.get(i).yD=dx>=800?(dx>=1000?1:-1):0;
                tanks.get(i).xD=dx<800?(dx<400?-1:1):0;

                if(tanks.get(i).yD>0)
                    tanks.get(i).setTankImage(enemyS);
                else if(tanks.get(i).yD<0)
                    tanks.get(i).setTankImage(enemyW);
                else if(tanks.get(i).xD>0)
                    tanks.get(i).setTankImage(enemyD);
                else tanks.get(i).setTankImage(enemyA);

            }
            if(tR%100==0){
                dx= dr.nextInt(100);
                if(dx>80) {
                    missiles.add(new Missile(tanks.get(i).position.x,tanks.get(i).position.y,
                            tanks.get(i).xD, tanks.get(i).yD,missileImage));
                }
            }
            if(count%5==0) {
                tankMap[tanks.get(i).position.y/Config.RECT_SIZE][tanks.get(i).position.x/Config.RECT_SIZE]=null;
                if(tanks.get(i).yD<0) {
                    if (tanks.get(i).position.y >= Config.RECT_SIZE + 1 &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE][tanks.get(i).position.x / Config.RECT_SIZE + 1] == null &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE][tanks.get(i).position.x / Config.RECT_SIZE] == null)
                        tanks.get(i).position.y += tanks.get(i).yD;
                    else if (tanks.get(i).position.y >= Config.RECT_SIZE + 1 &&
                            tanks.get(i).position.x % Config.RECT_SIZE == 0 &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE][tanks.get(i).position.x / Config.RECT_SIZE + 1] != null &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE][tanks.get(i).position.x / Config.RECT_SIZE] == null)
                        tanks.get(i).position.y += tanks.get(i).yD;
                }
                else if(tanks.get(i).yD>0) {
                    if (tanks.get(i).position.y <= Config.RECT_SIZE * (Config.GAME_HEIGHT - 2) - 1 &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE + 1][tanks.get(i).position.x / Config.RECT_SIZE + 1] == null &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE + 1][tanks.get(i).position.x / Config.RECT_SIZE] == null)
                        tanks.get(i).position.y += tanks.get(i).yD;
                    else if (tanks.get(i).position.y <= Config.RECT_SIZE * (Config.GAME_HEIGHT - 2) - 1 &&
                            tanks.get(i).position.x % Config.RECT_SIZE == 0 &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE + 1][tanks.get(i).position.x / Config.RECT_SIZE + 1] != null &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE + 1][tanks.get(i).position.x / Config.RECT_SIZE] == null)
                        tanks.get(i).position.y += tanks.get(i).yD;
                }
                else if(tanks.get(i).xD<0) {
                    if (tanks.get(i).position.x >= Config.RECT_SIZE + 1 &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE][tanks.get(i).position.x / Config.RECT_SIZE] == null &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE + 1][tanks.get(i).position.x / Config.RECT_SIZE] == null)
                        tanks.get(i).position.x += tanks.get(i).xD;
                    else if (tanks.get(i).position.x >= Config.RECT_SIZE + 1 &&
                            tanks.get(i).position.y % Config.RECT_SIZE == 0 &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE][tanks.get(i).position.x / Config.RECT_SIZE + 1] == null &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE + 1][tanks.get(i).position.x / Config.RECT_SIZE] != null)
                        tanks.get(i).position.x += tanks.get(i).xD;
                }
                else {
                    if (tanks.get(i).position.x <= Config.RECT_SIZE * (Config.GAME_WIDTH - 2) - 1 &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE][tanks.get(i).position.x / Config.RECT_SIZE + 1] == null &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE + 1][tanks.get(i).position.x / Config.RECT_SIZE + 1] == null)
                        tanks.get(i).position.x+=tanks.get(i).xD;
                    else if (tanks.get(i).position.x <= Config.RECT_SIZE * (Config.GAME_WIDTH - 2) - 1 &&
                            tanks.get(i).position.y % Config.RECT_SIZE == 0 &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE][tanks.get(i).position.x / Config.RECT_SIZE + 1] == null &&
                            map[tanks.get(i).position.y / Config.RECT_SIZE + 1][tanks.get(i).position.x / Config.RECT_SIZE + 1] != null)
                        tanks.get(i).position.x+=tanks.get(i).xD;
                }
                tankMap[tanks.get(i).position.y/Config.RECT_SIZE][tanks.get(i).position.x/Config.RECT_SIZE]=tanks.get(i);
            }
            g.drawImage(tanks.get(i).getTankImage().getImage(), tanks.get(i).position.x,tanks.get(i).position.y,
                    Config.RECT_SIZE,Config.RECT_SIZE,this);
        }
        //判断血包是否存在
        if(bloodSkill==false) {
            //判断是否被吃
            dx = bloodLocation.x / Config.RECT_SIZE;
            dy = bloodLocation.y / Config.RECT_SIZE;
            if (tankMap[dy][dx] != null) {

                tankMap[dy][dx].setBlood(5);
                bFindEmpty();
                missiles.add(new Missile(tankMap[dy][dx].position.x, tankMap[dy][dx].position.y, 0, 1,missileImage));
                missiles.add(new Missile(tankMap[dy][dx].position.x, tankMap[dy][dx].position.y, 0, -1,missileImage));
                missiles.add(new Missile(tankMap[dy][dx].position.x, tankMap[dy][dx].position.y, 1, 0,missileImage));
                missiles.add(new Missile(tankMap[dy][dx].position.x, tankMap[dy][dx].position.y, -1, 0,missileImage));
            }
            if (dx == tank.position.x / Config.RECT_SIZE && dy == tank.position.y / Config.RECT_SIZE) {
                bloodSkill = true;
                missiles.add(new Missile(tank.position.x, tank.position.y, 0, 3,myMissileImage));
                missiles.add(new Missile(tank.position.x, tank.position.y, 0, -3,myMissileImage));
                missiles.add(new Missile(tank.position.x, tank.position.y, 3, 0,myMissileImage));
                missiles.add(new Missile(tank.position.x, tank.position.y, -3, 0,myMissileImage));
                tank.setBlood(5);
            }
            //画血包
            g.drawImage(bloodP.getImage(), bloodLocation.x, bloodLocation.y, Config.RECT_SIZE, Config.RECT_SIZE, this);
        }
        //画血条
        g.drawImage(blood[tank.getBlood()-1].getImage(),tank.position.x-5,tank.position.y-30,
                40,30,this);
        for(Tank t:tanks)
            g.drawImage(blood[t.getBlood()-1].getImage(),t.position.x-5,t.position.y-30,
                    40,30,this);
        //画墙

        for(int i=0;i< walls.size();i++){
            if(walls.get(i).getBloodBar()<=0){
                map[walls.get(i).position.y/Config.RECT_SIZE][walls.get(i).position.x/Config.RECT_SIZE]=null;
                score+=walls.get(i).score;
                walls.remove(walls.get(i));
                continue;
            }
            g.drawImage(walls.get(i).getWallImage().getImage(),walls.get(i).position.x,walls.get(i).position.y,Config.RECT_SIZE,Config.RECT_SIZE,this);
        }
        //画功能区

        g.setFont(font);
        g.setColor(Color.MAGENTA);
        g.drawRect(Config.RECT_SIZE*Config.GAME_WIDTH+3,0,Config.RECT_SIZE*13,Config.RECT_SIZE*Config.GAME_HEIGHT);

        g.drawString("游戏说明",Config.RECT_SIZE*(Config.GAME_WIDTH+4),Config.RECT_SIZE*1);
        g.drawString("坦克手："+Config.userName,Config.RECT_SIZE*(Config.GAME_WIDTH+3),Config.RECT_SIZE*3);

        g.setColor(Color.ORANGE);
        g.drawImage(new ImageIcon("picture\\yellowwall.gif").getImage(),
                Config.RECT_SIZE*(Config.GAME_WIDTH+1),Config.RECT_SIZE*4,Config.RECT_SIZE,Config.RECT_SIZE,this);
        g.drawString("黄砖墙：防御力-》2  积分-》2",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*5);

        g.setColor(Color.gray);
        g.drawImage(new ImageIcon("picture\\whitewall.gif").getImage(),
                Config.RECT_SIZE*(Config.GAME_WIDTH+1),Config.RECT_SIZE*6,Config.RECT_SIZE,Config.RECT_SIZE,this);
        g.drawString("白墙  ：防御力-》8  积分-》8",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*7);

        g.drawImage(enemyD.getImage(),
                Config.RECT_SIZE*(Config.GAME_WIDTH+1),Config.RECT_SIZE*8,Config.RECT_SIZE,Config.RECT_SIZE,this);
        g.drawString("坦克  ，防御力-》20 积分-》20",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*9);

        g.setColor(Color.BLUE);
        g.drawImage(new ImageIcon("picture\\water.gif").getImage(),
                Config.RECT_SIZE*(Config.GAME_WIDTH+1),Config.RECT_SIZE*10,Config.RECT_SIZE,Config.RECT_SIZE,this);
        g.drawString("水魔镜：防御力-》30 积分-》30",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*11);
        g.drawString("损耗神秘魔力，用来反射一切子弹",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*12);

        g.drawImage(bloodP.getImage(),
                Config.RECT_SIZE*(Config.GAME_WIDTH+1),Config.RECT_SIZE*14,Config.RECT_SIZE,Config.RECT_SIZE,this);
        g.drawString("血包：加满生命值",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*15);
        g.drawString("获得技能，按E，发射四面导弹",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*16);

        g.drawString("您的得分： "+score,Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*24);

        g.setColor(Color.ORANGE);
        g.drawString("W： 前进",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*17);
        g.drawString("S： 后退",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*18);
        g.drawString("A： 左行",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*19);
        g.drawString("D： 右行",Config.RECT_SIZE*(Config.GAME_WIDTH+2),Config.RECT_SIZE*20);
    }

    public Game(){

        Tank t1;
        //添加坦克们
        for(int i=0;i<20;i++){
            t1=new Tank(Config.RECT_SIZE*i*2+2*Config.RECT_SIZE,Config.RECT_SIZE*i+10*Config.RECT_SIZE);
            t1.setTankImage(enemyS);
            t1.setBlood(5);
            tanks.add(t1);
            tankMap[t1.position.y/Config.RECT_SIZE][t1.position.x/Config.RECT_SIZE]=t1;
            System.out.println(tanks.size());
        }
        //给集合添加墙
        Wall t=null;
        for(int i=1;i<Config.GAME_WIDTH-1;i++) {
            t=new Wall("water", Config.RECT_SIZE * i, Config.RECT_SIZE * (Config.GAME_HEIGHT / 4));
            walls.add(t);
            map[Config.GAME_HEIGHT / 4][i]=t;
            t=new Wall("water", Config.RECT_SIZE * i, Config.RECT_SIZE * (Config.GAME_HEIGHT / 4+1));
            map[Config.GAME_HEIGHT / 4+1][i]=t;
            walls.add(t);
        }
        for(int i=1;i<Config.GAME_WIDTH-1;i++) {
            t=new Wall("water", Config.RECT_SIZE * i, Config.RECT_SIZE * (Config.GAME_HEIGHT / 4*3));
            walls.add(t);
            map[Config.GAME_HEIGHT / 4*3][i]=t;
            t=new Wall("water", Config.RECT_SIZE * i, Config.RECT_SIZE * (Config.GAME_HEIGHT / 4*3+1));
            walls.add(t);
            map[Config.GAME_HEIGHT / 4*3+1][i]=t;
        }

        //给集合添加黄墙
        for(int i=1;i<=5;i++)
            for(int j=1;j<=i;j++) {
                t=new Wall("yellowWall", Config.RECT_SIZE * j + (Config.GAME_WIDTH / 4 )* Config.RECT_SIZE,
                        Config.RECT_SIZE * (Config.GAME_HEIGHT / 7 * 3) + Config.RECT_SIZE * i);
                walls.add(t);
                map[Config.GAME_HEIGHT / 7 * 3 +  i][j + Config.GAME_WIDTH / 4]=t;
            }
        for(int i=1;i<=8;i++)
            for(int j=1;j<=i;j++) {
                t=new Wall("yellowWall", Config.RECT_SIZE * j + Config.GAME_WIDTH / 4 * Config.RECT_SIZE + Config.RECT_SIZE * 15,
                        Config.RECT_SIZE * (Config.GAME_HEIGHT / 7 * 3) + Config.RECT_SIZE * i);
                walls.add(t);
                map[ Config.GAME_HEIGHT / 7 * 3 + i][ j + Config.GAME_WIDTH / 4  +  15]=t;

            }
        frame.setSize(Config.RECT_SIZE * (Config.GAME_WIDTH + 14), Config.RECT_SIZE * (Config.GAME_HEIGHT + 4));
        /*for(int i=1;i<=3;i++)
            for(int j=1;j<=i;j++) {
                t=new Wall("yellowWall", Config.RECT_SIZE * j + Config.GAME_WIDTH / 4 * Config.RECT_SIZE,
                        Config.RECT_SIZE * Config.GAME_HEIGHT / 7 * 3 + Config.RECT_SIZE * i + Config.RECT_SIZE*5);
                walls.add(t);
                map[ Config.GAME_HEIGHT / 7 * 3 + i + 1][ j + Config.GAME_WIDTH / 4 ]=t;
            }*/
                //白墙
        for(int i=1;i<20;i++) {
            t=new Wall("whiteWall", Config.GAME_WIDTH / 8 * Config.RECT_SIZE,
                    Config.RECT_SIZE * (Config.GAME_HEIGHT / 7 * 3) + Config.RECT_SIZE * i);
            walls.add(t);
            map[ Config.GAME_HEIGHT / 7 * 3 + i][Config.GAME_WIDTH / 8]=t;
        }
        //初始血包位置
        bFindEmpty();

        frame.setLocationRelativeTo(null);
        this.setSize(Config.RECT_SIZE*(Config.GAME_WIDTH+14),Config.RECT_SIZE*(Config.GAME_HEIGHT+4));
        ImageIcon icon=new ImageIcon("picture\\icon.png");
        frame.setIconImage(icon.getImage());
        JMenuBar menuBar=new JMenuBar();

        JMenu pause=new JMenu("暂停");
        pause.setIcon(pauseP);
        menuBar.add(pause);
        JMenu conti1=new JMenu("继续");
        pause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPause=true;
            }
        });
        conti1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPause=false;
            }
        });
        conti1.setIcon(conti);
        menuBar.add(conti1);
        frame.setJMenuBar(menuBar);

        frame.add(this);

        frame.setVisible(true);


        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainMenu.windows.get("menu").setVisible(true);
                go.stop();
            }
        });



        //监听玩家操作
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed  (KeyEvent e) {
                forward=KeyEvent.getKeyText(e.getKeyCode());
                switch (forward){
                    case "W":
                        myTank=tankImageUP;
                        if(tank.position.y>=Config.RECT_SIZE+3&&
                                map[tank.position.y/Config.RECT_SIZE][tank.position.x/Config.RECT_SIZE+1]==null&&
                                map[tank.position.y/Config.RECT_SIZE][tank.position.x/Config.RECT_SIZE]==null)
                            tank.position.y-=3;
                        else if(tank.position.y>=Config.RECT_SIZE+3&&
                                tank.position.x%Config.RECT_SIZE==0&&
                                map[tank.position.y/Config.RECT_SIZE][tank.position.x/Config.RECT_SIZE+1]!=null&&
                                map[tank.position.y/Config.RECT_SIZE][tank.position.x/Config.RECT_SIZE]==null)
                            tank.position.y-=3;
                        else if(tank.position.y<=Config.RECT_SIZE+3)
                            tank.position.y=Config.RECT_SIZE;

                        break;
                    case "S":
                        myTank=tankImageDown;
                        /*if(tank.position.y<=Config.RECT_SIZE*(Config.GAME_HEIGHT-2)-3)
                            tank.position.y+=3;
                        else tank.position.y=Config.RECT_SIZE*(Config.GAME_HEIGHT-2);
                        break;*/
                        if(tank.position.y<=Config.RECT_SIZE*(Config.GAME_HEIGHT-2)-3&&
                                map[tank.position.y/Config.RECT_SIZE+1][tank.position.x/Config.RECT_SIZE+1]==null&&
                                map[tank.position.y/Config.RECT_SIZE+1][tank.position.x/Config.RECT_SIZE]==null)
                            tank.position.y+=3;
                        else if(tank.position.y<=Config.RECT_SIZE*(Config.GAME_HEIGHT-2)-3&&
                                tank.position.x%Config.RECT_SIZE==0&&
                                map[tank.position.y/Config.RECT_SIZE+1][tank.position.x/Config.RECT_SIZE+1]!=null&&
                                map[tank.position.y/Config.RECT_SIZE+1][tank.position.x/Config.RECT_SIZE]==null)
                            tank.position.y+=3;
                        else if(tank.position.y>=Config.RECT_SIZE*(Config.GAME_HEIGHT-2)-3)
                            tank.position.y=Config.RECT_SIZE*(Config.GAME_HEIGHT-2);

                        break;
                    case "A":
                       myTank=tankImageLeft;
                        /*if(tank.position.x>=Config.RECT_SIZE+3)
                            tank.position.x-=3;
                        else tank.position.x=Config.RECT_SIZE;
                        break;*/
                        if(tank.position.x>=Config.RECT_SIZE+3&&
                                map[tank.position.y/Config.RECT_SIZE][tank.position.x/Config.RECT_SIZE]==null&&
                                map[tank.position.y/Config.RECT_SIZE+1][tank.position.x/Config.RECT_SIZE]==null)
                            tank.position.x-=3;
                        else if(tank.position.x>=Config.RECT_SIZE+3&&
                                tank.position.y%Config.RECT_SIZE==0&&
                                map[tank.position.y/Config.RECT_SIZE][tank.position.x/Config.RECT_SIZE+1]==null&&
                                map[tank.position.y/Config.RECT_SIZE+1][tank.position.x/Config.RECT_SIZE]!=null)
                            tank.position.x-=3;
                        else if(tank.position.y>=Config.RECT_SIZE*(Config.GAME_HEIGHT-2)-3)
                            tank.position.x=Config.RECT_SIZE;

                        break;
                    case "D":
                        myTank=tankImageRight;
                        /*if(tank.position.x<=Config.RECT_SIZE*(Config.GAME_WIDTH-2)-3)
                            tank.position.x+=3;
                        else tank.position.x=Config.RECT_SIZE*(Config.GAME_WIDTH-2);
                        break;*/
                        if(tank.position.x<=Config.RECT_SIZE*(Config.GAME_WIDTH-2)-3&&
                                map[tank.position.y/Config.RECT_SIZE][tank.position.x/Config.RECT_SIZE+1]==null&&
                                map[tank.position.y/Config.RECT_SIZE+1][tank.position.x/Config.RECT_SIZE+1]==null)
                            tank.position.x+=3;
                        else if(tank.position.x<=Config.RECT_SIZE*(Config.GAME_WIDTH-2)-3&&
                                tank.position.y%Config.RECT_SIZE==0&&
                                map[tank.position.y/Config.RECT_SIZE][tank.position.x/Config.RECT_SIZE+1]==null&&
                                map[tank.position.y/Config.RECT_SIZE+1][tank.position.x/Config.RECT_SIZE+1]!=null)
                            tank.position.x+=3;
                        else if(tank.position.x>=Config.RECT_SIZE*(Config.GAME_WIDTH-2)-3)
                            tank.position.x=Config.RECT_SIZE*(Config.GAME_WIDTH-2);

                        break;
                    case "J":
                        if(myTank==tankImageUP)
                        missiles.add(new Missile(tank.position.x,tank.position.y,0,-3,myMissileImage));
                        else if(myTank==tankImageDown)
                            missiles.add(new Missile(tank.position.x,tank.position.y,0,3,myMissileImage));
                        else if(myTank==tankImageLeft)
                            missiles.add(new Missile(tank.position.x,tank.position.y,-3,0,myMissileImage));
                        else
                            missiles.add(new Missile(tank.position.x,tank.position.y,3,0,myMissileImage));
                        break;
                    case "E":if(bloodSkill){
                        bFindEmpty();
                        bloodSkill=false;
                    }
                         break;
                    case "P":isPause=!isPause;
                        JOptionPane.showMessageDialog(null,"暂停了");
            }
        }});
        go.start();
    }
    public void bFindEmpty(){
        while(true){
            dx=dr.nextInt(Config.GAME_WIDTH*(Config.RECT_SIZE-2))+Config.RECT_SIZE;
            dy=dr.nextInt(Config.GAME_HEIGHT*(Config.RECT_SIZE-2))+Config.RECT_SIZE;
            //判断血包位置是否无障碍物和坦克
            if(tankMap[dy/Config.RECT_SIZE][dx/Config.RECT_SIZE]==null&&
                    map[dy/Config.RECT_SIZE][dx/Config.RECT_SIZE]==null&&
                    tank.position.x/Config.RECT_SIZE!=dx/Config.RECT_SIZE&&
                    tank.position.y/Config.RECT_SIZE!=dy/Config.RECT_SIZE
            )
                break;

        }
        bloodLocation.x=dx;
        bloodLocation.y=dy;
    }

    @Override
    public void run() {
        while(true) {
            if(!isPause)
                this.repaint();
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
