import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){
        MainMenu mainMenu = new MainMenu();
        JFrame frame = mainMenu.getFrame();
        mainMenu.test();
        frame.setVisible(true);
    }
}
