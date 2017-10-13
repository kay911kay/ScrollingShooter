public class Enemy extends AllPeople {
  int id;
  boolean goingDown = true,inMap = false;
  
  public Enemy(int identity,int px,int py,int teHeight,int teWidth,int teSpeed) {
    super(px,py,teHeight,teWidth,teSpeed);
    id = identity;
  }
  
  public void move() {
    if (!inMap) {
      moveDown();
      if (y > 0)
        inMap = true;
    }
    else {
      if (goingDown) {
        moveDown();
        if (y >= 800 - width)
          goingDown = false;
      }
      else {
        moveUp();
        if (y <= 0) 
          goingDown = true;
      }
    }
  }
  
  public int getID() {
    return id;
  }
  
  public boolean getInMap() {
    return inMap;
  }
}