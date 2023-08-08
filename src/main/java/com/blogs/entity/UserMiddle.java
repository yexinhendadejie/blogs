package com.blogs.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMiddle {

	// id
	private Integer id;

	// 当前账号/关注账号对象
	private Integer userId;

	// 账号粉丝/当前账号
	private Integer targetUserId;

	// 0->对应粉丝 1->对应关注
	private Integer flag;

	// UpdateTime
	private Timestamp createTime;

	// UpdateTime
	private Timestamp updateTime;

}
