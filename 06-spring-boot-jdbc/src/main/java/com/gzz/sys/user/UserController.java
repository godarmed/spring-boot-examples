package com.gzz.sys.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gzz.common.base.Page;
import com.gzz.common.config.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @类说明 【用户】控制器
 * @author 高振中
 * @date 2020-03-18 13:56:40
 **/
//@Slf4j
@RestController
@RequestMapping("user")
@Api(tags = "【用户】API")
public class UserController {

	@Autowired
	private UserService userService; // 注入【用户】业务逻辑层

	/**
	 * @方法说明 新增【用户】记录
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增【用户】记录")
	public Result<Integer> save(@RequestBody @Valid User user) {
		return Result.success(userService.save(user));
	}

	/**
	 * @方法说明 删除【用户】记录
	 */
	@PostMapping("delete")
	@ApiOperation(value = "删除【用户】记录")
	public Result<Integer> delete(Long[] ids) {
		return Result.success(userService.delete(ids));
	}

	/**
	 * @方法说明 修改【用户】记录
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改【用户】记录")
	public Result<Integer> update(@RequestBody @Valid User user) {
		return Result.success(userService.update(user));
	}

	/**
	 * @方法说明 按条件查询分页【用户】列表
	 */
	@PostMapping("page")
	@ApiOperation(value = "按条件查询分页【用户】列表")
	public Result<Page<User>> page(@RequestBody UserCond cond) {
		return Result.success(userService.page(cond));
	}

	/**
	 * @方法说明 按条件查询不分页【用户】列表
	 */
	@PostMapping("list")
	@ApiOperation(value = "按条件查询不分页【用户】列表")
	public Result<List<User>> list(@RequestBody UserCond cond) {
		return Result.success(userService.list(cond));
	}

	/**
	 * @方法说明 按主键查单个【用户】记录
	 */
	@PostMapping("findById")
	@ApiOperation(value = "按主键查单个【用户】记录")
	public Result<User> findById(@RequestParam("id") Long id) {
		return Result.success(userService.findById(id));
	}

	/**
	 * @方法说明 按条件查询【用户】记录个数
	 */
	@PostMapping("count")
	@ApiOperation(value = "【用户】记录个数")
	public Result<Integer> count(@RequestBody UserCond cond) {
		return Result.success(userService.count(cond));
	}
}