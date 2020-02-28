import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public abstract class Game implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private JFrame jframe;
	
	protected int width;
	protected int height;
	
	private Graphics g;
	private boolean running;
	private Thread t;
	public static final int FPS = 30;
	public static final long ONE_NANO = 1_000_000_000;
	public static final long LIMIT = ONE_NANO / FPS;
	private BufferStrategy bs;
	private Canvas canvas;

	protected boolean up;
	protected boolean down;
	protected boolean left;
	protected boolean right;
	protected boolean space;
	protected int mouseX;
	protected int mouseY;
	protected boolean clicked;

	private boolean lockClick;


	public Game(int width, int height){
		
		this.width = width;
		this.height = height;

		jframe = new JFrame();
		jframe.setSize(width, height);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setLocationRelativeTo(null);
		jframe.setResizable(false);

		canvas = new Canvas();
		Dimension d = new Dimension(width, height);
		canvas.setPreferredSize(d);
		canvas.setMinimumSize(d);
		canvas.setMaximumSize(d);

		jframe.add(BorderLayout.CENTER, canvas);
		jframe.pack();

		jframe.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);

		canvas.createBufferStrategy(2);

		jframe.setIgnoreRepaint(true);
		canvas.setFocusable(false);

		t = new Thread(this);
	}


	public void start(){
		running = true;
		jframe.setVisible(true);
		t.start();
	}

	private void update(){
		updateClass();
	}

	public abstract void updateClass();

	private void render(){
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		renderClass(g);
		g.dispose();
		bs.show();
		Toolkit.getDefaultToolkit().sync();
	}

	public abstract void renderClass(Graphics g);

	@Override
	public void run(){
		long ini;
		long end;
		long delta;
		long wait;
		while (running) {
			ini = System.nanoTime();
			
			update();
			render();
			//resetMouseInput();

			end = System.nanoTime();
			delta = end - ini;
			wait = LIMIT - delta;
			if (wait > 0) {
				try{
					wait = wait/1_000_000;
					//System.out.println("wait: " + wait + " millis");
					Thread.sleep(wait);
				} catch(Exception ex){}
			} else {
				//System.out.println("OVERLOAD:" + wait / 1_000_000);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			space = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			space = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e){

	}

	@Override
    public void mouseClicked(MouseEvent mouseEvent) {
    	//este metodo eh considerado quando
    	//se clica e solta rapidinho o botao
    	//do mouse.
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    	//este metodo eh chamado soh de clicar
    	//no componente com o listener.
    	//nao importa se soltou o botao do 
    	//mouse ou se continua pressionado.
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    	if (lockClick) {
    		lockClick = false;
    		clicked = false;
    	}
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
    	mouseX = mouseEvent.getX();
    	mouseY = mouseEvent.getY();
    	if (!lockClick) {
    		lockClick = true;
    		clicked = true;
    	}
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

	public void testFill(){
		g.setColor(Color.PINK);
		g.fillRect(0, 0, width, height);
	}

	public void resetMouseInput() {
		clicked = false;
	}

}
