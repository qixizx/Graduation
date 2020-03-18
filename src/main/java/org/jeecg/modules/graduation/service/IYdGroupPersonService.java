package org.jeecg.modules.graduation.service;

import org.jeecg.modules.graduation.entity.YdGroupPerson;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 分组关系表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdGroupPersonService extends IService<YdGroupPerson> {
	//分页查询关联学生
	public IPage<YdGroupPerson> findGroupStudent(Page<YdGroupPerson> page,YdGroupPerson ydGroupPerson);
	//分页查询关联老师
	public IPage<YdGroupPerson> findGroupTeacher(Page<YdGroupPerson> page,YdGroupPerson ydGroupPerson);
}
