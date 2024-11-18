package org.andersen.homework;

import org.andersen.homework.util.collection.MyArrayList;
import org.andersen.homework.util.collection.MyHashSet;

public class MainClass {

  public static void main(String[] args) {
    System.out.println("/// MyArrayList ///\n");
    MyArrayList<Integer> myArrayList = new MyArrayList<>(0);
    myArrayList.add(1);
    myArrayList.add(2);
    myArrayList.add(3);
    myArrayList.add(4);
    System.out.println(myArrayList);
    System.out.println("2nd element: " + myArrayList.get(1));
    myArrayList.remove(1);
    myArrayList.remove(1);
    System.out.println("Size: " + myArrayList.size());
    myArrayList.trimToSize();
    System.out.println(myArrayList);

    System.out.println("\n-----------------------------\n");

    System.out.println("/// MyHashSet ///\n");
    MyHashSet<Integer> myHashSet = new MyHashSet<>();
    System.out.println("Size: " + myHashSet.size());
    myHashSet.add(1);
    myHashSet.add(2);
    myHashSet.add(2);
    myHashSet.add(3);
    System.out.println(myHashSet);
    System.out.println("Is contains '" + 3 + "' : " + myHashSet.contains(3));
    System.out.println("Size: " + myHashSet.size());
    myHashSet.remove(3);
    myHashSet.remove(4);
    myHashSet.add(7);
    myHashSet.add(8);
    myHashSet.iterator().forEachRemaining(System.out::print);
  }
}
