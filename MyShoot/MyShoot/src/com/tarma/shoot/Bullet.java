package com.tarma.shoot;

import java.util.Random;

/**
 * 子弹
 * @author Administrator
 *
 */

public class Bullet extends FlyingObject{
	private int speed = 3; //走步步数
	
	public Bullet(int x,int y) {
		// TODO Auto-generated constructor stub
		image = ShootGame.bullet;
		width = image.getWidth();
		height= image.getHeight();
		this.x= x;
		this.y = y;
	
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		y-=speed;
	}
	
	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		
		return this.y<= -this.height;
	}
}
