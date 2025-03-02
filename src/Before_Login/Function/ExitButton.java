package Before_Login.Function;

import Before_Login.LoginPage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ExitButton extends JLabel {
	public ExitButton(JFrame frame, boolean closingAll, float fontSize) {
		this.setToolTipText("닫기");
		this.setText("");
		
		Font currentFont = this.getFont();
		Font newFont = currentFont.deriveFont(fontSize);
		this.setFont(newFont);
		this.setForeground(Color.gray);
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				frame.setVisible(false);
				if(closingAll) {
					System.exit(0);
				}
			}
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				LoginPage.X_minus_ON.setVisible(true);
			}
			public void mouseExited(MouseEvent me) {
				LoginPage.X_minus_ON.setVisible(false);
			}
		});

	}
}
