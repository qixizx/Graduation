package org.jeecg.modules.graduation.service;

import org.jeecg.modules.graduation.entity.YdGroup;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 分组表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdGroupService extends IService<YdGroup> {
	//查询分组表页面
	public IPage<YdGroup> findGroupPageList(Page<YdGroup> page, YdGroup ydGroup);

	
}
