def choosePostalCode (city:String, location:String, postalCode:String): PostalCodeTrait =
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


def loadCsv (): Seq[Property] =
{
  var properties: Seq[Property] = Seq()
  //Load data (CV)
  try{
  val csvFile = "./08-PropertiesLondon.csv"
  val reader = CSVReader.open(new File(csvFile))
      properties = reader.allWithHeaders().map(record => 
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
  properties
  }
  catch {
    case ex:Exception =>
      ex.printStackTrace
      System.exit(1)
  }
}