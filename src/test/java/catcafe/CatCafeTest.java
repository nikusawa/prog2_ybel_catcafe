package catcafe;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import tree.*;

class CatCafeTest {
    private CatCafe cafe;
    private FelineOverLord luna;
    private FelineOverLord felix;
    private FelineOverLord simba;
    private FelineOverLord emma;

    @BeforeEach
    void setUp() {
        cafe = new CatCafe();
        luna = new FelineOverLord("Luna", 2);
        felix = new FelineOverLord("Felix", 3);
        simba = new FelineOverLord("Simba", 3);
        emma = new FelineOverLord("Emma", 5);
    }

    // 1. Neues Café ist leer
    @Test
    void givenNewCafe_whenCounted_thenZero() {
        // given (implizit: setUp)
        // when
        long count = cafe.getCatCount();
        // then
        assertEquals(0, count);
    }

    // 2. Nach dem Hinzufügen einer Katze steigt Zähler
    @Test
    void givenEmptyCafe_whenAddCat_thenCountIncreases() {
        // when
        cafe.addCat(luna);
        // then
        assertEquals(1, cafe.getCatCount());
    }

    // 3. Keine doppelten Einträge
    @Test
    void givenCatAlreadyPresent_whenAddSameCat_thenCountDoesNotChange() {
        cafe.addCat(luna);
        cafe.addCat(luna);
        assertEquals(1, cafe.getCatCount());
    }

    // 4. Katze nach Namen finden
    @Test
    void givenCats_whenGetCatByName_thenReturnCorrectCat() {
        cafe.addCat(luna);
        cafe.addCat(simba);
        FelineOverLord found = cafe.getCatByName("Simba");
        assertNotNull(found);
        assertEquals("Simba", found.name());
    }

    // 5. Suche nach nicht existierendem Namen liefert null
    @Test
    void givenCats_whenGetCatByNameWithUnknown_thenReturnNull() {
        cafe.addCat(luna);
        assertNull(cafe.getCatByName("Garfield"));
    }

    // 6. Namenssuche mit null als Argument
    @Test
    void givenCats_whenGetCatByNameWithNull_thenReturnNull() {
        cafe.addCat(luna);
        assertNull(cafe.getCatByName(null));
    }

    // 7. Katze nach Gewicht finden (innerhalb der Grenzen)
    @Test
    void givenCats_whenGetCatByWeightInRange_thenReturnCorrectCat() {
        cafe.addCat(luna); // Gewicht 2
        cafe.addCat(emma);    // Gewicht 5
        FelineOverLord found = cafe.getCatByWeight(3, 6);
        assertNotNull(found);
        // emma (5) liegt im Intervall [3,6)
        assertEquals("Emma", found.name());
    }

    // 8. Gewichtssuche ohne Treffer
    @Test
    void givenCats_whenGetCatByWeightOutOfRange_thenReturnNull() {
        cafe.addCat(luna); // 2
        assertNull(cafe.getCatByWeight(5, 10));
    }

    // 9. Gewichtssuche mit ungültigen Grenzen
    @Test
    void givenInvalidWeightBounds_thenReturnNull() {
        cafe.addCat(luna);
        assertNull(cafe.getCatByWeight(5, 3)); // min > max
        assertNull(cafe.getCatByWeight(-1, 3)); // min negativ
    }
}
