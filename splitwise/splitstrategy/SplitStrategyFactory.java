package com.LLD.splitwise.splitstrategy;

public class SplitStrategyFactory {
    private static final String EQUAL = "Equal";
    private static final String PERCENTAGE = "Percentage";
    private static final String RATIO = "Ratio";
    private static final String EXACT = "Exact";
    public static SplitStrategy getSplitStrategy(String splitMethod) {
        if(splitMethod.equals(EQUAL)){
            return new EqualSplitStrategy();
        }else if(splitMethod.equals(PERCENTAGE)){
            return new PercentageSplitStrategy();
        }else if(splitMethod.equals(RATIO)){
            return new RatioSplitStrategy();
        }else{
            return new ExactSplitStrategy();
        }
    }
}
