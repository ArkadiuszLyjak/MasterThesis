package testNewFunc.genType;

public class Ograniczenia {

    static class A {
        public A() {
        }
    }

    static class B extends A {
        public B() {
        }
    }

    static class C extends A {
        public C() {
        }
    }

    static class D {
        public D() {
        }
    }




    /*public static void main(String[] args) {
        System.out.println("tu bêdzie jazdaaa");
    }*/
}


class GenT<T> {
    T ob;

    public GenT(T ob) {
        this.ob = ob;
    }

    public T getOb() {
        return ob;
    }

    public void setOb(T ob) {
        this.ob = ob;
    }

    @Override
    public String toString() {
        return "GenT{" +
                "ob=" + ob +
                '}';
    }
}

class UnbundedWildCard {

    static void test(GenT<? extends Ograniczenia.A> object) {
        System.out.println(object);
    }

    public static void main(String[] args) {

        Ograniczenia.A a = new Ograniczenia.A();
        Ograniczenia.B b = new Ograniczenia.B();
        Ograniczenia.C c = new Ograniczenia.C();
        Ograniczenia.D d = new Ograniczenia.D();

        GenT<Ograniczenia.A> aGenT = new GenT<>(a);
        GenT<Ograniczenia.B> bGenT = new GenT<>(b);
        GenT<Ograniczenia.C> cGenT = new GenT<>(c);
        GenT<Ograniczenia.D> dGenT = new GenT<>(d);

        test(aGenT);
        test(bGenT);
        test(cGenT);
//        test(dGenT);



//        test();

    }
}
