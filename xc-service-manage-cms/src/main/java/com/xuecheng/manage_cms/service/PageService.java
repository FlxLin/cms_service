package com.xuecheng.manage_cms.service;


import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    //增加页面
    public CmsPageResult add(CmsPage cmsPage){
        //调用dao
        //通过页面名称，站点id，路径可以唯一确定页面
        CmsPage cmspages = cmsPageRepository.findCmsPageByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(cmspages == null){
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    //根据id查找页面
    public CmsPage getById(String id){
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }else{
            return null;
        }
    }

    //修改页面信息
    public CmsPageResult update(String id, CmsPage cmsPage){
        CmsPage one = this.getById(id);
        if(one != null){
            //修改页面信息
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());

            //提交修改
            cmsPageRepository.save(cmsPage);

            return new CmsPageResult(CommonCode.SUCCESS,one);
        }

        return new CmsPageResult(CommonCode.FAIL,null);
    }
}
