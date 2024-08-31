package com.tawham.ifpb.sistema.distribuida;

import com.rabbitmq.client.*;

public class EmitLogDirect {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setHost("localhost");
        factory.setUsername("mqadmin");
        factory.setPassword("Admin123XX_");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String severity = getSeverity(argv);
            String message = getMessage(argv);
            String stringToInsert = "Taw-Ham Almeida Balbino de Paula";
            int middleIndex = message.length() / 2;
            String modifiedMessage = message.substring(0, middleIndex) + stringToInsert + message.substring(middleIndex);
            channel.basicPublish(EXCHANGE_NAME, severity, null, modifiedMessage.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity + "':'" + modifiedMessage + "'");
        }
    }

    private static String getSeverity(String[] strings) {
        if (strings.length < 1)
            return "info";
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length <= startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
