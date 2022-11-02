import ObjEmp.ObjEmp;
import exception.IncompatibleOperands;

public class PileRPL
{
    private int nbObj;
    private ObjEmp[] tab;


    public PileRPL(int size)
    {
        this.nbObj = 0;
        this.tab = new ObjEmp[size];
    }


    public boolean isEmpty()
    {
        return nbObj == 0;
    }


    public boolean isFull()
    {
        return nbObj == tab.length;
    }


    public boolean push(ObjEmp o)
    {
        if(isFull() || o == null)
            return false;
    
        tab[nbObj] = o;
        nbObj++;
        return true;
    }


    public ObjEmp pop()
    {
        return isEmpty() ? null : tab[--nbObj];
    }


    public void empty()
    {
        while(!isEmpty())
            pop();
    }


    public void add() throws IncompatibleOperands
    {
        ObjEmp o2 = pop();
        if(o2 == null)
            return;
        
        ObjEmp o1 = pop();
        if(o1 == null)
        {
            push(o2);
            return;
        }

        if(!o1.getClass().equals(o2.getClass()))
        {
            push(o1);
            push(o2);
            throw new IncompatibleOperands();
        }

        push(o1.add(o2));
    }


    public void substract() throws IncompatibleOperands
    {
        ObjEmp o2 = pop();
        if(o2 == null)
            return;
        
        ObjEmp o1 = pop();
        if(o1 == null)
        {
            push(o2);
            return;
        }

        if(!o1.getClass().equals(o2.getClass()))
        {
            push(o1);
            push(o2);
            throw new IncompatibleOperands();
        }

        push(o1.substract(o2));
    }


    public void multiply() throws IncompatibleOperands
    {
        ObjEmp o2 = pop();
        if(o2 == null)
            return;
        
        ObjEmp o1 = pop();
        if(o1 == null)
        {
            push(o2);
            return;
        }

        if(!o1.getClass().equals(o2.getClass()))
        {
            push(o1);
            push(o2);
            throw new IncompatibleOperands();
        }

        push(o1.multiply(o2));
    }


    public void divide() throws IncompatibleOperands
    {
        ObjEmp o2 = pop();
        if(o2 == null)
            return;
        
        ObjEmp o1 = pop();
        if(o1 == null)
        {
            push(o2);
            return;
        }

        if(!o1.getClass().equals(o2.getClass()))
        {
            push(o1);
            push(o2);
            throw new IncompatibleOperands();
        }

        push(o1.divide(o2));
    }


    public void print()
    {
        for(int i=tab.length-1; i>=nbObj; i--)
            System.out.println(i + " !   !");
        
        for(int i=nbObj-1; i>=0; i--)
            System.out.println(i + " ! " + tab[i] + " !");
        
        System.out.println("  + - +");
    }


    @Override
    public String toString()
    {
        if(nbObj == 0)
            return "La pile est vide";
        else
        {
            StringBuilder sb = new StringBuilder("La pile contient : ");
            for(int i=0; i<nbObj; i++)
                sb.append(tab[i] + " ");
            return sb.toString();
        }
    }
}