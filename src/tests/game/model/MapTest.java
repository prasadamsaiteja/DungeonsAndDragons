package tests.game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import game.components.SharedVariables;
import game.model.Map;
import game.model.jaxb.MapJaxb;

/**
 * This class is used to test map model class
 * @author saiteja prasadam
 * @version 1.0.0
 * @since 2/28/2017
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MapTest {

      private static Map mapObject;
  
      /**
       * This static method initalises map object variable to perform test operations
       */
      @BeforeClass
      public static void initVariables(){
          mapObject = new Map("MontrealTest", 20, 20, null);
      }
      
      /**
       * This method test map name
       */
      @Test
      public void test1MapName(){
          assertEquals(mapObject.getMapName(), "MontrealTest");
      }
      
      /**
       * This method test map width
       */
      @Test
      public void test2MapWidth(){
          assertEquals(mapObject.mapWidth, 20);
      }
      
      /**
       * This method test map height
       */
      @Test
      public void test3MapHeight(){
          assertEquals(mapObject.mapHeight, 20);
      }
  
      /**
       * This method test map saving
       */
      @Test
      public void test4MapSaving(){
          MapJaxb.convertMapObjectToXml(mapObject);
          assertTrue(new File(SharedVariables.MapsDirectory + File.separator + mapObject.getMapName()).exists());
      }

      /**
       * This method test map retreving
       */
      @Test
      public void test5MapReterving(){
          assertNotNull(MapJaxb.getMapFromXml(mapObject.getMapName()));
      }
      
      /**
       * This method test map deletion
       */
      @Test
      public void test6MapDelete(){
          MapJaxb.deleteMapXml(mapObject.getMapName());
          assertTrue(!new File(SharedVariables.MapsDirectory + File.separator + mapObject.getMapName()).exists());
      }
}