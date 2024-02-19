package org.jeecg.modules.valuable.entity;

import java.io.Serializable;
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
 * @Description: 阿里客户表
 * @Author: jeecg-boot
 * @Date: 2024-01-19
 * @Version: V1.0
 */
@Data
@TableName("val_alibaba_client")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "val_alibaba_client对象", description = "阿里客户表")
public class ValAlibabaClient implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 平台账号
     */
    @Excel(name = "平台账号", width = 15)
    @ApiModelProperty(value = "平台账号")
    private String account;
    /**
     * 公司名称
     */
    @Excel(name = "公司名称", width = 15)
    @ApiModelProperty(value = "公司名称")
    private String name;
    /**
     * 一级行业
     */
    @Excel(name = "一级行业", width = 15)
    @ApiModelProperty(value = "一级行业")
    private String firstIndustry;
    /**
     * 二级行业
     */
    @Excel(name = "二级行业", width = 15)
    @ApiModelProperty(value = "二级行业")
    private String secondIndustry;
    /**
     * 三级行业
     */
    @Excel(name = "三级行业", width = 15)
    @ApiModelProperty(value = "三级行业")
    private String thirdIndustry;
    /**
     * 国际站网址
     */
    @Excel(name = "国际站网址", width = 15)
    @ApiModelProperty(value = "国际站网址")
    private String siteUrl;
    /**
     * 工贸类型
     */
    @Excel(name = "工贸类型", width = 15)
    @ApiModelProperty(value = "工贸类型")
    private String industryTradeType;
    /**
     * 联系人
     */
    @Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private String contact;
    /**
     * 联系电话
     */
    @Excel(name = "联系电话", width = 15)
    @ApiModelProperty(value = "联系电话")
    private String phone;
    /**
     * 地址
     */
    @Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 区域
     */
    @Excel(name = "区域", width = 15)
    @ApiModelProperty(value = "区域")
    private String area;
    /**
     * 渠道类型
     */
    @Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private String channelType;
    /**
     * 渠道名称
     */
    @Excel(name = "渠道名称", width = 15)
    @ApiModelProperty(value = "渠道名称")
    private String channelName;
    /**
     * 阿里会员服务类型：金品/出口通
     */
    @Excel(name = "阿里会员服务类型：金品/出口通", width = 15)
    @ApiModelProperty(value = "阿里会员服务类型：金品/出口通")
    private String serviceType;
    /**
     * 合同开始时间
     */
    @Excel(name = "合同开始时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "合同开始时间")
    private Date contractStart;
    /**
     * 合同结束时间
     */
    @Excel(name = "合同结束时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "合同结束时间")
    private Date contractEnd;
    /**
     * 年投入金额
     */
    @Excel(name = "年投入金额", width = 15)
    @ApiModelProperty(value = "年投入金额")
    private BigDecimal annualAmount;
    /**
     * 客户经理
     */
    @Excel(name = "客户经理", width = 15)
    @ApiModelProperty(value = "客户经理")
    private String manager;
    /**
     * 运营
     */
    @Excel(name = "运营", width = 15)
    @ApiModelProperty(value = "运营")
    private String operator;
    /**
     * 客户类型
     */
    @Excel(name = "客户类型", width = 15)
    @ApiModelProperty(value = "客户类型")
    private String clientType;
    /**
     * 业务类型
     */
    @Excel(name = "业务类型", width = 15)
    @ApiModelProperty(value = "业务类型")
    private String businessType;
    /**
     * 是否有人跟进，0-否，1-是
     */
    @Excel(name = "是否有人跟进，0-否，1-是", width = 15)
    @ApiModelProperty(value = "是否有人跟进，0-否，1-是")
    private Integer isFollow;
    /**
     * 是否为公海客户，0-否，1-是
     */
    @Excel(name = "是否为公海客户，0-否，1-是", width = 15)
    @ApiModelProperty(value = "是否为公海客户，0-否，1-是")
    private Integer isPublic;
    /**
     * 是否为业务员添加，0-否，1-是
     */
    @Excel(name = "是否为业务员添加，0-否，1-是", width = 15)
    @ApiModelProperty(value = "是否为业务员添加，0-否，1-是")
    private Integer isPrivate;
    /**
     * 逻辑删除，0-否，1-是
     */
    @Excel(name = "逻辑删除，0-否，1-是", width = 15)
    @ApiModelProperty(value = "逻辑删除，0-否，1-是")
    @TableLogic
    private Integer delFlag;
}
