package com.reven.throwable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Foo {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(…);
                BufferedWriter writer = new BufferedWriter(…)) {// Try-with-resources
           // do something
           catch ( IOException | XEception e) {// Multiple catch
              // Handle it
           } 

    }
}
