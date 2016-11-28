package org.keega.idea.model;

import org.keega.idea.util.dao.annotation.SqlColumn;
import org.keega.idea.util.dao.annotation.SqlPrimaryKey;
import org.keega.idea.util.dao.annotation.SqlTableName;


@SqlTableName("t_user")
public class User {

    @SqlPrimaryKey
    private int id;
    @SqlColumn
    private String username;
    @SqlColumn
    private String nickname;
    @SqlColumn
    private String password;
    @SqlColumn
    private String born;
    @SqlColumn
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", born='" + born + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
