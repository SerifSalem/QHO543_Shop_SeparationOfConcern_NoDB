package domain

import data.ShopSeedData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

/*
 * Scenario:
 * Unit tests for the Shop Facade.
 * Tests start with simple validations (null, true/false),
 * then progress to more complex sale and stock-handling scenarios.
 */
class ShopTest {

    private lateinit var shop: Shop

    @BeforeEach
    fun setup() {
        shop = Shop()
        ShopSeedData.seed(shop)
    }

    /*
     * Scenario 1:
     * Searching for a product with an empty SKU should return null.
     * (Simple NULL test)
     */
    @Test
    fun searchWithEmptySkuReturnsNull() {
        val result = shop.searchForProduct("")
        assertNull(result)
    }

    /** Scenario 2:
     * Searching for an existing product should return a non-null Product.
     * (Simple NOT NULL / TRUE-style validation)
     */


    /** Scenario 3:
     * Searching for a non-existent SKU should return null.
     * (NULL test)
     */


    /** Scenario 4:
     * Selling a valid product with valid quantity and payment method
     * should result in SUCCESS.
     * (EQUAL / TRUE test)
     */


    /** Scenario 5:
     * Selling with an invalid quantity (zero or negative)
     * should fail with INVALID_QUANTITY.
     * (FALSE-style validation via status)
     */


    /** Scenario 6:
     * Selling more items than available in stock
     * should result in INSUFFICIENT_STOCK.
     * (EQUAL test)
     */


    /** Scenario 7:
     * Selling with an unsupported payment method
     * should result in PAYMENT_FAILED.
     * (FALSE-style outcome)
     */


    /** Scenario 8:
     * After a successful sale, the product stock should be reduced.
     * (NOT EQUAL test)
     */


    /** Scenario 9:
     * After selling a known quantity, the remaining stock
     * should be exactly correct.
     * (EQUAL test)
     */


    /** Scenario 10:
     * Attempting to sell a product using an empty SKU
     * should result in INVALID_SKU.
     * (Complex validation with domain rules)
     */

}
