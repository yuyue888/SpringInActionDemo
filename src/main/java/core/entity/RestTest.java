package core.entity;

import java.util.Date;

/**
 * Created by ssc on 2017/6/11.
 */
public class RestTest {

    private String name;

    private int age;

    private Date date = new Date();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
