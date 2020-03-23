package org.jeecg.modules.graduation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.graduation.entity.YdGroupPerson;
import org.jeecg.modules.graduation.entity.YdGroupPersonVo;
import org.jeecg.modules.graduation.entity.YdStudentInfo;
import org.jeecg.modules.graduation.service.IYdGroupPersonService;
import org.jeecg.modules.graduation.service.IYdStudentInfoService;
import org.jeecg.modules.system.entity.SysUserRole;
import org.jeecg.modules.system.vo.SysUserRoleVO;

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
 * @Description: 分组关系表
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags="分组关系表")
@RestController
@RequestMapping("/graduation/ydGroupPerson")
public class YdGroupPersonController {
	@Autowired
	private IYdGroupPersonService ydGroupPersonService;
	
	@Autowired
	private IYdStudentInfoService ydStudentInfoService;
	/**
	  * 分页列表查询
	 * @param ydGroupPerson
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "分组关系表-分页列表查询")
	@ApiOperation(value="分组关系表-分页列表查询", notes="分组关系表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<YdGroupPerson>> queryPageList(YdGroupPerson ydGroupPerson,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<YdGroupPerson>> result = new Result<IPage<YdGroupPerson>>();
		QueryWrapper<YdGroupPerson> queryWrapper = QueryGenerator.initQueryWrapper(ydGroupPerson, req.getParameterMap());
		Page<YdGroupPerson> page = new Page<YdGroupPerson>(pageNo, pageSize);
		IPage<YdGroupPerson> pageList = ydGroupPersonService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	
	/**
	  * 分页查询关联学生
	 * @param ydGroupPerson
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "分组关系表-分页列表查询")
	@ApiOperation(value="分组关系表-分页列表查询", notes="分组关系表-分页列表查询")
	@GetMapping(value = "/findGroupStudent")
	public Result<IPage<YdGroupPerson>> findGroupStudent(YdGroupPerson ydGroupPerson,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<YdGroupPerson>> result = new Result<IPage<YdGroupPerson>>();
		Page<YdGroupPerson> page = new Page<YdGroupPerson>(pageNo, pageSize);
		IPage<YdGroupPerson> pageList = ydGroupPersonService.findGroupStudent(page, ydGroupPerson);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	
	/**
	  * 分页查询老师
	 * @param ydGroupPerson
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "分组关系表-分页列表查询")
	@ApiOperation(value="分组关系表-分页列表查询", notes="分组关系表-分页列表查询")
	@GetMapping(value = "/findGroupTeacher")
	public Result<IPage<YdGroupPerson>> findGroupTeacher(YdGroupPerson ydGroupPerson,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<YdGroupPerson>> result = new Result<IPage<YdGroupPerson>>();
		Page<YdGroupPerson> page = new Page<YdGroupPerson>(pageNo, pageSize);
		IPage<YdGroupPerson> pageList = ydGroupPersonService.findGroupTeacher(page, ydGroupPerson);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	
	/**
	  *   添加
	 * @param ydGroupPerson
	 * @return
	 */
	@AutoLog(value = "分组关系表-添加")
	@ApiOperation(value="分组关系表-添加", notes="分组关系表-添加")
	@PostMapping(value = "/add")
	public Result<YdGroupPerson> add(@RequestBody YdGroupPerson ydGroupPerson) {
		Result<YdGroupPerson> result = new Result<YdGroupPerson>();
		try {
			if("0".equals(ydGroupPerson.getType())) {
				//改变学生小组状态
				YdStudentInfo ydStudentInfo = ydStudentInfoService.getById(ydGroupPerson.getRealId());
				ydStudentInfo.setIsGroup("0");
				ydStudentInfoService.updateById(ydStudentInfo);
			}
			ydGroupPersonService.save(ydGroupPerson);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	
	/**
	 * 给指定群组添加用户
	 *
	 * @param
	 * @return
	 */
	@AutoLog(value = "分组关系表-给指定群组添加用户")
	@ApiOperation(value="分组关系表-给指定群组添加用户", notes="分组关系表-给指定群组添加用户")
	@RequestMapping(value = "/addGroupUser")
	public Result<String> addGroupUser(@RequestBody YdGroupPersonVo ydGroupPersonVo) {
		Result<String> result = new Result<String>();
		try {
			String groupId = ydGroupPersonVo.getGroupId();
			String type = ydGroupPersonVo.getType();
			for (String realId : ydGroupPersonVo.getRealIdList()) {
				YdGroupPerson ydGroupPerson = new YdGroupPerson();
				ydGroupPerson.setGroupId(groupId);
				ydGroupPerson.setRealId(realId);
				ydGroupPerson.setType(type);
				QueryWrapper<YdGroupPerson> queryWrapper = new QueryWrapper<YdGroupPerson>();
				queryWrapper.eq("group_id", groupId).eq("real_id", realId).eq("type", type);
				YdGroupPerson one = ydGroupPersonService.getOne(queryWrapper);
				if (one == null) {
					ydGroupPersonService.save(ydGroupPerson);
				}
				if("0".equals(type)) {
					//改变学生小组状态
					YdStudentInfo ydStudentInfo = ydStudentInfoService.getById(ydGroupPerson.getRealId());
					ydStudentInfo.setIsGroup("0");
					ydStudentInfoService.updateById(ydStudentInfo);
				}

			}
			result.setMessage("添加成功!");
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("出错了: " + e.getMessage());
			return result;
		}
	}
	
	
	/**
	  *  编辑
	 * @param ydGroupPerson
	 * @return
	 */
	@AutoLog(value = "分组关系表-编辑")
	@ApiOperation(value="分组关系表-编辑", notes="分组关系表-编辑")
	@PutMapping(value = "/edit")
	public Result<YdGroupPerson> edit(@RequestBody YdGroupPerson ydGroupPerson) {
		Result<YdGroupPerson> result = new Result<YdGroupPerson>();
		YdGroupPerson ydGroupPersonEntity = ydGroupPersonService.getById(ydGroupPerson.getId());
		if(ydGroupPersonEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = ydGroupPersonService.updateById(ydGroupPerson);
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
	@AutoLog(value = "分组关系表-通过id删除")
	@ApiOperation(value="分组关系表-通过id删除", notes="分组关系表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			YdGroupPerson ydGroupPerson = ydGroupPersonService.getById(id);
			if("0".equals(ydGroupPerson.getType())) {
				//解除加入小组状态
				YdStudentInfo ydStudentInfo = ydStudentInfoService.getById(ydGroupPerson.getRealId());
				ydStudentInfo.setIsGroup("1");
				ydStudentInfoService.updateById(ydStudentInfo);
			}
			ydGroupPersonService.removeById(id);
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
	@AutoLog(value = "分组关系表-批量删除")
	@ApiOperation(value="分组关系表-批量删除", notes="分组关系表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<YdGroupPerson> deleteBatch(@RequestParam(name="ids",required=true) String ids,@RequestParam(name="type",defaultValue="1") String type) {
		Result<YdGroupPerson> result = new Result<YdGroupPerson>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			if("0".equals(type)) {
				//如果是学生需要解除自身组
				//根据id批量查询
				Collection<YdGroupPerson> ydGroupPersons=this.ydGroupPersonService.listByIds(Arrays.asList(ids.split(",")));
				//collection转list   查出来所有的list
				List<YdGroupPerson> studentItems = new ArrayList<YdGroupPerson>(ydGroupPersons);
				for (YdGroupPerson item : studentItems) {
					//查询该学生信息
					YdStudentInfo ydStudentInfo = ydStudentInfoService.getById(item.getRealId());
					ydStudentInfo.setIsGroup("1");
					ydStudentInfoService.updateById(ydStudentInfo);
				}
			}
			this.ydGroupPersonService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "分组关系表-通过id查询")
	@ApiOperation(value="分组关系表-通过id查询", notes="分组关系表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<YdGroupPerson> queryById(@RequestParam(name="id",required=true) String id) {
		Result<YdGroupPerson> result = new Result<YdGroupPerson>();
		YdGroupPerson ydGroupPerson = ydGroupPersonService.getById(id);
		if(ydGroupPerson==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(ydGroupPerson);
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
      QueryWrapper<YdGroupPerson> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              YdGroupPerson ydGroupPerson = JSON.parseObject(deString, YdGroupPerson.class);
              queryWrapper = QueryGenerator.initQueryWrapper(ydGroupPerson, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<YdGroupPerson> pageList = ydGroupPersonService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "分组关系表列表");
      mv.addObject(NormalExcelConstants.CLASS, YdGroupPerson.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("分组关系表列表数据", "导出人:Jeecg", "导出信息"));
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
              List<YdGroupPerson> listYdGroupPersons = ExcelImportUtil.importExcel(file.getInputStream(), YdGroupPerson.class, params);
              ydGroupPersonService.saveBatch(listYdGroupPersons);
              return Result.ok("文件导入成功！数据行数:" + listYdGroupPersons.size());
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
