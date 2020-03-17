package org.jeecg.modules.graduation.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.graduation.entity.YdMajorInfo;
import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.mapper.YdMajorInfoMapper;
import org.jeecg.modules.graduation.service.IYdMajorInfoService;
import org.jeecg.modules.graduation.service.IYdTeacherInfoService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 专业信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class YdMajorInfoServiceImpl extends ServiceImpl<YdMajorInfoMapper, YdMajorInfo> implements IYdMajorInfoService {
	@Autowired
	private YdMajorInfoMapper ydMajorInfoMapper;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private IYdTeacherInfoService ydTeacherInfoService;
	@Override
	public IPage<YdMajorInfo> findMajorPageList(Page<YdMajorInfo> page, YdMajorInfo ydMajorInfo) {
		// TODO Auto-generated method stub
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		String queryName = null;
		List<String> list = sysUserService.getRole(sysUser.getUsername());
		// 判断是否是老师或者教务人员   只查自己院系的信息
		if (list.contains("academic") || list.contains("teacher")) {
			YdTeacherInfo ydTeacherInfo = ydTeacherInfoService.findTeacherInfo(sysUser.getUsername());
				queryName = ydTeacherInfo.getFacultyId();
				//查询条件修改
				ydMajorInfo.setFacultyId(null);
		}	
		return ydMajorInfoMapper.findMajorPageList(page, ydMajorInfo,queryName);
	}
	
}
