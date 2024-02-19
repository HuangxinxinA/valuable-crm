package org.jeecg.modules.valuable.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ISVexcel文档
 */
@Data
public class ISVDTO {
    /**
     * 平台账号
     */
    @Excel(name = "admin_mbr_id", width = 15)
    private String account;
    /**
     * 公司名称
     */
    @Excel(name = "公司名", width = 15)
    private String name;
    /**
     * 一级行业
     */
    @Excel(name = "一级行业EN", width = 15)
    private String firstIndustry;
    /**
     * 二级行业
     */
    @Excel(name = "二级行业EN", width = 15)
    private String secondIndustry;
    /**
     * 三级行业
     */
    @Excel(name = "三级行业EN", width = 15)
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
    @Excel(name = "电话", width = 15)
    private String phone;
    /**
     * 地址
     */
    @Excel(name = "地址", width = 15)
    private String address;
    /**
     * 区域
     */
    @Excel(name = "新区域", width = 15)
    private String area;
    /**
     * 渠道类型
     */
    @Excel(name = "渠道类型", width = 15)
    private String channelType;
    /**
     * 渠道名称
     */
    @Excel(name = "渠道公司", width = 15)
    private String channelName;
    /**
     * 阿里会员服务类型：金品/出口通
     */
    @Excel(name = "是否金品", width = 15)
    private String serviceType;

    public String getServiceType() {
        if ("Y".equals(serviceType)) {
            return "金品";
        } else if ("N".equals(serviceType)) {
            return "出口通";
        }
        return "";
    }

    /**
     * 合同开始时间
     */
    @Excel(name = "当前合同的服务开始时间", width = 15, format = "yyyy/MM/dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractStart;
    /**
     * 合同结束时间
     */
    @Excel(name = "当前合同到期日期", width = 15, format = "yyyy/MM/dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractEnd;
    /**
     * 年投入金额
     */
    @Excel(name = "一年方案金额投入", width = 15)
    private BigDecimal annualAmount;
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