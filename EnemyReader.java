import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.*;

public class EnemyReader {
  static ArrayList <Enemy> enemies = new ArrayList <Enemy>();
  //the constructor fills the array songArray with -1 in every slot
  public EnemyReader() {
  }//end constructor
  
  //Reads the text file, counts how many numbers there are in the file, and then transfers those numbers into the 
  //songArray array
  public static void readFile() throws FileNotFoundException {
    Scanner fileInput = new Scanner (new FileReader ("enemies.txt"));
    while (fileInput.hasNextLine()) {
      String text = fileInput.nextLine();
      StringTokenizer tokens = new StringTokenizer(text);
      int enemyType = Integer.parseInt(tokens.nextToken());
      switch (enemyType) {
        case 0:
          Sprinter sprinter = new Sprinter(enemyType,Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()));
          enemies.add(sprinter);
          break;
        case 1:
          Shooter shooter = new Shooter(enemyType,Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()));
          enemies.add(shooter);
          break;
        case 2:
          Stationary stationary = new Stationary(enemyType,Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()));
          enemies.add(stationary);
          break;
        case 3:
          Boss boss = new Boss(enemyType,Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()));
          enemies.add(boss);
          break;
      }//end while loop
    }
    fileInput.close();
  }//end readSong
  
  //returns the next note to be sent down the screen
  public ArrayList<Enemy> getEnemies() {
    return enemies;
  }//end nextNote
}//end class