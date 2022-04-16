import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class KeyPanel extends JPanel {
    //64 buttons
    public String password = "";
    public String seed = "";
    private PassFrame parent;

    public KeyPanel(PassFrame j) {
        this.parent = j;
        this.setVisible(true);
        this.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 10; i++) {
            createKeyButton(String.valueOf(i));
        }
        for (int i = 65; i < 91; i++) {
            createKeyButton(String.valueOf((char) (i)));
        }
        for (int i = 97; i < 123; i++) {
            createKeyButton(String.valueOf((char) (i)));
        }
        createKeyButton("@");
        createKeyButton("#");
    }

    private void clickKey(String text) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, ClassNotFoundException {
        if (text.equals("#")) {
            JOptionPane.showMessageDialog(this, "Yay!");
            while(seed.length()<16){seed+=seed;}
            seed = seed.substring(0,16);
            password = seed;
            parent.logSucess();
            seed = "";
        } else {
            this.seed += text;
        }
    }

    private void createKeyButton(String text) {
        JButton t = new JButton(text);
        t.addActionListener(e -> {
            try {
                clickKey(text);
            } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        add(t);
    }

}
