package domain

// Domain entity: UI-independent data class.
data class Product(
    val sku: String,
    val name: String,
    val unitPrice: Double,
    val quantity: Int
)
