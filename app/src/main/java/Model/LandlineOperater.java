package Model;

public class LandlineOperater {
    String optListName;
    int optListImage;

    public LandlineOperater(String subName,int subImage)
    {
        this.optListImage=subImage;
        this.optListName=subName;
    }
    public String getoptName()
    {
        return optListName;
    }
    public int getoptImage()
    {
        return optListImage;
    }
}
