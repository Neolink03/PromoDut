/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JFileChooser;


import vue.Observateur;


/**
 *
 * @author Effantin
 */
public class Promotion implements Observable{

    private ArrayList<Etudiant> list;
    private ArrayList<Observateur> listeObs;

    public Promotion() {
        list = new ArrayList<Etudiant>();
    }

    public void addEtudiant(Etudiant etu) {
        list.add(etu);
    }

    public void removeEtudiant(Etudiant etu) {
        list.remove(etu);
    }

    public ArrayList<Etudiant> getListeEtudiants() {
        return list;
    }

    public Etudiant searchEtudiant(String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().compareTo(id) == 0) {
                return list.get(i);
            }
        }
        return null;
    }

    public int[] seriesbacs(){
        int[] tab=new int[6];
        for (int i=0;i<tab.length;i++)
            tab[i]=0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSerieBac().compareToIgnoreCase("S")==0)
                tab[0]++;
            else if (list.get(i).getSerieBac().compareToIgnoreCase("ES")==0)
                tab[1]++;
            else if (list.get(i).getSerieBac().compareToIgnoreCase("STI")==0)
                tab[2]++;
            else if (list.get(i).getSerieBac().compareToIgnoreCase("STG")==0)
                tab[3]++;
            else if (list.get(i).getSerieBac().compareToIgnoreCase("Etranger")==0)
                tab[4]++;
            else if (list.get(i).getSerieBac().compareToIgnoreCase("Autre")==0)
                tab[5]++;
        }
        return tab;
    }

    public void loadFileCSV() {
        String ligne,idt,nom,prenom,dept,bac;
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                FileInputStream fichier_int = new FileInputStream(fileChooser.getSelectedFile());
                 InputStreamReader inputs = new InputStreamReader(fichier_int,"Latin1");
                BufferedReader input=new BufferedReader(inputs);
                 while ((ligne=input.readLine())!=null) {
                     String[] fields = ligne.split(";");
                     addEtudiant(new Etudiant(fields[0],fields[1],fields[2],fields[4],fields[3]));
                 }
                 System.out.println("Liste chargée");
                input.close();
            }
        } catch (Exception exception) {
            System.out.println("Probleme import csv");
        }
    }

	@Override
	public void addObservateur(Observateur obs) {
		
		if ( listeObs == null ) {
			listeObs = new ArrayList<Observateur>();
		}
		
		listeObs.add(obs);
		
	}

	@Override
	public void removeObservateur(Observateur obs) {
		
		
	}

	@Override
	public void notifyObservateurs() {

		for (int i = 0 ; i < listeObs.size() ; i++) {
			
			listeObs.get(i).update();
		}
	}
}
