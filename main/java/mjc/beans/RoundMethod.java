package main.java.mjc.beans;

public enum RoundMethod {
    FLOOR {
        protected double roundFunction(double value) {
            return Math.floor(value);
        }
    },

    ROUND {
        protected double roundFunction(double value) {
            return Math.round(value);
        }
    };


    protected abstract double roundFunction(double value);

    public int round(double roundedValue, int d) {
        int[] tenPow = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000};
        int result = (int) this.roundFunction(roundedValue / tenPow[d]) * tenPow[d];
        return result;
    }
}
