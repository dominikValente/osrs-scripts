package osrs_scripts;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.GroundItem;

@Script.Manifest(name="RooftopAgility", description="rooftop runner")

public class Main extends PollingScript<ClientContext> implements PaintListener, ActionListener{
	
	Courses course;
	JFrame frame;
	JPanel btnPanel;
	JPanel contentPane;
	JPanel radioPanel;
	
	JRadioButton btn1;
	JRadioButton btn2;
	JRadioButton btn3;
	JRadioButton btn4;
	JRadioButton btn5;
	
	JButton startBtn;
	JButton stopBtn;
	
	ButtonGroup group;
	
	ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	Tile startTile;
	
	private int marksOfGrace = 11849;
	private int level;
	private int startExp;
	private int skill = Constants.SKILLS_AGILITY;
	private int obs;
	private long startTime;
	private boolean running = false;
	
	
	@Override
	public void start() {
		GUI();
		course = new Courses();
		
		frame.setSize(400, 300);
		frame.setVisible(true);
		
		startTime = getRuntime();
		startExp = ctx.skills.experience(skill);
		level = ctx.skills.level(skill);
	}
	
	public void GUI() {
		frame = new JFrame("Rooftop Agility");
		contentPane = new JPanel(new BorderLayout());
		radioPanel = new JPanel();
		btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		group = new ButtonGroup();
		
		frame.setContentPane(contentPane);
		
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
		
		contentPane.setBorder(BorderFactory.createTitledBorder("Courses"));
		btnPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	
		btn1 = new JRadioButton("Draynor");
		btn2 = new JRadioButton("Al Kharid");
		btn3 = new JRadioButton("Varrock");
		btn4 = new JRadioButton("Canifis");
		btn5 = new JRadioButton("Falador");
		group.add(btn1);
		group.add(btn2);
		group.add(btn3);
		group.add(btn4);
		group.add(btn5);
		radioPanel.add(btn1);
		radioPanel.add(btn2);
		radioPanel.add(btn3);
		radioPanel.add(btn4);
		radioPanel.add(btn5);
		
		startBtn = new JButton("start");
		stopBtn = new JButton("stop");

		startBtn.addActionListener(this);
		stopBtn.addActionListener(this);
		
		btnPanel.add(startBtn);
		btnPanel.add(stopBtn);
		
		contentPane.add(radioPanel, BorderLayout.NORTH);
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		
		frame.setSize(400, 300);
		frame.setVisible(true);
	}
	
	
	@Override
	public void poll() {
		if(obstacles.isEmpty()) {
			System.out.println("list is empty");
			return;
		}
		
		if(ctx.movement.energyLevel() > 50) {
			ctx.movement.running(true);
		}
		
		switch(state()) {
		
		case START:
			obs = 0;
			if(!ctx.players.local().inMotion()) {
				ctx.movement.step(startTile);
			}
			break;
			
		case ACTION:
			for(Obstacle o : obstacles) {
				if(o.area.contains(ctx.players.local())){
					obs = obstacles.indexOf(o);
					break;
				}
			}
			GroundItem mark = ctx.groundItems.select(10).id(marksOfGrace).poll();
			
			while(mark.valid() && mark.tile().matrix(ctx).reachable() && !ctx.players.local().interacting().valid()) {
				if(!mark.inViewport()) {
					ctx.movement.step(mark);
					
					Condition.wait(new Callable<Boolean>() {

						@Override
						public Boolean call() throws Exception {
							return ctx.players.local().animation() == -1 && !ctx.players.local().inMotion();
						}
					}, 500, 6);
					mark.interact("Take");
				}
				mark.interact("Take");
			}
			
			final int ex = ctx.skills.experience(skill);
			
			GameObject obstacle = ctx.objects.select(25).id(obstacles.get(obs).ID).poll();
			
			if(!obstacle.inViewport() || obstacle.tile().distanceTo(ctx.players.local()) > 10 && !mark.valid()) {
				ctx.movement.step(obstacle);
			}else {
				
				//canifis tall tree misclick bug
				do {
					ctx.camera.pitch(99);
					ctx.camera.angle(240);
					obstacle.interact(obstacles.get(obs).action);
				}while(obstacles.get(obs).ID == 14843 && !ctx.players.local().inMotion());
				
				//falador rough wall misclick bug
				do {
					ctx.camera.pitch(99);
					ctx.camera.angle(160);
					obstacle.interact(obstacles.get(obs).action);
				}while(obstacles.get(obs).ID == 14898 && !ctx.players.local().inMotion());
				
				obstacle.interact(obstacles.get(obs).action);
				
				Condition.wait(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return ex != ctx.skills.experience(skill);
					}
				}, 500, 10);
			}
			break;
			
		case DEFAULT:
			//ctx.camera.pitch((int) (Math.random() * (99 - 1) + 1));
			//ctx.camera.angle((int) (Math.random() * (360 - 1) + 1));
			break;
		}
		
	}
	
	public State state() {
		if(ctx.client().getFloor() == 0 && !ctx.players.local().tile().equals(startTile)) {
			return State.START;
		}
		if(ctx.players.local().animation() == -1 && !ctx.players.local().inMotion()) {
			return State.ACTION;
		}
		return State.DEFAULT;
	}
	
	public enum State {
		START, ACTION, DEFAULT;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startBtn && running == false) {
			if(btn1.isSelected()) {
				obstacles = course.GetCourse(obstacles, "Draynor");
				startTile = course.startTile;
				running = true;
			}
			if(btn2.isSelected()) {
				obstacles = course.GetCourse(obstacles, "AlKharid");
				startTile = course.startTile;
				running = true;
			}
			if(btn3.isSelected()) {
				obstacles = course.GetCourse(obstacles, "Varrock");
				startTile = course.startTile;
				running = true;
			}
			if(btn4.isSelected()) {
				obstacles = course.GetCourse(obstacles, "Canifis");
				startTile = course.startTile;
				running = true;
			}
			if(btn5.isSelected()) {
				obstacles = course.GetCourse(obstacles, "Falador");
				startTile = course.startTile;
				running = true;
			}
			System.out.println("started script");
			System.out.println("obstacles list contains: " + obstacles.size() + " elements");
	     }
		else if (e.getSource() == stopBtn && running == true) {
			System.out.println("stopped script");
			running = false;
			obstacles.clear();
		}
		
	}
	@Override
	public void repaint(Graphics arg0) {
		
		/* compute current time running
		 * compute experience
		 * compute experience per hour
		 * paint GUI
		 */
		
		Graphics2D g = (Graphics2D) arg0;
		
		long time = getRuntime() - startTime;
		
		int seconds = (int) Math.floor(time/1000 % 60);
		int minutes = (int) Math.floor(time/60000 % 60);
		int hours = (int) Math.floor(time/3600000);
		
		int exp = ctx.skills.experience(skill) - startExp;
		int expPerHour = Math.abs((int)(exp * 3600000/time));
		
		int levelGain = Math.abs(ctx.skills.level(skill) - level);
		
		Font title = new Font("Consolas", Font.ITALIC, 18);
		Font body = new Font("Consolas", Font.PLAIN, 12);
		
		g.setColor(Color.CYAN);
		g.setFont(title);
		g.drawString(String.format("Rooftop Agility"), 20, 110);
		
		g.setFont(body);
		g.drawString(String.format("Runtime: %02d:%02d:%02d", hours, minutes, seconds), 20, 130);
		g.drawString(String.format("Level: %d + (%d)", level, levelGain), 20, 150);
		g.drawString(String.format("Exp: %d/hr", expPerHour), 20, 170);
		
		g.setColor(Color.BLACK);
		AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);	//create transparency
		g.setComposite(alphaComp);
		
		g.fillRect(3, 80, 180, 120); // draw and fill rectangle
	}
	
	

}
