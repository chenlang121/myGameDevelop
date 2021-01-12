package cn.itcast.factory;

import cn.itcast.service.IUserInfoService;
import cn.itcast.service.impl.UserInfoIServiceImpl;

public class ServiceFactory {
    public static IUserInfoService getIUserInfoServiceInstance(){
        return new UserInfoIServiceImpl();
    }
}
