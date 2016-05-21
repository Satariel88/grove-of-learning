package progetto.ml.navigable.menu;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import java.util.LinkedList;
import java.util.List;
import playn.core.Image;
import playn.core.ImageLayer;
import progetto.ml.navigable.menu.button.Button;
import progetto.ml.navigable.menu.button.ToNewGameButton;
import progetto.ml.navigable.menu.button.ToOptionsButton;
import pythagoras.f.IPoint;
import pythagoras.f.Point;

public class MainMenu extends Menu
{
	private static final String BACKGROUND = "images/bg.png";
	private static final String LOGO = "images/logo.png";
	private static final IPoint LOGO_POS = new Point(146.0f, 5.0f); 
	private static MainMenu instance;
	private static Image bgImage;
	private static Image logoImage;
	
	private ImageLayer background;
	private ImageLayer logo;
	private List<Button> menuButtons;
	
	private MainMenu()
	{
		super(bgImage);
		logo = graphics().createImageLayer(logoImage);
		logo.setDepth(1.0f);
		logo.setVisible(false);
		logo.setTranslation(LOGO_POS.x(), LOGO_POS.y());
		graphics().rootLayer().add(logo);
		menuButtons = new LinkedList<Button>();
		menuButtons.add(new ToNewGameButton());
		menuButtons.add(new ToOptionsButton());
	}
	
	public static MainMenu getInstance()
	{
		if (instance == null)
			instance = new MainMenu();
		
		return instance;
	}
	
	public static void loadAssets()
	{
		bgImage = assets().getImage(BACKGROUND);
		logoImage = assets().getImage(LOGO);
	}

	@Override
	public List<Button> getButtons()
	{
		return menuButtons;
	}
	
	@Override
	public ImageLayer getBg()
	{
		return background;
	}
	
	@Override
	public void setBg(ImageLayer background)
	{
		this.background = background;
	}
	
	@Override
	public void show()
	{
		super.show();
		logo.setVisible(true);
	}
	
	@Override
	public void hide()
	{
		super.hide();
		logo.setVisible(false);
	}
}
