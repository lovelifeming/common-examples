package com.zsm.commonexample.binarytree;

/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/7 16:18.
 * @Modified By: 树节点类
 */
public class TreeNode<Value extends Comparable<? super Value>>
{
    private Value value;

    private TreeNode left;

    private TreeNode right;

    public Value getValue()
    {
        return value;
    }

    public void setValue(Value value)
    {
        this.value = value;
    }

    public TreeNode getLeft()
    {
        return left;
    }

    public void setLeft(TreeNode left)
    {
        this.left = left;
    }

    public TreeNode getRight()
    {
        return right;
    }

    public void setRight(TreeNode right)
    {
        this.right = right;
    }

    public TreeNode(Value value)
    {
        this.value = value;
    }
}
