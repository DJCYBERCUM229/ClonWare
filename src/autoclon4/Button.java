package autoclon4;

import processing.core.PApplet;
import processing.core.PFont;

public class Button extends UIElement {
	String text = "";
	String name = "";
	int textSize = 0;
	PFont font;

	boolean hoverOver = false;
	boolean clicked = false;
	boolean pClicked = false;
	boolean needsProcessing = false;

	public Button(PApplet parent, String name, int x, int y, int w, int h, String text, int textSize, PFont font) {
		super(parent, x, y, w, h);
		this.text = text;
		this.textSize = textSize;
		this.font = font;
		this.name = name;
	}

	@Override
	public void update() {
		hoverOver = parent.mouseX > x && parent.mouseX < x + w && parent.mouseY > y && parent.mouseY < y + h;
		pClicked = clicked;
		clicked = hoverOver && parent.mousePressed;
		// quick and dirty fix
		if ((pClicked && !clicked) || (name.equals("rebinder") && clicked)) {
			needsProcessing = true;
		}
	}

	@Override
	public void render() {
		parent.stroke(175);
		parent.strokeWeight(1f);
		if (hoverOver) {
			if (clicked) {
				parent.fill(175);
			} else {
				parent.fill(200);
			}
		} else {
			parent.fill(225);
		}
		parent.rect(x, y, w, h);
		parent.fill(0);
		parent.textFont(font);
		parent.textSize(textSize);
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		parent.text(text, x + w / 2, y + h / 2);
	}
}
