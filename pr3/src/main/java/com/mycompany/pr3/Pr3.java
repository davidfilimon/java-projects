package com.mycompany.pr3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class Pr3 {

    public static void main(String[] args) {
        Algoritm mA = new Algoritm("D:\\programare orientata pe obiecte\\pr3\\gramatica.txt");

        String cuvant = "baaba";

        boolean esteGramatical = mA.verificaCuvant(cuvant);

        Set<String>[][] tabel = mA.getTabel(cuvant);
        scrieDesenInFisier(tabel, cuvant.length(), "output.txt");
        afiseazaSubsiruriTotale(tabel, cuvant.length(), new StringBuilder());
        afiseazaSubsirurile(tabel, cuvant.length(), cuvant);
        if (esteGramatical) {
            System.out.println("Cuvantul '" + cuvant + "' apartine gramaticii.");

        } else {
            System.out.println("Cuvantul '" + cuvant + "' nu apartine gramaticii.");

        }
    }

    private static void scrieDesenInFisier(Set<String>[][] tabel, int n, String numeFisier) {
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
                    if (!tabel[i][j].isEmpty()) {
                        sb.append(tabel[i][j]).append("\t");
                    } else {
                        sb.append("0\t");
                    }
                }
                sb.append("\n");
            }

            afiseazaSubsiruriTotale(tabel, n, sb);
            writer.write(sb.toString());
            System.out.println("Desenul a fost scris in fisierul: " + numeFisier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void afiseazaSubsiruriTotale(Set<String>[][] tabel, int n, StringBuilder sb) {
        sb.append("\n\nSubsirurile:\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                afiseazaSubsiruri(tabel[i][j], sb, "baaba");
                sb.append("\n");
            }
        }
    }
private static void afiseazaSubsirurile(Set<String>[][] tabel, int n, String cuvant) {
    System.out.println("Subsirurile:");

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n - i; j++) {
            if (!tabel[i][j].isEmpty()) {
                afiseazaSubsiruri(tabel[i][j], new StringBuilder(), cuvant);
            }
        }
    }
}

    private static void afiseazaSubsiruri(Set<String> subsiruri, StringBuilder sb, String cuvant) {
        if (subsiruri.isEmpty()) {
            sb.append("0\t");
        } else {
            for (String subsir : subsiruri) {
                sb.append(subsir).append(" (").append(getSubsiruriFormate(subsir, cuvant)).append(")\t");
            }
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
}
