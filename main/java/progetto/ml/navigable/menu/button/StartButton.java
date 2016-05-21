package progetto.ml.navigable.menu.button;

import playn.core.Font.Style;
import progetto.ml.media.Text;
import progetto.ml.navigable.GameLoop;
import progetto.ml.navigable.Navigable;
import pythagoras.f.IPoint;
import pythagoras.f.Point;

public class StartButton extends GameButton
{
	private static IPoint POSITION = new Point(94.0f, 374.0f);
	private static Text TEXT = new Text("Start", Style.BOLD, 12.0f, DEFAULT_TEXT_COLOR);
	private static Text OVER_TEXT = new Text("Start", Style.BOLD, 12.0f, DEFAULT_OVER_TEXT_COLOR);
	
	@Override
	public IPoint getPosition()
	{
		return POSITION;
	}

	@Override
	public Text getText()
	{
		return TEXT;
	}
	
	@Override
	public Text getTextOver()
	{
		return OVER_TEXT;
	}

	@Override
	public Navigable getState()
	{
		return GameLoop.getInstance();
	}
}
