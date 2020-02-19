package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-14 09:59
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    //分页查询
    @Test
    public void testFindPage(){
        int page = 0;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    @Test
    public void testFindAllByExample(){
        int page = 0;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);

        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageAliase("轮播");
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

        System.out.println(all.getContent());
    }
    //修改
    @Test
    public void testSave(){
        Optional<CmsPage> optional = cmsPageRepository.findById("5a795ac7dd573c04508f3a56");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("index.html");
            cmsPageRepository.save(cmsPage);
        }
    }
}
