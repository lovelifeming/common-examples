package com.zsm.commonexample.util;

import com.zsm.commonexample.model.User;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Java8 Stream流操作示例,Stream 三特性：
 * 1.stream不存储数据
 * 2.stream不改变源数据
 * 3.stream的延迟执行特性
 * <p>
 * JDK 中的流来源
 * 方法	描述
 * Collection.stream()	使用一个集合的元素创建一个流。
 * Stream.of(T...)	使用传递给工厂方法的参数创建一个流。
 * Stream.of(T[])	使用一个数组的元素创建一个流。
 * Stream.empty()	创建一个空流。
 * Stream.iterate(T first, BinaryOperator<T> f)	创建一个包含序列 first, f(first), f(f(first)), ... 的无限流
 * Stream.iterate(T first, Predicate<T> test, BinaryOperator<T> f)	（仅限 Java 9）类似于 Stream.iterate(T first,
 * BinaryOperator<T> f)，但流在测试预期返回 false 的第一个元素上终止。
 * Stream.generate(Supplier<T> f)	使用一个生成器函数创建一个无限流。
 * IntStream.range(lower, upper)	创建一个由下限到上限（不含）之间的元素组成的 IntStream。
 * IntStream.rangeClosed(lower, upper)	创建一个由下限到上限（含）之间的元素组成的 IntStream。
 * BufferedReader.lines()	创建一个有来自 BufferedReader 的行组成的流。
 * BitSet.stream()	创建一个由 BitSet 中的设置位的索引组成的 IntStream。
 * Stream.chars()	创建一个与 String 中的字符对应的 IntStream。
 * <p>
 * 中间操作负责将一个流转换为另一个流，中间操作包括 filter()（选择与条件匹配的元素）、map()（根据函数来转换元素）
 * 、distinct()（删除重复）、limit()（在特定大小处截断流）和 sorted()。
 * 中间操作始终是惰性的：调用中间操作只会设置流管道的下一个阶段，不会启动任何操作。重建操作可进一步划分为无状态 和有状态 操作。
 * 无状态操作（比如 filter() 或 map()）可独立处理每个元素，而有状态操作（比如 sorted() 或 distinct()）可合并以前看到的影响其他元素处理的元素状态。
 * <p>
 * 中间流操作
 * 操作	内容
 * filter(Predicate<T>)	与预期匹配的流的元素
 * map(Function<T, U>)	将提供的函数应用于流的元素的结果
 * Stream<T> peek(Consumer<? super T> action)  提供一个没有返回值的λ表达式消费数据,返回流
 * flatMap(Function<T, Stream<U>>	将提供的流处理函数应用于流元素后获得的流元素
 * distinct()	已删除了重复的流元素
 * sorted()	按自然顺序排序的流元素
 * Sorted(Comparator<T>)	按提供的比较符排序的流元素
 * limit(long)	截断至所提供长度的流元素
 * skip(long)	丢弃了前 N 个元素的流元素
 * takeWhile(Predicate<T>)	（仅限 Java 9）在第一个提供的预期不是 true 的元素处阶段的流元素
 * dropWhile(Predicate<T>)	（仅限 Java 9）丢弃了所提供的预期为 true 的初始元素分段的流元素
 * <p>
 * 数据集的处理在执行终止操作时开始，比如缩减（sum() 或 max()）、应用 (forEach()) 或搜索 (findFirst()) 操作。
 * 终止操作会生成一个结果或副作用。执行终止操作时，会终止流管道，如果您想再次遍历同一个数据集，可以设置一个新的流管道。
 * <p>
 * 终止流操作
 * 操作	描述
 * forEach(Consumer<T> action)	将提供的操作应用于流的每个元素。
 * toArray()	使用流的元素创建一个数组。
 * reduce(...)	将流的元素聚合为一个汇总值。
 * collect(...)	将流的元素聚合到一个汇总结果容器中。
 * min(Comparator<T>)	通过比较符返回流的最小元素。
 * max(Comparator<T>)	通过比较符返回流的最大元素。
 * count()	返回流的大小。
 * {any,all,none}Match(Predicate<T>)	返回流的任何/所有元素是否与提供的预期相匹配。
 * findFirst()	返回流的第一个元素（如果有）。
 * findAny()	返回流的任何元素（如果有）。
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/10/30.
 * @Modified By:
 */
public class Java8StreamTest
{

    /**
     * 测试Java8 stream流式处理操作
     */
    @Test
    public void testStream()
    {
        List<User> users = getUsers();

        //users.parallelStream()  Stream并行处理
        Optional<User> jason = users.stream().filter(s -> s.getName().equals("jason")).findFirst();
        System.out.println("循环查找第一个名叫jason的用户：" + jason.get().getName());
        long count = users.stream().filter(s -> s.getName().equals("jason")).count();
        System.out.println("循环查找名叫json的人总数：" + count);

        List<User> list1 = users.stream().filter(s -> s.getName().contains("jason")).collect(Collectors.toList());
        System.out.println("循环查找名叫jason的人个数：" + list1.size());

        Map<Integer, List<User>> collect = users.stream().collect(Collectors.groupingBy(User::getAge));
        System.out.println("按照用户年龄进行分组:" + collect.size());

        Set<String> set = users.stream().flatMap(u -> u.getTags().stream()).collect(Collectors.toSet());
        System.out.println("获取所有用户标签：" + set.size());

        List<Integer> ages = users.stream().map(u -> u.getAge() + 1).collect(Collectors.toList());
        System.out.println("获取所有用户年龄：" + ages.toString());

        Map<String, User> userMap = users.stream()
            .collect(Collectors.toMap(u -> u.getName() + u.getAge(), Function.identity()));
        System.out.println("用户名字典集合总数：" + userMap.size());

        IntSummaryStatistics statistics = users.stream().mapToInt(u -> u.getAge()).summaryStatistics();
        System.out.println("年龄最大是：" + statistics.getMax());
        System.out.println("年龄最小是：" + statistics.getMin());
        System.out.println("平均年龄是：" + statistics.getAverage());
        System.out.println("年龄总和是：" + statistics.getSum());

        //创建流
        Stream<User> userStream = users.stream();
        Stream<String> stream = Stream.of("a", "b", "c", "d", "e", "f");
        String[] array = new String[] {"a", "b", "c", "d", "e", "f"};
        Stream<String> stringStream = Stream.of(array);
        Stream<String> stringStream1 = Arrays.stream(array);
        Stream<Object> empty = Stream.empty();

    }

    private List<User> getUsers()
    {
        User user = new User();
        user.setName("xiaoming");
        user.setAge(18);
        user.setTags(new ArrayList<String>()
        {
            {add("geek");}

            {add("active");}
        });

        User user1 = new User();
        user1.setName("mingming");
        user1.setAge(20);
        user1.setTags(new ArrayList<String>()
        {
            {add("alert");}

            {add("aspiring");}
        });

        User user2 = new User();
        user2.setName("xiaoming");
        user2.setAge(19);
        user2.setTags(new ArrayList<String>()
        {
            {add("attractive");}

            {add("audacious");}
        });

        User user3 = new User();
        user3.setName("xiaohong");
        user3.setAge(20);
        user3.setTags(new ArrayList<String>()
        {
            {add("geek");}

            {add("alert");}
        });

        User user4 = new User();
        user4.setName("jason");
        user4.setAge(22);
        user4.setTags(new ArrayList<String>()
        {
            {add("audacious");}

            {add("geek");}
        });
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        return users;
    }
}
