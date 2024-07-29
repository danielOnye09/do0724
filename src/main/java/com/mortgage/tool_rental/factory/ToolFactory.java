package com.mortgage.tool_rental.factory;

import com.mortgage.tool_rental.model.Tool;

public class ToolFactory {
    public static Tool createTool(String toolCode) {
        return switch (toolCode) {
            case "CHNS" -> new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true);
            case "LADW" -> new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false);
            case "JAKD" -> new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false);
            case "JAKR" -> new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false);
            default -> throw new IllegalArgumentException("Invalid tool code");
        };
    }
}

