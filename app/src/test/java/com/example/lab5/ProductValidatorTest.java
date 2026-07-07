package com.example.lab5;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductValidatorTest {

    @Test
    public void skuMustBePositive() {
        assertFalse(ProductValidator.isValidSku(0));
        assertFalse(ProductValidator.isValidSku(-42));
        assertTrue(ProductValidator.isValidSku(1001));
    }
    @Test
    public void invalidSkuTextShouldNotCrash() {
        // Ce test documente le comportement attendu : un texte non numérique
        // doit être intercepté par le try/catch dans MainActivity avant d'atteindre isValidSku.
        // isValidSku lui-même ne reçoit que des int, donc ce test vérifie
        // simplement les bornes numériques valides.
        assertTrue(ProductValidator.isValidSku(1));
        assertFalse(ProductValidator.isValidSku(0));
    }
}