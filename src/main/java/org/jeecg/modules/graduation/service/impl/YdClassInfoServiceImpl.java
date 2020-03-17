package org.jeecg.modules.graduation.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.graduation.entity.YdClassInfo;
import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.mapper.YdClassInfoMapper;
import org.jeecg.modules.graduation.service.IYdClassInfoService;
import org.jeecg.modules.graduation.service.IYdTeacherInfoService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 班级信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class YdClassInfoServiceImpl extends ServiceImpl<YdClassInfoMapper, YdClassInfo> implements IYdClassInfoService {
	@Autowired
	private YdClassInfoMapper ydClassInfoMapper;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private IYdTeacherInfoService ydTeacherInfoService;
	
	@Override
	public IPage<YdClassInfo> findClassPageList(Page<YdClassInfo> page, YdClassInfo ydClassInfo) {
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
		}
		return ydClassInfoMapper.findClassPageList(page, ydClassInfo,queryName);
	}

}
