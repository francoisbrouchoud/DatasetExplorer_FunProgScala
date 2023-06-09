class Property(val name:String, val price:Int, val houseType:HouseType, val area:Int, val nbBedrooms:Int, val nbBathrooms:Int, val nbReceptions:Int, val address:PostalCode)
{
  override def toString: String = s"House(name: $name, houseType: $houseType, price: $price)"
}