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
@Table("sys_apply_info")
@Schema(title = "ApplyInfoPo对象", description = "")
public class ApplyInfoPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Column("apply_data")
    private String applyData;

    @Column("apply_type")
    private String applyType;

    @Column("apply_user_code")
    private String applyUserCode;

    @Column("is_end")
    private Boolean isEnd;

    @Column("proc_def_id")
    private String procDefId;

    @Column("proc_inst_id")
    private String procInstId;

    @Column("vo_clazz")
    private String voClazz;


}
