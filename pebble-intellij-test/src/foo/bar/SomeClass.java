package foo.bar;

@SuppressWarnings("unused")
public class SomeClass {

    public SomeClass() {
    }

    public Integer publicField;

    private Integer privateField;

    protected Integer protectedField;

    Integer packagePrivateField;

    public boolean isProperty1() {
        return false;
    }

    public boolean hasProperty2() {
        return false;
    }

    public Integer getProperty3() {
        return 1;
    }

    private boolean isProperty1Private() {
        return false;
    }

    private boolean hasProperty2Private() {
        return false;
    }

    private Integer getProperty3Private() {
        return 1;
    }

    protected boolean isProperty1Protected() {
        return false;
    }

    protected boolean hasProperty2Protected() {
        return false;
    }

    protected Integer getProperty3Protected() {
        return 1;
    }

    boolean isProperty1PackagePrivate() {
        return false;
    }

    boolean hasProperty2PackagePrivate() {
        return false;
    }

    Integer getProperty3PackagePrivate() {
        return 1;
    }

    public void voidMethod() {
    }

    public int intMethod() {
        return 1;
    }

    public Integer integerMethod() {
        return 1;
    }

    protected void voidMethodProtected() {
    }

    protected int intMethodProtected() {
        return 1;
    }

    protected Integer integerMethodProtected() {
        return 1;
    }

    private void voidMethodPrivate() {
    }

    private int intMethodPrivate() {
        return 1;
    }

    private Integer integerMethodPrivate() {
        return 1;
    }

    void voidMethodPackagePrivate() {
    }

    int intMethodPackagePrivate() {
        return 1;
    }

    Integer integerMethodPackagePrivate() {
        return 1;
    }

    public void overloaded(String s) {}
    public void overloaded(Integer i) {}
    public void overloaded(String s, String s2) {}
}
