package Game1;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
public class Bullet
{
    private double x,y;
    private final Shape shape;
    private final Color color=new Color(255, 255, 255);
    private final float angle;
    private double size;
    private float speed=1f;
    public Bullet(double x,double y,float angle,double size,float speed)
    {
        x += Player.player_size /2 - (size /2);
        y += Player.player_size /2 - (size /2);
        this.x=x;
        this.y=y;
        this.angle=angle;
        this.size=size;
        this.speed=speed;
        shape=new Ellipse2D.Double(0, 0, size+3, size+3);
    }
    public void update()
    {
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
    }
    public boolean check(int width,int height)
    {
        if(x<= -size || y< -size || x>width || y>height)
            return false;
        else
            return true;
    }
    public void draw(Graphics2D g2)
    {
        AffineTransform old=g2.getTransform();
        g2.setColor(color);
        g2.translate(x,y);
        g2.fill(shape);
        g2.setTransform(old);
    }
    public Shape getShape()
    {
        return new Area(new Ellipse2D.Double(x,y,size,size));
    }
    public double  getX()
    {
        return x;
    }
    public double  getY()
    {
        return y;
    }
    public double getSize()
    {
        return size;
    }
    public double getCenterX()
    {
        return x + size / 2 ;
    }
    public double getCenterY()
    {
        return y + size / 2;
    }
}