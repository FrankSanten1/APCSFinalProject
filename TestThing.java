public class TestThing implements Cloneable{
    private int jig;
    TestThingThing slun;

    public TestThing(int jig) {
        this.jig = jig;
        slun = new TestThingThing(1.5);
    }

    public TestThing(int jig, TestThingThing slun) {
        this.jig = jig;
        this.slun = slun;
    }

    public int getJig() {return jig;}
    public void setJig(int jig) {this.jig = jig;}

    public TestThing clone() throws CloneNotSupportedException{
        TestThing deepCopy = (TestThing)super.clone();

        deepCopy.slun = slun.clone();

        return deepCopy;
    }
}
