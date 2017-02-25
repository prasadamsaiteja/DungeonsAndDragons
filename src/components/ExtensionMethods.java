package components;

import java.io.File;

public class ExtensionMethods {

      public static String[] getMapsList(){
        
        if(!new File(SharedVariables.MapsDirectory).exists())
          return new String[0];
        
        File[] fileList = new File(SharedVariables.MapsDirectory).listFiles();
        String[] fileName = new String[fileList.length];

        for(int i=0; i<fileList.length;i++) 
            if(fileList[i].getName().endsWith(".xml"))
              fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");
        
        return fileName;
      }
      
      public static String[] getItemsList(){
        
        if(!new File(SharedVariables.ItemsDirectory).exists())
          return new String[0];
        
        File[] fileList = new File(SharedVariables.ItemsDirectory).listFiles();
        String[] fileName = new String[fileList.length];

        for(int i=0; i<fileList.length;i++) 
            if(fileList[i].getName().endsWith(".xml"))
              fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");
        
        return fileName;        
      }
      
      public static String[] getCampaignsList(){
    	  File f=new File(SharedVariables.CampaignsDirectory);
    	  if(!f.exists()){
    		  return  new String[0];
    	  }
    	  File[] fileList = f.listFiles();
          String[] fileName = new String[fileList.length];
          for(int i=0; i<fileList.length;i++) {
        	  if(fileList[i].getName().endsWith(".xml"))
                  fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");
            
          }
    	  return fileName;
    }      
  

}