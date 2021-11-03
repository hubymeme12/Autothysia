package com.authothysia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainFrame {
    // gui interface objects
    static JFrame mainFrame;
    static JTextField targetFile;
    static JPasswordField passField;
    static JButton encrypt;
    static JButton decrypt;
    static JButton addFile;
    static JLabel label1;
    static JLabel label2;

    // some constants for sizes and offsets
    // Text field constants
    static final int left_textField_offset = 32;
    static final int top_textField_offset = 32;
    static final int y_textField = 20;
    static final int x_textField = 200;

    // Label constants
    static final int left_label_offset = 10;
    static final int x_label = 55;

    // Button Constants
    static final int x_button = 80;
    static final int y_button = 30;

    MainFrame() {
        // declarations of variables
        mainFrame = new JFrame("Autothysia Desktop");
        targetFile = new JTextField();
        passField = new JPasswordField();
        encrypt = new JButton("Encrypt");
        decrypt = new JButton("Decrypt");
        addFile = new JButton("...");

        ////////////////////////
        //  LABEL ATTRIBUTES  //
        ////////////////////////
        // Filepath label
        label1 = new JLabel("Filepath");
        label1.setSize(x_label, y_textField);
        label1.setLocation(left_label_offset, top_textField_offset);
        mainFrame.add(label1);

        // Password label
        label2 = new JLabel("Password");
        label2.setSize(x_label, y_textField);
        new SwingRelative(label2, label1, 0, 10);
        mainFrame.add(label2);

        /////////////////////////////
        //  TEXT FIELD ATTRIBUTES  //
        /////////////////////////////
        // Text input for file directory
        targetFile.setSize(x_textField, y_textField);
        targetFile.setLocation(x_label + left_textField_offset, top_textField_offset);
        mainFrame.add(targetFile);

        // Password input for password
        passField.setSize(x_textField, y_textField);
        new SwingRelative(passField, targetFile, 0, 10);
        mainFrame.add(passField);

        /////////////////////////
        //  BUTTON ATTRIBUTES  //
        /////////////////////////
        encrypt.setSize(x_button, y_button);
        new SwingRelative(encrypt, passField, 12, 12);
        mainFrame.add(encrypt);

        // decrypt button attributes
        decrypt.setSize(x_button, y_button);
        new SwingRelative(decrypt, encrypt, encrypt.getWidth() + 12, -encrypt.getHeight());
        mainFrame.add(decrypt);

        // add file button attributes
        addFile.setSize(y_textField, y_textField);
        new SwingRelative(addFile, targetFile, 200, -targetFile.getHeight());
        mainFrame.add(addFile);

        //////////////////////////////
        //  BUTTON FUNCTIONALITIES  //
        //////////////////////////////
        addFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // opens file explorer interface
                JFileChooser fileChooser = new JFileChooser("C:\\");
                int response = fileChooser.showOpenDialog(null);

                // check if the user selected a file
                if (response == JFileChooser.APPROVE_OPTION) {
                    // show the target file
                    targetFile.setText( fileChooser.getSelectedFile().getAbsolutePath() );
                }
            }
        });

        // file encryption button action
        encrypt.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checks if the text field has input
                if (!targetFile.getText().equals("") && passField.getPassword().length != 0) {
                    // test for file's existence
                    File file = new File(targetFile.getText());
                    if (file.exists()) {
                        // implement the encryption in the file bytes
                        AutothysiaEncryption encryption = new AutothysiaEncryption(file, new String(passField.getPassword()));

                        // retrieve encrypted bytes
                        try {
                            byte[] encryptedBytes = encryption.encryptFile();

                            // write the file bytes here
                            FileOutputStream fileOut = new FileOutputStream(file);
                            fileOut.write(encryptedBytes);
                            fileOut.close();

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        });

        // Action listener of decrypt button
        decrypt.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checks if the text field has input
                if (!targetFile.getText().equals("") && passField.getPassword().length != 0) {
                    // test for file's existence
                    File file = new File(targetFile.getText());
                    if (file.exists()) {
                        // implement the encryption in the file bytes
                        AutothysiaEncryption decryption = new AutothysiaEncryption(file, new String(passField.getPassword()));

                        // retrieve encrypted bytes
                        try {
                            byte[] encryptedBytes = decryption.decryptFile();

                            // write the file bytes here
                            FileOutputStream fileOut = new FileOutputStream(file);
                            fileOut.write(encryptedBytes);
                            fileOut.close();

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        });
        // Final setup for the frame
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);

    }
}
