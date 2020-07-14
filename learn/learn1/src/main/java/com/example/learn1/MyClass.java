package com.example.learn1;

import java.util.ArrayList;

import static com.example.learn1.c1.*;

public class MyClass {
    public static void main(String[] args) {
        ArrayList a=new ArrayList();
        a.add(1);
        a.add("yu");
        a.add(false);
        //a.remove(new Integer(1));
        for(Object sio:a){
            System.out.println(sio);
        }
        System.out.println(a);
       c1 c=new c1("nothing","blue",23);
       c1 c2=new c1();
       //c.setColor("blue");
       //c.setModel(23);
       //c.setName("nothing");
        c.des();
        c2.des();
        person p=new person("yuvraj",300000000,27);
        p.des();
    }

    public static int add(int a,int b){
        System.out.println("sum is"+(a+b));
        int c=a+b;
        return c;
    }
}