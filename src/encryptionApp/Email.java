package encryptionApp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Email extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel emailPanel;
	JButton sendEmail, cancelEmail;
	JLabel fromEmailLabel, fromPasswordLabel, toEmailLabel;
	JTextField fromEmail, toEmail;
	JPasswordField fromPassword;
	String encryptedMessage;
	
	private static String HOST = "smtp.gmail.com";
    private static String PORT = "465";
 
    private static String STARTTLS = "true";
    private static String AUTH = "true";
    private static String DEBUG = "true";
    private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static String SUBJECT = "README";
	
	public Email(String encryptedMessage) {
		emailPanel = new JPanel(new FlowLayout());
		sendEmail = new JButton("Send");
		sendEmail.addActionListener(this);
		cancelEmail = new JButton("Cancel");
		cancelEmail.addActionListener(this);
		fromEmailLabel = new JLabel("Enter your Gmail:                          ");
		fromPasswordLabel = new JLabel("Enter your password:                  ");
		toEmailLabel = new JLabel("Who would you like to send to? ");
		fromEmail = new JTextField(10);
		fromPassword = new JPasswordField(10);
		toEmail = new JTextField(10);
		
		this.encryptedMessage = encryptedMessage;
		
		emailPanel.add(fromEmailLabel);
		emailPanel.add(fromEmail);
		emailPanel.add(fromPasswordLabel);
		emailPanel.add(fromPassword);
		emailPanel.add(toEmailLabel);
		emailPanel.add(toEmail);
		emailPanel.add(sendEmail);
		emailPanel.add(cancelEmail);
		
		add(emailPanel);
		setTitle("Email");
		setSize(400, 135);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
     
    public static synchronized boolean send(String FROM, String PASSWORD,String TO, String TEXT) {
        Properties props = new Properties();
 
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.user", FROM);
 
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);
        props.put("mail.smtp.debug", DEBUG);
 
        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
 
        try {
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
 
            MimeMessage message = new MimeMessage(session);
            message.setText(TEXT);
            message.setSubject(SUBJECT);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(RecipientType.TO, new InternetAddress(TO));
            message.saveChanges();
 
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, FROM, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
 
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return false;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String fromEmail = this.fromEmail.getText().toString();
		
		String fromPassword = "";
		char[] fromPasswordArray = this.fromPassword.getPassword();
		for (int i = 0; i < fromPasswordArray.length; i++) {
			fromPassword += fromPasswordArray[i];
		}
		
		String toEmail = this.toEmail.getText().toString();
		
		if (e.getSource() == sendEmail) {
			if (send(fromEmail, fromPassword, toEmail, encryptedMessage)) {
				JOptionPane.showMessageDialog(null, "Email sent successfully!", "Email", JOptionPane.PLAIN_MESSAGE);
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "An error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (e.getSource() == cancelEmail) {
			dispose();
		}
	}
}
