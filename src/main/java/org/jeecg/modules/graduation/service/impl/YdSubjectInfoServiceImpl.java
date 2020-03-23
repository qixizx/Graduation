package org.jeecg.modules.graduation.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.graduation.entity.YdSubjectInfo;
import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.mapper.YdSubjectInfoMapper;
import org.jeecg.modules.graduation.service.IYdSubjectInfoService;
import org.jeecg.modules.graduation.service.IYdTeacherInfoService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 选题信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class YdSubjectInfoServiceImpl extends ServiceImpl<YdSubjectInfoMapper, YdSubjectInfo> implements IYdSubjectInfoService {
	@Autowired 
	private YdSubjectInfoMapper ydSubjectInfoMapper;
	
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private IYdTeacherInfoService ydTeacherInfoService;
	@Override
	public IPage<YdSubjectInfo> findSubjectPageList(Page<YdSubjectInfo> page, YdSubjectInfo ydSubjectInfo) {
		// TODO Auto-generated method stub
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		String queryName = null;
		String teacherId = null;
		List<String> list = sysUserService.getRole(sysUser.getUsername());
		YdTeacherInfo ydTeacherInfo = ydTeacherInfoService.findTeacherInfo(sysUser.getUsername());
		// 判断是否是老师或者教务人员 
		if (list.contains("academic")) {
			//教务人员查看只查自己院系的信息
				queryName = ydTeacherInfo.getFacultyId();
		}else if (list.contains("teacher")) {
			//导师只查看自己群组下的
			queryName = ydTeacherInfo.getFacultyId();
			teacherId = ydTeacherInfo.getId();
		}
		return ydSubjectInfoMapper.findSubjectPageList(page, ydSubjectInfo,queryName,teacherId);
	}
	@Override
	public YdSubjectInfo getSubjectByStuId(String stuId) {
		// TODO Auto-generated method stub
		//根据学生id查询对应的选题信息
		return ydSubjectInfoMapper.getSubjectByStuId(stuId);
	}

}
