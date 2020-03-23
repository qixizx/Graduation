package org.jeecg.modules.graduation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.graduation.entity.YdAssignment;
import org.jeecg.modules.graduation.entity.YdThesis;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 论文表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdThesisMapper extends BaseMapper<YdThesis> {

	public IPage<YdThesis> findThesisPageList(Page<YdThesis> page,
			@Param("map")YdThesis ydThesis, 
			@Param("queryName") String queryName,
			@Param("teacherId") String teacherId
			);

	public YdThesis getThesisByStuId(String stuId);

}
