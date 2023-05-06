package autoclon4;

import processing.core.PApplet;
import processing.core.PFont;

public class TextElement extends UIElement {
	String text = "";
	String name = "";
	int textSize = 0;
	PFont font;
	
	public TextElement(PApplet parent, int x, int y, int w, int h, String text, int textSize, PFont font) {
		this(parent, text + "" + x + "" + y, x, y, w, h, text, textSize, font);
	}
	
	public TextElement(PApplet parent, String name, int x, int y, int w, int h, String text, int textSize, PFont font) {
		super(parent, x, y, w, h);
		this.name = name;
		
		this.text = text;
		this.textSize = textSize;
		this.font = font;
	}

	@Override
	public void render() {
		parent.textFont(font);
		parent.fill(0);
		parent.textSize(textSize);
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		parent.text(text, x + w / 2, y + h / 2);
	}
}
