package progetto.ml.decisiontree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;
import progetto.ml.core.AgeFinderGame.Category;
import progetto.ml.core.AgeFinderGame.Feature;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class J48Tree
{
//	private static final String DATASET_FILE = "./dataset.arff";
	private static final String DATASET_FILE = "/Users/epilol/agefinder/assets/src/main/resources/assets/dataset/dataset.arff";
	private static J48Tree instance;

	private Map<String, List<List<String>>> dataset = new HashMap<String, List<List<String>>>();
	private J48 tree;
	private List<String> features;
	private J48Node node;
	private J48Node root;

	public J48Tree()
	{
		features = new LinkedList<String>();
		reset();
	}

	public static J48Tree getInstance()
	{
		if (instance == null)
			instance = new J48Tree();

		return instance;
	}

	public void reset()
	{
		dataset = getDatasetInstances();
		tree = generateTree();
		String treeText = tree.toString();
		String[] tokenizedTree = treeText.split("\n");
		tokenizedTree = Arrays.copyOfRange(tokenizedTree, 3, tokenizedTree.length - 4);
		root = new J48Node(tokenizedTree[0].replaceAll("\\?.*", "?").replace("-", " "), 0, false);
		buildTree(treeText);
	}

	private Map<String, List<List<String>>> getDatasetInstances()
	{
		dataset = new HashMap<String, List<List<String>>>();
		BufferedReader br = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(DATASET_FILE), "UTF-8"));
			String line = br.readLine();

			for (Feature feature : Feature.values())
				features.add(feature.getText());

			while (!(line = br.readLine()).equals("@data"))
				;

			while ((line = br.readLine()) != null)
			{
				List<String> instanceValues = new LinkedList<String>();
				String[] instance = line.split(",");
				String category = instance[instance.length - 1].replace("'", "");

				if (!dataset.containsKey(category))
					dataset.put(category, new LinkedList<List<String>>());

				for (int i = 0; i < instance.length - 1; i++)
					instanceValues.add(instance[i]);

				dataset.get(category).add(instanceValues);
			}
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}

		finally
		{
			try
			{
				if (br != null)
					br.close();
			}

			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return dataset;
	}

	private void buildTree(String treeText)
	{
		String[] tokenizedTree = treeText.split("\n");
		tokenizedTree = Arrays.copyOfRange(tokenizedTree, 3, tokenizedTree.length - 4);
		Stack<J48Node> nodeStack = new Stack<J48Node>();
		node = root;
		nodeStack.push(node);

		for (int i = 1; i < tokenizedTree.length; i++)
		{
			int depth = tokenizedTree[i].replaceAll("[^\\|]", "").length();
			int previousDepth = nodeStack.get(nodeStack.size() - 1).getDepth();

			if (depth < previousDepth)
			{
				int nodesToRemove = previousDepth - depth;

				for (int j = 0; j < nodesToRemove; j++)
					nodeStack.pop();

				if (tokenizedTree[i].contains(":"))
					nodeStack.get(nodeStack.size() - 1).addChild(new J48Node(tokenizedTree[i], depth + 1));

			}

			else if (depth > previousDepth)
			{
				J48Node node = new J48Node(tokenizedTree[i], depth, false);
				nodeStack.get(nodeStack.size() - 1).addChild(node);
				nodeStack.push(node);

				if (tokenizedTree[i].contains(":"))
					nodeStack.get(nodeStack.size() - 1).addChild(new J48Node(tokenizedTree[i], depth + 1));
			}

			else if (tokenizedTree[i].contains(":"))
				nodeStack.get(nodeStack.size() - 1).addChild(new J48Node(tokenizedTree[i], depth + 1));
		}
	}

	private J48 generateTree()
	{
		J48 tree = null;

		try
		{
			tree = new J48();
			tree.buildClassifier(getDataset());
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		return tree;
	}

	private Instances getDataset()
	{
		BufferedReader br = null;
		Instances dataset = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(DATASET_FILE), "UTF-8"));
			dataset = new Instances(br);
			dataset.setClassIndex(dataset.numAttributes() - 1);
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}

		finally
		{
			try
			{
				if (br != null)
					br.close();
			}

			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return dataset;
	}

	public double getMeanDepth()
	{
		return node.getMeanDepth();
	}
	
	public boolean hasSolution()
	{
		return node.isLeaf();
	}

	public String getValue()
	{
		return node.getNodeValue();
	}

	public void answer(boolean b)
	{
		node = b ? node.getTrueChild() : node.getFalseChild();
	}

	public void updateDataset(int[] featuresValues, Category category)
	{
		BufferedWriter bw = null;
		int[] meanValues = Arrays.copyOf(featuresValues, featuresValues.length);

		try
		{
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DATASET_FILE, true), "UTF-8"));

			for (Entry<String, List<List<String>>> entry : dataset.entrySet())
			{
				if (entry.getKey().equals(Category.getCategoryText(category)))
					for (List<String> instance : entry.getValue())
						for (int i = 0; i < instance.size(); i++)
							if (featuresValues[i] == 0)
								meanValues[i] += Integer.parseInt(instance.get(i).replace("'", ""));
			}

			String instance = "";

			for (int i = 0; i < meanValues.length; i++)
				instance += meanValues[i] >= 0 ? "'+1'," : "'-1',";

			instance += "'" + Category.getCategoryText(category) + "'";
			bw.append(instance + "\n");
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}

		finally
		{
			try
			{
				if (bw != null)
					bw.close();
			}

			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
