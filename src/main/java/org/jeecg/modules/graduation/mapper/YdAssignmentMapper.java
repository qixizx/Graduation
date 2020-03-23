package org.jeecg.modules.graduation.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdAssignment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 任务书表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdAssignmentMapper extends BaseMapper<YdAssignment> {
	//开题报告查询
	public IPage<YdAssignment> findAssignPageList(Page<YdAssignment> page,
			@Param("map")YdAssignment ydAssignment, 
			@Param("queryName") String queryName,
			@Param("teacherId") String teacherId
			);
	
	
	public YdAssignment getAssignByStuId(String stuId);
}
