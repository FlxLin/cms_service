package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-26 11:26
 **/
@Component
public class ConsumerPostPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);

    @Autowired
    PageService pageService;

    @RabbitListener(queues = "${xuecheng.mq.queue}")
    public void postPage(String msg){
        Map map = JSON.parseObject(msg, Map.class);
        String pageId = (String)map.get("pageId");
        if(pageService.findCmsPageById(pageId) == null){
            LOGGER.error("CmsPage is null , pageId is {}",pageId);
            return;
        }
        pageService.savePageToServerPath(pageId);
    }
}
