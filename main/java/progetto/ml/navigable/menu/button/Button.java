package progetto.ml.navigable.menu.button;

import playn.core.ImageLayer;
import progetto.ml.media.AudioManager;
import progetto.ml.media.AudioManager.SoundType;
import progetto.ml.navigable.Navigable;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;

public abstract class Button
{
	public enum ButtonType
	{
		NORMAL(0), OVER(1), ON(1);
		
		private int offset;
		
		ButtonType(int offset)
		{
			this.offset = offset;
		}
		
		public int getOffset()
		{
			return offset;
		}
	}

	public static void loadAssets()
	{
		RectButton.loadAssets();
		CircleButton.loadAssets();
		SwitchButton.loadAssets();
		CheckBox.loadAssets();
	}
	
	public boolean visible()
	{
		return getButtonLayerOff().visible();
	}
	
	public void show()
	{
		getButtonLayerOff().setVisible(true);
	}
	
	public void hide()
	{
		getButtonLayerOff().setVisible(false);
	}
	
	public void play()
	{
		AudioManager.getInstance().play(SoundType.BUTTON_CLICK);
	}
	
	public boolean isMouseOver()
	{
		return false;
	}
	
	public void setMouseOver(boolean isOver)
	{
		
	}

	public abstract ImageLayer getButtonLayerOff();
	
	public abstract boolean intersects(IPoint point); 
	
	public abstract IDimension getSize();
	
	public abstract IPoint getPosition();
	
	public abstract Navigable getState();
}
