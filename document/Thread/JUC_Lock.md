### Lock

#### ReentrantLock 

​	实现 Lock 接口

​	是一种重入互斥锁

重入锁的实现目的，为了避免死锁

synchronized 和 reentrantlock 都是支持重入

**多个线程竞争锁的时候，其他线程会怎么办？**

#### AQS

##### 同步工具，具有两种功能

* 独占 （SHARED） -> 互斥锁
* 共享 （EXCLUSIVE）-> 读写锁

##### AQS 的基本实现

维护一个双向链表

##### 锁的基本要素

* 一个共享的数据来记录锁的状态

  State：锁标记

  0：无锁状态； >=1：有锁状态，可重入

##### AbstractQueuedSynchronizer 类的 addWaiter(Node mode) 方法