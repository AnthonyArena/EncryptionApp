package encryptionApp;

import encryptionApp.EncryptionAlgorithm;
import encryptionApp.Email;

import javax.swing.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EncryptionUserInterface extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel messageLabel, keyLabel;
	private JTextField keyField;
	private JTextArea messageField;
	private JButton encryptButton, decryptButton, cancelButton;
	
	public EncryptionUserInterface() {
		setTitle("Encrypter");
		setSize(300, 235);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new FlowLayout());
		
		messageLabel = new JLabel("Message to be Encrypted: ");
		keyLabel = new JLabel("Encryption Key: ");
		
		messageField = new JTextArea(8, 20);
		JScrollPane messageScroll = new JScrollPane(messageField);
		messageField.setLineWrap(true);
		messageField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		keyField = new JTextField(8);
		encryptButton = new JButton("Encrypt");
		encryptButton.addActionListener(this);
		decryptButton = new JButton("Decrypt");
		decryptButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		panel.add(messageLabel);
		panel.add(messageScroll);
		panel.add(keyLabel);
		panel.add(keyField);
		panel.add(encryptButton);
		panel.add(decryptButton);
		panel.add(cancelButton);
		
		add(panel);
		
		setResizable(false);
		setVisible(true);
	}

	private void saveMessage(String message) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose a folder");
		int val = fileChooser.showSaveDialog(null);
		if (val == JFileChooser.APPROVE_OPTION) {
			File messageFile = fileChooser.getSelectedFile();
			try {
				FileWriter fw = new FileWriter(messageFile);
				BufferedWriter bufferedWriter = new BufferedWriter(fw);
				bufferedWriter.write(message);
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int inputSave;
		String encryptedMessage = "";
		String decryptedMessage = "";
		String message = messageField.getText().toString();
		String key = keyField.getText().toString();
		EncryptionAlgorithm alg = new EncryptionAlgorithm(message, key);
		
		if ((e.getSource() == encryptButton ||
				e.getSource() == decryptButton) && message.equals("")) {
			JOptionPane.showMessageDialog(null, 
					"One or more fields are empty!", "Error", 
					JOptionPane.ERROR_MESSAGE);
		} 
		else if (e.getSource() == encryptButton) {
			encryptedMessage = alg.Encrypt();
			JOptionPane.showMessageDialog(null, "Encrypted message: " + encryptedMessage, 
					"Encryption Successful", JOptionPane.INFORMATION_MESSAGE);
			inputSave = JOptionPane.showConfirmDialog(null, 
					"Would you like to save the encrypted message?", "Save",
					JOptionPane.YES_NO_OPTION);
			if (inputSave == 0) {
				saveMessage(encryptedMessage);
			}
			int inputEmail = JOptionPane.showConfirmDialog(null, 
					"Would you like to email the encrypted message?", "Email",
					JOptionPane.YES_NO_OPTION);
			if (inputEmail == 0) {
				@SuppressWarnings("unused")
				Email email = new Email(encryptedMessage);
				messageField.setText("");
				keyField.setText("");
			}
		} 
		else if (e.getSource() == decryptButton) {
			decryptedMessage = alg.Decrypt();
			JOptionPane.showMessageDialog(null, "Decrypted message: " + decryptedMessage, 
					"Decryption Successful", JOptionPane.INFORMATION_MESSAGE);
			inputSave = JOptionPane.showConfirmDialog(null, 
					"Would you like to save the decrypted message?", "Save",
					JOptionPane.YES_NO_OPTION);
			if (inputSave == 0) {
				saveMessage(decryptedMessage);
			}
		} 
		else if (e.getSource() == cancelButton) {
			System.exit(0);
		}
	}
}
