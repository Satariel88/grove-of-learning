package progetto.ml.core;

import playn.core.Game;

public class AgeFinder extends Game.Default
{
	public AgeFinder()
	{
		super(33);
	}

	@Override
	public void init()
	{
		AgeFinderGame.loadAssets();
		AgeFinderGame.getInstance().init();
	}
	
	@Override
	public void update(int delta)
	{
		AgeFinderGame.getInstance().update(delta);
	}

	@Override
	public void paint(float alpha)
	{
		AgeFinderGame.getInstance().paint(alpha);
	}
}
