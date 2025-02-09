package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ConsumidorAck {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Consumidor no ar ===");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        
        String NOME_FILA = "PDIST_20242";
        Connection conexao = connectionFactory.newConnection();
        Channel canal = conexao.createChannel();
        
        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody());
            System.out.println("Recebi mensagem: " + mensagem);
            try {
                System.out.println("consumindo...");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            canal.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            System.out.println("Enviado ack");
        };
        
        canal.basicConsume(NOME_FILA, false, callback, consumerTag-> {});
        System.out.println("Concluí");
        
    }
}
