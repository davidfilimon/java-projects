/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pr3;
import java.util.Arrays;
/**
 *
 * @author David
 */
public class Productie {
    private String stanga;
       private String[] dreapta;

        Productie(String stanga, String[] dreapta) {
            this.stanga = stanga;
            this.dreapta = dreapta;
        }
        @Override
    public String toString() {
        return "Productie{" +
                "stanga='" + stanga + '\'' +
                ", dreapta=" + Arrays.toString(dreapta) +
                '}';
    }
     public String getStanga() {
        return stanga;
    }

   
    public String[] getDreapta() {
        return dreapta;
    }
    
}
