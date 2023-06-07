import javax.swing.*;
import java.awt.*;

public class MainMenu{
    JFrame frame;
    MainMenu(){
        JFrame frame = new JFrame("Main Menu");
        this.frame = frame;
        setFrameOption();
    }
    MainMenu(JFrame frame){
        this.frame = frame;
        setFrameOption();
    }
    private void setFrameOption(){
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JFrame getFrame(){
        return frame;
    }
    void test (){
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel midPanel = new JPanel();
        GridLayout midLayout =  new GridLayout(3, 3);
        midPanel.setLayout(midLayout);


        frame.add(topPanel);
        frame.add(midPanel);
        frame.add(bottomPanel);
    }
}