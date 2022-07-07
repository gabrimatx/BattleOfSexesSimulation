public abstract class SubPopulation extends ThreadGroup {
    public int size;
    public final Population population = (Population) this.getParent().getParent();

    public SubPopulation(ThreadGroup parent, String name, int size) {
        super(parent, name);
        this.size = size;
        addToInitialPopulation();
    }

    public synchronized void increaseSize() {
         size++;
         synchronized (population) {
             population.size++;
         }
    }

    public synchronized void decreaseSize() {
         size--;
        synchronized (population) {
            population.size--;
        }
    }

    public abstract void addToInitialPopulation();

    public abstract class SubType extends Thread {
        private int countLife = 0;
        private final int id;
        public int credit = population.startCredit;
        public int lifePoints = population.lifePoints;
        public boolean isSingle = true;
        public Object ring = new Object();

        public SubType(ThreadGroup group, String name) {
            super(group, name);
            this.id = ++population.ID;
        }

        public void updateLifePoints() {
            countLife++;
            lifePoints--;
            int deltaCredit = this.credit / (countLife*countLife);
            System.out.println(deltaCredit);
            lifePoints += deltaCredit;
            this.credit -= deltaCredit;
        }

        public abstract String getSubType();

        public <E extends SubPopulation> E getPopulation() {
            return (E) SubPopulation.this;
        }

        public synchronized void hey() {
            notify();
        }

        @Override
        public long getId() {
            return this.id;
        }

        @Override
        public abstract void run();

    }
}
