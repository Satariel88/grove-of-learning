package progetto.ml.navigable.menu.button;

import static playn.core.PlayN.assets;
import playn.core.Image;
import progetto.ml.media.AudioManager.SoundType;
import progetto.ml.navigable.Navigable;
import progetto.ml.navigable.menu.OptionsMenu;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.Rectangle;

public class ActiveButton extends SwitchButton
{
	private static final String BUTTON_IMAGE = "images/menu/active_button.png";
	private static final IDimension SIZE = new Dimension(119.0f, 28.0f);
	private static Image buttonImage;
	
	private IPoint position;
	
	public ActiveButton(IPoint position, SoundType sound)
	{
		super(sound);
		this.position = position;
		initLayers(buttonImage);
		setHitBox(new Rectangle(getPosition(), getSize()));
	}
	
	public static void loadAssets()
	{
		buttonImage = assets().getImage(BUTTON_IMAGE);
	}

	@Override
	public Navigable getState()
	{
		return OptionsMenu.getInstance();
	}
	
	@Override
	public IDimension getSize()
	{
		return SIZE;
	}

	@Override
	public IPoint getPosition()
	{
		return position;
	}
}
