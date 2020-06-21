package com.gzz.single_table.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "single_table_jpa_user")
public class JpaUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true) // 名字上有唯一索引，重复会报
	private String userName;

	@Column(nullable = false, length = 50)
	private String passWord;

	@Column(nullable = false)
	private Integer age;

}
