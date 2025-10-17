package com.jad;

public class Corge {

    private Foo foo;

    public Corge(Foo foo) {
        this.foo = foo;

        if (foo != null) {
            foo.setCorge(this);
        }
    }

    public Foo getFoo() {
        return this.foo;
    }

    public void setFoo(Foo foo) {
        if (foo != null) {
            foo.setCorge(this);
        } else if (this.foo != null) {
            this.foo.setCorge(null);
        }
    }

    void internalSetFoo(Foo foo) {
        this.foo = foo;
    }
}