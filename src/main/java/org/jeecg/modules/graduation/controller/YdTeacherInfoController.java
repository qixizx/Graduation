package org.jeecg.modules.graduation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.service.IYdFacultyInfoService;
import org.jeecg.modules.graduation.service.IYdTeacherInfoService;
import org.jeecg.modules.graduation.vo.ClassTreeVo;
import org.jeecg.modules.graduation.vo.TestTreeList;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: 老师信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags="老师信息表")
@RestController
@RequestMapping("/graduation/ydTeacherInfo")
public class YdTeacherInfoController {
	@Autowired
	private IYdTeacherInfoService ydTeacherInfoService;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private IYdFacultyInfoService ydFacultyInfoService;
	/**
	  * 分页列表查询
	 * @param ydTeacherInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "老师信息表-分页列表查询")
	@ApiOperation(value="老师信息表-分页列表查询", notes="老师信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<YdTeacherInfo>> queryPageList(YdTeacherInfo ydTeacherInfo,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<YdTeacherInfo>> result = new Result<IPage<YdTeacherInfo>>();
//		QueryWrapper<YdTeacherInfo> queryWrapper = QueryGenerator.initQueryWrapper(ydTeacherInfo, req.getParameterMap());
		Page<YdTeacherInfo> page = new Page<YdTeacherInfo>(pageNo, pageSize);
//		IPage<YdTeacherInfo> pageList = ydTeacherInfoService.page(page, queryWrapper);
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		List<String> list = sysUserService.getRole(sysUser.getUsername());
		  // 判断是否是老师或者教务人员 只查自己院系的信息 
		if (list.contains("academic") ||list.contains("teacher")) { 
			YdTeacherInfo ydTeacherInfo1 =ydTeacherInfoService.findTeacherInfo(sysUser.getUsername());
			ydTeacherInfo.setFacultyId(ydTeacherInfo1.getFacultyId()); 
		}	
		IPage<YdTeacherInfo> pageList = ydTeacherInfoService.findTeacherPageList(page, ydTeacherInfo);
		
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	
	@AutoLog(value = "老师树显示")
	@ApiOperation(value="老师树显示", notes="老师树显示")
	@GetMapping(value = "/teacherTree")
	public Result<List<ClassTreeVo>> teacherTree() {
		Result<List<ClassTreeVo>> result = new Result<List<ClassTreeVo>>();
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		List<String> list = sysUserService.getRole(sysUser.getUsername());
		String facultyId = null;
		  // 判断是否是老师或者教务人员 只查自己院系的信息 
		if (list.contains("academic") ||list.contains("teacher")) { 
			YdTeacherInfo ydTeacherInfo =ydTeacherInfoService.findTeacherInfo(sysUser.getUsername());
			facultyId = ydTeacherInfo.getFacultyId();
		}	
		List<ClassTreeVo> facultyList=ydFacultyInfoService.findFacultyTree(facultyId);
		List<ClassTreeVo> teacherList=ydTeacherInfoService.findTeacherTree();
		List<ClassTreeVo> voList=new ArrayList<ClassTreeVo>();
		voList.addAll(facultyList);
		voList.addAll(teacherList);
		TestTreeList testTreeList = new TestTreeList();
		List<ClassTreeVo> teacherTree = testTreeList.buildChilTree(voList,"");
		result.setSuccess(true);
		result.setResult(teacherTree);
		return result;
	}
	/**
	  *   添加
	 * @param ydTeacherInfo
	 * @return
	 */
	@RequiresPermissions("teacher:add")
	@AutoLog(value = "老师信息表-添加")
	@ApiOperation(value="老师信息表-添加", notes="老师信息表-添加")
	@PostMapping(value = "/add")
	public Result<YdTeacherInfo> add(@RequestBody JSONObject jsonObject) {
		Result<YdTeacherInfo> result = new Result<YdTeacherInfo>();
		try {
			//先添加用户表
			SysUser user = JSON.parseObject(jsonObject.toJSONString(), SysUser.class);
			user.setCreateTime(new Date());// 设置创建时间
			//设置默认密码
			String salt = oConvertUtils.randomGen(8);
			user.setSalt(salt);
			String passwordEncode = PasswordUtil.encrypt(user.getUsername(), "123456", salt);
			user.setPassword(passwordEncode);
			user.setStatus(1);
			user.setDelFlag("0");
			//再添加学生表
			YdTeacherInfo ydTeacherInfo=JSON.parseObject(jsonObject.toJSONString(), YdTeacherInfo.class);
			//老师角色
			if("0".equals(ydTeacherInfo.getType())) {
				sysUserService.addUserWithRole(user, "ce22f30fa8f4bb501e491c4f1f71e0cf");
			}else if("1".equals(ydTeacherInfo.getType())) {
				//教务人员角色
				sysUserService.addUserWithRole(user, "a492020d466cbf310dd266bd1454c3c7");
			}
			ydTeacherInfo.setUserId(user.getId());
			ydTeacherInfoService.save(ydTeacherInfo);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败,用户名已经存在");
		}
		return result;
	}
//	/**
//	  *   添加
//	 * @param ydTeacherInfo
//	 * @return
//	 */
//	@AutoLog(value = "老师信息表-添加")
//	@ApiOperation(value="老师信息表-添加", notes="老师信息表-添加")
//	@PostMapping(value = "/add")
//	public Result<YdTeacherInfo> add(@RequestBody YdTeacherInfo ydTeacherInfo) {
//		Result<YdTeacherInfo> result = new Result<YdTeacherInfo>();
//		try {
//			ydTeacherInfoService.save(ydTeacherInfo);
//			result.success("添加成功！");
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			result.error500("操作失败");
//		}
//		return result;
//	}
	
	/**
	  *  编辑
	 * @param ydTeacherInfo
	 * @return
	 */
	@AutoLog(value = "老师信息表-编辑")
	@ApiOperation(value="老师信息表-编辑", notes="老师信息表-编辑")
	@PutMapping(value = "/edit")
	public Result<YdTeacherInfo> edit(@RequestBody JSONObject jsonObject) {
		Result<YdTeacherInfo> result = new Result<YdTeacherInfo>();
		//先查老师表 再根据老师学生表查用户表
		YdTeacherInfo ydTeacherInfo=JSON.parseObject(jsonObject.toJSONString(), YdTeacherInfo.class);
		YdTeacherInfo ydTeacherInfoEntity = ydTeacherInfoService.getById(ydTeacherInfo.getId());
		if(ydTeacherInfoEntity==null) {
			result.error500("未找到对应实体");
		}else {
			SysUser user = JSON.parseObject(jsonObject.toJSONString(), SysUser.class);
			user.setId(ydTeacherInfoEntity.getUserId());
			//查找用户实体
			SysUser sysUserEntity = sysUserService.getById(user.getId());
			if(sysUserEntity==null) {
				result.error500("未找到对应实体");
			}else {
				sysUserService.updateById(user);
			}
			boolean ok = ydTeacherInfoService.updateById(ydTeacherInfo);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}	
		return result;
	}
//	/**
//	  *  编辑
//	 * @param ydTeacherInfo
//	 * @return
//	 */
//	@AutoLog(value = "老师信息表-编辑")
//	@ApiOperation(value="老师信息表-编辑", notes="老师信息表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<YdTeacherInfo> edit(@RequestBody YdTeacherInfo ydTeacherInfo) {
//		Result<YdTeacherInfo> result = new Result<YdTeacherInfo>();
//		YdTeacherInfo ydTeacherInfoEntity = ydTeacherInfoService.getById(ydTeacherInfo.getId());
//		if(ydTeacherInfoEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = ydTeacherInfoService.updateById(ydTeacherInfo);
//			//TODO 返回false说明什么？
//			if(ok) {
//				result.success("修改成功!");
//			}
//		}
//		
//		return result;
//	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "老师信息表-通过id删除")
	@ApiOperation(value="老师信息表-通过id删除", notes="老师信息表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			//查询该老师信息
			YdTeacherInfo ydTeacherInfo = ydTeacherInfoService.getById(id);
			//查询该用户信息
			SysUser sysUser = sysUserService.getById(ydTeacherInfo.getUserId());
			//删除
			boolean ok = sysUserService.removeById(sysUser.getId());
			ydTeacherInfoService.removeById(id);
			if (ok) {
				Result.ok("删除成功!");
			}
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
//	/**
//	  *   通过id删除
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "老师信息表-通过id删除")
//	@ApiOperation(value="老师信息表-通过id删除", notes="老师信息表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
//		try {
//			ydTeacherInfoService.removeById(id);
//		} catch (Exception e) {
//			log.error("删除失败",e.getMessage());
//			return Result.error("删除失败!");
//		}
//		return Result.ok("删除成功!");
//	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "老师信息表-批量删除")
	@ApiOperation(value="老师信息表-批量删除", notes="老师信息表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<YdTeacherInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<YdTeacherInfo> result = new Result<YdTeacherInfo>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			//根据id批量查询
			Collection<YdTeacherInfo> ydTeacherInfo =this.ydTeacherInfoService.listByIds(Arrays.asList(ids.split(",")));
			//collection转list
			List<YdTeacherInfo> teachercartItems = new ArrayList<YdTeacherInfo>(ydTeacherInfo);
//			System.out.println(cartItems);
			for (YdTeacherInfo item : teachercartItems) {
				//查询该用户信息
				SysUser sysUser = sysUserService.getById(item.getUserId());
				//删除
				boolean ok = sysUserService.removeById(sysUser.getId());
				if(ok) {
					ydTeacherInfoService.removeById(item.getId());
				}
			}
//			this.ydTeacherInfoService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
//	/**
//	  *  批量删除
//	 * @param ids
//	 * @return
//	 */
//	@AutoLog(value = "老师信息表-批量删除")
//	@ApiOperation(value="老师信息表-批量删除", notes="老师信息表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<YdTeacherInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<YdTeacherInfo> result = new Result<YdTeacherInfo>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.ydTeacherInfoService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除成功!");
//		}
//		return result;
//	}
	
	/**
	  * 通过登录账号查询
	 * @return
	 */
	@AutoLog(value = "老师信息表-通过登录账号查询")
	@ApiOperation(value="老师信息表-通过登录账号查询", notes="老师信息表-通过登录账号查询")
	@GetMapping(value = "/queryByUsername")
	public Result<YdTeacherInfo> queryByUsername() {
		Result<YdTeacherInfo> result = new Result<YdTeacherInfo>();
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		List<String> list = sysUserService.getRole(sysUser.getUsername());
		YdTeacherInfo ydTeacherInfo =null;
		// 判断是否是老师或者教务人员   只查自己院系的信息
		if (list.contains("academic") || list.contains("teacher")) {
			 ydTeacherInfo = ydTeacherInfoService.findTeacherInfo(sysUser.getUsername());
		}
		if(ydTeacherInfo==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(ydTeacherInfo);
			result.setSuccess(true);
		}
		return result;
	}
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "老师信息表-通过id查询")
	@ApiOperation(value="老师信息表-通过id查询", notes="老师信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<YdTeacherInfo> queryById(@RequestParam(name="id",required=true) String id) {
		Result<YdTeacherInfo> result = new Result<YdTeacherInfo>();
		YdTeacherInfo ydTeacherInfo = ydTeacherInfoService.getById(id);
		if(ydTeacherInfo==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(ydTeacherInfo);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
//      QueryWrapper<YdTeacherInfo> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              YdTeacherInfo ydTeacherInfo = JSON.parseObject(deString, YdTeacherInfo.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(ydTeacherInfo, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
	  YdTeacherInfo ydTeacherInfo = new YdTeacherInfo();
	  JSONObject requestJson = new JSONObject();
	  //request.getParameterNames()转为json对象
      Enumeration paramNames = request.getParameterNames();
      while (paramNames.hasMoreElements()) {
          String paramName = (String) paramNames.nextElement();
          String[] pv = request.getParameterValues(paramName);
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < pv.length; i++) {
              if (pv[i].length() > 0) {
                  if (i > 0) {
                      sb.append(",");
                  }
                  sb.append(pv[i]);
              }
          }
          requestJson.put(paramName, sb.toString());
      }
	  ydTeacherInfo=JSON.parseObject(requestJson.toJSONString(), YdTeacherInfo.class);
      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<YdTeacherInfo> pageList = ydTeacherInfoService.listImport(ydTeacherInfo);
      LoginUser user = (LoginUser)SecurityUtils.getSubject().getPrincipal(); // 登录账号 
      String tittle=null;
		if (user != null) {
			tittle="导出人:"+user.getUsername();
		}else {
			tittle="导出人:jeecg";
		}
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "老师信息表列表");
      mv.addObject(NormalExcelConstants.CLASS, YdTeacherInfo.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("老师信息表列表数据", tittle, "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequiresPermissions("teacher:import")
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
				//重叠部分class需要处理
				List<SysUser> listSysUsers = ExcelImportUtil.importExcel(file.getInputStream(), SysUser.class, params);
				for (SysUser sysUserExcel : listSysUsers) {
					if (sysUserExcel.getPassword() == null) {
						// 密码默认为“123456”
						sysUserExcel.setCreateTime(new Date());// 设置创建时间
						String salt = oConvertUtils.randomGen(8);
						sysUserExcel.setSalt(salt);
						String passwordEncode = PasswordUtil.encrypt(sysUserExcel.getUsername(), "123456", salt);
						sysUserExcel.setPassword(passwordEncode);
						//直接默认状态 正常
						sysUserExcel.setStatus(1);
						//直接默认 0  正常
						sysUserExcel.setDelFlag("0");
					}				
					
			          try {
			        	  List<YdTeacherInfo> listYdTeacherInfos = ExcelImportUtil.importExcel(file.getInputStream(), YdTeacherInfo.class, params);
			              //  需要测试  是否上下两个  按相同的顺序排序  防止excle 读取顺序不同
			              Optional<YdTeacherInfo> ydTeacherInfo=listYdTeacherInfos.stream().filter(p -> sysUserExcel.getUsername().equals(p.getUsername())).findFirst();
			              System.out.println(ydTeacherInfo.get()+"---------");
			              //现存到用户表 在存储到学生表 同时设置角色
//			                                          多角色时通过excle获取
			              sysUserService.addUserWithRole(sysUserExcel, ydTeacherInfo.get().getRole());
//			              //根据用户名查询用户信息 
//			              SysUser sysUser =sysUserService.getUserByName(sysUserExcel.getUsername());
			              ydTeacherInfo.get().setUserId(sysUserExcel.getId());
			              if("ce22f30fa8f4bb501e491c4f1f71e0cf".equals(ydTeacherInfo.get().getRole())) {
			            	  //老师角色
			            	  ydTeacherInfo.get().setType("0");
			              }else if("a492020d466cbf310dd266bd1454c3c7".equals(ydTeacherInfo.get().getRole())) {
			            	  //教务人员角色
			            	  ydTeacherInfo.get().setType("1");
			              }
			              ydTeacherInfoService.save(ydTeacherInfo.get());
			          } catch (Exception e) {
			              log.error(e.getMessage(),e);
			              return Result.error("文件导入失败:"+e.getMessage());
			          } finally {
			              try {
			                  file.getInputStream().close();
			              } catch (IOException e) {
			                  e.printStackTrace();
			              }
			          }
				}
				return Result.ok("文件导入成功！数据行数：" + listSysUsers.size());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return Result.error("抱歉! 您导入的数据中用户名已经存在.");
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
      }
      return Result.ok("文件导入失败！");
  }

}
