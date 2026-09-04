import javax.swing.*;
import java.awt.*;
public class Numerical_Analysis_Calculator extends JFrame
{
private JComboBox<String> groupBox;
private JComboBox<String> methodBox;
private JTextArea inputArea;
private JTextArea outputArea;
private JLabel inputLabel;
private JLabel iterationLabel;
private JTextField iterationField;
public Numerical_Analysis_Calculator()
      {
      setTitle("Equations Solver Calculator");
      setSize(850, 650);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      createGUI();
      }
public static double f(double x)
      {
      return Math.pow(x, 2) - Math.toRadians(Math.sin(x)) + (2.303 * Math.log(x)) - 3;
      }
public static double df(double x)
      {
      return 2 * x - Math.toRadians(Math.cos(x)) + (1 / x);
      }
public static double g(double x)
      {
      return Math.sqrt(Math.toRadians(Math.sin(x)) - (2.303 * Math.log(x)) + 3);
      }
private void createGUI()
       {
       setLayout(new BorderLayout(10, 10));
       JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));
       topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
       topPanel.add(new JLabel("Select Group:"));
       groupBox = new JComboBox<>(new String[]{"Group A - Equation Solving","Group B - Linear Equations"});
       topPanel.add(groupBox);
       topPanel.add(new JLabel("Select Method:"));
       methodBox = new JComboBox<>();
       topPanel.add(methodBox);
       add(topPanel, BorderLayout.NORTH);
       JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
       centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
       JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
       inputLabel = new JLabel("Input:");
       inputPanel.add(inputLabel, BorderLayout.NORTH);
       inputArea = new JTextArea();
       inputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
       inputArea.setText("For Bisection / Regula Falsi / Secant:\n" + "Enter values separated by spaces.\n" + "Example: 1 2");
       inputPanel.add(new JScrollPane(inputArea),BorderLayout.CENTER);
       JPanel iterationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
       iterationLabel = new JLabel("Iterations:");
       iterationField = new JTextField("10", 8);
       iterationPanel.add(iterationLabel);
       iterationPanel.add(iterationField);
       inputPanel.add(iterationPanel, BorderLayout.SOUTH);
       centerPanel.add(inputPanel);
       JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
       outputPanel.add(new JLabel("Output / Results:"),BorderLayout.NORTH);
       outputArea = new JTextArea();
       outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
       outputArea.setEditable(false);
       outputPanel.add(new JScrollPane(outputArea),BorderLayout.CENTER);
       centerPanel.add(outputPanel);
       add(centerPanel, BorderLayout.CENTER);
       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
       JButton calculateButton = new JButton("Calculate");
       JButton clearButton = new JButton("Clear");
       JButton exitButton = new JButton("Exit");
       buttonPanel.add(calculateButton);
       buttonPanel.add(clearButton);
       buttonPanel.add(exitButton);
       add(buttonPanel, BorderLayout.SOUTH);
       updateMethods();
       groupBox.addActionListener(e -> updateMethods());
       methodBox.addActionListener(e -> updateInputHint());
       calculateButton.addActionListener(e -> calculate());
       clearButton.addActionListener(e ->
                  {
                  inputArea.setText("");
                  outputArea.setText("");
                  });
       exitButton.addActionListener(e -> System.exit(0));
       }
private void updateMethods()
       {
       methodBox.removeAllItems();
       if(groupBox.getSelectedIndex() == 0)
         {
         methodBox.addItem("1. Bisection Method");
         methodBox.addItem("2. Fixed Point Iteration");
         methodBox.addItem("3. Newton-Raphson Method");
         methodBox.addItem("4. Regula Falsi Method");
         methodBox.addItem("5. Secant Method");
         }
       else
          {
          methodBox.addItem("1. Naive Gaussian Elimination");
          methodBox.addItem("2. LU Decomposition");
          methodBox.addItem("3. Partial Pivoting");
          }
       updateInputHint();
       }
private void updateInputHint()
       {
       int group = groupBox.getSelectedIndex();
       int method = methodBox.getSelectedIndex();
       if(group == 0)
         {
         if(method == 0)
           {
           inputArea.setText("Enter a and b:\n" + "Example:\n" + "1 2");
           iterationLabel.setText("Iterations:");
           }
         else if(method == 1)
                {
                inputArea.setText("Enter initial guess x0:\n" + "Example:\n" + "2");
                iterationLabel.setText("Iterations:");
                }
         else if(method == 2)
                {
                inputArea.setText("Enter initial guess:\n" + "Example:\n" + "2");
                iterationLabel.setText("Iterations:");
                }
         else if(method == 3)
                {
                inputArea.setText("Enter a and b:\n" + "Example:\n" + "1 2"
                );
                iterationLabel.setText("Iterations:");
                }
         else if(method == 4)
                {
                inputArea.setText("Enter a and b:\n" + "Example:\n" + "1 2");
                iterationLabel.setText("Iterations:");
                }
         }
       else
          {
          inputArea.setText("First line: number of variables\n"+"Then enter matrix values.\n\n"+"Example for 2 variables:\n"+"2\n"+"2 3 8\n"+"4 5 14");
          iterationLabel.setText("Iterations:");
          iterationField.setText("0");
          }
       }
private void calculate()
       {
       outputArea.setText("");
       try
         {
         int group = groupBox.getSelectedIndex();
         int method = methodBox.getSelectedIndex();
         if(group == 0)
           {
           int iteration = Integer.parseInt(iterationField.getText().trim());
           if(iteration <= 0)
             {
             throw new Exception("Iterations must be greater than 0.");
             }
           switch(method)
                 {
                 case 0:
                        bisection();
                        break;
                 case 1:
                        fixedPoint(iteration);
                        break;
                 case 2:
                        newtonRaphson(iteration);
                        break;
                 case 3:
                        regulaFalsi(iteration);
                        break;
                 case 4:
                        secant(iteration);
                        break;
                 default:
                        throw new Exception("Invalid method.");
                 }
           }
         else
            {
            switch(method)
                  {
                  case 0:
                        gaussianElimination();
                        break;
                  case 1:
                        luDecomposition();
                        break;
                  case 2:
                        partialPivoting();
                        break;
                  default:
                         throw new Exception("Invalid method.");
                  }
            }
         }
       catch(NumberFormatException e)
            {
            JOptionPane.showMessageDialog(this,"Please enter valid numeric values.","Input Error",JOptionPane.ERROR_MESSAGE);
            }
       catch(Exception e)
            {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
       }
private void bisection()
       {
       String[] values = inputArea.getText().trim().split("\\s+");
       if(values.length < 2)
         {
         throw new IllegalArgumentException("Enter two values: a and b.");
         }
       double a = Double.parseDouble(values[0]);
       double b = Double.parseDouble(values[1]);
       int iteration = Integer.parseInt(iterationField.getText());
       if(f(a) * f(b) > 0)
         {
         throw new IllegalArgumentException("Invalid interval. f(a) and f(b) must have opposite signs.");
         }
       double c = 0;
       double oldC = 0;
       double error = 0;
       outputArea.append("BISECTION METHOD\n");
       outputArea.append("==============================\n\n");
       for(int i = 1; i <= iteration; i++)
          {
          oldC = c;
          c = (a + b) / 2;
          if(i > 1)
            {
            error = Math.abs((c - oldC) / c) * 100;
            }
          outputArea.append(String.format("Iteration %d: Root = %.6f,Error = %.4f%%\n",i,c,error));
          if(f(c) == 0)
            {
            break;
            }
          if(f(a) * f(c) < 0)
            {
            b = c;
            }
          else
             {
             a = c;
             }
          }
       outputArea.append("\nFinal Approximate Root = " + String.format("%.6f", c));
       outputArea.append("\nFinal Approximate Error = " + String.format("%.4f%%", error));
       }
private void fixedPoint(int iteration)
       {
       String[] values = inputArea.getText().trim().split("\\s+");
       double x0 = Double.parseDouble(values[0]);
       double x1 = x0;
       double error = 0;
       outputArea.append("FIXED POINT ITERATION\n");
       outputArea.append("==============================\n\n");
       for(int i = 1; i <= iteration; i++)
          {
          x1 = g(x0);
          error = Math.abs((x1 - x0) / x1) * 100;
          outputArea.append(String.format("Iteration %d: Root = %.6f, Error = %.4f%%\n",i,x1,error));
          x0 = x1;
          }
       outputArea.append("\nFinal Approximate Root = " + String.format("%.6f", x1));
       outputArea.append("\nApproximate Error = " + String.format("%.4f%%", error));
       }
private void newtonRaphson(int iteration)
       {
       String[] values = inputArea.getText().trim().split("\\s+");
       double x = Double.parseDouble(values[0]);
       double x1 = x;
       double error = 0;
       outputArea.append("NEWTON-RAPHSON METHOD\n");
       outputArea.append("==============================\n\n");
       for(int i = 1; i <= iteration; i++)
          {
          double denominator = df(x);
          if(denominator == 0)
            {
            throw new ArithmeticException("Derivative became zero.");
            }
          x1 = x - (f(x) / denominator);
          error = Math.abs((x1 - x) / x1) * 100;
          outputArea.append(String.format("Iteration %d: Root = %.6f, Error = %.4f%%\n",i,x1,error));
          x = x1;
          }
       outputArea.append("\nFinal Approximate Root = " + String.format("%.6f", x1));
       outputArea.append("\nFinal Approximate Error = " + String.format("%.4f%%", error));
       }
private void regulaFalsi(int iteration)
       {
       String[] values = inputArea.getText().trim().split("\\s+");
       double a = Double.parseDouble(values[0]);
       double b = Double.parseDouble(values[1]);
       if(f(a) * f(b) > 0)
         {
         throw new IllegalArgumentException("f(a) and f(b) must have opposite signs.");
         }
       double x = 0;
       double oldX = 0;
       double error = 0;
       outputArea.append("REGULA FALSI METHOD\n");
       outputArea.append("==============================\n\n");
       for(int i = 1; i <= iteration; i++)
          {
          oldX = x;
          x = (a * f(b) - b * f(a)) / (f(b) - f(a));
          if(i > 1)
            {
            error = Math.abs((x - oldX) / x) * 100;
            }
          outputArea.append(String.format("Iteration %d: Root = %.6f, Error = %.4f%%\n",i,x,error));
          if(f(x) == 0)
            break;
          if(f(a) * f(x) < 0)
            b = x;
          else
             a = x;
          }
       outputArea.append("\nFinal Approximate Root = " + String.format("%.6f", x));
       outputArea.append("\nFinal Approximate Error = " + String.format("%.4f%%", error));
       }
private void secant(int iteration)
       {
       String[] values = inputArea.getText().trim().split("\\s+");
       double a = Double.parseDouble(values[0]);
       double b = Double.parseDouble(values[1]);
       double c = 0;
       double error = 0;
       outputArea.append("SECANT METHOD\n");
       outputArea.append("==============================\n\n");
       for(int i = 1; i <= iteration; i++)
          {
          double denominator = f(b) - f(a);
          if(denominator == 0)
            {
            throw new ArithmeticException("Division by zero occurred.");
            }
          c = b - f(b) * (b - a) / (f(b) - f(a));
          error = Math.abs((c - b) / c) * 100;
          outputArea.append(String.format("Iteration %d: Root = %.6f, Error = %.4f%%\n",i,c,error));
          a = b;
          b = c;
          }
       outputArea.append("\nFinal Approximate Root = " + String.format("%.6f", c));
       outputArea.append("\nFinal Approximate Error = " + String.format("%.4f%%", error));
       }
private double[][] readAugmentedMatrix()
       {
       String[] values = inputArea.getText().trim().split("\\s+");
       int n = Integer.parseInt(values[0]);
       if(values.length < 1 + n * (n + 1))
         {
         throw new IllegalArgumentException("Not enough matrix values.");
         }
       double[][] matrix = new double[n][n + 1];
       int index = 1;
       for(int i = 0; i < n; i++)
          {
          for(int j = 0; j <= n; j++)
             {
             matrix[i][j] = Double.parseDouble(values[index++]);
             }
          }
       return matrix;
       }
private void gaussianElimination()
       {
       double[][] a = readAugmentedMatrix();
       int n = a.length;
       for(int k = 0; k < n - 1; k++)
          {
          if(Math.abs(a[k][k]) < 1e-12)
            {
            throw new ArithmeticException("Zero pivot encountered.");
            }
          for(int i = k + 1; i < n; i++)
             {
             double factor = a[i][k] / a[k][k];
             for(int j = k; j <= n; j++)
                {
                a[i][j] -= factor * a[k][j];
                }
             }
          }
       double[] x = new double[n];
       for(int i = n - 1; i >= 0; i--)
          {
          double sum = 0;
          for(int j = i + 1; j < n; j++)
             {
             sum += a[i][j] * x[j];
             }
          if(Math.abs(a[i][i]) < 1e-12)
            {
            throw new ArithmeticException("No unique solution.");
            }
          x[i] = (a[i][n] - sum) / a[i][i];
          }
       outputArea.append("NAIVE GAUSSIAN ELIMINATION\n");
       outputArea.append("==============================\n\n");
       for(int i = 0; i < n; i++)
          {
          outputArea.append(String.format("x%d = %.4f\n",i + 1,x[i]));
          }
       }
private void luDecomposition()
       {
       double[][] augmented = readAugmentedMatrix();
       int n = augmented.length;
       double[][] A = new double[n][n];
       double[] B = new double[n];
       for(int i = 0; i < n; i++)
          {
          for(int j = 0; j < n; j++)
             {
             A[i][j] = augmented[i][j];
             }
          B[i] = augmented[i][n];
          }
       double[][] L = new double[n][n];
       double[][] U = new double[n][n];
       for(int i = 0; i < n; i++)
          {
          for(int k = i; k < n; k++)
             {
             double sum = 0;
             for(int j = 0; j < i; j++)
                {
                sum += L[i][j] * U[j][k];
                }
             U[i][k] = A[i][k] - sum;
             }
          for(int k = i; k < n; k++)
             {
             if(i == k)
               L[i][i] = 1;
             else
                {
                double sum = 0;
                for(int j = 0; j < i; j++)
                   {
                   sum += L[k][j] * U[j][i];
                    }
                if(Math.abs(U[i][i]) < 1e-12)
                  {
                  throw new ArithmeticException("LU decomposition failed.");
                  }
                L[k][i] = (A[k][i] - sum) / U[i][i];
                }
             }
          }
       double[] Y = new double[n];
       for(int i = 0; i < n; i++)
          {
          double sum = 0;
          for(int j = 0; j < i; j++)
             {
             sum += L[i][j] * Y[j];
             }
          Y[i] = B[i] - sum;
          }
       double[] X = new double[n];
       for(int i = n - 1; i >= 0; i--)
          {
          double sum = 0;
          for(int j = i + 1; j < n; j++)
             {
             sum += U[i][j] * X[j];
             }
          if(Math.abs(U[i][i]) < 1e-12)
            {
            throw new ArithmeticException("No unique solution.");
            }
          X[i] = (Y[i] - sum) / U[i][i];
          }
       outputArea.append("LU DECOMPOSITION\n");
       outputArea.append("==============================\n\n");
       outputArea.append("L Matrix:\n");
       for(int i = 0; i < n; i++)
          {
          for(int j = 0; j < n; j++)
             {
             outputArea.append(String.format("%10.4f",L[i][j]));
             }
          outputArea.append("\n");
          }
       outputArea.append("\nU Matrix:\n");
       for(int i = 0; i < n; i++)
          {
          for(int j = 0; j < n; j++)
             {
             outputArea.append(String.format("%10.4f",U[i][j]));
             }
          outputArea.append("\n");
          }
       outputArea.append("\nSolutions:\n");
       for(int i = 0; i < n; i++)
          {
          outputArea.append(String.format("x%d = %.4f\n",i + 1,X[i])
          );
          }
       }
private void partialPivoting()
       {
       double[][] a = readAugmentedMatrix();
       int n = a.length;
       for(int k = 0; k < n - 1; k++)
          {
          int maxRow = k;
          for(int i = k + 1; i < n; i++)
             {
             if(Math.abs(a[i][k]) > Math.abs(a[maxRow][k]))
               maxRow = i;
             }
          if(Math.abs(a[maxRow][k]) < 1e-12)
            {
            throw new ArithmeticException("No unique solution.");
            }
          if(maxRow != k)
            {
            double[] temp = a[k];
            a[k] = a[maxRow];
            a[maxRow] = temp;
            }
          for(int i = k + 1; i < n; i++)
             {
             double factor = a[i][k] / a[k][k];
             for(int j = k; j <= n; j++)
                {
                a[i][j] -= factor * a[k][j];
                }
             }
          }
       double[] x = new double[n];
       for(int i = n - 1; i >= 0; i--)
          {
          double sum = 0;
          for(int j = i + 1; j < n; j++)
             {
             sum += a[i][j] * x[j];
             }
          if(Math.abs(a[i][i]) < 1e-12)
            {
            throw new ArithmeticException("No unique solution.");
            }
          x[i] = (a[i][n] - sum) / a[i][i];
          }
       outputArea.append("PARTIAL PIVOTING\n");
       outputArea.append("==============================\n\n");
       for(int i = 0; i < n; i++)
          {
          outputArea.append(String.format("x%d = %.4f\n",i + 1,x[i]));
          }
       }
public static void main(String[] args)
      {
      SwingUtilities.invokeLater(() ->
                    {
                    Numerical_Analysis_Calculator calculator = new Numerical_Analysis_Calculator();
                    calculator.setVisible(true);
                    });
      }
}