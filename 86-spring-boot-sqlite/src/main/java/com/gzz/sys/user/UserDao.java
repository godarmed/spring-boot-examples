package com.gzz.sys.user;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gzz.common.base.BaseDao;
import com.gzz.common.base.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @类说明:用户数据访问层
 * @author https://www.jianshu.com/u/3bd57d5f1074
 * @date 2019-12-24 10:50:00
 **/
@Repository
@Slf4j
public class UserDao extends BaseDao {
	private StringBuilder select = new StringBuilder();

	/**
	 * @方法说明:构造方法,用于拼加SQL及初始化工作
	 **/
	public UserDao() {
		select.append("SELECT t.id,t.name FROM user t ");
	}

	/**
	 * @方法说明:新增用户记录
	 **/
	public int save(User vo) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO user (id,name) VALUES (?,?) ");
		Object[] params = { vo.getId(), vo.getName() };
		log.info(super.sql(sql.toString(), params));// 显示SQL语句
		return jdbcTemplate.update(sql.toString(), params);
	}

	/**
	 * @方法说明:按条件查询不分页用户列表
	 **/
	public List<User> queryList(UserCond cond) {
		StringBuilder sb = new StringBuilder(select);
		sb.append(cond.where());
		log.info(super.sql(sb.toString(), cond.array()));// 显示SQL语句
		return jdbcTemplate.query(sb.toString(), cond.array(), new BeanPropertyRowMapper<>(User.class));
	}

	/**
	 * @方法说明:按条件查询分页用户列表
	 **/
	public Page<User> queryPage(UserCond cond) {
		StringBuilder sb = new StringBuilder(select);
		sb.append(cond.where());
		log.info(super.sql(sb.toString(), cond.array()));// 显示SQL语句
		return queryPage(sb.toString(), cond, User.class);
	}
}