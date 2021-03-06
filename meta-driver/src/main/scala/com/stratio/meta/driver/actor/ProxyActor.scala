/*
 * Stratio Meta
 *
 * Copyright (c) 2014, Stratio, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package com.stratio.meta.driver.actor

import akka.actor.{Actor, Props, ActorRef}
import com.stratio.meta.common.ask.{Command, Query, Connect}
import com.stratio.meta.common.result.{ErrorResult, Result}
import com.stratio.meta.communication.{Disconnect, ACK}
import scala.concurrent.duration._
import akka.util.Timeout
import akka.contrib.pattern.ClusterClient
import com.stratio.meta.driver.BasicDriver
import org.apache.log4j.Logger

/**
 * Companion object.
 */
object ProxyActor{
  /**
   * Config prop in ProxyActor.
   * @param clusterClientActor ActorRef to ClusterClientActor pattern.
   * @param remoteActor Remote actor's name.
   * @return Actor's props.
   */
  def props(clusterClientActor: ActorRef, remoteActor: String, driver: BasicDriver): Props= Props(new ProxyActor(clusterClientActor,
    remoteActor, driver))

  /**
   * Initial path for actor's indentify.
   */
  val INIT_PATH= "/user/"

  /**
   * Create path with actor's name.
   * @param remoteActor Remote actor's name.
   * @return Complete path.
   */
  def remotePath(remoteActor: String)= INIT_PATH + remoteActor
}

/**
 * Actor to connect with receptionist actor in the remote cluster.
 * @param clusterClientActor ActorRef to ClusterClientActor pattern.
 * @param remoteActor Remote actor's name.
 */
class ProxyActor(clusterClientActor:ActorRef, remoteActor:String, driver: BasicDriver) extends Actor{

  /**
   * Class logger.
   */
  lazy val logger = Logger.getLogger(getClass)

  implicit val timeout = Timeout(5 seconds)

  override def receive: Actor.Receive = {

    /* The driver sends the connect message. */
    case c : Connect => {
      clusterClientActor forward ClusterClient.Send(ProxyActor.remotePath(remoteActor), c, localAffinity = true)
    }

    case c : Disconnect => {
      //println("Send connect " + c)
      clusterClientActor forward ClusterClient.Send(ProxyActor.remotePath(remoteActor), c, localAffinity = true)
    }

    /* API Command */
    case cmd : Command => {
      println("Send command: " + cmd);
      clusterClientActor forward ClusterClient.Send(ProxyActor.remotePath(remoteActor), cmd, localAffinity = true)
    }

    /* ACK received */
    case ack : ACK => {
      val handler = driver.getResultHandler(ack.queryId)
      if(handler != null){
        handler.processAck(ack.queryId, ack.status)
      }else{
        logger.warn("ACK not expected received: " + ack)
      }
    }

    /* Send a query to the remote meta-server infrastructure. */
    case message:Query => {
      clusterClientActor ! ClusterClient.Send(ProxyActor.remotePath(remoteActor), message, localAffinity = true)
    }
    case result:Result => {

      val handler = driver.getResultHandler(result.getQueryId)
      if(handler != null){
        if(!result.isInstanceOf[ErrorResult]) {
          handler.processResult(result)
        }else{
          handler.processError(result)
        }
      }else{
        logger.warn("Result not expected received: " + result.getQueryId)
      }

    }
    case unknown: Any => {
      logger.warn("Unknown message: " + unknown)
      sender ! "Message type not supported"
    }
  }
}


