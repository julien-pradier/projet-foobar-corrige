package com.jad;

// On importe les types dont on va avoir besoin (List et ArrayList)

import java.util.ArrayList;
import java.util.List;

public class Foo {

    // --- 1. Attributs (Relations) ---

    // Relation 1:1 avec Bar (Association)
    private final Bar bar; // 'final' car il est assigné dans le constructeur et ne change pas

    // Relation 1:* avec Baz (Agrégation)
    // On utilise une Liste pour gérer le tableau dynamique (c'est mieux qu'un tableau Baz[])
    private final List<Baz> bazs;

    // Relation 1:1 avec Qux (Composition)
    private final Qux qux;

    // Relation 1:0..1 avec Corge (Association)
    private Corge corge; // Pas 'final' car il peut être null ou assigné plus tard

    // Relation 1:* avec Grault (Composition)
    private final List<Grault> graults;

    // --- 2. Constructeur ---
    // Il doit prendre 'bar' en paramètre comme vu sur le diagramme
    public Foo(Bar bar) {
        this.bar = bar;

        // On initialise les listes (vides au début)
        this.bazs = new ArrayList<>();
        this.graults = new ArrayList<>();

        // --- Gérer la COMPOSITION ---
        // Foo est responsable de la création de son Qux.
        this.qux = new Qux();

        // Note: 'corge' reste null pour l'instant, c'est correct (relation 0..1)
    }

    // --- 3. Méthodes ---

    public void addBaz(Baz baz) {
        // Pour l'agrégation, on ajoute simplement l'objet 'baz' (qui existe déjà) à la liste
        this.bazs.add(baz);
    }

    public void addGrault() {
        // Pour la composition, Foo est responsable de la CRÉATION de Grault.
        // On crée un nouveau Grault et on lui passe 'this' (le Foo actuel)
        // car le constructeur de Grault(foo: Foo) en a besoin.
        this.graults.add(new Grault(this));
    }

    // --- 4. Getters (Méthodes implicites) ---
    // Les tests (FooTest.java) ont besoin de récupérer les attributs pour vérifier l'état.
    // On doit donc ajouter les "getters".

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

    // Un setter pour Corge (car la relation est 0..1 et non définie à la construction)
    public void setCorge(Corge corge) {
        // Si on nous donne le même corge qu'on a déjà, on ne fait rien.
        if (this.corge == corge) {
            return;
        }

        // 1. On garde une référence à notre ancien corge
        Corge oldCorge = this.corge;

        // 2. On adopte le nouveau corge
        this.corge = corge;

        // 3. On dit à l'ancien corge qu'il est orphelin (s'il existait)
        if (oldCorge != null) {
            oldCorge.internalSetFoo(null);
        }

        // 4. On dit au nouveau corge qu'on est son parent (s'il existe)
        if (this.corge != null) {
            this.corge.internalSetFoo(this);
        }
    }
}