package ModelClasses;

import GameComponents.SharedVariables.ItemType;

public class Item {
  
      private String itemName;
      private ItemType itemType; 
      private String itemClass;
      
      public Item(String itemName, ItemType itemType, String itemClass){
          itemName = this.itemName;
          itemType = this.itemType;
          itemClass = this.itemClass;
      }

}
