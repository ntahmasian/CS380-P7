/**
 * Created by Narvik on 6/1/17.
 */


import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import java.util.zip.CRC32;


public class FileTransfer {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {


        if (args[0].equalsIgnoreCase("makekeys")) {
            try {
                KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
                gen.initialize(2048);
                KeyPair keyPair = gen.genKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();
                try (ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(new File("public.bin")))) {
                    oos.writeObject(publicKey);
                }
                try (ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(new File("private.bin")))) {
                    oos.writeObject(privateKey);
                }
            } catch (NoSuchAlgorithmException | IOException e) {
                e.printStackTrace(System.err);
            }
        } else if (args[0].equalsIgnoreCase("server") && args[1].equalsIgnoreCase("private.bin")) {
            //server
            PrivateKey prk = (PrivateKey) new ObjectInputStream(new FileInputStream(new File(args[1]))).readObject();
            int serverPortNumber = Integer.parseInt(args[2]);

            try (ServerSocket serverSocket = new ServerSocket(serverPortNumber)) {


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (args[0].equalsIgnoreCase("client") && args[1].equalsIgnoreCase("public.bin")) {
            //client
            PublicKey puk = (PublicKey) new ObjectInputStream(new FileInputStream(new File(args[1]))).readObject();
            String host = args[2];
            int clientPortNumber = Integer.parseInt(args[3]);

            try (Socket socket = new Socket(host, clientPortNumber)) {
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("invalid entry ");
        }
    }
}