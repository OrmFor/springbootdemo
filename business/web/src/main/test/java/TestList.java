package java;

import java.util.ArrayList;
import java.util.List;

public class TestList {
    public static void main(String[] args){


        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("g");
        for(String s: list){
            System.out.println(s);
        }
        list.add(0,"c");

        System.out.println("================");
        for(String s: list){

            System.out.println(s);
        }

    }
}
