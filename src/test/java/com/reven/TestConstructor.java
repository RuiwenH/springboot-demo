package com.reven;
class Base{
    static{     System.out.println("base static");    }
    public Base(){
        System.out.println("base constructor");
    }
}
public class TestConstructor extends Base{
    static{      System.out.println("test static");    }
    public TestConstructor(){
        System.out.println("test constructor");
    }
    public static void main(String[] args) {
        new TestConstructor();
    }
}
