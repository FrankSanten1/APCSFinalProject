public class TestThingThing implements Cloneable{
    //me testing out how deepCopies work
    double blam;

    public TestThingThing(double blam) {
        this.blam = blam;
    }
    
    public TestThingThing clone() throws CloneNotSupportedException {
        return (TestThingThing) super.clone();
    }
    

    public double get() {return blam;}
    public void set(double blam) {this.blam = blam;}
}
