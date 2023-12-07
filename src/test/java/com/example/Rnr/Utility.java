package com.example.Rnr;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utility {
    public String createRandomString(int length){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public List<String> createRandomStrings(int n,int length){
        Set<String> randomStrings = new HashSet<>();
        while (randomStrings.size() <= n){
            randomStrings.add(createRandomString(length));
        }

        return new ArrayList<>(randomStrings);
    }

    public Date createRandomDate(int minYear, int maxYear){
        Random random = new Random(System.currentTimeMillis());

        List<Integer> longDayMonth = List.of(1,3,5,7,8,10,12);
        List<Integer> february = List.of(2);

        int year = random.nextInt(minYear,maxYear);
        int month = random.nextInt(1,12);
        int day;
        if (longDayMonth.contains(month)){
            day = random.nextInt(1,31);
        } else if (february.contains(month)) {
            day = random.nextInt(1,28);
        }else {
            day = random.nextInt(1,30);
        }
        int hour = random.nextInt(0,23);
        int minute = random.nextInt(0,59);
        int second = random.nextInt(0,59);

        return new Date(year,month,day,hour,minute,second);
    }



    public List<Date> createRandomDates(int n,int minYear,int maxYear){
        List<Date> dates = Stream.iterate(0,a->a+1).limit(n)
                .map(a->createRandomDate(minYear,maxYear))
                .collect(Collectors.toList());

        return dates;
    }


    public List<Integer> randomNumbers(int n,int min,int max){
        Set<Integer> numbers = new HashSet<>();
        Random random = new Random();
        while(numbers.size()<n){
            numbers.add(random.nextInt(min,max));
        }

        return new ArrayList<>(numbers);
    }

}
