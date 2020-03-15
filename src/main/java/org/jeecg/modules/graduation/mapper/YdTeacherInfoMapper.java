package org.jeecg.modules.graduation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdTeacherInfo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 老师信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdTeacherInfoMapper extends BaseMapper<YdTeacherInfo> {
	//老师页面查询
	public IPage<YdTeacherInfo> findTeacherPageList(Page<YdTeacherInfo> page,@Param("map")YdTeacherInfo ydTeacherInfo);

	public List<YdTeacherInfo> listImport(@Param("map")YdTeacherInfo ydTeacherInfo) ;
}
