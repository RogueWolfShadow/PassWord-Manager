import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ContentPanel extends JPanel {
    JTextArea jta = new JTextArea("");
    HashMap<String, ArrayList<String>> list = new HashMap<>();


    public ContentPanel(PassFrame f){
        this.setVisible(false);
        jta.setVisible(true);
        Font tr = new Font("TimesRoman", Font.PLAIN, 15);
        jta.setFont(tr);
        this.add(jta);
    }

    public void display(String text){
        if (text.equals("null")){
            jta.setText("");
            return;
        }
        Iterator<String> a = list.keySet().iterator();
        ArrayList<String> b = new ArrayList<>();
        int index = 0;
        while(a.hasNext()){
            String c = a.next();
            if (c.contains(text)){
                b.add(c);
                index++;
            }
        }
        jta.setColumns(index);
        for (int i = 0;i<index;i++){
            jta.append(b.get(i)+": "+list.get(b.get(i)));
        }
    }

}
