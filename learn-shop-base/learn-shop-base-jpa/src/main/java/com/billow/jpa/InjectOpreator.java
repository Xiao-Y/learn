package com.billow.jpa;

//import com.billow.tools.utlis.UserTools;
import com.billow.jpa.utils.JpaUserTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 给Bean中的 @CreatedBy  @LastModifiedBy 注入操作人
 *
 * @author LiuYongTao
 * @date 2019/7/16 14:29
 */
@Configuration
public class InjectOpreator implements AuditorAware<String> {

    @Autowired
    private JpaUserTools jpaUserTools;


    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> optional = Optional.of(jpaUserTools.getCurrentUserCode());
        return optional;
    }
}