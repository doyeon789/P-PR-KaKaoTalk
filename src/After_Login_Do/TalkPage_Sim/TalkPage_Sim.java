package After_Login_Do.TalkPage_Sim;

import Home_Do.Home_Page_Do;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static After_Login_Do.TalkPage_Sim.Chat_TalkSim.chat_cnt;
import static Home_Do.Home_Page_Do.isTalk_Sim_Open;

public class TalkPage_Sim {
    static JTextArea InputArea = new JTextArea("메시지 입력");
    static JPanel Talk_panel_me = new JPanel();
    static JLabel sending = new JLabel();

    public static Socket socket;
    public static BufferedWriter out;

    public static String MessageFromSim;

    public static int Mode = 1;

    static JFrame frame_Talk_me = new JFrame("");
    public static void TalkPage() throws IOException {
        Thread chatting = new Thread(TalkPage_Sim::Chatting);
        chatting.start();

        frame_Talk_me.setSize(379, 639);
        frame_Talk_me.setLocation(720,285);
        frame_Talk_me.setUndecorated(true);
        frame_Talk_me.setBackground(new Color(0,0,0,0));
        frame_Talk_me.setResizable(false);

        Talk_panel_me.setLayout(null);
        Talk_panel_me.setOpaque(false);
        Talk_panel_me.setBounds(0, 0, 379, 639);

        sending.setOpaque(false);
        sending.setBounds(313, 602, 61, 31);
        ImageIcon sendingI = new ImageIcon("image/After_Login_img/TalkPage/send.png");
        Image sending_img = sendingI.getImage();
        Image sending_logo = sending_img.getScaledInstance(61, 31, Image.SCALE_SMOOTH);
        sending.setIcon(new ImageIcon(sending_logo));
        sending.setVisible(false);

        InputArea.setForeground(new Color(139,139,139));
        InputArea.setOpaque(false);

        // InputArea.setBackground(Color.white);
        InputArea.setBounds(0,0,370,72);
        InputArea.setLineWrap(true);
        InputArea.setWrapStyleWord(true);
        Talk_panel_me.requestFocus();

        InputArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (InputArea.getForeground().equals(new Color(139,139,139))) {
                    InputArea.setText("");
                    InputArea.setForeground(Color.BLACK);
                }
                checkInputState();
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (InputArea.getText().isEmpty()) {
                    InputArea.setForeground(new Color(139, 139, 139));
                    InputArea.setText("메시지 입력");
                    sending.setVisible(false);
                }
                checkInputState();
            }
        });

        InputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkInputState();
            }
        });

        Talk_panel_me.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Talk_panel_me.requestFocus();
            }
        });


        Action sendAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (InputArea.getText().isEmpty() || (InputArea.getText().equals("메시지 입력") && InputArea.getForeground().equals(new Color(139,139,139)))) {
                    return;
                }
                String outputMessage = InputArea.getText();

                try {
                    out.write(outputMessage + "\n");
                    out.flush();
                } catch (IOException er) {
                    System.out.println("전송 중 오류 발생: " + er.getMessage());
                }
                Mode = 1;
                send();
            }
        };

        sending.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                if(sending.isVisible()) {
                    Talk_panel_me.repaint();
                    if (InputArea.getText().isEmpty()) {
                        return;
                    }
                    String outputMessage = InputArea.getText();

                    try {
                        out.write(outputMessage + "\n");
                        out.flush();
                    } catch (IOException er) {
                        System.out.println("전송 중 오류 발생: " + er.getMessage());
                    }
                    Mode = 1;
                    send();
                    sending.setVisible(false);
                }
            }
        });

        InputArea.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("shift ENTER"),"insert-newline");
        InputArea.getActionMap().put("insert-newline", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputArea.append(System.lineSeparator());
            }
        });

        InputArea.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("ENTER"),"send");
        InputArea.getActionMap().put("send",sendAction);

        JScrollPane scrollPane = new JScrollPane(InputArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scrollPane.setBounds(8,525,370,72);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        Talk_panel_me.add(sending,0);
        Talk_panel_me.add(scrollPane,0);

        Init_Workig(Talk_panel_me);
    }
    private static void Init_Workig(JPanel Talk_panel_me){
        JLabel Talk_Background_me = new JLabel();
        Talk_Background_me.setOpaque(false);
        Talk_Background_me.setBounds(0, 0, 379, 639);
        ImageIcon Talk_B_meI = new ImageIcon("image/After_Login_img/Do/TalkPage/TalkPage_Sim.png");
        Image Talk_B_me_img = Talk_B_meI.getImage();
        Image Talk_B_me_logo = Talk_B_me_img.getScaledInstance(379, 639, Image.SCALE_SMOOTH);
        Talk_Background_me.setIcon(new ImageIcon(Talk_B_me_logo));

        final Point[] mouseClickPoint = {null};

        JLabel Move = new JLabel();
        Move.setOpaque(false);
        Move.setBounds(65,0,332,40);
        Move.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                mouseClickPoint[0] = e.getPoint();
            }
        });
        Move.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                if(mouseClickPoint[0] != null){
                    Point current = frame_Talk_me.getLocation();
                    frame_Talk_me.setLocation(
                            current.x + e.getX() - mouseClickPoint[0].x,
                            current.y + e.getY() - mouseClickPoint[0].y
                    );
                }
            }
        });

        JLabel X_button = new JLabel();
        X_button.setOpaque(false);
        X_button.setBounds(5, 3, 56, 21);
        ImageIcon X_buttonI = new ImageIcon("image/After_Login_img/Do/COM/X.png");
        Image X_button_img = X_buttonI.getImage();
        Image X_button_logo = X_button_img.getScaledInstance(56, 21, Image.SCALE_SMOOTH);
        X_button.setIcon(new ImageIcon(X_button_logo));
        X_button.setVisible(false);

        JLabel X_button_Box = new JLabel();
        X_button_Box.setBounds(10,5,18,18);
        X_button_Box.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                X_button.setVisible(false);
                isTalk_Sim_Open = false;
                Home_Page_Do.setTalk_myself_Open(false);
                frame_Talk_me.dispose();
            }

            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                X_button.setVisible(true);
                Talk_panel_me.revalidate();
                Talk_panel_me.repaint();
                X_button_Box.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                X_button.setVisible(false);
                Talk_panel_me.revalidate();
                Talk_panel_me.repaint();
                X_button_Box.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });


        Talk_panel_me.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeWindow");
        Talk_panel_me.getActionMap().put("closeWindow", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                X_button.setVisible(false);
                isTalk_Sim_Open = false;
                Home_Page_Do.setTalk_myself_Open(false);
                frame_Talk_me.dispose();
            }
        });

        Talk_panel_me.add(X_button_Box);
        Talk_panel_me.add(X_button);

        Talk_panel_me.add(Move);
        Talk_panel_me.add(Talk_Background_me);

        frame_Talk_me.add(Talk_panel_me);
        frame_Talk_me.setVisible(true);
    }
    private static void send() {
        if (InputArea.getText().isEmpty()) {
            return;
        }
        String messageText = InputArea.getText();

        LocalTime currentTime = LocalTime.now(); // 현재 시간
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // 이전 메시지와 시간이 동일하면 시간 정보 삭제
        if (chat_cnt > 0 && isTimeEqual(formattedTime)) {
            // 시간이 같으면 중복된 시간은 출력하지 않음
            formattedTime = "";
        }

        Chat_TalkSim.Chat(messageText, Talk_panel_me, formattedTime);
        InputArea.setText(null);
    }

    // 입력 상태를 확인하는 메서드
    private static void checkInputState() {
        if (InputArea.getText().isEmpty() || (InputArea.getText().equals("메시지 입력") && InputArea.getForeground().equals(new Color(139,139,139)))) {
            sending.setVisible(false);
        } else {
            sending.setVisible(true);
        }
    }
    private static boolean isTimeEqual(String formattedTime) {
        Component[] components = Talk_panel_me.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component innerComp : panel.getComponents()) {
                    if (innerComp instanceof JLabel) {
                        JLabel label = (JLabel) innerComp;
                        // 시간을 표시하는 JLabel을 찾아서 비교
                        if (label.getText().equals(formattedTime)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private static void Chatting() {
        ServerSocket listener = null;
        try {
            listener = new ServerSocket(9999);
            System.out.println("연결을 기다리고 있습니다...");
            socket = listener.accept();
            System.out.println("연결되었습니다");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Thread receiveThread = new Thread(() -> {
                try {
                    String inputMessage;
                    while ((inputMessage = in.readLine()) != null) {
                        MessageFromSim = inputMessage;
                        Mode = 2;
                        send();
                    }
                } catch (IOException e) {
                    System.out.println("수신 중 오류 발생: " + e.getMessage());
                }
            });
            SendMessage();
            receiveThread.start();

            // receiveThread가 종료될 때까지 기다림
            receiveThread.join();
        } catch (IOException | InterruptedException e) {
            System.out.println("오류 발생: " + e.getMessage());
        } finally {
            try {
                if (socket != null && !socket.isClosed()) socket.close();
                if (listener != null && !listener.isClosed()) listener.close();
            } catch (IOException e) {
                System.out.println("리소스 해제 중 오류 발생: " + e.getMessage());
            }
        }
    }
    private static void SendMessage(){
        InputArea.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("ENTER"),"send");
        InputArea.getActionMap().put("send",sendAction1);

        Scanner scanner = new Scanner(System.in);
        Thread sendThread = new Thread(() -> {
            try {
                while (true) {
                    String outputMessage = scanner.nextLine();
                    out.write(outputMessage + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("전송 중 오류 발생: " + e.getMessage());
            }
        });
        sendThread.start();
    }
    static Action sendAction1= new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (InputArea.getText().isEmpty() || (InputArea.getText().equals("메시지 입력") && InputArea.getForeground().equals(new Color(139,139,139)))) {
                return;
            }
            String outputMessage = InputArea.getText();

            try {
                out.write(outputMessage + "\n");
                out.flush();
            } catch (IOException er) {
                System.out.println("전송 중 오류 발생: " + er.getMessage());
            }
            Mode = 1;
            send();
        }
    };
}