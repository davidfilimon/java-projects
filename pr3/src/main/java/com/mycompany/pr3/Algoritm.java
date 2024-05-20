/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pr3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author David
 */
public class Algoritm {
     Citire mC;

    Algoritm(String sursaGramatica) {
        mC = new Citire(sursaGramatica);
    }

    public boolean verificaCuvant(String cuvant) {
        List<Productie> productii = mC.daProductii();
        Set<String>[][] tabel = tabel(cuvant);

        if (tabel[cuvant.length() - 1][0].contains("S")) {
            afiseazaProductii(tabel, cuvant);
            return true;
        } else {
            return false;
        }
    }

    public Set<String>[][] getTabel(String cuvant) {
        return tabel(cuvant);
    }

private void afiseazaProductii(Set<String>[][] tabel, String cuvant) {
    int n = cuvant.length();

    System.out.println("Desenul tabelului:");

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n - i; j++) {
            if (!tabel[i][j].isEmpty()) {
                for (String productie : tabel[i][j]) {
                    System.out.println("Pentru subsirul [" + (j + 1) + ", " + (j + i + 1) + "], s-a folosit productia din S: " + productie);
                    afiseazaSubsiruriFormate(productie, cuvant);
                }
            }
        }
    }
}

private void afiseazaSubsiruriFormate(String productie, String cuvant) {
    if (productie.startsWith("S(") && productie.endsWith(")")) {
        String interior = productie.substring(2, productie.length() - 1);
        String[] subsiruri = interior.split(",");

        System.out.print("Subsirurile din cuvantul original sunt: ");
        for (String subsir : subsiruri) {
            subsir = subsir.trim();
            int index = cuvant.indexOf(subsir);
            while (index != -1) {
                System.out.print("'" + subsir + "' ");
                cuvant = cuvant.substring(index + 1);
                index = cuvant.indexOf(subsir);
            }
        }
        System.out.println();
    }
}

    private Set<String>[][] tabel(String cuvant) {
        List<Productie> productii = mC.daProductii();
        int n = cuvant.length();
        Set<String>[][] V = new HashSet[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                V[i][j] = new HashSet<>();
            }
        }   
    for (int j = 0; j < n; j++) {
        char currentChar = cuvant.charAt(j);
        for (Productie prod : productii) {
            for (String rhs : prod.getDreapta()) {
                if (rhs.length() == 1 && rhs.charAt(0) == currentChar) {
                    V[0][j].add(prod.getStanga());
                }
            }
        }
    }

    
    for (int i = 1; i < n; i++) { // i este lungimea substringului
        for (int j = 0; j < n - i; j++) { // j este pozitia de inceput a substringului
            for (int k = 0; k < i; k++) { // k este pozitia diviziunii substringului
                for (Productie prod : productii) {
                    for (String rhs : prod.getDreapta()) {
                        if (rhs.length() == 2) {
                            String B = rhs.substring(0, 1);
                            String C = rhs.substring(1, 2);
                            if (V[k][j].contains(B) && V[i - k - 1][j + k + 1].contains(C)) {
                                V[i][j].add(prod.getStanga());
                                if (prod.getStanga().equals("S")) {
                                    V[i][j].add("S(" + B + C + ")");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    return V;

    }  

}
