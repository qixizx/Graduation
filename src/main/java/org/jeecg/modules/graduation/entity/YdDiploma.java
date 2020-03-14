package org.jeecg.modules.graduation.entity;

import java.io.Serializable;
import java.util.Date;
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
 * @Description: 毕业设计表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Data
@TableName("yd_diploma")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="yd_diploma对象", description="毕业设计表")
public class YdDiploma {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**选题id*/
	@Excel(name = "选题id", width = 15)
    @ApiModelProperty(value = "选题id")
	private java.lang.String subjectId;
	/**开题报告id*/
	@Excel(name = "开题报告id", width = 15)
    @ApiModelProperty(value = "开题报告id")
	private java.lang.String reportId;
	/**任务书id*/
	@Excel(name = "任务书id", width = 15)
    @ApiModelProperty(value = "任务书id")
	private java.lang.String assignmentId;
	/**论文id*/
	@Excel(name = "论文id", width = 15)
    @ApiModelProperty(value = "论文id")
	private java.lang.String thesisId;
	/**学生id*/
	@Excel(name = "学生id", width = 15)
    @ApiModelProperty(value = "学生id")
	private java.lang.String userId;
	/**成绩*/
	@Excel(name = "成绩", width = 15)
    @ApiModelProperty(value = "成绩")
	private java.lang.String grade;
	/**0未通过 1 已通过 2优秀生*/
	@Excel(name = "0未通过 1 已通过 2优秀生", width = 15)
    @ApiModelProperty(value = "0未通过 1 已通过 2优秀生")
	private java.lang.String isPass;
	/**添加人*/
	@Excel(name = "添加人", width = 15)
    @ApiModelProperty(value = "添加人")
	private java.lang.String createBy;
	/**添加时间*/
	@Excel(name = "添加时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
	private java.util.Date createTime;
	/**修改人*/
	@Excel(name = "修改人", width = 15)
    @ApiModelProperty(value = "修改人")
	private java.lang.String updateBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
	private java.util.Date updateTime;
	/**删除状态（0，正常，1已删除）*/
	@Excel(name = "删除状态（0，正常，1已删除）", width = 15)
    @ApiModelProperty(value = "删除状态（0，正常，1已删除）")
	@TableLogic(value="0",delval = "1")
	private java.lang.String delFlag;
}
