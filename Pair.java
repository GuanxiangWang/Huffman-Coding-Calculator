public class Pair implements Comparable<Pair>{
    private char value;
    private double probability;

    public Pair(char value, double probability) {
        this.value = value;
        this.probability = probability;
    }

    public char getValue() {
        return value;
    }

    public double getProbability() {
        return probability;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "value=" + value +
                ", probability=" + probability +
                '}';
    }

    //compare the probability of this pair and another pair
    @Override
    public int compareTo(Pair p){
        return Double.compare(this.getProbability(), p.getProbability());
    }
}
