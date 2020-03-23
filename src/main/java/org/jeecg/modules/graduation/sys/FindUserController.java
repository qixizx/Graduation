package org.jeecg.modules.graduation.sys;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.graduation.controller.YdTeacherInfoController;
import org.jeecg.modules.graduation.entity.YdStudentInfo;
import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.service.IYdStudentInfoService;
import org.jeecg.modules.graduation.service.IYdTeacherInfoService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @Author zx
 * @since 2018-12-20
 */
@Slf4j
@Api(tags="个人资料")
@RestController
@RequestMapping("/graduation/findInfo")
public class FindUserController {
	@Autowired
	private IYdTeacherInfoService ydTeacherInfoService;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private IYdStudentInfoService ydStudentInfoService;
	/**
	 *	个人查询改接口
	 *
	 * @return
	 */
	@AutoLog(value = "个人资料查询接口")
	@ApiOperation(value="人资料查询接口", notes="人资料查询接口")
	@GetMapping(value = "/getUserInfo")
	public Result<JSONObject> getUserInfo() {
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject json=new JSONObject();
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		List<String> list = sysUserService.getRole(sysUser.getUsername());
		  // 判断是否是老师或者教务人员 只查自己院系的信息 
		if (list.contains("academic") ||list.contains("teacher")) { 
			YdTeacherInfo ydTeacherInfo =ydTeacherInfoService.findTeacherInfo(sysUser.getUsername());
			json = (JSONObject) JSONObject.toJSON(ydTeacherInfo);
		}else if(list.contains("student")) {
			YdStudentInfo ydStudentInfo = ydStudentInfoService.findStudentInfo(sysUser.getUsername());
			json = (JSONObject) JSONObject.toJSON(ydStudentInfo);
		}else if(list.contains("admin")) {
			SysUser user = sysUserService.getUserByName(sysUser.getUsername());
			json = (JSONObject) JSONObject.toJSON(user);
		}
		result.setResult(json);
		result.setMessage("查询成功");
		result.setSuccess(true);
		return result;
	}
	
	/**
	 *	个人修改改接口
	 *
	 * @return
	 */
	@AutoLog(value = "个人修改改接口")
	@ApiOperation(value="个人修改改接口", notes="个人修改改接口")
	@PutMapping(value = "/editUserInfo")
	public Result<JSONObject> editUserInfo(@RequestBody JSONObject jsonObject) {
		Result<JSONObject> result = new Result<JSONObject>();
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		List<String> list = sysUserService.getRole(sysUser.getUsername());
		  // 判断是否是老师或者教务人员 只查自己院系的信息 
		if (list.contains("academic") ||list.contains("teacher")) { 
			YdTeacherInfo ydTeacherInfo=JSON.parseObject(jsonObject.toJSONString(), YdTeacherInfo.class);
			YdTeacherInfo ydTeacherInfoEntity = ydTeacherInfoService.getById(ydTeacherInfo.getId());
			if(ydTeacherInfoEntity==null) {
				result.error500("未找到对应实体");
			}else {
				SysUser user = JSON.parseObject(jsonObject.toJSONString(), SysUser.class);
				user.setId(ydTeacherInfoEntity.getUserId());
				//查找用户实体
				SysUser sysUserEntity = sysUserService.getById(user.getId());
				if(sysUserEntity==null) {
					result.error500("未找到对应实体");
				}else {
					sysUserService.updateById(user);
				}
				boolean ok = ydTeacherInfoService.updateById(ydTeacherInfo);
				//TODO 返回false说明什么？
				if(ok) {
					result.setMessage("修改成功");
					result.setSuccess(true);
				}
			}	
		}else if(list.contains("student")) {
			YdStudentInfo ydStudentInfo=JSON.parseObject(jsonObject.toJSONString(), YdStudentInfo.class);
			YdStudentInfo ydStudentInfoEntity = ydStudentInfoService.getById(ydStudentInfo.getId());
			if(ydStudentInfoEntity==null) {
				result.error500("未找到对应实体");
			}else {
				SysUser user = JSON.parseObject(jsonObject.toJSONString(), SysUser.class);
				user.setId(ydStudentInfoEntity.getUserId());
				//查找用户实体
				SysUser sysUserEntity = sysUserService.getById(user.getId());
				if(sysUserEntity==null) {
					result.error500("未找到对应实体");
				}else {
					sysUserService.updateById(user);
				}
				boolean ok = ydStudentInfoService.updateById(ydStudentInfo);
				//TODO 返回false说明什么？
				if(ok) {
					result.setMessage("修改成功");
					result.setSuccess(true);
				}
			}
		}else if(list.contains("admin")) {
			SysUser user = JSON.parseObject(jsonObject.toJSONString(), SysUser.class);
			user.setUpdateTime(new Date());
			boolean ok = sysUserService.updateById(user);
			//TODO 返回false说明什么？
			if(ok) {
				result.setMessage("修改成功");
				result.setSuccess(true);
			}
		}
		return result;
	}
}
