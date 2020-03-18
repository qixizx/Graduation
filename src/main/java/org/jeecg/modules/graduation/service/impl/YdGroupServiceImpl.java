package org.jeecg.modules.graduation.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.graduation.entity.YdGroup;
import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.mapper.YdGroupMapper;
import org.jeecg.modules.graduation.service.IYdGroupService;
import org.jeecg.modules.graduation.service.IYdTeacherInfoService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 分组表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class YdGroupServiceImpl extends ServiceImpl<YdGroupMapper, YdGroup> implements IYdGroupService {
	@Autowired
	private YdGroupMapper ydGroupMapper;
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private IYdTeacherInfoService ydTeacherInfoService;
	@Override
	public IPage<YdGroup> findGroupPageList(Page<YdGroup> page,YdGroup ydGroup) {
		// TODO Auto-generated method stub
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		String facultyId = null;
		List<String> list = sysUserService.getRole(sysUser.getUsername());
		// 判断是否是老师或者教务人员   只查自己院系的信息
		if (list.contains("academic") || list.contains("teacher")) {
			YdTeacherInfo ydTeacherInfo = ydTeacherInfoService.findTeacherInfo(sysUser.getUsername());
			facultyId = ydTeacherInfo.getFacultyId();
		}
//		System.out.println(ydGroup.getLabels().size());
		return ydGroupMapper.findGroupPageList(page,ydGroup,facultyId);
	}

}
