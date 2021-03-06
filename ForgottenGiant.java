import java.awt.event.*;
import java.io.FileNotFoundException;
import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class ForgottenGiant extends JFrame implements KeyListener, Runnable {
  
  static final int WIDTH = 600;
  static final int HEIGHT = 800;
  int time,stopWatch,shootTimer;
  Thread runner;
 URL backMusic =  null;
  Image background,playerImg,sprinter,boss,blast,bulletUp,bulletDown;
  Graphics2D buffer;
  Image bufferImage;
  Timer timer;
  Character player = new Character();
  Rectangle hitBox = new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());
  Rectangle shotBox = new Rectangle(player.getX(),(player.getY()-200),player.getWidth(),200);
  ArrayList <Enemy> enemyArrayList;
  ArrayList <Rectangle> enemyRect = new ArrayList<Rectangle>();
  static EnemyReader enemySpawner = new EnemyReader();
  boolean canShoot = true,bossCanHit = true;
  public static ArrayList <Bullet> playerBullets = new ArrayList <Bullet>();
  public static ArrayList <Rectangle> pbr = new ArrayList<Rectangle>();
  public static ArrayList <Bullet> enemyBullets = new ArrayList <Bullet>();
  public static ArrayList <Rectangle> ebr = new ArrayList<Rectangle>();
  
  /* In the constructor:
   * displays the game name on the window
   * reads the list of enemies using the EnemyReader class
   * makes the runner a thread
   * with a given enemyArrayList, creates rectangles for each enemy
   * starts the runner
   * sets the size of the JFrame
   * imports the images
   * creates the timer
   */
  public ForgottenGiant() {
    super("Forgotten Giant");
    try {
      enemySpawner.readFile();
    }catch(FileNotFoundException fnfe){}
    enemyArrayList = enemySpawner.getEnemies();
    for (int i=0;i<enemyArrayList.size();i++) {
      Rectangle nextRectangle = new Rectangle(enemyArrayList.get(i).getX(),enemyArrayList.get(i).getY(),enemyArrayList.get(i).getWidth(),enemyArrayList.get(i).getHeight());
      enemyRect.add(nextRectangle);
    }
    runner = new Thread(this);
    runner.start();
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    background = Toolkit.getDefaultToolkit().getImage("wowback.jpg");
    playerImg = Toolkit.getDefaultToolkit().getImage("Combover.png");
    sprinter = Toolkit.getDefaultToolkit().getImage("FallingNote.png");
    boss = Toolkit.getDefaultToolkit().getImage("imgo1.png");
    bulletUp = Toolkit.getDefaultToolkit().getImage("bullet.gif");
    bulletDown = Toolkit.getDefaultToolkit().getImage("BulletDown.png");
    setVisible(true);
    enemySort();
    repaint();
    addKeyListener(this);
//    ActionListener actionListener = new ActionListener()
//         public void actionPerformed(ActionEvent actionEvent) {
//
//          System.out.println(actionEvent.getActionCommand());
//      }
//    };
    timer = new Timer(20, new ActionListener(){
      public void actionPerformed (ActionEvent evt) {
        time++;
        if (!bossCanHit) {
          stopWatch++;
          if (stopWatch >= 50)
            bossCanHit = true;
        }
        if (!canShoot) {
          shootTimer++;
          if (shootTimer >= 10)
            canShoot = true;
        }
        hitBox.setLocation(player.getX(),player.getY());
        for (int i=0;i<enemyArrayList.size();i++) {
          enemyArrayList.get(i).move();
          enemyRect.get(i).setLocation(enemyArrayList.get(i).getX(),enemyArrayList.get(i).getY());
          if (enemyRect.get(i).intersects(hitBox)) {
            if (enemyArrayList.get(i).getID() == 3) {
              if (bossCanHit) {
                player.lowerHealth(2);
                lose();
                bossCanHit = false;
                stopWatch = 0;
              }
            }
            else {
              player.lowerHealth(1);
              enemyArrayList.remove(i);
              enemyRect.remove(i);
              lose();
            }
          }
        }
        for (int bNum=0;bNum<playerBullets.size();bNum++) {
          playerBullets.get(bNum).move();
          if (playerBullets.get(bNum).getY() <= 0) {
            playerBullets.remove(bNum);
            pbr.remove(bNum);
            break;
          }
          for (int eNum=0;eNum<enemyArrayList.size();eNum++) {
            pbr.get(bNum).setLocation(playerBullets.get(bNum).getX(),playerBullets.get(bNum).getY());
            if (pbr.get(bNum).intersects(enemyRect.get(eNum))) {
              if (enemyArrayList.get(eNum).getID() == 3) {
                playerBullets.remove(bNum);
                pbr.remove(bNum);
                ((Boss)enemyArrayList.get(eNum)).lowerHealth(1);
                win();
                break;
              }
              else {
                playerBullets.remove(bNum);
                pbr.remove(bNum);
                enemyArrayList.remove(eNum);
                enemyRect.remove(eNum);
                break;
              }
            }
          }
        }
        for (int ebNum=0;ebNum<enemyBullets.size();ebNum++) {
          enemyBullets.get(ebNum).move();
          if (enemyBullets.get(ebNum).getY() >= 800) {
            enemyBullets.remove(ebNum);
            ebr.remove(ebNum);
            break;
          }
          ebr.get(ebNum).setLocation(enemyBullets.get(ebNum).getX(),enemyBullets.get(ebNum).getY());
          if (ebr.get(ebNum).intersects(hitBox)) {
            enemyBullets.remove(ebNum);
            ebr.remove(ebNum);
            player.lowerHealth(1);
            lose();
            break;
          }
        }
        repaint();
      }
    });
  }//end constructor
  
  //plays sounds based on which string is strumed
//  public void playSound() throws Exception {
//    
//    switch(soundNote){
//      case 0:
//        songFile = new URL("file:6th_String_E.wav");
//        break;
//      case 1:
//        songFile = new URL("file:5th_String_A.wav");
//        break;
//      case 2:
//        songFile = new URL("file:4th_String_D.wav");
//        break;
//      case 3:
//        songFile = new URL("file:3rd_String_G.wav");
//        break;
//      case 4:
//        songFile = new URL("file:2nd_String_B.wav");
//        break;
//      case 5:
//        songFile = new URL("file:1st_String_E.wav");
//        break;
//    }//end switch statement
//    Clip clip = AudioSystem.getClip();
//    // getAudioInputStream() also accepts a File or InputStream
//    AudioInputStream ais = AudioSystem.getAudioInputStream(songFile);
//    clip.open(ais);
//    clip.start();
//  }//end playSound
  
  public void backgroundMusic() throws Exception {
    //      AudioInputStream audioIn = AudioSystem.getAudioInputStream("1234.wav");
//Clip clip = AudioSystem.getClip();
//clip.open(audioIn);
//clip.start();
     backMusic = new URL("file:ThatRaw2.wav");
    Clip clip = AudioSystem.getClip();
    AudioInputStream ais = AudioSystem.getAudioInputStream(backMusic);
    clip.open(ais);
    clip.start();
  }//end backgroundMusic
  
  public void keyTyped(KeyEvent event){}
  
  //checks for keys pressed by the user, moves player accordingly
  
  public void keyPressed(KeyEvent event) {
    int key = event.getKeyCode();
   if( KeyEvent.VK_W.getActionCommand()  == spacebar)
        player.moveUp();
     //   break;
//      case KeyEvent.VK_A:
//        player.moveLeft();
//        break;
//      case KeyEvent.VK_S:
//        player.moveDown();
//        break;
//      case KeyEvent.VK_D:
//        player.moveRight();
//        break;
//      case KeyEvent.VK_SPACE: {
//        if (canShoot) { 
//          shoot();
//          canShoot = false;
//          shootTimer = 0;  
//        }
   
      //}
   /// }//end of switch for key
  }//end of keyPressed
  
  public void keyReleased(KeyEvent event) {}//end of keyReleased
  
  //creates a new bullet from the player's position
  public void shoot() {
    Bullet bullet = new Bullet(1,player.getX()+41,player.getY()-16);
    playerBullets.add(bullet);
    Rectangle bRectangle = new Rectangle(bullet.getX(),bullet.getY(),bullet.getWidth(),bullet.getHeight());
    pbr.add(bRectangle);
  }//end shoot
  
  public void lose() {
    if (player.getHealth() <= 0) {
      JOptionPane.showMessageDialog(null,"You Fail it!");
      System.exit(0);
    }
  }
  
  public void win() {
    if (((Boss)enemyArrayList.get(bossPosition())).getHealth() == 0) {
      JOptionPane.showMessageDialog(null,"You win it!");
      System.exit(0);
    }
  }
  
  // run() method: displays a message and then starts the timer and plays music
  public void run() {
    JOptionPane.showMessageDialog(null, "You are some guy that has a gun," +
                                     "\n Use the W to move up," + 
                                     "\n Use the A to move left," + 
                                     "\n Use the S to move down," + 
                                     "\n Use the D to move right," + 
                                     "\n spacebar to shoot," +
                                     "\n and aim for the enemies." +
                                     "\n Your goal is to kill the Forgotten Giant.");
    JOptionPane.showMessageDialog(null, "but most importantly...");
    JOptionPane.showMessageDialog(null, "...don't die.");
    JOptionPane.showMessageDialog(null, "Good Luck");
    timer.start();    
    try{
      backgroundMusic();
    }catch(Exception e) {}
  }//end of run method

  //draws all the GUI of the game
  public void paint(Graphics a){
    Graphics2D g = (Graphics2D)a;
    Dimension dim = getSize();
    bufferImage = createImage(dim.width, dim.height);
    if (bufferImage == null) 
      JOptionPane.showMessageDialog(null,"could not get any image with createImage");
    buffer = (Graphics2D)bufferImage.getGraphics();
    buffer.drawImage(background,0,0,this);
    buffer.drawImage(playerImg,player.getX(),player.getY(),player.getWidth(),player.getHeight(),this);
    for (int i=0;i<enemyArrayList.size();i++) {
      switch (enemyArrayList.get(i).getID()) {
        case 0:
          buffer.drawImage(sprinter,enemyArrayList.get(i).getX(),enemyArrayList.get(i).getY(),enemyArrayList.get(i).getWidth(),enemyArrayList.get(i).getHeight(),this);
          break;
        case 1:
          buffer.drawImage(sprinter,enemyArrayList.get(i).getX(),enemyArrayList.get(i).getY(),enemyArrayList.get(i).getWidth(),enemyArrayList.get(i).getHeight(),this);
          break;
        case 2:
          buffer.drawImage(sprinter,enemyArrayList.get(i).getX(),enemyArrayList.get(i).getY(),enemyArrayList.get(i).getWidth(),enemyArrayList.get(i).getHeight(),this);
          break;
        case 3:
          buffer.drawImage(boss,enemyArrayList.get(i).getX(),enemyArrayList.get(i).getY(),enemyArrayList.get(i).getWidth(),enemyArrayList.get(i).getHeight(),this);
          break;
      }
    }
    for (int i=0;i<playerBullets.size();i++) {
      buffer.drawImage(bulletUp,playerBullets.get(i).getX(),playerBullets.get(i).getY(),playerBullets.get(i).getWidth(),playerBullets.get(i).getHeight(),this);
    }
    for (int i=0;i<enemyBullets.size();i++) {
      buffer.drawImage(bulletDown,enemyBullets.get(i).getX(),enemyBullets.get(i).getY(),enemyBullets.get(i).getWidth(),enemyBullets.get(i).getHeight(),this);
    }
    buffer.drawString("Time: "+time/10,275,100);
    buffer.drawString("Health: "+player.getHealth(),55,50);
    if (enemyArrayList.get(enemyArrayList.size()-1).getInMap())
      buffer.drawString("Boss Health: "+((Boss)enemyArrayList.get(bossPosition())).getHealth(),enemyArrayList.get(bossPosition()).getX(),enemyArrayList.get(bossPosition()).getY());
    g.drawImage(bufferImage,0,0,this);
    repaint();
  }//end paint method
  
  public int bossPosition() {
    int bottom = 0,top = enemyArrayList.size(),bossID = 3,mid = 0;
    boolean found = false;
    while (bottom != top-1) {
      mid = (bottom + top)/2;
      if (enemyArrayList.get(mid).getID() < bossID) 
        bottom = mid;
      else if (enemyArrayList.get(mid).getID() > bossID) 
        top = mid;
      else {
        found = true;
        bottom = top - 1;
      }
    }
    return mid;
  }//end bossPosition
  
  public void enemySort() {
    Enemy temp;
    for (int i=0;i<enemyArrayList.size()-1;i++) {
      for(int j=0; j<enemyArrayList.size()-1 -i;j++) {
        if (enemyArrayList.get(j).getID() > enemyArrayList.get(j+1).getID())  {                       
          temp = enemyArrayList.get(j);
          enemyArrayList.set(j,enemyArrayList.get(j+1));
          enemyArrayList.set(j+1,temp);
        }
      }
    }
  }
  
  //starts the game
  public static void main(String[] args){
    new ForgottenGiant();
  }//end main
}//end of class