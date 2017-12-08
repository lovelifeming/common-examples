package com.zsm.commonexample.binarytree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/7 17:00.
 * @Modified By:
 */
public class TreeOperatorTest
{
    private TreeNode<Character> treeNode;

    @Before
    public void setUp()
        throws Exception
    {
        /**
         *   树节点      A
         *        B             C
         *   D        E     F        G
         *         H
         *
         */
        treeNode = new TreeNode<>('A');
        treeNode.setLeft(new TreeNode<>('B'));
        treeNode.getLeft().setLeft(new TreeNode<>('D'));
        treeNode.getLeft().setRight(new TreeNode<>('E'));
        treeNode.getLeft().getRight().setLeft(new TreeNode<>('H'));
        treeNode.setRight(new TreeNode<>('C'));
        treeNode.getRight().setLeft(new TreeNode<>('F'));
        treeNode.getRight().setRight(new TreeNode<>('G'));
    }

    @Test
    public void levelOrder()
        throws Exception
    {
        TreeOperator.levelOrder(treeNode);
    }

    @Test
    public void frontOrder()
        throws Exception
    {
        TreeOperator.frontOrder(treeNode);
    }

    @Test
    public void middleOrder()
        throws Exception
    {
        TreeOperator.middleOrder(treeNode);
    }

    @Test
    public void laterOrder()
        throws Exception
    {
        TreeOperator.laterOrder(treeNode);
    }

    @Test
    public void treeDepth()
    {
        int depth = TreeOperator.treeDepth(treeNode);
        System.out.println(depth);
        Assert.assertEquals(depth, 4);
    }

    @Test
    public void leafCount()
    {
        int count = TreeOperator.leafCount(treeNode);
        System.out.println(count);
        Assert.assertEquals(count, 4);
    }
}
