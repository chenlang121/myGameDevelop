package cn.itcast.service.impl;

import cn.itcast.dbc.DatabaseConnection;
import cn.itcast.factory.DAOFactory;
import cn.itcast.service.IUserInfoService;
import cn.itcast.vo.userinfo.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserInfoIServiceImpl implements IUserInfoService {
    private DatabaseConnection dbc=new DatabaseConnection();
    @Override
    public boolean insert(UserInfo vo) throws Exception {
        try {
            if (DAOFactory.getIUerInfoDAOInstance(this.dbc.getConnection()).findById(vo.getName()) == null) {
                return DAOFactory.getIUerInfoDAOInstance(this.dbc.getConnection()).doCreate(vo);
            }
            return false;
        }catch (Exception e){
            throw e;
        }finally {
            this.dbc.close();
        }
    }

    @Override
    public boolean update(UserInfo vo) throws Exception {
        try{
            return DAOFactory.getIUerInfoDAOInstance(this.dbc.getConnection()).doUpdate(vo);
        }catch(Exception e){
            throw e;
        }finally {
            this.dbc.close();
        }

    }

    @Override
    public boolean delete(Set<String> ids) throws Exception {
        try {
            return DAOFactory.getIUerInfoDAOInstance(this.dbc.getConnection()).doRemove(ids);
        }catch (Exception e){
            throw e;
        }finally {
            this.dbc.close();
        }
    }

    @Override
    public UserInfo get(String name) throws Exception {
        try{
            return DAOFactory.getIUerInfoDAOInstance(this.dbc.getConnection()).findById(name);
        }catch (Exception e){
            throw e;
        }finally {
            this.dbc.close();
        }
    }

    @Override
    public List<UserInfo> list() throws Exception {
        try{
            return DAOFactory.getIUerInfoDAOInstance(this.dbc.getConnection()).findAll();
        }catch (Exception e){
            throw e;
        }finally {
            this.dbc.close();
        }

    }

    @Override
    public Map<String, Object> listSplit(String column, String keyWord, int currentPage, int lineSize) throws Exception {
        try {
           Map<String,Object> map=new HashMap<String,Object>();
           map.put("allUsers",DAOFactory.getIUerInfoDAOInstance(this.dbc.getConnection()).findAllSplit(column,keyWord,currentPage,lineSize));
           map.put("userCount",DAOFactory.getIUerInfoDAOInstance(this.dbc.getConnection()).getAllCount(column,keyWord));
           return map;
        }catch (Exception e){
            throw e;
        }finally {
            this.dbc.close();
        }
    }
}
