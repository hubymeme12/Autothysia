/*
    Author : Hubert F. Espinola I
    Date : November 1, 2021

    What is this interface?
        This interface is the overall abstraction of the Autothysia Encryption class.

    What is this all about?
        The encryption is a simple modified XOR encryption with circular shifting involved,
        since shifting of a byte may result in loss of information (a bit or more), BPBS is used
        therefore, preserving the bits that will be shifted.

    What is BPBS?
        BPBS is short for Bit-Preserving-Bit-Shift, it is similar to the algorithm: "circular shifting"
        but is using more memory, slower, but is less complex. bpbs_shift() can be overridden with the
        actual circular shifting for faster performance (I just prefer using this one for less complexity to understand
        the code).

        n - number of allocated memory.
        s - number of shift to be performed.
        i - input of the user.

        > BPBS Left shift (sample s = 2; n = 5):
            a_n = (i_n >> (n - s))     | a | b | c | d | e | --> |   |   |   | a | b |
            b_n = (i_n << s)           | a | b | c | d | e | --> | c | d | e |   |   |

                                       |   |   |   | a | b |   xor
                                       | c | d | e |   |   |   xor
            i_n = (a_n ^ b_n)          | c | d | e | a | b |
            or
            i_n = (a_n ^ (i_n << s)); for lesser memory allocation

        > BPBS right shift (reverse of left shift):
            a_n = (i_n << (n - s))     | a | b |   |   |   | xor
            b_n = (i_n >> s)           |   |   | c | d | e | xor

            i_n = (a_n ^ b_n)          | a | b | c | d | e |
            or
            i_n = (a_n ^ (i_n << s)); for lesser memory allocation

        In simple terms: left shift is reversible by right shift and vice versa, so it can be applied in encryption.
 */

package com.authothysia;
import java.io.File;
import java.io.IOException;

public interface AutothysiaInterface {
    ///////////////////////////////////////////
    //  SIMPLE ENCRYPTION AND PREREQUISITES  //
    ///////////////////////////////////////////
    // calculates the number of shifts
    // the number of shifts depends on the password
    public int number_of_shift(String password, int limiting_shift);

    // shifts the file bytes
    public byte bpbs_left_shift(byte input_byte);
    public byte bpbs_right_shift(byte input_byte);


    //////////////////////////////
    //  HYBRID ENCRYPTION PART  //
    //////////////////////////////
    // encrypts a single byte from the file
    public byte encryptByte(byte input_byte, byte key);

    // encrypts the whole file bytes of the inputted byte array
    public byte[] encryptByte(byte[] input_byte);

    // encrypts file bytes of inputted file path
    public byte[] encryptFile(File path) throws IOException;
    public byte[] encryptFile(String path) throws IOException;

    //////////////////////////////
    //  HYBRID DECRYPTION PART  //
    //////////////////////////////
    // decrypts a single byte from the file
    public byte decryptByte(byte input_byte, byte key);

    // decrypts the whole file bytes of the inputted byte array
    public byte[] decryptByte(byte[] input_byte);

    // decrypts file bytes of inputted file path
    public byte[] decryptFile(File path) throws IOException;
    public byte[] decryptFile(String path) throws IOException;
}
