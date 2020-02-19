package com.xuecheng.manage_cms.service;


import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-14 10:51
 **/
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    //分页查询 page:页码，从0开始 size:每页行数
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        //自定义条件查询
        if(queryPageRequest == null){
            queryPageRequest = new QueryPageRequest();
        }

        CmsPage cmsPage = new CmsPage();

        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
         if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
             cmsPage.setTemplateId(queryPageRequest.getTemplateId());
          }
         if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
             cmsPage.setSiteId(queryPageRequest.getSiteId());
         }

         //定义模糊匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);

        Pageable pageable = PageRequest.of(page-1,size);
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());

        return  new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
