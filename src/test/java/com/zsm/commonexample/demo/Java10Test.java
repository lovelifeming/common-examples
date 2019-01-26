package com.zsm.commonexample.demo;

/**
 * 局部变量类型推断（Local-Variable Type Inference）var是一个保留类型名称，而不是关键字。所以之前使用var作为
 * 变量、方法名、包名的都没问题，但是如果作为类或接口名，那么这个类和接口就必须重命名了
 * var的使用场景主要有以下四种： *
 * 本地变量初始化。
 * 增强for循环中。
 * 传统for循环中声明的索引变量。
 * Try-with-resources 变量。
 * 不能使用var的地方：
 * 方法参数,全局变量,构造函数参数,方法返回类型,字段,捕获表达式(或任何其他类型的变量声明)。
 * <p>
 * Optional类添加了新的方法orElseThrow。相比于已经存在的get方法，这个方法更推荐使用
 * <p>
 * GC改进和内存管理 并行全垃圾回收器 G1。G1是Java 9中的默认GC，并且此JEP的目标是使G1平行。
 * <p>
 * <p>
 * 新增API：ByteArrayOutputStream
 * <p>
 * 新增API：List、Map、Set。这3个接口都增加了一个新的静态方法，copyOf(Collection）。
 * 这些函数按照其迭代顺序返回一个不可修改的列表、映射或包含给定集合的元素的集合。
 * <p>
 * 新增API：java.util.Properties
 * <p>
 * 新增API： Collectors收集器。
 * toUnmodifiableList();
 * toUnmodifiableSet();
 * toUnmodifiableMap(Function, Function);
 * toUnmodifiableMap(Function, Function, BinaryOperator);
 * 这四个新方法都返回 Collectors ，将输入元素聚集到适当的不可修改的集合中。
 * <p>
 * 删除工具javah(JEP 313)
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/12/5.
 * @Modified By:
 */
public class Java10Test
{
}
