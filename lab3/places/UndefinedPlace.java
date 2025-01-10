package places;

public class UndefinedPlace extends Place{
    public UndefinedPlace(){
        super("неопределенное место");
    }

    public void resetState(){
        this.text = "";
        persons.clear();
        items.clear();
    }
    
}
