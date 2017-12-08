package com.zsm.commonexample.binarytree;

import java.util.LinkedList;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/7 16:22.
 * @Modified By:
 */
public class TreeOperator
{
    /**
     * 获取树节点深度
     *
     * @param node
     * @return
     */
    public static int treeDepth(TreeNode node)
    {
        if (node == null)
        {
            return 0;
        }
        return Math.max(treeDepth(node.getLeft()), treeDepth(node.getRight())) + 1;
    }

    /**
     * 获取树节点叶子节点总数
     *
     * @param node
     * @return
     */
    public static int leafCount(TreeNode node)
    {
        if (node == null)
        {
            return 0;
        }
        if (node.getLeft() == null && node.getRight() == null)
        {
            return 1;
        }
        return leafCount(node.getLeft()) + leafCount(node.getRight());
    }

    /**
     * 层序遍历，层次遍历
     *
     * @param node 树的根
     */
    public static void levelOrder(TreeNode node)
    {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty())
        {
            TreeNode cur = queue.pop();
            System.out.print(cur.getValue() + "\t");
            if (cur.getLeft() != null)
            {
                queue.add(cur.getLeft());
            }
            if (cur.getRight() != null)
            {
                queue.add(cur.getRight());
            }
        }
    }

    /**
     * 前序遍历，先遍历根
     *
     * @param node 树的根节点
     */
    public static void frontOrder(TreeNode node)
    {
        if (node == null)
        {
            return;
        }
        System.out.print(node.getValue() + "\t");
        frontOrder(node.getLeft());
        frontOrder(node.getRight());
    }

    /**
     * 中序遍历，中遍历根
     *
     * @param node 树的根节点
     */
    public static void middleOrder(TreeNode node)
    {
        if (node == null)
        {
            return;
        }
        middleOrder(node.getLeft());
        System.out.print(node.getValue() + "\t");
        middleOrder(node.getRight());
    }

    /**
     * 后序遍历，后遍历根
     *
     * @param node 树的根节点
     */
    public static void laterOrder(TreeNode node)
    {
        if (node == null)
        {
            return;
        }
        laterOrder(node.getLeft());
        laterOrder(node.getRight());
        System.out.print(node.getValue() + "\t");
    }
}
