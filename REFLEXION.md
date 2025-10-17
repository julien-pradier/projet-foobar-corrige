# Réflexion sur l'Activité UML Java

## 1. Mini-plan d'implémentation (Analyse)

L'objectif était d'implémenter un diagramme UML de 6 classes.

**Classes identifiées :**
* `Foo` : Classe centrale avec 5 relations.
* `Bar` : Association simple avec `Foo`.
* `Baz` : Agrégation (plusieurs `Baz` pour 1 `Foo`).
* `Qux` : Composition (1 `Qux` créé par `Foo`).
* `Corge` : Association bidirectionnelle (0..1) avec `Foo`.
* `Grault` : Composition (plusieurs `Grault` créés par `Foo`).

**Détails d'implémentation :**
* Les relations `*` (plusieurs) ont été implémentées avec des `ArrayList` pour plus de flexibilité que des tableaux `[]`.
* Les relations de **Composition** (`Qux`, `Grault`) ont été gérées en appelant `new Qux()` ou `new Grault(this)` depuis la classe `Foo`.
* La relation d'**Agrégation** (`Baz`) a été gérée en passant un objet `Baz` déjà existant à la méthode `foo.addBaz(baz)`.

## 2. Difficultés rencontrées

La difficulté principale a été l'implémentation de la relation bidirectionnelle entre `Foo` et `Corge`.

1.  **Erreur de compilation :** Les tests unitaires (`FooTest.java`) s'attendaient à une méthode `setFoo(Foo foo)` dans la classe `Corge`, qui n'était pas explicitement visible sur le diagramme UML.
2.  **Test en échec (`corgeTest`) :** Après avoir ajouté le `setFoo`, le test échouait toujours. Il s'attendait à ce que la liaison se fasse dans les **deux sens** automatiquement. Si `foo.setCorge(c)` était appelé, alors `c.getFoo()` devait renvoyer `foo`.
3.  **Boucle infinie :** Une première tentative de correction (où `foo.setCorge` appelait `corge.setFoo` et vice-versa) créait une boucle infinie (`StackOverflowError`).

## 3. Améliorations et Solutions

La solution pour la relation `Corge <-> Foo` a été de créer une méthode "interne" (`internalSetFoo`) dans `Corge`.

* Le setter public `foo.setCorge(corge)` gère la logique "maître" : il met à jour son propre attribut `corge`, puis appelle `oldCorge.internalSetFoo(null)` et `newCorge.internalSetFoo(this)`.
* La méthode `internalSetFoo` (avec une visibilité "package-private") se contente de changer l'attribut `foo` de `Corge` **sans** appeler `foo.setCorge` en retour.
* Cela casse la boucle infinie et permet de passer tous les tests.