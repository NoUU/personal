JVM 笔记

## Java 虚拟机栈和栈帧

### 1. JVM 运行时数据区 （Run-Time Data Areas）

#### 1.1 方法区 （Method Area）

* 方法区只有一块，被所有线程共享（线程非安全），生命周期跟虚拟机的相同

  > The Java Virtual Machine has a *method area* that is shared among all Java Virtual Machine threads.  

* 存储类信息、常量、静态变量、*即时编译器编译后的代码 ?*

  > It stores per-class structures such as the run-time constant pool, field and method data, and the code for methods and constructors, including the special methods ([§2.9](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.9)) used in class and instance initialization and interface initialization. 

* 逻辑上来讲，方法区属于堆的一部分，但通常情况下不考虑该区域的内存回收

  > Although the method area is logically part of the heap, simple implementations may choose not to either garbage collect or compact it. 

* OutOfMemorryError	

JDK 1.7： PermSpace  永久代

JDK 1.8： MetaSpace  元空间

#### 1.2 运行时常量池 （Run-Time Constant Pool）

> Each run-time constant pool is allocated from the Java Virtual Machine's method area  

可以理解为方法区的一部分

#### 1.3 堆（Heap）

* 堆只有一个，所有线程共享，线程非安全

  >  The Java Virtual Machine has a *heap* that is shared among all Java Virtual Machine threads. 

* 堆中存储所有的类及数组

  >  The heap is the run-time data area from which memory for all class instances and arrays is allocated. 

* 生命周期与虚拟机同步

  >  The heap is created on virtual machine start-up. 

* OutOfMemoryError

#### 1.4 Java 虚拟机栈 （Java Virtual Machine Stacks）

* StackOverflowError
* OutOfMemoryError

#### 1.5 本地方法栈 （Native Method Stacks）

> Each Java Virtual Machine thread has a private *Java Virtual Machine stack*, created at the same time as the thread. A Java Virtual Machine stack stores frames 

> A new frame is created each time a method is invoked. A frame is destroyed when its method invocation completes, whether that completion is normal or abrupt (it throws an uncaught exception). 

#### 1.6 程序计数器 （The pc Register）

每个线程都有一个私有的程序计数器，指向方法区中的 **方法字节码**（用来存储指向下一条指令的地址）

##### 1. 栈帧 Frame  

> https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.6
>
> >A new frame is created each time a method is invoked. 



栈帧：每个栈帧对应一个被调用的方法，可以理解为一个方法的运行空间

栈帧的构成：局部变量表（Local Variables）、操作数栈、动态链接、方法返回地址和附加信息。

局部变量表：用于存放方法中定义的局部变量及方法参数

局部变量表中的变量不可以直接使用，必须通过相关指令将变量加载到操作数栈中作为操作数使用。

操作数栈：以压栈和出栈的方式存储操作数



![image-20191223220249227](C:\Users\wwawp\AppData\Roaming\Typora\typora-user-images\image-20191223220249227.png)

> 将 class 文件反编译成计算机指令文件
>
> > javap -c Person.class > Person.txt

```java
package com.anqiu.jvm;

public class Person {

    private final static String hobby = "Programming";
    private static String address;
    private final double salary = 100;
    private String name = "Jack";
    private int age;

    public static int calc(int op1, int op2) {
        op1 = 3;
        int result = op1 + op2;
        return result;
    }

    public String concat(String str1, String str2) {
        str1 = "aaa";
        String str3 = str1 + str2;
        return str3;
    }
    
    public static void order() {
    }

    public static void main(String[] args) {
        calc(1, 2);
        order();
    }

    public void say() {
        System.out.println("person say...");
    }
}
```

class 文件编译成计算机指令

```
  public static int calc(int, int);
    Code:
       0: iconst_3
       1: istore_0
       2: iload_0
       3: iload_1
       4: iadd
       5: istore_2
       6: iload_2
       7: ireturn

  public java.lang.String concat(java.lang.String, java.lang.String);
    Code:
       0: ldc           #7          // String aaa
       
       2: astore_1
       3: new           #8          // class java/lang/StringBuilder
       
       6: dup
       7: invokespecial #9          // Method java/lang/StringBuilder."<init>":()V
      
      10: aload_1
      11: invokevirtual #10               
  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
  
  	  14: aload_2
      15: invokevirtual #10               
  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
  
      18: invokevirtual #11
      // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      
      21: astore_3
      22: aload_3
      23: areturn

```

指令

```java
public static int calc(int op1, int op2) {
//加载方法时，局部变量表中存储3个变量（变量0 op1，变量1 op2，变量2 result）和方法返回地址
    
    op1 = 3;
    // 0:iconst_3  iconst：Push int constant
	// 将3以 int 的类型 push 到操作数栈中 
 	
    // 1:istore_0  istore：Store int into local variable  将操作数栈中的值出栈，赋值给局部变量表中变量
	// 将栈中的值出栈，取得3，以 int 的类型存储到局部变量表中，赋值给第0个变量。 
    
    
    int result = op1 + op2;
    // 2: iload_0  iload：Load int from local variable
	// 取得参数 op1 的值，入栈到操作数栈
    
    // 3: iload_1
    // 取得参数 op2 的值，入栈到操作数栈中 （假设 op2 = 10）
    
    // 4: iadd  iadd：Add int
	// 将操作数据栈中的两个整数值出栈，相加
    
    // 5: istore_2
    // 将 add 计算后得到的值13，将13存储到局部变量表的第2个变量
      
   
    return result;
    // 6: iload_2
    // 取得参数2的值13，入栈到操作数栈中
    
    // ireturn  Return int from method
	// 将操作数栈中的值出栈，取得13，通过返回方法地址，返回数值
}
```

### 2. 内存模型 （JMM）



### 3. 区别

运行时数据区：代码运行时的状态

内存模型：物理落地，划分为 MetaSpace、Heap

符号引用



#### ![image-20191224123806238](E:\github\document\JVM\image-20191224123806238.png)

对象头：包含两到三个部分（数组对象）内容，通过 Class Pointer 指向方向区，标明实例对象具体属于哪个类





![image-20191224154815567](E:\github\document\JVM\image-20191224154815567.png)

Eden：S0：S1 = 8：1：1

Young GC：  包含 Eden 区和 Survivor 区，也叫做 Minor GC

Old GC：也称为 Major GC，通常伴随着 Minor GC，也就意味着会触发 Full GC

Young GC + Old GC = Full GC

Full GC 会 STW （Stop the World），所以要尽可能的减少 Full GC 的频率



JVM 参数

设置虚拟机栈大小：-Xss128K，默认大小 1M，栈的深度通常设置到 3000-5000 



Garbage Collect

GC ROOT：长时间存在，及具有存在的意义

* 虚拟机栈中的本地变量、static 成员、常量引用、本地方法栈中的变量
* 类加载器
* Thread

Java 进程中的堆内存对象，导出文件 heap.hprof	