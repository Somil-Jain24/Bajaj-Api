package com.example.demo.service;

import com.example.demo.dto.BFHLRequest;
import com.example.demo.dto.BFHLResponse;
import com.example.demo.util.Constants;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BFHLServiceImpl implements BFHLService {

    @Override
    public BFHLResponse processBFHLData(BFHLRequest request) {
        List<String> data = request.getData() == null ? new ArrayList<>() : request.getData();

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        
        BigInteger sum = BigInteger.ZERO;
        BigInteger two = new BigInteger("2");

        for (String item : data) {
            if (item == null || item.isEmpty()) continue;

            if (isInteger(item)) {
                try {
                    BigInteger number = new BigInteger(item);
                    if (number.remainder(two).equals(BigInteger.ZERO)) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                    sum = sum.add(number);
                } catch (Exception e) {
                    specialCharacters.add(item);
                }
            } else if (isAlphabetOnly(item)) {
                alphabets.add(item.toUpperCase());
            } else {
                specialCharacters.add(item);
            }
        }

        String concatString = generateConcatString(alphabets);
        String userId = Constants.USER_ID;

        return BFHLResponse.builder()
                .isSuccess(true)
                .userId(userId)
                .email(Constants.EMAIL)
                .rollNumber(Constants.ROLL_NUMBER)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(sum.toString())
                .concatString(concatString)
                .build();
    }

    private boolean isInteger(String s) {
        return s.matches("-?\\d+");
    }

    private boolean isAlphabetOnly(String s) {
        return s.matches("[a-zA-Z]+");
    }

    private String generateConcatString(List<String> alphabets) {
        if (alphabets.isEmpty()) return "";

        StringBuilder concatenated = new StringBuilder();
        for (String alpha : alphabets) {
            concatenated.append(alpha);
        }

        String reversed = concatenated.reverse().toString();
        
        StringBuilder alternating = new StringBuilder();
        boolean toUpper = true;
        for (char c : reversed.toCharArray()) {
            if (Character.isLetter(c)) {
                if (toUpper) {
                    alternating.append(Character.toUpperCase(c));
                } else {
                    alternating.append(Character.toLowerCase(c));
                }
                toUpper = !toUpper;
            } else {
                alternating.append(c);
            }
        }
        return alternating.toString();
    }
}
