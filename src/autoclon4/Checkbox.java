package autoclon4;

import processing.core.PApplet;
import processing.core.PFont;

public class Checkbox extends Button {
	public boolean checked = false;
	
	public Checkbox(PApplet parent, String name, int x, int y, int w, int h, String text, int textSize, PFont font) {
		super(parent, name, x, y, w, h, text, textSize, font);
	}
	
	public Checkbox(PApplet parent, String name, int x, int y, int w, int h, String text, int textSize, PFont font, boolean checked) {
		this(parent, name, x, y, w, h, text, textSize, font);
		this.checked = checked;
	}

	@Override
	public void update() {
		hoverOver = parent.mouseX > x && parent.mouseX < x + w && parent.mouseY > y && parent.mouseY < y + h;
		pClicked = clicked;
		clicked = hoverOver && parent.mousePressed;
		if (pClicked && !clicked) {
			checked = !checked;
		}
	}

	@Override
	public void render() {
		parent.stroke(0);
		parent.strokeWeight(1f);
		if (hoverOver) {
			if (clicked) {
				parent.fill(210);
			} else {
				parent.fill(230);
			}
		} else {
			parent.fill(255);
		}
		parent.rect(x, y, h, h);
		
		if(checked) {
			parent.strokeWeight(2f);
			parent.stroke(0);
			parent.line(x, y + h / 2, x + h / 2, y + h);
			parent.line(x + h / 2, y + h, x + h, y);
		}
		
		parent.fill(250);
		parent.textFont(font);
		parent.textSize(textSize);
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		parent.text(text, x + w / 2 + h / 2, y + h / 2);
	}
}
