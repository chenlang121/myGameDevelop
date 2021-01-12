package cn.itcast.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class MainMenu
{
    public static Map<String,JFrame> windows=new HashMap<String,JFrame>();//存放所有窗口对象
    public static void main(String[] args){

        JFrame frame=new JFrame("坦克世界");

        windows.put("menu",frame);
        frame.setLayout(null);
        frame.setSize(1100,800);
        frame.setLocationRelativeTo(null);
        //设置字体
        Font font=new Font("",Font.ITALIC+Font.BOLD,24);

        //开始游戏按钮
        JButton play=new JButton("开始游戏");
        play.setFont(font);
        play.setBounds(400,200,200,50);
        play.setBackground(Color.YELLOW);
        frame.add(play);
        //排行榜
        JButton rankCheck=new JButton("排行榜");
        rankCheck.setForeground(Color.RED);
        rankCheck.setFont(font);
        rankCheck.setBounds(400,260,200,50);
        rankCheck.setBackground(Color.BLUE);
        frame.add(rankCheck);
        //退出游戏按钮
        JButton endGame=new JButton("退出游戏");
        endGame.setBounds(400,320,200,50);
        endGame.setBackground(Color.RED);
        endGame.setFont(font);
        frame.add(endGame);
        //登录按钮
        Icon loginP=new ImageIcon("picture\\login.png");
        JButton login=new JButton(loginP);
        login.setBounds(400,380,90,50);
        frame.add(login);
        //更换
        Icon changeP=new ImageIcon("picture\\change.png");
        JButton change=new JButton(changeP);
        change.setBounds(500,380,90,50);
        frame.add(change);
        //注册按钮
        JButton register=new JButton("注册");
        register.setBounds(400,460,90,50);
        register.setFont(font);
        register.setForeground(Color.GREEN);
        frame.add(register);
        //游客
        JButton tourist=new JButton("游客");
        tourist.setBounds(500,460,90,50);
        tourist.setFont(font);
        tourist.setForeground(Color.GRAY);
        frame.add(tourist);
        //菜单背景
        Icon bgp=new ImageIcon("picture\\background.png");
        JLabel label=new JLabel(bgp);
        label.setBounds(0,0,frame.getWidth(),frame.getHeight());
        frame.add(label);
        frame.setVisible(true);
        //绑定事件
        frame.addWindowListener(new WindowAdapter() {           //关闭窗口的监听
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
        play.addActionListener(new ActionListener() {           //开始游戏的监听
            @Override
            public void actionPerformed(ActionEvent e) {
                new Game();
                frame.setVisible(false);

            }
        });
        rankCheck.addActionListener(new ActionListener() {      //排行榜的监听
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setVisible(false);
                try {
                    new Rank();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        endGame.addActionListener(new ActionListener() {        //退出游戏的监听
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        login.addActionListener(new ActionListener() {          //登录的监听
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                frame.setVisible(false);
            }
        });
        change.addActionListener(new ActionListener() {         //更换的监听
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                frame.setVisible(false);
            }
        });
        register.addActionListener(new ActionListener() {       //注册的监听
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
                frame.setVisible(false);
            }
        });
        tourist.addActionListener(new ActionListener() {        //游客的监听
            @Override
            public void actionPerformed(ActionEvent e) {
                Config.userName="游客";
                Config.password="123456";
                new Game();
                frame.setVisible(false);
            }
        });
    }
}
