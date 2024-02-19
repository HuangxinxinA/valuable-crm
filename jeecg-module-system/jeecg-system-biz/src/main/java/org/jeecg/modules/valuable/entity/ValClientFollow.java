package org.jeecg.modules.valuable.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 客户跟进表
 * @Author: jeecg-boot
 * @Date:   2024-02-18
 * @Version: V1.0
 */
@Data
@TableName("val_client_follow")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="val_client_follow对象", description="客户跟进表")
public class ValClientFollow implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**逻辑删除，0-否，1-是*/
	@Excel(name = "逻辑删除，0-否，1-是", width = 15)
    @ApiModelProperty(value = "逻辑删除，0-否，1-是")
    @TableLogic
    private Integer delFlag;
	/**客户类型，1-阿里，2-谷歌*/
	@Excel(name = "客户类型，1-阿里，2-谷歌", width = 15)
    @ApiModelProperty(value = "客户类型，1-阿里，2-谷歌")
    private Integer type;
	/**客户ID*/
	@Excel(name = "客户ID", width = 15)
    @ApiModelProperty(value = "客户ID")
    private String clientId;
	/**客户经理ID*/
	@Excel(name = "客户经理ID", width = 15)
    @ApiModelProperty(value = "客户经理ID")
    private String managerId;
	/**跟进状态，0-待跟进，1-跟进中，2-未成交，3-已成交，4-关闭*/
	@Excel(name = "跟进状态，0-待跟进，1-跟进中，2-未成交，3-已成交，4-关闭", width = 15)
    @ApiModelProperty(value = "跟进状态，0-待跟进，1-跟进中，2-未成交，3-已成交，4-关闭")
    private Integer status;
	/**跟进内容*/
	@Excel(name = "跟进内容", width = 15)
    @ApiModelProperty(value = "跟进内容")
    private String content;
}
