package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-27 18:33
 **/
@Service
public class CourseService {
    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseMarketRepository courseMarketRepository;
    //查询课程计划
    public TeachplanNode findTeachplanList(String courseId){
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }

    //添加课程计划
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan){
        if (teachplan == null ||
                StringUtils.isEmpty(teachplan.getCourseid()) ||
                StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String courseId = teachplan.getCourseid();
        //处理parentId
        String parentId = teachplan.getParentid();
        if(parentId == null){
            parentId = this.getTeachplanRoot(courseId);
        }
        Teachplan teachplanNew = new Teachplan();
        Optional<Teachplan> optional = teachplanRepository.findById(parentId);
        String grade = optional.get().getGrade();
        BeanUtils.copyProperties(teachplan,teachplanNew);
        if(grade.equals("1")){
            teachplanNew.setGrade("2");
        }else {
            teachplanNew.setGrade("3");
        }
        teachplanNew.setParentid(parentId);
        teachplanNew.setCourseid(courseId);
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //查询课程根节点，如果查询不到，自动添加根节点
    private String getTeachplanRoot(String courseId){
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        CourseBase course = optional.get();

        List<Teachplan> courseInfo = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if(courseInfo == null || courseInfo.size() <= 0){
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseId);
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setStatus("0");
            teachplan.setPname(course.getName());
            teachplanRepository.save(teachplan);
            return teachplan.getId();
        }
        return courseInfo.get(0).getId();
    }

    //查询课程列表
    public QueryResponseResult findAllCourseInfo(int page, int size, CourseListRequest courseListRequest){
        PageHelper.startPage(page,size);
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(courseListPage.getResult());
        queryResult.setTotal(courseListPage.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    //添加课程
    public ResponseResult addCourseBase(CourseBase courseBase) {
        CourseBase save = courseBaseRepository.save(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //查询课程信息
    public CourseBase findCourseInfo(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        return optional.get();
    }

    //修改课程信息
    @Transactional
    public ResponseResult updateCourseBase(String courseId, CourseBase courseBase) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CourseBase course = optional.get();
        BeanUtils.copyProperties(courseBase,course);
        courseBaseRepository.save(course);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //课程营销信息查询
    public CourseMarket getCourseMarketById(String courseId) {
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        return optional.get();
    }

    //修改课程营销信息
    @Transactional
    public ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket) {
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if(optional.isPresent()){
            CourseMarket market = optional.get();
            BeanUtils.copyProperties(courseMarket,market);
            courseMarketRepository.save(market);
            return ResponseResult.SUCCESS();
        }
        courseMarketRepository.save(courseMarket);
        return ResponseResult.SUCCESS();
    }
}
