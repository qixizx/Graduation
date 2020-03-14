package org.jeecg.modules.graduation.service;

import org.jeecg.modules.graduation.entity.YdClassInfo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 班级信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdClassInfoService extends IService<YdClassInfo> {
	//查询班级的专业显示
	public IPage<YdClassInfo> findClassPageList(Page<YdClassInfo> page,YdClassInfo ydClassInfo);
}
