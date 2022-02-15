
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
public class Shooting extends JFrame{

	JPanel startPanel;
	DrawPanel panel;

	static String player_name; //플레이어 이름 변수
	JLabel nameLabel;
	JLabel scoreLabel;
	JLabel levelLabel;
	JLabel highName;

	Timer t; //비행기 타이머
	Timer at; //공격 타이머


	int INDEX = 3; //생명력 카운트
	int CountE = 0; //엔터키 횟수 카운트

	int score = 0; //점수 카운트
	int level = 1; //레벨 카운트

	int count = 0; //타이머 카운트
	
	playerInfo highestPlayer; //최고득점자
	
	static File path = new File("."); //절대경로
	String root = path.getAbsolutePath(); //경로

	//이미지파일
	Image gameScreen = new ImageIcon(getClass().getClassLoader().getResource("res/gameScreen.png")).getImage(); //게임 화면
	Image explainScreen = new ImageIcon(getClass().getClassLoader().getResource("res/explainScreen.png")).getImage(); //설명화면
	Image mainScreen = new ImageIcon(getClass().getClassLoader().getResource("res/mainScreen.png")).getImage(); //게임시작 전 화면
	Image finishScreen = new ImageIcon(getClass().getClassLoader().getResource("res/finishScreen.png")).getImage(); //게임 종료 화면
	Image player = new ImageIcon(getClass().getClassLoader().getResource("res/player.png")).getImage(); //플레이어
	Image enemy1 = new ImageIcon(getClass().getClassLoader().getResource("res/enemy1.gif")).getImage(); //몬스터 1
	Image enemy2 = new ImageIcon(getClass().getClassLoader().getResource("res/enemy2.gif")).getImage(); //몬스터 2
	Image enemy3 = new ImageIcon(getClass().getClassLoader().getResource("res/enemy3.gif")).getImage(); //몬스터 3
	Image enemy4 = new ImageIcon(getClass().getClassLoader().getResource("res/enemy4.gif")).getImage(); //몬스터 4
	Image heart1 = new ImageIcon(getClass().getClassLoader().getResource("res/HEART.png")).getImage(); //하트
	Image sub = new ImageIcon(getClass().getClassLoader().getResource("res/sub.png")).getImage(); //서브 비행기
	Image[] itemList = {
			new ImageIcon(getClass().getClassLoader().getResource("res/HEART1.png")).getImage(), //아이템 하트 추가
			new ImageIcon(getClass().getClassLoader().getResource("res/SUB1.png")).getImage(), //아이템 서브 비행기
			new ImageIcon(getClass().getClassLoader().getResource("res/HEART2.png")).getImage()};  //아이템 하트 삭제


	private Image bImage;
	private Graphics screenGraphics;

	private boolean isMainScreen,isExplainScreen, isGameScreen, isFinishScreen; //화면 전환을 위한 boolean 자료형

	static int PLANE_WIDTH = 50; //비행기 가로크기
	static int PLANE_HEIGHT = 50; //비행기 세로크기
	static int PANEL_WIDTH = 720; //패널 가로 크기 
	static int PANEL_HEIGHT = 1000; //패널 세로 크기

	static int FRAME_WIDTH = PANEL_WIDTH+16; //프레임 크기 오차 계산
	static int FRAME_HEIGHT = PANEL_HEIGHT+39; //프레임 크기 오차 계산

	ArrayList<AirPlane> listAP = new ArrayList<AirPlane>(); //비행기 객체 
	ArrayList<A_Attack> listAT = new ArrayList<A_Attack>(); //공격 객체
	ArrayList<Attack1> listAM1 = new ArrayList<Attack1>(); //적이 공격하는 객체
	ArrayList<Attack2> listAM2 = new ArrayList<Attack2>(); //적이 공격하는 객체
	ArrayList<Monster1> listM1 = new ArrayList<Monster1>();//적1 객체
	ArrayList<Monster2> listM2 = new ArrayList<Monster2>();//적2 객체
	ArrayList<Monster3> listM3 = new ArrayList<Monster3>(); //적3 객체
	ArrayList<Monster4> listM4 = new ArrayList<Monster4>();  //적4 객체
	ArrayList<Item> listI = new ArrayList<Item>(); //아이템 객체
	ArrayList<Heart> listH = new ArrayList<Heart>(); // 하트 객체
	ArrayList<players> listN = new ArrayList<players>();//플레이어 이름 객체
	ArrayList<SubPlane> listSP = new ArrayList<SubPlane>(); //서브 비행기 객체
	ArrayList<playerInfo> highScoreList = new ArrayList<>(); //최고 득점자 객체
	
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
	ArrayList<AirPlane>APRemove = new ArrayList<>();
	
	//키보드 움직임 자연스럽게 하기 위함
	boolean moveUp = false;
	boolean moveDown = false;
	boolean moveRight = false;
	boolean moveLeft = false;
	boolean checkSpace = false;

	 //배경 음악
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
					
					highScoreList.add(new playerInfo(fileName, fileScore));
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
			for(playerInfo pi : highScoreList) {
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

	// 생성자 - Frame 기본설정, 패널 추가
	Shooting() {			
		
		//파일 불러오기
		try {
			FILERead();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("READ ERROR");
		}
		
		//파일 비교
		if(!highScoreList.isEmpty()) {
			highestPlayer = highScoreList.get(0);
			if(highScoreList.size()>=2) {
				for(int i = 1; i<highScoreList.size(); i++) {
					if(highestPlayer.compareTo(highScoreList.get(i))>0)
						highestPlayer = highScoreList.get(i);
				}
			}
		}
		
		//이름 저장
		nameInput();

		//타이머 생성
		t = new Timer(10, new Draw());
		at = new Timer(100,new MakeA());

		//패널
		panel = new DrawPanel();
		panel.addKeyListener(panel);

		nameLabel = new JLabel("NAME: " + player_name);
		scoreLabel = new JLabel("SCORE: " + score);
		levelLabel = new JLabel("LEVEL: " + level);

		if(!highScoreList.isEmpty()) { //최고득점자가 있을 때 
			highName = new JLabel("HIGHEST: " + highestPlayer.getName() + ", " + highestPlayer.get());
			highName.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
			highName.setBounds(500, 920, 200, 30);
			highName.setHorizontalAlignment(JLabel.CENTER);
			highName.setForeground(Color.white);
			
		}else { //최고득점자가 없을 때 
			highName = new JLabel("HIGHEST:"+" "+" NULL , NULL");
			highName.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
			highName.setBounds(500, 920, 200, 30);
			highName.setHorizontalAlignment(JLabel.CENTER);
			highName.setForeground(Color.white);
		}
		
		//라벨 글씨체
		nameLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
		scoreLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
		levelLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
		
		panel.setLayout(null);

		//위치 선정//setBounds(x,y,가로,세로) 절대위치
		nameLabel.setBounds(370, 950, 100, 30);
		scoreLabel.setBounds(490, 950, 100, 30); 
		levelLabel.setBounds(610, 950, 100, 30);
		

		//가운데 정렬
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		levelLabel.setHorizontalAlignment(JLabel.CENTER);

		//색 선정
		nameLabel.setForeground(Color.white);
		scoreLabel.setForeground(Color.white);
		levelLabel.setForeground(Color.white);

		this.add(panel);

		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SHOOTING");
		this.setResizable(false);

		init(); //boolean형을 통해 mainScreen그려주기
		name(); //이름 저장
		bgm(); //배경음악
	}

	//boolean형을 통해 mainScreen그려주기
	public void init() {
		isMainScreen = true;
		isExplainScreen = false;
		isGameScreen = false;
		isFinishScreen = false;
	}

	//이름 저장
	public void name() {
		listN.add(new players(player_name));
	}
	
	//화면 전환
	public void screenDraw(Graphics g) { 
		if(isMainScreen==true) {
			g.drawImage(mainScreen,0,0,null);
		}

		if(isExplainScreen == true) {
			g.drawImage(explainScreen, 0, 0, null);
		}

		if(isGameScreen==true) {
			g.drawImage(gameScreen,0,0,null);
		}

		if(isFinishScreen == true) {
			g.drawImage(finishScreen, 0, 0, null);
		}
	}

	// 시작 전 사용자 이름 받기
	public void nameInput() { 
		for(;;) {
			player_name = (String)JOptionPane.showInputDialog(this, "플레이어의 이름을 입력하세요(3자 이내)", "SHOOTING GAME", JOptionPane.PLAIN_MESSAGE);
			if(player_name == null)
				System.exit(0);
			else if(player_name.length() > 3)
				player_name = (String)JOptionPane.showInputDialog(this, "플레이어의 이름을 입력하세요(3자 이내)", "SHOOTING GAME", JOptionPane.PLAIN_MESSAGE);
			else if(player_name.length() <= 3) {
				break;
			}
		}
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

			// 비행기의 움직임
			for(AirPlane ap : listAP) { 
				//방향키로 움직임 제어
				if(moveUp == true) {
					if(ap.y - 10 > 0)
						ap.moveUP();
				}
				else if(moveDown == true) {
					if(ap.y + PLANE_HEIGHT < PANEL_HEIGHT)
						ap.moveDOWN();
				}
				else if(moveRight == true) {
					if(ap.x + PLANE_WIDTH < PANEL_WIDTH)
						ap.moveRIGHT();
				}
				else if(moveLeft == true) {
					if(ap.x > 0)
						ap.moveLEFT();
				}

				if(checkSpace == true){ //스페이스바를 통한 공격
					at.start();
				}
				else if(checkSpace == false) {
					at.stop();
				}
			}


			//몬스터 list 추가
			if(count % 100 == 0) {
				x = (int)(Math.random()*690);
				listM1.add(new Monster1(x,0, enemy1));
			}

			if(count % 200 == 0) {
				x = (int)(Math.random()*684);
				y = (int)(Math.random()*500);
				listM2.add(new Monster2(x,y, enemy2));
			}

			if(count % 250 == 0 && level > 1) {
				x = (int)(Math.random()*360);
				y = (int)(Math.random()*500);
				listM3.add(new Monster3(x,y, enemy3));
			}

			if(count % 300 == 0 && level > 1) {
				x = (int)(Math.random()*620);
				y = (int)(Math.random()*500);
				listM4.add(new Monster4(x,y,enemy4));
			}

			//몬스터가 하는 공격 list추가
			if(count % 200 == 0 && CountE > 1) {
				for(Monster1 M1 : listM1) {
					x = M1.getX();
					y = M1.getY();
					listAM1.add(new Attack1(x, y, 4, 20, 30, Color.red));
				}
				if(level>1) {
					for(Monster3 M3 : listM3) {
						x = M3.getX();
						y = M3.getY();
						listAM2.add(new Attack2(x, y, 4, 20, 45, Color.blue));
					}
				}
			}
		}
	}

	class MakeA implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int gX;
			int gY;
			count++;

			//비행기가 하는 공격 list추가
			for(AirPlane AP : listAP) {
				gX = AP.getX();
				gY = AP.getY();
				listAT.add(new A_Attack(gX, gY-20, 4, 15, 50, Color.yellow));
				if(listSP.isEmpty() != true) {
					listAT.add(new A_Attack(gX-13, gY-20, 4, 15, -10, Color.yellow));
					listAT.add(new A_Attack(gX+63, gY-20, 4, 15, 10, Color.yellow));

					//보조비행기가 하는 공격 list추가
					if(count % 80 == 0) {
						for(SubPlane SP : listSP) {
							SPRemove.add(SP);
						}
						for(SubPlane SP: SPRemove) {
							listSP.remove(SP);
						}
					}
				}
			}
		}
	}


	class DrawPanel extends JPanel implements KeyListener {
		public void paintComponent(Graphics g) {

			bImage = createImage(PANEL_WIDTH, PANEL_HEIGHT); //화면 채우기
			screenGraphics = bImage.getGraphics();
			screenDraw(screenGraphics);
			g.drawImage(bImage,0,0,null);

			//생명력이 0이 되었을때 게임 종료 화면
			if(INDEX == 0) {
				isGameScreen = false;
				isFinishScreen = true;
							
				for(A_Attack a : listAT) {ATRemove.add(a);}
				for(Attack1 a : listAM1) {MARemove1.add(a);}
				for(Attack2 a : listAM2) {MARemove2.add(a);}
				for(Monster1 a : listM1) {MRemove1.add(a);}
				for(Monster2 a : listM2) {MRemove2.add(a);}
				for(Monster3 a : listM3) {MRemove3.add(a);}
				for(Monster4 a : listM4) {MRemove4.add(a);}
				for(Heart a : listH) {HRemove.add(a);}
				for(Item a : listI) {IRemove.add(a);}
				for(AirPlane a : listAP) {APRemove.add(a);}
				
				for(A_Attack a : ATRemove) 
					listAT.remove(a);
				for(Attack1 a : MARemove1) 
					listAM1.remove(a);
				for(Attack2 a : MARemove2) 
					listAM2.remove(a);
				for(Monster1 a : MRemove1)
					listM1.remove(a);
				for(Monster2 a : MRemove2) 
					listM2.remove(a);
				for(Monster3 a : MRemove3)
					listM3.remove(a);
				for(Monster4 a : MRemove4) 
					listM4.remove(a);
				for(Item a : IRemove)
					listI.remove(a);
				for(SubPlane a : SPRemove) 
					listSP.remove(a);
				for(Heart a : HRemove) 
					listH.remove(a);
				for(AirPlane a : APRemove)
					listAP.remove(a);

				nameLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
				scoreLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
				levelLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
				highName.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
				
				//위치 재정렬
				nameLabel.setBounds(210,600,300,100);
				scoreLabel.setBounds(210, 700, 300, 100); //setBounds(x,y,가로,세로) 절대위치
				levelLabel.setBounds(210, 800, 300, 100);
				highName.setBounds(110, 500, 500, 100);

				nameLabel.setHorizontalAlignment(JLabel.CENTER);
				scoreLabel.setHorizontalAlignment(JLabel.CENTER);
				levelLabel.setHorizontalAlignment(JLabel.CENTER);
				highName.setHorizontalAlignment(JLabel.CENTER);
				
				highScoreList.add(new playerInfo(player_name, score));
				
				t.stop();
				
				//파일 쓰기 실행
				try {
					RecordWrite();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//finishScreen 화면 그려주기
				g.drawImage(finishScreen, 0, 0, 720, 1000, null);

			}

			//15점 이상 = level++
			if(score >= 0)
				level = 1;
			if(score >= 15)
				level = 2;



			//객체 생성
			//하트 그림 그리기
			for(Heart h : listH) {
				h.drawH(g);
			}
			
			//비행기 그림 그리기
			for(AirPlane ap1 : listAP) { 
				ap1.draw(g);
			}
			
			//공격 그림그리기
			
			for(A_Attack a : listAT) {
				if(a.getY()<0)
					ATRemove.add(a);

				if(listAT.isEmpty() != true) {
					a.draw(g);
					a.move();
				}
			}

			//몬스터 1 공격
			if(listAM1.isEmpty() != true)
				for(Attack1 ma : listAM1) {
					ma.draw(g);
					ma.move();
				}
			
			//몬스터 2 공격
			if(listAM2.isEmpty() != true)
				for(Attack2 ma : listAM2) {
					ma.draw(g);
					ma.move();
				}

			//비행기가 몬스터 공격에 맞았을때, 생명력--, 몬스터 사라짐
			for(AirPlane ap1 : listAP) {
				for(Attack1 ma: listAM1) {
					if(ma.getY()>=ap1.getY() && ma.getX()>=ap1.getX() && ma.getX()<=ap1.getX()+50 && ma.getY() < ap1.getY() + 50) {
						MARemove1.add(ma);
						HRemove.add(listH.get(INDEX-1));
						INDEX--;
					}
				}

				for(Attack2 ma: listAM2) {
					if(ma.getY()>=ap1.getY() && ma.getX() >= ap1.getX() && ma.getX() <= ap1.getX()+50 && ma.getY() < ap1.getY() + 50) {
						MARemove2.add(ma);
						HRemove.add(listH.get(INDEX-1));
						INDEX--;
					}
				}


				// 몬스터가 비행기의 공격을 받았을때, hp--, hp=0 -> 3분의 확률 아이템 생성, 아이템의 종류는 3가지
				for(Monster1 m : listM1) {
					for(A_Attack a: listAT) {

						if(m.distance(a.getX()+2,a.getY())<15.5) {
							ATRemove.add(a);
							m.reduceHp();

							if(m.getHp()<=0) {
								score++;
								MRemove1.add(m);
								boom();

								//아이템 3분의 1의 확률로 생성
								int rand = (int)(Math.random()*3);
								if(rand == 2) {
									int rand2 = (int)(Math.random()*3);
									if(rand2 == 0) 
										listI.add(new Item(m.getX(), m.getY(), rand2, itemList[rand2])); //하트 추가
									else if (rand2 == 1)
										listI.add(new Item(m.getX(), m.getY(), rand2, itemList[rand2])); //서브 비행기 추가
									else if(rand2 == 2)
										listI.add(new Item(m.getX(), m.getY(), rand2, itemList[rand2])); //하트 삭제
								}
							}
						}
					}
				}
				
				for(Monster2 m2 : listM2) { 
					for(A_Attack a: listAT) {

						if(m2.distance(a.getX()+2,a.getY())<18.5) {
							ATRemove.add(a);
							m2.reduceHp();

							if(m2.getHp()<=0) {
								score++;
								MRemove2.add(m2);
								boom();

								int rand = (int)(Math.random()*3); //0~4
								if(rand == 2) {
									int rand2 = (int)(Math.random()*3);
									if(rand2 == 0) 
										listI.add(new Item(m2.getX(), m2.getY(), rand2, itemList[rand2]));
									else if (rand2 == 1)
										listI.add(new Item(m2.getX(), m2.getY(), rand2, itemList[rand2]));

									else if(rand2 == 2)
										listI.add(new Item(m2.getX(), m2.getY(), rand2, itemList[rand2]));
								}
							}
						}
					}
				}

				for(Monster3 m3 : listM3) {
					for(A_Attack a: listAT) {
						if(m3.distance(a.getX()+2,a.getY())<23) {
							ATRemove.add(a);
							m3.reduceHp();

							if(m3.getHp()<=0) {
								score++;
								MRemove3.add(m3);
								boom();

								int rand = (int)(Math.random()*3); //0~4
								if(rand == 2) {
									int rand2 = (int)(Math.random()*3);
									if(rand2 == 0) 
										listI.add(new Item(m3.getX(), m3.getY(), rand2, itemList[rand2]));
									else if (rand2 == 1)
										listI.add(new Item(m3.getX(), m3.getY(), rand2, itemList[rand2]));
									else if(rand2 == 2)
										listI.add(new Item(m3.getX(), m3.getY(), rand2, itemList[rand2]));
								}

							}
						}
					}
				}

				for(Monster4 m4 : listM4) {
					for(A_Attack a: listAT) {
						if(m4.distance(a.getX()+2,a.getY())<25.5) {
							ATRemove.add(a);
							m4.reduceHp();

							if(m4.getHp()<=0) {
								score++;
								boom();
								MRemove4.add(m4);

								int rand = (int)(Math.random()*3); //0~4
								if(rand == 2) {
									int rand2 = (int)(Math.random()*3);
									if(rand2 == 0) 
										listI.add(new Item(m4.getX(), m4.getY(), rand2, itemList[rand2]));
									else if (rand2 == 1)
										listI.add(new Item(m4.getX(), m4.getY(), rand2, itemList[rand2]));
									else if(rand2 == 2)
										listI.add(new Item(m4.getX(), m4.getY(), rand2, itemList[rand2]));
								}
							}
						}
					}
				}

				//아이템 생성
				for(Item item: listI) {
					item.draw(g);
					item.move();
					
					//거리 계산
					if(ap1.distance(item.getXI(), item.getYI()) <= 40) {
						IRemove.add(item);
						if(item.getType()==0) { //첫번째 아이템인 생명력 추가하기
							listH.add(new Heart(INDEX));
							if(INDEX<5)
								INDEX++; //생명력 추가

						}else if(item.getType() == 1 && listSP.isEmpty()) { // 두번째 아이템인 보조비행기 추가하기
							listSP.add(new SubPlane(listAP.get(0).getX(), listAP.get(0).getY()));

						}else if(item.getType() == 2) { //세번째 아이템인 생명력 감소하기
							HRemove.add(listH.get(INDEX-1));
							INDEX--; //생명력 감소
						}
					}
					
					if(item.getYI() >= 980)
						IRemove.add(item);
				}
			}

			//비행기와 몬스터가 맞닿으면 점수 감점
			for(AirPlane AP : listAP) {
				for(Monster1 m : listM1) {
					m.draw(g);
					m.move();
					if(AP.distance(m.getX()+15,m.getY()+15)<=31) {
						MRemove1.add(m);
						score--;
					}
				}

				for(Monster2 m2 : listM2) {
					m2.draw(g);
					m2.move();

					if(AP.distance(m2.getX()+18,m2.getY()+18)<=44) {
						MRemove2.add(m2);
						score--;
					}
				}

				for(Monster3 m3 : listM3) {
					if(level > 1) {
						m3.draw(g);
						m3.move();

						if(AP.distance(m3.getX()+22,m3.getY()+22)<=48) {
							MRemove3.add(m3);
							score--;
						}
					}
				}

				for(Monster4 m4 : listM4) {
					if(level>1) {
						m4.draw(g);
						m4.move();

						if(AP.distance(m4.getX()+25,m4.getY()+25)<=51) {
							MRemove4.add(m4);
							score--;
						}
					}
				}
			}
			
			//보조 비행기 추가
			for(SubPlane sp : listSP) {
				sp.draw(g);
			}
			
			//score과 level 새로고침
			scoreLabel.setText("SCORE: " + score);
			levelLabel.setText("LEVEL: " +level);
			
			
			//제거
			for(A_Attack a : ATRemove) 
				listAT.remove(a);
			for(Attack1 a : MARemove1) 
				listAM1.remove(a);
			for(Attack2 a : MARemove2) 
				listAM2.remove(a);
			for(Monster1 a : MRemove1)
				listM1.remove(a);
			for(Monster2 a : MRemove2) 
				listM2.remove(a);
			for(Monster3 a : MRemove3)
				listM3.remove(a);
			for(Monster4 a : MRemove4) 
				listM4.remove(a);
			for(Item a : IRemove)
				listI.remove(a);
			for(SubPlane a : SPRemove) 
				listSP.remove(a);
			for(Heart a : HRemove) 
				listH.remove(a);
			for(AirPlane a : APRemove)
				listAP.remove(a);

			setFocusable(true);
			requestFocus();
		}



		@Override
		public void keyTyped(KeyEvent e) {}
		
		//비행기를 방향키로 이동
		@Override
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
			
			//방향 키보드를 통해 움직임 조정
			if(keycode == KeyEvent.VK_UP) {
				moveUp = true;
			}
			else if(keycode == KeyEvent.VK_DOWN) {
				moveDown = true;
			}
			else if(keycode == KeyEvent.VK_RIGHT) {
				moveRight = true;
			}
			else if(keycode == KeyEvent.VK_LEFT) {
				moveLeft = true;
			}
			if(keycode == KeyEvent.VK_SPACE) {
				checkSpace = true;
			}
			
			//엔터키를 통해 화면 바꿈
			if(keycode == KeyEvent.VK_ENTER) {
				CountE++;
				
				//시작 화면 -> 설명 화면
				if(CountE == 1) { 
					isMainScreen = false;
					isExplainScreen = true;
					isGameScreen = false;
					isFinishScreen = false;
					t.start();
				}
				
				//설명 화면 -> 게임 화면 
				if(CountE == 2) { 
					isMainScreen = false;
					isExplainScreen = false;
					isGameScreen = true;
					isFinishScreen = false;
					panel.add(nameLabel);
					panel.add(scoreLabel);
					panel.add(levelLabel);
					panel.add(highName);
					
					if(listAP.isEmpty()) //비행기 생성 위치 =(가로의 중점, 세로의 3분의 2 위치)
						listAP.add(new AirPlane(PANEL_WIDTH/2-PLANE_WIDTH/2, PANEL_HEIGHT/3*2-PLANE_HEIGHT/2));
					
					for(int i = 0; i< INDEX; i++ ) { //기본 생명력 = 3개
						listH.add(new Heart(i));

						if(INDEX >= 6) { //최대 생명력 5개
							INDEX = 5;
						}
					}
				}	
			}
			else if (keycode == KeyEvent.VK_ESCAPE) { //ESC 키를 통해 종료
				System.exit(0);
			}
		}
		
		
		//키보드를 눌렀다 떼었을 때 작동 중지
		@Override
		public void keyReleased(KeyEvent e) { 
			int keycode = e.getKeyCode();

			if(keycode==KeyEvent.VK_UP) {
				moveUp = false;
			}
			if(keycode==KeyEvent.VK_DOWN) {
				moveDown = false;
			}
			if(keycode==KeyEvent.VK_RIGHT) {
				moveRight = false;
			}
			if(keycode==KeyEvent.VK_LEFT) {
				moveLeft = false;
			}

			if(keycode == KeyEvent.VK_SPACE) {
				checkSpace=false;	
			}
		}
	}
	
	//비행기
	class AirPlane extends ImageIcon{
		int x;
		int y;
		int w;
		int h;

		int moveX = 10;
		int moveY = 10;

		AirPlane(int posX, int posY){
			x = posX;
			y = posY;
			w=PLANE_WIDTH;
			h=PLANE_HEIGHT;
		}

		public void draw(Graphics g) {
			g.drawImage(player,x,y,w,h, null);
		}

		public void moveUP() {
			y -= moveY;
		}
		public void moveDOWN() {
			y += moveY;
		}
		public void moveRIGHT() {
			x += moveX;
		}
		public void moveLEFT() {
			x -= moveX;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}

		public double distance(int x, int y) { //중점과의 거리
			return Math.sqrt(Math.pow((this.x+w/2)-x, 2) + Math.pow((this.y+h/2)-y, 2));
		}

	}

	//서브 비행기
	class SubPlane extends ImageIcon{
		int x;
		int y;
		int w;
		int h;

		SubPlane(int posX, int posY){
			x = posX;
			y = posY;
			w = 30;
			h = 30;
		}

		public void draw(Graphics g) { //서브 비행기 위치 설정
			g.drawImage(sub,listAP.get(0).getX()-35,listAP.get(0).getY()+10,w,h,null);
			g.drawImage(sub,listAP.get(0).getX()+55,listAP.get(0).getY()+10,w,h,null);
		}

		public int getX() {
			return this.x;
		}
		public int getY() {
			return this.y;
		}

	}
	/*
	//비행기가 공격
	class Attack {
		int pX;
		int pY;
		int width;
		int height;

		Attack(int x, int y){
			pX = x;
			pY = y;
			width = 4;
			height = 20;
		}

		public void moveA() {
			pY -= 10;
		}

		public void drawA1(Graphics g2) { //비행기가 공격할때
			g2.setColor(Color.yellow);
			g2.fillRect(pX,pY,width,height);
		}

		public int getY() {
			return pY;
		}

		public int getX() {
			return pX;
		}

	}

	
	//몬스터 1이 하는 공격
	class MAttack1{
		int pX;
		int pY;
		int wid = 4;
		int hei = 20;
		Color color;

		MAttack1(int x, int y, Color color){
			pX = x+13;
			pY = y+20;

			this.color = color;
		}

		public void moveA() {
			pY += 15;
		}

		public void drawMA(Graphics g) {
			g.setColor(color);
			g.fillRect(pX, pY, wid, hei);
		}

		public int getY() {
			return pY;
		}

		public int getX() {
			return pX;
		}
	}

	//몬스터 3이 하는 공격
	class MAttack2{
		int pX;
		int pY;
		int wid = 4;
		int hei = 20;
		Color color;

		MAttack2(int x, int y, Color color){
			pX = x+20;
			pY = y+45;

			this.color = color;
		}

		public void moveA() {
			pY += 15;
		}

		public void drawMA(Graphics g) {
			g.setColor(color);
			g.fillRect(pX, pY, wid, hei);
		}

		public int getY() {
			return pY;
		}

		public int getX() {
			return pX;
		}

	}
	*/
	
	//생명력을 나타내는 하트
	class Heart{
		int index;

		Heart(int i){
			index = i;
		}

		public void drawH(Graphics g) {
			g.drawImage(heart1, 30+index*30, 950, 27, 21, null);
		}
	}
	
	//플레이어 이름
	class players{
		String name;

		players(String player_name){
			this.name = player_name;
		}

		public String name() {
			return this.name;
		}
	}
	
	//최고 득점자 비교
	class playerInfo{
		String name;
		int score;
		
		playerInfo(String name, int score){
			this.name = name;
			this.score = score;
		}
		
		public int compareTo(playerInfo playerInfo) {
			if(playerInfo.score>this.score)
				return 1;
			else
				return 0;
		}
		
		public void printInfo() {
			System.out.println("Name: " + name + ", SCORE: " + score);
		}
		public String getName() {
			return name;
		}
		public int get() {
			return score;
		}
	}

}
