import java.awt.*;


public class Shooter extends Enemy {
  int shootAt;
  
  public Shooter(int identity,int px,int py) {
    super(identity,px,py,50,50,2);
    shootAt = 50 - speed;
  }
  
  public void move() {
    if (!inMap) {
      y+=5;
      if (y > 0)
        inMap = true;
    }
    else {
      shootAt += speed;
      if (goingDown) {
        moveDown();
        if (y >= 800 - width)
          goingDown = false;
        if (shootAt >= 200) {
          Bullet pew = new Bullet(0,x+width/2,y+height);
          ForgottenGiant.enemyBullets.add(pew);
          Rectangle pRectangle = new Rectangle(pew.getX(),pew.getY(),pew.getWidth(),pew.getHeight());
          ForgottenGiant.ebr.add(pRectangle);
          shootAt = 0;
        }
      }
      else {
        moveUp();
        if (y <= 0) 
          goingDown = true;
        if (shootAt >= 200) {
          Bullet bullet = new Bullet(0,x+width/2,y+height);
          ForgottenGiant.enemyBullets.add(bullet);
          Rectangle bRectangle = new Rectangle(bullet.getX(),bullet.getY(),bullet.getWidth(),bullet.getHeight());
          ForgottenGiant.ebr.add(bRectangle);
          shootAt = 0;
        }
      }
    }
  }
}