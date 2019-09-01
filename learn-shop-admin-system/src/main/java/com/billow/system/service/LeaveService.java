package com.billow.system.service;

import com.billow.system.pojo.vo.LeaveVo;

public interface LeaveService {
    void submitLeave(String currentUserCode, LeaveVo leaveVo);
}
