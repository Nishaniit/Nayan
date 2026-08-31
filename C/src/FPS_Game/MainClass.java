package FPS_Game;
import Game_Component.PanelGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainClass extends JFrame
{
    public void ExplosionSound()
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("3.wav"));
            Clip clip =AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public MainClass()
    {
        ExplosionSound();
        setTitle("FPS Game");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        PanelGame panel = new PanelGame();
        add(panel);
        addWindowListener(new WindowAdapter()
        {
            public void windowOpened(WindowEvent e)
            {
                panel.start();
            }
        });

    }
    public static void main(String[] args)
    {
        MainClass c = new MainClass();
        c.setVisible(true);
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}