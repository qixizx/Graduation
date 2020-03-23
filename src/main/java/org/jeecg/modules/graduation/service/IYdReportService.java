package org.jeecg.modules.graduation.service;

import org.jeecg.modules.graduation.entity.YdReport;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 开题报告表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdReportService extends IService<YdReport> {
	//查询开题报告页面信息
	public IPage<YdReport> findReportPageList(Page<YdReport> page ,YdReport ydReport);
	
	//根据学生id查询对应的开题报告选题信息
	public YdReport getSubjectByStuId(String stuId);
}
