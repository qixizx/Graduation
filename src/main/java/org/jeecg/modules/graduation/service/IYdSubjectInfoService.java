package org.jeecg.modules.graduation.service;

import org.jeecg.modules.graduation.entity.YdSubjectInfo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 选题信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdSubjectInfoService extends IService<YdSubjectInfo> {
	//选题页面查询
	public IPage<YdSubjectInfo> findSubjectPageList(Page<YdSubjectInfo> page,YdSubjectInfo ydSubjectInfo);
	
	//根据学生id查询对应的选题信息
	public YdSubjectInfo getSubjectByStuId(String stuId);
}
