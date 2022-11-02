package ObjEmp;

public class IntegerEmp implements ObjEmp
{
    private int value;

    public IntegerEmp(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    @Override
    public ObjEmp add(ObjEmp o)
    {
        IntegerEmp numberEmp = (IntegerEmp) o;
        return (ObjEmp) new IntegerEmp(value + numberEmp.getValue());
    }

    @Override
    public ObjEmp substract(ObjEmp o)
    {
        IntegerEmp numberEmp = (IntegerEmp) o;
        return (ObjEmp) new IntegerEmp(value - numberEmp.getValue());
    }

    @Override
    public ObjEmp multiply(ObjEmp o)
    {
        IntegerEmp numberEmp = (IntegerEmp) o;
        return (ObjEmp) new IntegerEmp(value * numberEmp.getValue());
    }

    @Override
    public ObjEmp divide(ObjEmp o)
    {
        IntegerEmp numberEmp = (IntegerEmp) o;
        return (numberEmp.getValue() == 0) ? this : (ObjEmp) new IntegerEmp(value / numberEmp.getValue());
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }
}