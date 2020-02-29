package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-27 18:35
 **/
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {
    @Autowired
    CourseService courseService;

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable("page") int page,@PathVariable("size") int size,CourseListRequest courseListRequest) {
        return courseService.findAllCourseInfo(page, size, courseListRequest);
    }

    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult addCourseBase(@RequestBody CourseBase courseBase) {
        return courseService.addCourseBase(courseBase);
    }

    @Override
    @GetMapping("/courseview/{courseid}")
    public CourseBase findCourseBase(@PathVariable("courseid") String courseId) {
        return courseService.findCourseInfo(courseId);
    }

    @Override
    @PutMapping("/update/{courseId}")
    public ResponseResult updateCourseBase(@PathVariable("courseId") String courseId,@RequestBody CourseBase courseBase) {
        return courseService.updateCourseBase(courseId,courseBase);
    }

    @Override
    @GetMapping("/market/{id}")
    public CourseMarket getCourseMarketById(@PathVariable("id") String id) {
        return courseService.getCourseMarketById(id);
    }

    @Override
    @PutMapping("/market/update/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id") String id,@RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(id, courseMarket);
    }
}
