import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
public class GameFunction extends Component {
    static File path = new File("."); //절대경로
    String root = path.getAbsolutePath(); //경로

    static String player_name; //플레이어 이름 변수
    PlayerInfo highestPlayer;
    ArrayList<PlayerInfo> highScoreList = new ArrayList<>(); //최고 득점자 객체
    ArrayList<Players> listN = new ArrayList<>();//플레이어 이름 객체
    boolean isMainScreen, isExplainScreen, isGameScreen, isFinishScreen; //화면 전환을 위한 boolean 자료형

    Image gameScreen = new ImageIcon(getClass().getClassLoader().getResource("res/gameScreen.png")).getImage(); //게임 화면
    Image explainScreen = new ImageIcon(getClass().getClassLoader().getResource("res/explainScreen.png")).getImage(); //설명화면
    Image mainScreen = new ImageIcon(getClass().getClassLoader().getResource("res/mainScreen.png")).getImage();
    Image finishScreen = new ImageIcon(getClass().getClassLoader().getResource("res/finishScreen.png")).getImage(); //게임 종료 화면
    public static void bgm() {
        try {
            File bgm = new File(path + "/res/bgm.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(bgm));
            clip.loop(10);
            clip.start();
        }catch(Exception e) {
            System.out.println("Sound Error bgm");
        }
    }
    // 몬스터가 죽을 때 나는 소리
    public static void boom() {
        try {
            File boom = new File(path + "/res/boom.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(boom));
            clip.loop(0);
            clip.start();
        }catch(Exception e) {
            System.out.println("Sound Error bgm");
        }
    }

    // 파일 읽기
    public void  FILERead() throws IOException {
        File file = new File("HIGHEST.txt");
        Scanner s = null;
        String fileName;
        int fileScore;

        if(!file.exists())
            file.createNewFile();
        else {
            try {
                s = new Scanner(new BufferedReader(new FileReader(file)));
                while(s.hasNext()) {
                    fileName = s.next();
                    fileScore = s.nextInt();

                    highScoreList.add(new PlayerInfo(fileName, fileScore));
                }
            } finally {
                if(s!=null)
                    s.close();
            }
        }
    }

    //파일 쓰기
    public void RecordWrite() throws IOException{
        if(highScoreList.size() >= 3) {
            for(int i = 1; i<highScoreList.size(); i++) {
                if(highestPlayer.compareTo(highScoreList.get(i)) >0)
                    highestPlayer = highScoreList.get(i);
            }
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(path + "/HIGHEST.txt");
            for(PlayerInfo pi : highScoreList) {
                out.println(pi.getName());
                out.println(pi.get());
            }
        }catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(out!=null)
                out.close();
        }
    }

    //boolean형을 통해 mainScreen그려주기
    public void init() {
        isMainScreen = true;
        isExplainScreen = false;
        isGameScreen = false;
        isFinishScreen = false;
    }
    public void name() {
        listN.add(new Players(player_name));
    }

    //화면 전환
    public void screenDraw(Graphics g) {
        if (isMainScreen == true) {
            g.drawImage(mainScreen, 0, 0, null);
        }

        if (isExplainScreen == true) {
            g.drawImage(explainScreen, 0, 0, null);
        }

        if (isGameScreen == true) {
            g.drawImage(gameScreen, 0, 0, null);
        }

        if (isFinishScreen == true) {
            g.drawImage(finishScreen, 0, 0, null);
        }
    }

    // 시작 전 사용자 이름 받기
    public void nameInput() {
        for (; ; ) {
            player_name = (String) JOptionPane.showInputDialog(this, "플레이어의 이름을 입력하세요(3자 이내)", "SHOOTING GAME", JOptionPane.PLAIN_MESSAGE);
            if (player_name == null)
                System.exit(0);
            else if (player_name.length() > 3)
                player_name = (String) JOptionPane.showInputDialog(this, "플레이어의 이름을 입력하세요(3자 이내)", "SHOOTING GAME", JOptionPane.PLAIN_MESSAGE);
            else if (player_name.length() <= 3) {
                break;
            }
        }
    }

}

