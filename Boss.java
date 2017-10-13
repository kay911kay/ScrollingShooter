/* Class: Boss
 * Last Updated: 1/12/2015
 * Author: Simon Gyorkos and Daniel Wang
 * Description: Final enemy of the game
 *              Hardest enemy
 *              Only enemy that has health
 *              Inherits the Enemy class
 */

import java.awt.*;

public class Boss extends Enemy {
  //Most of these variables determine when to charge or shoot
  int health, chargeAt, shootAt;
  boolean goingLeft = true,charging = false,backing = false;
  
  // constructor method
  // sends values to the Enemy super class
  public Boss(int identity,int px,int py) {
    super(identity,px,py,200,200,5);
    chargeAt = 50;
    shootAt = 50;
    health = 10;
  }//end constructor
  
  
  // move method (polymorphed)
  // moves the object into the map
  // when out of map, moves down
  // when in map, moves left and right
  // shoots bullets on an interval 
  // charges downward on a random interval
  public void move() {
    if (!inMap) {
      moveDown();
      if (y > 50)
        inMap = true;
    }// end if not in map
    else {
      if (charging) {
        if (!backing) {
          for (int i=0;i<3;i++)
            moveDown();
          if (y >= 800 - height - speed*3) 
            backing = true;
        }// end if not backing
        else {
          for (int i=0;i<3;i++){
            moveUp();
            if (y <= 50 + speed*3) { 
              charging = false;
              backing = false;
              chargeAt = 0;
            }// end if
          }// end for loop
        }// end else
      }// end if charging
      else if (chargeAt >= (Math.random()*1000)+1000) {
        charging = true;
      }// end charging check
      else {    
        chargeAt += speed;
        shootAt += speed;
        if (shootAt >= 500) {
          for (int i=0;i<10;i++) { 
            Bullet bullet = new Bullet(0,x+i*20,y+height);
            ForgottenGiant.enemyBullets.add(bullet);
            Rectangle bRectangle = new Rectangle(bullet.getX(),bullet.getY(),bullet.getWidth(),bullet.getHeight());
            ForgottenGiant.ebr.add(bRectangle);
          }// end for loop
          shootAt = 0;
        }// end if
        if (goingLeft) {
          moveLeft();
          if (x <= 0)
            goingLeft = false;
        }// end if goingLeft
        else {
          moveRight();
          if (x >= 600 - width) 
            goingLeft = true;
        }// end else
      }// end else
    }// end else
  }// end move
  
  // getHealth method
  // returns the boss's health
  public int getHealth() {
    return health;
  }// end getHealth
  
  // lowerHealth method
  // subtracts health by a certain amount
  public void lowerHealth(int dmg) {
    health -= dmg;
  }// end lowerHealth
}// end class