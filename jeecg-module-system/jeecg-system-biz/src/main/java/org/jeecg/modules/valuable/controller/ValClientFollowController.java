package org.jeecg.modules.valuable.controller;

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
import org.jeecg.modules.valuable.entity.ValClientFollow;
import org.jeecg.modules.valuable.service.IValClientFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 客户跟进表
 * @Author: jeecg-boot
 * @Date: 2024-02-18
 * @Version: V1.0
 */
@Api(tags = "客户跟进表")
@RestController
@RequestMapping("/val/clientFollow")
@Slf4j
public class ValClientFollowController extends JeecgController<ValClientFollow, IValClientFollowService> {
    @Autowired
    private IValClientFollowService valClientFollowService;

    /**
     * 分页列表查询
     *
     * @param valClientFollow
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "客户跟进表-分页列表查询")
    @ApiOperation(value = "客户跟进表-分页列表查询", notes = "客户跟进表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ValClientFollow>> queryPageList(ValClientFollow valClientFollow,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        QueryWrapper<ValClientFollow> queryWrapper = QueryGenerator.initQueryWrapper(valClientFollow, req.getParameterMap());
        Page<ValClientFollow> page = new Page<ValClientFollow>(pageNo, pageSize);
        IPage<ValClientFollow> pageList = valClientFollowService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    @ApiOperation(value = "客户跟进表-根据客户和经理ID查询", notes = "客户跟进表-根据客户和经理ID查询")
    @GetMapping(value = "/listByClientAndManager")
    public Result<List<ValClientFollow>> listByClientAndManager(@RequestParam(value = "clientId") String clientId,
                                                                HttpServletRequest req) {
        LambdaQueryWrapper<ValClientFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ValClientFollow::getClientId, clientId);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        queryWrapper.eq(ValClientFollow::getManagerId, sysUser.getId());
        queryWrapper.orderByDesc(ValClientFollow::getCreateTime);
        List<ValClientFollow> list = valClientFollowService.list(queryWrapper);
        return Result.OK(list);
    }

    /**
     * 添加
     *
     * @param valClientFollow
     * @return
     */
    @AutoLog(value = "客户跟进表-添加")
    @ApiOperation(value = "客户跟进表-添加", notes = "客户跟进表-添加")
    @RequiresPermissions("val:clientFollow:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ValClientFollow valClientFollow) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        valClientFollow.setManagerId(sysUser.getId());
        valClientFollowService.save(valClientFollow);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param valClientFollow
     * @return
     */
    @AutoLog(value = "客户跟进表-编辑")
    @ApiOperation(value = "客户跟进表-编辑", notes = "客户跟进表-编辑")
    @RequiresPermissions("val:clientFollow:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ValClientFollow valClientFollow) {
        valClientFollowService.updateById(valClientFollow);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "客户跟进表-通过id删除")
    @ApiOperation(value = "客户跟进表-通过id删除", notes = "客户跟进表-通过id删除")
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
    @AutoLog(value = "客户跟进表-批量删除")
    @ApiOperation(value = "客户跟进表-批量删除", notes = "客户跟进表-批量删除")
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
    //@AutoLog(value = "客户跟进表-通过id查询")
    @ApiOperation(value = "客户跟进表-通过id查询", notes = "客户跟进表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ValClientFollow> queryById(@RequestParam(name = "id", required = true) String id) {
        ValClientFollow valClientFollow = valClientFollowService.getById(id);
        if (valClientFollow == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(valClientFollow);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param valClientFollow
     */
    @RequiresPermissions("val:clientFollow:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ValClientFollow valClientFollow) {
        return super.exportXls(request, valClientFollow, ValClientFollow.class, "客户跟进表");
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
        return super.importExcel(request, response, ValClientFollow.class);
    }

}
