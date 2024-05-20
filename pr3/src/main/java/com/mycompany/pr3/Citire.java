/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pr3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author David
 */
public class Citire {
    private String caleFisier;
    private List<Productie> productii;

    Citire(String caleFisier) {
        this.caleFisier = caleFisier;
        productii = new ArrayList<>();
        citesteProductiiDinFisier();
    }

    private void citesteProductiiDinFisier() {
        try (Stream<String> linii = Files.lines(Paths.get(caleFisier))) {
            linii.forEach(linie -> {
                String[] parti = linie.split("->");
                String stanga = parti[0].trim();
                String[] dreapta = parti[1].split("\\|");

                for (String d : dreapta) {
                    productii.add(new Productie(stanga, new String[]{d.trim()}));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private static void afiseazaSubsiruri(Set<String> subsiruri, StringBuilder sb, String cuvant) {
    if (subsiruri.isEmpty()) {
        sb.append("0\t");
    } else {
        for (String subsir : subsiruri) {
            sb.append(subsir).append("\t");
            afiseazaSubsiruriMaiMici(subsir, cuvant);
        }
    }
}

private static void afiseazaSubsiruriMaiMici(String subsir, String cuvant) {
    System.out.println("   Subsirurile din cuvantul original sunt: ");
    int index = cuvant.indexOf(subsir);
    while (index != -1) {
        System.out.println("   " + cuvant.substring(index, index + subsir.length()));
        index = cuvant.indexOf(subsir, index + 1);
    }
}



private static String getSubsiruriFormate(String subsir, String cuvant) {
    StringBuilder sb = new StringBuilder();
    int index = cuvant.indexOf(subsir);
    
    while (index != -1) {
        sb.append(cuvant, 0, index).append(", ");
        cuvant = cuvant.substring(index + 1);
        index = cuvant.indexOf(subsir);
    }
    
    sb.append(cuvant);
    return sb.toString();
}


    public void scrieDesenInFisier(Set<String>[][] tabel, int n, String cuvant, String numeFisier) {
        numeFisier = "output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
            StringBuilder sb = new StringBuilder();

            sb.append("\t");
            for (int j = 0; j < n; j++) {
                sb.append((j + 1)).append("\t");
            }
            sb.append("\n");

            for (int i = 0; i < n; i++) {
                sb.append((i + 1)).append("\t");
                for (int j = 0; j < n - i; j++) {
                    afiseazaSubsiruri(tabel[i][j], sb, cuvant);
                }
                sb.append("\n");
            }

            writer.write(sb.toString());
            System.out.println("Desenul a fost scris in fisierul: " + numeFisier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  

    public List<Productie> daProductii() {
        return productii;
    }
}
