package autoclon4;

import processing.core.PApplet;

public class UIElement {
	protected PApplet parent;
	
	public int x, y, w, h;
	
	public UIElement(PApplet parent, int x, int y, int w, int h) {
		this.parent = parent;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void update() {
		
	}
	
	public void render() {
		parent.noStroke();
		parent.fill(255, 0, 255);
		parent.rect(x, y, w / 2, h / 2);
		parent.rect(x + w / 2, y + h / 2, w / 2, h / 2);
		parent.fill(0);
		parent.rect(x + w / 2, y, w / 2, h / 2);
		parent.rect(x, y + h / 2, w / 2, h / 2);
	}
}
