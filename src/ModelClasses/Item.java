package ModelClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import GameComponents.SharedVariables.ItemType;

@XmlRootElement(name="Item")
public class Item {
  
      @XmlElement(name="itemName")   
      public String itemName;     
      @XmlElement(name="itemType")   
      public ItemType itemType;
      @XmlElement(name="itemClass")   
      public String itemClass;     
      @XmlElement(name="itemLevel")   
      public int itemLevel;
      private int armorClass;
      private int strength;
      private int damagebonus;
      private int count;
      
      public Item(String itemName, ItemType itemType, String itemClass, int itemLevel){
          itemName = this.itemName;
          itemType = this.itemType;
          itemClass = this.itemClass;
          itemLevel = this.itemLevel;
      }
   public Item()
      {
    	  
      }

      public String getItemName()
      {
    	  return itemName;
      }
      
      public void values(){
    	  
    	  if(level<=4){
    		  count = 1;
    	  }
    	  else if(level<=8){
    		  count = 2;
    	  }
    	  else if (level<=12){
    		  count = 3;
    	  }
    	  else if (level<=16){
    		  count = 4;
    	  }
    	  else
    		  count = 5;
    	 
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
    		damagebonus = damagebonus + count;
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
