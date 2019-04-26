package org.dllwh.mybatis.dao;

import java.util.List;

import org.dllwh.mybatis.model.User;

public interface UserDao {
	List<User> getAllUser();
}