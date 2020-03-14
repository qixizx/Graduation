package org.jeecg.modules.graduation.service.impl;

import org.jeecg.modules.graduation.entity.YdMajorInfo;
import org.jeecg.modules.graduation.mapper.YdMajorInfoMapper;
import org.jeecg.modules.graduation.service.IYdMajorInfoService;
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
	@Override
	public IPage<YdMajorInfo> findMajorPageList(Page<YdMajorInfo> page, YdMajorInfo ydMajorInfo) {
		// TODO Auto-generated method stub
		return ydMajorInfoMapper.findMajorPageList(page, ydMajorInfo);
	}

}
