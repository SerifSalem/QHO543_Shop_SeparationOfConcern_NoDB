package ui

import data.ShopSeedData
import domain.Shop

fun main() {

    val shop = Shop()
    ShopSeedData.seed(shop)

    println()
    println("===================================")
    println(" Shop Application")
    println("===================================")
    println("1) Console UI")
    println("2) GUI (Compose Desktop)")
    println("0) Exit")
    print("Choose option: ")

    when (readln().trim()) {
        "1" -> consoleMain(shop)
        "2" -> runGui(shop)
        "0" -> println("Exiting application.")
        else -> println("Invalid option. Please choose 0, 1, or 2.")
    }
}

