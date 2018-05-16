package com.billow.pojo.po;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_user")
public class UserPo extends BasePo implements Serializable {
}
