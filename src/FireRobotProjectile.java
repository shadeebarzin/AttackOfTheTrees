
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import javax.swing.ImageIcon;

/** 
 * FireRobotProjectile represents the projectile released by the FireRobot enemy
 * 
 * @author Shadee Barzin
 * @author Andrew Ferguson
 * @author Michele Haque
 * @author Brendan Murphy
 * @author Fengyu Wang
 * @version CS 48, Winter 2015
*/

public class FireRobotProjectile extends Projectile implements Serializable{
    //Constructor
    public FireRobotProjectile(double x, double y, int direction, Graphics g, double angle, Player p) {
        super(x + 5, y, direction, g, angle, p);
        ImageIcon iLeft = new ImageIcon("../images/sourceImage/fire.png");
	ImageIcon iRight = new ImageIcon ("../images/sourceImage/fire.png");
        setStillLeft(iLeft);
	setStillRight(iRight);
        setXAcc(0);
        setYAcc(0);
        setSpeed(5);
        setYVel(0);
        CreateImage(g);
        setHorizontalSize(28);
        setVerticalSize(43);
        setAttack(20);
        
                
        getXY(this.getSpeed(), angle);
    }
    //paints projectile
    public void CreateImage(Graphics g){
	Graphics2D g2d = (Graphics2D) g;
	if(getFacing() == 0){
	    g2d = (Graphics2D) g;
            g2d.drawImage(this.getStillLeft().getImage(), (int) (this.getXCoord()), (int) (this.getYCoord()), null);
	}
	else if(getFacing() == 1){
	    g2d = (Graphics2D) g;
            g2d.drawImage(this.getStillRight().getImage(), (int) (this.getXCoord() + 57), (int) (this.getYCoord() + 5), null);
	    
	}	
    }

}
