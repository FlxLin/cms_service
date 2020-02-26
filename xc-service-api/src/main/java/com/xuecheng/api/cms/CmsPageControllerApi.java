package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

public interface CmsPageControllerApi {

    //页面查询
    @ApiOperation("页面查询")
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    //增加页面
    @ApiOperation("增加页面")
    public CmsPageResult add(CmsPage cmsPage);

    //根据页面ID查询页面信息
    @ApiOperation("根据页面ID查询页面信息")
    public CmsPage findById(String id);

    //修改页面信息
    @ApiOperation("修改页面信息")
    public CmsPageResult edit(String id, CmsPage cmsPage);

    //删除页面
    @ApiOperation("删除页面")
    public ResponseResult delete(String id);

    //发布页面
    @ApiOperation("发布页面")
    public ResponseResult post(String pageId);
}
