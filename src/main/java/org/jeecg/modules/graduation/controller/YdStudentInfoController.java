package org.jeecg.modules.graduation.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.graduation.entity.YdStudentInfo;
import org.jeecg.modules.graduation.service.IYdStudentInfoService;
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
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags="学生信息表")
@RestController
@RequestMapping("/graduation/ydStudentInfo")
public class YdStudentInfoController {
	@Autowired
	private IYdStudentInfoService ydStudentInfoService;
	@Autowired
	private ISysUserService sysUserService;
	/**
	  * 分页列表查询
	 * @param ydStudentInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "学生信息表-分页列表查询")
	@ApiOperation(value="学生信息表-分页列表查询", notes="学生信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<YdStudentInfo>> queryPageList(YdStudentInfo ydStudentInfo,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<YdStudentInfo>> result = new Result<IPage<YdStudentInfo>>();
//		QueryWrapper<YdStudentInfo> queryWrapper = QueryGenerator.initQueryWrapper(ydStudentInfo, req.getParameterMap());
		Page<YdStudentInfo> page = new Page<YdStudentInfo>(pageNo, pageSize);
//		IPage<YdStudentInfo> pageList = ydStudentInfoService.page(page, queryWrapper);
		
		IPage<YdStudentInfo> pageList = ydStudentInfoService.finStudentPageList(page,ydStudentInfo);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	/**
	  *   添加
	 * @param ydStudentInfo
	 * @return
	 */
	@AutoLog(value = "学生信息表-添加")
	@ApiOperation(value="学生信息表-添加", notes="学生信息表-添加")
	@PostMapping(value = "/add")
	public Result<YdStudentInfo> add(@RequestBody JSONObject jsonObject) {
		Result<YdStudentInfo> result = new Result<YdStudentInfo>();
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
			sysUserService.addUserWithRole(user, "39c038c874f52a6430b6e03bdcbad46f");
			//再添加学生表
			YdStudentInfo ydStudentInfo=JSON.parseObject(jsonObject.toJSONString(), YdStudentInfo.class);
			ydStudentInfo.setUserId(user.getId());
			ydStudentInfoService.save(ydStudentInfo);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败,用户名已经存在");
		}
		return result;
	}
//	/**
//	  *   添加
//	 * @param ydStudentInfo
//	 * @return
//	 */
//	@AutoLog(value = "学生信息表-添加")
//	@ApiOperation(value="学生信息表-添加", notes="学生信息表-添加")
//	@PostMapping(value = "/add")
//	public Result<YdStudentInfo> add(@RequestBody YdStudentInfo ydStudentInfo) {
//		Result<YdStudentInfo> result = new Result<YdStudentInfo>();
//		try {
//			ydStudentInfoService.save(ydStudentInfo);
//			result.success("添加成功！");
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			result.error500("操作失败");
//		}
//		return result;
//	}
	
	/**
	  *  编辑
	 * @param ydStudentInfo
	 * @return
	 */
	@AutoLog(value = "学生信息表-编辑")
	@ApiOperation(value="学生信息表-编辑", notes="学生信息表-编辑")
	@PutMapping(value = "/edit")
	public Result<YdStudentInfo> edit(@RequestBody JSONObject jsonObject) {
		Result<YdStudentInfo> result = new Result<YdStudentInfo>();
		//先查学生表 再根据学生表查用户表
		YdStudentInfo ydStudentInfo=JSON.parseObject(jsonObject.toJSONString(), YdStudentInfo.class);
		YdStudentInfo ydStudentInfoEntity = ydStudentInfoService.getById(ydStudentInfo.getId());
		if(ydStudentInfoEntity==null) {
			result.error500("未找到对应实体");
		}else {
			SysUser user = JSON.parseObject(jsonObject.toJSONString(), SysUser.class);
			user.setId(ydStudentInfoEntity.getUserId());
			//查找用户实体
			SysUser sysUserEntity = sysUserService.getById(user.getId());
			if(sysUserEntity==null) {
				result.error500("未找到对应实体");
			}else {
				sysUserService.updateById(user);
			}
			boolean ok = ydStudentInfoService.updateById(ydStudentInfo);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
//	/**
//	  *  编辑
//	 * @param ydStudentInfo
//	 * @return
//	 */
//	@AutoLog(value = "学生信息表-编辑")
//	@ApiOperation(value="学生信息表-编辑", notes="学生信息表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<YdStudentInfo> edit(@RequestBody YdStudentInfo ydStudentInfo) {
//		Result<YdStudentInfo> result = new Result<YdStudentInfo>();
//		YdStudentInfo ydStudentInfoEntity = ydStudentInfoService.getById(ydStudentInfo.getId());
//		if(ydStudentInfoEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = ydStudentInfoService.updateById(ydStudentInfo);
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
	@AutoLog(value = "学生信息表-通过id删除")
	@ApiOperation(value="学生信息表-通过id删除", notes="学生信息表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			//查询该学生信息
			YdStudentInfo ydStudentInfo = ydStudentInfoService.getById(id);
			//查询该用户信息
			SysUser sysUser = sysUserService.getById(ydStudentInfo.getUserId());
			//删除
			boolean ok = sysUserService.removeById(sysUser.getId());
			ydStudentInfoService.removeById(id);
			if (ok) {
				Result.ok("删除成功!");
			}
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生信息表-批量删除")
	@ApiOperation(value="学生信息表-批量删除", notes="学生信息表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<YdStudentInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<YdStudentInfo> result = new Result<YdStudentInfo>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			//根据id批量查询
			Collection<YdStudentInfo> ydStudentInfo =this.ydStudentInfoService.listByIds(Arrays.asList(ids.split(",")));
			//collection转list
			List<YdStudentInfo> studentItems = new ArrayList<YdStudentInfo>(ydStudentInfo);
//			System.out.println(cartItems);
			for (YdStudentInfo item : studentItems) {
				//查询该用户信息
				SysUser sysUser = sysUserService.getById(item.getUserId());
				//删除
				boolean ok = sysUserService.removeById(sysUser.getId());
				if(ok) {
					ydStudentInfoService.removeById(item.getId());
				}
			}
//			this.ydStudentInfoService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生信息表-通过id查询")
	@ApiOperation(value="学生信息表-通过id查询", notes="学生信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<YdStudentInfo> queryById(@RequestParam(name="id",required=true) String id) {
		Result<YdStudentInfo> result = new Result<YdStudentInfo>();
		YdStudentInfo ydStudentInfo = ydStudentInfoService.getById(id);
		if(ydStudentInfo==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(ydStudentInfo);
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
      QueryWrapper<YdStudentInfo> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              YdStudentInfo ydStudentInfo = JSON.parseObject(deString, YdStudentInfo.class);
              queryWrapper = QueryGenerator.initQueryWrapper(ydStudentInfo, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<YdStudentInfo> pageList = ydStudentInfoService.list(queryWrapper);
      LoginUser user = (LoginUser)SecurityUtils.getSubject().getPrincipal(); // 登录账号 
      String tittle=null;
		if (user != null) {
			tittle="导出人:"+user.getUsername();
		}else {
			tittle="导出人:jeecg";
		}
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "学生信息表列表");
      //注解对象Class
      mv.addObject(NormalExcelConstants.CLASS, YdStudentInfo.class);
      //自定义表格参数
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("学生信息表列表数据", tittle, "导出信息"));
      //导出数据列表
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
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
//	  http://easypoi.mydoc.io/#text_217704  文档
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
//          是否需要保存上传的Excel
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
			              List<YdStudentInfo> listYdStudentInfos = ExcelImportUtil.importExcel(file.getInputStream(), YdStudentInfo.class, params);
			              //  需要测试  是否上下两个  按相同的顺序排序  防止excle 读取顺序不同
			              Optional<YdStudentInfo> ydStudentInfo=listYdStudentInfos.stream().filter(p -> sysUserExcel.getUsername().equals(p.getUsername())).findFirst();
			              System.out.println(ydStudentInfo.get()+"---------");
			              //现存到用户表 在存储到学生表 同时设置角色
//			                                          多角色时通过excle获取
//			              sysUserService.addUserWithRole(sysUserExcel, ydStudentInfo.get().getRole());
			              //只有学生角色时  直接固定role 
			              sysUserService.addUserWithRole(sysUserExcel, "39c038c874f52a6430b6e03bdcbad46f");
			              //根据用户名查询用户信息 
			              SysUser sysUser =sysUserService.getUserByName(sysUserExcel.getUsername());
			              ydStudentInfo.get().setUserId(sysUser.getId());
			              ydStudentInfoService.save(ydStudentInfo.get());
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
