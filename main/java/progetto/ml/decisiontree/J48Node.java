package progetto.ml.decisiontree;

public class J48Node
{
	private J48Node trueChild;
	private J48Node falseChild;
	private String value;
	private boolean isLeaf;
	private int depth;
	
	public J48Node(String text, int depth, boolean isLeaf)
	{
		this.depth = depth;
		this.isLeaf = isLeaf;
		
		if (!isLeaf)
		{
    		if (text.contains(":"))
    		{
    			String[] tokenized = text.replace("-", " ").replaceAll("[^a-zA-Z  :\\&\\(\\)]", "").split(":");
    			value = tokenized[0].trim() + "?";
    			addChild(new J48Node(tokenized[1].substring(0, tokenized[1].length() - 2).trim(), depth + 1, true));
    		}
    		
    		else
    			value = text.replace("-", " ").replaceAll("[^a-zA-Z ]", "").trim() + "?";
    	}
    		
		else
			this.value = text;
	}
	
	public J48Node(String text, int depth)
	{
		String[] tokenized = text.replace("-", " ").replaceAll("[^a-zA-Z  :\\&\\(\\)]", "").split(":");
		this.value = tokenized[1].substring(0, tokenized[1].length() - 2).trim();
		this.depth = depth;
		this.isLeaf = true;
	}
	
	public void addChild(J48Node child)
	{
		if (trueChild == null)
			trueChild = child;
		
		else
			falseChild = child;
	}
	
	public J48Node getTrueChild()
	{
		return trueChild;
	}
	
	public J48Node getFalseChild()
	{
		return falseChild;
	}
	
	public boolean isLeaf()
	{
		return isLeaf;
	}
	
	public String getNodeValue()
	{
		return value;
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public double getMeanDepth()
	{
		return getTotalLeavesDepth(this) * (1 / getLeavesNumber(this));
	}
	
	private double getLeavesNumber(J48Node node)
	{
		if (node.isLeaf())
			return 1;
		
		return getLeavesNumber(node.getTrueChild()) + getLeavesNumber(node.getFalseChild());
	}
	
	private double getTotalLeavesDepth(J48Node node)
	{
		if (node.isLeaf())
			return node.getDepth();
		
		return getTotalLeavesDepth(node.getTrueChild()) + getTotalLeavesDepth(node.getFalseChild());
	}
	
	public int getLongestPath()
	{
		return getLongestPath(this);
	}
	
	private int getLongestPath(J48Node node)
	{
		if (node.isLeaf())
			return 0;
		
		return 1 + Math.max(getLongestPath(node.getTrueChild()), getLongestPath(node.getFalseChild()));
	}
	
	public int getSubtreeSize()
	{
		return getSubtreeSize(this);
	}
	
	private int getSubtreeSize(J48Node node)
	{
		if (node.isLeaf())
			return 1;
		
		return 1 + getSubtreeSize(node.getTrueChild()) + getSubtreeSize(node.getFalseChild());
	}
	
	@Override
	public String toString()
	{
		return value;
	}
}
