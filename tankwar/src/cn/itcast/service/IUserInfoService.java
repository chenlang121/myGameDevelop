package cn.itcast.service;

import cn.itcast.vo.userinfo.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IUserInfoService {
    public boolean insert(UserInfo vo) throws Exception;
    public boolean update(UserInfo vo) throws Exception;
    public boolean delete(Set<String> ids) throws Exception;
    public UserInfo get(String id) throws Exception;
    public List<UserInfo> list() throws Exception;
    public Map<String,Object> listSplit(String column,String keyWord,int currentPage,int lineSize) throws Exception;

}
