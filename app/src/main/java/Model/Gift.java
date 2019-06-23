package Model;

public class Gift {
    String subListName;
    int subListImage;

    public Gift(String subName,int subImage)
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
