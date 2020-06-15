package com.gzz.sys.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzz.common.base.Page;

/**
 * @类说明:用户数据逻辑层
 * @author http://www.gaozz.club
 * @date:2018-07-13 10:17:27
 **/
@Service
public class UserService {
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(getClass());
	@Autowired
	private UserDao dao; // 注入用户数据访问层

	/**
	 * @方法说明:新增用户记录
	 **/
	@Transactional
	public int save(User user) {
		return dao.save(user);
	}

	/**
	 * @方法说明:删除用户记录(多条)
	 **/
	public int delete(Long ids[]) {
		// return dao.deleteLogic(ids);//逻辑删除
		return dao.delete(ids);// 物理删除
	}

	/**
	 * @方法说明:更新用户记录
	 **/
	@Transactional
	public int update(User user) {
		return dao.update(user);
	}

	/**
	 * @方法说明:按条件查询分页用户列表
	 **/
	public Page<User> queryPage(UserCond cond) {
		return dao.queryPage(cond);
	}

	/**
	 * @方法说明:按条件查询不分页用户列表(使用范型)
	 **/
	public List<User> queryList(UserCond cond) {
		return dao.queryList(cond);
	}

	/**
	 * @方法说明:按ID查找单个用户记录
	 **/
	public User findById(Long id) {
		return dao.findById(id);
	}

	/**
	 * @方法说明:按条件查询用户记录个数
	 **/
	public long queryCount(UserCond cond) {
		return dao.queryCount(cond);
	}
}