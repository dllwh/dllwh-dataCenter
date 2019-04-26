package org.dllwh.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.dllwh.mybatis.dao.UserDao;
import org.dllwh.mybatis.model.User;

public class MybatisApplication {
	/**
	 * @方法描述: 根据XML配置构建SqlSessionFactory
	 */
	private static SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = null;
		SqlSessionFactory sqlSessionFactory = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} finally {
			inputStream.close();
		}
		return sqlSessionFactory;
	}

	public static void main(String[] args) throws IOException {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			UserDao userDao = session.getMapper(UserDao.class);
			List<User> users = userDao.getAllUser();
			System.out.println(users);
		} catch (Exception e) {
			session.close();
		}
	}
}