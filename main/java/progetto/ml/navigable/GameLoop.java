package progetto.ml.navigable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import playn.core.Font;
import playn.core.Mouse.ButtonEvent;
import progetto.ml.core.AgeFinderGame;
import progetto.ml.core.AgeFinderGame.Feature;
import progetto.ml.core.AgeFinderGameAssets;
import progetto.ml.core.AgeFinderGame.Category;
import progetto.ml.core.AgeFinderGameAssets.Face;
import progetto.ml.decisiontree.J48Tree;
import progetto.ml.media.Text;
import progetto.ml.navigable.menu.button.BackHomeButton;
import progetto.ml.navigable.menu.button.CheckBox;
import progetto.ml.navigable.menu.button.GameButton;
import progetto.ml.navigable.menu.button.NoButton;
import progetto.ml.navigable.menu.button.OkButton;
import progetto.ml.navigable.menu.button.RetryButton;
import progetto.ml.navigable.menu.button.StartButton;
import progetto.ml.navigable.menu.button.YesButton;
import pythagoras.f.Dimension;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

public class GameLoop implements Navigable
{
	private static final float DESCRIPTION_OFFSET_Y = 20.0f;
	private static final float DESCRIPTION_HEIGHT_POINT = 75.0f;
	private static final IRectangle SCROLL = new Rectangle(new Point(17.0f, 67.0f), new Dimension(259.0f, 355.0f));
	private static GameLoop instance;
	
	private AgeFinderGameAssets assets;
	private GameButton yesButton;
	private GameButton noButton;
	private GameButton backHomeButton;
	private GameButton retryButton;
	private GameButton startButton;
	private GameButton okButton;
	private GameButton selectionCategoryButton;
	private List<CheckBox> checkBoxes;
	private int[] featuresValues;
	private CheckBox currCheckBox;
	private List<Text> currentText;
	private String bestClassification;
	private double currentMeanDepth;
	private boolean isSolutionShown;
	
	private GameLoop()
	{
		featuresValues = new int[Feature.values().length];
		bestClassification = "";
		assets = AgeFinderGameAssets.getInstance();
		yesButton = new YesButton();
		noButton = new NoButton();
		startButton = new StartButton();
		backHomeButton = new BackHomeButton();
		retryButton = new RetryButton();
		okButton = new OkButton();
		selectionCategoryButton = new OkButton();
		yesButton.hide();
		noButton.hide();
		backHomeButton.hide();
		retryButton.hide();
		startButton.hide();
		okButton.hide();
		selectionCategoryButton.hide();
		checkBoxes = new LinkedList<CheckBox>();
		
		for (Category category : Category.values())
			checkBoxes.add(new CheckBox(category));
		
		currentMeanDepth = J48Tree.getInstance().getMeanDepth();
	}
	
	private void initText(String textValue)
	{
		currentText = new LinkedList<Text>();
		
		String[] tokenizedText = splitText(textValue, 22);
		float currentIndex = DESCRIPTION_HEIGHT_POINT;
		
		for (int i = 0; i < tokenizedText.length; i++)
		{
			Text text = new Text(tokenizedText[i], Font.Style.BOLD, 15, 0xFF362c26);
			text.setTranslation(SCROLL.x() + SCROLL.width() / 2 - text.width() / 2, currentIndex += DESCRIPTION_OFFSET_Y);
			text.setDepth(5.0f);
			text.init();
			currentText.add(text);
		}		
	}
	
	private String[] splitText(String gloss, int lineSize)
	{
		StringBuilder sb = new StringBuilder(gloss);
		int i = 0;
		while ((i = sb.indexOf(" ", i + lineSize)) != -1)
			sb.replace(i, i + 1, "\t");
		return sb.toString().split("\t");
	}

	public static GameLoop getInstance()
	{
		if (instance == null)
			instance = new GameLoop();
		
		return instance;
	}
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{
		if (isSolutionShown)
		{
			if (yesButton.intersects(new Point(event.x(), event.y())))
			{
				switchFace(Face.RIGHT);
				isSolutionShown = false;
				clearText();
				initText("I guessed it right! :) Do you want to test my skills again?");
				
				for (Text text : currentText)
					text.setVisible(true);
				
				yesButton.hide();
				noButton.hide();
				backHomeButton.show();
				retryButton.show();
			}
			
			else if (noButton.intersects(new Point(event.x(), event.y())))
			{
				switchFace(Face.WRONG);
				isSolutionShown = false;
				clearText();
				initText("Damn, you defeated me! Please select the correct answer so I can learn more about the characters and the next time it will be harder to beat me!");
				
				for (Text text : currentText)
					text.setVisible(true);
				
				yesButton.hide();
				noButton.hide();
				okButton.show();
			}
		}
		
		else if (backHomeButton.visible() && backHomeButton.intersects(new Point(event.x(), event.y())))
		{
			backHomeButton.play();
			hide();
			clearText();
			backHomeButton.getState().show();
			return backHomeButton.getState();
		}
		
		else if (retryButton.visible() && retryButton.intersects(new Point(event.x(), event.y())))
		{
			retryButton.play();
			hide();
			clearText();
			retryButton.getState().show();
			return retryButton.getState();
		}
		
		else if (okButton.visible() && okButton.intersects(new Point(event.x(), event.y())))
		{
			okButton.play();
			clearText();
			okButton.hide();
			selectionCategoryButton.show();
			assets.getCategoriesLayer().setVisible(true);
			
			for (CheckBox checkBox : checkBoxes)
				checkBox.show();
		}

		else if (selectionCategoryButton.visible())
		{
			boolean isCheckBoxClicked = false;

			for (CheckBox checkBox : checkBoxes)
			{
				if (checkBox.intersects(new Point(event.x(), event.y())))
				{
					isCheckBoxClicked = true;

					if (!checkBox.isChecked() && !Category.getCategoryText(checkBox.getCategory()).equals(bestClassification))
					{
						checkBox.check();
						currCheckBox = checkBox;
					}
					
					else
					{
						checkBox.uncheck();
						currCheckBox = null;
					}
					
					for (CheckBox otherCheckBox : checkBoxes)
						if (!otherCheckBox.equals(currCheckBox))
							otherCheckBox.uncheck();
				}
			}
			
			if (!isCheckBoxClicked && currCheckBox != null && !selectionCategoryButton.intersects(new Point(event.x(), event.y())))
			{
				for (CheckBox otherCheckBox : checkBoxes)
					otherCheckBox.uncheck();
				
				currCheckBox = null;
			}
			
			if (selectionCategoryButton.intersects(new Point(event.x(), event.y())) && currCheckBox != null)
			{
        		assets.getCategoriesLayer().setVisible(false);
        		switchFace(Face.RIGHT);
        		initText("Thanks a lot! Do you want to play one more game?");
        		
    			for (CheckBox checkBox : checkBoxes)
    				checkBox.hide();
    			
        		for (Text text : currentText)
        			text.setVisible(true);
        		
        		selectionCategoryButton.hide();
        		backHomeButton.show();
        		retryButton.show();
        		J48Tree.getInstance().updateDataset(featuresValues, currCheckBox.getCategory());
        		Arrays.fill(featuresValues, 0);
        		bestClassification = "";
        		currCheckBox = null;
			}
		}
		
		if (startButton.visible() && startButton.intersects(new Point(event.x(), event.y())))
		{
			startButton.play();
			startButton.hide();
			yesButton.show();
			noButton.show();
			clearText();
			nextQuestion();
		}
		
		else if (!startButton.visible() && !J48Tree.getInstance().hasSolution())
		{
			if (yesButton.intersects(new Point(event.x(), event.y())))
			{
				featuresValues[Feature.fromString(J48Tree.getInstance().getValue()).ordinal()] = +1;
				J48Tree.getInstance().answer(true);
			}
			
			else if (noButton.intersects(new Point(event.x(), event.y())))
			{
				featuresValues[Feature.fromString(J48Tree.getInstance().getValue()).ordinal()] = -1;
				J48Tree.getInstance().answer(false);
			}
	
			clearText();
			updateFace();
			
			if (!J48Tree.getInstance().hasSolution())
				nextQuestion();
			
			else
			{
				if (!isSolutionShown)
				{
					getSolution();
					isSolutionShown = true;
				}
			}
			
		}
		
		return this;
	}

	private void nextQuestion()
	{
		initText(J48Tree.getInstance().getValue());
		
		for (Text text : currentText)
			text.setVisible(true);
	}
	
	private void getSolution()
	{
		bestClassification = J48Tree.getInstance().getValue().toString();
		initText("the answer is: " + bestClassification + "!");
		
		for (Text text : currentText)
			text.setVisible(true);
	}
	
	private void updateFace()
	{
		if (currentMeanDepth > J48Tree.getInstance().getMeanDepth() && assets.getCurrentFace() != Face.QUIET_RIGHT)
			switchFace(Face.values()[(assets.getCurrentFace().ordinal() + 1)]);
		
		else if (currentMeanDepth < J48Tree.getInstance().getMeanDepth() && assets.getCurrentFace() != Face.QUIET_WRONG)
			switchFace(Face.values()[(assets.getCurrentFace().ordinal() - 1)]);
		
		currentMeanDepth = J48Tree.getInstance().getMeanDepth();
	}

	private void clearText()
	{
		Iterator<Text> textIterator = currentText.iterator();
		while (textIterator.hasNext())
		{
			Text text = textIterator.next();
			text.destroy();
			textIterator.remove();
		}		
	}

	@Override
	public void show()
	{
		initText("Think about a character and answer my questions. I will find out the category of the character you are thinking about!");
		
		assets.show();
		startButton.show();

		for (Text text : currentText)
			text.setVisible(true);
	}

	@Override
	public void hide()
	{
		assets.hide();
		yesButton.hide();
		noButton.hide();
		backHomeButton.hide();
		retryButton.hide();
		startButton.hide();
		okButton.hide();
		selectionCategoryButton.hide();
		
		for (CheckBox checkBox : checkBoxes)
			checkBox.hide();

		for (Text text : currentText)
			text.setVisible(false);
	}
	
	private void switchFace(Face newFace)
	{
		assets.getFaceLayer(assets.getCurrentFace()).setVisible(false);
		assets.setCurrentFace(newFace);
		assets.getFaceLayer(assets.getCurrentFace()).setVisible(true);
	}

	private void checkOverButton()
	{
		if (okButton.visible())
		{
			if (okButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && !okButton.isMouseOver())
				okButton.setMouseOver(true);

			else if (!okButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && okButton.isMouseOver())
				okButton.setMouseOver(false);
		}
		
		if (selectionCategoryButton.visible())
		{
			if (selectionCategoryButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && !selectionCategoryButton.isMouseOver())
				selectionCategoryButton.setMouseOver(true);

			else if (!selectionCategoryButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && selectionCategoryButton.isMouseOver())
				selectionCategoryButton.setMouseOver(false);
		}
		
		if (yesButton.visible())
		{
			if (yesButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && !yesButton.isMouseOver())
				yesButton.setMouseOver(true);

			else if (!yesButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && yesButton.isMouseOver())
				yesButton.setMouseOver(false);
		}
		
		if (noButton.visible())
		{
			if (noButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && !noButton.isMouseOver())
				noButton.setMouseOver(true);

			else if (!noButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && noButton.isMouseOver())
				noButton.setMouseOver(false);
		}
		
		if (backHomeButton.visible())
		{
			if (backHomeButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && !backHomeButton.isMouseOver())
				backHomeButton.setMouseOver(true);

			else if (!backHomeButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && backHomeButton.isMouseOver())
				backHomeButton.setMouseOver(false);
		}
		
		if (retryButton.visible())
		{
			if (retryButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && !retryButton.isMouseOver())
				retryButton.setMouseOver(true);

			else if (!retryButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && retryButton.isMouseOver())
				retryButton.setMouseOver(false);
		}
		
		if (startButton.visible())
		{
			if (startButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && !startButton.isMouseOver())
				startButton.setMouseOver(true);

			else if (!startButton.intersects(AgeFinderGame.getInstance().getPointerLocation()) && startButton.isMouseOver())
				startButton.setMouseOver(false);
		}		
	}
	
	@Override
	public void update(int delta)
	{
		checkOverButton();
	}
}
