import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class Student_Management extends JFrame implements ActionListener
{
Container c;
JLabel l1,l2,l3,l4;
JButton b1,b2,b3,b4;
JTextField t1,t2,t3;
JTable table;
DefaultTableModel model;
JScrollPane sp;
String[] columns={"Student Name","Student ID","Student Age"};
String[] rows=new String[3];
JTableHeader header;
public Student_Management()
     {
     Management();
     }
public void Management()
     {
     c=getContentPane();
     c.setLayout(null);
     c.setBackground(Color.PINK);
     l1=new JLabel("Students Details");
     l1.setBounds(350,30,200,50);
     l1.setFont(new Font("Arial",Font.BOLD,25));
     c.add(l1);
     l2=new JLabel("Students Name:");
     l2.setFont(new Font("Arial",Font.BOLD,20));
     l2.setBounds(30,90,200,50);
     c.add(l2);
     t1=new JTextField();
     t1.setBounds(200,85,150,50);
     t1.setFont(new Font("Arial",Font.BOLD,20));
     c.add(t1);
     b1=new JButton("Add");
     b1.setBounds(370,85,150,50);
     b1.setFont(new Font("Arial",Font.BOLD,20));
     c.add(b1);

     l3=new JLabel("Students ID:");
     l3.setFont(new Font("Arial",Font.BOLD,20));
     l3.setBounds(30,140,200,50);
     c.add(l3);
     t2=new JTextField();
     t2.setBounds(200,140,150,50);
     t2.setFont(new Font("Arial",Font.BOLD,20));
     c.add(t2);
     b2=new JButton("Delete");
     b2.setBounds(370,140,150,50);
     b2.setFont(new Font("Arial",Font.BOLD,20));
     c.add(b2);

     l4=new JLabel("Students Age:");
     l4.setFont(new Font("Arial",Font.BOLD,20));
     l4.setBounds(30,190,200,50);
     c.add(l4);
     t3=new JTextField();
     t3.setBounds(200,195,150,50);
     t3.setFont(new Font("Arial",Font.BOLD,20));
     c.add(t3);
     b3=new JButton("Clear");
     b3.setBounds(370,195,150,50);
     b3.setFont(new Font("Arial",Font.BOLD,20));
     c.add(b3);

     b4=new JButton("Update");
     b4.setBounds(530,140,150,50);
     b4.setFont(new Font("Arial",Font.BOLD,20));
     c.add(b4);

     table=new JTable();
     model=new DefaultTableModel();
     model.setColumnIdentifiers(columns);
     table.setModel(model);
     table.setRowHeight(30);
     table.setFont(new Font("Arial",Font.BOLD,25));
     sp=new JScrollPane(table);
     sp.setBounds(50,280,885,350);
     c.add(sp);
     header=table.getTableHeader();
     header.setBackground(Color.GREEN);
     header.setFont(new Font("Arial",Font.BOLD,25));
     b1.addActionListener(this);
     b2.addActionListener(this);
     b3.addActionListener(this);
     b4.addActionListener(this);
     table.addMouseListener(new MouseAdapter()
          {
          public void mouseClicked(MouseEvent e)
                {
                int numofrow=table.getSelectedRow();
                String sn=model.getValueAt(numofrow,0).toString();
                String id=model.getValueAt(numofrow,1).toString();
                String age=model.getValueAt(numofrow,2).toString();
                t1.setText(sn);
                t2.setText(id);
                t3.setText(age);
                }
          });
     }
public void actionPerformed(ActionEvent e)
     {
     if(e.getSource()==b1)
       {
       rows[0]=t1.getText();
       rows[1]=t2.getText();
       rows[2]=t3.getText();
       model.addRow(rows);
       }
     else if(e.getSource()==b3)
       {
       t1.setText("");
       t2.setText("");
       t3.setText("");
       }
     else if(e.getSource()==b2)
           {
           int SelectedRow=table.getSelectedRow();
           if(SelectedRow!=-1)
             model.removeRow(SelectedRow);
           else
            JOptionPane.showMessageDialog(null,"Please create at least one Row.Then select this",
                    "Warning",JOptionPane.WARNING_MESSAGE);
           }
     else
         {
         int SelectedRow=table.getSelectedRow();
         String sn=t1.getText();
         String id=t2.getText();
         String age=t3.getText();
         model.setValueAt(sn,SelectedRow,0);
         model.setValueAt(id,SelectedRow,1);
         model.setValueAt(age,SelectedRow,2);
         }
     }
public static void main(String[] args)
     {
     Student_Management sm =new Student_Management();
     sm.setBounds(100,100,1000,700);
     sm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     sm.setVisible(true);
     sm.setTitle("Student Management");
     }
}