package com.zzy.jpaCrud.single_table.entity;

import com.zzy.jpaCrud.common.model.po.BasePO;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "single_table_jpa_user")
public class JpaUser implements BasePO {

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
