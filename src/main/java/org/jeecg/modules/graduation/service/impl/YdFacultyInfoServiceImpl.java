package org.jeecg.modules.graduation.service.impl;

import java.util.List;

import org.jeecg.modules.graduation.entity.YdFacultyInfo;
import org.jeecg.modules.graduation.mapper.YdFacultyInfoMapper;
import org.jeecg.modules.graduation.service.IYdFacultyInfoService;
import org.jeecg.modules.graduation.vo.ClassTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 院系表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class YdFacultyInfoServiceImpl extends ServiceImpl<YdFacultyInfoMapper, YdFacultyInfo> implements IYdFacultyInfoService {
	@Autowired
	private YdFacultyInfoMapper ydFacultyInfoMapper;
	@Override
	public List<ClassTreeVo> findFacultyTree(String facultyId) {
		// TODO Auto-generated method stub
		return ydFacultyInfoMapper.findFacultyTree(facultyId);
	}

}
