package dev.chiba;

import dev.chiba.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic",groupId = "notification-consumers")
    public void handleNotification(OrderEvent orderEvent){
        log.info(orderEvent.getOrderNumber());
    }


    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> numsList = Arrays.stream(nums)
                .boxed()
                .collect(Collectors.toList());

        for(int i=0;i < nums.length ; i++){
            for(int j=i+1;j < nums.length; j++){
                int sum = nums[i] + nums[j];
                if(numsList.contains(-sum)){
                    ans.add(new ArrayList<>(Arrays.asList(nums[i],nums[j],-sum)));
                }
            }
        }

        ans.forEach(list -> list.sort(Integer::compareTo));
        return ans.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
