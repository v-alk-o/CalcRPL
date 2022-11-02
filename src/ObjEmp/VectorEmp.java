package ObjEmp;

public class VectorEmp implements ObjEmp
{
    private int x;
    private int y;
    
    public VectorEmp(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public ObjEmp add(ObjEmp o)
    {
        VectorEmp vectorEmp = (VectorEmp) o; 
        return (ObjEmp) new VectorEmp(x + vectorEmp.getX(), y + vectorEmp.getY());
    }

    @Override
    public ObjEmp substract(ObjEmp o)
    {
        VectorEmp vectorEmp = (VectorEmp) o; 
        return (ObjEmp) new VectorEmp(x - vectorEmp.getX(), y - vectorEmp.getY());
    }

    @Override
    public ObjEmp multiply(ObjEmp o)
    {
        VectorEmp vectorEmp = (VectorEmp) o; 
        return (ObjEmp) new VectorEmp(x * vectorEmp.getX(), y * vectorEmp.getY());
    }

    @Override
    public ObjEmp divide(ObjEmp o)
    {
        VectorEmp vectorEmp = (VectorEmp) o; 
        return (vectorEmp.getX() == 0 || vectorEmp.getY() == 0) ? this : (ObjEmp) new VectorEmp(x / vectorEmp.getX(), y / vectorEmp.getY());
    }

    @Override
    public String toString()
    {
        return String.format("(%d,%d)", x, y);
    }
}