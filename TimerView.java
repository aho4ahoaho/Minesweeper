import javax.swing.*;
import java.time.*;

public class TimerView extends JLabel implements Runnable{
    private LocalDateTime startTime;
    private Boolean isRunning = false;
    TimerView(){
        startTime = LocalDateTime.now();
        setText("00:00");
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

    public void stop(){
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        setText("00:00");
        while(isRunning){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            long time = getTime();
            long minute = time / 60;
            long second = time % 60;
            setText(String.format("%02d:%02d", minute, second));
        }
    }
}
