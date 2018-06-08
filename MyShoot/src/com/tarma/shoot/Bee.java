package com.tarma.shoot;

import java.util.Random;

public class Bee extends FlyingObject implements Award{
	private int xSpeed =1;//走步步数
	private int ySpeed =2;
	private int awardType; //奖励类型
	
	public Bee() {
		// TODO Auto-generated constructor stub
		image = ShootGame.bee;
		width = image.getWidth();
		height= image.getHeight();
		Random rand = new Random();
		x= rand.nextInt(ShootGame.WIDTH-this.width);
		y= -this.height; //负的敌机的高
		awardType= rand.nextInt(2);
	}
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return awardType;
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		x+=xSpeed;
		y+=ySpeed;
		if(x>=ShootGame.WIDTH - this.width){
			xSpeed = -1;
		}
		if(x<=0){
			xSpeed =1;
		}
	}
	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		
		return this.y>=ShootGame.HEIGHT;
	}

}
