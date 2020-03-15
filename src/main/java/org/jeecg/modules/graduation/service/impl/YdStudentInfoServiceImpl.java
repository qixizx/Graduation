package org.jeecg.modules.graduation.service.impl;

import org.jeecg.modules.graduation.entity.YdStudentInfo;
import org.jeecg.modules.graduation.mapper.YdStudentInfoMapper;
import org.jeecg.modules.graduation.service.IYdStudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class YdStudentInfoServiceImpl extends ServiceImpl<YdStudentInfoMapper, YdStudentInfo> implements IYdStudentInfoService {
	@Autowired
	private YdStudentInfoMapper ydStudentInfoMapper;
	
	@Override
	public IPage<YdStudentInfo> finStudentPageList(Page<YdStudentInfo> page, YdStudentInfo ydStudentInfo) {
		// TODO Auto-generated method stub
		return ydStudentInfoMapper.finStudentPageList(page,ydStudentInfo);
	}

}
