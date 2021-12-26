package com.billow.system.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单权限 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-12-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MenuPermissionSearchParam extends BasePage implements Serializable {

}
