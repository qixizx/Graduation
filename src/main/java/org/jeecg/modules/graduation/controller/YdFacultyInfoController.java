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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.graduation.entity.YdFacultyInfo;
import org.jeecg.modules.graduation.service.IYdFacultyInfoService;
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
 * @Description: 院系表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags="院系表")
@RestController
@RequestMapping("/graduation/ydFacultyInfo")
public class YdFacultyInfoController {
	@Autowired
	private IYdFacultyInfoService ydFacultyInfoService;
	
	/**
	  * 分页列表查询
	 * @param ydFacultyInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "院系表-分页列表查询")
	@ApiOperation(value="院系表-分页列表查询", notes="院系表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<YdFacultyInfo>> queryPageList(YdFacultyInfo ydFacultyInfo,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<YdFacultyInfo>> result = new Result<IPage<YdFacultyInfo>>();
		QueryWrapper<YdFacultyInfo> queryWrapper = QueryGenerator.initQueryWrapper(ydFacultyInfo, req.getParameterMap());
		Page<YdFacultyInfo> page = new Page<YdFacultyInfo>(pageNo, pageSize);
		IPage<YdFacultyInfo> pageList = ydFacultyInfoService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param ydFacultyInfo
	 * @return
	 */
	@RequiresPermissions("faculty:add")
	@AutoLog(value = "院系表-添加")
	@ApiOperation(value="院系表-添加", notes="院系表-添加")
	@PostMapping(value = "/add")
	public Result<YdFacultyInfo> add(@RequestBody YdFacultyInfo ydFacultyInfo) {
		Result<YdFacultyInfo> result = new Result<YdFacultyInfo>();
		try {
			ydFacultyInfoService.save(ydFacultyInfo);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param ydFacultyInfo
	 * @return
	 */
	@AutoLog(value = "院系表-编辑")
	@ApiOperation(value="院系表-编辑", notes="院系表-编辑")
	@PutMapping(value = "/edit")
	public Result<YdFacultyInfo> edit(@RequestBody YdFacultyInfo ydFacultyInfo) {
		Result<YdFacultyInfo> result = new Result<YdFacultyInfo>();
		YdFacultyInfo ydFacultyInfoEntity = ydFacultyInfoService.getById(ydFacultyInfo.getId());
		if(ydFacultyInfoEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = ydFacultyInfoService.updateById(ydFacultyInfo);
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
	@AutoLog(value = "院系表-通过id删除")
	@ApiOperation(value="院系表-通过id删除", notes="院系表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			ydFacultyInfoService.removeById(id);
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
	@AutoLog(value = "院系表-批量删除")
	@ApiOperation(value="院系表-批量删除", notes="院系表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<YdFacultyInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<YdFacultyInfo> result = new Result<YdFacultyInfo>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.ydFacultyInfoService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "院系表-通过id查询")
	@ApiOperation(value="院系表-通过id查询", notes="院系表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<YdFacultyInfo> queryById(@RequestParam(name="id",required=true) String id) {
		Result<YdFacultyInfo> result = new Result<YdFacultyInfo>();
		YdFacultyInfo ydFacultyInfo = ydFacultyInfoService.getById(id);
		if(ydFacultyInfo==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(ydFacultyInfo);
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
      QueryWrapper<YdFacultyInfo> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              YdFacultyInfo ydFacultyInfo = JSON.parseObject(deString, YdFacultyInfo.class);
              queryWrapper = QueryGenerator.initQueryWrapper(ydFacultyInfo, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<YdFacultyInfo> pageList = ydFacultyInfoService.list(queryWrapper);
      LoginUser user = (LoginUser)SecurityUtils.getSubject().getPrincipal(); // 登录账号 
      String tittle=null;
		if (user != null) {
			tittle="导出人:"+user.getUsername();
		}else {
			tittle="导出人:jeecg";
		}
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "院系表列表");
      mv.addObject(NormalExcelConstants.CLASS, YdFacultyInfo.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("院系表列表数据", tittle, "导出信息"));
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
  @RequiresPermissions("faculty:import")
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
              List<YdFacultyInfo> listYdFacultyInfos = ExcelImportUtil.importExcel(file.getInputStream(), YdFacultyInfo.class, params);
              ydFacultyInfoService.saveBatch(listYdFacultyInfos);
              return Result.ok("文件导入成功！数据行数:" + listYdFacultyInfos.size());
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
