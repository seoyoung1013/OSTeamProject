import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import monster.Monster1;
import monster.Monster2;
import monster.Monster3;
import monster.Monster4;
import attack.A_Attack;
import attack.Attack1;
import attack.Attack2;

@SuppressWarnings("serial")
public class Shooting extends JFrame {



	Timer t; //비행기 타이머
	Timer at; //공격 타이머
//




	int count = 0; //타이머 카운트
	//이미지파일
	 //게임시작 전 화면


	Image enemy1 = new ImageIcon(getClass().getClassLoader().getResource("res/enemy1.gif")).getImage(); //몬스터 1
	Image enemy2 = new ImageIcon(getClass().getClassLoader().getResource("res/enemy2.gif")).getImage(); //몬스터 2
	Image enemy3 = new ImageIcon(getClass().getClassLoader().getResource("res/enemy3.gif")).getImage(); //몬스터 3
	Image enemy4 = new ImageIcon(getClass().getClassLoader().getResource("res/enemy4.gif")).getImage(); //몬스터 4



	DrawPanel dP;

	static int FRAME_WIDTH = 720+ 16; //프레임 크기 오차 계산
	static int FRAME_HEIGHT = 1000 + 39; //프레임 크기 오차 계산



	// 생성자 - Frame 기본설정, 패널 추가
	Shooting() {
		dP = new DrawPanel();
		//파일 불러오기
		try {
			dP.gF.FILERead();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("READ ERROR");
		}
		//파일 비교
		if (!dP.gF.highScoreList.isEmpty()) {
			dP.gF.highestPlayer = dP.gF.highScoreList.get(0);
			if (dP.gF.highScoreList.size() >= 2) {
				for (int i = 1; i < dP.gF.highScoreList.size(); i++) {
					if (dP.gF.highestPlayer.compareTo(dP.gF.highScoreList.get(i)) > 0)
						dP.gF.highestPlayer = dP.gF.highScoreList.get(i);
				}
			}
		}
		//이름 저장
		dP.gF.nameInput();
		//타이머 생성
		t = new Timer(10, new Draw());
		at = new Timer(100, new MakeA());
		//패널

		dP.addKeyListener(dP);
		dP.nameLabel = new JLabel("NAME: " + dP.gF.player_name);
		dP.scoreLabel = new JLabel("SCORE: " + dP.score);
		dP.levelLabel = new JLabel("LEVEL: " + dP.level);

		if (!dP.gF.highScoreList.isEmpty()) { //최고득점자가 있을 때
			dP.highName = new JLabel("HIGHEST: " + dP.gF.highestPlayer.getName() + ", " + dP.gF.highestPlayer.get());
			dP.highName.setFont(new Font("FTLAB Hoony", Font.BOLD, 15));
			dP.highName.setBounds(500, 920, 200, 30);
			dP.highName.setHorizontalAlignment(JLabel.CENTER);
			dP.highName.setForeground(Color.white);
		} else { //최고득점자가 없을 때
			dP.highName = new JLabel("HIGHEST:" + " " + " NULL , NULL");
			dP.highName.setFont(new Font("FTLAB Hoony", Font.BOLD, 15));
			dP.highName.setBounds(500, 920, 200, 30);
			dP.highName.setHorizontalAlignment(JLabel.CENTER);
			dP.highName.setForeground(Color.white);
		}
		//라벨 글씨체
		dP.nameLabel.setFont(new Font("FTLAB Hoony", Font.BOLD, 15));
		dP.scoreLabel.setFont(new Font("FTLAB Hoony", Font.BOLD, 15));
		dP.levelLabel.setFont(new Font("FTLAB Hoony", Font.BOLD, 15));
		dP.setLayout(null);
		//위치 선정//setBounds(x,y,가로,세로) 절대위치
		dP.nameLabel.setBounds(370, 950, 100, 30);
		dP.scoreLabel.setBounds(490, 950, 100, 30);
		dP.levelLabel.setBounds(610, 950, 100, 30);
		//가운데 정렬
		dP.nameLabel.setHorizontalAlignment(JLabel.CENTER);
		dP.scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		dP.levelLabel.setHorizontalAlignment(JLabel.CENTER);
		//색 선정
		dP.nameLabel.setForeground(Color.white);
		dP.scoreLabel.setForeground(Color.white);
		dP.levelLabel.setForeground(Color.white);

		this.add(dP);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SHOOTING");
		this.setResizable(false);

		dP.gF.init(); //boolean형을 통해 mainScreen그려주기
		dP.gF.name(); //이름 저장
		dP.gF.bgm(); //배경음악
		t.start();
	}
	public static void main(String[] args) {
		new Shooting();
	}

	class Draw implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			repaint();
			count++;
			int x;
			int y;
			if(dP.t==false)
				t.stop();
			// 비행기의 움직임
			for (AirPlane ap : dP.listAP) {
				//방향키로 움직임 제어
				if (dP.moveUp == true) {
					if (ap.y - 10 > 0)
						ap.moveUP();
				} else if (dP.moveDown == true) {
					if (ap.y + dP.PLANE_HEIGHT < dP.PANEL_HEIGHT)
						ap.moveDOWN();
				} else if (dP.moveRight == true) {
					if (ap.x + dP.PLANE_WIDTH < dP.PANEL_WIDTH)
						ap.moveRIGHT();
				} else if (dP.moveLeft == true) {
					if (ap.x > 0)
						ap.moveLEFT();
				}

				if (dP.checkSpace == true) { //스페이스바를 통한 공격
					at.start();
				} else if (dP.checkSpace == false) {
					at.stop();
				}
			}


			//몬스터 list 추가
			if (count % 100 == 0) {
				x = (int) (Math.random() * 690);
				dP.listM1.add(new Monster1(x, 0, enemy1));
			}

			if (count % 200 == 0) {
				x = (int) (Math.random() * 684);
				y = (int) (Math.random() * 500);
				dP.listM2.add(new Monster2(x, y, enemy2));
			}

			if (count % 250 == 0 && dP.level > 1) {
				x = (int) (Math.random() * 360);
				y = (int) (Math.random() * 500);
				dP.listM3.add(new Monster3(x, y, enemy3));
			}

			if (count % 300 == 0 && dP.level > 1) {
				x = (int) (Math.random() * 620);
				y = (int) (Math.random() * 500);
				dP.listM4.add(new Monster4(x, y, enemy4));
			}

			//몬스터가 하는 공격 list추가
			if (count % 200 == 0 && dP.CountE > 1) {
				for (Monster1 M1 : dP.listM1) {
					x = M1.getX();
					y = M1.getY();
					dP.listAM1.add(new Attack1(x, y, 4, 20, 30, Color.red));
				}
				if (dP.level > 1) {
					for (Monster3 M3 : dP.listM3) {
						x = M3.getX();
						y = M3.getY();
						dP.listAM2.add(new Attack2(x, y, 4, 20, 45, Color.blue));
					}
				}
			}
		}
	}

	class MakeA implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int gX;
			int gY;
			count++;

			//비행기가 하는 공격 list추가
			for (AirPlane AP : dP.listAP) {
				gX = AP.getX();
				gY = AP.getY();
				dP.listAT.add(new A_Attack(gX, gY - 20, 4, 15, 50, Color.yellow));
				if (dP.listSP.isEmpty() != true) {
					dP.listAT.add(new A_Attack(gX - 13, gY - 20, 4, 15, -10, Color.yellow));
					dP.listAT.add(new A_Attack(gX + 63, gY - 20, 4, 15, 10, Color.yellow));

					//보조비행기가 하는 공격 list추가
					if (count % 80 == 0) {
						for (SubPlane SP : dP.listSP) {
							dP.SPRemove.add(SP);
						}
						for (SubPlane SP : dP.SPRemove) {
							dP.listSP.remove(SP);
						}
					}
				}
			}
		}
	}



	//비행기

	//서브 비행기


}

