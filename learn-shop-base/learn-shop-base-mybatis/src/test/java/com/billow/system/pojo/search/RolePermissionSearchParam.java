package com.billow.system.pojo.search;

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
 * @since 2022-01-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RolePermissionSearchParam extends BasePage implements Serializable {

}
