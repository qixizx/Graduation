package org.jeecg.modules.graduation.service.impl;

import org.jeecg.modules.graduation.entity.YdClassInfo;
import org.jeecg.modules.graduation.mapper.YdClassInfoMapper;
import org.jeecg.modules.graduation.service.IYdClassInfoService;
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
	@Override
	public IPage<YdClassInfo> findClassPageList(Page<YdClassInfo> page, YdClassInfo ydClassInfo) {
		// TODO Auto-generated method stub
		return ydClassInfoMapper.findClassPageList(page, ydClassInfo);
	}

}
