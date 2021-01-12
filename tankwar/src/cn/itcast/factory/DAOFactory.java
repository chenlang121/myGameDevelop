package cn.itcast.factory;

import cn.itcast.dao.impl.UserInfoDAOImpl;
import cn.itcast.vo.userinfo.IUserInfoDAO;

import java.sql.Connection;

public class DAOFactory {
    public static IUserInfoDAO getIUerInfoDAOInstance(Connection conn){
        return new UserInfoDAOImpl(conn);
    }
}
