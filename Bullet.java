/* Class: Bullet
 * Last Updated: 1/12/2015
 * Author: Simon Gyorkos and Daniel Wang
 * Description: Holds variables for every bullet
 *              Inherits the AllPeople class
 */

public class Bullet extends AllPeople {
  // thatWay holds the direction of the bullet
  int thatWay;
  
  // constructor method
  // sends given values to the AllPeople super class
  public Bullet(int direction,int px,int py) {
    super (px,py,9,32,20);
    thatWay = direction;
  }// end constructor
  
  // move method
  // if thatWay is 0, go down
  // otherwise, go up
  public void move() {
    if (thatWay == 0)
      moveDown();
    else
      moveUp();
  }// end move method
}// end class