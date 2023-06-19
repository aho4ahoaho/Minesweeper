import javax.swing.*;
import java.awt.*;
import java.time.*;

public class TimerView extends JPanel implements Runnable{
    private LocalDateTime startTime;
    private Boolean isRunning = false;
    TimerView(){
        startTime = LocalDateTime.now();
    }

    public void setTime(){
        setTime(LocalDateTime.now());
    }

    public void setTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    // 経過時間を取得
    public long getTime(){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startTime, now);
        long time = duration.getSeconds();
        return time;
    }

    public void stopCount(){
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        while(isRunning){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            repaint();
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        long time = getTime();
        g.drawString("経過時間: " + time + "秒", 10, 20);
    }
}
