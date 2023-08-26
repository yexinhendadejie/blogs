package com.blogs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogs.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    void updateBlog(Blog blog);

    // 根据id查询博客
    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog findOneById(Integer id);
}
