package org.jeecg.modules.graduation.service;

import org.jeecg.modules.graduation.entity.YdAssignment;
import org.jeecg.modules.graduation.entity.YdThesis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 论文表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdThesisService extends IService<YdThesis> {
	//论文页面
	public IPage<YdThesis> findThesisPageList(Page<YdThesis> page,YdThesis ydThesis);
	
	//根据学生id查询对应的论文 信息
	public YdThesis getThesisByStuId(String stuId);
}
