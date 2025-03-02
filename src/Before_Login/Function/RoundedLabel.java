package Before_Login.Function;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class RoundedLabel extends JLabel {
    private int radius; // 모서리 반경

    public RoundedLabel(String text, int radius) {
        super(text, SwingConstants.CENTER); // 텍스트와 정렬 설정
        this.radius = radius;
        setOpaque(false); // 기본 배경을 그리지 않도록 설정
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 둥근 배경 그리기
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // 텍스트와 이미지를 그리기 전에 클리핑 설정
        g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));

        super.paintComponent(g);
    }
}

