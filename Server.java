import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener {
    
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JScrollPane sp;
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Server() {
        
        f.setLayout(null);
        
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 400, 55);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon("img/3.png");
        Image i2 = i1.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 15, 20, 20);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                f.setVisible(false);
            }
        });
        
        ImageIcon i4 = new ImageIcon("img/G1.png");
        Image i5 = i4.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(30, 7, 40, 40);
        p1.add(profile);
        
        ImageIcon i7 = new ImageIcon("img/video.png");
        Image i8 = i7.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(250, 15, 25, 25);
        p1.add(video);
        
        video.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                JFrame videoFrame = new JFrame("Video Call");
                videoFrame.setSize(250, 150);
                videoFrame.setLocationRelativeTo(f);
                videoFrame.getContentPane().setBackground(Color.BLACK);

                JLabel label = new JLabel("Connecting Video Call...", SwingConstants.CENTER);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 14));

                videoFrame.add(label);
                videoFrame.setVisible(true);
            }
        });
        
        ImageIcon i10 = new ImageIcon("img/phone.png");
        Image i11 = i10.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(300, 15, 25, 25);
        p1.add(phone);
        
        phone.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                JOptionPane.showMessageDialog(f, "Calling Maisha...", "Phone Call", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        ImageIcon i13 = new ImageIcon("img/3icon.png");
        Image i14 = i13.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel more = new JLabel(i15);
        more.setBounds(350, 15, 10, 20);
        p1.add(more);
        
        more.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                JPopupMenu menu = new JPopupMenu();

                menu.add(new JMenuItem("Delete Chat"));
                menu.add(new JMenuItem("Mute Notifications"));
                menu.add(new JMenuItem("Block Contact"));

                menu.show(more, more.getWidth()/2, more.getHeight()/2);
            }
        });
        
        JLabel name = new JLabel("Maisha");
        name.setBounds(90, 10, 100, 15);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        p1.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(90, 28, 100, 15);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
        p1.add(status);
        
        a1 = new JPanel();
        a1.setLayout(new BoxLayout(a1, BoxLayout.Y_AXIS));
        sp = new JScrollPane(a1);
        sp.setBounds(5, 65, 390, 470);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setBorder(null);
        f.add(sp);
        
        text = new JTextField();
        text.setBounds(5, 540, 270, 35);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(280, 540, 110, 35);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        f.add(send);
        
        f.setSize(400, 600);
        f.setLocation(300, 100);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(10));

            a1.add(vertical, BorderLayout.PAGE_START);
            
            SwingUtilities.invokeLater(() -> {
                JScrollBar bar = sp.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
            });

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 14));
        output.setBackground(new Color(173, 241, 164));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10, 10, 10, 40));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String[] args) {
        new Server();
        
        try {
            ServerSocket skt = new ServerSocket(6001);
            while(true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    
                    SwingUtilities.invokeLater(() -> {
                        JScrollBar bar = sp.getVerticalScrollBar();
                        bar.setValue(bar.getMaximum());
                    });
                    
                    f.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}