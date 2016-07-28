package ru.xc0re.games.tetris.model;

import ru.xc0re.games.tetris.enums.SpaceType;

public class Space {

    private SpaceType type;

    private Block block;

    public boolean isEmpty() {
        return type != SpaceType.NotEmpty;
    }

    public Block getBlock() {
        return block;
    }

    public void stopMoving() {
        if (type == SpaceType.MovingPart)
            type = SpaceType.NotEmpty;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setType(SpaceType type) {
        this.type = type;
    }

    public SpaceType getType() {
        return type;
    }

    public boolean isBroken() {
        return type == SpaceType.BrokenBlock;
    }
}
