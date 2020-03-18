package org.jeecg.modules.graduation.service.impl;

import org.jeecg.modules.graduation.entity.YdGroupPerson;
import org.jeecg.modules.graduation.mapper.YdGroupPersonMapper;
import org.jeecg.modules.graduation.service.IYdGroupPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 分组关系表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class YdGroupPersonServiceImpl extends ServiceImpl<YdGroupPersonMapper, YdGroupPerson> implements IYdGroupPersonService {
	@Autowired
	private YdGroupPersonMapper ydGroupPersonMapper;
	@Override
	public IPage<YdGroupPerson> findGroupStudent(Page<YdGroupPerson> page, YdGroupPerson ydGroupPerson) {
		// TODO Auto-generated method stub
		return ydGroupPersonMapper.findGroupStudent(page,ydGroupPerson);
	}

	@Override
	public IPage<YdGroupPerson> findGroupTeacher(Page<YdGroupPerson> page, YdGroupPerson ydGroupPerson) {
		// TODO Auto-generated method stub
		return ydGroupPersonMapper.findGroupTeacher(page,ydGroupPerson);
	}

}
