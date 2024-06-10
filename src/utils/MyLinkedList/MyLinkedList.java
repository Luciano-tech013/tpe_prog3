package utils.MyLinkedList;

public class MyLinkedList<T extends Comparable<T>> {
    private Node<T> firstNode;
    private int size;

    public MyLinkedList(Node<T> firstNode) {
        this.firstNode = firstNode;
    }

    public void addFirst(T info) {
        Node<T> newNode = new Node<T>(info, null);
        newNode.setNext(firstNode);
        this.firstNode = newNode;
        size++;
    }

    public void insertOrder(T info) {
        if(this.isEmpty()) {
            addFirst(info);
            return;
        }

        int resultado = info.compareTo(this.firstNode.getInfo());
        if(resultado < 0 || resultado == 0) {
            addFirst(info);
            return;
        }

        Node<T> newNode = new Node<>(info, null);
        Node<T> prev = this.firstNode;
        Node<T> tmp = this.firstNode.getNext();

        while (tmp != null && info.compareTo(tmp.getInfo()) > 0) {
            prev = tmp;
            tmp = tmp.getNext();
        }

        // Insertar el nuevo nodo en la lista
        newNode.setNext(tmp);
        prev.setNext(newNode);
        size++;
    }

    public T get(int index) {
        if(this.isEmpty() || index < 0 || index > this.size) {
            return null;
        }

        int contador = 0;
        Node<T> tmp = this.firstNode;

        while(contador != index) {
            contador++;
            tmp = tmp.getNext();
        }

        return tmp.getInfo();
    }

    public boolean isEmpty() {
        return this.firstNode == null;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        Node<T> tmp = this.firstNode;
        Node<T> tmp2 = this.firstNode;
        while(tmp2 != null) {
            tmp2 = tmp.getNext();
            tmp.setNext(null);
            tmp.setInfo(null);
            tmp = tmp2;
        }
    }
}
