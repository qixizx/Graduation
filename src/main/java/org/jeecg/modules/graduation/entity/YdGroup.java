package org.jeecg.modules.graduation.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 分组表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Data
@TableName("yd_group")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="yd_group对象", description="分组表")
public class YdGroup {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**群组名*/
	@Excel(name = "群组名", width = 15)
    @ApiModelProperty(value = "群组名")
	private java.lang.String gname;
	/**人数*/
	@Excel(name = "人数", width = 15)
    @ApiModelProperty(value = "人数")
	private java.lang.Integer number;
	/**所属班级*/
	@Excel(name = "所属班级", width = 15)
    @ApiModelProperty(value = "所属班级")
	private java.lang.String classId;
	/**导师id*/
	@Excel(name = "导师id", width = 15)
    @ApiModelProperty(value = "导师id")
	private java.lang.String tutorId;
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
//	@Excel(name = "删除状态（0，正常，1已删除）", width = 15)
    @ApiModelProperty(value = "删除状态（0，正常，1已删除）")
	@TableLogic(value="0",delval = "1")
	private java.lang.String delFlag;
    
	/** 班级名称 */
	@TableField(exist = false)
	private java.lang.String className;
	/** 专业id */
	@TableField(exist = false)
	private java.lang.String majorId;
	/** 院系id */
	@TableField(exist = false)
	private java.lang.String facultyId;
	/** 筛选条件 */ 
	@TableField(exist = false)
	private List<java.lang.String> labels;
	/** 筛选条件 */ 
	@TableField(exist = false)
	private java.lang.String trealname;
	
}
