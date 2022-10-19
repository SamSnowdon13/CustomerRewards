package com.sam.customerrewards.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Rewards {

    private final Map<String, Integer> monthlyRewards = new ConcurrentHashMap<>();

    @JsonIgnore
    public Integer getTotalRewards() {
        return monthlyRewards
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Integer getRewardsByDate(String key) {
        return monthlyRewards.getOrDefault(key, 0);
    }

    public void addRewards(String month, Integer year, Integer rewards) {
        String key = year + "-" + month;
        Integer totalRewards = getRewardsByDate(key) + rewards;
        monthlyRewards.put(key, totalRewards);
    }
}
