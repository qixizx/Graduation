package org.jeecg.modules.graduation.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.graduation.entity.YdGroup;
import org.jeecg.modules.graduation.entity.YdGroupPerson;
import org.jeecg.modules.graduation.entity.YdStudentInfo;
import org.jeecg.modules.graduation.entity.YdSubjectInfo;
import org.jeecg.modules.graduation.entity.YdTeacherInfo;
import org.jeecg.modules.graduation.service.IYdGroupService;
import org.jeecg.modules.graduation.service.IYdStudentInfoService;
import org.jeecg.modules.graduation.service.IYdSubjectInfoService;
import org.jeecg.modules.graduation.service.IYdTeacherInfoService;
import org.jeecg.modules.system.service.ISysUserService;

import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 选题信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags="选题信息表")
@RestController
@RequestMapping("/graduation/ydSubjectInfo")
public class YdSubjectInfoController {
	@Autowired
	private IYdSubjectInfoService ydSubjectInfoService;
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private IYdStudentInfoService ydStudentInfoService;
	/**
	  * 分页列表查询
	 * @param ydSubjectInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "选题信息表-分页列表查询")
	@ApiOperation(value="选题信息表-分页列表查询", notes="选题信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<YdSubjectInfo>> queryPageList(YdSubjectInfo ydSubjectInfo,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<YdSubjectInfo>> result = new Result<IPage<YdSubjectInfo>>();
//		QueryWrapper<YdSubjectInfo> queryWrapper = QueryGenerator.initQueryWrapper(ydSubjectInfo, req.getParameterMap());
		Page<YdSubjectInfo> page = new Page<YdSubjectInfo>(pageNo, pageSize);
//		IPage<YdSubjectInfo> pageList = ydSubjectInfoService.page(page, queryWrapper);
		IPage<YdSubjectInfo> pageList = ydSubjectInfoService.findSubjectPageList(page,ydSubjectInfo);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  * 通过登录人查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "选题信息表-通过登录人查询")
	@ApiOperation(value="选题信息表-通过登录人查询", notes="选题信息表-通过登录人查询")
	@GetMapping(value = "/queryLogin")
	public Result<YdSubjectInfo> queryLoginSubjectInfo() {
		Result<YdSubjectInfo> result = new Result<YdSubjectInfo>();
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		YdStudentInfo ydStudentInfo =ydStudentInfoService.findGroupTutor(sysUser.getUsername());
		QueryWrapper<YdSubjectInfo> queryWrapper = new QueryWrapper<YdSubjectInfo>();
		queryWrapper.eq("stu_id", ydStudentInfo.getId());
		YdSubjectInfo ydSubjectInfo = ydSubjectInfoService.getOne(queryWrapper);
		if(ydSubjectInfo==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(ydSubjectInfo);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  *   添加
	 * @param ydSubjectInfo
	 * @return
	 */
	@AutoLog(value = "选题信息表-添加")
	@ApiOperation(value="选题信息表-添加", notes="选题信息表-添加")
	@PostMapping(value = "/add")
	public Result<YdSubjectInfo> add(@RequestBody YdSubjectInfo ydSubjectInfo) {
		Result<YdSubjectInfo> result = new Result<YdSubjectInfo>();
		try {
			ydSubjectInfo.setCommitTime(new Date());
			
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
					.getPrincipal();
			List<String> list = sysUserService.getRole(sysUser.getUsername());
			// 判断是否是学生
			if (list.contains("student")) {
				//设置 提交状态
				ydSubjectInfo.setState("1");
				
				YdStudentInfo ydStudentInfo =ydStudentInfoService.findGroupTutor(sysUser.getUsername());
				//设置学生id
				ydSubjectInfo.setStuId(ydStudentInfo.getId());
				if(ydStudentInfo.getTeacherId()==null || "".equals(ydStudentInfo.getTeacherId())) {
					result.error500("操作失败,您当前为加入设计小组，请联系导师加入小组！");
					return result;
				}else {
					
					//设置老师id
					ydSubjectInfo.setTeacherId(ydStudentInfo.getTeacherId());
				}
				
			}else {
				//如果状态为1不能提交新的选题信息
				if(ydSubjectInfo.getState()==null || "".equals( ydSubjectInfo.getState().trim())) {
					ydSubjectInfo.setState("0");
				}
			}
			ydSubjectInfoService.save(ydSubjectInfo);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param ydSubjectInfo
	 * @return
	 */
	@AutoLog(value = "选题信息表-编辑")
	@ApiOperation(value="选题信息表-编辑", notes="选题信息表-编辑")
	@PutMapping(value = "/edit")
	public Result<YdSubjectInfo> edit(@RequestBody YdSubjectInfo ydSubjectInfo) {
		Result<YdSubjectInfo> result = new Result<YdSubjectInfo>();
		YdSubjectInfo ydSubjectInfoEntity = ydSubjectInfoService.getById(ydSubjectInfo.getId());
		if(ydSubjectInfoEntity==null) {
			result.error500("未找到对应实体");
		}else {
			if(ydSubjectInfo.getState()==null || "".equals( ydSubjectInfo.getState().trim())) {
				ydSubjectInfo.setState("0");
			}
			boolean ok = ydSubjectInfoService.updateById(ydSubjectInfo);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "选题信息表-通过id删除")
	@ApiOperation(value="选题信息表-通过id删除", notes="选题信息表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			ydSubjectInfoService.removeById(id);
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
	@AutoLog(value = "选题信息表-批量删除")
	@ApiOperation(value="选题信息表-批量删除", notes="选题信息表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<YdSubjectInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<YdSubjectInfo> result = new Result<YdSubjectInfo>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.ydSubjectInfoService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "选题信息表-通过id查询")
	@ApiOperation(value="选题信息表-通过id查询", notes="选题信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<YdSubjectInfo> queryById(@RequestParam(name="id",required=true) String id) {
		Result<YdSubjectInfo> result = new Result<YdSubjectInfo>();
		YdSubjectInfo ydSubjectInfo = ydSubjectInfoService.getById(id);
		if(ydSubjectInfo==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(ydSubjectInfo);
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
      QueryWrapper<YdSubjectInfo> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              YdSubjectInfo ydSubjectInfo = JSON.parseObject(deString, YdSubjectInfo.class);
              queryWrapper = QueryGenerator.initQueryWrapper(ydSubjectInfo, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<YdSubjectInfo> pageList = ydSubjectInfoService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "选题信息表列表");
      mv.addObject(NormalExcelConstants.CLASS, YdSubjectInfo.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("选题信息表列表数据", "导出人:Jeecg", "导出信息"));
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<YdSubjectInfo> listYdSubjectInfos = ExcelImportUtil.importExcel(file.getInputStream(), YdSubjectInfo.class, params);
              ydSubjectInfoService.saveBatch(listYdSubjectInfos);
              return Result.ok("文件导入成功！数据行数:" + listYdSubjectInfos.size());
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
      return Result.ok("文件导入失败！");
  }

}
