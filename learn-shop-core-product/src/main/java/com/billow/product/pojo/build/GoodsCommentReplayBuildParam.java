package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 产品评价回复表 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsCommentReplayBuildParam extends BasePo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    private Long commentId;

    @ApiModelProperty(value = "会员昵称")
    private String memberNickName;

    @ApiModelProperty(value = "会员头像")
    private String memberIcon;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "评论人员类型；0->会员；1->管理员")
    private Integer type;


}
