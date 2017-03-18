package game.model.onclickListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import game.views.jpanels.GamePlayScreen;
import game.model.character.Character;

/**
 * This class is a mouse listener for map cell 
 * @author saiteja prasadam 
 * @since 3/9/2017
 * @version 1.0.0
 *
 */
public class MapClickListener implements MouseListener{
  
    private GamePlayScreen gameplayScreen;
    private Object object;
  
    /**
     * This is the class constructor which implements mouse onclick listener for map cells.
     * @param gameplayScreen gameplay screen reference to call showplayer details method.
     * @param object This is map cell object 
     */
    public MapClickListener(GamePlayScreen gameplayScreen, Object object){
        this.gameplayScreen = gameplayScreen;
        this.object = object;
    }

    /**
     * Mouse clicked method
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(object instanceof Character)
            gameplayScreen.showPlayerDetails(((Character) object).clone());
    }

    /**
     * Mouse entered method
     */
    @Override
    public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
  
    /**
     * Mouse exited method
     */
    @Override
    public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
  
    /**
     * Mouse pressed method
     */
    @Override
    public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
  
    /**
     * Mouse released method
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

}
