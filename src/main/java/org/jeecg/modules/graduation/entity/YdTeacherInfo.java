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
 * @Description: 老师信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Data
@TableName("yd_teacher_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="yd_teacher_info对象", description="老师信息表")
public class YdTeacherInfo {
    
	/**工号*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "工号")
	private java.lang.String id;
	/**真实姓名*/
	@Excel(name = "真实姓名", width = 15)
    @ApiModelProperty(value = "真实姓名")
	private java.lang.String name;
	/**学号*/
	@Excel(name = "学号", width = 15)
    @ApiModelProperty(value = "学号")
	private java.lang.String stuNumber;
	/**性别*/
	@Excel(name = "性别", width = 15)
    @ApiModelProperty(value = "性别")
	private java.lang.String sex;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
    @ApiModelProperty(value = "邮箱")
	private java.lang.String mail;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
	private java.lang.String phone;
	/**所属院系id*/
	@Excel(name = "所属院系id", width = 15)
    @ApiModelProperty(value = "所属院系id")
	private java.lang.String facultyId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
	private java.lang.String userId;
	/**老师0    教务人员1*/
	@Excel(name = "老师0    教务人员1", width = 15)
    @ApiModelProperty(value = "老师0    教务人员1")
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
