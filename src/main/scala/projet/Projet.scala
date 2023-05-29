package projet

@main def hello() = println("Hello, World !")

enum HouseType :
    case House, FlatApartment, NewDevelopment, Penthouse, Bungalow, Duplex, Mews, Studio

class Address(city:String, location:String, postalCode:String)

class Properties(name:String, price:Int, houseType:HouseType, area:Int, nbBedrooms:Int, nbBathrooms:Int, nbReceptions:Int, address:Address)



//Load data (CV)
//Query1(CV): map: prix par m2 (prix/area)
//Query2(FB): reduce: moyenne prix ou bathroom au total (plus simple) -> (à voir si on filtre sur City)
//Query3(CV): filtre: sortir toutes les flat appartment
//Query4(FB): aggragate: filtrer sur une ville puis affichage du nombre par HouseType
//Query5(FB): calcul du nombre de pièces avec un taux par pièce (badroom 0.5 par ex.)
