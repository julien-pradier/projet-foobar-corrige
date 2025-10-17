package com.jad;

public class Corge {

    // On retire 'final' car 'foo' peut être changé (mis à null)
    private Foo foo;

    // 1. Constructeur (Corrigé)
    public Corge(Foo foo) {
        // On assigne le foo
        this.foo = foo;

        // Et on dit au 'foo' de nous adopter (ce qui cassera son ancien lien)
        if (foo != null) {
            foo.setCorge(this);
        }
    }

    // 2. Getter (inchangé)
    public Foo getFoo() {
        return this.foo;
    }

    // 3. Setter public (pour le test à la ligne 68)
    public void setFoo(Foo foo) {
        if (foo != null) {
            // Si on nous donne un foo, on dit à ce foo de nous adopter
            foo.setCorge(this);
        } else if (this.foo != null) {
            // Si on nous met à null, on dit à notre ancien foo de nous oublier
            this.foo.setCorge(null);
        }
    }

    /**
     * C'EST LA MÉTHODE MANQUANTE !
     * Setter interne (visibilité "package-private", pas de 'public').
     * Seule la classe Foo (qui est dans le même package) a le droit d'appeler cette méthode.
     * Ça évite les boucles infinies de setters.
     */
    void internalSetFoo(Foo foo) {
        this.foo = foo;
    }
}