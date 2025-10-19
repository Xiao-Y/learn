package com.billow.system.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.mybatisflex.annotation.Table;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mybatisflex.annotation.Column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("sys_white_list")
@Schema(title = "WhiteListPo对象", description="")
public class WhiteListPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Column("ip")
    private String ip;

    @Column("mark")
    private String mark;

    @Column("module")
    private String module;

    @Column("port")
    private String port;


}
