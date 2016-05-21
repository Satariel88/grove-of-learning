package progetto.ml.navigable.menu;

import static playn.core.PlayN.assets;
import java.util.LinkedList;
import java.util.List;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import progetto.ml.media.AudioManager;
import progetto.ml.media.AudioManager.SoundType;
import progetto.ml.navigable.Navigable;
import progetto.ml.navigable.menu.button.AcceptButton;
import progetto.ml.navigable.menu.button.ActiveButton;
import progetto.ml.navigable.menu.button.Button;
import progetto.ml.navigable.menu.button.CancelButton;
import progetto.ml.navigable.menu.button.SwitchButton;
import pythagoras.f.Point;

public class OptionsMenu extends Menu
{
	private static final String BACKGROUND = "images/options_bg.png";
	private static Image bgImage;
	private static OptionsMenu instance;
	
	private Button acceptButton;
	private Button cancelButton;
	private SwitchButton musicButton;
	private SwitchButton soundButton;
	private List<Button> optionButtons;
	private ImageLayer background;
	
	private boolean isMusicPlaying;
	private boolean isSoundPlaying;
	private boolean isSavedMusicPlaying;
	private boolean isSavedSoundPlaying;
	
	private OptionsMenu()
	{
		super(bgImage);
		isMusicPlaying = AudioManager.getInstance().isPlaying(SoundType.MAIN_THEME);
		isSoundPlaying = AudioManager.getInstance().isPlaying(SoundType.BUTTON_CLICK);
		isSavedMusicPlaying = AudioManager.getInstance().isPlaying(SoundType.MAIN_THEME);
		isSavedSoundPlaying = AudioManager.getInstance().isPlaying(SoundType.BUTTON_CLICK);
		optionButtons = new LinkedList<Button>();
		acceptButton = new AcceptButton();
		cancelButton = new CancelButton();
		musicButton = new ActiveButton(new Point(329.0f, 173.0f), SoundType.MAIN_THEME);
		soundButton = new ActiveButton(new Point(329.0f, 240.0f), SoundType.BUTTON_CLICK);
		optionButtons.add(acceptButton);
		optionButtons.add(cancelButton);
		optionButtons.add(musicButton);
		optionButtons.add(soundButton);
	}
	
	public static OptionsMenu getInstance()
	{
		if (instance == null)
			instance = new OptionsMenu();
		
		return instance;
	}
	
	public static void loadAssets()
	{
		bgImage = assets().getImage(BACKGROUND);
	}

	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{

		if (event.button() == Mouse.BUTTON_LEFT)
		{
			if (acceptButton.intersects(new Point(event.localX(), event.localY())))
			{
				acceptButton.play();
				AudioManager.getInstance().setSoundVolume(SoundType.MAIN_THEME, isMusicPlaying ? 0.5f : 0.0f);
				AudioManager.getInstance().setSoundVolume(SoundType.BUTTON_CLICK, isSoundPlaying ? 0.5f : 0.0f);
				isSavedMusicPlaying = isMusicPlaying;
				isSavedSoundPlaying = isSoundPlaying;
				hide();
				acceptButton.getState().show();
				return acceptButton.getState();
			}
			
			if (cancelButton.intersects(new Point(event.localX(), event.localY())))
			{
				if (isSoundPlaying)
				{
					AudioManager.getInstance().setSoundVolume(SoundType.BUTTON_CLICK, 0.8f);
					cancelButton.play();
				}
				
				isMusicPlaying = isSavedMusicPlaying;
				isSoundPlaying = isSavedSoundPlaying;
				hide();
				cancelButton.getState().show();
				AudioManager.getInstance().setSoundVolume(SoundType.MAIN_THEME, isSavedMusicPlaying ? 0.8f : 0.0f);
				AudioManager.getInstance().setSoundVolume(SoundType.BUTTON_CLICK, isSavedSoundPlaying ? 0.8f : 0.0f);
				return cancelButton.getState();
			}
			
			if (musicButton.intersects(new Point(event.localX(), event.localY())))
			{
				musicButton.play();
				isMusicPlaying = !isMusicPlaying;
				musicButton.setActive(isMusicPlaying);
				AudioManager.getInstance().setSoundVolume(SoundType.MAIN_THEME, isMusicPlaying ? 0.8f : 0.0f);
			}
			
			if (soundButton.intersects(new Point(event.localX(), event.localY())))
			{
				soundButton.play();
				isSoundPlaying = !isSoundPlaying;
				soundButton.setActive(isSoundPlaying);
				AudioManager.getInstance().setSoundVolume(SoundType.BUTTON_CLICK, isSoundPlaying ? 0.8f : 0.0f);
			}
		}
		
		return this;
	}
	
	@Override
	public List<Button> getButtons()
	{
		return optionButtons;
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
}
