def query1 (properties:Seq[Property]) : Seq={
    properties.map(property => (property.name, property.price/property.area, property.houseType, property.address)).sortBy(_._2)
}

def query2 (properties:Seq[Property]) : Seq={
    properties.groupBy(_.houseType).map { 
    case (houseType, properties) =>
        val totalPrices = properties.map(_.price.toLong).sum
        val avgPriceByProperty = totalPrices / properties.size
        (houseType, avgPriceByProperty)
    }
}

def query3 (properties:Seq[Property], filter:HouseType) : Seq={
    properties.filter(_.houseType == filter)
}

def query4 (properties:Seq[Property]) : Seq={
}

def query5 (properties:Seq[Property]) : Seq={
    properties.map(property => (property.name, property.nbBedrooms + property.nbReceptions + (0.5 * property.nbBathrooms))).sortBy(_._2)
}