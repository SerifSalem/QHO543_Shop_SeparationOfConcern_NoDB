package domain

// Domain-safe outcome model.
// Domain returns results; UI decides how to present them.
enum class SaleStatus {
    SUCCESS,
    PRODUCT_NOT_FOUND,
    INSUFFICIENT_STOCK,
    PAYMENT_FAILED,
    INVALID_QUANTITY,
    INVALID_SKU
}

data class SaleResult(
    val status: SaleStatus,
    val message: String,
    val totalPrice: Double? = null
)
