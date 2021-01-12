package cn.itcast.vo.userinfo;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private Integer score;
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getScore() {
        return score;
    }
}
