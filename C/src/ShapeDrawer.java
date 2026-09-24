import javax.swing.*;
import java.awt.*;
class ShapeDrawer extends JPanel
{
protected float hue = 0f;
protected Color nextColor()
        {
        hue += 0.003f;
        if (hue > 1f) hue = 0f;
        return Color.getHSBColor(hue, 1f, 3f);
        }
}
class TurtlePattern extends ShapeDrawer
{
public void paintComponent(Graphics g)
     {
     super.paintComponent(g);
     setBackground(Color.getHSBColor(hue, 1f, 3f));
     Graphics2D g2 = (Graphics2D) g;
     g2.setStroke(new BasicStroke(10));
     int x = getWidth()/2;
     int y = getHeight()/2;
     g2.translate(x, y);
     for(int i = 0; i < 200; i++)
        {
        g2.setColor(nextColor());
        g2.rotate(Math.toRadians(90));
        g2.drawRect(-i / 2, -i / 2, i, i);
        g2.drawArc(-i, -i, i * 2, i * 2, 0, 91);
        }
     }
}
class MainClass43
{
public static void main(String[] args)
     {
     JFrame frame = new JFrame("Turtle Pattern");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setSize(800, 800);
     frame.add(new TurtlePattern());
     frame.setVisible(true);
     }
}