import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}

case class RabbitMQ(connection: Connection, channel: Channel) {
  def close(): Unit = {
    channel.close()
    connection.close()
  }
}

object RabbitMQ {
  def apply(factory: ConnectionFactory): RabbitMQ = {
    val co = factory.newConnection()
    val ch = co.createChannel()
    RabbitMQ(co, ch)
  }
}
