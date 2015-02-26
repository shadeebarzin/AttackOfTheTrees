import java.awt.*;
import java.awt.event.KeyEvent;
import static java.lang.Math.abs;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class EnemyBear extends Enemies{

    //Constructor
    public EnemyBear(double x, double y){
        super(x, y);
        ImageIcon i = new ImageIcon("../images/enemyImages/bear/bearSide.png");
        this.setStill(i.getImage());
        this.setHorizontalSize(60);
        this.setVerticalSize(115);
        
        this.setHp(25);
        this.setAttack(10);
        this.setSpeed(2);
        this.setAttackSpeed(75);
        this.setAttackRange(20);
        this.setJumpSpeed(-20);
        this.setAttackSpeedCount(19);
    }
    
    @Override
    public void Attack(Player p, Graphics g){
        if(Board.getState() == Board.STATE.GAME){
        //if attacking and in attack range
        if(this.isAttacking()){
            if(this.checkInRange(p)){
            //increment attack speed count
            this.setAttackSpeedCount(this.getAttackSpeedCount() + 1);
            //if attack speed count == the enemies attack speed
            if(this.getAttackSpeedCount() == this.getAttackSpeed()){
            //create a projectile towards the player
                RobotProjectile laser = new RobotProjectile(this.getXCoord()
                        , this.getYCoord(), this.getFacing(), g, 
                        findAngle(p.getPlayerPoint()));
                this.addProjectile(laser);
            //reset attack speed count
                this.setAttackSpeedCount(0);
            }
            }
        }
        }
    }
   
    @Override
    public void AI(Player p, Graphics g, ArrayList<Terrain>terrain){
        //If it can't move, Jump
         if(checkMove()){
                    this.setYVel(this.getJumpSpeed());
                    setYAcc(.5);
                    setInAir(true);
        }    
         //if not in air and is in range, attack
        if(this.checkInRange(p) && !isInAir()){
                this.setAttacking(true);
                this.Attack(p, g);
                this.attackAnimation(g);
            }
        //else, move toward player
            else{
               this.setAttacking(false);
                if((p.getXCoord() + p.getHorizontalSize()) < this.getXCoord()){
                    this.setXVel(-1 * (this.getSpeed()));
                    this.move(terrain);
                }else if((p.getXCoord()) > (this.getXCoord() + this.getHorizontalSize())){
                    this.setXVel(this.getSpeed());
                    this.move(terrain);
                }   
            }
        //adjust facing
            if(p.getXCoord() > getXCoord()){
                setFacing(1);
            }
            else if(p.getXCoord() < getXCoord()){
                setFacing(0);
            }   
        
    }
    @Override
    public void dropResource(Graphics g){
    //Do once items have been implemented
    }
    @Override
    public void die(){
    //not sure if needed, keep just in case
    }
    @Override
    public void paintEnemy(Player p, Graphics g){
        //paint enemy and it's projectiles
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.getStill(), (int) this.getXCoord(), (int) this.getYCoord(), null);


	g.setColor(Color.red);
	g.fillRect((int) (this.getXCoord() -10) , (int) (this.getYCoord() - 10), this.getHp(), 7);        
        paintProjectile(p, g);
        deleteProjectiles();
    }
    
    public void attackAnimation(Graphics g){
        
        
    }

    @Override
    public void print() {
        System.out.println("Bear");
    }

    
}

