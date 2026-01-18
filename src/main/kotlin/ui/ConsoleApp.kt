package ui

import domain.Product
import domain.SaleStatus
import domain.Shop

// Console UI
fun consoleMain(shop: Shop) {
    var running = true
    while (running) {
        println()
        println("===================================")
        println(" SHOP (Console) - Home")
        println("===================================")
        println("1) Add Item")
        println("2) Search for Item")
        println("3) List All Products")
        println("4) Sell")
        println("0) Quit")
        print("Choose option: ")

        when (readln().trim()) {
            "1" -> addItemScreen(shop)
            "2" -> searchItemScreen(shop)
            "3" -> listProductsScreen(shop)
            "4" -> sellScreen(shop)
            "0" -> running = false
            else -> println("Invalid option. Please choose 1-5.")
        }
    }

    println("Goodbye.")
}

// Console “screens”
private fun addItemScreen(shop: Shop) {
    println()
    println("=== Add Item ===")

    print("SKU: ")
    val sku = readln().trim()

    print("Name: ")
    val name = readln().trim()

    print("Unit price (e.g. 2.50): ")
    val price = readln().trim().toDoubleOrNull()

    print("Quantity (e.g. 10): ")
    val qty = readln().trim().toIntOrNull()

    if (sku.isEmpty() || name.isEmpty() || price == null || qty == null) {
        println("Invalid input. SKU/name must not be empty and price/quantity must be numeric.")
        returnToHomePrompt()
        return
    }

    shop.addProduct(Product(sku, name, price, qty))
    println("Added: $name ($sku) | £$price | qty=$qty")

    returnToHomePrompt()
}

private fun searchItemScreen(shop: Shop) {
    println()
    println("=== Search for Item ===")

    print("Enter SKU: ")
    val sku = readln().trim()

    val found = shop.searchForProduct(sku)
    if (found == null) {
        println("Not found.")
    } else {
        println("Found: ${found.sku} | ${found.name} | £${found.unitPrice} | qty=${found.quantity}")
    }

    returnToHomePrompt()
}

private fun listProductsScreen(shop: Shop) {
    println()
    println("=== List All Products ===")

    val products = shop.listProducts()
    if (products.isEmpty()) {
        println("No products available.")
    } else {
        products.forEach { p ->
            println("${p.sku} | ${p.name} | £${p.unitPrice} | qty=${p.quantity}")
        }
    }

    returnToHomePrompt()
}

private fun sellScreen(shop: Shop) {
    println()
    println("=== Sell ===")

    print("SKU: ")
    val sku = readln().trim()

    print("Quantity: ")
    val qty = readln().trim().toIntOrNull() ?: 0

    print("Payment method (CARD/CASH): ")
    val method = readln().trim()

    val result = shop.sellProduct(sku, qty, method)

    when (result.status) {
        SaleStatus.SUCCESS -> println("${result.message} Total: £${result.totalPrice}")
        else -> println("Sale failed: ${result.message}")
    }

    returnToHomePrompt()
}

// Helpers
private fun returnToHomePrompt() {
    println()
    print("Press ENTER to return to Home...")
    readln()
}

