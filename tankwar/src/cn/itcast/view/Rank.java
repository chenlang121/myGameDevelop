package cn.itcast.view;

import cn.itcast.service.impl.UserInfoIServiceImpl;
import cn.itcast.vo.userinfo.UserInfo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

public class Rank extends JFrame {
    ImageIcon identityP = new ImageIcon("picture\\identity.png");
    private JTable table = null;
    private DefaultTableModel tableModel = null;
    private String[] titles ;
    private Object[][] users;
    private JButton addRow = new JButton("增加行");
    private JButton remove = new JButton("删除选中行");
    private JButton queryId = new JButton("id查询");
    //用户集合
    private List<UserInfo> userList=null;
    UserInfoIServiceImpl userInfo = new UserInfoIServiceImpl();


    public Rank() {

        JMenuBar menuBar = new JMenuBar();
        JMenu identity = new JMenu("身份");
        identity.setIcon(identityP);
        menuBar.add(identity);
        JMenuItem user = new JMenuItem("用户");
        user.setForeground(Color.BLACK);
        identity.addSeparator();
        identity.add(user);
        JMenuItem admin = new JMenuItem("管理员");
        admin.setForeground(Color.BLUE);
        identity.add(admin);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
        this.setBackground(Color.gray);
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainMenu.windows.get("menu").setVisible(true);
            }
        });
        if(Config.adminName.equals(Config.userName)){

            adminInit();

        }
        else{

            userInit();
        }
        JScrollPane scr=new JScrollPane(table);
        this.add(scr);
        //this.setLayout(null);
        //remove();
    }
        public void userInit(){
            titles=new String[]{"编号","用户名", "最高得分"};


            try {
                userList=userInfo.list();
                users=new Object[userList.size()][3];
                for (int i=0;i<userList.size();i++){
                    users[i][0]=userList.get(i).getId();
                    users[i][1]=userList.get(i).getName();
                    users[i][2]=userList.get(i).getScore();
                }
                tableModel=new DefaultTableModel(users,titles);
                table=new JTable(tableModel);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        public void adminInit () {
            titles= new String[]{"编号", "用户名", "密码", "最高得分"};
            this.tableModel=new DefaultTableModel(this.users,titles);
            this.table=new JTable(this.tableModel);
            JScrollPane scr=new JScrollPane(table);
            JPanel toolBar=new JPanel();
            toolBar.add(this.addRow);
            toolBar.add(this.remove);
            toolBar.add(this.queryId);

            this.add(toolBar,BorderLayout.NORTH);
            this.add(scr,BorderLayout.CENTER);
            this.setSize(699,500);
            this.setLocationRelativeTo(null);
            addRow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tableModel.addRow(new Object[]{1,"2","2",5});
                }

            });
            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    int rowCount=tableModel.getRowCount()-1;
                    tableModel.removeRow(rowCount);
                    tableModel.setRowCount(rowCount);
                }
            });
            queryId.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

            //获取排行榜


            try {
                userList=userInfo.list();
                users=new Object[userList.size()][4];
                for(int i=0;i<userList.size();i++){
                    users[i][0]=userList.get(i).getId();
                    users[i][1]=userList.get(i).getName();
                    users[i][2]=userList.get(i).getPassword();
                    users[i][3]=userList.get(i).getScore();
                }
                tableModel=new DefaultTableModel(users,titles);
                table=new JTable(tableModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*public void remove(){
            JTextArea jt=new JTextArea();
            jt.setLocation(getWidth()/3,getHeight()/3);
            jt.setSize(200,100);
            JButton sure=new JButton("sure");
            sure.setBounds(300,300,100,100);
            this.add(jt);
            this.add(sure);
            sure.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name=jt.getText();


                    UserInfoIServiceImpl us=new UserInfoIServiceImpl();
                    try {
                        //us.remove(name);
                        System.out.println(33);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });
        }*/


}
