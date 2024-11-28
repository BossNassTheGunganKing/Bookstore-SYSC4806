package codelook.jpa;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "orders", groupId = "group1")
    void listener(String data){
        System.out.println("Listener Received: " + data);
    }

}
