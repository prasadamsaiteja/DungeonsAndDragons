package ModelClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Item")
public class Item {
  
      @XmlElement(name="itemName")   
      public String itemName;     
      @XmlElement(name="itemType")   
      public String itemType;
      @XmlElement(name="itemClass")   
      public String itemClass;     
      @XmlElement(name="itemLevel")   
      public int itemLevel;
      
      public Item(String name, String type, String itemclass, int level){
          itemName = name;
          itemType = type;
          itemClass = itemclass;
          itemLevel = level;
      }

      /**
       * Instantiates a new map.
       */
      public Item(){}
}
