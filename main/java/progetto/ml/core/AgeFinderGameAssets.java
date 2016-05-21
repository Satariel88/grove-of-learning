package progetto.ml.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import java.util.LinkedList;
import java.util.List;
import playn.core.Image;
import playn.core.ImageLayer;

import pythagoras.f.Dimension;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

public class AgeFinderGameAssets
{
	public static final String MAIN_THEME_AUDIO = "sound/music/skyrim";
	public static final String BUTTON_CLICK_AUDIO = "sound/sound_effects/button";
	private static final String GAME_BACKGROUND = "images/game/bg";
	private static final String GAME_CHAR = "images/game/char.png";
	private static final String GAME_CHAR_FACES = "images/game/faces.png";	
	private static final String QUESTION_SCROLL = "images/game/question_scroll.png";
	private static final String CATEGORIES = "images/game/categories.png";
	private static final IPoint CHAR_POINT = new Point(269.0f, 1.0f);
	private static final IPoint QUESTION_SCROLL_POINT = new Point(9.0f, 63.0f);
	private static final IPoint CATEGORIES_POINT = new Point(56.0f, 102.0f);
	private static final IRectangle FACE = new Rectangle(new Point(406.0f, 164.0f), new Dimension(64.0f, 80.0f));
	private static final int NUMBER_OF_BGS = 3;
	private static List<Image> bgImages;
	private static Image charImage;
	private static Image facesImage;
	private static Image questionScrollImage;
	private static Image categoriesImage;
	private static AgeFinderGameAssets instance;
	
	private List<ImageLayer> bgLayers;
	private ImageLayer charLayer;
	private ImageLayer questionScrollLayer;
	private ImageLayer categoriesLayer;
	private List<ImageLayer> faceLayers;
	private Face currentFace;
	private int currentBg;
	
	private AgeFinderGameAssets()
	{
		faceLayers = new LinkedList<ImageLayer>();
		initBgLayer();
		initCharLayer();
		initFaceLayers();
		initQuestionScrollLayer();
		currentFace = Face.NORMAL;
	}
	
	public enum Face
	{
		WRONG, QUIET_WRONG, NORMAL, QUIET_RIGHT, RIGHT;
	}
	
	public static AgeFinderGameAssets getInstance()
	{
		if (instance == null)
			instance = new AgeFinderGameAssets();
		
		return instance;
	}
	
	public static void loadAssets()
	{
		bgImages = new LinkedList<Image>();
		
		for (int i = 0; i < NUMBER_OF_BGS; i++)
			bgImages.add(assets().getImage(GAME_BACKGROUND + i + ".png"));

		charImage = assets().getImage(GAME_CHAR);
		facesImage = assets().getImage(GAME_CHAR_FACES);
		questionScrollImage = assets().getImage(QUESTION_SCROLL); 
		categoriesImage = assets().getImage(CATEGORIES); 
	}
	
	
	private void initBgLayer()
	{
		bgLayers = new LinkedList<ImageLayer>();
		
		for (Image bgImage : bgImages)
		{
    		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    		bgLayer.setDepth(0.0f);
    		bgLayer.setVisible(false);
    		graphics().rootLayer().add(bgLayer);
    		bgLayers.add(bgLayer);
		}
	}

	private void initCharLayer()
	{
		charLayer = graphics().createImageLayer(charImage);
		charLayer.setDepth(1.0f);
		charLayer.setVisible(false);
		charLayer.setTranslation(CHAR_POINT.x(), CHAR_POINT.y());
		graphics().rootLayer().add(charLayer);
	}
	
	private void initFaceLayers()
	{
		faceLayers.add(graphics().createImageLayer(facesImage.subImage(FACE.width() * Face.WRONG.ordinal(), 0, FACE.width(), FACE.height())));
		faceLayers.add(graphics().createImageLayer(facesImage.subImage(FACE.width() * Face.QUIET_WRONG.ordinal(), 0, FACE.width(), FACE.height())));
		faceLayers.add(graphics().createImageLayer(facesImage.subImage(FACE.width() * Face.NORMAL.ordinal(), 0, FACE.width(), FACE.height())));
		faceLayers.add(graphics().createImageLayer(facesImage.subImage(FACE.width() * Face.QUIET_RIGHT.ordinal(), 0, FACE.width(), FACE.height())));
		faceLayers.add(graphics().createImageLayer(facesImage.subImage(FACE.width() * Face.RIGHT.ordinal(), 0, FACE.width(), FACE.height())));

		for (Face face : Face.values())
		{
			faceLayers.get(face.ordinal()).setDepth(1.0f);
			faceLayers.get(face.ordinal()).setTranslation(FACE.x(), FACE.y());
			faceLayers.get(face.ordinal()).setVisible(false);
			graphics().rootLayer().add(faceLayers.get(face.ordinal()));
		}
	}

	private void initQuestionScrollLayer()
	{
		questionScrollLayer = graphics().createImageLayer(questionScrollImage);
		questionScrollLayer.setDepth(3.0f);
		questionScrollLayer.setVisible(false);
		questionScrollLayer.setTranslation(QUESTION_SCROLL_POINT.x(), QUESTION_SCROLL_POINT.y());
		categoriesLayer = graphics().createImageLayer(categoriesImage);
		categoriesLayer.setDepth(4.0f);
		categoriesLayer.setVisible(false);
		categoriesLayer.setTranslation(CATEGORIES_POINT.x(), CATEGORIES_POINT.y());
		graphics().rootLayer().add(questionScrollLayer);
		graphics().rootLayer().add(categoriesLayer);
	}
	
	public void show()
	{
		currentBg = (currentBg + 1) % (NUMBER_OF_BGS);
		bgLayers.get(currentBg).setVisible(true);
		charLayer.setVisible(true);
		questionScrollLayer.setVisible(true);
		currentFace = Face.NORMAL;
		faceLayers.get(currentFace.ordinal()).setVisible(true);
	}
	
	public void hide()
	{
		bgLayers.get(currentBg).setVisible(false);
		charLayer.setVisible(false);
		questionScrollLayer.setVisible(false);
		categoriesLayer.setVisible(false);
		
		for (Face face : Face.values())
			faceLayers.get(face.ordinal()).setVisible(false);
	}
	
	public ImageLayer getCharacterLayer()
	{
		return charLayer;
	}
	
	public ImageLayer getFaceLayer(Face face)
	{
		return faceLayers.get(face.ordinal());
	}
	
	public ImageLayer getQuestionScrollLayer()
	{
		return charLayer;
	}
	
	public ImageLayer getCategoriesLayer()
	{
		return categoriesLayer;
	}
	
	public Face getCurrentFace()
	{
		return currentFace;
	}
	
	public void setCurrentFace(Face face)
	{
		currentFace = face;
	}
}
