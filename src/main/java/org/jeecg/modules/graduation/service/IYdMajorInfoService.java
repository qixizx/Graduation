package org.jeecg.modules.graduation.service;

import org.jeecg.modules.graduation.entity.YdMajorInfo;

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
	public IPage<YdMajorInfo> findMajorClassPageList(Page<YdMajorInfo> page,YdMajorInfo ydMajorInfo);
}
