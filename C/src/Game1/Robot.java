package Game1;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
public class Robot extends HP_Render
{
    public static final double Robot_size=70;
    private double x,y;
    private final float speed=0.3f;
    private float angle=0;
    private final Image image;
    private final Area robotshape;
    private int fireDelay=0;
    public Robot()
    {
        super(new HP(20,20));
        this.image=new ImageIcon(getClass().getResource("/Game1/100-01.png")).getImage();
        Path2D p=new Path2D.Double();
        p.moveTo(Robot_size * .1, Robot_size * 0.5);
        p.lineTo(Robot_size * 0.25, Robot_size * 0.01);
        p.lineTo(Robot_size * 0.65, Robot_size * 0.01);
        p.lineTo(Robot_size * .9, Robot_size * 0.5);
        p.lineTo(Robot_size * 0.75, Robot_size * 1);
        p.lineTo(Robot_size * 0.35, Robot_size * 1);
        p.closePath();
        robotshape=new Area(p);
    }
    public void ChangeLocation(double x,double y)
    {
        this.x=x;
        this.y=y;
    }
    public void update()
    {
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
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
        this.angle=angle;
    }
    public void draw(Graphics2D g2)
    {
        AffineTransform at1= g2.getTransform();
        g2.translate(x,y);
        AffineTransform at2= new AffineTransform();
        at2.rotate(Math.toRadians(angle),Robot_size/2,Robot_size/2);
        g2.drawImage(image,0,0,(int)Robot_size,(int)Robot_size,null);
        Shape shape=getShape();
        hpRender(g2,shape,y);
        g2.setTransform(at1);
        g2.setColor(new Color(20, 20, 20, 131));
        g2.draw(shape);
        g2.draw(shape.getBounds2D());
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
        return angle;
    }
    public Area getShape()
    {
        AffineTransform af=new AffineTransform();
        af.translate(x,y);
        af.rotate(Math.toRadians(angle),Robot_size/2,Robot_size/2);
        return new Area(af.createTransformedShape(robotshape));
    }
    public boolean check(int width,int height)
    {
        Rectangle size=getShape().getBounds();
        if(x<=-size.getWidth() || y<-size.getHeight() || x>width || y>height)
            return false;
        else
            return true;
    }
    public int getFireDelay() {
        return fireDelay;
    }

    public void setFireDelay(int fireDelay) {
        this.fireDelay = fireDelay;
    }
}