package progetto.ml.navigable.menu.button;

import static playn.core.PlayN.assets;
import playn.core.Image;
import pythagoras.f.IPoint;
import pythagoras.f.Point;

public class CancelButton extends OptionsMenuButton
{
	private static final String BUTTON_IMAGE = "images/menu/cancel_button.png";
	private static IPoint POSITION = new Point(374, 365);
	private static Image buttonImage;

	public CancelButton()
	{
		super(buttonImage);
	}
	
	public static void loadAssets()
	{
		buttonImage = assets().getImage(BUTTON_IMAGE);
	}
	
	@Override
	public IPoint getPosition()
	{
		return POSITION;
	}
}
