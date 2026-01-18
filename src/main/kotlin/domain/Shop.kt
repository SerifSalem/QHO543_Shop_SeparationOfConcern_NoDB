package domain

// Shop is a FACADE:
// UI code (console/GUI) interacts only with this simplified interface.
/* Separation of Concerns:
 * - No println(); - No readln(); - No Compose classes; - No UI behaviour */
class Shop {

    private val inventory = Inventory()
    private val pricingService = PricingService()
    private val paymentService = PaymentService()

    fun addProduct(product: Product) {
        inventory.addOrMerge(product)
    }

    fun searchForProduct(sku: String): Product? {
        val cleanSku = sku.trim()
        if (cleanSku.isEmpty()) return null
        return inventory.findBySku(cleanSku)
    }

    fun listProducts(): List<Product> = inventory.allProducts()

    fun sellProduct(sku: String, quantity: Int, paymentMethod: String): SaleResult {

        val cleanSku = sku.trim()
        if (cleanSku.isEmpty()) {
            return SaleResult(SaleStatus.INVALID_SKU, "SKU must not be empty.")
        }

        if (quantity <= 0) {
            return SaleResult(SaleStatus.INVALID_QUANTITY, "Quantity must be greater than zero.")
        }

        val product = inventory.findBySku(cleanSku)
            ?: return SaleResult(SaleStatus.PRODUCT_NOT_FOUND, "No product found with SKU: $cleanSku")

        if (!inventory.hasSufficientStock(cleanSku, quantity)) {
            return SaleResult(
                SaleStatus.INSUFFICIENT_STOCK,
                "Insufficient stock for ${product.name}. Available: ${product.quantity}"
            )
        }

        val total = pricingService.calculateTotal(product.unitPrice, quantity)

        val paymentOk = paymentService.takePayment(total, paymentMethod)
        if (!paymentOk) {
            return SaleResult(
                SaleStatus.PAYMENT_FAILED,
                "Unsupported or failed payment method: ${paymentMethod.trim()}"
            )
        }

        inventory.reduceStock(cleanSku, quantity)

        return SaleResult(
            SaleStatus.SUCCESS,
            "Sale completed for ${product.name}.",
            totalPrice = total
        )
    }

    // Internal subsystems hidden behind the facade

    private class Inventory {
        private val products = mutableListOf<Product>()

        fun addOrMerge(product: Product) {
            val index = products.indexOfFirst { it.sku == product.sku }
            if (index == -1) {
                products.add(product)
            } else {
                val existing = products[index]
                products[index] = existing.copy(quantity = existing.quantity + product.quantity)
            }
        }

        fun findBySku(sku: String): Product? = products.find { it.sku == sku }

        fun hasSufficientStock(sku: String, qty: Int): Boolean =
            (findBySku(sku)?.quantity ?: 0) >= qty

        fun reduceStock(sku: String, qty: Int) {
            val index = products.indexOfFirst { it.sku == sku }
            if (index != -1) {
                val p = products[index]
                products[index] = p.copy(quantity = p.quantity - qty)
            }
        }

        fun allProducts(): List<Product> = products.toList()
    }

    private class PricingService {
        fun calculateTotal(unitPrice: Double, quantity: Int): Double = unitPrice * quantity
    }

    private class PaymentService {
        fun takePayment(amount: Double, method: String): Boolean {
            val m = method.trim().uppercase()
            return m == "CARD" || m == "CASH"
        }
    }
}
