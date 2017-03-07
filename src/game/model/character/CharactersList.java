package game.model.character;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import game.components.SharedVariables;

/**
 * Singleton class that loads all the available characters in a list
 * 
 * @author Supreet Singh (s_supree)
 *
 */
public class CharactersList extends Observable
{

    private String dirPath = SharedVariables.CharactersDirectory;
    private ArrayList<Character> characters;

    private static CharactersList inst = null;

    public CharactersList()
    {
        this.updateCharactersList();
    }

    public void updateCharactersList()
    {
        this.characters = this.getCharacters(this.dirPath);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Traverses recursively through characters directory and loads them
     * 
     * @param dPath set directory path from where to fetch list of all
     *        characters
     * @return an array list of Character class with all the characters found in
     *         the directory
     */
    private ArrayList<Character> getCharacters(String dPath)
    {

        ArrayList<Character> characters = new ArrayList<Character>();

        File dir = new File(dPath);
        File[] files = dir.listFiles();
        if (files != null)
        {
            for (File f : files)
            {
                if (f.isDirectory())
                {
                    characters.addAll(this.getCharacters(f.getPath()));
                }
                else
                {
                    try
                    {
                        String fileName = f.getName();
                        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                        if (extension.trim().equals("xml"))
                        {
                            BufferedReader r = new BufferedReader(new FileReader(f.getPath()));
                            String sCurrentLine, xml = "";
                            try
                            {
                                while ((sCurrentLine = r.readLine()) != null)
                                {
                                    xml += sCurrentLine;
                                }
                                XStream xstream = new XStream(new StaxDriver());
                                Character character = (Character) xstream.fromXML(xml);
                                characters.add(character);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return characters;
    }

    /**
     * @return an array list of Character class with all the characters found
     */
    public ArrayList<Character> getCharacters()
    {
        return this.characters;
    }

    /**
     * @return initialized CharactersList object
     */
    public static CharactersList init()
    {
        if (null == CharactersList.inst)
            CharactersList.inst = new CharactersList();
        return CharactersList.inst;
    }

    /**
     * @return an array list of Character class with all the characters found
     */
    public static ArrayList<Character> get()
    {
        CharactersList inst = CharactersList.init();
        return inst.getCharacters();
    }

    /**
     * @param characterName
     * @return Character object, if characterName is a valid character name, or
     *         null, if otherwise
     */
    public static Character getByName(String characterName)
    {
        ArrayList<Character> characters = get();
        Iterator<Character> characterIterator = characters.iterator();
        while (characterIterator.hasNext())
        {
            Character c = characterIterator.next();
            if (c.getName().equals(characterName))
                return c;
        }
        return null;
    }
    
    /**
     * Get all character names
     * 
     * @return array list containing character names
     */
    public static ArrayList<String> getNames()
    {
        ArrayList<String> characterNames = new ArrayList<String>();
        ArrayList<Character> characters = CharactersList.get();
        Iterator<Character> charactersList = characters.iterator();
        
        while (charactersList.hasNext())
        {
            Character c = charactersList.next();
            characterNames.add(c.getName());
        }
        
        return characterNames;
    }
    
    /**
     * Get character file name by character name
     * 
     * @param characterName character name
     * @throws Exception 
     */
    public static String getFileName(String characterName) throws Exception
    {
        Character c = CharactersList.getByName(characterName);
        
        if (c == null)
            throw new Exception("Character not found");
        
        return c.getFileName();        
    }
}
