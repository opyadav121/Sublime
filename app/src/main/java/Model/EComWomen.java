package Model;

public class EComWomen {
    String subListName;
    int subListImage;

    public EComWomen(String subName,int subImage)
    {
        this.subListImage=subImage;
        this.subListName=subName;
    }
    public String getsubName()
    {
        return subListName;
    }
    public int getSubImage()
    {
        return subListImage;
    }
}
