public class Hash<T extends Comparable<T>> {

    /* Represents number of items in map */
    private int capacity;
    private int TABLE_SIZE;

    private String[][] key_list;
    private T[][] value_list;
    private int[] cap_list; // Current # items in each array

    @SuppressWarnings("unchecked")
    public Hash(int size) {
        this.key_list = new String[size][];
        this.value_list = (T[][]) (new Object[size][]);
        this.cap_list = new int[size];
        for (int i = 0; i < size; i++) {
            this.key_list[i] = new String[1];
            this.value_list[i] = (T[]) (new Object[1]);
            cap_list[i] = 0;
        }
        this.capacity = 0;
        this.TABLE_SIZE = size;
    }

    public int hash(String key) {
        try {
            return key.hashCode() % this.TABLE_SIZE;
        } catch (Exception e) {
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    public boolean set(String key, T value) {
        // If the value is already set, return success
        if (this.get(key).compareTo(value) == 0) {
            return true;
        } else if (this.load() == 1) {
            try {
                /* Map is about to go over capacity.
                Rehash the whole map and reinsert all key/values.*/

                String[][] old_key_list = this.key_list;
                T[][] old_value_list = this.value_list;
                int[] old_cap_list = this.cap_list;
                this.TABLE_SIZE *= 2;
                this.key_list = new String[this.TABLE_SIZE][];
                this.value_list = (T[][]) (new Object[this.TABLE_SIZE][]);
                this.cap_list = new int[this.TABLE_SIZE];
                this.capacity = 0;

                for (int i = 0; i < this.cap_list.length; i++) {
                    // New #s of columns should be on average half that of old.
                    // The # of rows doubled so the average # of cols should halve.
                    this.key_list[i] = new String[old_cap_list[i]/2];
                    this.value_list[i] = (T[]) (new Object[old_cap_list[i]/2]);
                    this.cap_list[i] = 0;
                }

                // Insert every key-value pair from the old hashmap into this.
                for (int i = 0; i < TABLE_SIZE/2; i++) {
                    for (int j = 0; j < old_cap_list[i]; j++) {
                        String k = old_key_list[i][j];
                        T val = old_value_list[i][j];
                        this.set(k, val);
                    }
                }

                return this.set(key, value);
            } catch (Exception e) {
                return false; // Error
            }
        } else {
            try {
                // There is enough free capacity. Insert freely
                int hash = this.hash(key);
                if (hash == -1) return false; // Error
                if (this.key_list[hash].length == this.cap_list[hash]) {
                    // Both arrays are at capacity. Double them.
                    String[] new_key_list = new String[this.cap_list[hash]*2];
                    @SuppressWarnings("unchecked")
                    T[] new_value_list = (T[]) (new Object[this.cap_list[hash]*2]);
                    for (int i = 0; i < this.key_list[hash].length; i++) {
                        // Insert all old key-value pairs into new arrays
                        new_key_list[i] = this.key_list[hash][i];
                        new_value_list[i] = this.value_list[hash][i];
                    }
                    // Establish the new key/value arrays and call set() again.
                    this.key_list[hash] = new_key_list;
                    this.value_list[hash] = new_value_list;
                    return this.set(key, value);
                }
                this.key_list[hash][this.cap_list[hash]] = key;
                this.value_list[hash][this.cap_list[hash]] = value;
                this.cap_list[hash]++;
                this.capacity++;
                return true;
            } catch (Exception e) {
                return false; // Error
            }
        }
    }

    public T get(String key) {
        try {
            int hash = this.hash(key);
            if (hash == -1) return null; // Error
            for (int i = 0; i < cap_list[hash]; i++) {
                if (key_list[hash][i].equals(key)) {
                    return value_list[hash][i];
                }
            }
            return null; // Key not found
        } catch (Exception e) {
            return null; // Error
        }
    }

    public T delete(String key) {
        try {
            int hash = this.hash(key);
            if (hash == -1) return null; // Error
            for (int i = 0; i < this.cap_list[hash]; i++) {
                if (key_list[hash][i].equals(key)) { // Key found.
                    // This works because reference still points to the
                    // Object after it is no longer pointed to by the array
                    T ret = this.value_list[hash][i];
                    for (int j = i; j < this.cap_list[hash]-1; j++) {
                        // Shift all after j'th position back once.
                        this.key_list[hash][j] = this.key_list[hash][j+1];
                        this.value_list[hash][j] = this.value_list[hash][j+1];
                    }
                    this.cap_list[hash]--;
                    this.capacity--;
                    // Check if the deletion that just occurred renders the
                    // array to be in a state where only half of it is utilized
                    int curr_len = this.key_list[hash].length;
                    if (this.cap_list[hash]*2 <= curr_len) {
                        // The latter half of the array is not being used.
                        // Resize the array to half its length to save space.
                        String[] new_key_list = new String[curr_len/2];
                        @SuppressWarnings("unchecked")
                        T[] new_value_list = (T[]) (new Object[curr_len/2]);
                        for (int k = 0; k < curr_len/2; k++) {
                            new_key_list[k] = this.key_list[hash][k];
                            new_value_list[k] = this.value_list[hash][k];
                        }
                        this.key_list[hash] = new_key_list;
                        this.value_list[hash] = new_value_list;
                    }
                    return ret;
                }
            }
            return null; // Key not found
        } catch (Exception e) {
            return null; // Error
        }
    }

    public float load() {
        return this.capacity/this.TABLE_SIZE;
    }

    public String printHash() {
        String s = "";
        for (int i = 0; i < this.TABLE_SIZE; i++) {
            s += "Row " + (i+1) + ": ";
            for (int j = 0; j < this.cap_list[i]; j++) {
                s += " -- " + this.key_list[i][j] + " | " + this.value_list[i][j] + " -- ";
            }
            s += ": \n";
        }
        return s;
    }

    public static void main(String[] args) {

        System.out.println("Hi");

    }

}
