/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.supermarketmanager;

/**
 * Veer Manoram 
 * @author User
 */

public class CustomQueue<T> {
    private Node<T> front, rear;
    private int size;
    
    /**
    Nested static class representing a single node in the linked-list implementation
    Used to store data and a reference to the next node
    */

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }
    
    /**
    Adds an item to the rear of the queue (enqueue operation)
    Maintains FIFO order and updates size. Used for storing activity history
    */
    
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
    Removes and returns the item from the front of the queue (dequeue operation)
    Throws exception if queue is empty. Used to remove oldest activities when limit is exceeded
    */
    
    public T dequeue() {
        if (front == null) throw new IllegalStateException("Queue is empty");
        T item = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return item;
    }

    /**
     Checks if the queue has no elements
     Used to determine if there are any activities to display or process
    */
    
    public boolean isEmpty() { return front == null; }
    
    /**
    Returns the current number of elements in the queue.
    Used for limiting activity history to the last 4 entries.
    */
    
    public int size() { return size; }
 
    /**
    Custom method to get all elements as an array for sorting (without modifying queue)
    Converts the linked list to an array so sorting can be performed externally
    while preserving the original queue order.
    */
    
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<T> current = front;
        int index = 0;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }
}