package org.example;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class Produtor {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Produtor no ar ===");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        
        String NOME_FILA = "PDIST_20242";
        try (
                Connection conexao = connectionFactory.newConnection();
                Channel canal = conexao.createChannel();
                ) {
            
            //durável
            //exclusivo
            //autodelete
            canal.queueDeclare(NOME_FILA, true, false, false, null);
            String mensagem = "tchau mundo!";
            canal.basicPublish("", NOME_FILA, MessageProperties.TEXT_PLAIN, mensagem.getBytes());
            System.out.println("Mensagem enviada!");
        }
    }
}
