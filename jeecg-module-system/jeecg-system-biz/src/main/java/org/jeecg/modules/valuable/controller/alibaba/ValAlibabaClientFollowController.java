package org.jeecg.modules.valuable.controller.alibaba;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.valuable.entity.ValAlibabaClientFollow;
import org.jeecg.modules.valuable.service.IValAlibabaClientFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 阿里客户跟进信息
 * @Author: jeecg-boot
 * @Date: 2024-02-18
 * @Version: V1.0
 */
@Api(tags = "阿里客户跟进信息")
@RestController
@RequestMapping("/val/alibabaClient/follow")
@Slf4j
public class ValAlibabaClientFollowController extends JeecgController<ValAlibabaClientFollow, IValAlibabaClientFollowService> {
    @Autowired
    private IValAlibabaClientFollowService valClientFollowService;

    /**
     * 分页列表查询
     *
     * @param valAlibabaClientFollow
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "阿里客户跟进信息-分页列表查询")
    @ApiOperation(value = "阿里客户跟进信息-分页列表查询", notes = "阿里客户跟进信息-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ValAlibabaClientFollow>> queryPageList(ValAlibabaClientFollow valAlibabaClientFollow,
                                                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                               HttpServletRequest req) {
        QueryWrapper<ValAlibabaClientFollow> queryWrapper = QueryGenerator.initQueryWrapper(valAlibabaClientFollow, req.getParameterMap());
        Page<ValAlibabaClientFollow> page = new Page<ValAlibabaClientFollow>(pageNo, pageSize);
        IPage<ValAlibabaClientFollow> pageList = valClientFollowService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    @ApiOperation(value = "阿里客户跟进信息-根据客户和经理ID查询", notes = "阿里客户跟进信息-根据客户和经理ID查询")
    @GetMapping(value = "/listByClientAndManager")
    public Result<List<ValAlibabaClientFollow>> listByClientAndManager(@RequestParam(value = "clientId") String clientId,
                                                                       HttpServletRequest req) {
        LambdaQueryWrapper<ValAlibabaClientFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ValAlibabaClientFollow::getClientId, clientId);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        queryWrapper.eq(ValAlibabaClientFollow::getManagerId, sysUser.getId());
        queryWrapper.orderByDesc(ValAlibabaClientFollow::getCreateTime);
        List<ValAlibabaClientFollow> list = valClientFollowService.list(queryWrapper);
        return Result.OK(list);
    }

    /**
     * 添加
     *
     * @param valAlibabaClientFollow
     * @return
     */
    @AutoLog(value = "阿里客户跟进信息-添加")
    @ApiOperation(value = "阿里客户跟进信息-添加", notes = "阿里客户跟进信息-添加")
    @RequiresPermissions("val:clientFollow:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ValAlibabaClientFollow valAlibabaClientFollow) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        valAlibabaClientFollow.setManagerId(sysUser.getId());
        valClientFollowService.save(valAlibabaClientFollow);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param valAlibabaClientFollow
     * @return
     */
    @AutoLog(value = "阿里客户跟进信息-编辑")
    @ApiOperation(value = "阿里客户跟进信息-编辑", notes = "阿里客户跟进信息-编辑")
    @RequiresPermissions("val:clientFollow:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ValAlibabaClientFollow valAlibabaClientFollow) {
        valClientFollowService.updateById(valAlibabaClientFollow);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "阿里客户跟进信息-通过id删除")
    @ApiOperation(value = "阿里客户跟进信息-通过id删除", notes = "阿里客户跟进信息-通过id删除")
    @RequiresPermissions("val:clientFollow:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        valClientFollowService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "阿里客户跟进信息-批量删除")
    @ApiOperation(value = "阿里客户跟进信息-批量删除", notes = "阿里客户跟进信息-批量删除")
    @RequiresPermissions("val:clientFollow:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.valClientFollowService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "阿里客户跟进信息-通过id查询")
    @ApiOperation(value = "阿里客户跟进信息-通过id查询", notes = "阿里客户跟进信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ValAlibabaClientFollow> queryById(@RequestParam(name = "id", required = true) String id) {
        ValAlibabaClientFollow valAlibabaClientFollow = valClientFollowService.getById(id);
        if (valAlibabaClientFollow == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(valAlibabaClientFollow);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param valAlibabaClientFollow
     */
    @RequiresPermissions("val:clientFollow:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ValAlibabaClientFollow valAlibabaClientFollow) {
        return super.exportXls(request, valAlibabaClientFollow, ValAlibabaClientFollow.class, "阿里客户跟进信息");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("val:clientFollow:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ValAlibabaClientFollow.class);
    }

}
