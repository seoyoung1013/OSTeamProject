
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

@SuppressWarnings("serial")
public class Shooting extends JFrame{

	JPanel startPanel;
	DrawPanel panel;

	static String player_name; //�÷��̾� �̸� ����
	JLabel nameLabel;
	JLabel scoreLabel;
	JLabel levelLabel;
	JLabel highName;

	Timer t; //����� Ÿ�̸�
	Timer at; //���� Ÿ�̸�


	int INDEX = 3; //������ ī��Ʈ
	int CountE = 0; //����Ű Ƚ�� ī��Ʈ

	int moveYM = 3; //���� y�� ������
	int moveXM = 2; //���� x�� ������

	int score = 0; //���� ī��Ʈ
	int level = 1; //���� ī��Ʈ

	int count = 0; //Ÿ�̸� ī��Ʈ
	
	playerInfo highestPlayer; //�ְ�������
	
	static File path = new File("."); //������
	String root = path.getAbsolutePath(); //���

	//�̹�������
	Image gameScreen = new ImageIcon(getClass().getClassLoader().getResource("gameScreen.png")).getImage(); //���� ȭ��
	Image explainScreen = new ImageIcon(getClass().getClassLoader().getResource("explainScreen.png")).getImage(); //����ȭ��
	Image mainScreen = new ImageIcon(getClass().getClassLoader().getResource("mainScreen.png")).getImage(); //���ӽ��� �� ȭ��
	Image finishScreen = new ImageIcon(getClass().getClassLoader().getResource("finishScreen.png")).getImage(); //���� ���� ȭ��
	Image player = new ImageIcon(getClass().getClassLoader().getResource("player.png")).getImage(); //�÷��̾�
	Image enemy1 = new ImageIcon(getClass().getClassLoader().getResource("enemy1.gif")).getImage(); //���� 1
	Image enemy2 = new ImageIcon(getClass().getClassLoader().getResource("enemy2.gif")).getImage(); //���� 2
	Image enemy3 = new ImageIcon(getClass().getClassLoader().getResource("enemy3.gif")).getImage(); //���� 3
	Image enemy4 = new ImageIcon(getClass().getClassLoader().getResource("enemy4.gif")).getImage(); //���� 4
	Image heart1 = new ImageIcon(getClass().getClassLoader().getResource("HEART.png")).getImage(); //��Ʈ
	Image sub = new ImageIcon(getClass().getClassLoader().getResource("sub.png")).getImage(); //���� �����
	Image[] item = {
			new ImageIcon(getClass().getClassLoader().getResource("HEART1.png")).getImage(), //������ ��Ʈ �߰�
			new ImageIcon(getClass().getClassLoader().getResource("SUB1.png")).getImage(), //������ ���� ����� 
			new ImageIcon(getClass().getClassLoader().getResource("HEART2.png")).getImage()};  //������ ��Ʈ ����


	private Image bImage;
	private Graphics screenGraphics;

	private boolean isMainScreen,isExplainScreen, isGameScreen, isFinishScreen; //ȭ�� ��ȯ�� ���� boolean �ڷ���

	static int PLANE_WIDTH = 50; //����� ����ũ��
	static int PLANE_HEIGHT = 50; //����� ����ũ��
	static int PANEL_WIDTH = 720; //�г� ���� ũ�� 
	static int PANEL_HEIGHT = 1000; //�г� ���� ũ��

	static int FRAME_WIDTH = PANEL_WIDTH+16; //������ ũ�� ���� ���
	static int FRAME_HEIGHT = PANEL_HEIGHT+39; //������ ũ�� ���� ���

	ArrayList<AirPlane> listAP = new ArrayList<AirPlane>(); //����� ��ü 
	ArrayList<Attack> listAT = new ArrayList<Attack>(); //���� ��ü
	ArrayList<MAttack1> listAM1 = new ArrayList<MAttack1>(); //���� �����ϴ� ��ü
	ArrayList<MAttack2> listAM2 = new ArrayList<MAttack2>(); //���� �����ϴ� ��ü
	ArrayList<Monster> listM1 = new ArrayList<Monster>();//��1 ��ü
	ArrayList<Monster2> listM2 = new ArrayList<Monster2>();//��2 ��ü
	ArrayList<Monster3> listM3 = new ArrayList<Monster3>(); //��3 ��ü
	ArrayList<Monster4> listM4 = new ArrayList<Monster4>();  //��4 ��ü
	ArrayList<ITEM> listI = new ArrayList<ITEM>(); //������ ��ü
	ArrayList<Heart> listH = new ArrayList<Heart>(); // ��Ʈ ��ü
	ArrayList<players> listN = new ArrayList<players>();//�÷��̾� �̸� ��ü
	ArrayList<SubPlane> listSP = new ArrayList<SubPlane>(); //���� ����� ��ü
	ArrayList<playerInfo> highScoreList = new ArrayList<>(); //�ְ� ������ ��ü
	
	//���� ��ü
	ArrayList<Attack> ATRemove = new ArrayList<>(); 
	ArrayList<MAttack1> MARemove1 = new ArrayList<>();
	ArrayList<MAttack2> MARemove2 = new ArrayList<>();
	ArrayList<Monster> MRemove1 = new ArrayList<>();
	ArrayList<Monster2> MRemove2 = new ArrayList<>();
	ArrayList<Monster3> MRemove3 = new ArrayList<>();
	ArrayList<Monster4> MRemove4 = new ArrayList<>();
	ArrayList<ITEM> IRemove = new ArrayList<>();
	ArrayList<SubPlane> SPRemove = new ArrayList<>();
	ArrayList<Heart> HRemove = new ArrayList<>();
	ArrayList<AirPlane>APRemove = new ArrayList<>();
	
	//Ű���� ������ �ڿ������� �ϱ� ����
	boolean moveUp = false;
	boolean moveDown = false;
	boolean moveRight = false;
	boolean moveLeft = false;
	boolean checkSpace = false;

	 //��� ����
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

	 // ���Ͱ� ���� �� ���� �Ҹ�
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
	
	// ���� �б�
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
	
	//���� ����
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

	// ������ - Frame �⺻����, �г� �߰�
	Shooting() {			
		
		//���� �ҷ�����
		try {
			FILERead();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("READ ERROR");
		}
		
		//���� ��
		if(!highScoreList.isEmpty()) {
			highestPlayer = highScoreList.get(0);
			if(highScoreList.size()>=2) {
				for(int i = 1; i<highScoreList.size(); i++) {
					if(highestPlayer.compareTo(highScoreList.get(i))>0)
						highestPlayer = highScoreList.get(i);
				}
			}
		}
		
		//�̸� ����
		nameInput();

		//Ÿ�̸� ����
		t = new Timer(10, new Draw());
		at = new Timer(100,new MakeA());

		//�г�
		panel = new DrawPanel();
		panel.addKeyListener(panel);

		nameLabel = new JLabel("NAME: " + player_name);
		scoreLabel = new JLabel("SCORE: " + score);
		levelLabel = new JLabel("LEVEL: " + level);

		if(!highScoreList.isEmpty()) { //�ְ������ڰ� ���� �� 
			highName = new JLabel("HIGHEST: " + highestPlayer.getName() + ", " + highestPlayer.get());
			highName.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
			highName.setBounds(500, 920, 200, 30);
			highName.setHorizontalAlignment(JLabel.CENTER);
			highName.setForeground(Color.white);
			
		}else { //�ְ������ڰ� ���� �� 
			highName = new JLabel("HIGHEST:"+" "+" NULL , NULL");
			highName.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
			highName.setBounds(500, 920, 200, 30);
			highName.setHorizontalAlignment(JLabel.CENTER);
			highName.setForeground(Color.white);
		}
		
		//�� �۾�ü
		nameLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
		scoreLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
		levelLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
		
		panel.setLayout(null);

		//��ġ ����//setBounds(x,y,����,����) ������ġ
		nameLabel.setBounds(370, 950, 100, 30);
		scoreLabel.setBounds(490, 950, 100, 30); 
		levelLabel.setBounds(610, 950, 100, 30);
		

		//��� ����
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		levelLabel.setHorizontalAlignment(JLabel.CENTER);

		//�� ����
		nameLabel.setForeground(Color.white);
		scoreLabel.setForeground(Color.white);
		levelLabel.setForeground(Color.white);

		this.add(panel);

		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SHOOTING");
		this.setResizable(false);

		init(); //boolean���� ���� mainScreen�׷��ֱ�
		name(); //�̸� ����
		bgm(); //�������
	}

	//boolean���� ���� mainScreen�׷��ֱ�
	public void init() {
		isMainScreen = true;
		isExplainScreen = false;
		isGameScreen = false;
		isFinishScreen = false;
	}

	//�̸� ����
	public void name() {
		listN.add(new players(player_name));
	}
	
	//ȭ�� ��ȯ
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

	// ���� �� ����� �̸� �ޱ�
	public void nameInput() { 
		for(;;) {
			player_name = (String)JOptionPane.showInputDialog(this, "�÷��̾��� �̸��� �Է��ϼ���(3�� �̳�)", "SHOOTING GAME", JOptionPane.PLAIN_MESSAGE);
			if(player_name == null)
				System.exit(0);
			else if(player_name.length() > 3)
				player_name = (String)JOptionPane.showInputDialog(this, "�÷��̾��� �̸��� �Է��ϼ���(3�� �̳�)", "SHOOTING GAME", JOptionPane.PLAIN_MESSAGE);
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

			// ������� ������
			for(AirPlane ap : listAP) { 
				//����Ű�� ������ ����
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

				if(checkSpace == true){ //�����̽��ٸ� ���� ����
					at.start();
				}
				else if(checkSpace == false) {
					at.stop();
				}
			}


			//���� list �߰�
			if(count % 100 == 0) {
				x = (int)(Math.random()*690);
				listM1.add(new Monster(x,0));
			}

			if(count % 200 == 0) {
				x = (int)(Math.random()*684);
				y = (int)(Math.random()*500);
				listM2.add(new Monster2(x,y));
			}

			if(count % 250 == 0) {
				x = (int)(Math.random()*360);
				y = (int)(Math.random()*500);
				listM3.add(new Monster3(x,y));
			}

			if(count % 300 == 0) {
				x = (int)(Math.random()*620);
				y = (int)(Math.random()*500);
				listM4.add(new Monster4(x,y));
			}


			//���Ͱ� �ϴ� ���� list�߰�
			if(count % 200 == 0) {
				for(Monster M1 : listM1) {
					x = M1.getX();
					y = M1.getY();
					listAM1.add(new MAttack1(x, y, Color.red));
				}
				if(level>1) {
					for(Monster3 M3 : listM3) {
						x = M3.getX();
						y = M3.getY();
						listAM2.add(new MAttack2(x, y, Color.blue));
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

			//����Ⱑ �ϴ� ���� list�߰�
			for(AirPlane AP : listAP) {
				gX = AP.getX();
				gY = AP.getY();
				listAT.add(new Attack(gX+23,gY-20));
				if(listSP.isEmpty() != true) {
					listAT.add(new Attack(gX-13, gY-20));
					listAT.add(new Attack(gX+63, gY-20));

					//��������Ⱑ �ϴ� ���� list�߰�
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

			bImage = createImage(PANEL_WIDTH, PANEL_HEIGHT); //ȭ�� ä���
			screenGraphics = bImage.getGraphics();
			screenDraw(screenGraphics);
			g.drawImage(bImage,0,0,null);

			//�������� 0�� �Ǿ����� ���� ���� ȭ��
			if(INDEX == 0) {
				isGameScreen = false;
				isFinishScreen = true;
							
				for(Attack a : listAT) {ATRemove.add(a);}
				for(MAttack1 a : listAM1) {MARemove1.add(a);}
				for(MAttack2 a : listAM2) {MARemove2.add(a);}
				for(Monster a : listM1) {MRemove1.add(a);}
				for(Monster2 a : listM2) {MRemove2.add(a);}
				for(Monster3 a : listM3) {MRemove3.add(a);}
				for(Monster4 a : listM4) {MRemove4.add(a);}
				for(Heart a : listH) {HRemove.add(a);}
				for(ITEM a : listI) {IRemove.add(a);}
				for(AirPlane a : listAP) {APRemove.add(a);}
				
				for(Attack a : ATRemove) 
					listAT.remove(a);
				for(MAttack1 a : MARemove1) 
					listAM1.remove(a);
				for(MAttack2 a : MARemove2) 
					listAM2.remove(a);
				for(Monster a : MRemove1) 
					listM1.remove(a);
				for(Monster2 a : MRemove2) 
					listM2.remove(a);
				for(Monster3 a : MRemove3)
					listM3.remove(a);
				for(Monster4 a : MRemove4) 
					listM4.remove(a);
				for(ITEM a : IRemove) 
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
				
				//��ġ ������
				nameLabel.setBounds(210,600,300,100);
				scoreLabel.setBounds(210, 700, 300, 100); //setBounds(x,y,����,����) ������ġ
				levelLabel.setBounds(210, 800, 300, 100);
				highName.setBounds(110, 500, 500, 100);

				nameLabel.setHorizontalAlignment(JLabel.CENTER);
				scoreLabel.setHorizontalAlignment(JLabel.CENTER);
				levelLabel.setHorizontalAlignment(JLabel.CENTER);
				highName.setHorizontalAlignment(JLabel.CENTER);
				
				highScoreList.add(new playerInfo(player_name, score));
				
				t.stop();
				
				//���� ���� ����
				try {
					RecordWrite();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//finishScreen ȭ�� �׷��ֱ�
				g.drawImage(finishScreen, 0, 0, 720, 1000, null);

			}

			//15�� �̻� = level++
			if(score >= 0)
				level = 1;
			if(score >= 15)
				level = 2;



			//��ü ����
			//��Ʈ �׸� �׸���
			for(Heart h : listH) {
				h.drawH(g);
			}
			
			//����� �׸� �׸���
			for(AirPlane ap1 : listAP) { 
				ap1.draw(g);
			}
			
			//���� �׸��׸���
			
			for(Attack a : listAT) {
				if(a.getY()<0)
					ATRemove.add(a);

				if(listAT.isEmpty() != true) {
					a.drawA1(g);
					a.moveA();
				}
			}

			//���� 1 ����
			if(listAM1.isEmpty() != true)
				for(MAttack1 ma : listAM1) {
					ma.drawMA(g);
					ma.moveA();
				}
			
			//���� 2 ����
			if(listAM2.isEmpty() != true)
				for(MAttack2 ma : listAM2) {
					ma.drawMA(g);
					ma.moveA();
				}

			//����Ⱑ ���� ���ݿ� �¾�����, ������--, ���� �����
			for(AirPlane ap1 : listAP) {
				for(MAttack1 ma: listAM1) {
					if(ma.getY()>=ap1.getY() && ma.getX()>=ap1.getX() && ma.getX()<=ap1.getX()+50 && ma.getY() < ap1.getY() + 50) {
						MARemove1.add(ma);
						HRemove.add(listH.get(INDEX-1));
						INDEX--;
					}
				}

				for(MAttack2 ma: listAM2) {
					if(ma.getY()>=ap1.getY() && ma.getX() >= ap1.getX() && ma.getX() <= ap1.getX()+50 && ma.getY() < ap1.getY() + 50) {
						MARemove2.add(ma);
						HRemove.add(listH.get(INDEX-1));
						INDEX--;
					}
				}


				// ���Ͱ� ������� ������ �޾�����, hp--, hp=0 -> 3���� Ȯ�� ������ ����, �������� ������ 3����
				for(Monster m : listM1) {
					for(Attack a: listAT) {

						if(m.distanceM(a.pX+2,a.pY)<30) {
							ATRemove.add(a);
							m.hpM1();

							if(m.HP1<=0) {
								score++;
								MRemove1.add(m);
								boom();
								
								
								//������ 3���� 1�� Ȯ���� ����
								int rand = (int)(Math.random()*3);
								if(rand == 2) {
									int rand2 = (int)(Math.random()*3);
									if(rand2 == 0) 
										listI.add(new ITEM(m.pX, m.pY,item[rand2],rand2)); //��Ʈ �߰�
									else if (rand2 == 1) 
										listI.add(new ITEM(m.pX, m.pY, item[rand2],rand2)); //���� ����� �߰�
									else if(rand2 == 2)
										listI.add(new ITEM(m.pX, m.pY, item[rand2], rand2)); //��Ʈ ����
								}
							}
						}
					}
				}

				for(Monster2 m2 : listM2) { 
					for(Attack a: listAT) {

						if(m2.distanceM(a.pX+2,a.pY)<36) {
							ATRemove.add(a);
							m2.hpM2();

							if(m2.HP2<=0) {
								score++;
								MRemove2.add(m2);
								boom();

								int rand = (int)(Math.random()*3); //0~4
								if(rand == 2) {
									int rand2 = (int)(Math.random()*3);
									if(rand2 == 0) 
										listI.add(new ITEM(m2.pX, m2.pY,item[rand2],rand2));
									else if (rand2 == 1)
										listI.add(new ITEM(m2.pX, m2.pY, item[rand2],rand2));

									else if(rand2 == 2)
										listI.add(new ITEM(m2.pX, m2.pY, item[rand2], rand2));
								}
							}
						}
					}
				}

				for(Monster3 m3 : listM3) {
					for(Attack a: listAT) {
						if(m3.distanceM(a.pX+2,a.pY)<45) {
							ATRemove.add(a);
							m3.hpM3();

							if(m3.HP3<=0) {
								score++;
								MRemove3.add(m3);
								boom();

								int rand = (int)(Math.random()*3); //0~4
								if(rand == 2) {
									int rand2 = (int)(Math.random()*3);
									if(rand2 == 0) 
										listI.add(new ITEM(m3.pX, m3.pY,item[rand2],rand2));
									else if (rand2 == 1)
										listI.add(new ITEM(m3.pX, m3.pY, item[rand2],rand2));
									else if(rand2 == 2)
										listI.add(new ITEM(m3.pX, m3.pY, item[rand2], rand2));
								}

							}
						}
					}
				}

				for(Monster4 m4 : listM4) {
					for(Attack a: listAT) {
						if(m4.distanceM(a.pX+2,a.pY)<45) {
							ATRemove.add(a);
							m4.hpM4();

							if(m4.HP4<=0) {
								score++;
								boom();
								MRemove4.add(m4);

								int rand = (int)(Math.random()*3); //0~4
								if(rand == 2) {
									int rand2 = (int)(Math.random()*3);
									if(rand2 == 0) 
										listI.add(new ITEM(m4.pX, m4.pY,item[rand2],rand2));									
									else if (rand2 == 1)
										listI.add(new ITEM(m4.pX, m4.pY, item[rand2],rand2));
									else if(rand2 == 2)
										listI.add(new ITEM(m4.pX, m4.pY, item[rand2], rand2));
								}
							}
						}
					}
				}

				//������ ����
				for(ITEM item: listI) {
					item.drawI(g);
					item.moveI();
					
					//�Ÿ� ���
					if(ap1.distance(item.getXI(), item.getYI()) <= 40) {
						IRemove.add(item);
						if(item.typenum()==0) { //ù��° �������� ������ �߰��ϱ�
							listH.add(new Heart(INDEX));
							if(INDEX<5)
								INDEX++; //������ �߰�

						}else if(item.typenum() == 1 && listSP.isEmpty()) { // �ι�° �������� ��������� �߰��ϱ�
							listSP.add(new SubPlane(listAP.get(0).getX(), listAP.get(0).getY()));

						}else if(item.typenum() == 2) { //����° �������� ������ �����ϱ�
							HRemove.add(listH.get(INDEX-1));
							INDEX--; //������ ����
						}
					}
					
					if(item.getYI() >= 980)
						IRemove.add(item);
				}
			}

			//������ ���Ͱ� �´����� ���� ����
			for(AirPlane AP : listAP) {
				for(Monster m : listM1) {
					m.drawM1(g);
					m.moveM();
					if(AP.distance(m.getX(),m.getY())<=50) {
						MRemove1.add(m);
						score--;
					}
				}

				for(Monster2 m2 : listM2) {
					m2.drawM2(g);
					m2.moveM();

					if(AP.distance(m2.getX(),m2.getY())<=43) {
						MRemove2.add(m2);
						score--;
					}
				}

				for(Monster3 m3 : listM3) {
					if(level > 1) {
						m3.drawM3(g);
						m3.moveM();

						if(AP.distance(m3.getX(),m3.getY())<=47) {
							MRemove3.add(m3);
							score--;
						}
					}
				}

				for(Monster4 m4 : listM4) {
					if(level>1) {
						m4.drawM4(g);
						m4.moveM();

						if(AP.distance(m4.getX(),m4.getY())<=50) {
							MRemove4.add(m4);
							score--;
						}
					}
				}
			}
			
			//���� ����� �߰�
			for(SubPlane sp : listSP) {
				sp.draw(g);
			}
			
			//score�� level ���ΰ�ħ
			scoreLabel.setText("SCORE: " + score);
			levelLabel.setText("LEVEL: " +level);
			
			
			//����
			for(Attack a : ATRemove) 
				listAT.remove(a);
			for(MAttack1 a : MARemove1) 
				listAM1.remove(a);
			for(MAttack2 a : MARemove2) 
				listAM2.remove(a);
			for(Monster a : MRemove1) 
				listM1.remove(a);
			for(Monster2 a : MRemove2) 
				listM2.remove(a);
			for(Monster3 a : MRemove3)
				listM3.remove(a);
			for(Monster4 a : MRemove4) 
				listM4.remove(a);
			for(ITEM a : IRemove) 
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
		
		//����⸦ ����Ű�� �̵�
		@Override
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
			
			//���� Ű���带 ���� ������ ����
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
			
			//����Ű�� ���� ȭ�� �ٲ�
			if(keycode == KeyEvent.VK_ENTER) {
				CountE++;
				
				//���� ȭ�� -> ���� ȭ��
				if(CountE == 1) { 
					isMainScreen = false;
					isExplainScreen = true;
					isGameScreen = false;
					isFinishScreen = false;
					t.start();
				}
				
				//���� ȭ�� -> ���� ȭ�� 
				if(CountE == 2) { 
					isMainScreen = false;
					isExplainScreen = false;
					isGameScreen = true;
					isFinishScreen = false;
					panel.add(nameLabel);
					panel.add(scoreLabel);
					panel.add(levelLabel);
					panel.add(highName);
					
					if(listAP.isEmpty()) //����� ���� ��ġ =(������ ����, ������ 3���� 2 ��ġ)
						listAP.add(new AirPlane(PANEL_WIDTH/2-PLANE_WIDTH/2, PANEL_HEIGHT/3*2-PLANE_HEIGHT/2));
					
					for(int i = 0; i< INDEX; i++ ) { //�⺻ ������ = 3��
						listH.add(new Heart(i));

						if(INDEX >= 6) { //�ִ� ������ 5��
							INDEX = 5;
						}
					}
				}	
			}
			else if (keycode == KeyEvent.VK_ESCAPE) { //ESC Ű�� ���� ����
				System.exit(0);
			}
		}
		
		
		//Ű���带 ������ ������ �� �۵� ����
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
	
	//�����
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

		public double distance(int x, int y) { //�������� �Ÿ�
			return Math.sqrt(Math.pow((this.x+w/2)-x, 2)+ Math.pow((this.y+h/2)-y, 2));
		}

	}

	//���� �����
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

		public void draw(Graphics g) { //���� ����� ��ġ ����
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
	
	//����Ⱑ ����
	class Attack{
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

		public void drawA1(Graphics g2) { //����Ⱑ �����Ҷ�
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

	//���� 1�� �ϴ� ����
	class MAttack1{
		int pX;
		int pY;
		int wid = 2;
		int hei = 20;
		Color color;

		MAttack1(int x, int y, Color color){
			pX = x+14;
			pY = y+20;
			wid = 2;
			hei = 20;

			this.color = color;
		}

		public void moveA() {
			pY += 20;
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

	//���� 3�� �ϴ� ����
	class MAttack2{
		int pX;
		int pY;
		int wid = 2;
		int hei = 20;
		Color color;

		MAttack2(int x, int y, Color color){
			pX = x+21;
			pY = y+45;
			wid = 2;
			hei = 20;

			this.color = color;
		}

		public void moveA() {
			pY += 20;
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
	
	//���� 1
	class Monster{ //������ �Ʒ��� ������
		int pX;
		int pY;
		int width = 30;
		int height = 30;
		int HP1 = 5;

		Monster(int x, int y){
			pX = x;
			pY = y;
		}

		public void drawM1(Graphics g3) {
			g3.drawImage(enemy1,pX,pY,width, height, null);
		}
		public void moveM() {
			pY += moveYM;
		}
		public void hpM1() {
			HP1--;
		}
		public int getX() {
			return pX;
		}
		public int getY() {
			return pY;
		}


		public double distanceM(int x,int y) {
			return Math.sqrt(Math.pow((pX+width/2) - x, 2) + Math.pow((pY+height/2) - y, 2));
		}
	}

	//���� 2
	class Monster2{ //�����ʿ��� ���� �밢�� ������
		int pX;
		int pY;
		int width = 36;
		int height = 36;
		int HP2 = 5;

		Monster2(int x, int y){
			pX = x;
			pY = y;
		}

		public void drawM2(Graphics g4) {
			g4.drawImage(enemy2,pX,pY,width,height, null);
		}
		public void moveM() {
			pX -= moveXM;
			pY -= moveYM;
		}
		public void hpM2() {
			HP2--;
		}
		public int getX() {
			return pX;
		}
		public int getY() {
			return pY;
		}

		public double distanceM(int x,int y) {
			return Math.sqrt(Math.pow((pX+width/2) - x, 2) + Math.pow((pY+height/2) - y, 2));
		}
	}

	//���� 3
	class Monster3{ //���ʿ��� ���������� ������
		int pX;
		int pY;
		int width = 45;
		int height = 45;
		int HP3 = 10;

		Monster3(int x, int y){
			pX = x;
			pY = y;
		}

		public void drawM3(Graphics g5) {
			g5.drawImage(enemy3,pX,pY,width,height, null);
		}
		public void moveM() {
			pX += moveXM;
		}
		public void hpM3() {
			HP3--;
		}
		public int getX() {
			return pX;
		}
		public int getY() {
			return pY;
		}

		public double distanceM(int x,int y) {
			return Math.sqrt(Math.pow((pX+width/2) - x, 2) + Math.pow((pY+height/2) - y, 2));
		}
	}

	//���� 4
	class Monster4{ //���ʿ��� ������ �밢�� ������
		int pX;
		int pY;
		int width = 50;
		int height = 50;
		int HP4 = 10;

		Monster4(int x, int y){
			pX = x;
			pY = y;
		}

		public void drawM4(Graphics g6) {
			g6.drawImage(enemy4,pX,pY,width,height, null);
		}
		public void moveM() {
			pX += moveXM;
			pY += moveYM;
		}
		public void hpM4() {
			HP4--;
		}
		public int getX() {
			return pX;
		}
		public int getY() {
			return pY;
		}

		public void drawAM(Graphics g2) {
			g2.setColor(Color.red);
			g2.fillRect(pX+24,pY+50,2,10);
		}

		public double distanceM(int x,int y) {
			return Math.sqrt(Math.pow((pX+width/2) - x, 2) + Math.pow((pY+height/2) - y, 2));
		}
	}

	//������ ����
	class ITEM {
		int px;
		int py;
		int wid=30;
		int hei=30;	
		Image image;
		int type;

		ITEM(int x, int y, Image item, int type) {
			px = x;
			py = y;
			image = item;
			this.type = type;
		}

		public int typenum() {
			return type;
		}
		public void drawI(Graphics g7) {
			g7.drawImage(image, px, py, wid,hei,null);
		}
		public void moveI() {
			py++;
		}
		public int getXI() {
			return px+10;
		}
		public int getYI() {
			return py+10;
		}

		public double distanceI(int x,int y) {
			return Math.sqrt(Math.pow((x+wid/2) - x, 2) + Math.pow((y+hei/2) - y, 2));
		}
	}
	
	//�������� ��Ÿ���� ��Ʈ
	class Heart{
		int index;

		Heart(int i){
			index = i;
		}

		public void drawH(Graphics g) {
			g.drawImage(heart1, 30+index*30, 950, 27, 21, null);
		}
	}
	
	//�÷��̾� �̸�
	class players{
		String name;

		players(String player_name){
			this.name = player_name;
		}

		public String name() {
			return this.name;
		}
	}
	
	//�ְ� ������ ��
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

