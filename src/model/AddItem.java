package model;

import components.SharedVariables.ItemType;


public class AddItem {
  
	  
      private String itemName;
      private ItemType itemType; 
      private String itemClass;
      private int armorClass;
      private int level;
      private int strength;
      private int damagebonus;
      public int count;
      
      
      public AddItem(String itemName, ItemType itemType, String itemClass){
          itemName = this.itemName;
          itemType = this.itemType;
          itemClass = this.itemClass;
      }
      public String getItemName()
      {
    	  return itemName;
      }
      
      public void values(){
    	  
    	  if(level<=4){count = 1;}
	      
    	  else if(level<=8){count =2;}
    	 
    	  else if (level<=12){count=3;}
    	  
    	  else if (level<=16){count=4;}
    	  
    	  else
   	  count= 5; 
      }
 @SuppressWarnings("fallthrough") 
      public void calculateValues()
      {
	      values();
    	  switch(itemType)
    	  {
    	  case Belt :
    		  strength = strength + count;
    		  break;
		  case Weapon :
    		  break;
    	  case Armor :
    		  armorClass = armorClass + count;
    		  break;
    	  case Ring :
    		  armorClass = armorClass + count;
    		  break;
    	  case Shield :
    		  armorClass = armorClass + count;
    		  break;
    	  case Boots :
    		  armorClass = armorClass + count;
    		  break;
    	  case Helmet:
    		  armorClass = armorClass + count;
    	  }
      }
}
