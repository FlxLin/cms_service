package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-26 10:27
 **/
@Service
public class PageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    public void savePageToServerPath(String pageId){
        //得到Cmspage信息
        CmsPage cmsPage = this.findCmsPageById(pageId);
        //得到htmlfiledId
        String fileId = cmsPage.getHtmlFileId();
        //得到html文件流
        InputStream inputStream = this.getFiledById(fileId);
        if(inputStream == null){
            LOGGER.error("getFiledById InputStream is null , htmlFileId is {}",fileId);
            return;
        }
        //得到站点的物理路径
        String pagePhysicalPath = this.findSitePageById(cmsPage.getSiteId()).getPagePhysicalPath();
        //得到页面的物理路径
        String path = pagePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        //将html文件保存到服务器的物理路径上
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(path));
            IOUtils.copy(inputStream,fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //从Gridfs中获得文件流
    public InputStream getFiledById(String filedId){
        //获得文件
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(filedId)));
        //打开下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(file.getObjectId());
        //打开资源
        GridFsResource gridFsResource = new GridFsResource(file,gridFSDownloadStream);
        try {
            return gridFsResource.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //查询页面信息
    public CmsPage findCmsPageById(String pageId){
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    //查询站点页面
    public CmsSite findSitePageById(String siteId){
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if(optional.isPresent()){
            return  optional.get();
        }
        return null;
    }
}
