package Model;

public class History {
    String accListName;
    int accListImage;

    public History(String subName,int subImage)
    {
        this.accListImage=subImage;
        this.accListName=subName;
    }
    public String getoptName()
    {
        return accListName;
    }
    public int getoptImage()
    {
        return accListImage;
    }

}
