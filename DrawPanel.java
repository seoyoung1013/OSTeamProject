import attack.A_Attack;
import attack.Attack1;
import attack.Attack2;
import monster.Monster1;
import monster.Monster2;
import monster.Monster3;
import monster.Monster4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements KeyListener {
    int PLANE_WIDTH = 50; //비행기 가로크기
    int PLANE_HEIGHT = 50; //비행기 세로크기
    int PANEL_WIDTH = 720; //패널 가로 크기
    int PANEL_HEIGHT = 1000; //패널 세로 크기

    private Image bImage;
    private Graphics screenGraphics;

    boolean moveUp = false;
    boolean moveDown = false;
    boolean moveRight = false;
    boolean moveLeft = false;
    boolean checkSpace = false;
    boolean t=true;
    JLabel nameLabel;
    JLabel scoreLabel;
    JLabel levelLabel;
    JLabel highName;

    int score = 0; //점수 카운트
    int INDEX = 3; //생명력 카운트
    int level = 1; //레벨 카운트
    int CountE = 0; //엔터키 횟수 카운트
    GameFunction gF = new GameFunction();

    ArrayList<AirPlane> listAP = new ArrayList<AirPlane>(); //비행기 객체
    ArrayList<A_Attack> listAT = new ArrayList<A_Attack>(); //공격 객체
    ArrayList<Attack1> listAM1 = new ArrayList<Attack1>(); //적이 공격하는 객체
    ArrayList<Attack2> listAM2 = new ArrayList<Attack2>(); //적이 공격하는 객체
    ArrayList<Monster1> listM1 = new ArrayList<Monster1>();//적1 객체
    ArrayList<Monster2> listM2 = new ArrayList<Monster2>();//적2 객체
    ArrayList<Monster3> listM3 = new ArrayList<Monster3>(); //적3 객체
    ArrayList<Monster4> listM4 = new ArrayList<Monster4>();  //적4 객체
    ArrayList<Item> listI = new ArrayList<Item>(); //아이템 객체
    ArrayList<Heart> listH = new ArrayList<>(); // 하트 객체
    ArrayList<SubPlane> listSP = new ArrayList<SubPlane>(); //서브 비행기 객체
    Image[] itemList = {
            new ImageIcon(getClass().getClassLoader().getResource("res/HEART1.png")).getImage(), //아이템 하트 추가
            new ImageIcon(getClass().getClassLoader().getResource("res/SUB1.png")).getImage(), //아이템 서브 비행기
            new ImageIcon(getClass().getClassLoader().getResource("res/HEART2.png")).getImage()};  //아이템 하트 삭제

    //삭제 객체
    ArrayList<A_Attack> ATRemove = new ArrayList<>();
    ArrayList<Attack1> MARemove1 = new ArrayList<>();
    ArrayList<Attack2> MARemove2 = new ArrayList<>();
    ArrayList<Monster1> MRemove1 = new ArrayList<>();
    ArrayList<Monster2> MRemove2 = new ArrayList<>();
    ArrayList<Monster3> MRemove3 = new ArrayList<>();
    ArrayList<Monster4> MRemove4 = new ArrayList<>();
    ArrayList<Item> IRemove = new ArrayList<>();
    ArrayList<SubPlane> SPRemove = new ArrayList<>();
    ArrayList<Heart> HRemove = new ArrayList<>();
    ArrayList<AirPlane> APRemove = new ArrayList<>();
    public void paintComponent(Graphics g) {
        bImage = createImage(PANEL_WIDTH, PANEL_HEIGHT); //화면 채우기
        screenGraphics = bImage.getGraphics();

        gF.screenDraw(screenGraphics);
        g.drawImage(bImage, 0, 0, null);

        //생명력이 0이 되었을때 게임 종료 화면
        if (INDEX == 0) {
            gF.isGameScreen = false;
            gF.isFinishScreen = true;

            for (A_Attack a : listAT) {
                ATRemove.add(a);
            }
            for (Attack1 a : listAM1) {
                MARemove1.add(a);
            }
            for (Attack2 a : listAM2) {
                MARemove2.add(a);
            }
            for (Monster1 a : listM1) {
                MRemove1.add(a);
            }
            for (Monster2 a : listM2) {
                MRemove2.add(a);
            }
            for (Monster3 a : listM3) {
                MRemove3.add(a);
            }
            for (Monster4 a : listM4) {
                MRemove4.add(a);
            }
            for (Heart a : listH) {
                HRemove.add(a);
            }
            for (Item a : listI) {
                IRemove.add(a);
            }
            for (AirPlane a : listAP) {
                APRemove.add(a);
            }

            for (A_Attack a : ATRemove)
                listAT.remove(a);
            for (Attack1 a : MARemove1)
                listAM1.remove(a);
            for (Attack2 a : MARemove2)
                listAM2.remove(a);
            for (Monster1 a : MRemove1)
                listM1.remove(a);
            for (Monster2 a : MRemove2)
                listM2.remove(a);
            for (Monster3 a : MRemove3)
                listM3.remove(a);
            for (Monster4 a : MRemove4)
                listM4.remove(a);
            for (Item a : IRemove)
                listI.remove(a);
            for (SubPlane a : SPRemove)
                listSP.remove(a);
            for (Heart a : HRemove)
                listH.remove(a);
            for (AirPlane a : APRemove)
                listAP.remove(a);

            nameLabel.setFont(new Font("FTLAB Hoony", Font.BOLD, 30));
            scoreLabel.setFont(new Font("FTLAB Hoony", Font.BOLD, 30));
            levelLabel.setFont(new Font("FTLAB Hoony", Font.BOLD, 30));
            highName.setFont(new Font("FTLAB Hoony", Font.BOLD, 30));

            //위치 재정렬
            nameLabel.setBounds(210, 600, 300, 100);
            scoreLabel.setBounds(210, 700, 300, 100); //setBounds(x,y,가로,세로) 절대위치
            levelLabel.setBounds(210, 800, 300, 100);
            highName.setBounds(110, 500, 500, 100);

            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            scoreLabel.setHorizontalAlignment(JLabel.CENTER);
            levelLabel.setHorizontalAlignment(JLabel.CENTER);
            highName.setHorizontalAlignment(JLabel.CENTER);

            gF.highScoreList.add(new PlayerInfo(gF.player_name, score));

            t=false;

            //파일 쓰기 실행
            try {
                gF.RecordWrite();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //finishScreen 화면 그려주기
            g.drawImage(gF.finishScreen, 0, 0, 720, 1000, null);

        }

        //15점 이상 = level++
        if (score >= 0)
            level = 1;
        if (score >= 15)
            level = 2;


        //객체 생성
        //하트 그림 그리기
        for (Heart h : listH) {
            h.drawH(g);
        }

        //비행기 그림 그리기
        for (AirPlane ap1 : listAP) {
            ap1.draw(g);
        }

        //공격 그림그리기

        for (A_Attack a : listAT) {
            if (a.getY() < 0)
                ATRemove.add(a);

            if (listAT.isEmpty() != true) {
                a.draw(g);
                a.move();
            }
        }

        //몬스터 1 공격
        if (listAM1.isEmpty() != true)
            for (Attack1 ma : listAM1) {
                ma.draw(g);
                ma.move();
            }

        //몬스터 2 공격
        if (listAM2.isEmpty() != true)
            for (Attack2 ma : listAM2) {
                ma.draw(g);
                ma.move();
            }

        //비행기가 몬스터 공격에 맞았을때, 생명력--, 몬스터 사라짐
        for (AirPlane ap1 : listAP) {
            for (Attack1 ma : listAM1) {
                if (ma.getY() >= ap1.getY() && ma.getX() >= ap1.getX() && ma.getX() <= ap1.getX() + 50 && ma.getY() < ap1.getY() + 50) {
                    MARemove1.add(ma);
                    HRemove.add(listH.get(INDEX - 1));
                    INDEX--;
                }
            }

            for (Attack2 ma : listAM2) {
                if (ma.getY() >= ap1.getY() && ma.getX() >= ap1.getX() && ma.getX() <= ap1.getX() + 50 && ma.getY() < ap1.getY() + 50) {
                    MARemove2.add(ma);
                    HRemove.add(listH.get(INDEX - 1));
                    INDEX--;
                }
            }


            // 몬스터가 비행기의 공격을 받았을때, hp--, hp=0 -> 3분의 확률 아이템 생성, 아이템의 종류는 3가지
            for (Monster1 m : listM1) {
                for (A_Attack a : listAT) {

                    if (m.distance(a.getX() + 2, a.getY()) < 15.5) {
                        ATRemove.add(a);
                        m.reduceHp();

                        if (m.getHp() <= 0) {
                            score++;
                            MRemove1.add(m);
                            gF.boom();

                            //아이템 3분의 1의 확률로 생성
                            int rand = (int) (Math.random() * 3);
                            if (rand == 2) {
                                int rand2 = (int) (Math.random() * 3);
                                if (rand2 == 0)
                                    listI.add(new Item(m.getX(), m.getY(), rand2, itemList[rand2])); //하트 추가
                                else if (rand2 == 1)
                                    listI.add(new Item(m.getX(), m.getY(), rand2, itemList[rand2])); //서브 비행기 추가
                                else if (rand2 == 2)
                                    listI.add(new Item(m.getX(), m.getY(), rand2, itemList[rand2])); //하트 삭제
                            }
                        }
                    }
                }
            }

            for (Monster2 m2 : listM2) {
                for (A_Attack a : listAT) {

                    if (m2.distance(a.getX() + 2, a.getY()) < 18.5) {
                        ATRemove.add(a);
                        m2.reduceHp();

                        if (m2.getHp() <= 0) {
                            score++;
                            MRemove2.add(m2);
                            gF.boom();

                            int rand = (int) (Math.random() * 3); //0~4
                            if (rand == 2) {
                                int rand2 = (int) (Math.random() * 3);
                                if (rand2 == 0)
                                    listI.add(new Item(m2.getX(), m2.getY(), rand2, itemList[rand2]));
                                else if (rand2 == 1)
                                    listI.add(new Item(m2.getX(), m2.getY(), rand2, itemList[rand2]));

                                else if (rand2 == 2)
                                    listI.add(new Item(m2.getX(), m2.getY(), rand2, itemList[rand2]));
                            }
                        }
                    }
                }
            }

            for (Monster3 m3 : listM3) {
                for (A_Attack a : listAT) {
                    if (m3.distance(a.getX() + 2, a.getY()) < 23) {
                        ATRemove.add(a);
                        m3.reduceHp();

                        if (m3.getHp() <= 0) {
                            score++;
                            MRemove3.add(m3);
                            gF.boom();

                            int rand = (int) (Math.random() * 3); //0~4
                            if (rand == 2) {
                                int rand2 = (int) (Math.random() * 3);
                                if (rand2 == 0)
                                    listI.add(new Item(m3.getX(), m3.getY(), rand2, itemList[rand2]));
                                else if (rand2 == 1)
                                    listI.add(new Item(m3.getX(), m3.getY(), rand2, itemList[rand2]));
                                else if (rand2 == 2)
                                    listI.add(new Item(m3.getX(), m3.getY(), rand2, itemList[rand2]));
                            }

                        }
                    }
                }
            }

            for (Monster4 m4 : listM4) {
                for (A_Attack a : listAT) {
                    if (m4.distance(a.getX() + 2, a.getY()) < 25.5) {
                        ATRemove.add(a);
                        m4.reduceHp();

                        if (m4.getHp() <= 0) {
                            score++;
                            gF.boom();
                            MRemove4.add(m4);

                            int rand = (int) (Math.random() * 3); //0~4
                            if (rand == 2) {
                                int rand2 = (int) (Math.random() * 3);
                                if (rand2 == 0)
                                    listI.add(new Item(m4.getX(), m4.getY(), rand2, itemList[rand2]));
                                else if (rand2 == 1)
                                    listI.add(new Item(m4.getX(), m4.getY(), rand2, itemList[rand2]));
                                else if (rand2 == 2)
                                    listI.add(new Item(m4.getX(), m4.getY(), rand2, itemList[rand2]));
                            }
                        }
                    }
                }
            }

            //아이템 생성
            for (Item item : listI) {
                item.draw(g);
                item.move();

                //거리 계산
                if (ap1.distance(item.getXI(), item.getYI()) <= 40) {
                    IRemove.add(item);
                    if (item.getType() == 0) { //첫번째 아이템인 생명력 추가하기
                        listH.add(new Heart(INDEX));
                        if (INDEX < 5)
                            INDEX++; //생명력 추가

                    } else if (item.getType() == 1 && listSP.isEmpty()) { // 두번째 아이템인 보조비행기 추가하기
                        listSP.add(new SubPlane(listAP.get(0).getX(), listAP.get(0).getY()));

                    } else if (item.getType() == 2) { //세번째 아이템인 생명력 감소하기
                        HRemove.add(listH.get(INDEX - 1));
                        INDEX--; //생명력 감소
                    }
                }

                if (item.getYI() >= 980)
                    IRemove.add(item);
            }
        }

        //비행기와 몬스터가 맞닿으면 점수 감점
        for (AirPlane AP : listAP) {
            for (Monster1 m : listM1) {
                m.draw(g);
                m.move();
                if (AP.distance(m.getX() + 15, m.getY() + 15) <= 31) {
                    MRemove1.add(m);
                    score--;
                }
            }

            for (Monster2 m2 : listM2) {
                m2.draw(g);
                m2.move();

                if (AP.distance(m2.getX() + 18, m2.getY() + 18) <= 44) {
                    MRemove2.add(m2);
                    score--;
                }
            }

            for (Monster3 m3 : listM3) {
                if (level > 1) {
                    m3.draw(g);
                    m3.move();

                    if (AP.distance(m3.getX() + 22, m3.getY() + 22) <= 48) {
                        MRemove3.add(m3);
                        score--;
                    }
                }
            }

            for (Monster4 m4 : listM4) {
                if (level > 1) {
                    m4.draw(g);
                    m4.move();

                    if (AP.distance(m4.getX() + 25, m4.getY() + 25) <= 51) {
                        MRemove4.add(m4);
                        score--;
                    }
                }
            }
        }

        //보조 비행기 추가
        for (SubPlane sp : listSP) {
            sp.x=listAP.get(0).getX();sp.y= listAP.get(0).getY();
            sp.draw(g);
        }

        //score과 level 새로고침
        scoreLabel.setText("SCORE: " + score);
        levelLabel.setText("LEVEL: " + level);


        //제거
        for (A_Attack a : ATRemove)
            listAT.remove(a);
        for (Attack1 a : MARemove1)
            listAM1.remove(a);
        for (Attack2 a : MARemove2)
            listAM2.remove(a);
        for (Monster1 a : MRemove1)
            listM1.remove(a);
        for (Monster2 a : MRemove2)
            listM2.remove(a);
        for (Monster3 a : MRemove3)
            listM3.remove(a);
        for (Monster4 a : MRemove4)
            listM4.remove(a);
        for (Item a : IRemove)
            listI.remove(a);
        for (SubPlane a : SPRemove)
            listSP.remove(a);
        for (Heart a : HRemove)
            listH.remove(a);
        for (AirPlane a : APRemove)
            listAP.remove(a);

        setFocusable(true);
        requestFocus();
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    //비행기를 방향키로 이동
    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();

        //방향 키보드를 통해 움직임 조정
        if (keycode == KeyEvent.VK_UP) {
            moveUp = true;
        } else if (keycode == KeyEvent.VK_DOWN) {
            moveDown = true;
        } else if (keycode == KeyEvent.VK_RIGHT) {
            moveRight = true;
        } else if (keycode == KeyEvent.VK_LEFT) {
            moveLeft = true;
        }
        if (keycode == KeyEvent.VK_SPACE) {
            checkSpace = true;
        }

        //엔터키를 통해 화면 바꿈
        if (keycode == KeyEvent.VK_ENTER) {
            CountE++;

            //시작 화면 -> 설명 화면
            if (CountE == 1) {
                gF.isMainScreen = false;
                gF.isExplainScreen = true;
                gF.isGameScreen = false;
                gF.isFinishScreen = false;
            }

            //설명 화면 -> 게임 화면
            if (CountE == 2) {
                gF.isMainScreen = false;
                gF.isExplainScreen = false;
                gF.isGameScreen = true;
                gF.isFinishScreen = false;
                this.add(nameLabel);
                this.add(scoreLabel);
                this.add(levelLabel);
                this.add(highName);

                if (listAP.isEmpty()) //비행기 생성 위치 =(가로의 중점, 세로의 3분의 2 위치)
                    listAP.add(new AirPlane(PANEL_WIDTH / 2 - PLANE_WIDTH / 2, PANEL_HEIGHT / 3 * 2 - PLANE_HEIGHT / 2,PLANE_WIDTH,PLANE_HEIGHT));

                for (int i = 0; i < INDEX; i++) { //기본 생명력 = 3개
                    listH.add(new Heart(i));

                    if (INDEX >= 6) { //최대 생명력 5개
                        INDEX = 5;
                    }
                }
            }
        } else if (keycode == KeyEvent.VK_ESCAPE) { //ESC 키를 통해 종료
            System.exit(0);
        }
    }


    //키보드를 눌렀다 떼었을 때 작동 중지
    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();

        if (keycode == KeyEvent.VK_UP) {
            moveUp = false;
        }
        if (keycode == KeyEvent.VK_DOWN) {
            moveDown = false;
        }
        if (keycode == KeyEvent.VK_RIGHT) {
            moveRight = false;
        }
        if (keycode == KeyEvent.VK_LEFT) {
            moveLeft = false;
        }

        if (keycode == KeyEvent.VK_SPACE) {
            checkSpace = false;
        }
    }
}


