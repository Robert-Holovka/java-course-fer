package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Provides methods for cryptographic operations such as calculating message
 * digest, encryption and decryption. Program executes single operation and then
 * terminates. It can take 2 or 3 arguments depending on which operation will be
 * executed. Arguments are passed through the command line.
 * 
 * 1st argument is always command name. 2nd argument is path to the file from
 * which this program will read its bytes. 1st and 2nd arguments are obligatory.
 * 3rd argument is path to the file where program will output result of the
 * operation. 3rd argument must be provided only for encryption/decryption.
 * 
 * If operation is calculating message digest, program will prompt message to
 * the user asking to specify expected sha-256 digest.
 * 
 * <p>
 * Example of usage for comparing message digest:
 * </p>
 * 
 * <pre>
 * java Crypto checksha hw06test.bin
 * output: Please provide expected sha-256 digest for hw06test.bin:
 * input:  2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598
 * output: Digesting completed. Digest of hw06test.bin matches expected digest.
 * </pre>
 * 
 * If operation is encryption/decryption, program will prompt message to the
 * user asking for password and initialization vector.
 * 
 * <p>
 * Example of usage for encryption/decryption.
 * </p>
 * 
 * <pre>
 * java Crypto encrypt hw06.pdf hw06.crypted.pdf
 * output: Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):
 * input:  e52217e3ee213ef1ffdee3a192e2ac7e
 * output: Please provide initialization vector as hex-encoded text (32 hex-digits):
 * input:  000102030405060708090a0b0c0d0e0f
 * output: Encryption completed. Generated file hw06.crypted.pdf based on file hw06.pdf.
 * </pre>
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Crypto {

	/**
	 * List of valid command names.
	 */
	private static final List<String> commands = new ArrayList<>(Arrays.asList("checksha", "encrypt", "decrypt"));
	/**
	 * Number of elements that buffer can store.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 4096;
	/**
	 * Buffer used for reading/writing data.
	 */
	private static byte[] buff = new byte[DEFAULT_BUFFER_SIZE];

	/**
	 * Entry point of the program. Valid number of arguments is 2 or 3.
	 * 
	 * @see Crypto
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 3) {
			System.out.println("Wrong number of arguments.");
			return;
		}

		String command = args[0];
		if (!commands.contains(command)) {
			System.out.println("Invalid command: " + command);
			return;
		}

		Path inFile = Paths.get(args[1]);
		Scanner sc = new Scanner(System.in);

		if (command.equals("checksha")) {
			System.out.printf("Please provide expected sha-256 digest for %s:%n> ", inFile);
			String expected = sc.nextLine();
			System.out.println(checkSHA(inFile, expected));
		} else {
			if (args.length != 3) {
				System.out.println("Wrong number of arguments");
				sc.close();
				return;
			}

			System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\r\n" + "> ");
			String key = sc.nextLine();
			System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):\r\n" + "> ");
			String vector = sc.nextLine();

			Path outFile = Paths.get(args[2]);
			boolean encrypt = command.equals("encrypt");

			if (crypt(encrypt, inFile, outFile, key, vector)) {
				System.out.printf("%s completed. Generated file %s based on file %s.",
						encrypt ? "Encryption" : "Decryption", outFile.getFileName(), inFile.getFileName());
			} else {
				System.out.printf(
						"%s failed. Check if paths to the files valid or if you have provided correct password and vector",
						encrypt ? "Encryption" : "Decryption");
			}
		}

		sc.close();
	}

	/**
	 * Performs encryption or decryption on a provided input file and writes result
	 * on a specified path. Algorithm used for cryptographic operations is 128-bit AES.
	 * 
	 * @param encrypt    Flag which specifies whether encryption or decryption will
	 *                   be performed
	 * @param inFile     File on which operation will be performed
	 * @param outFile    File where result will be written
	 * @param key        hex-encoded password
	 * @param initVector hex-encoded initialization vector
	 * @return True if operation was successful, false otherwise
	 */
	private static boolean crypt(boolean encrypt, Path inFile, Path outFile, String key, String initVector) {

		try (InputStream is = new BufferedInputStream(Files.newInputStream(inFile));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(outFile))) {

			// Init cipher
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(key), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(initVector));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			while (true) {
				int size = is.read(buff);
				if (size < 0) {
					os.write(cipher.doFinal());
					break;
				}
				byte[] crypted = cipher.update(buff, 0, size);
				os.write(crypted);
			}

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Checks whether given file has same message digest as the provided one.
	 * Message digest is calculated with SHA-256 algorithm.
	 * 
	 * @param path     Path to the file which message digest will be calculated
	 * @param expected Expected message digest
	 * @return String status message
	 */
	private static String checkSHA(Path path, String expected) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			while (true) {
				int size = is.read(buff);
				if (size < 0)
					break;
				md.update(buff, 0, size);
			}

			String actual = Util.bytetohex(md.digest());
			if (actual.equals(expected)) {
				return String.format("Digesting completed. Digest of %s matches expected digest.%n",
						path.getFileName());
			} else {
				return String.format(
						"Digesting completed. Digest of %s does not match the expected digest. Digest was: %s",
						path.getFileName(), actual);
			}
		} catch (IOException | NoSuchAlgorithmException e) {
			return "Could not digest, something went wrong.";
		}
	}
}
