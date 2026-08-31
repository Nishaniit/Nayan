package Game1;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;
public class Effect
{
    private final double x;
    private final double y;
    private final double max_distance;
    private final int max_size;
    private final Color color;
    private final int totalEffect;
    private final float speed;
    private double current_distance;
    private ModelBoom booms[];
    private float alpha=1f;
    public void ExplosionSound()
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("4.wav"));
            Clip clip =AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public Effect(double x,double y,int totalEffect,int max_size,double max_distance,float speed,Color color)
    {
        this.x=x;
        this.y=y;
        this.totalEffect=totalEffect;
        this.max_size=max_size;
        this.max_distance=max_distance;
        this.speed=speed;
        this.color=color;
        createRandom();
        ExplosionSound();
    }
    private void createRandom()
    {
        booms=new ModelBoom[totalEffect];
        float per=360f/totalEffect;
        Random rand=new Random();
        for(int i=1;i<=totalEffect;i++)
        {
            int r=rand.nextInt((int)per)+1;
            int boomSize=rand.nextInt(max_size)+1;
            float angle=i*per+r;
            booms[i-1]=new ModelBoom(boomSize,angle);
        }
    }
    public void draw(Graphics2D g2)
    {
        AffineTransform oldTransform=g2.getTransform();
        Composite oldComposite=g2.getComposite();
        g2.setColor(color);
        g2.translate(x,y);
        for(ModelBoom mb:booms)
        {
            double bx=Math.cos(Math.toRadians(mb.getAngle()))*current_distance;
            double by=Math.sin(Math.toRadians(mb.getAngle()))*current_distance;
            double boomSize=mb.getSize();
            double space=boomSize/2;
            if(current_distance>=max_distance-(max_distance*0.7f))
            {
                alpha=(float)((max_distance-current_distance)/(max_distance*0.7f));
            }
            if(alpha>1)
            {
                alpha = 1;
            }
            else if(alpha<0)
            {
                alpha = 0;
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            g2.fill(new Rectangle2D.Double(bx-space,by-space,boomSize,boomSize));
        }
        g2.setComposite(oldComposite);
        g2.setTransform(oldTransform);
    }
    public void update()
    {
        current_distance+=speed;
    }
    public boolean check()
    {
        return current_distance<max_distance;
    }
}