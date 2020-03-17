package org.jeecg.modules.graduation.vo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class TestTreeList {
	public  static List<ClassTreeVo> buildChilTree(List<ClassTreeVo> classTrees, String parentid) {
		List<ClassTreeVo> newTreeList = new ArrayList<ClassTreeVo>();

		for (ClassTreeVo classTree : classTrees) {
			if (classTree.getParentId().equals(parentid)) {
				List<ClassTreeVo> childList = buildChilTree(classTrees, classTree.getValue());
				classTree.setChildren(childList);
				newTreeList.add(classTree);
			}
		}

		return newTreeList;
	}

	 public static List<ClassTreeVo> parseMenuTree(List<ClassTreeVo> list){
	    List<ClassTreeVo> result = new ArrayList<ClassTreeVo>();
		
	    // 1、获取第一级节点
	    for (ClassTreeVo menu : list) {
	        if(null == menu.getParentId()) {
		    result.add(menu);
	        }	 
	    }
		
	    // 2、递归获取子节点
	    for (ClassTreeVo parent : result) {
	    	parent = recursiveTree(parent, list);
	    }
		
	    return result;
	}

	public static ClassTreeVo recursiveTree(ClassTreeVo parent, List<ClassTreeVo> list) {
	    for (ClassTreeVo menu : list) {
	        if(parent.getValue() == menu.getParentId()) {
		    menu = recursiveTree(menu, list);
		    parent.getChildren().add(menu);
		}
	    }
		    
	    return parent;
	}
	public static void main(String[] args) {
		ClassTreeVo vo1 = new ClassTreeVo();
		vo1.setValue("7428b5a7a3bf7479198ac3156ba191e7");
		vo1.setLabel("计算机与控制工程学院");
		vo1.setParentId("");
		ClassTreeVo vo2 = new ClassTreeVo();
		vo2.setValue("7ea832e3822884ec91dba10d3dd25106");
		vo2.setLabel("光电信息科学技术学院");
		vo2.setParentId("");
		ClassTreeVo vo3 = new ClassTreeVo();	
		vo3.setValue("c91d1bf7a68a49b049f3d56133b200ba");
		vo3.setLabel("音乐舞蹈学院");
		vo3.setParentId("");
		ClassTreeVo vo4 = new ClassTreeVo();
		vo4.setValue("f1d91d62567193492881c44f32db9bb6");
		vo4.setLabel("通信工程");
		vo4.setParentId("7ea832e3822884ec91dba10d3dd25106"); 		
		ClassTreeVo vo5 = new ClassTreeVo();
		vo5.setValue("18246d3eff6195cc8b13e39ec452fc20");
		vo5.setLabel("计算机科学与技术");
		vo5.setParentId("7428b5a7a3bf7479198ac3156ba191e7");
		ClassTreeVo vo6 = new ClassTreeVo();
		vo6.setValue("f3ac2537269039c522d2e8778defdf54");
		vo6.setLabel("软件工程");
		vo6.setParentId("7428b5a7a3bf7479198ac3156ba191e7");
		ClassTreeVo vo7 = new ClassTreeVo();
		vo7.setValue("20fb2caf11bea868feb5fde617dc9344");
		vo7.setLabel("计164-2");
		vo7.setParentId("f3ac2537269039c522d2e8778defdf54");
		ClassTreeVo vo8 = new ClassTreeVo();
		vo8.setValue("4dfe2b173c1a8e15afe3a6637db80764");
		vo8.setLabel("计164-1");
		vo8.setParentId("f3ac2537269039c522d2e8778defdf54");
		List<ClassTreeVo> voList=new ArrayList<ClassTreeVo>();
		voList.add(vo1);
		voList.add(vo2);
		voList.add(vo3);
		voList.add(vo4);
		voList.add(vo5);
		voList.add(vo6);
		voList.add(vo7);
		voList.add(vo8);
		//System.out.println(JSONObject.toJSON(voList));
//		List<ClassTreeVo> result = parseMenuTree(voList);
		List<ClassTreeVo> result = buildChilTree(voList,"");
		
		System.out.println(JSONObject.toJSON(result));

	}

}
