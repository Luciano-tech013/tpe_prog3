package utils.Tree;

import entidades.Tarea;

import java.util.LinkedList;
import java.util.List;

public class Tree {
    private TreeNode root;

    public Tree() {
        this.root = null;
    }

    public void add(int value, Tarea elem) {
        if(this.root == null) {
            this.root = new TreeNode(value);
            root.addElement(elem);
        }
        else
            add(this.root, value, elem);
    }

    /*
        Costo computacional: o(h) donde h = altura del arbol. Porque en el peor de los casos vamos a tener que insertar despues del nodo hoja mas lejano
    */
    private void add(TreeNode node, int value, Tarea elem) {
        if(value > node.getValue()) {
            if(node.getRight() == null) {
                TreeNode newNode = new TreeNode(value);
                newNode.addElement(elem);
                node.setRight(newNode);
                node.getRight().setFather(node);
            } else
                add(node.getRight(), value, elem);
        } else if (value < node.getValue()) {
            if(node.getLeft() == null) {
                TreeNode newNode = new TreeNode(value);
                newNode.addElement(elem);
                node.setLeft(newNode);
                node.getLeft().setFather(node);
            } else {
                add(node.getLeft(), value, elem);
            }
        } else {
            node.addElement(elem);
        }
    }

    public int getRoot() {
        return root.getValue();
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public List<Tarea> searchNodesByRange(int min, int max) {
        if(this.isEmpty())
            return null;

        if((min < 0 || max > 100) || (min < 0 && max > 100))
            return null;

        return searchNodesByRange(min, max, new LinkedList<Tarea>(), this.root);
    }

    private List<Tarea> searchNodesByRange(int min, int max, LinkedList<Tarea> resultado, TreeNode node) {
        if(node != null) {
            if (node.getValue() >= min && node.getValue() <= max) {
                resultado.addAll(node.getElementos());
                if (node.getLeft() != null)
                    searchNodesByRange(min, max, resultado, node.getLeft());

                if (node.getRight() != null)
                    searchNodesByRange(min, max, resultado, node.getRight());
            }
            else {
                if (node.getValue() < min)
                    searchNodesByRange(min, max, resultado, node.getRight());

                if (node.getValue() > max)
                    searchNodesByRange(min, max, resultado, node.getLeft());
            }
        }

        return resultado;
    }
<<<<<<< HEAD

    /*public boolean hasElem(int elem) {
        return hasElem(this.root, elem);
    }

    /*
        Costo computacional: o(h) donde h = altura del arbol, porque en el peor de los casos tendré que ir al nodo mas lejano (no encontro el elemento)
    */
    /*private boolean hasElem(TreeNode node, int elem) {
        if(node == null)
            return false;

        if(elem > node.getValue())
            return hasElem(node.getRight(), elem);
        else if (elem < node.getValue())
            return hasElem(node.getLeft(), elem);

        return true;
    }*/

    /*
        Costo computacional: o(h) donde h = altura del arbol, porque en el peor de los casos el nodo a borrar sea la hoja mas lejana (altura)
    */
    /*public boolean delete(int value) {
        if(this.isEmpty())
            return false;

        //Busco nodo padre del nodo a borrar
        TreeNode nodeFather = this.search(this.root, value);

        if (nodeFather == null)
            return false;

        //Obtengo el nodo a borrar
        TreeNode nodeToDelete = nodeFather.getLeft();

        //Verifico los 3 casos posibles de borrado
        if (nodeToDelete.getLeft() != null && nodeToDelete.getRight() != null) {
            nodeFather.setLeft(findMaxinMinValues(nodeToDelete)); //Porque estableci en este caso que se tiene en cuenta el mayor de los menores
        } else if ((nodeToDelete.getLeft() == null && nodeToDelete.getRight() == null)) {
            nodeFather.setLeft(nodeToDelete.getLeft());
        } else {
            if (nodeToDelete.getLeft() != null)
                nodeFather.setLeft(nodeToDelete.getLeft());
            else
                nodeFather.setLeft(nodeToDelete.getRight());
        }

        return true;
    }


        Complejidad Computacional: o(h) donde h = altura del arbol, porque en el peor de los casos tengo que ir al nodo mas lejano (el elemento no existe)

    @Nullable
    private TreeNode search(TreeNode node, int value) {
        if(node == null)
            return null;

        if(value > node.getValue())
            return search(node.getRight(), value);
        else if (value < node.getValue())
            return search(node.getLeft(), value);

        return node.getFather();
    }


    Complejidad Computacional: o(n) donde N = nivel del arbol, porque en el peor de los casos me tendré que ir al nodo hoja que esta mas a la derecha

    private TreeNode findMaxinMinValues(TreeNode node) {
        if(node.getRight() == null)
            return node;

        return findMaxinMinValues(node.getRight());
    }

    public int maxDeepth() {
        if(this.isEmpty())
            return -1;

        return maxDeepth(this.root);
    }


        Costo computacional: Si mi arbol es una enredadera (lista vinculada) o esta balanceado, en el peor de los casos es un 0(n) donde n = cant de nodos, porque tuve que ir
        hasta el ultimo nodo del arbol
        Si mi arbol completo, el costo es 0(log2N), porque en el peor de los casos la altura está controlada (es igual para todos los nodos del ultimo nivel

    private int maxDeepth(TreeNode node) {
        if(node == null)
            return 0;

        if(node.getRight() == null && node.getLeft() == null)
            return 0;

        int leftDepth = 0; int rightDepth = 0;

        if(node.getLeft() != null)
            leftDepth = maxDeepth(node.getLeft()) + 1;

        if(node.getRight() != null)
            rightDepth = maxDeepth(node.getRight()) + 1;

        if(leftDepth >= rightDepth)
            return leftDepth;

        return rightDepth;
    }


        Los tres tipos de recorridos son o(n) porque recorro todos los nodos del arbol

    public void printPostOrder() {
        if(this.isEmpty())
            return;

        printPostOrder(this.root);
    }

    private void printPostOrder(TreeNode node) {
        if(node == null)
            return;

        printPostOrder(node.getLeft());

        printPostOrder(node.getRight());

        System.out.print(node.getValue() + " | ");
    }

    public void printPreOrder() {
        if(this.isEmpty())
            return;

        printPreOrder(this.root);
    }

    private void printPreOrder(TreeNode node) {
        if(node == null)
            return;

        System.out.print(node.getValue() + " | ");

        printPreOrder(node.getLeft());

        printPreOrder(node.getRight());
    }

    public void printInOrder() {
        if(this.isEmpty())
            return;

        printInOrder(this.root);
    }

    private void printInOrder(TreeNode node) {
        if(node == null)
            return;

        printInOrder(node.getLeft());

        System.out.print(node.getValue() + " | ");

        printInOrder(node.getRight());
    }*/

    /*public MySimpleLinkedList<Integer> getLongestBranch() {
        MySimpleLinkedList<Integer> list = new MySimpleLinkedList<Integer>();
        if(this.isEmpty())
            return null;

        return getLongestBranch(this.root, list);
    }

    /*
        Costo computacional: Si mi arbol es una enredadera, o está balanceado, en el peor de los casos es un o(n) donde n = cantidad de nodos) porque tuve que llegar
        hasta el ultimo nodo de la lista
        Si mi arbol es uno completo, en el peor de los casos es un o(log n), porque no hay rama mas larga, es igual para los nodos del ultimo nivel
    */
    /*private MySimpleLinkedList<Integer> getLongestBranch(TreeNode node, MySimpleLinkedList<Integer> list) {
        if(node == null)
            return null;

        int leftDepth = maxDeepth(node.getLeft()) + 1;
        int rightDepth = maxDeepth(node.getRight()) + 1;

        list.insertOrder(node.getValue());
        if(leftDepth >= rightDepth)
            getLongestBranch(node.getLeft(), list);
        else
            getLongestBranch(node.getRight(), list);

        return list;
    }

    public MySimpleLinkedList<Integer> getFrontera() {
        MySimpleLinkedList<Integer> list = new MySimpleLinkedList<Integer>();
        if(this.isEmpty())
            return null;

        searchSheet(list, this.root.getLeft());
        searchSheet(list, this.root.getRight());

        return list;
    }

    /*
        Costo computacional: o(h) donde h = altura del arbol, porque en el peor de los casos mi nodo hoja a insertar sea el mas lejano
    */
    /*private void searchSheet(MySimpleLinkedList<Integer> list, TreeNode node) {
        if(node.getLeft() == null && node.getRight() == null)
            list.insertOrder(node.getValue());

        if (node.getLeft() != null)
            searchSheet(list, node.getLeft());

        if(node.getRight() != null)
            searchSheet(list, node.getRight());
    }

    public Integer getMaxElem() {
        if(this.isEmpty())
            return -1;

        return getMaxElem(this.root);
    }

    /*
        Costo computacional: o(n) donde n = nodos porque en el peor de los casos tengo que ir hasta el ultimo nodo (el nodo hoja)
    */
    /*private Integer getMaxElem(TreeNode node) {
        if(node.getRight() == null)
            return node.getValue();
        else
            return getMaxElem(node.getRight());
    }

    public MySimpleLinkedList<Integer> getElemAtLevel(int level) {
        MySimpleLinkedList<Integer> list = new MySimpleLinkedList<Integer>();
        if(this.isEmpty() || level < 0)
            return null;

        return getElemAtLevel(this.root, level, 0, list);
    }

    /*
            Costo computacional: o(n) donde n = nivel del arbol, porque el peor de los casos seria que me pasen el ultimo nivel del arbol
     */
    /*private MySimpleLinkedList<Integer> getElemAtLevel(TreeNode node, int level, int contador, MySimpleLinkedList<Integer> list) {
        if(contador == level)
            list.insertOrder(node.getValue());

        if (node.getLeft() != null)
            getElemAtLevel(node.getLeft(), level, contador + 1, list);

        if (node.getRight() != null)
            getElemAtLevel(node.getRight(), level, contador + 1, list);

        return list;
    }

    public int sumInternalNodes() {
        return sumInternalNodes(this.root);
    }

    private int sumInternalNodes(TreeNode node) {
        if(node.getRight() == null && node.getLeft() == null)
            return 0;

        int sum;
        if(node.getRight() != null && node.getLeft() != null) {
            sum = sumInternalNodes(node.getLeft());
            sum += sumInternalNodes(node.getRight()) + node.getValue();
        } else {
            if (node.getRight() != null)
                return sumInternalNodes(node.getRight()) + node.getValue();
            else
                return sumInternalNodes(node.getLeft()) + node.getValue();
        }

        return sum;
    }

    public MySimpleLinkedList<Integer> getMajorValuesOfSheet(int K, MySimpleLinkedList<Integer> list) {
        return getMajorValuesOfSheet(this.root, K, list);
    }

    private MySimpleLinkedList<Integer> getMajorValuesOfSheet(TreeNode node, int K, MySimpleLinkedList<Integer> list) {
        if(node.getValue() > K && node.getRight() == null && node.getLeft() == null)
            list.insertOrder(node.getValue());

        if(node.getRight() != null)
                getMajorValuesOfSheet(node.getRight(), K, list);

        if(node.getLeft() != null)
                getMajorValuesOfSheet(node.getLeft(), K, list);

        return list;
    }*/


=======
>>>>>>> 745d7901ce769c01f5fd6412d7df4dffa4edc52e
}

