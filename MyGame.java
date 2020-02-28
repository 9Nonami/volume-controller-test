import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyGame extends Game{

	private boolean u;
	private boolean d;
	private boolean l;
	private boolean r;
	private int mx;
	private int my;
	private boolean click;
	private VolumeGroup volumeGroup;



	public MyGame(int width, int height){
		super(width, height);
		create();	
	}

	private void create() {
		int vWidth = 10;
		int vHeight = 30;

		int gWidth = 300;
		int gHeight = 10;

		Volume volume1 = new Volume();
		volume1.definePointer(20, 90, vWidth, vHeight); //must be the 1st one
		volume1.defineGauge(20, 100, gWidth, gHeight); //must be the 2nd one
		volume1.defineArea(10, 90, 330, 30);
		volume1.defineRange(0.0f, 255.0f); //-80 ~ 6
		volume1.defineRenderValueXY(20, 20);
		volume1.defineInitialValue(0.0f);

		Volume volume2 = new Volume();
		volume2.definePointer(20, 140, vWidth, vHeight);
		volume2.defineGauge(20, 150, gWidth, gHeight);
		volume2.defineArea(10, 140, 330, 30);
		volume2.defineRange(0.0f, 255.0f);	
		volume2.defineRenderValueXY(20, 40);
		volume2.defineInitialValue(0.0f);

		Volume volume3 = new Volume();
		volume3.definePointer(20, 190, vWidth, vHeight);
		volume3.defineGauge(20, 200, gWidth, gHeight);
		volume3.defineArea(10, 190, 330,30);
		volume3.defineRange(0.0f, 255.0f);
		volume3.defineRenderValueXY(20, 60);
		volume3.defineInitialValue(0.0f);

		volumeGroup = new VolumeGroup(this, new Volume[]{volume1, volume2, volume3});
	}

	@Override
	public void updateClass(){
		updateKeys();
		volumeGroup.update(mx, my, clicked);
	}

	@Override
	public void renderClass(Graphics g){
		g.setColor(new Color((int)(volumeGroup.getValueOf(0)), (int)(volumeGroup.getValueOf(1)), (int)(volumeGroup.getValueOf(2))));
		g.fillRect(0, 0, width, height);

		g.setColor(Color.WHITE);
		g.fillRect(10, 0, 120, 80);

		volumeGroup.render(g);
	}

	private void updateKeys() {
		u = up;
		d = down;
		l = left;
		r = right;
		mx = mouseX;
		my = mouseY;
		click = clicked;
	}

}
