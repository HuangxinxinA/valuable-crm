package org.jeecg.modules.valuable.controller.alibaba;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.system.entity.SysLog;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysLogService;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.modules.valuable.dto.ChengXingChengYiDTO;
import org.jeecg.modules.valuable.dto.ISVDTO;
import org.jeecg.modules.valuable.entity.ValAlibabaClient;
import org.jeecg.modules.valuable.service.IValAlibabaClientService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.valuable.vo.ValAlibabaClientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 阿里客户
 * @Author: jeecg-boot
 * @Date: 2024-01-19
 * @Version: V1.0
 */
@Api(tags = "阿里客户")
@RestController
@RequestMapping("/val/alibabaClient")
@Slf4j
public class ValAlibabaClientController extends JeecgController<ValAlibabaClient, IValAlibabaClientService> {
    @Autowired
    private IValAlibabaClientService valAlibabaClientService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysLogService logService;

    /**
     * 分页列表查询
     *
     * @param valAlibabaClient
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "阿里客户-分页列表查询")
    @ApiOperation(value = "阿里客户-管理员-客户列表分页列表查询", notes = "阿里客户-管理员-客户列表分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ValAlibabaClientVO>> queryPageList(ValAlibabaClient valAlibabaClient,
                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                           HttpServletRequest req) {
        QueryWrapper<ValAlibabaClient> queryWrapper = QueryGenerator.initQueryWrapper(valAlibabaClient, req.getParameterMap());
        Page<ValAlibabaClient> page = new Page<ValAlibabaClient>(pageNo, pageSize);
        IPage<ValAlibabaClient> pageList = valAlibabaClientService.page(page, queryWrapper);
        List<SysUser> userList = userService.list();
        List<ValAlibabaClientVO> valAlibabaClientVOList = JSONArray.parseArray(JSON.toJSONString(pageList.getRecords()), ValAlibabaClientVO.class);
        for (ValAlibabaClientVO valAlibabaClientVO : valAlibabaClientVOList) {
            Optional<SysUser> managerOptional = userList.stream()
                    .filter(obj -> obj.getId().equals(valAlibabaClientVO.getManager())).findFirst();
            managerOptional.ifPresent(sysUser -> valAlibabaClientVO.setManagerName(sysUser.getRealname()));
            Optional<SysUser> operatorOptional = userList.stream()
                    .filter(obj -> obj.getId().equals(valAlibabaClientVO.getOperator())).findFirst();
            operatorOptional.ifPresent(sysUser -> valAlibabaClientVO.setOperatorName(sysUser.getRealname()));
        }
        IPage<ValAlibabaClientVO> result = new Page<>();
        result.setPages(pageList.getPages());
        result.setCurrent(pageList.getCurrent());
        result.setTotal(pageList.getTotal());
        result.setSize(pageList.getSize());
        result.setRecords(valAlibabaClientVOList);
        return Result.OK(result);
    }

    @ApiOperation(value = "阿里客户-业务员-我的客户分页列表查询", notes = "阿里客户-业务员-我的客户分页列表查询")
    @RequestMapping(value = "/listByToken", method = RequestMethod.GET)
    public Result<IPage<ValAlibabaClientVO>> listByToken(ValAlibabaClient valAlibabaClient,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         HttpServletRequest req) {
        //直接获取当前用户
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (oConvertUtils.isEmpty(loginUser)) {
            return Result.error("请登录系统！");
        }
        //只查询客户经理是该用户的客户
        QueryWrapper<ValAlibabaClient> queryWrapper = QueryGenerator.initQueryWrapper(valAlibabaClient, req.getParameterMap());
        queryWrapper.eq("manager", loginUser.getId());
        Page<ValAlibabaClient> page = new Page<>(pageNo, pageSize);
        IPage<ValAlibabaClient> pageList = valAlibabaClientService.page(page, queryWrapper);
        List<SysUser> userList = userService.list();
        List<ValAlibabaClientVO> valAlibabaClientVOList = JSONArray.parseArray(JSON.toJSONString(pageList.getRecords()), ValAlibabaClientVO.class);
        for (ValAlibabaClientVO valAlibabaClientVO : valAlibabaClientVOList) {
            Optional<SysUser> managerOptional = userList.stream()
                    .filter(obj -> obj.getId().equals(valAlibabaClientVO.getManager())).findFirst();
            managerOptional.ifPresent(sysUser -> valAlibabaClientVO.setManagerName(sysUser.getRealname()));
            Optional<SysUser> operatorOptional = userList.stream()
                    .filter(obj -> obj.getId().equals(valAlibabaClientVO.getOperator())).findFirst();
            operatorOptional.ifPresent(sysUser -> valAlibabaClientVO.setOperatorName(sysUser.getRealname()));
        }
        IPage<ValAlibabaClientVO> result = new Page<>();
        result.setPages(pageList.getPages());
        result.setCurrent(pageList.getCurrent());
        result.setTotal(pageList.getTotal());
        result.setSize(pageList.getSize());
        result.setRecords(valAlibabaClientVOList);
        return Result.OK(result);
    }

    @ApiOperation(value = "阿里客户-公海客户分页列表查询", notes = "阿里客户-公海客户分页列表查询")
    @RequestMapping(value = "/listPublic", method = RequestMethod.GET)
    public Result<IPage<ValAlibabaClient>> listPublic(ValAlibabaClient valAlibabaClient,
                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                      HttpServletRequest req) {
        QueryWrapper<ValAlibabaClient> queryWrapper = QueryGenerator.initQueryWrapper(valAlibabaClient, req.getParameterMap());
        queryWrapper.eq("is_public", 1);
        queryWrapper.eq("is_follow", 0);
        Page<ValAlibabaClient> page = new Page<>(pageNo, pageSize);
        IPage<ValAlibabaClient> pageList = valAlibabaClientService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    @ApiOperation(value = "阿里客户-管理员-可加为公海客户的分页列表查询", notes = "阿里客户-管理员-可加为公海客户的分页列表查询")
    @RequestMapping(value = "/listEnabledPublic", method = RequestMethod.GET)
    public Result<List<ValAlibabaClient>> listEnabledPublic(ValAlibabaClient valAlibabaClient,
                                                            HttpServletRequest req) {
        QueryWrapper<ValAlibabaClient> queryWrapper = QueryGenerator.initQueryWrapper(valAlibabaClient, req.getParameterMap());
        //非公海
        queryWrapper.eq("is_public", 0);
        //非业务员添加的
        queryWrapper.eq("is_private", 0);
        //无业务员跟进的
        queryWrapper.eq("is_follow", 0);
        List<ValAlibabaClient> list = valAlibabaClientService.list(queryWrapper);
        return Result.OK(list);
    }

    /**
     * 添加
     *
     * @param valAlibabaClient
     * @return
     */
    @AutoLog(value = "阿里客户-管理员-添加")
    @ApiOperation(value = "阿里客户-管理员-添加", notes = "阿里客户-管理员-添加")
    @RequiresPermissions("val:alibabaClient:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ValAlibabaClient valAlibabaClient) {
        //校验账号是否已存在
        if (StringUtils.isBlank(valAlibabaClient.getAccount())) {
            return Result.error("客户阿里账号不能为空");
        }
        LambdaQueryWrapper<ValAlibabaClient> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ValAlibabaClient::getAccount, valAlibabaClient.getAccount());
        ValAlibabaClient one = valAlibabaClientService.getOne(queryWrapper);
        if (one != null) {
            return Result.error("该客户阿里账号已存在");
        }
        valAlibabaClientService.save(valAlibabaClient);
        return Result.OK("添加成功！");
    }

    /**
     * 添加
     *
     * @param valAlibabaClient
     * @return
     */
    @AutoLog(value = "阿里客户-业务员-添加")
    @ApiOperation(value = "阿里客户-业务员-添加", notes = "阿里客户-业务员-添加")
    @RequiresPermissions("val:alibabaClient:addPrivate")
    @PostMapping(value = "/addPrivate")
    public Result<String> addPrivate(@RequestBody ValAlibabaClient valAlibabaClient) {
        //校验账号是否已存在
        if (StringUtils.isBlank(valAlibabaClient.getAccount())) {
            return Result.error("客户阿里账号不能为空");
        }
        //直接获取当前用户
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (oConvertUtils.isEmpty(loginUser)) {
            return Result.error("请登录系统！");
        }
        LambdaQueryWrapper<ValAlibabaClient> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ValAlibabaClient::getAccount, valAlibabaClient.getAccount());
        ValAlibabaClient one = valAlibabaClientService.getOne(queryWrapper);
        if (one != null) {
            return Result.error("该客户阿里账号已存在");
        }
        //业务员添加标志
        valAlibabaClient.setIsPrivate(1);
        valAlibabaClient.setIsFollow(1);
        valAlibabaClient.setManager(loginUser.getId());
        valAlibabaClientService.save(valAlibabaClient);
        return Result.OK("添加成功！");
    }

    /**
     * 批量添加公海客户
     *
     * @param valAlibabaClient
     * @return
     */
    @AutoLog(value = "阿里客户-管理员-批量添加公海客户")
    @ApiOperation(value = "阿里客户-管理员-批量添加公海客户", notes = "阿里客户-管理员-批量添加公海客户")
    @RequiresPermissions("val:alibabaClient:addBatchPublic")
    @PostMapping(value = "/addBatchPublic")
    public Result<String> addBatchPublic(@RequestBody ValAlibabaClient valAlibabaClient) {
        if (StringUtils.isBlank(valAlibabaClient.getId())) {
            return Result.error("客户ID不能为空");
        }
        List<String> idList = Arrays.asList(valAlibabaClient.getId().split(","));
        List<ValAlibabaClient> clientList = valAlibabaClientService.listByIds(idList);
        if (idList.size() != clientList.size()) {
            return Result.error("客户不存在，请联系管理员");
        }
        for (ValAlibabaClient client : clientList) {
            client.setIsPublic(1);
        }
        valAlibabaClientService.updateBatchById(clientList);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "阿里客户-业务员-加为我的客户")
    @ApiOperation(value = "阿里客户-业务员-加为我的客户", notes = "阿里客户-业务员-加为我的客户")
    @RequiresPermissions("val:alibabaClient:addMyClient")
    @PostMapping(value = "/addMyClient")
    public Result<String> addMyClient(@RequestBody ValAlibabaClient valAlibabaClient) {
        if (StringUtils.isBlank(valAlibabaClient.getId()) || StringUtils.isBlank(valAlibabaClient.getManager())) {
            return Result.error("客户ID和用户ID不能为空");
        }
        ValAlibabaClient client = valAlibabaClientService.getById(valAlibabaClient.getId());
        if (client == null) {
            return Result.error("客户不存在，请联系管理员");
        }
        client.setManager(valAlibabaClient.getManager());
        client.setIsFollow(1);
        valAlibabaClientService.updateById(client);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "阿里客户-管理员-移出公海")
    @ApiOperation(value = "阿里客户-管理员-移出公海", notes = "阿里客户-管理员-移出公海")
    @RequiresPermissions("val:alibabaClient:removePublic")
    @PostMapping(value = "/removePublic")
    public Result<String> removePublic(@RequestBody ValAlibabaClient valAlibabaClient) {
        if (StringUtils.isBlank(valAlibabaClient.getId())) {
            return Result.error("客户ID不能为空");
        }
        List<String> idList = Arrays.asList(valAlibabaClient.getId().split(","));
        List<ValAlibabaClient> clientList = valAlibabaClientService.listByIds(idList);
        if (idList.size() != clientList.size()) {
            return Result.error("客户不存在，请联系管理员");
        }
        for (ValAlibabaClient client : clientList) {
            client.setIsPublic(0);
        }
        valAlibabaClientService.updateBatchById(clientList);
        return Result.OK("移出成功！");
    }

    @AutoLog(value = "阿里客户-业务员-移入公海")
    @ApiOperation(value = "阿里客户-业务员-移入公海", notes = "阿里客户-业务员-移入公海")
    @RequiresPermissions("val:alibabaClient:returnPublic")
    @PostMapping(value = "/returnPublic")
    public Result<String> returnPublic(@RequestBody ValAlibabaClient valAlibabaClient) {
        if (StringUtils.isBlank(valAlibabaClient.getId())) {
            return Result.error("客户ID不能为空");
        }
        //判断是否为原公海客户
        ValAlibabaClient client = valAlibabaClientService.getById(valAlibabaClient.getId());
        if (client == null) {
            return Result.error("客户不存在，请联系管理员");
        }
        if (client.getIsPublic() == 0) {
            return Result.error("该客户非原公海客户，移入失败");
        }
        client.setIsFollow(0);
        client.setManager(null);
        valAlibabaClientService.updateById(client);
        return Result.OK("移入成功！");
    }

    /**
     * 编辑
     *
     * @param valAlibabaClient
     * @return
     */
    @AutoLog(value = "阿里客户-编辑")
    @ApiOperation(value = "阿里客户-编辑", notes = "阿里客户-编辑")
    @RequiresPermissions("val:alibabaClient:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ValAlibabaClient valAlibabaClient) {
        valAlibabaClientService.updateById(valAlibabaClient);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "阿里客户-通过id删除")
    @ApiOperation(value = "阿里客户-通过id删除", notes = "阿里客户-通过id删除")
    @RequiresPermissions("val:alibabaClient:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        valAlibabaClientService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "阿里客户-批量删除")
    @ApiOperation(value = "阿里客户-批量删除", notes = "阿里客户-批量删除")
    @RequiresPermissions("val:alibabaClient:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.valAlibabaClientService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "阿里客户-通过id查询")
    @ApiOperation(value = "阿里客户-通过id查询", notes = "阿里客户-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ValAlibabaClient> queryById(@RequestParam(name = "id", required = true) String id) {
        ValAlibabaClient valAlibabaClient = valAlibabaClientService.getById(id);
        if (valAlibabaClient == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(valAlibabaClient);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param valAlibabaClient
     */
    @RequiresPermissions("val:alibabaClient:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ValAlibabaClient valAlibabaClient) {
        return super.exportXls(request, valAlibabaClient, ValAlibabaClient.class, "阿里客户");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("val:alibabaClient:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        //判断文件名是否包含橙星橙意、ISV
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        MultipartFile multipartFile = new ArrayList<>(fileMap.values()).get(0);
        String fileName = multipartFile.getOriginalFilename();
        if (fileName.startsWith("阿里客户")) {
            return super.importExcel(request, response, ValAlibabaClient.class);
        } else if (fileName.startsWith("橙星橙意")) {
            return super.importExcel(request, response, ValAlibabaClient.class, 0, 1, ChengXingChengYiDTO.class);
        } else if (fileName.startsWith("ISV")) {
            return super.importExcel(request, response, ValAlibabaClient.class, 0, 1, ISVDTO.class);
        } else {
            return Result.error("文件名不符合导入格式");
        }
    }

    /**
     * 概览
     *
     * @param req
     * @return
     */
    //@AutoLog(value = "阿里客户-概览")
    @ApiOperation(value = "阿里客户-概览", notes = "阿里客户-概览")
    @GetMapping(value = "/overview")
    public Result<JSONObject> overview(HttpServletRequest req) {
        //直接获取当前用户
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (oConvertUtils.isEmpty(loginUser)) {
            return Result.error("请登录系统！");
        }
        List<ValAlibabaClient> list = valAlibabaClientService.list();
        //筛选出未分配的公海客户
        long publicCount = list.stream().filter(obj -> obj.getIsPublic() == 1 && obj.getIsFollow() == 0).count();
        //筛选出我的客户
        long privateCount = list.stream().filter(obj -> obj.getManager().equals(loginUser.getId())).count();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("publicCount", publicCount);
        jsonObject.put("privateCount", privateCount);
        return Result.OK(jsonObject);
    }

    /**
     * 客户操作日志-取最新的6条
     *
     * @param req
     * @return
     */
    //@AutoLog(value = "阿里客户-概览")
    @ApiOperation(value = "阿里客户-操作日志查询", notes = "阿里客户-操作日志查询")
    @GetMapping(value = "/log")
    public Result<List<JSONObject>> log(HttpServletRequest req) {
        //查询日志：管理员添加公海客户、管理员将客户移出公海、业务员从公海中添加客户、业务员将客户移入公海、
        // 业务员新增客户、业务员编辑客户、业务员更新客户跟进信息、业务员删除客户
        LambdaQueryWrapper<SysLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SysLog::getLogContent, "阿里客户");
        queryWrapper.orderByDesc(SysLog::getCreateTime);
        queryWrapper.last("limit 10");
        List<SysLog> list = logService.list(queryWrapper);
        //查询所有用户的信息
        List<SysUser> userList = userService.list();
        //组装操作日志
        List<JSONObject> result = new ArrayList<>();
        for (SysLog sysLog : list) {
            JSONObject jsonObject = new JSONObject();
            //avatar
            Optional<SysUser> optional = userList.stream().filter(obj -> obj.getUsername().equals(sysLog.getUserid()))
                    .findFirst();
            optional.ifPresent(sysUser -> jsonObject.put("avatar", sysUser.getAvatar()));
            //name
            jsonObject.put("name", sysLog.getUsername());
            //date
            jsonObject.put("date", sysLog.getCreateTime());
            //desc
            if ("阿里客户-管理员-批量添加公海客户".equals(sysLog.getLogContent())) {
                JSONArray array = JSONArray.parseArray(sysLog.getRequestParam());
                int count = array.getJSONObject(0).getString("id").split(",").length;
                jsonObject.put("desc", "添加了" + count + "条公海客户信息");
            } else if ("阿里客户-管理员-移出公海".equals(sysLog.getLogContent())) {
                JSONArray array = JSONArray.parseArray(sysLog.getRequestParam());
                int count = array.getJSONObject(0).getString("id").split(",").length;
                jsonObject.put("desc", "移出了" + count + "条公海客户信息");
            } else if ("阿里客户-业务员-加为我的客户".equals(sysLog.getLogContent())) {
                jsonObject.put("desc", "从公海中添加了客户");
            } else if ("阿里客户-业务员-移入公海".equals(sysLog.getLogContent())) {
                jsonObject.put("desc", "将客户移入了公海");
            } else if ("阿里客户-业务员-添加".equals(sysLog.getLogContent())) {
                jsonObject.put("desc", "添加了个人客户信息");
            } else if (sysLog.getLogContent().contains("删除")) {
                jsonObject.put("desc", "删除了个人客户信息");
            } else if ("阿里客户-编辑".equals(sysLog.getLogContent())) {
                jsonObject.put("desc", "编辑了个人客户信息");
            } else if ("阿里客户跟进信息-添加".equals(sysLog.getLogContent())) {
                jsonObject.put("desc", "添加了客户跟进信息");
            } else {
                continue;
            }
            result.add(jsonObject);
        }
        return Result.OK(result);
    }


}
