package org.dllwh.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.dllwh.mybatis.dao.UserDao;
import org.dllwh.mybatis.model.User;

import java.io.*;
import java.util.List;

public class MybatisApplication {
    /**
     * @方法描述: 根据XML配置构建SqlSessionFactory
     */
    private static SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSessionFactory sqlSessionFactory;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
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
            if (session != null) {
                session.close();
            }
        }
    }
}