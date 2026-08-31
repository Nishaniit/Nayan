package FPS_Game;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Fight extends JFrame
{
    Container c;
    JLabel l1,l2;
    JButton b1;
    public void ExplosionSound()
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("5.wav"));
            Clip clip =AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Fight()
    {
        c=getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(0, 0, 0, 220));
        ImageIcon icon1=new ImageIcon(getClass().getResource("2.png"));
        l1=new JLabel(icon1);
        l1.setBounds(450,150,icon1.getIconWidth(),icon1.getIconHeight());
        c.add(l1);
        ImageIcon icon2=new ImageIcon(getClass().getResource("img_1.png"));
        l2=new JLabel(icon2);
        l2.setBounds(0,0,1190,700);
        c.add(l2);
        b1=new JButton();
        b1.setText("Start");
        b1.setBounds(530,360,150,70);
        b1.setFont(new Font("Times New Roman",Font.BOLD,40));
        b1.setForeground(Color.BLACK);
        b1.setBackground(new Color(183, 195, 184, 255));
        c.add(b1);
        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String s=b1.getText();
                if(s=="Start")
                {
                    dispose();
                    MainClass mainclass=new MainClass();
                    mainclass.setVisible(true);
                }
            }
        });
        ExplosionSound();
    }
    public static void main(String[] args)
    {
        Fight f1=new Fight();
        f1.setVisible(true);
        f1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f1.setBounds(180,60,1190,700);
    }
}