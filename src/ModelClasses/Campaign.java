package ModelClasses;

/**
 * Generic Class for Campaign
 * @author RahulReddy
 *
 */
public class Campaign {
	String campaignName;
	String playerName;
	int noOfLevels=0;
	/**
	 * Constructor for Campaign
	 * @param name
	 * @param player
	 * @param level
	 */
	public Campaign(String name,String player,int level) {
		// TODO Auto-generated constructor stub
		this.campaignName=name;
		this.playerName=player;
		this.noOfLevels=level;
	}
	
}
