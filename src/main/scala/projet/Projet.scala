package projet

@main def hello() = println("Hello, World !")

enum HouseType :
    case House, FlatApartment, NewDevelopment, Penthouse, Bungalow, Duplex, Mews, Studio

class Properties(name:String, price:Int,houseType:HouseType, Area:Int, NbBedrooms:Int, NbBathrooms:Int, NbReceptions:Int)
