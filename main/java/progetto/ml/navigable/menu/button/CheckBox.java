package progetto.ml.navigable.menu.button;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import progetto.ml.core.AgeFinderGame.Category;
import progetto.ml.navigable.GameLoop;
import progetto.ml.navigable.Navigable;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

public class CheckBox extends Button
{
	private static final String BUTTON_IMAGE = "images/game/checkbox.png";
	private static final IRectangle BASE_BOX = new Rectangle(new Point(35.0f, 99.0f), new Dimension(20.0f, 22.0f));
	private static final IPoint SHIFT = new Point(110.0f, 30.0f);
	private static Image buttonImage;
	
	private ImageLayer buttonlayerOn;
	private ImageLayer buttonlayerOff;
	private Category category;
	private IRectangle hitBox;
	
	public CheckBox(Category category)
	{
		initLayer(buttonImage, category);
		this.category = category;
	}
	
	public static void loadAssets()
	{
		buttonImage = assets().getImage(BUTTON_IMAGE);
	}
	
	private void initLayer(Image buttonImage, Category category)
	{
		hitBox = new Rectangle(new Point(BASE_BOX.x() + SHIFT.x() * category.x(), BASE_BOX.y() + SHIFT.y() * category.y()), new Dimension(20.0f, 22.0f));
		buttonlayerOff = graphics().createImageLayer(buttonImage.subImage(0, getSize().height() * ButtonType.NORMAL.getOffset(), getSize().width(), getSize().height()));
		buttonlayerOff.setDepth(4.0f);
		buttonlayerOff.setVisible(false);
		buttonlayerOff.setTranslation(hitBox.x(), hitBox.y());
		buttonlayerOn = graphics().createImageLayer(buttonImage.subImage(0, getSize().height() * ButtonType.ON.getOffset(), getSize().width(), getSize().height()));
		buttonlayerOn.setDepth(5.0f);
		buttonlayerOn.setVisible(false);
		buttonlayerOn.setTranslation(hitBox.x(), hitBox.y());
		graphics().rootLayer().add(buttonlayerOff);
		graphics().rootLayer().add(buttonlayerOn);
	}
	
	@Override
	public ImageLayer getButtonLayerOff()
	{
		return buttonlayerOff;
	}
	
	public ImageLayer getButtonLayerOn()
	{
		return buttonlayerOn;
	}

	@Override
	public boolean intersects(IPoint point)
	{
		return hitBox.contains(point);
	}

	@Override
	public IDimension getSize()
	{
		return BASE_BOX.size();
	}

	@Override
	public IPoint getPosition()
	{
		return hitBox.location();
	}
	
	@Override
	public void hide()
	{
		super.hide();
		getButtonLayerOn().setVisible(false);
	}
	
	public void check()
	{
		buttonlayerOff.setVisible(false);
		buttonlayerOn.setVisible(true);
	}
	
	public void uncheck()
	{
		buttonlayerOff.setVisible(true);
		buttonlayerOn.setVisible(false);
	}
	
	public boolean isChecked()
	{
		return buttonlayerOn.visible();
	}
	
	@Override
	public Navigable getState()
	{
		return GameLoop.getInstance();
	}

	public Category getCategory()
	{
		return category;
	}
}
