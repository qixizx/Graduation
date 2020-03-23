package org.jeecg.modules.graduation.service;

import org.jeecg.modules.graduation.entity.YdAssignment;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 任务书表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdAssignmentService extends IService<YdAssignment> {
	//任务书页面
	public IPage<YdAssignment> findAssignPageList(Page<YdAssignment> page,YdAssignment ydAssignment);
	
	//根据学生id查询对应的任务书 信息
	public YdAssignment getAssignByStuId(String stuId);
}
