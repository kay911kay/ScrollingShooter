import java.awt.*;

public class Stationary extends Enemy {
  boolean stop = false;
  int shootAt;
  
  public Stationary(int identity,int px,int py) {
    super(identity,px,py,50,50,2);
    shootAt = 50;
  }
  
  public void move() {
    if (!stop) {
      if (y <= 0)
        y+=5;
      else
        moveDown();
      if (y >= 200)
        stop = true;
    }
    else {
      shootAt++;
      if (shootAt >= 50) {
        Bullet bullet = new Bullet(0,x+width/2,y+height);
        ForgottenGiant.enemyBullets.add(bullet);
        Rectangle bRectangle = new Rectangle(bullet.getX(),bullet.getY(),bullet.getWidth(),bullet.getHeight());
        ForgottenGiant.ebr.add(bRectangle);
        shootAt = 0;
      }
    }
  }
}