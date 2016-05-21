package progetto.ml.navigable.menu.button;

import static playn.core.PlayN.assets;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;

public abstract class MainMenuButton extends RectButton
{
	private static final String BUTTON_IMAGE = "images/menu/menu_button.png";
	private static final IDimension SIZE = new Dimension(237.0f, 59.0f);
	private static Image buttonImage;
	
	public MainMenuButton()
	{
		super(buttonImage);
	}
	
	public static void loadAssets()
	{
		buttonImage = assets().getImage(BUTTON_IMAGE);
	}
		
	@Override
	public IDimension getSize()
	{
		return SIZE;
	}
}
