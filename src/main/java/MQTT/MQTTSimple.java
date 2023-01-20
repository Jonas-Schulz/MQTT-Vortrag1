package MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTSimple {

    private final String topic = "Vortrag/IntelliJ";
    private final int qos = 2;
    private final String broker = "tcp://192.168.10.100:1883";
    private final String clientId = "JavaSample";
    private final MemoryPersistence persistence = new MemoryPersistence();
    private MqttClient sampleClient;

    public MQTTSimple() {
        connectToBroker();
    }

    private void connectToBroker() {
        try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("Jonas");
            connOpts.setPassword(("Jonas54").toCharArray());
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void pushMessageToBroker(String value) {
        try {
            System.out.println("Publishing message: " + value);
            MqttMessage message = new MqttMessage(value.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnectFromBroker() {
        try {
            sampleClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
