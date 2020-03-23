package org.jeecg.modules.graduation.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdReport;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;



/**
 * @Description: 开题报告表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdReportMapper extends BaseMapper<YdReport> {
	//开题报告查询
	public IPage<YdReport> findReportPageList(Page<YdReport> page,
			@Param("map")YdReport ydReport, 
			@Param("queryName") String queryName,
			@Param("teacherId") String teacherId
			);
	//根据学生id查询对应的开题报告
	public YdReport getSubjectByStuId(String stuId);
}
