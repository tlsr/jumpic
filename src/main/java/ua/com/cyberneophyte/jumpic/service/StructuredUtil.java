package ua.com.cyberneophyte.jumpic.service;

import ua.com.cyberneophyte.jumpic.domain.Module;
import ua.com.cyberneophyte.jumpic.domain.Structured;

import java.util.List;

public  class StructuredUtil {

    public static void incrementConsecutiveNumber(Structured element, List<? extends Structured> listOfElements){
        int lastConsecutiveNumber = 1;
        if (listOfElements.isEmpty()) {
            element.setConsecutiveNumber(lastConsecutiveNumber);
        } else {
            for (Structured temp : listOfElements) {
                if (temp.getConsecutiveNumber()>lastConsecutiveNumber){
                    lastConsecutiveNumber = temp.getConsecutiveNumber();
                }
            }
            element.setConsecutiveNumber(lastConsecutiveNumber+1);
        }
    }

    public static void decrementConsecutiveNumber(Structured element, List<? extends Structured> listOfElements){
        int currentConsecutiveNumber = element.getConsecutiveNumber();
        System.out.println("--------------------------new decrement----------------");
        for (Structured temp: listOfElements) {
            System.out.println("element ConsecutiveNumber: "+temp.getConsecutiveNumber());
            System.out.println("currentConsecutiveNumber: "+ currentConsecutiveNumber);
            if(temp.getConsecutiveNumber()>currentConsecutiveNumber){
                temp.setConsecutiveNumber(temp.getConsecutiveNumber()-1);

            }
        }
    }
}
