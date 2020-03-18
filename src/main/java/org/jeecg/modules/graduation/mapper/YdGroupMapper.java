package org.jeecg.modules.graduation.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdGroup;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 分组表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdGroupMapper extends BaseMapper<YdGroup> {
	//查询分组表页面
	public IPage<YdGroup> findGroupPageList(Page<YdGroup> page,@Param("map")YdGroup ydGroup,@Param("facultyId")String facultyId);
}
