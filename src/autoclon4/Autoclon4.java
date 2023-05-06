package autoclon4;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import processing.core.PApplet;
import processing.core.PFont;

public class Autoclon4 extends PApplet {
	boolean bindingInputs = false;

	String[] buttonNames = { "green", "red", "yellow", "blue", "orange", "up", "down" };
	char[] buttonKeys = { 'a', 's', 'j', 'k', 'l', 'z', 'x' };
	int[] buttons = new int[7];
	int selectedController = -1;
	int index = 0;
	boolean[] lastButtons = new boolean[5];

	Controller[] controllers;

	ArrayList<UIElement> elements = new ArrayList<UIElement>();

	public static PFont segoeUI;
	public static PFont segoeUI_i;
	public static PFont consolas;
	public static PFont calibri;

	int iterations = 0;
	int delay = 6;
	int legitChance = 6;
	int rakeOSMS = 0; // rake strum overstrum protection milliseconds
	int rakeMS = 0;
	int rakeAmnt = 2;
	boolean legitOvertap = false;
	boolean overtapPress = false;
	boolean overtapRelease = false;

	boolean pmousePressed = false;
	boolean justBound = false;

	public void settings() {
		size(400, 260);
	}

	public void setup() {
		controllers = ControllerEnvironment.getEnvironment().getControllers();
		if (controllers.length == 0) {
			System.out.println("Found no controllers.");
			System.exit(0);
		}

		surface.setTitle("ClonWare v1.0");

		segoeUI = createFont("Segoe UI", 2, true);
		segoeUI_i = createFont("Segoe UI Italic", 2, true);
		consolas = createFont("Consolas", 2, true);
		calibri = createFont("Calibri", 2, true);

		elements.add(new TextElement(this, width / 2 - 59, height - 20, 100, 15, "DJCYBERCUM229", 10, segoeUI));
		elements.add(new TextElement(this, 19, height - 20, 72, 15, "ClonWare (v1.0)", 10, segoeUI));

		elements.add(new TextElement(this, width - 138, 5, 87, 15, "LEGIT CHECK", 15, segoeUI_i));
		elements.add(new TextElement(this, "iterations", 65, 29, 16, 16, iterations + "", 16, consolas));
		elements.add(new TextElement(this, "delay", 65, 54, 16, 16, delay + " MS", 16, consolas));
		elements.add(new TextElement(this, "legitCheck", width - 145, 32, 96, 15, "1 / " + legitChance + " CHANCE", 15, consolas));
		elements.add(new TextElement(this, "extraRakeOSCheck", width / 2 - 60, 140, 123, 15, rakeMS + " ANTI-OVERSTRUM MS", 15, consolas));
		elements.add(new TextElement(this, "extraRakeCheck", width / 2 - 60, 170, 123, 15, rakeMS + " FLICK MS", 15, consolas));
		elements.add(new TextElement(this, "extraRake", width / 2 - 60, 200, 123, 15, rakeAmnt + "/" + rakeAmnt + " RAKE", 15, consolas));
		elements.add(new TextElement(this, 46, 5, 56, 15, "OVERTAP", 15, segoeUI_i));

		elements.add(new Checkbox(this, "legitActivate", 52, 80, 45, 12, "LEGIT", 11, segoeUI));
		elements.add(new Checkbox(this, "pressActivate", 7, 95, 69, 12, "ON PRESS", 11, segoeUI));
		elements.add(new Checkbox(this, "releaseActivate", 76, 95, 77, 12, "ON RELEASE", 11, segoeUI));
		elements.add(new Button(this, "rebinder", width - 101, height - 20, 80, 15, "REBIND", 14, segoeUI));
		elements.add(new Checkbox(this, "rakeOverrideActivate", width / 2 - 47, 119, 96, 12, "RAKE OVERRIDE", 11, segoeUI));

		elements.add(new HelpBox(this, 110, 9, 12, 12, 11, calibri, "Overtaps, what else do", "you need to know."));
		elements.add(new HelpBox(this, 110, 80, 12, 12, 11, calibri, "Makes generated overtap", "randomized. Change the", "chance of which it", "activates to the right", "of the overtap controls."));
		elements.add(new HelpBox(this, 160, 95, 12, 12, 11, calibri, "Sets what causes overtap", "to activate."));
		elements.add(new HelpBox(this, width / 2 + 56, 119, 12, 12, 11, calibri, "Changes the rake", "of your guitar.", "(Note: you will", "need to unbind", "your guitar", "strumming in", "Clone Hero.)"));
		elements.add(new HelpBox(this, width / 2 + 120, 142, 12, 12, 11, calibri, "Threshold to", "block strum", "inputs for", "guitars that", "already rake."));
		elements.add(new HelpBox(this, width / 2 + 80, 172, 12, 12, 11, calibri, "Threshold to detect", "flicking the strumbar", "to start a rake strum."));
		elements.add(new Button(this, "overtapAddOne", 88, 27, 22, 22, "+1", 15, consolas));
		elements.add(new Button(this, "overtapSubOne", 36, 27, 22, 22, "-1", 15, consolas));
		elements.add(new Button(this, "overtapMSAddOne", 95, 52, 22, 22, "+1", 15, consolas));
		elements.add(new Button(this, "overtapMSSubOne", 29, 52, 22, 22, "-1", 15, consolas));
		elements.add(new Button(this, "legitOvertapAddOne", width - 38, 27, 22, 22, "+1", 15, consolas));
		elements.add(new Button(this, "legitOvertapSubOne", width - 177, 27, 22, 22, "-1", 15, consolas));
		elements.add(new Button(this, "extraRakeOSMSSubOne", width / 2 - 112, 137, 22, 22, "-1", 15, consolas));
		elements.add(new Button(this, "extraRakeOSMSAddOne", width / 2 + 92, 137, 22, 22, "+1", 15, consolas));
		elements.add(new Button(this, "extraRakeMSSubOne", width / 2 - 72, 167, 22, 22, "-1", 15, consolas));
		elements.add(new Button(this, "extraRakeMSAddOne", width / 2 + 52, 167, 22, 22, "+1", 15, consolas));
		elements.add(new Button(this, "extraRakeSubOne", width / 2 - 65, 197, 22, 22, "-1", 15, consolas));
		elements.add(new Button(this, "extraRakeAddOne", width / 2 + 42, 197, 22, 22, "+1", 15, consolas));

		elements.add(new TextElement(this, width - 145, 57, 97, 13, "Changes the chance", 12, segoeUI_i));
		elements.add(new TextElement(this, width - 164, 72, 137, 13, "legit overtap would activate.", 12, segoeUI_i));
	}

	public void draw() {
		background(255, 225, 214);

		if (bindingInputs) {
			textAlign(CENTER);
			fill(0);
			textSize(32);

			if (index < 5) {
				text("Press " + buttonNames[index], width / 2, height / 2);
			} else {
				text("Strum " + buttonNames[index], width / 2, height / 2);
			}

			if (!mousePressed && !pmousePressed) {
				for (int i = 0; i < controllers.length; i++) {
					if (!controllers[i].getName().contains("Mouse")) {
						controllers[i].poll();

						EventQueue queue = controllers[i].getEventQueue();
						Event event = new Event();

						while (queue.getNextEvent(event)) {
							Component comp = event.getComponent();
							float value = event.getValue();

							if (comp.getName().contains("Button") && value > 0) {
								buttons[index] = Integer.parseInt(comp.getName().split(" ")[1]);

								index++;
								selectedController = i;

								if (index == 7) {
									bindingInputs = false;
									justBound = true;
									pollControllers();
								}
							}
						}
					}
				}
			}
		} else {
			stroke(175);
			strokeWeight(1);
			line(14, height - 24, width - 14, height - 24);
			line(0, 113, width, 113);

			for (int i = 0; i < elements.size(); i++) {
				elements.get(i).update();
				if (elements.get(i) instanceof TextElement) {
					updateText((TextElement) elements.get(i));
				}

				if (elements.get(i) instanceof Button) {
					if (((Button) elements.get(i)).needsProcessing) {
						processButton((Button) elements.get(i));
					}
				}

				if (elements.get(i) instanceof Checkbox) {
					setChecked((Checkbox) elements.get(i), ((Checkbox) elements.get(i)).checked);
				}
			}

			for (int i = 0; i < elements.size(); i++) {
				elements.get(i).render();
			}
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i) instanceof HelpBox) {
					if (((HelpBox) elements.get(i)).hoverOver) {
						textFont(((HelpBox) elements.get(i)).font);
						textSize(((HelpBox) elements.get(i)).textSize);
						fill(200);
						rect(mouseX, mouseY, ((HelpBox) elements.get(i)).maxWidth + 20, textAscent() * (((HelpBox) elements.get(i)).lines.length - 1) + 10);

						textAlign(PApplet.LEFT);
						fill(0);
						for (int j = 0; j < ((HelpBox) elements.get(i)).lines.length; j++) {
							text(((HelpBox) elements.get(i)).lines[j], mouseX + 15, mouseY + 5 + j * textAscent() + textDescent());
						}
					}
				}
			}
		}
//		stroke(0);
//		strokeWeight(1);
//		line(68, 0, 68, height);

		pmousePressed = mousePressed;
	}

	public void updateText(TextElement text) {
		switch (text.name) {
		case "iterations":
			text.text = iterations + "";
			break;
		case "delay":
			text.text = delay + " MS";
			break;
		case "legitCheck":
			text.text = "1 / " + legitChance + " CHANCE";
			break;
		case "extraRakeOSCheck":
			text.text = rakeMS + " ANTI-OVERSTRUM MS";
			break;
		case "extraRakeCheck":
			text.text = rakeMS + " FLICK MS";
			break;
		}
	}

	public void setChecked(Checkbox box, boolean checked) {
		switch (box.name) {
		case "legitActivate":
			legitOvertap = checked;
			break;
		case "overtapPress":
			overtapPress = checked;
			break;
		case "overtapRelease":
			overtapRelease = checked;
			break;
		}
	}

	// horrible
	public void processButton(Button button) {
		switch (button.name) {
		case "overtapAddOne":
			iterations++;
			break;
		case "overtapSubOne":
			iterations--;
			iterations = max(iterations, 0);
			break;
		case "overtapMSAddOne":
			delay++;
			break;
		case "overtapMSSubOne":
			delay--;
			delay = max(delay, 0);
			break;
		case "legitOvertapAddOne":
			legitChance++;
			break;
		case "legitOvertapSubOne":
			legitChance--;
			legitChance = max(legitChance, 1);
			break;
		case "extraRakeAddOne":
			rakeAmnt++;
			break;
		case "extraRakeSubOne":
			rakeAmnt--;
			rakeAmnt = max(rakeAmnt, 1);
			break;
		case "extraRakeOSMSAddOne":
			rakeOSMS++;
			break;
		case "extraRakeOSMSSubOne":
			rakeOSMS--;
			rakeMS = max(rakeMS, 1);
			break;
		case "extraRakeMSAddOne":
			rakeMS++;
			break;
		case "extraRakeMSSubOne":
			rakeMS--;
			rakeMS = max(rakeMS, 1);
			break;
		case "rebinder":
			if (!justBound) {
				index = 0;
				bindingInputs = true;
			} else {
				justBound = false;
			}
			break;
		default:
			System.err.println("Unhandled button " + button.name);
			break;
		}
		button.needsProcessing = false;
	}

	public void pollControllers() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					controllers[selectedController].poll();

					EventQueue queue = controllers[selectedController].getEventQueue();
					Event event = new Event();

					while (queue.getNextEvent(event)) {
						Component comp = event.getComponent();
						float value = event.getValue();

						if (comp.getName().contains("Button")) {
							int buttonPressed = -1;
							for (int j = 0; j < 7; j++) {
								if (buttons[j] == Integer.parseInt(comp.getName().split(" ")[1])) {
									buttonPressed = j;
								}
							}

							if (buttonPressed != -1) {
								if (buttonPressed < 5) {
									if ((value > 0 && !lastButtons[buttonPressed] && overtapPress) || (value == 0 && lastButtons[buttonPressed] && overtapRelease)) {
										lastButtons[buttonPressed] = value > 0;

										int keyCode = KeyEvent.getExtendedKeyCodeForChar(buttonKeys[buttonPressed]);

										new Thread(new Runnable() {
											@Override
											public void run() {
												Robot robot = null;
												try {
													robot = new Robot();
												} catch (AWTException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}

												robot.delay(10);
												if (legitOvertap) {
													if (random(1) <= 1f / legitChance) {
														for (int n = 0; n < iterations + round(random(-1, 1)); n++) {
															robot.keyPress(keyCode);
															robot.delay(floor(delay / 2f));
															robot.keyRelease(keyCode);
															robot.delay(ceil(delay / 2f));
														}
													}
												} else {
													for (int n = 0; n < iterations; n++) {
														robot.keyPress(keyCode);
														robot.delay(floor(delay / 2f));
														robot.keyRelease(keyCode);
														robot.delay(ceil(delay / 2f));
													}
												}
											}
										}).start();
									}
								} else {
									int keyCode = KeyEvent.getExtendedKeyCodeForChar(buttonKeys[buttonPressed]);
									if (value > 0) {
										new Thread(new Runnable() {
											@Override
											public void run() {
												Robot robot = null;
												try {
													robot = new Robot();
												} catch (AWTException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}

												robot.keyPress(keyCode);
											}
										}).start();
									} else {

										new Thread(new Runnable() {
											@Override
											public void run() {
												Robot robot = null;
												try {
													robot = new Robot();
												} catch (AWTException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}

												robot.keyRelease(keyCode);
											}
										}).start();
									}
								}
							}
						}
					}
				}
			}
		}).start();
	}

	public static void main(String... args) {
		Autoclon4 pt = new Autoclon4();
		PApplet.runSketch(new String[] { "Autoclon4" }, pt);
	}
}
