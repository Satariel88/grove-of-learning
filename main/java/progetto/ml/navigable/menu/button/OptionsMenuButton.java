package progetto.ml.navigable.menu.button;

import playn.core.Image;
import progetto.ml.navigable.Navigable;
import progetto.ml.navigable.menu.MainMenu;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;

public abstract class OptionsMenuButton extends CircleButton
{
	private static final IDimension SIZE = new Dimension(45.0f, 45.0f);
	private static final float RADIUS = 22.5f;
	
	public OptionsMenuButton(Image buttonImage)
	{
		super(buttonImage);
	}
	
	public static void loadAssets()
	{
		AcceptButton.loadAssets();
		CancelButton.loadAssets();
	}
	
	@Override
	public Navigable getState()
	{
		return MainMenu.getInstance();
	}
	
	@Override
	public IDimension getSize()
	{
		return SIZE;
	}
	
	@Override
	public float getRadius()
	{
		return RADIUS;
	}
}
