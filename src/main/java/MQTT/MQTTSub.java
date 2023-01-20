package MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTSub {

    private final String topic = "Vortrag/Lampe/tele/SENSOR";
    private final String broker = "tcp://192.168.10.100:1883";
    private final String clientId = "JavaSampleSub";
    private final MemoryPersistence persistence = new MemoryPersistence();
    private MqttClient sampleClient;

    public MQTTSub() {
        connectToBroker();
        subscribeToTopic();
    }

    private void connectToBroker() {
        try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(1000);
            connOpts.setUserName("Jonas");
            connOpts.setPassword(("Jonas54").toCharArray());
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    private void subscribeToTopic() {
        try {
            sampleClient.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message: " + message.toString());
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
            sampleClient.subscribe(topic);
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
