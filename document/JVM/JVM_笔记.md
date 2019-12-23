# JVM 笔记

## Java 虚拟机栈和栈帧

### **栈帧 Frame**  

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

