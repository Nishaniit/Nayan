package Game1;
public class ModelBoom
{
    private double size;
    private float angle;
    public double getSize()
    {
        return size;
    }
    public void setSize(double size)
    {
        this.size=size;
    }
    public float getAngle()
    {
        return angle;
    }
    public void setAngle(float angle)
    {
        this.angle=angle;
    }
    public ModelBoom(double size,float angle)
    {
        this.size=size;
        this.angle=angle;
    }
    public ModelBoom()
    {}
}