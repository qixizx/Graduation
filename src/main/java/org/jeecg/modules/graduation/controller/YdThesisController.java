package org.jeecg.modules.graduation.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.graduation.entity.YdStudentInfo;
import org.jeecg.modules.graduation.entity.YdSubjectInfo;
import org.jeecg.modules.graduation.entity.YdThesis;
import org.jeecg.modules.graduation.service.IYdStudentInfoService;
import org.jeecg.modules.graduation.service.IYdSubjectInfoService;
import org.jeecg.modules.graduation.service.IYdThesisService;
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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: 论文表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags="论文表")
@RestController
@RequestMapping("/graduation/ydThesis")
public class YdThesisController {
	@Autowired
	private IYdThesisService ydThesisService;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private IYdStudentInfoService ydStudentInfoService;
	@Autowired
	private IYdSubjectInfoService ydSubjectInfoService;
	/**
	  * 分页列表查询
	 * @param ydThesis
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "论文表-分页列表查询")
	@ApiOperation(value="论文表-分页列表查询", notes="论文表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<YdThesis>> queryPageList(YdThesis ydThesis,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<YdThesis>> result = new Result<IPage<YdThesis>>();
//		QueryWrapper<YdThesis> queryWrapper = QueryGenerator.initQueryWrapper(ydThesis, req.getParameterMap());
		Page<YdThesis> page = new Page<YdThesis>(pageNo, pageSize);
//		IPage<YdThesis> pageList = ydThesisService.page(page, queryWrapper);
		IPage<YdThesis> pageList = ydThesisService.findThesisPageList(page,ydThesis);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  * 通过登录人查询
	 * @return
	 */
	@AutoLog(value = "选题信息表-通过登录人查询")
	@ApiOperation(value="选题信息表-通过登录人查询", notes="选题信息表-通过登录人查询")
	@GetMapping(value = "/queryLogin")
	public Result<YdThesis> queryLoginThesisInfo() {
		Result<YdThesis> result = new Result<YdThesis>();
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
				.getPrincipal();
		YdStudentInfo ydStudentInfo =ydStudentInfoService.findGroupTutor(sysUser.getUsername());
		YdThesis ydThesis = ydThesisService.getThesisByStuId(ydStudentInfo.getId());
		if(ydThesis==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(ydThesis);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  *   添加
	 * @param ydThesis
	 * @return
	 */
	@AutoLog(value = "论文表-添加")
	@ApiOperation(value="论文表-添加", notes="论文表-添加")
	@PostMapping(value = "/add")
	public Result<YdThesis> add(@RequestBody YdThesis ydThesis) {
		Result<YdThesis> result = new Result<YdThesis>();
		try {
			ydThesis.setState("0");
			ydThesis.setCommitTime(new Date());
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject()
					.getPrincipal();
			List<String> list = sysUserService.getRole(sysUser.getUsername());
			// 判断是否是学生
			if (list.contains("student")) {
				//设置 提交状态
				if(ydThesis.getFileId() !=null && !"".equals(ydThesis.getFileId())) {
					ydThesis.setState("1");
				}else {
					ydThesis.setState("0");
				}
				YdStudentInfo ydStudentInfo =ydStudentInfoService.findGroupTutor(sysUser.getUsername());
				//设置学生id
				ydThesis.setStuId(ydStudentInfo.getId());
				if(ydStudentInfo.getTeacherId()==null || "".equals(ydStudentInfo.getTeacherId())) {
					result.error500("操作失败,您当前为加入设计小组，请联系导师加入小组！");
					return result;
				}else {
					QueryWrapper<YdSubjectInfo> queryWrapper = new QueryWrapper<YdSubjectInfo>();
					queryWrapper.eq("stu_id", ydStudentInfo.getId());
					YdSubjectInfo ydSubjectInfo = ydSubjectInfoService.getOne(queryWrapper);
					//设置选题id
					ydThesis.setSubjectId(ydSubjectInfo.getId());
				}
				
			}
			ydThesisService.save(ydThesis);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param ydThesis
	 * @return
	 */
	@AutoLog(value = "论文表-编辑")
	@ApiOperation(value="论文表-编辑", notes="论文表-编辑")
	@PutMapping(value = "/edit")
	public Result<YdThesis> edit(@RequestBody YdThesis ydThesis) {
		Result<YdThesis> result = new Result<YdThesis>();
		YdThesis ydThesisEntity = ydThesisService.getById(ydThesis.getId());
		if(ydThesisEntity==null) {
			result.error500("未找到对应实体");
		}else {
			ydThesis.setCommitTime(new Date());
			boolean ok = ydThesisService.updateById(ydThesis);
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
	@AutoLog(value = "论文表-通过id删除")
	@ApiOperation(value="论文表-通过id删除", notes="论文表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			ydThesisService.removeById(id);
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
	@AutoLog(value = "论文表-批量删除")
	@ApiOperation(value="论文表-批量删除", notes="论文表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<YdThesis> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<YdThesis> result = new Result<YdThesis>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.ydThesisService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "论文表-通过id查询")
	@ApiOperation(value="论文表-通过id查询", notes="论文表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<YdThesis> queryById(@RequestParam(name="id",required=true) String id) {
		Result<YdThesis> result = new Result<YdThesis>();
		YdThesis ydThesis = ydThesisService.getById(id);
		if(ydThesis==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(ydThesis);
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
      QueryWrapper<YdThesis> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              YdThesis ydThesis = JSON.parseObject(deString, YdThesis.class);
              queryWrapper = QueryGenerator.initQueryWrapper(ydThesis, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<YdThesis> pageList = ydThesisService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "论文表列表");
      mv.addObject(NormalExcelConstants.CLASS, YdThesis.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("论文表列表数据", "导出人:Jeecg", "导出信息"));
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
              List<YdThesis> listYdThesiss = ExcelImportUtil.importExcel(file.getInputStream(), YdThesis.class, params);
              ydThesisService.saveBatch(listYdThesiss);
              return Result.ok("文件导入成功！数据行数:" + listYdThesiss.size());
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
