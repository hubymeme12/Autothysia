package com.authothysia;

import java.io.*;
import java.util.Arrays;

public class AutothysiaEncryption implements AutothysiaInterface {
    private int shifts;
    private String password;
    private File targetFile;

    // constructor for assigning password and limiting shifts
    AutothysiaEncryption(String _password, int _limiting_shifts) {
        assignRequisites(_password, _limiting_shifts);
        targetFile = null;

        System.out.println("Calculated shifts : " + shifts);
    }

    // constructor for assigning file path only
    AutothysiaEncryption(File _file_path, String _password) {
        targetFile = _file_path;
        assignRequisites(_password, 7);
    }

    // constructor for assigning the whole values needed
    AutothysiaEncryption(File _file_path, String _password, int _limiting_shifts) {
        assignRequisites(_password, _limiting_shifts);
        targetFile = _file_path;
    }

    // assigning password and limiting shifts
    public void assignRequisites(String _password, int _limiting_shifts) {
        password = _password;
        shifts = number_of_shift(_password, _limiting_shifts);
    }

    // encrypt the file path inputted from the constructor
    public byte[] encryptFile() throws IOException {
        return encryptFile(targetFile);
    }

    // decrypts the file path inputted from the constructor
    public byte[] decryptFile() throws IOException {
        return decryptFile(targetFile);
    }

    @Override
    public int number_of_shift(String password, int limiting_shift) {
        // the hash generator can be modified by you
        for (int i = 0; i < password.length(); i++) {
            if (i % 2 == 0) {
                shifts += password.charAt(i);
            } else {
                shifts *= password.charAt(i);
            }
        }

        // assign the limiting shifts
        return (shifts % limiting_shift);
    }

    @Override
    public byte bpbs_left_shift(byte input_byte) {
        byte a = (byte) ((input_byte & 0xff) >> (8 - shifts));
        byte b = (byte) (input_byte << shifts);
        return (byte) (a | b);
    }

    @Override
    public byte bpbs_right_shift(byte input_byte) {
        byte a = (byte) (input_byte << (8 - shifts));
        byte b = (byte) (((input_byte & 0xff) >> shifts));
        return (byte) (a ^ b);
    }


    @Override
    public byte encryptByte(byte input_byte, byte key) {
        return bpbs_left_shift((byte)(input_byte ^ key));
    }

    @Override
    public byte[] encryptByte(byte[] input_byte) {
        // updates each byte value in the array
        for (int i = 0; i < input_byte.length; i++)
            input_byte[i] = encryptByte(input_byte[i], (byte) password.charAt((i % password.length())));
        return input_byte;
    }

    @Override
    public byte[] encryptFile(File path) throws IOException {
        InputStream fileBytes = new FileInputStream(path);
        System.out.println("File length : " + path.length());
        byte[] buffer = new byte[(int)path.length()];
        fileBytes.read(buffer);
        fileBytes.close();

        System.out.println("===== normal =====");
        System.out.println(Arrays.toString(buffer));

        // update the buffer with encrypted file bytes
        buffer = encryptByte(buffer);
        System.out.println("===== encrypted =====");
        System.out.println(Arrays.toString(buffer));

        return buffer;
    }

    @Override
    public byte[] encryptFile(String path) throws IOException {
        File f_path = new File(path);
        if (f_path.exists())
            return encryptFile(f_path);
        return null;
    }

    @Override
    public byte decryptByte(byte input_byte, byte key) {
        return (byte) ( bpbs_right_shift(input_byte) ^ key );
    }

    @Override
    public byte[] decryptByte(byte[] input_byte) {
        for (int i = 0; i < input_byte.length; i++)
            input_byte[i] = decryptByte(input_byte[i], (byte) password.charAt(i % password.length()));
        return input_byte;
    }

    @Override
    public byte[] decryptFile(File path) throws IOException {
        InputStream fileBytes = new FileInputStream(path);
        byte[] buffer = fileBytes.readAllBytes();

        buffer = decryptByte(buffer);
        return buffer;
    }

    @Override
    public byte[] decryptFile(String path) throws IOException {
        File f_path = new File(path);
        if (f_path.exists())
            return decryptFile(f_path);
        return null;
    }
}
