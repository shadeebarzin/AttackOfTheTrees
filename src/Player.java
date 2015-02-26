
import java.awt.*;
import java.awt.event.KeyEvent;
import static java.lang.Math.abs;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Player{
    //private data
    private double XCoord;
    private double XVel;
    private double YCoord;
    private double YVel;
    private double XAcc;
    private double YAcc;
    private double scrollX;
    private double scrollY;
    private int hp;
    private int attack;
    private Image still;
    private boolean InAir = false;
    private boolean attacking = false;
    private int facing = 1;
    private int AttackSpeed;
    private int AttackSpeedCount = 19;
    //player size
    private int HorizontalSize = 50;
    private int VerticalSize = 115;
    //world dimensions
    private int WorldBot = 700;
    private int WorldLeft = 0;
    private int WorldRight = 7478;
    private int WorldTop = 0;
    private double Speed;
    private double JumpSpeed;
    private ArrayList<Weapon> weapon = new ArrayList<Weapon>(); 
    private Weapon currentWeapon;
    private Point MousePoint;
    private int WeaponTracker = 0;
    
    //Constructor
    public Player() {
	ImageIcon i = new ImageIcon("../images/playerImages/guy/guySideDown.png"); //character image
	setStill(i.getImage());
        //starting Player postion/velocity/acceleration
	XCoord = 10;
	YCoord = WorldBot - VerticalSize;
        YAcc =  .5;
        YVel = 0;
        XVel = 0;
        hp = 100;
        attack = 10;
        AttackSpeed = 20;
        Speed = 4;
        JumpSpeed = -15;
    }
    
    public void move(ArrayList <Terrain> terrain) {
        //add velocities to positions/add gravity to yVel
	setXCoord(getXCoord() + getXVel());
	setYVel(getYVel() + getYAcc());
	setYCoord(getYCoord() + getYVel());
        //check terrain contact
        for(Terrain t : terrain){
            t.CheckPlayerContact(this);
            
        }
        
	//check orld boundaries
	if((getYCoord() + VerticalSize) >= 700){
	    setYCoord(getWorldBot() - VerticalSize);
	    setYVel(0);
	    setInAir(false);
	}
        if(getXCoord() <= 0){
            setXCoord(0);
        }
        else if((getXCoord() + 50) >= 7000){
            setXCoord(7000 - 50);
        }
        
        setScrollX(getXCoord()*(-1)); //For background
	setScrollY(getYCoord()); //For background

        
    }
    
    public void paintPlayer(Graphics g){
        //paint player and health bar
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.getStill(), (int) (this.getXCoord()), (int) (this.getYCoord()), null);

        if(this.getHp() >= 0){
            g.setColor(Color.white);
            g.fillRect((int) (this.getXCoord() -10) , (int) (this.getYCoord() - 10), this.getHp(), 7);
        }
    }
    
public void AttackAnimation(Graphics g){
//change player image depending on attack and facing
if(isAttacking()){
    if(getFacing() == 0){

        ImageIcon i = new ImageIcon("../images/playerImages/guy/guySideUpLeft.png"); //character image
        setStill(i.getImage());


    }
    else if(getFacing() == 1){
        ImageIcon i = new ImageIcon("../images/playerImages/guy/guySideUpRight.png");
        setStill(i.getImage());

    }

}else{
    if(getFacing() == 0){
        ImageIcon i = new ImageIcon("../images/playerImages/guy/guySideDownLeft.png");
        setStill(i.getImage());
    }else{
        ImageIcon i = new ImageIcon("../images/playerImages/guy/guySideDownRight.png");
        setStill(i.getImage());
    }
}
}
    
//subtract Hp by enemy attack
    public void takeDmg(Enemies e){
        setHp(getHp() - e.getAttack());
    }
//add weapon    
    public void AddWeapon(Weapon w){
        weapon.add(w);
        currentWeapon = w;
    }
//switch current weapon
    public void switchWeapon(){
        if(WeaponTracker < weapon.size()){
            WeaponTracker++;
            currentWeapon = weapon.get(WeaponTracker);
        }
        else if(WeaponTracker == weapon.size()){
            WeaponTracker = 0;
            currentWeapon = weapon.get(WeaponTracker);
        }
    }
//get mouse coords
// if attacking, shoot weapon
//readjust mouse coords
    public void PlayerAttack(Graphics g){
        Board.MouseCoords.x += (this.getXCoord() - 300);
        setMousePoint(Board.MouseCoords);
        if(isAttacking()){
            getCurrentWeapon().shoot(this.getMousePoint(), g);
        }
        Board.MouseCoords.x -= (this.getXCoord() - 300);
    }
//get player coords in point form
    public Point getPlayerPoint(){
        Point p = new Point((int)this.getXCoord(), (int)this.getYCoord());
        return p;
    }
    
    //player movement input
    public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
        //input cahnges velocity
	if (key == KeyEvent.VK_A){
            if(!isAttacking()){
                ImageIcon iLeft = new ImageIcon("../images/playerImages/guy/guySideDownLeft.png"); // character image
                setStill(iLeft.getImage());
            }
	    setXVel(-1 * (this.getSpeed()));
            setFacing(0);
        }
	if (key == KeyEvent.VK_D){
            if(!isAttacking()){
                ImageIcon iRight = new ImageIcon("../images/playerImages/guy/guySideDown.png"); // character image
                setStill(iRight.getImage());
            }
	    setXVel(getSpeed());
            setFacing(1);
        }
        //if on ground, can jump
        if(!isInAir()){
            if(key == KeyEvent.VK_W){
                setYAcc(.5);
                setYVel((getJumpSpeed()));
                setInAir(true);    
            }
        }
    }
    
    //player movement input
    public void keyReleased(KeyEvent e) {
	int key = e.getKeyCode();
        //realease of L/R key's result in 0 horiz vel
	if (key == KeyEvent.VK_A)
	    setXVel(0);
	
	if (key == KeyEvent.VK_D)
	    setXVel(0);
    }

    
    
    //-----------------Getters/Setters------------------------------------------------------------------------------------------------
    public double getXCoord() {
        return XCoord;
    }
    
    public void setXCoord(double XCoord) {
        this.XCoord = XCoord;
    }
    
    public double getXVel() {
        return XVel;
    }
    
    public void setXVel(double XVel) {
        this.XVel = XVel;
    }

    public double getYCoord() {
        return YCoord;
    }
    
    public void setYCoord(double YCoord) {
        this.YCoord = YCoord;
    }
    
    public double getYVel() {
        return YVel;
    }

    public void setYVel(double YVel) {
        this.YVel = YVel;
    }

    public double getXAcc() {
        return XAcc;
    }

    public void setXAcc(double XAcc) {
        this.XAcc = XAcc;
    }

    public double getYAcc() {
        return YAcc;
    }

    public void setYAcc(double YAcc) {
        this.YAcc = YAcc;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Image getStill() {
        return still;
    }

    public void setStill(Image still) {
        this.still = still;
    }

    public boolean isInAir() {
        return InAir;
    }

    public void setInAir(boolean InAir) {
        this.InAir = InAir;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public int getAttackSpeed() {
        return AttackSpeed;
    }

    public void setAttackSpeed(int AttackSpeed) {
        this.AttackSpeed = AttackSpeed;
    }

    public int getHorizontalSize() {
        return HorizontalSize;
    }

    public void setHorizontalSize(int HorizontalSize) {
        this.HorizontalSize = HorizontalSize;
    }

    public int getVerticalSize() {
        return VerticalSize;
    }

    public void setVerticalSize(int VerticalSize) {
        this.VerticalSize = VerticalSize;
    }

    /**
     * @return the WorldBot
     */
    public int getWorldBot() {
        return WorldBot;
    }

    /**
     * @param WorldBot the WorldBot to set
     */
    public void setWorldBot(int WorldBot) {
        this.WorldBot = WorldBot;
    }

    /**
     * @return the WorldLeft
     */
    public int getWorldLeft() {
        return WorldLeft;
    }

    /**
     * @param WorldLeft the WorldLeft to set
     */
    public void setWorldLeft(int WorldLeft) {
        this.WorldLeft = WorldLeft;
    }

    /**
     * @return the WorldRight
     */
    public int getWorldRight() {
        return WorldRight;
    }

    /**
     * @param WorldRight the WorldRight to set
     */
    public void setWorldRight(int WorldRight) {
        this.WorldRight = WorldRight;
    }
    
    /**
     * @return the WorldTop
     */
    public int getWorldTop() {
        return WorldTop;
    }

    /**
     * @param WorldTop the WorldTop to set
     */
    public void setWorldTop(int WorldTop) {
        this.WorldTop = WorldTop;
    }

    /**
     * @return the AttackSpeedCount
     */
    public int getAttackSpeedCount() {
        return AttackSpeedCount;
    }

    /**
     * @param AttackSpeedCount the AttackSpeedCount to set
     */
    public void setAttackSpeedCount(int AttackSpeedCount) {
        this.AttackSpeedCount = AttackSpeedCount;
    }

    /**
     * @return the speed
     */
    public double getSpeed() {
        return Speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.Speed = speed;
    }

    /**
     * @return the jumpspeed
     */
    public double getJumpSpeed() {
        return JumpSpeed;
    }

    /**
     * @param jumpspeed the jumpspeed to set
     */
    public void setJumpspeed(double jumpspeed) {
        this.JumpSpeed = jumpspeed;
    }

    /**
     * @return the weapon
     */
    public ArrayList<Weapon> getWeapon() {
        return weapon;
    }

    /**
     * @param weapon the weapon to set
     */
    public void setWeapon(ArrayList<Weapon> weapon) {
        this.weapon = weapon;
    }

    /**
     * @return the MousePoint
     */
    public Point getMousePoint() {
        return MousePoint;
    }

    /**
     * @param MousePoint the MousePoint to set
     */
    public void setMousePoint(Point MousePoint) {
        this.MousePoint = MousePoint;
    }

    /**
     * @return the currentWeapon
     */
    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    /**
     * @param currentWeapon the currentWeapon to set
     */
    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    /**
     * @return the WeaponTracker
     */
    public int getWeaponTracker() {
        return WeaponTracker;
    }

    /**
     * @param WeaponTracker the WeaponTracker to set
     */
    public void setWeaponTracker(int WeaponTracker) {
        this.WeaponTracker = WeaponTracker;
    }

    /**
     * @return the scrollX
     */
    public double getScrollX() {
        return scrollX;
    }

    /**
     * @param scrollX the scrollX to set
     */
    public void setScrollX(double scrollX) {
        this.scrollX = scrollX;
    }

    /**
     * @return the scrollY
     */
    public double getScrollY() {
        return scrollY;
    }

    /**
     * @param scrollY the scrollY to set
     */
    public void setScrollY(double scrollY) {
        this.scrollY = scrollY;
    }



}