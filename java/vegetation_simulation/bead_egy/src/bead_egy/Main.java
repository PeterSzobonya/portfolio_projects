package bead_egy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adja meg a file eleresi utvonalat:");
        String filePath = scanner.nextLine();
        
        ReadAndProcess rap = new ReadAndProcess(filePath);
        
        try{
        rap.readFile();rap.process();}catch(Exception e){}
        
    }
    
    
    
}
