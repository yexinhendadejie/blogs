package com.blogs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@TableId(value="id",type= IdType.AUTO)
	// 用户Id
	private Integer id;

	// 账号
	private String uname;

	// 密码
	private String pwd;

	// 出生日期
	private String born;

	// 兴趣爱好
	private String hobby;

	// 个性签名
	private String label;

	// 性别 0->男 1->女
	private String sex;

	// 手机号
	private String phone;

	// 邮箱
	private String email;

	// 点赞总数
	private Integer likeCount;

	//  点踩总数
	private Integer downCount;

	// 修改时间
	private Timestamp updateTime;

	// 创建时间
	private Timestamp createTime;

	public User(String username, String pwd, String phone, String email) {
		this.uname = username;
		this.pwd = pwd;
		this.phone = phone;
		this.email = email;
	}
}
