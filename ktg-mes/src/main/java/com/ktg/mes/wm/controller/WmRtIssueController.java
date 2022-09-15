package com.ktg.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.common.constant.UserConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.wm.domain.WmRtIssue;
import com.ktg.mes.wm.service.IWmRtIssueService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 生产退料单头Controller
 * 
 * @author yinjinlu
 * @date 2022-09-15
 */
@RestController
@RequestMapping("/mes/wm/rtissue")
public class WmRtIssueController extends BaseController
{
    @Autowired
    private IWmRtIssueService wmRtIssueService;

    /**
     * 查询生产退料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtIssue wmRtIssue)
    {
        startPage();
        List<WmRtIssue> list = wmRtIssueService.selectWmRtIssueList(wmRtIssue);
        return getDataTable(list);
    }

    /**
     * 导出生产退料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:export')")
    @Log(title = "生产退料单头", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmRtIssue wmRtIssue)
    {
        List<WmRtIssue> list = wmRtIssueService.selectWmRtIssueList(wmRtIssue);
        ExcelUtil<WmRtIssue> util = new ExcelUtil<WmRtIssue>(WmRtIssue.class);
        util.exportExcel(response, list, "生产退料单头数据");
    }

    /**
     * 获取生产退料单头详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:query')")
    @GetMapping(value = "/{rtId}")
    public AjaxResult getInfo(@PathVariable("rtId") Long rtId)
    {
        return AjaxResult.success(wmRtIssueService.selectWmRtIssueByRtId(rtId));
    }

    /**
     * 新增生产退料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:add')")
    @Log(title = "生产退料单头", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtIssue wmRtIssue)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmRtIssueService.checkUnique(wmRtIssue))){
            return AjaxResult.error("退料单编号已存在");
        }
        return toAjax(wmRtIssueService.insertWmRtIssue(wmRtIssue));
    }

    /**
     * 修改生产退料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:edit')")
    @Log(title = "生产退料单头", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtIssue wmRtIssue)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmRtIssueService.checkUnique(wmRtIssue))){
            return AjaxResult.error("退料单编号已存在");
        }
        return toAjax(wmRtIssueService.updateWmRtIssue(wmRtIssue));
    }

    /**
     * 删除生产退料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:remove')")
    @Log(title = "生产退料单头", businessType = BusinessType.DELETE)
	@DeleteMapping("/{rtIds}")
    public AjaxResult remove(@PathVariable Long[] rtIds)
    {
        return toAjax(wmRtIssueService.deleteWmRtIssueByRtIds(rtIds));
    }
}