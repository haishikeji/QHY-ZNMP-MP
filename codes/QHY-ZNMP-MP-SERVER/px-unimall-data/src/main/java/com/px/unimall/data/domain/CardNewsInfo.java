package com.px.unimall.data.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.px.unimall.core.framework.aspectj.lang.annotaion.Excel;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;
import java.util.List;

/**
 * 记录消息信息对象 card_news_info
 * 
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("card_news_info")
public class CardNewsInfo extends SuperDO {
    private static final long serialVersionUID = 1L;

    /**  */
    @TableId("id")
    private Long id;

    /** 发送消息用户id */
    @Excel(name = "发送消息用户id")
    @TableField("send_id")
    private Long sendId;

    /** 接收消息用户id */
    @Excel(name = "接收消息用户id")
    @TableField("receive_id")
    private Long receiveId;

    /** 消息标题 */
    @Excel(name = "消息标题")
    @TableField("news_title")
    private String newsTitle;

    /** 消息内容 */
    @Excel(name = "消息内容")
    @TableField("news_content")
    private String newsContent;

    /** 消息类型 */
    @Excel(name = "消息类型")
    @TableField("news_type")
    private String newsType;

    /** 已读标识（1已读） */
    @Excel(name = "已读标识", readConverterExp = "1=已读")
    @TableField("news_flag")
    private String newsFlag;

    /**  */
    @Excel(name = "", width = 30, dateFormat = "yyyy-MM-dd")
    @TableField("gmt_create")
    private Date gmtCreate;

    /**  */
    @Excel(name = "", width = 30, dateFormat = "yyyy-MM-dd")
    @TableField("gmt_update")
    private Date gmtUpdate;

    /** 删除标识 */
    @Excel(name = "删除标识")
    @TableField("delete_flag")
    private Long deleteFlag;

    @TableField(exist = false)
    private List<CardNewsInfo> cardNewsInfoList;
    /** 全部发送标识 */
    @TableField(exist = false)
    private String allFlag;
    @TableField(exist = false)
    private String receiveName;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sendId", getSendId())
            .append("receiveId", getReceiveId())
            .append("newsTitle", getNewsTitle())
            .append("newsContent", getNewsContent())
            .append("newsType", getNewsType())
            .append("newsFlag", getNewsFlag())
            .append("gmtCreate", getGmtCreate())
            .append("gmtUpdate", getGmtUpdate())
            .append("deleteFlag", getDeleteFlag())
            .toString();
    }
}
