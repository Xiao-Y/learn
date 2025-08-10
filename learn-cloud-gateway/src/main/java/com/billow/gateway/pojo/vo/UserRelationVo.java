package com.billow.gateway.pojo.vo;

import com.billow.gateway.pojo.po.RolePo;
import com.billow.gateway.pojo.po.UserPo;
import com.billow.gateway.pojo.po.UserRolePo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRelationVo implements Serializable {

    private UserPo userPo;

    List<UserRolePo> userRolePoList;

    List<RolePo> rolePoList;

}
