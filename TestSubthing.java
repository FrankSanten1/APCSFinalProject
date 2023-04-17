public class TestSubthing extends TestThingThing{
    double kloosh;

    public TestSubthing(double blam, double kloosh) {
        super(blam);
        this.kloosh = kloosh;
    }

    public double get() {return kloosh;}
    public void set(double kloosh) {this.kloosh = kloosh;}
}
