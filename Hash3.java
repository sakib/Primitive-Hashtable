public class Hash_Lists<T extends Comparable<T>> {

    /* Represents number of items in map */
    private int capacity;
    private int TABLE_SIZE;

    private ArrayList<String>[] lists_of_keys;
    private ArrayList<T>[] lists_of_values;

    public Hash(int size) {
        lists_of_keys = new ArrayList<String>[size];
        lists_of_values = new ArrayList<T>[size];
        capacity = 0;
        TABLE_SIZE = size;
    }

    public int hash(String key) {
        try {
            return String.hashCode(key) % this.TABLE_SIZE;
        } catch (Exception e) {
            return -1;
        }
    }

    public boolean set(String key, T value) {
        // If the value is already set, return success
        if (this.get(key).compareTo(value) == 0) {
            return true;
        } else if (this.load() == 1) {
            /* Map is about to go over capacity.
            Rehash the whole map and reinsert all key/values.*/

            old_lists_of_keys = this.lists_of_keys;
            old_lists_of_values = this.lists_of_values;
            this.lists_of_keys = new ArrayList<String>[this.TABLE_SIZE*2];
            this.lists_of_values = new ArrayList<T>[this.TABLE_SIZE*2];
            this.TABLE_SIZE *= 2;
            this.capacity = 0;

            for (int i = 0; i < old_lists_of_keys.length; i++) {
                for (int j = 0; j < old_lists_of_keys[i].size(); i++) {
                    // Now insert the old key value pairs into the new arrays.
                    String k = old_lists_of_keys[i].get(j);
                    T val = old_lists_of_values[i].get(j);
                    this.set(k, val);
                }
            }

            return this.set(key, value);

        } else {
            try {
                this.lists_of_keys[this.hash(key)].insert(0, key);
                this.lists_of_values[this.hash(key)].insert(0, value);
                capacity++;
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public T get(String key) {
        try {
            int index = lists_of_keys[this.hash(key)].indexOf(key);
            return lists_of_values[this.hash(key)].get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public T delete(String key) {
        try {
            int index = lists_of_keys[this.hash(key)].indexOf(key);
            this.lists_of_keys[this.hash(key)].remove(index);
            return this.lists_of_values[this.hash(key)].remove(index);
        } catch (Exception e) {
            return null;
        }
    }

    public float load() {
        return this.capacity/this.TABLE_SIZE;
    }

    public String toString() {
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

}
