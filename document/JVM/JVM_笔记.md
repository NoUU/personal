# JVM 笔记

## Java 虚拟机栈和栈帧

### **栈帧 Frame**  

> https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.6
>
> >A new frame is created each time a method is invoked. 

````
栈帧：每个栈帧对应一个被调用的方法，可以理解为一个方法的运行空
栈帧的构成：局部变量表（Local Variables）、操作数栈、动态链接、方法返回地址和附加信息。
````

> ````
> 局部变量表：用于存放方法中定义的局部变量及方法参数
> ````
>
> ````
> 操作数栈：以压栈和出栈的方式存储操作数
> ````

