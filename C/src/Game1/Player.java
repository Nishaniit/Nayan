package Game1;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;
public class Player
{
    private double hp = 100;
    private final double maxHp = 100;
    public Player()
    {
        this.img =new ImageIcon(Objects.requireNonNull(getClass().getResource("/Game1/jet-100-01.png"))).getImage();
        this.img_speed =new ImageIcon(Objects.requireNonNull(getClass().getResource("/Game1/jet-100-02.png"))).getImage();
    }
    public static final double player_size=100;
    private double x;
    private double y;
    private final float MAX_SPEED=1.5f;
    private float speed=0f;
    private float Angle=0f;
    private final Image img;
    private final Image img_speed;
    private boolean speedup;
    public void changeLocation(double x,double y)
    {
        this.x=x;
        this.y=y;
    }
    public void update()
    {
        x+=Math.cos(Math.toRadians(Angle))*speed;
        y+=Math.sin(Math.toRadians(Angle))*speed;
    }
    public void changeAngle(float angle)
    {
        if(angle<0)
        {
            angle = 359;
        }
        else if(angle>359)
        {
            angle = 0;
        }
        Angle=angle;
    }
    public void draw(Graphics2D g2)
    {
        AffineTransform at1= g2.getTransform();
        g2.translate(x,y);
        AffineTransform at2= new AffineTransform();
        at2.rotate(Math.toRadians(Angle+45),player_size/2,player_size/2);
        g2.drawImage(speedup? img_speed:img,at2,null);
        g2.setTransform(at1);
    }
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public double getAngle()
    {
        return Angle;
    }
    public void speedup()
    {
        speedup=true;
        if(speed>MAX_SPEED)
        {
            speed = MAX_SPEED;
        }
        else
        {
            speed += 0.01f;
        }
    }
    public void speeddown()
    {
        speedup=false;
        if(speed<=0)
        {
            speed = 0;
        }
        else
        {
            speed -= 0.003f;
        }
    }
    public double getHP() {
        return hp;
    }

    public void setHP(double hp) {
        this.hp = hp;
    }
    public void damage(double value) {
        hp -= value;
        if(hp < 0)
            hp = 0;
    }
}
