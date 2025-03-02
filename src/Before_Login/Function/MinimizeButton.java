package Before_Login.Function;

import Before_Login.LoginPage;
import Before_Login.Main;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class MinimizeButton extends JLabel {
	public MinimizeButton() {

		this.setToolTipText("최소화");
		this.setText(" ");
		this.setForeground(Color.gray);
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Main.mainFrame.setState(Frame.ICONIFIED);
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
