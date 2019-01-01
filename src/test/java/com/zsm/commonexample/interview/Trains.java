package com.zsm.commonexample.interview;

import org.junit.Test;


/**
 * Problem one: Trains
 * <p>
 * The local commuter railroad services a number of towns in Kiwiland.  Because of monetary concerns, all of the tracks
 * are 'one-way.'  That is, a route from Kaitaia to Invercargill does not imply the existence of a route from
 * Invercargill to Kaitaia.  In fact, even if both of these routes do happen to exist, they are distinct and are
 * not necessarily the same distance!
 * <p>
 * The purpose of this problem is to help the railroad provide its customers with information about the routes.
 * In particular, you will compute the distance along a certain route, the number of different routes between two towns,
 * and the shortest route between two towns.
 * <p>
 * Input:  A directed graph where a node represents a town and an edge represents a route between two towns.
 * The weighting of the edge represents the distance between the two towns.  A given route will never appear more than
 * once, and for a given route, the starting and ending town will not be the same town.
 * <p>
 * Output: For test input 1 through 5, if no such route exists, output 'NO SUCH ROUTE'.  Otherwise, follow the route as
 * given; do not make any extra stops!  For example, the first problem means to start at city A, then travel directly to
 * city B (a distance of 5), then directly to city C (a distance of 4).
 * The distance of the route A-B-C.
 * The distance of the route A-D.
 * The distance of the route A-D-C.
 * The distance of the route A-E-B-C-D.
 * The distance of the route A-E-D.
 * The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below,
 * there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
 * The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below,
 * there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
 * The length of the shortest route (in terms of distance to travel) from A to C.
 * The length of the shortest route (in terms of distance to travel) from B to B.
 * The number of different routes from C to C with a distance of less than 30.  In the sample data,
 * the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
 * <p>
 * Test Input:
 * For the test input, the towns are named using the first few letters of the alphabet from A to D.
 * A route between two towns (A to B) with a distance of 5 is represented as AB5.
 * Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
 * Expected Output:
 * Output #1: 9
 * Output #2: 5
 * Output #3: 13
 * Output #4: 22
 * Output #5: NO SUCH ROUTE
 * Output #6: 2
 * Output #7: 3
 * Output #8: 9
 * Output #9: 9
 * Output #10: 7
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/11/8/008.
 * @Modified By:
 */
public class Trains
{
    /**
     * 最短路径长度
     */
    private static int MIN = Integer.MAX_VALUE;

    /**
     * 达到条件线路条数总和
     */
    private static int COUNT = 0;

    /**
     * 输入数据转换为二维数组
     */
    private static int[][] ARRAY_MAP = {
        {0, 5, 0, 5, 7},
        {0, 0, 4, 0, 0},
        {0, 0, 0, 8, 2},
        {0, 0, 8, 0, 6},
        {0, 3, 0, 0, 0}};

    @Test
    public void testTrains()
    {
        //1-5测试
        System.out.println("Output #1:" + findRouteLength("A-B-C"));
        System.out.println("Output #2:" + findRouteLength("A-D"));
        System.out.println("Output #3:" + findRouteLength("A-D-C"));
        System.out.println("Output #4:" + findRouteLength("A-E-B-C-D"));
        System.out.println("Output #5:" + findRouteLength("A-E-D"));
        //6测试
        System.out.println("Output #6:" + findRouteLTStops('C' - 65, 'C' - 65, 2));
        //7测试
        System.out.println("Output #7:" + findRouteEQStops('A' - 65, 'C' - 65, 4));
        //8、9测试
        findRouteByShortest('A' - 65, 'C' - 65, 0, "A");
        System.out.println("Output #8:" + MIN);
        findRouteByShortest('B' - 65, 'B' - 65, 0, "B");
        System.out.println("Output #9:" + MIN);
        //10测试
        conditionalCount('C' - 65, 'C' - 65, 0, "C");
        System.out.println("Output #10:" + COUNT);
    }

    /**
     * 查找指定路径长度
     *
     * @param path
     * @return
     */
    private static String findRouteLength(String path)
    {
        String[] split = path.split("-");
        int result = 0;
        int index = split.length - 1;
        for (int i = 0; i < index; i++)
        {
            int start = split[i].charAt(0) - 65;
            int end = split[i + 1].charAt(0) - 65;
            int length = ARRAY_MAP[start][end];
            if (length > 0)
            {
                result += length;
            }
            else
            {
                return "NO SUCH ROUTE";
            }
        }
        if (result > 0)
        {
            return String.valueOf(result);
        }
        else
        {
            return "NO SUCH ROUTE";
        }
    }

    /**
     * 查找小于指定站点数的路径数
     *
     * @param start
     * @param end
     * @param step
     * @return
     */
    private static int findRouteLTStops(int start, int end, int step)
    {
        int result = 0;
        int[] ints = ARRAY_MAP[start];
        for (int i = 0; i < ints.length; i++)
        {
            if (step == 0)
            {
                break;
            }
            if (ints[i] > 0)
            {
                if (i == end)
                {
                    result += 1;
                }
                else
                {
                    result += findRouteLTStops(i, end, step - 1);
                }
            }
        }
        return result;
    }

    /**
     * 查找等于指定站点数的路径数
     *
     * @param start
     * @param end
     * @param step
     * @return
     */
    private static int findRouteEQStops(int start, int end, int step)
    {
        if (step < 1)
        {
            return 0;
        }
        int result = 0;
        int[] ints = ARRAY_MAP[start];
        for (int i = 0; i < ints.length; i++)
        {
            if (ints[i] > 0)
            {
                if (i == end && step == 1)
                {
                    result += 1;
                }
                else
                {
                    result += findRouteEQStops(i, end, step - 1);
                }
            }
        }
        return result;
    }

    /**
     * 查找最短路径的长度
     *
     * @param start
     * @param end
     * @param pathVal
     * @param pathString
     */
    private static void findRouteByShortest(int start, int end, int pathVal, String pathString)
    {
        if (pathString.chars().filter(n -> n == start + 65).count() > 5)
        {
            return;
        }
        int[] ints = ARRAY_MAP[start];
        for (int i = 0; i < ints.length; i++)
        {
            if (ints[i] > 0)
            {
                String path = pathString + String.valueOf((char)(i + 65));
                int value = pathVal + ints[i];
                if (i == end)
                {
                    if (value < MIN && value > 0)
                    {
                        MIN = value;
                    }
//                    System.out.println(path + ":" + value);
                }
                else
                {
                    findRouteByShortest(i, end, value, path);
                }
            }
        }
    }

    /**
     * 查找路径长度小于30的路径数
     *
     * @param start
     * @param end
     * @param pathVal
     * @param pathString
     */
    private static void conditionalCount(int start, int end, int pathVal, String pathString)
    {
        //路径长度小于30
        if (pathVal > 30)
        {
            return;
        }
        int[] ints = ARRAY_MAP[start];
        for (int i = 0; i < ints.length; i++)
        {
            if (ints[i] > 0)
            {
                String path = pathString + String.valueOf((char)(i + 65));
                int value = pathVal + ints[i];
                if (i == end && value < 30)
                {
                    COUNT++;
//                    System.out.println(path + ":" + value);
                }
                conditionalCount(i, end, value, path);
            }
        }
    }
}
