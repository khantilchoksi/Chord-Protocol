import ChordNode;

public class FingerEntry {
    int m; // Bits
    int n; // The node's value associated with the FingerTable
    int k; // Finger Table Index
    // 1 <= k <= m

    // [intervalStart , intervalEnd)
    int intervalStart; // finger[k].start = intervalStart
    int intervalEnd;

    ChordNode node;

    public FingerEntry(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        initializeStart();
    }

    public void initializeStart() {
        this.intervalStart = (n + (int) Math.pow(2, k - 1)) % (int) (Math.pow(2, m));
    }
}