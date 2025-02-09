package org.example;

import com.rabbitmq.client.*;

public class Consumidor {
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
        canal.queueDeclare(NOME_FILA, true, false, false, null);
        
        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody());
            System.out.println("Recebi mensagem: " + mensagem);
        };
        
        canal.basicConsume(NOME_FILA, false, callback, consumerTag-> {});
        System.out.println("Concluí");
        
    }
}
