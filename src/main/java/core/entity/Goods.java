package core.entity;

import java.io.Serializable;

/**
 * Created by ssc on 2017/7/12.
 */
public class Goods implements Serializable{
    private Integer gid;

    private String gname;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }
}
