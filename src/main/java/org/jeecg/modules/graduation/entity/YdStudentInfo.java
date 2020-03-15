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
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date: 2020-03-13
 * @Version: V1.0
 */
@Data
@TableName("yd_student_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "yd_student_info对象", description = "学生信息表")
public class YdStudentInfo {

	/** id */
	@TableId(type = IdType.UUID)
	@ApiModelProperty(value = "id")
	private java.lang.String id;
	/** 学号 */
	@Excel(name = "登录账号", width = 15)
	@ApiModelProperty(value = "登录账号")
	private java.lang.String username;
	/** 真实姓名 */
	@Excel(name = "真实姓名", width = 15)
	@ApiModelProperty(value = "真实姓名")
	private java.lang.String realname;

	/**
	 * 性别（1：男 2：女）
	 */
	@ApiModelProperty(value = "性别")
	@Excel(name = "性别", width = 15, dicCode = "sex")
	@Dict(dicCode = "sex")
	private java.lang.String sex;
	/** 邮箱 */
	@Excel(name = "邮箱", width = 15)
	@ApiModelProperty(value = "邮箱")
	private java.lang.String email;
	/** 手机号 */
	@Excel(name = "手机号", width = 15)
	@ApiModelProperty(value = "手机号")
	private java.lang.String phone;
	/** 所属班级 id */
//	  此处dictTable为数据库表名，dicCode为关联字段名，dicText为excel中显示的内容对应的字段
	@Excel(name = "所属班级", width = 15, dictTable = "yd_class_info", dicCode = "id", dicText = "class_name")
	@ApiModelProperty(value = "所属班级")
	private java.lang.String classId;

	/** 用户id */
//	@Excel(name = "用户", width = 15)
	@ApiModelProperty(value = "用户")
	private java.lang.String userId;
	/** 添加人 */
	@ApiModelProperty(value = "添加人")
	private java.lang.String createBy;
	/** 添加时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "添加时间")
	private java.util.Date createTime;
	/** 修改人 */
	@ApiModelProperty(value = "修改人")
	private java.lang.String updateBy;
	/** 修改时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "修改时间")
	private java.util.Date updateTime;
	/** 删除状态（0，正常，1已删除） */
//	@Excel(name = "删除状态（0，正常，1已删除）", width = 15)
	@ApiModelProperty(value = "删除状态（0，正常，1已删除）")
	@TableLogic(value = "0", delval = "1")
	private java.lang.String delFlag;

	/*
	 * //单一角色时候不需要使用 直接后台固定角色id
	 *//** 角色名称 *//*
					 * // 此处dictTable为数据库表名，dicCode为关联字段名，dicText为excel中显示的内容对应的字段
					 * 
					 * @Excel(name = "角色", width =
					 * 15,dictTable="sys_role",dicCode="id",dicText="role_name")
					 * 
					 * @ApiModelProperty(value = "角色")
					 * 
					 * @TableField(exist=false) private java.lang.String role;
					 */

	/** 班级名称 */
	@TableField(exist = false)
	private java.lang.String className;
}
