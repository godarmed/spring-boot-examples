package com.gzz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author https://www.jianshu.com/u/3bd57d5f1074
 * @date 2019-12-24 14:50:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	public String id;
	public String firstName;
	public String lastName;
}