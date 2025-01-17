package com.px.unimall.data.domain.market;

import com.px.unimall.data.domain.SuperDO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.px.unimall.core.framework.aspectj.lang.annotaion.Excel;
import com.px.unimall.core.framework.aspectj.lang.annotaion.Excel.ColumnType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.px.unimall.core.util.StringUtils;
import java.util.Date;

/**
 * 广告信息对象 market_advertisement_info
 * 
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("market_advertisement_info")
public class MarketAdvertisementInfo extends SuperDO {
    private static final long serialVersionUID = 1L;

    /**  */
    @TableId("id")
    private Long id;

    /**  */
    @Excel(name = "")
    @TableField("company_id")
    private Long companyId;

    /** 广告名称 */
    @Excel(name = "广告名称")
    @TableField("advertisement_name")
    private String advertisementName;

    /** 图片 */
    @Excel(name = "图片")
    @TableField("url")
    private String url;

    /** 状态 */
    @Excel(name = "状态")
    @TableField("status")
    private String status;

    /** 0：否（默认） 1：是 */
    @Excel(name = "0：否", readConverterExp = "默=认")
    @TableField("delete_flag")
    private Integer deleteFlag;

    /**  */
    @Excel(name = "", width = 30, dateFormat = "yyyy-MM-dd")
    @TableField("gmt_create")
    private Date gmtCreate;

    /**  */
    @Excel(name = "", width = 30, dateFormat = "yyyy-MM-dd")
    @TableField("gmt_update")
    private Date gmtUpdate;

    /**  */
    @Excel(name = "")
    @TableField("user_id")
    private Long userId;

    /**  */
    @Excel(name = "")
    @TableField("admin_id")
    private Long adminId;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("advertisementName", getAdvertisementName())
            .append("url", getUrl())
            .append("status", getStatus())
            .append("deleteFlag", getDeleteFlag())
            .append("gmtCreate", getGmtCreate())
            .append("gmtUpdate", getGmtUpdate())
            .append("userId", getUserId())
            .append("adminId", getAdminId())
            .toString();
    }
}
