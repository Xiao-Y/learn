package com.billow.webflux.user.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-12-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserSearchParam extends BasePage implements Serializable {

}
