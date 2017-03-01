package tests.game.model.map;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import game.model.Map;

/**
 * This class is used to test map model class
 * @author saiteja prasadam
 * @version 1.0.0
 * @since 2/28/2017
 *
 */
public class MapTest {

      private static Map mapObject;
  
      /**
       * This static method initalises map object variable to perform test operations
       */
      @BeforeClass
      public static void initVariables(){
          mapObject = new Map("Montreal", 20, 20, null);
      }
      
      /**
       * This method test map name
       */
      @Test
      public void testMapName(){
          assertEquals(mapObject.getMapName(), "Montreal");
      }
      
      /**
       * This method test map width
       */
      @Test
      public void testMapWidth(){
          assertEquals(mapObject.mapWidth, 20);
      }
      
      /**
       * This method test map height
       */
      @Test
      public void testMapHeight(){
          assertEquals(mapObject.mapHeight, 20);
      }
  
}
