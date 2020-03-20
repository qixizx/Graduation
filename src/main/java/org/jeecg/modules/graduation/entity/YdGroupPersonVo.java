package org.jeecg.modules.graduation.entity;

import java.util.List;

import lombok.Data;
@Data
public class YdGroupPersonVo {
	/**群组部门id*/
	private String groupId;
	/**组员类型*/
	private String type;
	/**对应的用户id集合*/
	private List<String> realIdList;
	public YdGroupPersonVo(String groupId, List<String> realIdList,String type) {
		super();
		this.groupId = groupId;
		this.realIdList = realIdList;
		this.type = type;
	}
}
