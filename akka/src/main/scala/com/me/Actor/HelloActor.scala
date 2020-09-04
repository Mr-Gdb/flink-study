package com.me.Actor

import Message.{Hello, NiHao}
import akka.actor.Actor

case class HelloActor() extends Actor {
  override def receive: Receive = {
    case Hello(desc) => println("hello" + desc)
    case NiHao(desc) => println("你好" + desc)
    case _ => println("what are you saying!")
  }
}
