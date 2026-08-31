package Game_Component;

import FPS_Game.Fight;
import Game1.*;
import Game1.Robot;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PanelGame extends JComponent
{
    private Graphics2D g2;
    private BufferedImage image,background;
    private int width,height;
    private Thread thread;
    private boolean start=true;
    private Key key;
    private int ShotTime;
    private final int FPS=120;
    private final int TargetTime=1000000000/FPS;
    private Player player;
    private List<Bullet> bullets;
    private List<Robot> robots;
    private List<Robot1> robots1;
    private List<Effect>  boomEffects;
    private List<RobotBullet> robotBullets;
    private int robotFireDelay=0,score=0;
    public void start()
    {
        width=getWidth();
        height=getHeight();
        image=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        g2=image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread=new Thread(new Runnable()
        {
            public void run()
            {
                while(start)
                {
                    long startTime=System.nanoTime();
                    drawBackground();
                    drawGame();
                    render();
                    long time=System.nanoTime()-startTime;
                    if(time<TargetTime)
                    {
                        long sleep=(TargetTime-time) / 1000000;
                        sleep(sleep);
                    }
                }
            }
        });
        initComponents();
        keyBoard();
        initBullets();
        thread.start();
    }
    private void addRobot()
    {
        Random rand=new Random();
        int locationY=rand.nextInt(height-100)+25;
        Robot robot=new Robot();
        robot.ChangeLocation(0,locationY);
        robot.changeAngle(0);
        robots.add(robot);
    }
    private void addRobot1()
    {
        Random ran=new Random();
        int locationY=ran.nextInt(width-20)+60;
        Robot1 robot2=new Robot1();
        robot2.ChangeLocation(0, locationY);
        robot2.changeAngle(180);
        robots1.add(robot2);
        int locationX=ran.nextInt(height-50)+900;
        Robot1 robot1=new Robot1();
        robot1.ChangeLocation(locationX,locationY);
        robot1.changeAngle(180);
        robots1.add(robot1);
    }
    private void initComponents()
    {
        try
        {
            background=ImageIO.read(getClass().getResource("img_1.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        player=new Player();
        player.changeLocation(150,150);
        robots=new ArrayList<>();
        boomEffects=new ArrayList<>();
        robotBullets = new ArrayList<>();
        new Thread(new Runnable()
        {
            public void run()
            {
                while(start)
                {
                    addRobot();
                    sleep(1000);
                }
            }
        }).start();
        robots1=new ArrayList<>();
        boomEffects=new ArrayList<>();
        new Thread(new Runnable()
        {
            public void run()
            {
                while(start)
                {
                    addRobot1();
                    sleep(1000);
                }
            }
        }).start();
    }
    private void keyBoard()
    {
        key=new Key();
        requestFocus();
        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode()==KeyEvent.VK_A)
                    key.setKeyleft(true);
                else if(e.getKeyCode()==KeyEvent.VK_D)
                    key.setKeyright(true);
                else if(e.getKeyCode()==KeyEvent.VK_SPACE)
                    key.setKeyspace(true);
                else if(e.getKeyCode()==KeyEvent.VK_J)
                    key.setKey_j(true);
                else if(e.getKeyCode()==KeyEvent.VK_K)
                    key.setKey_k(true);
            }
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode()==KeyEvent.VK_A)
                    key.setKeyleft(false);
                else if(e.getKeyCode()==KeyEvent.VK_D)
                    key.setKeyright(false);
                else if(e.getKeyCode()==KeyEvent.VK_SPACE)
                    key.setKeyspace(false);
                else if(e.getKeyCode()==KeyEvent.VK_J)
                    key.setKey_j(false);
                else if(e.getKeyCode()==KeyEvent.VK_K)
                    key.setKey_k(false);
            }
        });
        new Thread(new Runnable()
        {
            public void run()
            {
                float s=0.5f;
                while(start)
                {
                    float Angle= (float) player.getAngle();
                    if(key.isKeyleft())
                        Angle-=s;
                    if(key.isKeyright())
                        Angle+=s;
                    if(key.isKey_j() || key.isKey_k())
                    {
                        if(ShotTime==0)
                        {
                            if(key.isKey_j())
                                bullets.add(0,new Bullet(player.getX(),player.getY(),(float) player.getAngle(),10,4f ));
                            else
                                bullets.add(0,new Bullet(player.getX(),player.getY(),(float) player.getAngle(),18,4f ));
                        }
                        ShotTime++;
                        if(ShotTime==15)
                            ShotTime=0;
                    }
                    else
                        ShotTime = 0;
                    if(key.isKeyspace())
                        player.speedup();
                    else
                        player.speeddown();
                    player.update();
                    player.changeAngle(Angle);
                    for(int i=0;i<robots.size();i++)
                    {
                        Robot robot=robots.get(i);
                        if(robot!=null)
                        {
                            robot.update();
                            double robotCenterX = (robot.getX() + Robot.Robot_size / 2)-20;
                            double robotCenterY = (robot.getY() + Robot.Robot_size / 2)-45;
                            double playerCenterX = player.getX() + Player.player_size / 2;
                            double playerCenterY = player.getY() + Player.player_size / 2;
                            double angleToPlayer = Math.toDegrees(Math.atan2(playerCenterY - robotCenterY,playerCenterX - robotCenterX));
                            if(angleToPlayer >= -5 && angleToPlayer <= 5)
                            {
                                if(robot.getFireDelay() == 0)
                                {
                                    robotBullets.add(new RobotBullet(robotCenterX,robotCenterY,0));
                                    robot.setFireDelay(50);
                                }
                                if(robot.getFireDelay() > 0)
                                {
                                    robot.setFireDelay(robot.getFireDelay() - 1);
                                }
                            }
                            if(!robot.check(width,height))
                                robots.remove(robot);
                        }
                    }
                    for(int i=0;i<robots1.size();i++)
                    {
                        Robot1 robot1=robots1.get(i);
                        if(robot1!=null)
                        {
                            robot1.update();
                            double robotCenterX = (robot1.getX() + Robot1.Robot_size / 2)-25;
                            double robotCenterY = (robot1.getY() + Robot1.Robot_size / 2)-45;
                            double playerCenterX = player.getX() + Player.player_size / 2;
                            double playerCenterY = player.getY() + Player.player_size / 2;
                            double angleToPlayer = Math.toDegrees(Math.atan2(playerCenterY - robotCenterY,playerCenterX - robotCenterX));
                            if(angleToPlayer < 0)
                            {
                                angleToPlayer += 360;
                            }
                            if(Math.abs(angleToPlayer - 180)<=2)
                            {
                                if(robotFireDelay == 0)
                                {
                                    robotBullets.add(new RobotBullet(robotCenterX,robotCenterY,180));
                                    robotFireDelay = 50;
                                }
                            }
                            if(!robot1.check(width,height))
                                robots1.remove(robot1);
                        }
                    }
                    if(robotFireDelay > 0)
                        robotFireDelay--;
                    sleep(5);
                }
            }
        }).start();
    }
    private void initBullets()
    {
        bullets=new ArrayList<>();
        new Thread(new Runnable()
        {
            public void run()
            {
                while(start)
                {
                    for(int i=0;i<bullets.size();i++)
                    {
                        Bullet bullet=bullets.get(i);
                        if(bullet!=null)
                        {
                            bullet.update();
                            checkBullets(bullet);
                            if(!bullet.check(width,height))
                            {
                                bullets.remove(bullet);
                            }
                        }
                        else
                            bullets.remove(bullet);
                    }
                    for(int i = 0; i < robotBullets.size(); i++)
                    {
                        RobotBullet bullet = robotBullets.get(i);
                        if(bullet != null)
                        {
                            bullet.update();
                            checkRobotBullet(bullet);
                            if(!bullet.check(width, height))
                            {
                                robotBullets.remove(i);
                                i--;
                            }

                        }
                        else
                        {
                            robotBullets.remove(i);
                            i--;
                        }
                    }
                    for(int i=0;i<boomEffects.size();i++)
                    {
                        Effect boomEffect = boomEffects.get(i);
                        if(boomEffect != null)
                        {
                            boomEffect.update();
                            if(!boomEffect.check())
                            {
                                boomEffects.remove(i);
                                i--;
                            }
                        }
                    }
                    sleep(1);
                }
            }
        }).start();
    }
    private void checkBullets(Bullet bullet)
    {
        for(int i=0;i<robots.size();i++)
        {
            Robot robot=robots.get(i);
            if(robot!=null)
            {
                Area area=new Area(bullet.getShape());
                area.intersect(robot.getShape());
                if(!area.isEmpty())
                {
                    boomEffects.add(new Effect(bullet.getCenterX(),bullet.getCenterY(),3,5,60,0.5f,new Color(230,207,105)));
                    if(!robot.updateHP(bullet.getSize()))
                    {
                        score++;
                        robots.remove(robot);
                        double x=robot.getX()+Robot.Robot_size/2;
                        double y=robot.getY()+Robot.Robot_size/2;
                        boomEffects.add(new Effect(x,y,5,5,20,0.05f,new Color(32,178,169)));
                        boomEffects.add(new Effect(x,y,10,5,70,0.4f,new Color(32, 61, 178)));
                        boomEffects.add(new Effect(x,y,5,5,40,0.3f,new Color(230,207,105)));
                        boomEffects.add(new Effect(x,y,10,5,70,0.7f,new Color(62, 178, 30)));
                        boomEffects.add(new Effect(x,y,5,5,100,0.2f,new Color(244, 114, 214, 255)));
                    }
                    bullets.remove(bullet);
                }
            }
        }
        for(int i=0;i<robots1.size();i++)
        {
            Robot1 robot1=robots1.get(i);
            if(robot1!=null)
            {
                Area area=new Area(bullet.getShape());
                area.intersect(robot1.getShape());
                if(!area.isEmpty())
                {
                    score++;
                    boomEffects.add(new Effect(bullet.getCenterX(),bullet.getCenterY(),3,5,60,0.5f,new Color(230,207,105)));
                    if(!robot1.updateHP(bullet.getSize()))
                    {
                        robots1.remove(robot1);
                        double x=robot1.getX()+Robot1.Robot_size/2;
                        double y=robot1.getY()+Robot1.Robot_size/2;
                        boomEffects.add(new Effect(x,y,5,5,20,0.05f,new Color(105, 230, 186)));
                        boomEffects.add(new Effect(x,y,10,5,70,0.4f,new Color(62, 178, 30)));
                        boomEffects.add(new Effect(x,y,5,5,40,0.3f,new Color(244, 114, 214, 255)));
                        boomEffects.add(new Effect(x,y,10,5,70,0.7f,new Color(255, 255, 255)));
                        boomEffects.add(new Effect(x,y,5,5,100,0.2f,new Color(255, 60, 0, 202)));
                    }
                    bullets.remove(bullet);
                }
            }
        }
    }
    private void checkRobotBullet(RobotBullet bullet)
    {
        Rectangle playerRect = new Rectangle((int)player.getX(),(int)player.getY(),(int)Player.player_size,(int)Player.player_size);
        Area area = new Area(playerRect);
        area.intersect(new Area(bullet.getShape()));
        if(!area.isEmpty())
        {
            player.setHP(player.getHP() - 1);
            robotBullets.remove(bullet);
            boomEffects.add(new Effect(bullet.getCenterX(),bullet.getCenterY(),5,5,60,0.5f, Color.RED));
            boomEffects.add(new Effect(bullet.getCenterX(),bullet.getCenterY(),5,5,60,0.5f, Color.RED));
        }
        if(player.getHP() <= 0)
        {
            gameOver();
        }
    }
    private void gameOver()
    {
        start = false;
        int choice = JOptionPane.showConfirmDialog(this,"Game Over!\nRestart Game?","Game Over",JOptionPane.YES_NO_OPTION);
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
        score=0;
        if(choice == JOptionPane.YES_OPTION)
        {
            Fight fight = new Fight();
            fight.setVisible(true);
            fight.setBounds(180,60,1190,700);
            fight.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        robots.clear();
        robots1.clear();
        bullets.clear();

    }
    private void drawBackground()
    {
        if(background != null)
            g2.drawImage(background, 0, 0, 2000, 700, null);
        else
        {
            g2.setColor(new Color(30,30,30));
            g2.fillRect(0,0,width,height);
        }
    }
    private void drawGame()
    {
        player.draw(g2);
        g2.setColor(Color.GRAY);
        g2.fillRect(20, 30, 200, 17);
        g2.setColor(new Color(244, 64, 64));
        g2.fillRect(20, 30, (int)(player.getHP() * 2 ), 17);
        g2.setColor(Color.WHITE);
        g2.drawRect(20, 30, 200, 17);
        g2.drawString("HP : " + (int)player.getHP(), 20, 20);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Score: " +score, 20, 75);
        for(int i=0;i<bullets.size();i++)
        {
            Bullet bullet=bullets.get(i);
            if(bullet!=null)
                bullet.draw(g2);
        }
        for(RobotBullet b : robotBullets)
        {
            b.draw(g2);
        }
        for(int i=0;i<robots.size();i++)
        {
            Robot robot=robots.get(i);
            if(robot!=null)
                robot.draw(g2);
        }
        for(int i=0;i<robots1.size();i++)
        {
            Robot1 robot1=robots1.get(i);
            if(robot1!=null)
                robot1.draw(g2);
        }
        for(int i=0;i<boomEffects.size();i++)
        {
            Effect boomEffect=boomEffects.get(i);
            if(boomEffect!=null)
                boomEffect.draw(g2);
        }
    }
    private void render()
    {
        Graphics g=getGraphics();
        g.drawImage(image,0,0,null);
        g.dispose();
    }
    private void sleep(float speed)
    {
        try
        {
            Thread.sleep((long) speed);
        }
        catch(InterruptedException e)
        {
            System.err.println(e);
        }
    }
}