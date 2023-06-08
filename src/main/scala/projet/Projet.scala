package projet



import com.github.tototoshi.csv._
import java.io.FileReader
import java.io.File

@main def hello() = {println("Hello, World !")

enum HouseType :
    case House, FlatApartment, NewDevelopment, Penthouse, Bungalow, Duplex, Mews, Studio
object HouseType {
  def fromString(value: String): Option[HouseType] =
      value.toLowerCase match {
        case "house" => Some(HouseType.House)
        case "flat / apartment" => Some(HouseType.FlatApartment)
        case "new development" => Some(HouseType.NewDevelopment)
        case "penthouse" => Some(HouseType.Penthouse)
        case "bungalow" => Some(HouseType.Bungalow)
        case "duplex" => Some(HouseType.Duplex)
        case "mews" => Some(HouseType.Mews)
        case "studio" => Some(HouseType.Studio)
        case _ => None
      }
  }
trait PostalCode {
  val postalCode: String
}
class Location(location: String, val postalCode: String) extends PostalCode
class City(city:String, val postalCode: String) extends PostalCode
class Address(city:String, location:String, val postalCode: String) extends PostalCode
class OnlyPostalCode(val postalCode: String) extends PostalCode

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

    //TODO à vérifier, car on peut pas instancier directement un trait
    else{
      new OnlyPostalCode(postalCode)
    }
  }
}

class Property(val name:String, val price:Int, val houseType:HouseType, val area:Int, val nbBedrooms:Int, val nbBathrooms:Int, val nbReceptions:Int, val address:PostalCode)
{
  override def toString: String = s"House(name: $name, houseType: $houseType, price: $price)"
}
//Load data (CV)
val csvFile = "./08-PropertiesLondon.csv"
val reader = CSVReader.open(new File(csvFile))
val properties = reader.allWithHeaders().map(record => 
    Property(record("Property Name"), 
    record("Price").toInt,
    HouseType.fromString(record("House Type")).getOrElse(HouseType.House), 
    record("Area in sq ft").toInt,
    record("No. of Bedrooms").toInt,
    record("No. of Bathrooms").toInt,
    record("No. of Receptions").toInt,
    choosePostalCode(record("Location"),record("City/County"),record("Postal Code"))
    )
)

// Dans le cas ou l'import csv ne fonctionne pas
/*val properties = Seq(
  Property("name1",12,HouseType.valueOf("Flat / Apartment"),12,1,1,1,PostalCode("test")),
  Property("name2",24,HouseType.valueOf("New development"),12,1,2,1,PostalCode("test2")),
  Property("name3",124,HouseType.valueOf("House"),120,11,2,3,PostalCode("test3"))
  )
*/
//Query1(CV): map: prix par m2 (prix/area)
/*
val priceM2 = properties.map(property => (property.name, property.price/property.area))
priceM2.foreach { case (name, price) =>
  println(s"Property: $name, Price per square meter: $price")
}
*/
//Query2(FB): reduce: moyenne prix ou bathroom au total (plus simple) -> (à voir si on filtre sur City)
//Query3(CV): filtre: sortir toutes les flat appartment
//val filter=HouseType.Duplex
//val filteredHouses = properties.filter(_.houseType == filter)
//filteredHouses.foreach(println)
//Query4(FB): aggragate: filtrer sur une ville puis affichage du nombre par HouseType
//Query5(FB): calcul du nombre de pièces avec un taux par pièce (badroom 0.5 par ex.)
}