package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.Product
import domain.SaleStatus
import domain.Shop
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

// Starts the Compose Desktop GUI.
fun runGui(shop: Shop) {
    application {
        Window(onCloseRequest = ::exitApplication, title = "Shop Facade (Compose Desktop)") {
            ShopGuiApp(shop)
        }
    }
}

// Compose Desktop GUI with a Home screen + separate screens per function.
private enum class Screen {
    HOME,
    ADD,
    SEARCH,
    LIST,
    SELL
}

@Composable
fun ShopGuiApp(shop: Shop) {
    var screen by remember { mutableStateOf(Screen.HOME) }

    MaterialTheme {
        when (screen) {
            Screen.HOME -> HomeScreen(
                onAdd = { screen = Screen.ADD },
                onSearch = { screen = Screen.SEARCH },
                onList = { screen = Screen.LIST },
                onSell = { screen = Screen.SELL }
            )

            Screen.ADD -> AddItemScreen(
                shop = shop,
                onBack = { screen = Screen.HOME }
            )

            Screen.SEARCH -> SearchItemScreen(
                shop = shop,
                onBack = { screen = Screen.HOME }
            )

            Screen.LIST -> ListProductsScreen(
                shop = shop,
                onBack = { screen = Screen.HOME }
            )

            Screen.SELL -> SellScreen(
                shop = shop,
                onBack = { screen = Screen.HOME }
            )
        }
    }
}

// Screen 1: HOME
@Composable
private fun HomeScreen(
    onAdd: () -> Unit,
    onSearch: () -> Unit,
    onList: () -> Unit,
    onSell: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Shop (Compose Desktop)")

        Spacer(Modifier.height(10.dp))

        Button(onClick = onAdd, modifier = Modifier.fillMaxWidth()) {
            Text("Add Item")
        }
        Button(onClick = onSearch, modifier = Modifier.fillMaxWidth()) {
            Text("Search for Item")
        }
        Button(onClick = onList, modifier = Modifier.fillMaxWidth()) {
            Text("List All Products")
        }
        Button(onClick = onSell, modifier = Modifier.fillMaxWidth()) {
            Text("Sell")
        }
    }
}

// Screen 2: ADD ITEM
@Composable
private fun AddItemScreen(shop: Shop, onBack: () -> Unit) {

    var sku by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Enter details and click Add.") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Add Item")

        OutlinedTextField(
            value = sku,
            onValueChange = { sku = it },
            label = { Text("SKU") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Unit Price (e.g., 2.50)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = qty,
            onValueChange = { qty = it },
            label = { Text("Quantity (e.g., 10)") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                val cleanSku = sku.trim()
                val cleanName = name.trim()
                val parsedPrice = price.trim().toDoubleOrNull()
                val parsedQty = qty.trim().toIntOrNull()

                if (cleanSku.isEmpty() || cleanName.isEmpty() || parsedPrice == null || parsedQty == null) {
                    status = "Invalid input. Please provide SKU, name, numeric price, and numeric quantity."
                } else {
                    shop.addProduct(Product(cleanSku, cleanName, parsedPrice, parsedQty))
                    status = "Added: $cleanName ($cleanSku)."
                    // Optional: clear fields after add
                    sku = ""
                    name = ""
                    price = ""
                    qty = ""
                }
            }) { Text("Add") }

            Button(onClick = onBack) { Text("Back to Home") }
        }

        Text("Status: $status")
    }
}

// Screen 3: SEARCH ITEM
@Composable
private fun SearchItemScreen(shop: Shop, onBack: () -> Unit) {

    var sku by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Enter SKU and click Search.") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Search for Item")

        OutlinedTextField(
            value = sku,
            onValueChange = { sku = it },
            label = { Text("SKU") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                val found = shop.searchForProduct(sku)
                resultText = if (found == null) {
                    "Not found."
                } else {
                    "Found: ${found.sku} | ${found.name} | £${found.unitPrice} | qty=${found.quantity}"
                }
            }) { Text("Search") }

            Button(onClick = onBack) { Text("Back to Home") }
        }

        Text(resultText)
    }
}

// Screen 4: LIST PRODUCTS
@Composable
private fun ListProductsScreen(shop: Shop, onBack: () -> Unit) {

    var products by remember { mutableStateOf(shop.listProducts()) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("List All Products")

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { products = shop.listProducts() }) { Text("Refresh") }
            Button(onClick = onBack) { Text("Back to Home") }
        }

        if (products.isEmpty()) {
            Text("No products available.")
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(products) { p ->
                    Text("${p.sku} | ${p.name} | £${p.unitPrice} | qty=${p.quantity}")
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

// Screen 5: SELL
@Composable
private fun SellScreen(shop: Shop, onBack: () -> Unit) {

    var sku by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("1") }
    var method by remember { mutableStateOf("CARD") }
    var status by remember { mutableStateOf("Enter details and click Sell.") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Sell")

        OutlinedTextField(
            value = sku,
            onValueChange = { sku = it },
            label = { Text("SKU") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = qty,
            onValueChange = { qty = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = method,
            onValueChange = { method = it },
            label = { Text("Payment Method (CARD/CASH)") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                val parsedQty = qty.trim().toIntOrNull() ?: 0
                val result = shop.sellProduct(sku, parsedQty, method)

                status = when (result.status) {
                    SaleStatus.SUCCESS -> "${result.message} Total: £${result.totalPrice}"
                    else -> "Sale failed: ${result.message}"
                }
            }) { Text("Sell") }

            Button(onClick = onBack) { Text("Back to Home") }
        }

        Text("Status: $status")
    }
}
