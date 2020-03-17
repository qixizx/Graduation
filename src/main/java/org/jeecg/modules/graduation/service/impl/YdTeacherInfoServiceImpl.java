package org.jeecg.modules.graduation.service.impl;

import java.util.List;

import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.mapper.YdTeacherInfoMapper;
import org.jeecg.modules.graduation.service.IYdTeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 老师信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class YdTeacherInfoServiceImpl extends ServiceImpl<YdTeacherInfoMapper, YdTeacherInfo> implements IYdTeacherInfoService {
	@Autowired
	private YdTeacherInfoMapper ydTeacherInfoMapper;

	
	@Override
	public IPage<YdTeacherInfo> findTeacherPageList(Page<YdTeacherInfo> page, YdTeacherInfo ydTeacherInfo) {
		// TODO Auto-generated method stub
		return ydTeacherInfoMapper.findTeacherPageList(page,ydTeacherInfo);
	}
	@Override
	
	public List<YdTeacherInfo> listImport(YdTeacherInfo ydTeacherInfo) {
		// TODO Auto-generated method stub
		return ydTeacherInfoMapper.listImport(ydTeacherInfo);
	}
	@Override
	public YdTeacherInfo findTeacherInfo(String username) {
		// TODO Auto-generated method stub
		return ydTeacherInfoMapper.findTeacherInfo(username);
	}

}
