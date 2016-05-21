package progetto.ml.core;

import static playn.core.PlayN.mouse;
import java.io.Serializable;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import progetto.ml.media.AudioManager;
import progetto.ml.media.AudioManager.SoundType;
import progetto.ml.navigable.Navigable;
import progetto.ml.navigable.menu.MainMenu;
import progetto.ml.navigable.menu.Menu;
import progetto.ml.navigable.menu.button.Button;
import pythagoras.f.IPoint;
import pythagoras.f.Point;

public class AgeFinderGame
{
	private static AgeFinderGame instance;

	private Navigable currentState;
	private IPoint pointerLocation;

	private AgeFinderGame()
	{

	}

	public enum Category
	{
		ART(0, 0), MOVIES_THEATRE_REAL(1, 0), MOVIES_TV_FICTION(2, 0), CRIME(3, 0), PHILOSOPHY(4, 0), CARTOONS_COMICS_REAL(5, 0), 
		CARTOONS_COMICS_FICTION(6, 0), LITERATURE_REAL(7, 0), LITERATURE_THEATRE_FICTION(8, 0),

		MEDIA(0, 1), MUSIC(1, 1), POLITICS(2, 1), RELIGION_REAL(3, 1), RELIGION_MYTHOLOGY(4, 1), SCIENCE_MEDICINE(5, 1), SPORT(
				6, 1), HISTORY(7, 1), VIDEOGAMES_INTERNET(8, 1);

		private int x;
		private int y;

		Category(int y, int x)
		{
			this.x = x;
			this.y = y;
		}

		public int x()
		{
			return x;
		}

		public int y()
		{
			return y;
		}
		
		public static String getCategoryText(Category category)
		{
			switch (category)
			{
				case ART:
					return "art";

				case MOVIES_THEATRE_REAL:
					return "movies & theatre (real character)";

				case MOVIES_TV_FICTION:
					return "movies & television (fictional character)";

				case CRIME:
					return "crime";

				case PHILOSOPHY:
					return "philosophy";

				case CARTOONS_COMICS_FICTION:
					return "comics & cartoons (fictional character)";

				case CARTOONS_COMICS_REAL:
					return "comics & cartoons (real character)";
					
				case LITERATURE_REAL:
					return "literature (real character)";

				case LITERATURE_THEATRE_FICTION:
					return "literature & theatre (fictional character)";

				case MEDIA:
					return "media";

				case MUSIC:
					return "music";

				case POLITICS:
					return "politics";

				case RELIGION_REAL:
					return "religion (real character)";

				case RELIGION_MYTHOLOGY:
					return "religion & mythology";

				case SCIENCE_MEDICINE:
					return "science & medicine";

				case SPORT:
					return "sport";

				case HISTORY:
					return "history";

				case VIDEOGAMES_INTERNET:
					return "videogames & internet";
						
				default:
					return null;
			}
		}
		
		public static Category getCategoryFromText(Serializable category)
		{
			String categoryText = category.toString();
			switch (categoryText)
			{
				case "art":
					return ART;

				case "movies & theatre (real character)":
					return MOVIES_THEATRE_REAL;

				case "movies & television (fictional character)":
					return MOVIES_TV_FICTION;

				case "crime":
					return CRIME;

				case "philosophy":
					return PHILOSOPHY;

				case "comics & cartoons (fictional character)":
					return CARTOONS_COMICS_FICTION;

				case "comics & cartoons (real character)":
					return CARTOONS_COMICS_REAL;
					
				case "literature (real character)":
					return LITERATURE_REAL;

				case "literature & theatre (fictional character)":
					return LITERATURE_THEATRE_FICTION;

				case "media":
					return MEDIA;

				case "music":
					return MUSIC;

				case "politics":
					return POLITICS;

				case "religion (real character)":
					return RELIGION_REAL;

				case "religion & mythology":
					return RELIGION_MYTHOLOGY;

				case "science & medicine":
					return SCIENCE_MEDICINE;

				case "sport":
					return SPORT;

				case "history":
					return HISTORY;

				case "videogames & internet":
					return VIDEOGAMES_INTERNET;
						
				default:
					return null;
			}
		}
	}

	public enum Feature
	{
		Q1("is it alive?"), Q2("is it real?"), Q3("did it ever get in trouble with the law?"), Q4("did it ever appear on the tv screen?"), 
		Q5("is it known for its ideology?"), Q6("is it studied at school?"), Q7("is it a woman?"), Q8("is it athletic?"), 
		Q9("did it ever win a prize?"), Q10("is it related to technology?"), Q11("do kids know it?"), Q12("is it creative?"), 
		Q13("can you control it?"), Q14("is it able to revive?"), Q15("has it been known for more than a century?"), 
		Q16("is it human like?"), Q17("has it ever been in front of a camera?"), Q18("is it good at drawing?"), 
		Q19("has it ever pretended to be someone else?"), Q20("did it direct other people?"), Q21("is it related to the supernatural?");

		private String text;

		Feature(String text)
		{
			this.text = text;
		}

		public String getText()
		{
			return this.text;
		}

		public static Feature fromString(String text)
		{
			if (text != null)
			{
				for (Feature feature : Feature.values())
				{
					if (text.equalsIgnoreCase(feature.text))
						return feature;
				}
			}
			
			throw new IllegalArgumentException("No constant with text " + text + " found");
		}
	}

	public static AgeFinderGame getInstance()
	{
		if (instance == null)
			instance = new AgeFinderGame();

		return instance;
	}

	public static void loadAssets()
	{
		Button.loadAssets();
		Menu.loadAssets();
		AgeFinderGameAssets.loadAssets();
	}

	public void init()
	{
		setMouseListener();
		initState();
		AudioManager.getInstance().addMusic(SoundType.MAIN_THEME, AgeFinderGameAssets.MAIN_THEME_AUDIO);
		AudioManager.getInstance().addSound(SoundType.BUTTON_CLICK, AgeFinderGameAssets.BUTTON_CLICK_AUDIO);
		AudioManager.getInstance().play(SoundType.MAIN_THEME);
	}

	public void setMouseListener()
	{
		pointerLocation = new Point();

		mouse().setListener(new Mouse.Listener()
		{
			@Override
			public void onMouseDown(ButtonEvent event)
			{
				currentState = currentState.onMouseDown(event);
			}

			@Override
			public void onMouseMove(MotionEvent event)
			{
				pointerLocation = new Point(event.localX(), event.localY());
			}

			@Override
			public void onMouseUp(ButtonEvent event)
			{
			}

			@Override
			public void onMouseWheelScroll(WheelEvent event)
			{

			}
		});
	}

	public IPoint getPointerLocation()
	{
		return pointerLocation;
	}

	public Navigable getCurrentState()
	{
		return currentState;
	}

	public void setCurrentState(Navigable state)
	{
		currentState = state;
	}

	public void initState()
	{
		currentState = MainMenu.getInstance();
		currentState.show();
	}

	public void update(int delta)
	{
		currentState.update(delta);
	}

	public void paint(float alpha)
	{
	}
}
