package com.billow.user.pojo.po;


import com.billow.common.base.pojo.BasePo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 测试用
 *
 * @author liuyongtao
 * @create 2018-02-11 9:39
 */
@Entity
@Table(name = "t_test")
public class TestPo extends BasePo implements Serializable {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public TestPo setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public TestPo setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "TestPo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                "} " + super.toString();
    }
}
