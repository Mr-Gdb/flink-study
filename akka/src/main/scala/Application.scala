import Message.{Hello, NiHao}
import akka.actor.{ActorSystem, Props}
import com.me.Actor.HelloActor

object Application {
  def main(args: Array[String]): Unit = {
    val as = ActorSystem("myActorSystem")
    val helloActor = as.actorOf(Props[HelloActor], "hello")
    helloActor ! Hello("zhang pei pei")
    helloActor ! NiHao("zhang pei pei")
    helloActor ! "ni hao ma"
    as.stop(helloActor)
    as.terminate()
  }
}
