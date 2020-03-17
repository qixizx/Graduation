package org.jeecg.modules.graduation.service;

import java.util.List;

import org.jeecg.modules.graduation.entity.YdFacultyInfo;
import org.jeecg.modules.graduation.vo.ClassTreeVo;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 院系表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdFacultyInfoService extends IService<YdFacultyInfo> {
	//查找院系树
	public List<ClassTreeVo> findFacultyTree(); 
}
