package com.blogs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogs.entity.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectionMapper extends BaseMapper<Collection> {

    // 根据用户id查询收藏
    List<Collection> selectByUserId(@Param("userId") int userId,
                                    @Param("startRow") int startRow,
                                    @Param("pageSize") int pageSize);

    // 根据博客id删除收藏
    void deleteByBlogId(@Param("list") List<Integer> blogId);
}
