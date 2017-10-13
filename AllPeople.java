/* Class: AllPeople
 * Last Updated: 1/12/2015
 * Author: Simon Gyorkos and Daniel Wang
 * Description: The super class
 *              Most classes inherit from this
 *              Has very general and default methods
 */

public class AllPeople {
  // variables required by every object
  int x,y,width,height,speed;
  
  // constructor method
  // This class requires all the variables to be declared in the constructor by the other classes
  // param: x position, y position, object width, object height, speed of movement
  public AllPeople(int px,int py,int teWidth,int teHeight,int aSpeed) {
    x = px;
    y = py;
    width = teWidth;
    height = teHeight;
    speed = aSpeed;
  }// end constructor
  
  // getX method
  // returns the object's x position
  public int getX() {
    return x;
  }// end getX
  
  // getY method
  // returns the object's y position
  public int getY() {
    return y;
  }// end getY
  
  // setX method
  // changes x position based on a given value
  public void setX(int newX) {
    x = newX;
  }// end setX
  
  // setY method
  // changes y position based on a given value
  public void setY(int newY) {
    y = newY;
  }// end setY
  
  // getWidth method
  // returns the width of the object
  public int getWidth() {
    return width;
  }// end getWidth
  
  // getHeight method
  // returns the height of the object
  public int getHeight() {
    return height;
  }// end getHeight
  
  // moveLeft method
  // moves the x position of the object to the left based on the object's speed
  public void moveLeft() {
    x-=speed;
  }// end moveLeft
  
  // moveRight method
  // moves the x position of the object to the right based on the object's speed
  public void moveRight() {
    x+=speed;
  }// end moveRight
  
  // moveUp method
  // changes the y position of the object up based on the object's speed
  public void moveUp() {
    y-=speed;
  }// end moveUp
  
  // moveDown method
  // changes the y position of the object down based on the object's speed
  public void moveDown() {
    y+=speed;
  }// end moveDown
}//end class