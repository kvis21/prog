package places;

import java.util.Objects;

public class Sea extends Place{
    protected boolean open;
    public Sea(){
        super("море");
        this.open = false;
    }

    private Sea(String part){
        super(part);
        this.open = false;
    }

   public enum SeaParts {
        BAY("бухта");
    
        private Sea part;
        SeaParts(String part){
            this.part = new Sea(part);   
        }
        public Sea getPart() {
            return this.part;
        }
    }

    public void Open() {
        this.open = true;
        this.text = "открытое " + this.text;
    }

    public boolean isOpen() {
        return this.open;
    }
    public void resetState(){
        this.open = false;
        this.text = "море";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sea place = (Sea) o;
        return name == place.name & persons.equals(place.persons) & items.equals(place.items) & open == place.open;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, persons, items, open);
    }


    @Override
    public String toString(){
        return  String.format("Sea{name = %s, persons = %s, items = %s, open = %b}", name, persons, items, open);
        }
}
