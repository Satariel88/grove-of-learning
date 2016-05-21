package progetto.ml.navigable.menu.button;

import static playn.core.PlayN.assets;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;

public abstract class GameButton extends RectButton
{
	private static final String BUTTON_IMAGE = "images/game/game_button.png";
	private static final IDimension SIZE = new Dimension(105.0f, 26.0f);
	private static Image buttonImage;
	
	public GameButton()
	{
		super(buttonImage);
		getButtonLayerOff().setDepth(4.0f);
		getButtonLayerOn().setDepth(5.0f);
		getText().setDepth(6.0f);
		getTextOver().setDepth(7.0f);
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
