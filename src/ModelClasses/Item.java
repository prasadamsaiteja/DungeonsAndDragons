package ModelClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import GameComponents.SharedVariables.ItemType;

@XmlRootElement(name="Item")
/** This is an item class which is used to store the items currently used by the 
 * character while playing the game. It affects various classes of selected items
 * based on the level of the character.
 *  
 * @author Priyanka A
 * 
 * @version 1.0
 * @since 2/24/2017 
 */
public class Item {
  
	  @XmlElement(name="itemName")
      private String itemName;
	  @XmlElement(name="itemType")
      private ItemType itemType; 
	  @XmlElement(name="itemClass")
      private String itemClass;
	  @XmlElement(name="itemLevel")
      private int level;
	  private int armorClass;
      private int strength;
      private int damagebonus;
      private int count;
      
      
      public Item(String itemName, ItemType itemType, String itemClass){
          itemName = this.itemName;
          itemType = this.itemType;
          itemClass = this.itemClass;
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