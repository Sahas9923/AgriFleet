package com.agrifleet.network_service.algorithm.kruskal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisjointSetTest {

    @Test
    void nodesShouldStartInSeparateSets() {

        DisjointSet dsu = new DisjointSet(5);

        assertEquals(1, dsu.find(1));
        assertEquals(2, dsu.find(2));
        assertEquals(3, dsu.find(3));
    }

    @Test
    void unionShouldMergeSets() {

        DisjointSet dsu = new DisjointSet(5);

        assertTrue(dsu.union(1, 2));

        assertEquals(
                dsu.find(1),
                dsu.find(2)
        );
    }

    @Test
    void duplicateUnionShouldNotMergeAgain() {

        DisjointSet dsu = new DisjointSet(5);

        assertTrue(dsu.union(1, 2));
        assertFalse(dsu.union(1, 2));
    }

    @Test
    void transitiveUnionShouldWork() {

        DisjointSet dsu = new DisjointSet(5);

        dsu.union(1, 2);
        dsu.union(2, 3);

        assertEquals(
                dsu.find(1),
                dsu.find(3)
        );
    }

    @Test
    void invalidNodeShouldThrowException() {

        DisjointSet dsu = new DisjointSet(5);

        assertThrows(
                IllegalArgumentException.class,
                () -> dsu.find(10)
        );
    }
}
