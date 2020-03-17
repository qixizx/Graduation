package org.jeecg.modules.graduation.mapper;

import java.util.List;

import org.jeecg.modules.graduation.entity.YdFacultyInfo;
import org.jeecg.modules.graduation.vo.ClassTreeVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 院系表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface YdFacultyInfoMapper extends BaseMapper<YdFacultyInfo> {
	public List<ClassTreeVo> findFacultyTree();
}
