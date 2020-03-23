package org.jeecg.modules.graduation.entity;

import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 开题报告表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Data
@TableName("yd_report")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="yd_report对象", description="开题报告表")
public class YdReport {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**论文名称*/
	@Excel(name = "论文名称", width = 15)
    @ApiModelProperty(value = "论文名称")
	private java.lang.String reportName;
	/**开题报告描述*/
	@Excel(name = "开题报告描述", width = 15)
    @ApiModelProperty(value = "开题报告描述")
	private java.lang.String recommend;
	/**开题报告文件id*/
	@Excel(name = "开题报告文件id", width = 15)
    @ApiModelProperty(value = "开题报告文件id")
	private java.lang.String fileId;
	/**未提交0 已提交1  未通过 2 已通过  3*/
	@Excel(name = "状态（提交状态）", width = 15)
    @ApiModelProperty(value = "未提交0 已提交1  未通过 2 已通过  3")
	@Dict(dicCode = "bs_state")
	private java.lang.String state;
	/**提交时间*/
	@Excel(name = "提交时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "提交时间")
	private java.util.Date commitTime;
	/**学生id*/
	@Excel(name = "学生id", width = 15)
    @ApiModelProperty(value = "学生id")
	private java.lang.String stuId;
	/**选题id*/
	@Excel(name = "选题id", width = 15)
    @ApiModelProperty(value = "选题id")
	private java.lang.String subjectId;
	/**添加人*/
    @ApiModelProperty(value = "添加人")
	private java.lang.String createBy;
	/**添加时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
	private java.util.Date createTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
	private java.lang.String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
	private java.util.Date updateTime;
	/**删除状态（0，正常，1已删除）*/
	@Excel(name = "删除状态（0，正常，1已删除）", width = 15)
    @ApiModelProperty(value = "删除状态（0，正常，1已删除）")
	@TableLogic(value="0",delval = "1")
	private java.lang.String delFlag;
	
	
	
	/**开题报告路径*/
	@TableField(exist = false)
	private java.lang.String filePath;
	
	/**开题报告名称*/
	@TableField(exist = false)
	private java.lang.String fileName;
	
	/** 老师名字*/
	@TableField(exist = false)
	private java.lang.String tname;
	/** 学生名字 */
	@TableField(exist = false)
	private java.lang.String sname;
	/** 学生账号 */
	@TableField(exist = false)
	private java.lang.String suname;
}
