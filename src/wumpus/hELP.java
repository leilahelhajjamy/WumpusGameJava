package wumpus;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class hELP extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void Help() {
		try {
			hELP dialog = new hELP();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public hELP() {
		setBounds(100, 100, 513, 353);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setToolTipText("\uF0A7\tF pour avancer");
		contentPanel.setBackground(new Color(255, 228, 181));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JTextPane txtpnFPour = new JTextPane();
			txtpnFPour.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 14));
			txtpnFPour.setBackground(new Color(255, 228, 181));
			txtpnFPour.setText("\uF0A7\tF pour avancer\r\n\uF0A7\tL pour tourner \u00E0 gauche\r\n\uF0A7\tR pour tourner \u00E0 droite\r\n\uF0A7\tS pour tirer la fl\u00E8che (Vous avez le droit de tirer deux fl\u00E8ches)\r\n\uF0A7\tG pour ramasser l\u2019or\r\n\uF0A7\tA pour jouer une nouvelle partie\r\n\uF0A7\tT pour grimper, \u00E0 partir de la premi\u00E8re case\r\n\uF0A7\tQ pour quitter\r\n\r\n\"+1000 pts si vous trouvez et ramassez l\u2019Or                     \r\n\"-1000 pts si vous tombez dans un Puit                               \r\n\"-1 pt pour chaque action ex\u00E9cut\u00E9\r\n\"-10 pts si vous tirez la fl\u00E8che, ATTENTION ! vous avez SEULEMENT deux fl\u00E8ches\r\n\t\t\t\t\t\t\r\n");
			contentPanel.add(txtpnFPour);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

}
