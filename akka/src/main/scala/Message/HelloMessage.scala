package Message

sealed trait HelloMessage



case class Hello(desc:String) extends HelloMessage

case class NiHao(desc:String) extends HelloMessage
