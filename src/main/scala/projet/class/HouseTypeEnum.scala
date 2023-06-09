package projet

enum HouseTypeEnum :
    case House, FlatApartment, NewDevelopment, Penthouse, Bungalow, Duplex, Mews, Studio
object HouseTypeEnum {
  def fromString(value: String): Option[HouseType] =
      value.toLowerCase match {
        case "house" => Some(HouseTypeEnum.House)
        case "flat / apartment" => Some(HouseTypeEnum.FlatApartment)
        case "new development" => Some(HouseTypeEnum.NewDevelopment)
        case "penthouse" => Some(HouseTypeEnum.Penthouse)
        case "bungalow" => Some(HouseTypeEnum.Bungalow)
        case "duplex" => Some(HouseTypeEnum.Duplex)
        case "mews" => Some(HouseTypeEnum.Mews)
        case "studio" => Some(HouseTypeEnum.Studio)
        case _ => None
      }
  def toString(houseType: HouseTypeEnum): String = 
    houseType match {
      case HouseTypeEnum.House => "house"
      case HouseTypeEnum.FlatApartment => "flat / apartment"
      case HouseTypeEnum.NewDevelopment => "new development"
      case HouseTypeEnum.Penthouse => "penthouse"
      case HouseTypeEnum.Bungalow => "bungalow"
      case HouseTypeEnum.Duplex => "duplex"
      case HouseTypeEnum.Mews => "mews"
      case HouseTypeEnum.Studio => "studio"
    }
  }
