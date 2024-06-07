package utils.Tree;

import entidades.Tarea;

import java.util.LinkedList;
import java.util.List;

public class TreeNode {
    private int value; //nivel de prioridad
    private List<Tarea> elementos;
    private TreeNode father;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int value) {
        this.value = value;
        this.elementos = new LinkedList<Tarea>();
        this.left = null;
        this.right = null;
    }

    public int getValue() {
        return value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public TreeNode getFather() {
        return father;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setFather(TreeNode father) {
        this.father = father;
    }

    public List<Tarea> getElementos() {
        return elementos;
    }

    public void addElement(Tarea elem) {
        this.elementos.add(elem);
    }
}
