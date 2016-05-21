package progetto.ml.navigable.menu.button;

import static playn.core.PlayN.graphics;
import java.util.LinkedList;
import java.util.List;
import playn.core.Color;
import playn.core.Image;
import playn.core.ImageLayer;
import progetto.ml.media.Text;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Rectangle;

public abstract class RectButton extends Button
{
	protected static final int DEFAULT_TEXT_COLOR = Color.rgb(225, 195, 123);  
	protected static final int DEFAULT_OVER_TEXT_COLOR = Color.rgb(255, 229, 167);

	private List<ImageLayer> buttonLayers;
	private IRectangle hitBox;
	private boolean isMouseOver;
	
	public RectButton(Image buttonImage)
	{
		initLayers(buttonImage);
		hitBox = new Rectangle(getPosition(), getSize());
		initText();
	}
	
	public static void loadAssets()
	{
		MainMenuButton.loadAssets();
		GameButton.loadAssets();
	}
	
	private void initLayers(Image buttonImage)
	{
		buttonLayers = new LinkedList<ImageLayer>();
		buttonLayers.add(graphics().createImageLayer(buttonImage.subImage(0, getSize().height() * ButtonType.NORMAL.getOffset(), getSize().width(), getSize().height())));
		buttonLayers.add(graphics().createImageLayer(buttonImage.subImage(0, getSize().height() * ButtonType.OVER.getOffset(), getSize().width(), getSize().height())));
		
		for (ButtonType type : ButtonType.values())
		{
			buttonLayers.get(type.getOffset()).setDepth(1.0f);
			buttonLayers.get(type.getOffset()).setVisible(false);
			buttonLayers.get(type.getOffset()).setTranslation(getPosition().x(), getPosition().y());
			graphics().rootLayer().add(buttonLayers.get(type.getOffset()));
		}
	}

	private void initText()
	{
		getText().setTranslation(getPosition(), getSize());
		getText().setDepth(3.0f);
		getText().setVisible(true);
		getText().init();
		
		getTextOver().setTranslation(getPosition(), getSize());
		getTextOver().setDepth(4.0f);
		getTextOver().init();
	}

	public ImageLayer getButtonLayerOff()
	{
		return buttonLayers.get(ButtonType.NORMAL.getOffset());
	}
	
	public ImageLayer getButtonLayerOn()
	{
		return getButtonLayers().get(ButtonType.OVER.getOffset());
	}
	
	public List<ImageLayer> getButtonLayers()
	{
		return buttonLayers;
	}
	
	@Override
	public boolean visible()
	{
		return getButtonLayerOff().visible() || getButtonLayerOn().visible();
	}
	
	
	@Override
	public void show()
	{
		super.show();
		getText().setVisible(true);
	}
	
	@Override
	public void hide()
	{
		super.hide();
		getButtonLayerOn().setVisible(false);
		getText().setVisible(false);
		getTextOver().setVisible(false);
	}
	
	public boolean isMouseOver()
	{
		return isMouseOver;
	}
	
	public void setMouseOver(boolean isMouseOver)
	{
		this.isMouseOver = isMouseOver;
		getButtonLayerOn().setVisible(isMouseOver);
		getButtonLayerOff().setVisible(!isMouseOver);
		getTextOver().setVisible(isMouseOver);
		getText().setVisible(!isMouseOver);
	}
	
	public boolean intersects(IPoint point) 
	{
		return hitBox.contains(point);
	}
	
	public abstract Text getText();
	
	public abstract Text getTextOver();
}
