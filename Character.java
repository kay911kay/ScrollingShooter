/* Class: Character
 * Last Updated: 1/12/2015
 * Author: Simon Gyorkos and Daniel Wang
 * Description: Object player controls
 *              Inherits the AllPeople class
 */

public class Character extends AllPeople {
  // health stores the player's health
  int health = 30;
  
  // constructor method
  // sends values to the AllPeople super class
  public Character() {
    super(275,620,50,60,10);
  }// end constructor
  
  // moveLeft method (polymorphed)
  // moves left unless object touches the boundries
  public void moveLeft() {
    if (x > 0 + speed + 4)
      x-=speed;
  }// end moveLeft
  
  // moveRight method (polymorphed)
  // moves right unless object touches the boundries
  public void moveRight() {
    if (x < 600 - speed - width - 4)
      x+=speed;
  }// end moveRight
  
  // moveUp method (polymorphed)
  // moves up unless object touches the boundries
  public void moveUp() {
    if (y > 0 + speed + 21)
      y-=speed;
  }// end moveUp
  
  // moveDown method (polymorphed)
  // moves down unless object touches the boundries
  public void moveDown() {
    if (y < 800 - speed - height - 5)
      y+=speed;
  }// end moveDown
  
  // getHealth method
  // returns the player's health
  public int getHealth() {
    return health;
  }// end getHealth
  
  // lowerHealth method
  // subtracts the player's health by a given amount
  public void lowerHealth(int dmg) {
    health -= dmg;
  }// end lowerHealth
}// end class