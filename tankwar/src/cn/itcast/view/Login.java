package cn.itcast.view;

import cn.itcast.service.impl.UserInfoIServiceImpl;
import cn.itcast.vo.userinfo.UserInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

class Login{
    Font font=new Font("name",Font.ITALIC+Font.BOLD,24);
    private JFrame frame=new JFrame("登录界面");
    private JButton submit=new JButton("登录");
    private JButton reset=new JButton("重置");
    private JLabel nameLab=new JLabel("用户名:");
    private JLabel passLab=new JLabel("密码");
    private JLabel infoLab=new JLabel("登录系统");
    private JTextField nameText=new JTextField();
    private JPasswordField passText=new JPasswordField();
    private int x=200;
    private int y=150;
    JTextArea jt=new JTextArea("登录系统");

    public Login(){
        Font fnt=new Font("Serief",Font.BOLD,12);
        jt.setForeground(Color.cyan);
        jt.setFont(font);
        jt.setBounds(frame.getWidth()/4,frame.getHeight()/4*3,2000,45);
        jt.setBackground(Color.gray);
        frame.add(jt);
        infoLab.setFont(fnt);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameText.setText("");
                passText.setText("");
                infoLab.setText("login System");

            }

        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameText.getText().equals("chenlang")&&"2368824873".equals(passText.getText())){
                    jt.replaceRange(":欢迎管理员：" +Config.userName+ "上线", 4, jt.getText().length());
                    Config.userName="chenlang";
                    Config.password="2368824873";
                    return;
                }

                if (e.getSource() == submit) {
                    UserInfo ut = null;
                    try {
                        ut = new UserInfoIServiceImpl().get(nameText.getText());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }


                    if (ut!=null&&ut.getPassword().equals(passText.getText())) {
                        jt.replaceRange(":欢迎坦克手：" + ut.getName() + "上线", 4, jt.getText().length());
                        Config.userName = ut.getName();
                        Config.password = ut.getPassword();
                    } else {
                        jt.replaceRange(":坦克证件无效，坦克手，去注册一个吧！", 4, jt.getText().length());

                    }
                }

            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                MainMenu.windows.get("menu").setVisible(true);
            }

        });
        frame.setLayout(null);
        //用户名区域
        nameLab.setBounds(x,y,130,30);
        nameLab.setFont(font);
        nameLab.setForeground(Color.GREEN);
        frame.add(nameLab);
        nameText.setBounds(x+130,y,240,30);
        nameText.setFont(font);
        nameText.setForeground(Color.GREEN);
        frame.add(nameText);
        //密码区域
        passLab.setBounds(x,y+40,130,30);
        passLab.setFont(font);
        passLab.setForeground(Color.GREEN);
        frame.add(passLab);
        passText.setBounds(x+130,y+40,240,30);
        passText.setFont(font);
        frame.add(passText);
        //提交or重置
        submit.setFont(font);
        submit.setForeground(Color.GREEN);
        submit.setBounds(x,y+80,100,30);
        frame.add(submit);
        reset.setFont(font);
        reset.setForeground(Color.RED);
        reset.setBounds(x+200,y+80,100,30);
        frame.add(reset);
        //frame的设置
        frame.setSize(700,550);
        frame.setLocationRelativeTo(null);
        Icon picture=new ImageIcon("picture//aa.jpg");
        JLabel background=new JLabel(picture);
        background.setBounds(0,0,frame.getWidth(),frame.getHeight());
        frame.add(background);
        frame.setVisible(true);
    }
}
