package ie.tus.project;

import java.util.Arrays;

public non-sealed abstract  class CharacterBase implements Describable {
	//Basic Character Details
    protected String name;
    protected int level;
    protected int hp;
    protected final int[] stats;

    protected CharacterBase(String name, int level, int[] stats) {
    	//Error Handling
    	if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (stats == null || stats.length != 6) {
            throw new IllegalArgumentException("Stats must be an array of 6 values");
        }
        this.name = name;
        this.level = level;
        this.stats = Arrays.copyOf(stats, stats.length);
        //calculateHP();
    }

    protected abstract void calculateHP();
    //Return Copy of Stats Array
    public int[] getStats() {
        return Arrays.copyOf(stats, stats.length);
    }
    //Get Hit Points
    public int getHp() {
        return hp;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String description() {
        return name + " is level " + level + " with HP " + hp;
    }
}
