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
 * @Description: 分组关系表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Data
@TableName("yd_group_person")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="yd_group_person对象", description="分组关系表")
public class YdGroupPerson {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**群组id*/
	@Excel(name = "群组id", width = 15)
    @ApiModelProperty(value = "群组id")
	private java.lang.String groupId;
	/**学生id*/
	@Excel(name = "学生id", width = 15)
    @ApiModelProperty(value = "学生id")
	private java.lang.String stuId;
	/**0学生 1导师 2审阅*/
	@Excel(name = "0学生 1导师 2审阅", width = 15)
    @ApiModelProperty(value = "0学生 1导师 2审阅")
	private java.lang.String type;
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
