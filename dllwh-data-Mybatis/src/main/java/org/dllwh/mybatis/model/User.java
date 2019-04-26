package org.dllwh.mybatis.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private Integer	id;
	private String	userName;
	private String	password;
	private Integer	creator;
	private Date	createTime;
	private Integer	modifier;
	private Date	updateTime;
}