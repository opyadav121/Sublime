package Model;

public class EComMen {
    String subListName;
    int subListImage;

    public EComMen(String subName,int subImage)
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
