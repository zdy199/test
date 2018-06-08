package com.tarma.shoot;
import java.util.Random;
//import com.tarma.shoot.ShootGame;

/**
 * �л�
 * @author Administrator
 *
 */
public class Airplane extends FlyingObject implements Enemy{
	
	private int speed =2; //�߲�����
	public Airplane() {
		// TODO Auto-generated constructor stub
		image = ShootGame.airplane;
		width = image.getWidth();
		height= image.getHeight();
		Random rand = new Random();
		x= rand.nextInt(ShootGame.WIDTH-this.width);
		y= -this.height; //���ĵл��ĸ�
	}
		
	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 5;
	}
	@Override
	public void step() {
		// TODO Auto-generated method stub
		y+=speed;
	}
	
	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		
		return this.y>=ShootGame.HEIGHT;
	}
}
