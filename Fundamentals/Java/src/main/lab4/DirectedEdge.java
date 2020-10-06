package main.lab4;

public class DirectedEdge {
    private int from;
    private int to;
    private double weight;

    /**
     * Create a new edge
     * @param from where this edge is coming from
     * @param to where this edge is going to
     * @param weight the weight of this edge
     */
    public DirectedEdge(int from, int to, double weight) {
        if (from < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (from < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     *
     * @return vertex this edge points from
     */
    public int from() {
        return this.from;
    }

    /**
     *
     * @return vertex this edge points to
     */
    public int to() {
        return this.to;
    }

    /**
     *
     * @return weight of this edge
     */
    public double weight() {
        return this.weight;
    }

    /**
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "DirectedEdge{" +
                "from=" + from +
                ", to=" + to +
                ", weight=" + weight +
                '}';
    }

    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(3, 4, 1000.5);
        System.out.println(e);
    }
}
