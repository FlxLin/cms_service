package com.xuecheng.framework.domain.cms.request;

import lombok.Data;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-13 10:29
 **/
@Data
public class QueryPageRequest {
    //请求数据类型
    //站点ID
    private String siteId;
    //页面ID
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //模板ID
    private String templateId;
    //...

}
