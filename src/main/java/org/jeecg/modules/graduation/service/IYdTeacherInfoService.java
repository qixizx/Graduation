package org.jeecg.modules.graduation.service;

import java.util.List;

import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.vo.ClassTreeVo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 老师信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IYdTeacherInfoService extends IService<YdTeacherInfo> {
	//老师页面查询
	public IPage<YdTeacherInfo> findTeacherPageList(Page<YdTeacherInfo> page,YdTeacherInfo ydTeacherInfo);
	
	//导出显示界面
	public List<YdTeacherInfo> listImport(YdTeacherInfo ydTeacherInfo);
	
	//根据 用户名查用户信息
	public YdTeacherInfo  findTeacherInfo(String username);
	
	//老师基础树
	public List<ClassTreeVo> findTeacherTree();
}
