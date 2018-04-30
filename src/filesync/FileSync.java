/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesync;

import java.io.File;
import java.util.List;

/**
 *
 * @author Talat
 */
public class FileSync {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        if (args.length < 2){
            System.out.println("Need atleast 2 arguments (path1, path2) for comparison");
        }
        String path1 =  args[0];
        String path2 = args[1];
        
        File folder1 = new File(path1);
        File folder2 = new File(path2);
        
        File[] files1 = folder1.listFiles(); 
        File[] files2 = folder2.listFiles(); 
        
        System.out.println("folder1 count : "+files1.length);
        System.out.println("folder2 count : "+files2.length);
                
        for(int i=0; i<files1.length; i++){
            findMatchingDirectory(files1[i], files2);
        }
        
        for(int i=0; i<files2.length; i++){
            findMatchingDirectory(files2[i], files1);
        }
    }
    
    private static boolean findMatchingDirectory(File folder, File[] arrFolders){
        boolean perfectMatchFound = false ;       
        
        int folderSubDirCount = subDirectoryCount(folder);
        int folderFileCount = fileCount(folder);
        
        System.out.println("\nFINDING MATCH FOR: "+folder.getAbsolutePath()+"("+folderSubDirCount+","+folderFileCount+")");
        
        try {
        
            for(int i=0; i<arrFolders.length; i++){
                File comparedToFolder = arrFolders[i];

                if(folder.getName().equalsIgnoreCase(comparedToFolder.getName())){                
                    int comparedToSubDirCount = subDirectoryCount(comparedToFolder);
                    int comparedToFileCount = fileCount(comparedToFolder);

                    if(folderSubDirCount == comparedToSubDirCount){
                        if(folderFileCount == comparedToFileCount){
                            System.out.println("\tMATCH FOUND: "+comparedToFolder.getAbsolutePath()+"("+comparedToSubDirCount+","+comparedToFileCount+")");
                            return true;
                        }
                    }

                    if(!perfectMatchFound){
                        System.out.println("\tMISMATCH: "+folder.getName()+"("+folderSubDirCount+","+folderFileCount+") - "
                            +comparedToFolder.getName()+"("+comparedToSubDirCount+","+comparedToFileCount+")");
                        Thread.sleep(3000);
                    }
                }
            }
        } catch (Exception ex){
            System.out.println("Exception: "+ex.getMessage());
            ex.printStackTrace();
        }
        
        return perfectMatchFound;
    }
    
    private static int subDirectoryCount(File directory){
        File[] files = directory.listFiles();
        int dirCount = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                dirCount++;
            }
        }
        return dirCount;
    }
    
    private static int fileCount(File directory){
        File[] files = directory.listFiles();
        int fileCount = 0;
        for (File file : files) {
            if (file.isFile()) {
                fileCount++;
            }
        }
        return fileCount;
    }
    
    private static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
    }
}
