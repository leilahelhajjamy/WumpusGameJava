package wumpus;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



import java.awt.Color;

public class Bienvenue extends JFrame {

	
	private static final long serialVersionUID = 1 ;
    private JPanel contentPane;
    private JTextField ordre;
    private JButton btnNewButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                   Bienvenue frame = new Bienvenue();
                    frame.setVisible(true);
                	frame.setSize(565, 465);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */

    public Bienvenue() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\Desktop\\STDM.jpg"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewUserRegister = new JLabel("Bienvenue , Entrer un ordre");
        lblNewUserRegister.setForeground(new Color(210, 105, 30));
        lblNewUserRegister.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewUserRegister.setBounds(161, 64, 325, 50);
        contentPane.add(lblNewUserRegister);

        JLabel lblName = new JLabel("");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblName.setBounds(58, 152, 99, 43);
        contentPane.add(lblName);

        ordre = new JTextField();
        ordre.setFont(new Font("Tahoma", Font.PLAIN, 32));
        ordre.setBounds(161, 157, 228, 50);
       
        ordre.setColumns(10);
        contentPane.add(ordre);
        btnNewButton = new JButton("Jouer");
        btnNewButton.setBackground(new Color(255, 222, 173));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ordreStr = ordre.getText().toString();
                 int i = Integer.parseInt(ordreStr);
                
               
                if (i>10 || i<4) {
                    JOptionPane.showMessageDialog(btnNewButton, "Entrer un nombre entre 4 et 10");
                }
                if (i>3 && i<11) {
                try {
                game.NewScreen(i);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnNewButton.setBounds(147, 253, 122, 74);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Help");
        btnNewButton_1.setBackground(new Color(152, 251, 152));
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnNewButton_1.setBounds(297, 253, 122, 74);
        btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				hELP.Help();
			}
		});

        contentPane.add(btnNewButton_1);
    }
}