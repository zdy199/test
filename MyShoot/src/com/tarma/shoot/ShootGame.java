package com.tarma.shoot;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.lang.reflect.Array;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ShootGame extends JPanel{
	
	public static final int WIDTH= 400;
	public static final int HEIGHT= 654;
	
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE =2;
	public static final int GAME_OVER =3;
	private int state = START;
	
	private Hero hero = new Hero();
	private FlyingObject[] flyings = {};
	private Bullet[] bullets ={};
	
	ShootGame(){
		flyings = new FlyingObject[2];
		flyings[0]= new Airplane();
		flyings[1]= new Bee();
		bullets = new Bullet[1];
		bullets[0] = new Bullet(100,200);
		
	}

	
 static {
		try{
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			
		}catch(Exception e){
			e.printStackTrace();
		}	
 }	
 /**
  * 敌机
  * @return
  */
 	public FlyingObject nextOne(){
 		Random rand = new Random();
 		int type = rand.nextInt(20);
 		if(type < 4){
 			return new Bee();
 		}else{
 			return new Airplane();
 		}
 	}

 	int flyEnteredIndex = 0;
 	
 	public void enterAction(){
 		flyEnteredIndex++;
 		if(flyEnteredIndex%40 ==0){
 			FlyingObject one  = nextOne();
 			 flyings = Arrays.copyOf(flyings,flyings.length+1);
 			 flyings[flyings.length-1] = one;
 		}
 	}

 /**
  * 飞行物走步	
  */
 	public void stepAction() {
		// TODO Auto-generated method stub
 		hero.step();
 		for(int i= 0;i<flyings.length;i++){
 			flyings[i].step();
 		}
 		for(int i=0;i<bullets.length;i++){
 			bullets[i].step();
 		}
	}
 	
 /**
  * 子弹入场
  */
 	int shootIndex =0;
 	private void shootAction() {
		// TODO Auto-generated method stub
 		shootIndex++;
 		if(shootIndex%30 ==0){
 			Bullet[] bs = hero.shoot(); //获取子弹对象
 			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);//扩容
 			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length); //数组的追加
 			
 		}
	}
 	
 	public void outOfBoundsAction(){
 		int index = 0; //数组下标，个数
 		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
 		for(int i = 0; i<flyings.length; i++){
 			FlyingObject f = flyings[i];
 			if(!f.outOfBounds()){
 				flyingLives[index] = f;
 				index++;
 			}
 		}
 		flyings = Arrays.copyOf(flyingLives,index);
 		
 		int index1= 0;
 		
 		Bullet[] bulletsLives = new Bullet[bullets.length];
 		for(int i= 0 ;i<bullets.length;i++){
 			Bullet b = bullets[i];
 			if(!b.outOfBounds()){
 				bulletsLives[index1] =b;
 				index1++;
 			}
 		}
 		bullets = Arrays.copyOf(bulletsLives, index1);
 	}
 	
 	public void bandAction(){
 		for(int i= 0;i<bullets.length;i++){
 			Bullet b = bullets[i];
 			bang(b);
 		}
 	}
 	
 	int score=0;
 	public void bang(Bullet b){
 		int index =-1;
 		for(int i = 0;i<flyings.length;i++){
 			FlyingObject f = flyings[i];
 			if(f.shootBy(b)){
 				index = i;
 				break;
 			}
 		}
 		
 	if(index!=-1){
 		FlyingObject one = flyings[index];
 		if(one instanceof Enemy){
 			Enemy e = (Enemy)one;
 			score+=e.getScore();
 	//		System.out.print(score);
 			
 		}
 		if(one instanceof Award){
 			Award a = (Award) one;
 			int type = a.getType();
 			
 			switch(type){
 			case Award.DOUBLE_FIRE:
 				hero.addDoubleFire();
 				break;
 				
 			case Award.LIFE:
 				hero.addLife();
 				break;
 			}
 		}
 		FlyingObject t = flyings[index];
 		flyings[index] = flyings[flyings.length-1];
 		flyings[flyings.length-1]=t;
 		flyings= Arrays.copyOf(flyings, flyings.length-1);
 	}
 	}
 	
 /**
  * 游戏是否结束
  */
 	public void checkGameOverAction(){
 		if(isGameOver()){
 			state = GAME_OVER;
 		}
 	}
 	
 	public boolean isGameOver(){
 		for(int i=0; i<flyings.length;i++){
 			FlyingObject f = flyings[i];
 			if(hero.hit(f)){
 				hero.subtractLife();
 				hero.clearDoubleFire();
 				FlyingObject t = flyings[i];
 				flyings[i] =flyings[flyings.length-1];
 				flyings[flyings.length-1] =t;
 				flyings= Arrays.copyOf(flyings,flyings.length-1);
 			}
 		}
 		return hero.getLife()<=0;
 	}
 	
 	
 /**
  * 游戏启动执行
  */
 	public void action(){
 		
 		MouseAdapter l  = new MouseAdapter(){
 			@Override
 			public void mouseMoved(MouseEvent e) {
 				// TODO Auto-generated method stub
 				if(state == RUNNING){
	 				int x = e.getX();
	 				int y = e.getY();
	 				hero.moveTo(x, y);
 				}
 			}
 			//点击
 			public void mouseClicked(MouseEvent e) {
 				switch(state){
 				case START:
 					state = RUNNING;
 					break;
 				case GAME_OVER:
 					score = 0;
 					hero = new Hero();
 					flyings = new FlyingObject[0];
 					bullets = new Bullet[0];
 					state = START;
 					break;
 				}
 			
 			}
 			public void mouseExited(MouseEvent e) {
 				if(state ==RUNNING){
 					state = PAUSE;
 				}
 			}
 			public void mouseEntered(MouseEvent e) {
 				if(state==PAUSE){
 					state = RUNNING;
 				}
 			}
 			
 		};
 		this.addMouseListener(l);
 		this.addMouseMotionListener(l);
 		
 		Timer timer = new Timer();
 		int intervel = 10; //时间间隔
 		timer.schedule(new TimerTask(){
 			@Override
 			public void run() {
 				// TODO Auto-generated method stub
 				if(state == RUNNING){
	 				enterAction(); //敌机
	 				stepAction(); //飞行物走步
	 				shootAction();//子弹入场
	 				outOfBoundsAction();
	 				bandAction(); //碰撞
	 				checkGameOverAction();
 				}
 				repaint();
 			}
 		},intervel,intervel);
 	}
 	
 /**
  * 画图
  */
 	public void paint(Graphics g){
 		g.drawImage(background,0,0,null);
 		paintHero(g);
 		paintFlyingObjects(g);
 		paintBullets(g);
 		paintScoreAndLife(g);
 		paintState(g);
 	}
 	
 	public void paintHero(Graphics g){
 		g.drawImage(hero.image,hero.x,hero.y,null);
 		
 	}
 	public void paintFlyingObjects(Graphics g){
 		for(int i=0;i<flyings.length;i++){
 			FlyingObject f = flyings[i];
 			g.drawImage(f.image,f.x,f.y,null);
 		}
 	}
 	public void paintBullets(Graphics g){
 		for(int i=0;i<bullets.length;i++){
 			Bullet b = bullets[i];
 			g.drawImage(b.image,b.x,b.y,null);
 		}
 	}
	public void paintState(Graphics g){
		switch(state){
		case START:
			g.drawImage(start, 0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause, 0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0,0,null);
			break;
		}
		
	}
 	
 	/**
 	 * 画分
 	 * @param g
 	 */
 	public void paintScoreAndLife(Graphics g){
 		g.setColor(new Color(0xFF0000));
 		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
 		g.drawString("SCORE: "+score, 10, 25);
 		g.drawString("LIFE: "+hero.getLife(),10,50);
 	}
 	
 	/**
 	 * 入口
 	 * @param args
 	 */
 
	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly");//窗口--画框
		ShootGame game = new ShootGame();
		frame.add(game);
		frame.setSize(WIDTH,HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.action(); //启动程序的执行
		
	}
}
