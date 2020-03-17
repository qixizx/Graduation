package org.jeecg.modules.graduation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdMajorInfo;
import org.jeecg.modules.graduation.vo.ClassTreeVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 专业信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdMajorInfoMapper extends BaseMapper<YdMajorInfo> {
	//查询专业相关信息
	public IPage<YdMajorInfo> findMajorPageList(Page<YdMajorInfo> page,@Param("map")YdMajorInfo ydMajorInfo, @Param("queryName") String queryName);
	
	public List<ClassTreeVo> findMajortyTree();
}
