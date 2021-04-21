package labs;



public class Key implements Comparable<Key>{

    @Override
	public String toString() {
		return x+":"+y;
	}

    public final int x;
    private final int y;

    public Key(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

	@Override
	public int compareTo(Key arg0) {
		if (x!=arg0.x) {
			return (x > arg0.x)? 1: -1;
		}
		else {
			if (y!=arg0.y) {
				return (y > arg0.y) ? 1: -1;
			}
		}
		return 0;
	}

}
