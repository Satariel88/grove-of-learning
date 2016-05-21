package progetto.ml.navigable.menu.button;

import static playn.core.PlayN.graphics;
import java.util.LinkedList;
import java.util.List;
import playn.core.Image;
import playn.core.ImageLayer;
import progetto.ml.media.AudioManager;
import progetto.ml.media.AudioManager.SoundType;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;

public abstract class SwitchButton extends Button
{
	private List<ImageLayer> buttonLayers;
	private IRectangle hitBox;
	private SoundType sound;
	
	public SwitchButton(SoundType sound)
	{
		this.sound = sound;
	}
	
	public static void loadAssets()
	{
		ActiveButton.loadAssets();
	}
	
	protected void initLayers(Image buttonImage)
	{
		buttonLayers = new LinkedList<ImageLayer>();
		buttonLayers.add(graphics().createImageLayer(buttonImage.subImage(0, getSize().height() * ButtonType.NORMAL.getOffset(), getSize().width(), getSize().height())));
		buttonLayers.add(graphics().createImageLayer(buttonImage.subImage(0, getSize().height() * ButtonType.ON.getOffset(), getSize().width(), getSize().height())));
		
		for (ButtonType type : ButtonType.values())
		{
			buttonLayers.get(type.getOffset()).setDepth(1.0f);
			buttonLayers.get(type.getOffset()).setVisible(false);
			buttonLayers.get(type.getOffset()).setTranslation(getPosition().x(), getPosition().y());
			graphics().rootLayer().add(buttonLayers.get(type.getOffset()));
		}
	}

	public ImageLayer getButtonLayerOff()
	{
		return buttonLayers.get(ButtonType.NORMAL.getOffset());
	}
	
	public ImageLayer getButtonLayerOn()
	{
		return getButtonLayers().get(ButtonType.ON.getOffset());
	}
	
	public List<ImageLayer> getButtonLayers()
	{
		return buttonLayers;
	}
	
	@Override
	public void hide()
	{
		super.hide();
		getButtonLayerOn().setVisible(false);
	}
	
	public void setActive(boolean isActive)
	{
		getButtonLayerOn().setVisible(!isActive);
		getButtonLayerOff().setVisible(isActive);
	}
	
	@Override
	public void show()
	{
		getButtonLayerOff().setVisible(AudioManager.getInstance().isPlaying(sound));
		getButtonLayerOn().setVisible(!AudioManager.getInstance().isPlaying(sound));
	}
	
	public boolean intersects(IPoint point) 
	{
		return hitBox.contains(point);
	}
	
	protected void setHitBox(IRectangle hitBox)
	{
		this.hitBox = hitBox;
	}
}
