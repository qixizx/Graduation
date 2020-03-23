package org.jeecg.modules.graduation.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.graduation.entity.YdAssignment;
import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.mapper.YdAssignmentMapper;
import org.jeecg.modules.graduation.service.IYdAssignmentService;
import org.jeecg.modules.graduation.service.IYdTeacherInfoService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 任务书表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class YdAssignmentServiceImpl extends ServiceImpl<YdAssignmentMapper, YdAssignment> implements IYdAssignmentService {
	@Autowired
	private YdAssignmentMapper ydAssignmentMapper;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private IYdTeacherInfoService ydTeacherInfoService;
	@Override
	public IPage<YdAssignment> findAssignPageList(Page<YdAssignment> page, YdAssignment ydAssignment) {
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
		return ydAssignmentMapper.findAssignPageList(page, ydAssignment,queryName,teacherId);
	}

	@Override
	public YdAssignment getAssignByStuId(String stuId) {
		// TODO Auto-generated method stub
		return ydAssignmentMapper.getAssignByStuId(stuId);
	}

}
