import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class PassFrame extends JFrame {
    KeyPanel keyPanel = new KeyPanel(this);
    IndexPanel indexPanel = new IndexPanel(this);
    ContentPanel contentPanel = new ContentPanel(this);
    private static final String fileName = "file1.txt";
    public PassFrame() throws IOException {
        super("Frame");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(keyPanel, BorderLayout.CENTER);
        Font tr = new Font("TimesRoman", Font.PLAIN, 18);
        setVisible(true);
    }
    public void logSucess() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, ClassNotFoundException {

        if (Files.exists(Paths.get("file1.txt"))){
            try {
                readFile();
                this.keyPanel.setVisible(false);
                add(indexPanel, BorderLayout.CENTER);
                add(contentPanel, BorderLayout.SOUTH);
                this.contentPanel.setVisible(true);
                this.indexPanel.setVisible(true);
            }catch(Exception e){
                JOptionPane.showMessageDialog(this,"Not the right phrase!");
            }
        }else{
            this.keyPanel.setVisible(false);
            add(indexPanel, BorderLayout.CENTER);
            add(contentPanel, BorderLayout.SOUTH);
            this.contentPanel.setVisible(true);
            this.indexPanel.setVisible(true);
        }
    }
    public void readFile() throws Exception {
        byte[] aah = Files.readAllBytes(Paths.get("C:\\Users\\Daniel\\IdeaProjects\\PassProject\\file1.txt"));
        Key aesKey = new SecretKeySpec(keyPanel.password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(aah));
        contentPanel.list = (HashMap<String, ArrayList<String>>) PassMain.fromString(decrypted);
    }
//    public void readFile() throws FileNotFoundException {
//        Scanner sc = new Scanner(new File("file1.txt"));
//        int max = sc.nextInt();
//        for (int oh = 0;oh<max;oh++){
//            String[]map = sc.next().split(":");
//            ArrayList<String> abacdabra = new ArrayList<>();
//            for (int i = 1; i<map.length;i++){
//                abacdabra.add(map[i]);
//            }
//            contentPanel.list.put(map[0], abacdabra);
//        }
//    }
}
