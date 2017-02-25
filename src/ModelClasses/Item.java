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
      private int itemLevel;
	  private int armorClass;
      private int strength;
      private int constitution;
      private int dexterity;
      private int damagebonus;
      private int attackbonus;
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
    	  
    	  if(itemLevel<=4){
    		  count = 1;
    	  }
    	  else if(itemLevel<=8){
    		  count = 2;
    	  }
    	  else if (itemLevel<=12){
    		  count = 3;
    	  }
    	  else if (itemLevel<=16){
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
    		  if(itemClass.equals("Constitution")){
    		  constitution = constitution + count;
    		  break;}
    		  else
    			  strength = strength + count;
    		  break;
    	  case Weapon :
    		if(itemClass.equalsIgnoreCase("AttackBonus")){
    			attackbonus = attackbonus + count;
    			break;}
    		else
    			damagebonus = damagebonus + count;
    		  break;
    	  case Armor :
    		  armorClass = armorClass + count;
    		  break;
    	  case Ring :
    		  if(itemClass.equalsIgnoreCase("ArmorClass")){
    			  armorClass = armorClass + count;
    			  break;}
    		  else if(itemClass.equalsIgnoreCase("Constitution")){
    			  constitution = constitution + count;
    		  break;}
    		  else
    		  strength = strength + count;
    		  break;
    	  case Shield :
    		  armorClass = armorClass + count;
    		  break;
    	  case Boots :
    		  if(itemClass.equalsIgnoreCase("Dexterity")){
    			  dexterity = dexterity + count;
    			  break;}
    		  else 
    			  armorClass = armorClass + count;
    		  break;
    	  case Helmet:
    		  armorClass = armorClass + count;
    	  }
      }
 
   public int getArmorClass(){
	   return armorClass;
   }
   public int getDexterity(){
	   return dexterity;
   }
   public int getStrength(){
	   return strength;
   }
   public int getConstitution(){
	   return constitution;
   }
   public int getDamageBonus(){
	   return damagebonus;
   }
   public int getAttackBonus(){
	   return attackbonus;
   }
   
   
}