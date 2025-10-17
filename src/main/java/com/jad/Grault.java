package com.jad;

public class Grault {

    // 1. L'attribut (relation inverse vers Foo)
    private final Foo foo;

    // 2. Le constructeur (demandé par le diagramme)
    public Grault(Foo foo) {
        this.foo = foo;
    }

    // On ajoute un getter pour que les tests puissent le lire
    public Foo getFoo() {
        return foo;
    }
}