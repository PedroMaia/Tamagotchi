package com.tamagotchi;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
 
public class Main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
        // Notifi hereglej baina ene hesgiig hussen gazraa duudaj Notifi iif Update hiij bolno
        newNotif(R.drawable.not2,"Notification","Hey man its to hot");
    }
    
    // ene funtion iin bolowsrongui bolgoj bolno
    public void newNotif(int icon,CharSequence contentTitle,CharSequence contentText){
		
    	int HELLO_ID = 1;
    	
		 	String ns = Context.NOTIFICATION_SERVICE;
	        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
	        
	        CharSequence tickerText = "Tamagochi mode";             
	        long when = System.currentTimeMillis();       
	        Context context = getApplicationContext();     
	        Intent notificationIntent = new Intent(this, Main.class);
	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

	      
	        Notification notification = new Notification(icon, tickerText, when);
	        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	        mNotificationManager.notify(HELLO_ID, notification);
	}
    
    public class Sprite {
    	private Bitmap bitmap;
    	private Rect src;
    	private Rect dst;
    	private int frameNo;
    	private int currentFrame;
    	private int x;
    	private int y;
    	
    	public Sprite(Bitmap bitmap, int x, int y, int frameNo, Display display) {
            this.bitmap = bitmap;
            this.x = x;
            this.x -= this.bitmap.getHeight()/2;
            this.x -= this.x % 6;
            this.y = y;
            this.y -= this.bitmap.getHeight()/2;
            this.y -= this.y % 6;
            this.src = new Rect(0, 0, this.bitmap.getWidth()/38, this.bitmap.getHeight());
            this.src.left = frameNo * this.bitmap.getHeight();
            this.src.right = this.src.left + this.bitmap.getHeight();
            this.dst = new Rect(this.x, this.y, this.bitmap.getWidth()/38 + this.x, this.bitmap.getHeight() + this.y);
            this.frameNo = frameNo;
            this.currentFrame = frameNo;
    	}

    	public void update() {
    		if (currentFrame == frameNo)
    			currentFrame++;
    		else
    			currentFrame--;
        	this.src.left = currentFrame * this.bitmap.getHeight();
            this.src.right = this.src.left + this.bitmap.getHeight();
    	}
    	
    	public void onDraw(Canvas canvas, Boolean updatable) {
    		if(updatable)
    			update();
    		canvas.drawBitmap(bitmap, src, dst, null);
    	}
    }
 
    class Panel extends SurfaceView implements SurfaceHolder.Callback {
        private TutorialThread _thread;
        Bitmap
        	bitmap;
        Sprite
        	degg,
        	hunger,
        	hunger_level,
        	hygiene,
        	hygiene_level;
        int
        	displayX,
        	displayY,
        	hunger_run = 0,
        	hygiene_run = 0,
        	hunger_level_level = 5,
        	hygiene_level_level = 5;
    	private Display display;
 
        public Panel(Context context) {
            super(context);
            getHolder().addCallback(this);
            _thread = new TutorialThread(getHolder(), this);
            setFocusable(true);
            
            display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
            degg = new Sprite(bitmap, display.getWidth()/2, display.getHeight()/3, 16, display);
            hunger = new Sprite(bitmap, display.getWidth()/4, display.getHeight()/3*2, 14, display);
            hunger_level = new Sprite(bitmap, display.getWidth()/4, display.getHeight()/3*2, 11 - hunger_level_level, display);
            hygiene = new Sprite(bitmap, display.getWidth()/4*3, display.getHeight()/3*2, 12, display);
            hygiene_level = new Sprite(bitmap, display.getWidth()/4*3, display.getHeight()/3*2, 11 - hygiene_level_level, display);
            
            try {
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()){
                    File gpxfile = new File(root, ".degg");
                    FileWriter gpxwriter = new FileWriter(gpxfile);
                    BufferedWriter out = new BufferedWriter(gpxwriter);
                    out.write("Hello world");
                    out.close();
                }
            } catch (IOException e) {
            }
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
        	
        	int i = event.getAction();
            switch (i) {
                case MotionEvent.ACTION_DOWN:

                 break;
                case MotionEvent.ACTION_MOVE:

                 break;
                case MotionEvent.ACTION_UP:
		        	if(hygiene_run == 0 && 
		        	hunger_run == 0 && 
		        	event.getX() > hunger.x &&
		        	event.getX() < hunger.x + hunger.bitmap.getHeight() &&
		        	event.getY() > hunger.y &&
		        	event.getY() < hunger.y + hunger.bitmap.getHeight()){
		        		hunger_run = 6;
		        		newNotif(R.drawable.not,"Notification","thanks man im done");
		        		Toast.makeText(getApplicationContext(),"Usand orj baina", Toast.LENGTH_SHORT).show();
		        	}
		        	if(hunger_run == 0 &&
		        			
		        			hygiene_run == 0 && event.getX() > hygiene.x &&
		        			event.getX() < hygiene.x + hygiene.bitmap.getWidth() &&
		        			event.getY() > hygiene.y &&
		        			event.getY() < hygiene.y + hygiene.bitmap.getHeight()){
		        		hygiene_run = 6;
		        		newNotif(R.drawable.not3,"Notification","oooooo yeh man thanks");
		        		Toast.makeText(getApplicationContext(),"Holloj baina", Toast.LENGTH_SHORT).show();
		        	}
                 break;
            }
        	
            return true;
        }
 
        @Override
        public void onDraw(Canvas canvas) {
    		canvas.drawColor(Color.LTGRAY);
    		
    		displayX = display.getWidth();
    		displayY = display.getHeight();
    		
        	Paint paint = new Paint();
    		paint.setColor(Color.GRAY);
    		
    		for (int i = 0; i < displayX; i += 6) {
    			for (int j = 0; j < displayY; j += 6) {
    				canvas.drawRect(i, j, i+5, j+5, paint);
    			}
    		}
    		
    		degg.onDraw(canvas, true);
    		
    		if(hunger_run > 0) {
    			hunger.onDraw(canvas, true);
    			hunger_run--;
    		} else
    			hunger.onDraw(canvas, false);
    		
    		hunger_level.currentFrame = 6 - hunger_level_level;
    		hunger_level.onDraw(canvas, false);

    		if(hygiene_run > 0) {
    			hygiene.onDraw(canvas, true);
    			hygiene_run--;
    		} else
    			hygiene.onDraw(canvas, false);
    		
    		hygiene_level.currentFrame = 6 - hygiene_level_level;
    		hygiene_level.onDraw(canvas, false);
        }
 
        //@Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // TODO Auto-generated method stub
 
        }
 
        //@Override
        public void surfaceCreated(SurfaceHolder holder) {
            _thread.setRunning(true);
            _thread.start();
        }
        
        //@Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // simply copied from sample application LunarLander:
            // we have to tell thread to shut down & wait for it to finish, or else
            // it might touch the Surface after we return and explode
            boolean retry = true;
            _thread.setRunning(false);
            while (retry) {
                try {
                    _thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    // we will try it again and again...
                }
            }
        }
    }
 
    class TutorialThread extends Thread {
        private SurfaceHolder _surfaceHolder;
        private Panel _panel;
        private boolean _run = false;
        //private long fps = 1;
 
        public TutorialThread(SurfaceHolder surfaceHolder, Panel panel) {
            _surfaceHolder = surfaceHolder;
            _panel = panel;
        }
 
        public void setRunning(boolean run) {
            _run = run;
        }
 
        @Override
        public void run() {
            Canvas c;
            while (_run) {
                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                        _panel.onDraw(c);
                    }
                } finally {
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
    			try {
    				sleep(500);
    			} catch (Exception e) {
    			}
            }
        }
    }
}