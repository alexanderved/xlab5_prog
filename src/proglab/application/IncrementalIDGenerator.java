package proglab.application;

public class IncrementalIDGenerator extends IDGenerator {
    private static int NEXT_ID = 1;

    @Override
    public int generateID() {
        return NEXT_ID++;
    }

    @Override
    public void reserve(int id) {
        if (id >= NEXT_ID) {
            NEXT_ID = id + 1;
        }
    }
}