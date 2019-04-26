# 什么是 MyBatis

![](http://www.mybatis.org/images/mybatis-logo.png)

&emsp;&emsp;MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO 为数据库中的记录。

# 入门

&emsp;&emsp;每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的，SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得，而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先定制的 Configuration 的实例构建出 SqlSessionFactory 的实例。

**MyBatis 的配置文件**

&emsp;&emsp;XML 配置文件中包含了对 MyBatis 系统的核心设置，包含获取数据库连接实例的数据源（DataSource）和决定事务作用域和控制方式的事务管理器（TransactionManager）。这里只做简单的说明，详细内容请参考[mybatis 配置](http://www.mybatis.org/mybatis-3/zh/configuration.html)

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" 
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!-- 属性 -->
	<properties></properties>
	<!-- 这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为 -->
	<settings></settings>
	<!-- 类型别名是为 Java 类型设置一个短的名字，只和 XML 配置有关，仅在于用来减少类完全限定名的冗余。 -->
	<typeAliases></typeAliases>
	<!-- 类型处理器 -->
	<typeHandlers></typeHandlers>
	<!-- 环境配置 -->
	<environments default="development">
		<environment id="development">
			<!-- 事务管理器 -->
			<transactionManager type="JDBC" />
			<!-- dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源 -->
			<dataSource type="POOLED">
				<!-- 选择连接数据库用的驱动 -->
				<property name="driver" value="com.mysql.cj.jdbc.Driver" />
				<!-- 数据库地址 -->
				<property name="url" value="" />
				<!-- 数据库用户名 -->
				<property name="username" value="" />
				<!-- 数据库密码 -->
				<property name="password" value="" />
			</dataSource>
		</environment>
	</environments>

	<!-- 映射器 -->
	<mappers>
		<mapper resource="org/mybatis/example/MyBatisMapper.xml" />
	</mappers>
</configuration>
```

**从 XML 中构建 SqlSessionFactory**

1. 从 XML 文件中构建 SqlSessionFactory 的实例非常简单，建议使用类路径下的资源文件进行配置。

```
String resource = "org/mybatis/example/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```

2. 从 SqlSessionFactory 中获取 SqlSession

- 既然有了 SqlSessionFactory，顾名思义，我们就可以从中获得 SqlSession 的实例了。SqlSession 完全包含了面向数据库执行 SQL 命令所需的所有方法。你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句。例如：

```
SqlSession session = sqlSessionFactory.openSession();
try {
  session.selectOne("org.mybatis.example.MyBatisMapper.selectByid", 101);
} finally {
  session.close();
}
```

- 诚然，这种方式能够正常工作，并且对于使用旧版本 MyBatis 的用户来说也比较熟悉。不过现在有了一种更简洁的方式 ——使用正确描述每个语句的参数和返回值的接口（比如 MyBatisMapper.class），你现在不仅可以执行更清晰和类型安全的代码，而且还不用担心易错的字符串字面值以及强制类型转换。

```
SqlSession session = sqlSessionFactory.openSession();
try {
  MyBatisMapper mapper = session.getMapper(MyBatisMapper.class);
  Blog blog = mapper.selectBlog(101);
} finally {
  session.close();
}
```

**XML 映射文件**

- MyBatis 的真正强大在于它的映射语句，这是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 为聚焦于 SQL 而构建，以尽可能地为你减少麻烦。

- SQL 映射文件只有很少的几个顶级元素
  - cache ： 对给定命名空间的缓存配置。[缓存](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html#cache)
  - resultMap、resultType ： 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。[结果映射](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html#Result_Maps)
  - parameterType [参数](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html#Parameters)
  - sql ： 可被其他语句引用的可重用语句块。
  - insert ： [映射插入语句](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html#insert_update_and_delete)
  - update ： [映射更新语句](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html#insert_update_and_delete)
  - delete ： [映射删除语句](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html#insert_update_and_delete)
  - select ： [映射查询语句](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html#select)

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.mybatis.example.MyBatisMapper">
	<!-- 这里仅仅显示使用，切记尽量不要select * 做全表字段查询 -->
	<select id="selectByid" resultType="MyBatis">
		select * from MyBatis where id = #{id}
	</select>
</mapper>
```