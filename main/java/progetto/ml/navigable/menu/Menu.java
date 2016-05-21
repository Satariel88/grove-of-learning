package progetto.ml.navigable.menu;

import static playn.core.PlayN.graphics;
import java.util.List;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import progetto.ml.core.AgeFinderGame;
import progetto.ml.navigable.Navigable;
import progetto.ml.navigable.menu.button.Button;
import pythagoras.f.Point;

public abstract class Menu implements Navigable
{

	
	public Menu(Image bgImage)
	{
		setBg(graphics().createImageLayer(bgImage));
		getBg().setDepth(0.0f);
		getBg().setVisible(false);
		graphics().rootLayer().add(getBg());
	}
	
	public static void loadAssets()
	{
		MainMenu.loadAssets();
		OptionsMenu.loadAssets();
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{
		for (Button button : getButtons())
		{
			if (event.button() == Mouse.BUTTON_LEFT && button.intersects(new Point(event.localX(), event.localY())))
			{
				button.play();

				if (!(button.getState()).equals(this))
				{
					hide();
					button.getState().show();
					return button.getState();
				}
			}
		}
		
		return this;
	}
	
	@Override
	public void show()
	{
		getBg().setVisible(true);
		
		for (Button button : getButtons())
			button.show();
	}
	
	@Override
	public void hide()
	{
		getBg().setVisible(false);
		
		for (Button button : getButtons())
			button.hide();
	}
	
	@Override
	public void update(int delta)
	{
		for (Button button : getButtons())
		{
			if (button.intersects(AgeFinderGame.getInstance().getPointerLocation()) && !button.isMouseOver())
				button.setMouseOver(true);
		
			if (!button.intersects(AgeFinderGame.getInstance().getPointerLocation()) && button.isMouseOver())
				button.setMouseOver(false);
		}
	}
	
	public abstract List<Button> getButtons();
	
	protected abstract ImageLayer getBg();
	
	protected abstract void setBg(ImageLayer background);
}
