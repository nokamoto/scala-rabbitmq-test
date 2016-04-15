import java.time.ZonedDateTime

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.{ConnectionFactory, DefaultConsumer, Envelope}

object Main {
  val defaultQueueName = "scala-rabbitmq-test.default"

  def main(args: Array[String]): Unit = {
    val factory = new ConnectionFactory()
    sys.props.get("host").foreach(host => factory.setHost(host))
    sys.props.get("port").foreach(port => factory.setPort(port.toInt))

    println(s"""$factory: host=${factory.getHost}, port=${factory.getPort}""")

    val p = RabbitMQ(factory)
    val c = RabbitMQ(factory)

    sys.addShutdownHook {
      p.close()
      c.close()
    }

    p.channel.queueDeclare(defaultQueueName, true, false, false, null)

    c.channel.basicConsume(defaultQueueName, true, new DefaultConsumer(c.channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]): Unit = {
        val message = new String(body)
        println(s"[x] Received '$message'")
      }
    })

    while (true) {
      val message = ZonedDateTime.now().toString
      p.channel.basicPublish("", defaultQueueName, null, message.getBytes())
      println(s"[x] Sent '$message'")
      Thread.sleep(1000)
    }
  }
}
