package org.jeecg.modules.graduation.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ClassTreeVo {
	//存储id
	private java.lang.String value;
	//存储名字
	private java.lang.String label;
	//放父节点
	private java.lang.String parentId;
	//子节点
	private List<ClassTreeVo> children = new ArrayList<ClassTreeVo>();
}
