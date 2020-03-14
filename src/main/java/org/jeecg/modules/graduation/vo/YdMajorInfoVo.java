package org.jeecg.modules.graduation.vo;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YdMajorInfoVo {
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**专业名称*/
	@Excel(name = "专业名称", width = 15)
    @ApiModelProperty(value = "专业名称")
	private java.lang.String major;
	/**所属院系*/
	@Excel(name = "所属院系", width = 15)
    @ApiModelProperty(value = "所属院系")
	private java.lang.String facultyId;
	/**专业描述*/
	@Excel(name = "专业描述", width = 15)
    @ApiModelProperty(value = "专业描述")
	private java.lang.String recommend;
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
	
	/**院系名称*/
	@Excel(name = "院系名称", width = 15)
    @ApiModelProperty(value = "院系名称")
	private java.lang.String faculty;
}
