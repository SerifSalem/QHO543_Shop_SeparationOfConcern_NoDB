package data

import domain.Product
import domain.Shop

// Centralised initial data seeding.
object ShopSeedData {

    fun seed(shop: Shop) {

        shop.addProduct(Product("001", "Notebook", 2.50, 20))
        shop.addProduct(Product("002", "Pen", 1.20, 50))
        shop.addProduct(Product("003", "Pencil", 0.80, 60))
        shop.addProduct(Product("004", "Eraser", 0.50, 40))
        shop.addProduct(Product("005", "Highlighter", 1.75, 30))
        shop.addProduct(Product("006", "Stapler", 5.99, 15))
        shop.addProduct(Product("007", "Paper Clips (Box)", 1.10, 25))
        shop.addProduct(Product("008", "Ruler (30cm)", 1.30, 20))
        shop.addProduct(Product("009", "Correction Tape", 2.10, 18))
        shop.addProduct(Product("010", "Glue Stick", 1.60, 22))

        shop.addProduct(Product("011", "A4 Notebook", 3.20, 35))
        shop.addProduct(Product("012", "A5 Notebook", 2.40, 30))
        shop.addProduct(Product("013", "Whiteboard Marker", 2.25, 28))
        shop.addProduct(Product("014", "Permanent Marker", 2.75, 26))
        shop.addProduct(Product("015", "Calculator (Basic)", 8.99, 12))
        shop.addProduct(Product("016", "Desk Organiser", 6.50, 14))
        shop.addProduct(Product("017", "Mouse Pad", 4.20, 16))
        shop.addProduct(Product("018", "USB Flash Drive 16GB", 7.99, 20))
        shop.addProduct(Product("019", "USB Flash Drive 32GB", 11.99, 18))
        shop.addProduct(Product("020", "Laptop Sleeve", 14.50, 10))

        shop.addProduct(Product("021", "Wireless Mouse", 12.99, 15))
        shop.addProduct(Product("022", "Keyboard (USB)", 16.99, 12))
        shop.addProduct(Product("023", "HDMI Cable", 6.99, 25))
        shop.addProduct(Product("024", "Ethernet Cable", 4.99, 30))
        shop.addProduct(Product("025", "Power Extension Lead", 9.50, 14))
        shop.addProduct(Product("026", "Desk Lamp", 18.75, 8))
        shop.addProduct(Product("027", "Headphones (Wired)", 13.40, 20))
        shop.addProduct(Product("028", "Headphones (Wireless)", 29.99, 10))
        shop.addProduct(Product("029", "Webcam (HD)", 24.99, 9))
        shop.addProduct(Product("030", "Office Chair Mat", 22.50, 7))
    }
}