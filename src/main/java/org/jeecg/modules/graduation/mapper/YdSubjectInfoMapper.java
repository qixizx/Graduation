package org.jeecg.modules.graduation.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdSubjectInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 选题信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdSubjectInfoMapper extends BaseMapper<YdSubjectInfo> {
	//选题页面查询
	public IPage<YdSubjectInfo> findSubjectPageList(Page<YdSubjectInfo> page,
			@Param("map")YdSubjectInfo ydSubjectInfo, 
			@Param("queryName") String queryName,
			@Param("teacherId") String teacherId
			);
	//根据学生id查询对应的选题信息
	public YdSubjectInfo getSubjectByStuId(String stuId);
}
