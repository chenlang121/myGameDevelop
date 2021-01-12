package cn.itcast.dao.impl;

import cn.itcast.vo.userinfo.IUserInfoDAO;
import cn.itcast.vo.userinfo.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UserInfoDAOImpl implements IUserInfoDAO {
    private Connection conn;
    private PreparedStatement pst;
    public UserInfoDAOImpl(Connection conn){
        this.conn=conn;
    }
    @Override
    public boolean doCreate(UserInfo vo) throws Exception {
        String sql="insert into userinfo (id,user_name,password,score) values (?,?,?,?)";
        this.pst=this.conn.prepareStatement(sql);
        this.pst.setInt(1,vo.getId());
        this.pst.setString(2,vo.getName());
        this.pst.setString(3,vo.getPassword());
        this.pst.setInt(4,vo.getScore());
        return this.pst.executeUpdate()>0;
    }

    @Override
    public UserInfo findById(String id) throws Exception {
        UserInfo vo=null;
        String sql="select id,user_name,password,score from userinfo where user_name=?";
        this.pst=this.conn.prepareStatement(sql);
        this.pst.setString(1,id);
        ResultSet rs=this.pst.executeQuery();
        if(rs.next()){
            vo=new UserInfo();
            vo.setId(rs.getInt(1));
            vo.setName(rs.getString(2));
            vo.setPassword(rs.getString(3));
            vo.setScore(rs.getInt(4));
        }
        return vo;
    }

    @Override
    public List<UserInfo> findAll() throws Exception {
        List<UserInfo> all=new ArrayList<UserInfo>();
        String sql="select id,user_name,password,score from userinfo order by score desc ";
        this.pst=conn.prepareStatement(sql);
        ResultSet rs=this.pst.executeQuery();

        while(rs.next()){

            UserInfo vo=new UserInfo();
            vo.setId(rs.getInt(1));
            vo.setName(rs.getString(2));
            vo.setPassword(rs.getString(3));
            vo.setScore(rs.getInt(4));
            all.add(vo);
        }
        System.out.println(all.size());
        return all;
    }


    @Override
    public List<UserInfo> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws Exception {
        List<UserInfo> all=new ArrayList<UserInfo>();
        String sql="select * from "+" (select id,user_name,password,score"+" from userinfo where "+
                column+" like ? and rownum<=?) temp"+" where temp.rn>?";
        this.pst=this.conn.prepareStatement(sql);
        this.pst.setString(1,"%"+keyWord+"%");
        this.pst.setInt(2,currentPage*lineSize);
        this.pst.setInt(3,(currentPage-1)*lineSize);
        ResultSet rs=this.pst.executeQuery();
        while(rs.next()){
            UserInfo vo=new UserInfo();
            vo.setId(rs.getInt(1));
            vo.setName(rs.getString(2));
            vo.setPassword(rs.getString(3));
            vo.setScore(rs.getInt(4));
            all.add(vo);
        }
        return all;
    }

    @Override
    public boolean doUpdate(UserInfo vo) throws Exception {
        String sql="update userinfo set id=?,user_name=?,password=?,score=? where id=?";
        this.pst=conn.prepareStatement(sql);
        this.pst.setInt(1,vo.getId());
        this.pst.setString(2,vo.getName());
        this.pst.setString(3, vo.getPassword());
        this.pst.setInt(4,vo.getScore());
        this.pst.setInt(5,vo.getId());
        return this.pst.executeUpdate()>0;
    }
    public void remove(String r) throws Exception{
        String sql="delete from userinfo where user_name=?";
        pst.setString(1,r);
        pst.executeUpdate();
    }
    @Override

    public boolean doRemove(Set<String> ids) throws Exception {
        StringBuffer sql=new StringBuffer();
        sql.append("delete from userinfo where user_name in(");
        Iterator<String> iter =ids.iterator();
        while(iter.hasNext()) {
            sql.append(iter.next()).append(",");
        }
            sql.delete(sql.length()-1,sql.length());
            sql.append(")");
            this.pst=this.conn.prepareStatement(sql.toString());
            return this.pst.executeUpdate()==ids.size();

    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws Exception {
        String sql="select count(*) from userinfo where "+column+" like ?";
        this.pst=this.conn.prepareStatement(sql);
        this.pst.setString(1,"%"+keyWord+"%");
        ResultSet rs=this.pst.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return null;
    }
}
