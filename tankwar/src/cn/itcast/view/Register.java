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
import java.util.Random;

class CheckCode extends JPanel{
    Font font=new Font("",Font.ITALIC+Font.BOLD,20);
    public static char c1='a',c2='b',c3='c',c4='d';
    Random r=new Random();
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(font);
        g.setColor(Color.BLUE);
        g.drawRect(0,0,100-1,30-1);
        g.drawString(c1+"",0,20);
        g.drawString(c2+"",20,20);
        g.drawString(c3+"",40,20);
        g.drawString(c4+"",60,20);
        for(int i=0;i<20;i++)
            g.drawLine(r.nextInt(100),r.nextInt(30),r.nextInt(80),r.nextInt(30));
    }
    public CheckCode(){
        this.setBounds(265,160,80,30);
    }
}

public class Register extends JFrame {
    Font font=new Font("",Font.ITALIC+Font.BOLD,30);
    JButton changeCode=new JButton("看不清");
    char[] codes="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private JButton submit=new JButton("注册");
    private JButton reset=new JButton("重置");
    private JLabel nameLab=new JLabel("用户名:");
    private JLabel passLab=new JLabel("密码：");
    private JLabel checkCode=new JLabel("验证码：");
    private JTextField nameText=new JTextField();
    private JPasswordField passText=new JPasswordField();
    private JTextField codeText=new JTextField();
    private int x=50;
    private int y=80;
    JTextArea title=new JTextArea("注册系统：");


    public Register(){
        this.setSize(460,400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        title.setFont(font);
        title.setForeground(Color.CYAN);
        title.setBackground(Color.gray);
        title.setBounds(0,0,this.getWidth(),40);
        add(title);
        CheckCode checkCode =new CheckCode();
        this.add(checkCode);
        this.setBackground(Color.gray);
        this.setLayout(null);



        changeCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random r=new Random();
                int index=r.nextInt(codes.length);
                checkCode.c1=codes[index];
                index=r.nextInt(codes.length);
                checkCode.c2=codes[index];
                index=r.nextInt(codes.length);
                checkCode.c3=codes[index];
                index=r.nextInt(codes.length);
                checkCode.c4=codes[index];
                checkCode.repaint();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainMenu.windows.get("menu").setVisible(true);
            }
        });
        nameLab.setBounds(x,y,130,30);
        nameLab.setFont(font);
        nameLab.setForeground(Color.GREEN);
        this.add(nameLab);
        nameText.setBounds(x+130,y,240,30);
        nameText.setFont(font);
        nameText.setForeground(Color.GREEN);
        this.add(nameText);
        //密码区域
        passLab.setBounds(x,y+40,130,30);
        passLab.setFont(font);
        passLab.setForeground(Color.GREEN);
        this.add(passLab);
        passText.setBounds(x+130,y+40,240,30);
        passText.setFont(font);
        this.add(passText);
        //验证码
        this.checkCode.setBounds(x,y+80,130,30);
        this.checkCode.setFont(font);
        this.checkCode.setForeground(Color.GREEN);
        this.add(this.checkCode);
        codeText.setBounds(x+130,y+80,80,30);
        codeText.setFont(font);
        this.add(codeText);
        changeCode.setBounds(x+300,y+80,80,30);
        this.add(changeCode);
        //提交or重置
        submit.setFont(font);
        submit.setForeground(Color.GREEN);
        submit.setBounds(x,y+120,100,30);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!codeText.getText().equals(""+ CheckCode.c1+ CheckCode.c2+ checkCode.c3+ checkCode.c4)){
                    title.replaceRange("验证码错误！！！",5,title.getText().length());
                    return;
                }
                if("".equals(nameText.getText())||"".equals(passText.getPassword())) {
                    title.replaceRange("用户名或密码为空！！！", 5, title.getText().length());
                    return;
                }
                Connection c=null;
                Statement st=null;
                String sql="select user_name from userinfo where user_name='"+nameText.getText()+"'";
                ResultSet rs=null;
                try {
                    Class.forName(Config.DRIVER);
                    c= DriverManager.getConnection(Config.URL,"root","2368824873");
                    st=c.createStatement();
                    rs=st.executeQuery(sql);
                    if(rs.next())
                       title.replaceRange("用户名重复！！！",5,title.getText().length());
                    else{
                        title.replaceRange("注册成功！！",5,title.getText().length());
                        UserInfoIServiceImpl us=new UserInfoIServiceImpl();
                        UserInfo ut=new UserInfo();
                        ut.setScore(0);
                        ut.setPassword(passText.getText());
                        ut.setName(nameText.getText());
                        ut.setId(passText.getText().length()*3456+nameText.getText().length()*3453);
                        us.insert(ut);
                        for (int i=3;i>0;i--){
                            title.replaceRange(i+"秒后启动游戏",10,title.getText().length());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }

                        }
                        Config.userName=nameText.getText();
                        Config.password=passText.getPassword().toString();
                        dispose();
                        new Game();
                    }
                } catch (ClassNotFoundException | SQLException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
        this.add(submit);
        reset.setFont(font);
        reset.setForeground(Color.RED);
        reset.setBounds(x+200,y+120,100,30);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameText.setText("");
                passText.setText("");
            }
        });
        this.add(reset);
        ImageIcon bp=new ImageIcon("picture\\loginBackground.jpg");
        JLabel jp=new JLabel(bp);
        jp.setSize(getWidth(),getHeight());
        this.add(jp);

    }
}
