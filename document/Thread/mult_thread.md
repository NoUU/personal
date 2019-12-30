多线程

#### 线程基本体系

##### 线程的生命周期

六种状态

NEW 

RUNNABLE

BLOCKED：t2 两个线程访问带有 sychronized 的方法块，t1 获得了锁，则 t2 的状态为 BLOCKED

TERMINATED

WAITING

TIMED_WAITING：带有超时时间

##### 线程的创建

1. 实现 Runnable 接口
2. 继承 Thead 类
3. Callable/Futrue 带返回值的线程 java.util.concurrent 中的两个接口 
4. TheadPool 线程池

##### 线程的使用

可以合理地利用多核心 CPU 资源，提高程序吞吐量

#### 实际应用

线程池

#### 并发基础

