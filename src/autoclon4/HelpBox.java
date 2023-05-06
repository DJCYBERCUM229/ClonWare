package autoclon4;

import processing.core.PApplet;
import processing.core.PFont;

public class HelpBox extends UIElement {
	boolean hoverOver = false;
	
	String name = "";
	int textSize = 0;
	int maxWidth = 0;
	String[] lines;
	PFont font;
	
	public HelpBox(PApplet parent, int x, int y, int w, int h, int textSize, PFont font, String... lines) {
		super(parent, x, y, w, h);
		this.textSize = textSize;
		this.font = font;
		this.lines = lines;
		parent.textFont(font);
		parent.textSize(textSize);
		for(int i=0;i<lines.length;i++) {
			maxWidth = (int) Math.max(maxWidth, parent.textWidth(lines[i]));
		}
	}
	
	@Override
	public void update() {
		hoverOver = parent.mouseX > x && parent.mouseX < x + w && parent.mouseY > y && parent.mouseY < y + h;
	}

	@Override
	public void render() {
		parent.noStroke();
		parent.strokeWeight(1f);
		if (hoverOver) {
			parent.fill(225, 200);
		} else {
			parent.fill(255, 150);
		}
		parent.rect(x, y, w, h);
		
		parent.fill(0);
		parent.textFont(Autoclon4.consolas);
		parent.textSize(textSize);
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		parent.text("?", x + w / 2, y + h / 2);
	}
}
