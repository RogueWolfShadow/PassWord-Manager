import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class IndexPanel extends JPanel {
    JTextField jtfSea = new JTextField("Search");
    JButton btn = new JButton("Find");

    JTextField jtfoldAcc = new JTextField("Old Account");
    JTextField jtfoldPass = new JTextField("Old User&Pass");
    JTextField jtfnewPass = new JTextField("New User&Pass");
    JButton btnUp = new JButton("Update");

    JButton btnAdd = new JButton("Add");
    JTextField jtapass = new JTextField("Add UserName & Add Password");
    JTextField jtacc = new JTextField("Add Account");

    JButton btnRemAcc = new JButton("Remove Account");
    JTextField jtRem = new JTextField("Account name");

    JButton btnRemPass = new JButton("Remove Pass");
    JTextField jtRemPassAcc = new JTextField("Account name");
    JTextField jtRemPass = new JTextField("User&Pass");

    JButton btnSave = new JButton("Save");
    JButton btnExit = new JButton("Logout");
    PassFrame pf;

    public IndexPanel(PassFrame j) throws IOException {
        pf = j;
        this.setVisible(false);
        this.setLayout(new GridLayout(7, 2));
        btn.addActionListener(e -> {
            search(jtfSea.getText());
        });
        btnAdd.addActionListener(e -> {
            add(jtacc.getText(), jtapass.getText());
        });
        btnRemAcc.addActionListener(e -> {
            remove(jtRem.getText());
        });
        btnRemPass.addActionListener(e -> {
            removePass(jtRemPassAcc.getText(), jtRemPass.getText());
        });
        btnExit.addActionListener(e -> {
            logOut();
        });
        btnUp.addActionListener(e -> {
            update(jtfoldAcc.getText(), jtfoldPass.getText(), jtfnewPass.getText());
        });
        btnSave.addActionListener(e -> {
            try {
                save();
            } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
                ex.printStackTrace();
            }
        });
//        btn.setBounds(20,20,60,30);
//        jtfSea.setBounds(100,20,200,30);
        this.add(btn); this.add(jtfSea);
//        btnAdd.setBounds(20,60,60,30);
//        jtacc.setBounds(100,60,200,30);
//        jtapass.setBounds(310,60,200,30);
        this.add(btnAdd);
        JPanel temp1 = new JPanel();
        temp1.setLayout(new GridLayout(1,2));
        this.add(temp1);

        temp1.add(jtacc);temp1.add(jtapass);

//        btnRemAcc.setBounds(20,100,60,30);
//        jtRem.setBounds(100,100,200,30);
        this.add(btnRemAcc); this.add(jtRem);

//        btnRemPass.setBounds(20,140,60,30);
//        jtRemPassAcc.setBounds(100,140,200,30);
//        jtRemPass.setBounds(310,140,200,30);
        this.add(btnRemPass);
        JPanel temp2 = new JPanel();
        temp2.setLayout(new GridLayout(1,2));
        this.add(temp2);
        temp2.add(jtRemPassAcc);temp2.add(jtRemPass);

//        btnUp.setBounds(20,180,60,30);
//        jtfoldAcc.setBounds(100,180,200,30);
//        jtfoldPass.setBounds(310,180,200,30);
//        jtfnewPass.setBounds(520,180,200,30);
        this.add(btnUp);
        JPanel temp3 = new JPanel();
        temp3.setLayout(new GridLayout(1,3));
        this.add(temp3);
        temp3.add(jtfoldAcc);temp3.add(jtfoldPass);temp3.add(jtfnewPass);

        //btnSave.setBounds(20,220,60,30);
        this.add(btnSave);

        //btnSave.setBounds(20,260,60,30);
        this.add(btnExit);
    }

    private void search(String text) {
        pf.contentPanel.display("null");
        if (pf.contentPanel.list.containsKey(text)) {
            pf.contentPanel.display(text);
        }
    }

    private void logOut(){
        pf.contentPanel.list = null;
        pf.keyPanel.password = "";
        pf.keyPanel.seed = "";
        pf.keyPanel.setVisible(true);
        pf.contentPanel.setVisible(false);
        pf.indexPanel.setVisible(false);
    }

    private void add(String account, String pass) {
        if (pf.contentPanel.list.containsKey(account)) {
            pf.contentPanel.list.get(account).add(pass);
            pf.contentPanel.display("null");
        } else {
            ArrayList<String> blah = new ArrayList<>();
            blah.add(pass);
            pf.contentPanel.list.put(account, blah);
            pf.contentPanel.display("null");
        }
    }

    private void remove(String text) {
        pf.contentPanel.list.remove(text);
        pf.contentPanel.display("null");
    }
    private void removePass(String acc, String text) {
        pf.contentPanel.list.get(acc).remove(text);
        pf.contentPanel.display("null");
    }
    private void update(String oldAcc, String oldPass, String newUserPass) {
        pf.contentPanel.list.get(oldAcc).remove(oldPass);
        pf.contentPanel.list.get(oldAcc).add(newUserPass);
        pf.contentPanel.display("null");
    }
    private void save() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String s = PassMain.toString(pf.contentPanel.list);
        Key aesKey = new SecretKeySpec(pf.keyPanel.password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(s.getBytes());
       try (FileOutputStream fos = new FileOutputStream("file1.txt")){ fos.write(encrypted);}
    }



//    private void save() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//        Iterator<String> a = pf.contentPanel.list.keySet().iterator();
//        StringBuilder answer = new StringBuilder();
//        int total = pf.contentPanel.list.keySet().size();
//        answer.append(total).append("\n");
//        while (a.hasNext()) {
//            String key = a.next();
//            answer.append(key);
//            ArrayList<String> values = pf.contentPanel.list.get(key);
//            for (String l : values) {
//                answer.append(":");
//                answer.append(l);
//            }
//            answer.append("\n");
//        }
//
//        String s = PassMain.toString(pf.contentPanel.list);
//
//
//        Key aesKey = new SecretKeySpec(pf.keyPanel.password.getBytes(), "AES");
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
//        byte[] encrypted = cipher.doFinal(answer.toString().getBytes());
//        writer = new BufferedWriter(new FileWriter("file1.txt"));
//        writer.write(new String(encrypted));
//        writer.close();
//    }
}
