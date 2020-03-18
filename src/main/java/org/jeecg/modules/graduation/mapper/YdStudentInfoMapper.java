package org.jeecg.modules.graduation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdStudentInfo;
import org.jeecg.modules.graduation.vo.ClassTreeVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdStudentInfoMapper extends BaseMapper<YdStudentInfo> {
	//学生信息页面查询
	public IPage<YdStudentInfo> finStudentPageList(Page<YdStudentInfo> page,@Param("map")YdStudentInfo ydStudentInfo, @Param("queryName") String queryName);

	public List<ClassTreeVo> findStudentTree();
}
