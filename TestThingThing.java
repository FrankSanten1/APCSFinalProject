public class TestThingThing implements Cloneable{
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
