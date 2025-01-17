package com.px.unimall.data.domain.log;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.px.unimall.core.framework.aspectj.lang.annotaion.Excel;
import com.px.unimall.core.framework.aspectj.lang.annotaion.Excel.ColumnType;

import lombok.Data;

/**
 * 操作日志记录表 oper_log
 * 
 * @author 品讯科技
 */
@Data
@TableName("sys_oper_log")
public class SysOperLog {
	private static final long serialVersionUID = 1L;

	/** 日志主键 */
	@Excel(name = "操作序号", cellType = ColumnType.NUMERIC)
	@TableId("oper_id")
	private Long operId;

	/** 操作模块 */
	@Excel(name = "操作模块")
	@TableField("title")
	private String title;

	/** 业务类型（0其它 1新增 2修改 3删除） */
	@Excel(name = "业务类型")
	@TableField("business_type")
	private String businessType;

	/** 请求方法 */
	@Excel(name = "请求方法")
	@TableField("method")
	private String method;

	/** 请求方式 */
	@Excel(name = "请求方式")
	@TableField("request_method")
	private String requestMethod;

	/** 操作类别（0其它 1后台用户 2手机端用户） */
	@Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
	@TableField("operator_type")
	private Integer operatorType;

	/** 操作人员 */
	@Excel(name = "操作人员")
	@TableField("oper_name")
	private String operName;

	/** 部门名称 */
	@Excel(name = "部门名称")
	@TableField("dept_name")
	private String deptName;

	/** 请求url */
	@Excel(name = "请求地址")
	@TableField("oper_url")
	private String operUrl;

	/** 操作地址 */
	@Excel(name = "操作地址")
	@TableField("oper_ip")
	private String operIp;

	/** 操作地点 */
	@Excel(name = "操作地点")
	@TableField("oper_location")
	private String operLocation;

	/** 请求参数 */
	@Excel(name = "请求参数")
	@TableField("oper_param")
	private String operParam;

	/** 返回参数 */
	@Excel(name = "返回参数")
	@TableField("json_result")
	private String jsonResult;

	/** 操作状态（0正常 1异常） */
	@Excel(name = "状态", readConverterExp = "0=正常,1=异常")
	@TableField("status")
	private Integer status;

	/** 错误消息 */
	@Excel(name = "错误消息")
	@TableField("error_msg")
	private String errorMsg;

	/** 操作时间 */
	@Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@TableField("oper_time")
	private Date operTime;

}
