package com.xuecheng.api.course;


import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="课程管理接口",description = "提供课程的增删改查")
public interface CourseControllerApi {
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    public ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("课程管理")
    public QueryResponseResult findCourseList(
            int page,
            int size,
            CourseListRequest courseListRequest
    );

    @ApiOperation("添加课程")
    public ResponseResult addCourseBase(CourseBase courseBase);

    @ApiOperation("课程信息查询")
    public CourseBase findCourseBase(String courseId);

    @ApiOperation("修改课程信息")
    public ResponseResult updateCourseBase(String courseId,CourseBase courseBase);

    @ApiOperation("课程营销信息查询")
    public CourseMarket getCourseMarketById(String courseId);

    @ApiOperation("课程营销信息修改")
    public ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket);
}
