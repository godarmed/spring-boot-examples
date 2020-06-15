package com.gzz.demo.sys.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
 
import org.apache.ibatis.annotations.Mapper;
/**
 * @类说明 【角色】数据访问层
 * @author 高振中
 * @date 2020-04-02 20:40:14
 **/
@Mapper
public interface IRoleMapper extends BaseMapper<Role> {
	
//	/**
//	 * @方法说明 自义联表查询
//	 **/
//	IPage<Role> queryList(IPage<Role> page, Role param);
}
