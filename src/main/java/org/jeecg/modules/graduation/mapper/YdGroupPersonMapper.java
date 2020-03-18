package org.jeecg.modules.graduation.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdGroupPerson;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 分组关系表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdGroupPersonMapper extends BaseMapper<YdGroupPerson> {

	public IPage<YdGroupPerson> findGroupStudent(Page<YdGroupPerson> page,@Param("map") YdGroupPerson ydGroupPerson);
	public IPage<YdGroupPerson> findGroupTeacher(Page<YdGroupPerson> page,@Param("map") YdGroupPerson ydGroupPerson);
}
