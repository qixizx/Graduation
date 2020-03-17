package org.jeecg.modules.graduation.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdClassInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 班级信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdClassInfoMapper extends BaseMapper<YdClassInfo> {
	public IPage<YdClassInfo> findClassPageList(Page<YdClassInfo> page,@Param("map")YdClassInfo ydClassInfo,@Param("queryName") String queryName);
}
