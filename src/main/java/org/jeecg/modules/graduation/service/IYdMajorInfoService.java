package org.jeecg.modules.graduation.service;

import java.util.List;

import org.jeecg.modules.graduation.entity.YdMajorInfo;
import org.jeecg.modules.graduation.vo.ClassTreeVo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 专业信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdMajorInfoService extends IService<YdMajorInfo> {
	//查询专业相关信息
	public IPage<YdMajorInfo> findMajorPageList(Page<YdMajorInfo> page,YdMajorInfo ydMajorInfoVo);
	//查询专业信息树
	public List<ClassTreeVo> findMajortyTree();
}
