package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());

        return  new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
