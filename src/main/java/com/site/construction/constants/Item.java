package com.site.construction.constants;

//Lamd operation Items that cost
public enum Item {
    COMMUNICATION_OVERHEAD(1, "communication overhead"),
    FUEL(1,"fuel usage"),
    UNCLEARED_SQUARE(3, "uncleared squares"),
    PROTECTED_TREE_DESTRUCTION(10, "destruction of protected tree"),
    PAINT_DAMAGE(2,"paint damage to bulldozer");

    private final int credits;
    private final String description;
    Item(int credits, String description){
        this.credits = credits;
        this.description = description;
    }

    public int getCredits() {
        return this.credits;
    }

    public String getDescription() {
        return this.description;
    }
}
