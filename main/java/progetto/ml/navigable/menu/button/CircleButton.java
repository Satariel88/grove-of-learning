package progetto.ml.navigable.menu.button;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import pythagoras.f.Circle;
import pythagoras.f.ICircle;
import pythagoras.f.IPoint;

public abstract class CircleButton extends Button
{
	private ICircle hitBox;
	private ImageLayer buttonLayer;
	
	public CircleButton(Image buttonImage)
	{
		initLayers(buttonImage);
		hitBox = new Circle(getPosition(), getRadius());
	}
	
	public static void loadAssets()
	{
		OptionsMenuButton.loadAssets();
	}
	
	private void initLayers(Image buttonImage)
	{
		buttonLayer = graphics().createImageLayer(buttonImage);
		buttonLayer.setDepth(1.0f);
		buttonLayer.setVisible(false);
		buttonLayer.setTranslation(getPosition().x() - getRadius(), getPosition().y() - getRadius());
		graphics().rootLayer().add(buttonLayer);
	}
	
	public ImageLayer getButtonLayerOff()
	{
		return buttonLayer;
	}
	
	public boolean intersects(IPoint point) 
	{
		return hitBox.contains(point);
	}
	
	public abstract float getRadius();
}
