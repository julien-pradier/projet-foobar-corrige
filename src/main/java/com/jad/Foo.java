package com.jad;

import java.util.ArrayList;
import java.util.List;

public class Foo {

    // --- Attributs (Relations) ---

    private final Bar bar;

    private final List<Baz> bazs;

    private final Qux qux;

    private Corge corge;

    private final List<Grault> graults;

    public Foo(Bar bar) {
        this.bar = bar;

        this.bazs = new ArrayList<>();
        this.graults = new ArrayList<>();


        this.qux = new Qux();
    }

    // --- Méthodes ---

    public void addBaz(Baz baz) {
        this.bazs.add(baz);
    }

    public void addGrault() {
        this.graults.add(new Grault(this));
    }

    // --- Getters ---

    public Bar getBar() {
        return bar;
    }

    public List<Baz> getBazs() {
        return bazs;
    }

    public Qux getQux() {
        return qux;
    }

    public Corge getCorge() {
        return corge;
    }

    public List<Grault> getGraults() {
        return graults;
    }

    public void setCorge(Corge corge) {
        if (this.corge == corge) {
            return;
        }

        Corge oldCorge = this.corge;

        this.corge = corge;

        if (oldCorge != null) {
            oldCorge.internalSetFoo(null);
        }

        if (this.corge != null) {
            this.corge.internalSetFoo(this);
        }
    }
}