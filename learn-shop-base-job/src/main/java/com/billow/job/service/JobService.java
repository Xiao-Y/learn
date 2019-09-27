package com.billow.job.service;

import com.billow.job.pojo.ex.MailEx;

public interface JobService {

    void sendMail(MailEx mailEx);
}
