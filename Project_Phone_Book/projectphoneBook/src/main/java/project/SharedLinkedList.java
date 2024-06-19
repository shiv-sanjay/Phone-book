package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SharedLinkedList {
    private static SharedLinkedList instance;

    // Node class for the doubly linked list
    private static class Node {
        String id;
        String name;
        String contact;
        String city;
        Node next;
        Node previous;

        Node(String id, String name, String contact, String city) {
            this.id = id;
            this.name = name;
            this.contact = contact;
            this.city = city;
        }
    }

    // Head of the doubly linked list
    private Node head = null;

    // Private constructor to prevent direct instantiation
    private SharedLinkedList() {
        addingNode();
    }

    // Singleton method to get the shared instance
    public static SharedLinkedList getInstance() {
        if (instance == null) {
            instance = new SharedLinkedList();
        }
        return instance;
    }

    // Method to add a node to the doubly linked list
    public void addNode(String id, String name, String contact, String city) {
        Node newNode = new Node(id, name, contact, city);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.previous = current;
        }
    }

    // Method to update a node in the doubly linked list
    public void updateNode(String oldValue, String name,String con,String Cit) {
        Node current = head;
        while (current != null) {
            if (current.id.equals(oldValue)) {
                current.name = name;
                current.contact=con;
                current.city=Cit;
                break; // Stop after the first occurrence is updated
            }
            current = current.next;
        }
    }

    // Method to search for a node by ID
    public Node searchNodeByID(String id) {
        Node current = head;
        while (current != null) {
            if (current.id.equals(id)) {
                return current;
            }
            current = current.next;
        }
        return null; // Node with the specified ID not found
    }

    // Method to delete a node by ID
    public void deleteNodeByID(String id) {
        Node nodeToDelete = searchNodeByID(id);
        if (nodeToDelete != null) {
            if (nodeToDelete.previous != null) {
                nodeToDelete.previous.next = nodeToDelete.next;
            } else {
                head = nodeToDelete.next;
            }
            if (nodeToDelete.next != null) {
                nodeToDelete.next.previous = nodeToDelete.previous;
            }
        }
    }

    void addingNode() {
        String filepath = "Records.txt";
        File file = new File(filepath);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // get first line
            // get the columns name
            // set column name to the jtable
            String firstline = br.readLine();
            String[] columnsName = firstline.split(":");

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                for (int i = 0; i < data.length; i = i + 4) {
                    addNode(data[i], data[i + 1], data[i + 2], data[i + 3]);
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SharedLinkedList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SharedLinkedList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayList() {
        System.out.println("Shared Doubly Linked List:");
        Node current = head;
        while (current != null) {
            System.out.println(current.id + " " + current.name + " " + current.contact + " " + current.city);
            current = current.next;
        }
    }

}
