import edu.duke.*;
import java.io.*;

public class FindAllGenes {
    
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
       int currIndex = dna.indexOf(stopCodon, startIndex + 3);
       while (currIndex != -1) {
           int diff = currIndex - startIndex;
           if (diff % 3 == 0) {
               return currIndex;
           }
           else {
               currIndex = dna.indexOf(stopCodon, currIndex + 1);
           }
       }
       return dna.length();
    }
    
    public String findGene(String dna, int where) {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1) {
            return "";
        }
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        int minIndex = 0;
        if (taaIndex == -1 || (tgaIndex != -1 && taaIndex > tgaIndex)) {
            minIndex = tgaIndex;
        }
        else {
            minIndex = taaIndex;   
        }
        if (minIndex == -1 || (tagIndex != -1 && minIndex > tagIndex)) {
            minIndex = tagIndex;
        }
        if (minIndex == -1) {
            return "";
        }
        return dna.substring(startIndex, minIndex + 3);
    }
    
    public StorageResource getAllGenes(String dna) {
        StorageResource geneList = new StorageResource();
        int startIndex = 0;
        while (true) {
            String currGene = findGene(dna, startIndex);
            if (currGene.isEmpty()) {
                break;
            }
            geneList.add(currGene);
            startIndex = dna.indexOf(currGene, startIndex) + 
                         currGene.length(); 
        }
        return geneList;
    }
    
    public int getcNum(String g) {
        int cIndex = g.indexOf("C");
        int cNum = 0;
        while (cIndex != -1) {
            cNum += 1;
            cIndex = g.indexOf("C", cIndex + 1);
        } 
        return cNum;
    }
    public int getgNum(String g) {
        int gIndex = g.indexOf("G");
        int gNum = 0;
        while (gIndex != -1) {
            gNum += 1;
            gIndex = g.indexOf("G", gIndex + 1);
        } 
        return gNum;
    }    

    
    public void testOn() {
        FileResource fr = new FileResource();
        String dna = fr.asString();
        int times = 0;
        int ctgIndex = dna.indexOf("CTG");
        while (ctgIndex != -1) {
            times += 1;
            ctgIndex = dna.indexOf("CTG", ctgIndex + 3);
        }    
        
        int count = 0; 
        int longcount = 0;
        int cgcount = 0;
        int cgNum = 0;
        int maxlength = 0;
        StorageResource genes = getAllGenes(dna);
        
        for (String g: genes.data()) {
            System.out.println(g);
            count += 1;
            double ccNum = getcNum(g);
            double ggNum = getgNum(g);   
            double len = g.length();
            double cgratio = (ccNum + ggNum)/len;
            
            if (cgratio > 0.35) {
                cgNum += 1;
            }
            
            if (g.length() > 60) {
                longcount += 1;
            }
            
            while (g.length() > maxlength) {
                maxlength = g.length();
            }
           
        } 
        //How many genes are in this file?
        System.out.println("count = " + count);
        //How many genes in this file are longer than 60?
        System.out.println("longcount = " + longcount);
        //How many genes in this file have cgRatio greater than 0.35?
        System.out.println("cgnum = " + cgNum);
        //How many times does the codon CTG appear in this strand of DNA?
        System.out.println("CTG times = " + times);
        //What is the length of the longest gene in the collection of genes found in this file?
        System.out.println("maxlength = " + maxlength);
    }
    
    public static void main (String[] args) {
        gene pr = new gene();
        pr.testOn();
    }
}
