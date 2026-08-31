import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Multiplication_Table extends JFrame
{
    Container c;
    ImageIcon icon;
    JLabel l1,l2;
    JTextField t;
    JButton b1,b2;
    JTextArea j;
    Multiplication_Table()
    {
        Show();
    }
    void Show()
    {
        c=getContentPane();
        c.setLayout(null);
        c.setBackground(Color.LIGHT_GRAY);
        icon=new ImageIcon(getClass().getResource("p1.png"));
        l1=new JLabel(icon);
        l1.setBounds(100,20,500,200);
        c.add(l1);
        l2=new JLabel("Enter a number:");
        l2.setFont(new Font("Times New Roman",Font.BOLD,33));
        l2.setBounds(100,220,300,80);
        c.add(l2);
        t=new JTextField();
        t.setFont(new Font("Times New Roman",Font.BOLD,25));
        t.setBounds(410,230,190,50);
        t.setHorizontalAlignment(JTextField.CENTER);
        c.add(t);
        b1=new JButton("Enter");
        b1.setBounds(100,290,240,50);
        b1.setBackground(Color.GREEN);
        b1.setFont(new Font("Times New Roman",Font.BOLD,25));
        c.add(b1);
        b2=new JButton("Clear");
        b2.setBounds(360,290,240,50);
        b2.setBackground(Color.GREEN);
        b2.setFont(new Font("Times New Roman",Font.BOLD,25));
        c.add(b2);
        j=new JTextArea();
        j.setFont(new Font("Times New Roman",Font.BOLD,25));
        j.setBounds(100,350,500,380);
        c.add(j);
        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String v=t.getText();
                if(v.isEmpty())
                    JOptionPane.showMessageDialog(null,"Please Enter a number!");
                else
                {
                    int num = Integer.parseInt(v);
                    for(int i = 1; i <= 10; i++)
                    {
                        int result = num * i;
                        String r = String.valueOf(result);
                        String n = String.valueOf(num);
                        String c = String.valueOf(i);
                        j.append(n + "*" + c + "= " + r + "\n");
                    }
                }
            }
        });
        b2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                j.setText("");
                t.setText("");
            }
        });
    }
    public static void main(String[] args)
    {
        Multiplication_Table obj=new Multiplication_Table();
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setTitle("Multiplication Table");
        obj.setBounds(50, 50, 800, 800);
    }
}

