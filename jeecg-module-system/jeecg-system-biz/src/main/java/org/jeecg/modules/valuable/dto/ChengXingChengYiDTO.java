package org.jeecg.modules.valuable.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 橙星橙意excel
 */
@Data
public class ChengXingChengYiDTO {
    /**
     * 平台账号
     */
    @Excel(name = "Id", width = 15)
    private String account;
    /**
     * 公司名称
     */
    @Excel(name = "公司名", width = 15)
    private String name;
    /**
     * 一级行业
     */
    @Excel(name = "一级行业", width = 15)
    private String firstIndustry;
    /**
     * 二级行业
     */
    @Excel(name = "二级行业", width = 15)
    private String secondIndustry;
    /**
     * 三级行业
     */
    @Excel(name = "三级行业", width = 15)
    private String thirdIndustry;
    /**
     * 国际站网址
     */
    @Excel(name = "国际站网址", width = 15)
    private String siteUrl;
    /**
     * 工贸类型
     */
    @Excel(name = "工贸类型", width = 15)
    private String industryTradeType;
    /**
     * 联系人
     */
    @Excel(name = "联系人", width = 15)
    private String contact;
    /**
     * 联系电话
     */
    @Excel(name = "联系电话", width = 15)
    private String phone;
    /**
     * 地址
     */
    @Excel(name = "地址", width = 15)
    private String address;
    /**
     * 区域
     */
    @Excel(name = "区域", width = 15)
    private String area;
    /**
     * 渠道类型
     */
    @Excel(name = "渠道类型", width = 15)
    private String channelType;
    /**
     * 渠道名称
     */
    @Excel(name = "渠道名称", width = 15)
    private String channelName;
    /**
     * 阿里会员服务类型：金品/出口通
     */
    @Excel(name = "类型", width = 15)
    private String serviceType;
    /**
     * 合同开始时间
     */
    @Excel(name = "第一份gs合同的服务开始时间", width = 15, format = "yyyy/MM/dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractStart;
    /**
     * 合同结束时间
     */
    @Excel(name = "合同结束时间", width = 15, format = "yyyy/MM/dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractEnd;
    /**
     * 年投入金额
     */
    @Excel(name = "近12月平均消耗", width = 15)
    private BigDecimal monthAmount;

    @Excel(name = "年投入金额", width = 15)
    private BigDecimal annualAmount;

    /**
     * 表格读取的是月平均，要乘12
     */
    public BigDecimal getAnnualAmount() {
        return monthAmount.multiply(new BigDecimal(12));
    }

    /**
     * 客户类型
     */
    @Excel(name = "客户类型", width = 15)
    private String clientType;
    /**
     * 业务类型
     */
    @Excel(name = "业务类型", width = 15)
    private String businessType;
}