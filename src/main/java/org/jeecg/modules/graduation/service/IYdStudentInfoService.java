package org.jeecg.modules.graduation.service;

import java.util.List;

import org.jeecg.modules.graduation.entity.YdStudentInfo;
import org.jeecg.modules.graduation.vo.ClassTreeVo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdStudentInfoService extends IService<YdStudentInfo> {
	//学生界面查询
	public IPage<YdStudentInfo> finStudentPageList(Page<YdStudentInfo> page , YdStudentInfo ydStudentInfo);
	
	//学生树查询
	public List<ClassTreeVo> findStudentTree();
}
