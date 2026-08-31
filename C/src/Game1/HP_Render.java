package Game1;
import java.awt.*;
import java.awt.geom.Rectangle2D;
public class HP_Render
{
    private final HP hp;
    public HP_Render(HP hp)
    {
        this.hp=hp;
    }
    protected void hpRender(Graphics2D g2,Shape shape,double y)
    {
        double hpY = shape.getBounds().getY() - y - 15;
        g2.setColor(new Color(70, 70, 70));
        g2.fill(new Rectangle2D.Double(0, hpY, Player.player_size - 40, 2));
        g2.setColor(new Color(253, 91, 91));
        double hpSize = hp.getCurrentHp() / hp.getMAX_HP() * Player.player_size - 40;
        g2.fill(new Rectangle2D.Double(0, hpY, hpSize, 10));
    }
    public boolean updateHP(double cutHP)
    {
        hp.setCurrentHp(hp.getCurrentHp()-cutHP);
        return hp.getCurrentHp()>0;
    }
    public double getHP()
    {
        return hp.getCurrentHp();
    }
    public void resetHP()
    {
        hp.setCurrentHp(hp.getMAX_HP());
    }
}