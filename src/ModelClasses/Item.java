package ModelClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import GameComponents.SharedVariables.ItemType;

@XmlRootElement(name="Item")
public class Item {
  
      @XmlElement(name="itemName")   
      private String itemName;     
      @XmlElement(name="itemType")   
      private ItemType itemType;
      @XmlElement(name="itemClass")   
      private String itemClass;     
      @XmlElement(name="itemLevel")   
      private int itemLevel;
      
      public Item(String itemName, ItemType itemType, String itemClass, int itemLevel){
          itemName = this.itemName;
          itemType = this.itemType;
          itemClass = this.itemClass;
          itemLevel = this.itemLevel;
      }

}
