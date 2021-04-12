package io.github.walayd.mower_project.domain.orientation;

public enum Orientation {
    N, E, W, S;

    static {
        N.left = W;
        N.right = E;
        W.left = S;
        W.right = N;
        E.left = N;
        E.right = S;
        S.left = E;
        S.right = W;
    }

    private Orientation left;
    private Orientation right;

    public Orientation turnTo(Turn t) {
        return t == Turn.L ? left : right;
    }
}

