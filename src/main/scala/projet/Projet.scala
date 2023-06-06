package projet

import $ivy.`com.github.tototoshi::scala-csv:1.3.8`

import com.github.tototoshi.csv._
import java.io.FileReader
import java.io.File

@main def hello() = println("Hello, World !")

enum HouseType :
    case House, FlatApartment, NewDevelopment, Penthouse, Bungalow, Duplex, Mews, Studio

trait PostalCode {
  val postalCode: String
}
class Location(location: String, val postalCode: String) extends PostalCode
class City(city:String, val postalCode: String) extends PostalCode
class Address(city:String, location:String, val postalCode: String) extends PostalCode

def choosePostalCode (city:String, location:String, postalCode:String): PostalCode =
{
  if(city!=null && city!="")
  {
    if(location!=null&& location!=""){
      new Address(city,location,postalCode)
    }
    else{
      new City(city,postalCode)
    }
  }else{
    if(location!=null&& location!=""){
      new Location(location,postalCode)
    }
    else{
      new PostalCode(postalCode)
    }
  }
}

class Property(name:String, price:Int, houseType:HouseType, area:Int, nbBedrooms:Int, nbBathrooms:Int, nbReceptions:Int, val address:PostalCode)
{
  override def toString: String = s"House(name: $name, houseType: $houseType, price: $price)"
}
//Load data (CV)
val csvFile = "08-PropertiesLondon.csv"
val reader = CSVReader.open(new File(csvFile))
val csvParser = CSVFormat.DEFAULT.withHeader().parseAll(reader)
val properties = csvParser.getRecords.map(record => 
    Property(record.get("Property Name"), 
    record.get("Price").toInt,
    HouseType.valueOf(record.get("House Type")),
    record.get("Area in sq ft").toInt,
    record.get("No. of Bedrooms").toInt,
    record.get("No. of Bathrooms").toInt,
    record.get("No. of Receptions").toInt,
    choosePostalCode(record.get("Location"),record.get("City/County"),record.get("Postal Code"))
    )
    )

//Query1(CV): map: prix par m2 (prix/area)
val priceM2 = properties.map(property=>property.price/property.area)
//Query2(FB): reduce: moyenne prix ou bathroom au total (plus simple) -> (à voir si on filtre sur City)
//Query3(CV): filtre: sortir toutes les flat appartment
val filter=HouseType.Duplex
val filteredHouses = properties.filter(_.houseType == filter)
//filteredHouses.foreach(println)
//Query4(FB): aggragate: filtrer sur une ville puis affichage du nombre par HouseType
//Query5(FB): calcul du nombre de pièces avec un taux par pièce (badroom 0.5 par ex.)
