# Zookeeper 概述

&emsp;&emsp;ZooKeeper是一种分布式协调服务，用于管理大型主机。在分布式环境中协调和管理服务是一个复杂的过程。ZooKeeper通过其简单的架构和API解决了这个问题。ZooKeeper允许开发人员专注于核心应用程序逻辑，而不必担心应用程序的分布式特性。

[Zookeeper](./Zookeeper.md)

# 访问会话

## 创建会话

> 调用Zookeeper首先需要先建立Zookeeper的会话，用于关联到具体的Zookeeper服务器和znode路径下

```
ZooKeeper(String connectString，int sessionTimeout，Watcher watcher)

ZooKeeper(String connectString，int sessionTimeout，Watcher watcher，
		boolean canBeReadOnly)

ZooKeeper(String connectString，int sessionTimeout，Watcher watcher，
		long sessionId，byte[] sessionPasswd)

ZooKeeper(String connectString，int sessionTimeout，Watcher watcher，
		long sessionId，byte[] sessionPasswd，boolean canBeReadOnly)
```

## 结束会话

> 可以在某一个会话过程中，通过如下方法来结束本次会话：

```
public void close()
``` 

# 访问节点

## 创建节点

> 创建节点有两种方式，一种是同步方式创建节点，并返回创建好的节点的路径名称，另一种方式是以异步的方式创建节点，然后通过回调接口方法的参数返回创建好的路径名称：

同步创建节点：

```
public String create(String path，byte[] data，Listacl，CreateMode createMode) 
		throws KeeperException，InterruptedException
```

异步创建节点

```
public void create(String path，byte[] data，Listacl，CreateMode createMode，
		AsyncCallback.StringCallback cb，Object ctx)
```

## 监听节点

> 可以在任一时刻对某个znode节点进行检查，无论该节点是否存在，并同时可以针对这个节点注册一个监听器，随时通过监听方法的参数event监听该节点的各种变化：


- 被监控节点被创建：event.getType() == EventType.NodeCreated
- 被监控节点被删除：event.getType() == EventType.NodeDeleted
- 被监控节点的子节点被修改：event.getType() == EventType.NodeChildrenChanged
- 被监控节点的数据被修改：event.getType() == EventType.NodeDataChanged

## 节点删除

> 可以在任一时刻，通过如下方法将某个节点删除，与创建节点相似，删除操作也分为同步和异步两种方式：

同步删除节点

```
public void delete(String path，int version)throws InterruptedException，KeeperException
```

异步删除节点

```
public void delete(String path，int version，AsyncCallback.VoidCallback cb，Object ctx)
```

## 获得子节点

> 从已知的某个节点获得其包含的所有子节点，有八种方式来获得子节点。

同步获得子节点有四种方式，其区别主要在于设置观察者和获得执行状态的不同。

```
public List getChildren(String path，boolean watch) 
		throws KeeperException，InterruptedException

public List getChildren(String path，boolean watch，Stat stat) 
		throws KeeperException，InterruptedException

public List getChildren(String path，Watcher watcher) 
		throws KeeperException，InterruptedException

public List getChildren(String path，Watcher watcher，Stat stat) 
		throws KeeperException，InterruptedException
```

异步获得子节点也有四种方式，其区别同样在于设置观察者和获得执行状态的不同

```
public void getChildren(String path，boolean watch，AsyncCallback.Children2Callback cb，Object ctx)

public void getChildren(String path，boolean watch，AsyncCallback.ChildrenCallback cb，Object ctx)

public void getChildren(String path，Watcher watcher， AsyncCallback.Children2Callback cb，Object ctx)

public void getChildren(String path，Watcher watcher，AsyncCallback.ChildrenCallback cb，Object ctx)
```

# 访问数据

## 获取节点数据

> 当关联到某个节点时，获取节点数据有两种方式，一种是同步方式获取节点数据，并以字节数组方式返回数据信息，另一种方式是以异步的方式获取节点数据，然后通过回调接口方法的参数返回节点数据：

同步获取节点数据

```
public byte[] getData(String path，boolean watch，Stat stat) throws KeeperException，InterruptedException

public byte[] getData(String path，Watcher watcher，Stat stat) throws KeeperException，InterruptedException
```

异步获取节点数据

```
public void getData(String path，boolean watch，AsyncCallback.DataCallback cb，Object ctx)

public void getData(String path，Watcher watcher，AsyncCallback.DataCallback cb，Object ctx)
```

## 修改节点数据

> 节点在创建时，就需要为其初始化一些数据，这些数据可以被修改，有两种方式可以修改一个节点的数据，一种是同步方式修改，另一种方式是以异步的方式修改，然后通过回调接口方法的参数返回结果：

同步修改节点数据

```
public Stat setData(String path，byte[] data，int version) throws KeeperException，InterruptedException
```

异步修改节点数据

```
public void setData(String path，byte[] data，int version，AsyncCallback.StatCallback cb，Object ctx)

```

# 数据结构

## 异步回调接口

> 异步回调接口定义在接口AsyncCallback中，一共有7种类型，分别用于在不同的操作中，回调并传入不同的内容：

|接口类型|应用的操作|
|---|---|
|**StatCallback**|setData()和exists()|
|**DataCallback**|getData()|
|**ACLCallback**|getACL()|
|**ChildrenCallback**|getChildren()|
|**Children2Callback**|getChildren()|
|**StringCallback**|create()|
|**VoidCallback**|delete()|


- StatCallback
  - 用于在设置节点数据时，以及在判断节点是否存在时进行回调，其回调方法为
  ```
    void processResult(int rc，String path，Object ctx，Stat stat)
  ```
- DataCallback
  - 用于在查询节点数据时进行回调，其回调方法为：
  ```
    void processResult(int rc，String path，Object ctx，byte[] data，Stat stat)
  ```
- ACLCallback
  - 用于在获得权限时进行回调，其回调方法为：
  ```
    void processResult(int rc，String path，Object ctx，Listacl，Stat stat)
  ```
- ChildrenCallback
  - 用于在查询子节点列表时进行回调，其回调方法为
  ```
    void processResult(int rc，String path，Object ctx，Listchildren)
  ```
- Children2Callback
  - 用于在查询子节点列表时进行回调，并获得状态信息，其回调方法为
  ```
    void processResult(int rc，String path，Object ctx，Listchildren，Stat stat)
  ```
- StringCallback
  - 用于在创建节点时进行回调，其回调方法为
  ```
    void processResult(int rc，String path，Object ctx，String name)
  ```
- VoidCallback
  - 用于在删除节点时进行回调，其回调方法为
  ```
    void processResult(int rc，String path，Object ctx)
  ```
- MultiCallback
  - 用于在批处理时进行回调，其回调方法为
  ```
    void processResult(int rc，String path，Object ctx，ListopResults)
  ```

## 返回状态码

> 回调接口方法中，第一个参数rc的含义

值|编码|含义
|---|---|---
|0 |    OK	                     |执行成功
|-1|    SYSTEMERROR	             |服务器系统错误
|-2|	RUNTIMEINCONSISTENCY     |在运行期发现一致性错误
|-3|	DATAINCONSISTENCY        |发现数据一致性错误
|-4|	CONNECTIONLOSS	         |客户端和服务器之间链接已断开
|-5|   	MARSHALLINGERROR         |数据编码或者解码错误
|-6|	UNIMPLEMENTED	         |操作不支持
|-7|	OPERATIONTIMEOUT         |操作超时
|-8|   	BADARGUMENTS	         |无效的参数
|-100|	APIERROR	API          |调用错误
|-101|	NONODE	                 |未找到指定的节点
|-102|	NOAUTH	                 |没有权限，没通过认证
|-103|	BADVERSION	             |版本冲突错误
|-108|	NOCHILDRENFOREPHEMERALS  |临时节点没有子节点
|-110|	NODEEXISTS	             |指定的节点已经存在
|-111|	NOTEMPTY	             |节点中已经存在了子节点
|-112|	SESSIONEXPIRED           |在当前服务器中会话已过期
|-113|  INVALIDCALLBACK	         |无效的回调
|-114|	INVALIDACL	             |无效的ACL控制
|-115|	AUTHFAILED               |客户端权限认证失败
|-118|	SESSIONMOVED	         |会话已经被迁移到另一台服务器中
|-119|	NOTREADONLY	             |状态更改请求被传递给只读服务器

## 状态对象

> 所有针对节点及其数据进行回调的状态信息，都会封装在状态对象Stat中返回给客户端，状态对象的声明：

```
public class Stat implements Record {
	/** 对应为该节点的创建时间（Create） */
	private long	czxid;
	/** 对应该节点的最近一次修改的时间（Mofify），与其子节点无关 */
	private long	mzxid;
	private long	ctime;
	private long	mtime;
	/** 数据内容的版本号，修改当前结点数据内容，则会变更此版本号 */
	private int		version;
	/** 子节点的版本号，子节点发生变更会改变此版本号 */
	private int		cversion;
	/** ACL的版本号，当前结点的ACL发生变更，会改变此版本号 */
	private int		aversion;
	private long	ephemeralOwner;
	private int		dataLength;
	private int		numChildren;
	/** 与该节点的子节点（或该节点）的最近一次 创建/删除的时间戳对应，且只与本节点/该节点的子节点有关 */
	private long	pzxid;
}
```